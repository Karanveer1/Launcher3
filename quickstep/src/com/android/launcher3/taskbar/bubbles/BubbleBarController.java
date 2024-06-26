/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.launcher3.taskbar.bubbles;

import static android.content.pm.LauncherApps.ShortcutQuery.FLAG_GET_PERSONS_DATA;
import static android.content.pm.LauncherApps.ShortcutQuery.FLAG_MATCH_CACHED;
import static android.content.pm.LauncherApps.ShortcutQuery.FLAG_MATCH_DYNAMIC;
import static android.content.pm.LauncherApps.ShortcutQuery.FLAG_MATCH_PINNED_BY_ANY_LAUNCHER;
import static android.os.Process.THREAD_PRIORITY_BACKGROUND;

import static com.android.launcher3.icons.FastBitmapDrawable.WHITE_SCRIM_ALPHA;
import static com.android.launcher3.util.Executors.MAIN_EXECUTOR;
import static com.android.systemui.shared.system.QuickStepContract.SYSUI_STATE_BOUNCER_SHOWING;
import static com.android.systemui.shared.system.QuickStepContract.SYSUI_STATE_IME_SHOWING;
import static com.android.systemui.shared.system.QuickStepContract.SYSUI_STATE_IME_SWITCHER_SHOWING;
import static com.android.systemui.shared.system.QuickStepContract.SYSUI_STATE_NOTIFICATION_PANEL_EXPANDED;
import static com.android.systemui.shared.system.QuickStepContract.SYSUI_STATE_QUICK_SETTINGS_EXPANDED;
import static com.android.systemui.shared.system.QuickStepContract.SYSUI_STATE_STATUS_BAR_KEYGUARD_SHOWING;
import static com.android.systemui.shared.system.QuickStepContract.SYSUI_STATE_STATUS_BAR_KEYGUARD_SHOWING_OCCLUDED;

import android.annotation.BinderThread;
import android.annotation.Nullable;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Log;
import android.util.PathParser;
import android.view.LayoutInflater;

import com.android.internal.graphics.ColorUtils;
import com.android.launcher3.R;
import com.android.launcher3.icons.BitmapInfo;
import com.android.launcher3.icons.BubbleIconFactory;
import com.android.launcher3.shortcuts.ShortcutRequest;
import com.android.launcher3.taskbar.TaskbarControllers;
import com.android.launcher3.util.Executors.SimpleThreadFactory;
import com.android.quickstep.SystemUiProxy;
import com.android.wm.shell.bubbles.IBubblesListener;
import com.android.wm.shell.common.bubbles.BubbleBarUpdate;
import com.android.wm.shell.common.bubbles.BubbleInfo;
import com.android.wm.shell.common.bubbles.RemovedBubble;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * This registers a listener with SysUIProxy to get information about changes to the bubble
 * stack state from WMShell (SysUI). The controller is also responsible for loading the necessary
 * information to render each of the bubbles & dispatches changes to
 * {@link BubbleBarViewController} which will then update {@link BubbleBarView} as needed.
 *
 * For details around the behavior of the bubble bar, see {@link BubbleBarView}.
 */
public class BubbleBarController extends IBubblesListener.Stub {

    private static final String TAG = BubbleBarController.class.getSimpleName();
    private static final boolean DEBUG = false;

    // Whether bubbles are showing in the bubble bar from launcher
    public static final boolean BUBBLE_BAR_ENABLED =
            SystemProperties.getBoolean("persist.wm.debug.bubble_bar", false);

    private static final int MASK_HIDE_BUBBLE_BAR = SYSUI_STATE_BOUNCER_SHOWING
            | SYSUI_STATE_STATUS_BAR_KEYGUARD_SHOWING
            | SYSUI_STATE_STATUS_BAR_KEYGUARD_SHOWING_OCCLUDED
            | SYSUI_STATE_IME_SHOWING
            | SYSUI_STATE_NOTIFICATION_PANEL_EXPANDED
            | SYSUI_STATE_QUICK_SETTINGS_EXPANDED
            | SYSUI_STATE_IME_SWITCHER_SHOWING;

    private static final int MASK_HIDE_HANDLE_VIEW = SYSUI_STATE_BOUNCER_SHOWING
            | SYSUI_STATE_STATUS_BAR_KEYGUARD_SHOWING
            | SYSUI_STATE_STATUS_BAR_KEYGUARD_SHOWING_OCCLUDED;

    private static final int MASK_SYSUI_LOCKED = SYSUI_STATE_BOUNCER_SHOWING
            | SYSUI_STATE_STATUS_BAR_KEYGUARD_SHOWING
            | SYSUI_STATE_STATUS_BAR_KEYGUARD_SHOWING_OCCLUDED;

    private final Context mContext;
    private final BubbleBarView mBarView;
    private final ArrayMap<String, BubbleBarBubble> mBubbles = new ArrayMap<>();

    private static final Executor BUBBLE_STATE_EXECUTOR = Executors.newSingleThreadExecutor(
            new SimpleThreadFactory("BubbleStateUpdates-", THREAD_PRIORITY_BACKGROUND));
    private final Executor mMainExecutor;
    private final LauncherApps mLauncherApps;
    private final BubbleIconFactory mIconFactory;

    private BubbleBarBubble mSelectedBubble;

    private BubbleBarViewController mBubbleBarViewController;
    private BubbleStashController mBubbleStashController;
    private BubbleStashedHandleViewController mBubbleStashedHandleViewController;

    /**
     * Similar to {@link BubbleBarUpdate} but rather than {@link BubbleInfo}s it uses
     * {@link BubbleBarBubble}s so that it can be used to update the views.
     */
    private static class BubbleBarViewUpdate {
        boolean expandedChanged;
        boolean expanded;
        String selectedBubbleKey;
        String suppressedBubbleKey;
        String unsuppressedBubbleKey;
        List<RemovedBubble> removedBubbles;
        List<String> bubbleKeysInOrder;

        // These need to be loaded in the background
        BubbleBarBubble addedBubble;
        BubbleBarBubble updatedBubble;
        List<BubbleBarBubble> currentBubbles;

        BubbleBarViewUpdate(BubbleBarUpdate update) {
            expandedChanged = update.expandedChanged;
            expanded = update.expanded;
            selectedBubbleKey = update.selectedBubbleKey;
            suppressedBubbleKey = update.suppressedBubbleKey;
            unsuppressedBubbleKey = update.unsupressedBubbleKey;
            removedBubbles = update.removedBubbles;
            bubbleKeysInOrder = update.bubbleKeysInOrder;
        }
    }

    public BubbleBarController(Context context, BubbleBarView bubbleView) {
        mContext = context;
        mBarView = bubbleView; // Need the view for inflating bubble views.

        if (BUBBLE_BAR_ENABLED) {
            SystemUiProxy.INSTANCE.get(context).setBubblesListener(this);
        }
        mMainExecutor = MAIN_EXECUTOR;
        mLauncherApps = context.getSystemService(LauncherApps.class);
        mIconFactory = new BubbleIconFactory(context,
                context.getResources().getDimensionPixelSize(R.dimen.bubblebar_icon_size),
                context.getResources().getDimensionPixelSize(R.dimen.bubblebar_badge_size),
                context.getResources().getColor(R.color.important_conversation),
                context.getResources().getDimensionPixelSize(
                        com.android.internal.R.dimen.importance_ring_stroke_width));
    }

    public void onDestroy() {
        SystemUiProxy.INSTANCE.get(mContext).setBubblesListener(null);
    }

    public void init(TaskbarControllers controllers, BubbleControllers bubbleControllers) {
        mBubbleBarViewController = bubbleControllers.bubbleBarViewController;
        mBubbleStashController = bubbleControllers.bubbleStashController;
        mBubbleStashedHandleViewController = bubbleControllers.bubbleStashedHandleViewController;

        bubbleControllers.runAfterInit(() -> {
            mBubbleBarViewController.setHiddenForBubbles(!BUBBLE_BAR_ENABLED);
            mBubbleStashedHandleViewController.setHiddenForBubbles(!BUBBLE_BAR_ENABLED);
        });
    }

    /**
     * Updates the bubble bar, handle bar, and stash controllers based on sysui state flags.
     */
    public void updateStateForSysuiFlags(int flags) {
        boolean hideBubbleBar = (flags & MASK_HIDE_BUBBLE_BAR) != 0;
        mBubbleBarViewController.setHiddenForSysui(hideBubbleBar);

        boolean hideHandleView = (flags & MASK_HIDE_HANDLE_VIEW) != 0;
        mBubbleStashedHandleViewController.setHiddenForSysui(hideHandleView);

        boolean sysuiLocked = (flags & MASK_SYSUI_LOCKED) != 0;
        mBubbleStashController.onSysuiLockedStateChange(sysuiLocked);
    }

    //
    // Bubble data changes
    //

    @BinderThread
    @Override
    public void onBubbleStateChange(Bundle bundle) {
        bundle.setClassLoader(BubbleBarUpdate.class.getClassLoader());
        BubbleBarUpdate update = bundle.getParcelable("update", BubbleBarUpdate.class);
        BubbleBarViewUpdate viewUpdate = new BubbleBarViewUpdate(update);
        if (update.addedBubble != null
                || update.updatedBubble != null
                || !update.currentBubbleList.isEmpty()) {
            // We have bubbles to load
            BUBBLE_STATE_EXECUTOR.execute(() -> {
                if (update.addedBubble != null) {
                    viewUpdate.addedBubble = populateBubble(update.addedBubble, mContext, mBarView);
                }
                if (update.updatedBubble != null) {
                    viewUpdate.updatedBubble =
                            populateBubble(update.updatedBubble, mContext, mBarView);
                }
                if (update.currentBubbleList != null && !update.currentBubbleList.isEmpty()) {
                    List<BubbleBarBubble> currentBubbles = new ArrayList<>();
                    for (int i = 0; i < update.currentBubbleList.size(); i++) {
                        BubbleBarBubble b =
                                populateBubble(update.currentBubbleList.get(i), mContext, mBarView);
                        currentBubbles.add(b);
                    }
                    viewUpdate.currentBubbles = currentBubbles;
                }
                mMainExecutor.execute(() -> applyViewChanges(viewUpdate));
            });
        } else {
            // No bubbles to load, immediately apply the changes.
            BUBBLE_STATE_EXECUTOR.execute(
                    () -> mMainExecutor.execute(() -> applyViewChanges(viewUpdate)));
        }
    }

    private void applyViewChanges(BubbleBarViewUpdate update) {
        final boolean isCollapsed = (update.expandedChanged && !update.expanded)
                || (!update.expandedChanged && !mBubbleBarViewController.isExpanded());
        BubbleBarBubble bubbleToSelect = null;
        if (!update.removedBubbles.isEmpty()) {
            for (int i = 0; i < update.removedBubbles.size(); i++) {
                RemovedBubble removedBubble = update.removedBubbles.get(i);
                BubbleBarBubble bubble = mBubbles.remove(removedBubble.getKey());
                if (bubble != null) {
                    mBubbleBarViewController.removeBubble(bubble);
                } else {
                    Log.w(TAG, "trying to remove bubble that doesn't exist: "
                            + removedBubble.getKey());
                }
            }
        }
        if (update.addedBubble != null) {
            mBubbles.put(update.addedBubble.getKey(), update.addedBubble);
            mBubbleBarViewController.addBubble(update.addedBubble);
            if (isCollapsed) {
                // If we're collapsed, the most recently added bubble will be selected.
                bubbleToSelect = update.addedBubble;
            }

        }
        if (update.currentBubbles != null && !update.currentBubbles.isEmpty()) {
            // Iterate in reverse because new bubbles are added in front and the list is in order.
            for (int i = update.currentBubbles.size() - 1; i >= 0; i--) {
                BubbleBarBubble bubble = update.currentBubbles.get(i);
                if (bubble != null) {
                    mBubbles.put(bubble.getKey(), bubble);
                    mBubbleBarViewController.addBubble(bubble);
                    if (isCollapsed) {
                        // If we're collapsed, the most recently added bubble will be selected.
                        bubbleToSelect = bubble;
                    }
                } else {
                    if (update.addedBubble != null) {
                        Log.w(TAG, "trying to add bubble but null after loading! "
                            + update.addedBubble.getKey());
                    } else {
                        Log.w(TAG, "trying to add bubble but null ");
                    }
                }
            }
        }

        // Adds and removals have happened, update visibility before any other visual changes.
        mBubbleBarViewController.setHiddenForBubbles(mBubbles.isEmpty());
        mBubbleStashedHandleViewController.setHiddenForBubbles(mBubbles.isEmpty());

        if (update.updatedBubble != null) {
            // TODO: (b/269670235) handle updates:
            //  (1) if content / icons change -- requires reload & add back in place
            //  (2) if showing update dot changes -- tell the view to hide / show the dot
        }
        if (update.bubbleKeysInOrder != null && !update.bubbleKeysInOrder.isEmpty()) {
            // Create the new list
            List<BubbleBarBubble> newOrder = update.bubbleKeysInOrder.stream()
                    .map(mBubbles::get).filter(Objects::nonNull).toList();
            if (!newOrder.isEmpty()) {
                mBubbleBarViewController.reorderBubbles(newOrder);
            }
        }
        if (update.suppressedBubbleKey != null) {
            // TODO: (b/273316505) handle suppression
        }
        if (update.unsuppressedBubbleKey != null) {
            // TODO: (b/273316505) handle suppression
        }
        if (update.selectedBubbleKey != null) {
            if (mSelectedBubble != null
                    && !update.selectedBubbleKey.equals(mSelectedBubble.getKey())) {
                BubbleBarBubble newlySelected = mBubbles.get(update.selectedBubbleKey);
                if (newlySelected != null) {
                    bubbleToSelect = newlySelected;
                } else {
                    Log.w(TAG, "trying to select bubble that doesn't exist:"
                            + update.selectedBubbleKey);
                }
            }
        }
        if (bubbleToSelect != null) {
            setSelectedBubble(bubbleToSelect);
        }
        if (update.expandedChanged) {
            if (update.expanded != mBubbleBarViewController.isExpanded()) {
                mBubbleBarViewController.setExpandedFromSysui(update.expanded);
            } else {
                Log.w(TAG, "expansion was changed but is the same");
            }
        }
    }

    /**
     * Sets the bubble that should be selected. This notifies the views, it does not notify
     * WMShell that the selection has changed, that should go through
     * {@link SystemUiProxy#showBubble}.
     */
    public void setSelectedBubble(BubbleBarBubble b) {
        if (!Objects.equals(b, mSelectedBubble)) {
            if (DEBUG) Log.w(TAG, "selectingBubble: " + b.getKey());
            mSelectedBubble = b;
            mBubbleBarViewController.updateSelectedBubble(mSelectedBubble);
        }
    }

    /**
     * Returns the selected bubble or null if no bubble is selected.
     */
    @Nullable
    public String getSelectedBubbleKey() {
        if (mSelectedBubble != null) {
            return mSelectedBubble.getKey();
        }
        return null;
    }

    //
    // Loading data for the bubbles
    //

    @Nullable
    private BubbleBarBubble populateBubble(BubbleInfo b, Context context, BubbleBarView bbv) {
        String appName;
        Bitmap badgeBitmap;
        Bitmap bubbleBitmap;
        Path dotPath;
        int dotColor;

        boolean isImportantConvo = b.isImportantConversation();

        ShortcutRequest.QueryResult result = new ShortcutRequest(context,
                new UserHandle(b.getUserId()))
                .forPackage(b.getPackageName(), b.getShortcutId())
                .query(FLAG_MATCH_DYNAMIC
                        | FLAG_MATCH_PINNED_BY_ANY_LAUNCHER
                        | FLAG_MATCH_CACHED
                        | FLAG_GET_PERSONS_DATA);

        ShortcutInfo shortcutInfo = result.size() > 0 ? result.get(0) : null;
        if (shortcutInfo == null) {
            Log.w(TAG, "No shortcutInfo found for bubble: " + b.getKey()
                    + " with shortcutId: " + b.getShortcutId());
        }

        ApplicationInfo appInfo;
        try {
            appInfo = mLauncherApps.getApplicationInfo(
                    b.getPackageName(),
                    0,
                    new UserHandle(b.getUserId()));
        } catch (PackageManager.NameNotFoundException e) {
            // If we can't find package... don't think we should show the bubble.
            Log.w(TAG, "Unable to find packageName: " + b.getPackageName());
            return null;
        }
        if (appInfo == null) {
            Log.w(TAG, "Unable to find appInfo: " + b.getPackageName());
            return null;
        }
        PackageManager pm = context.getPackageManager();
        appName = String.valueOf(appInfo.loadLabel(pm));
        Drawable appIcon = appInfo.loadUnbadgedIcon(pm);
        Drawable badgedIcon = pm.getUserBadgedIcon(appIcon, new UserHandle(b.getUserId()));

        // Badged bubble image
        Drawable bubbleDrawable = mIconFactory.getBubbleDrawable(context, shortcutInfo,
                b.getIcon());
        if (bubbleDrawable == null) {
            // Default to app icon
            bubbleDrawable = appIcon;
        }

        BitmapInfo badgeBitmapInfo = mIconFactory.getBadgeBitmap(badgedIcon, isImportantConvo);
        badgeBitmap = badgeBitmapInfo.icon;

        float[] bubbleBitmapScale = new float[1];
        bubbleBitmap = mIconFactory.getBubbleBitmap(bubbleDrawable, bubbleBitmapScale);

        // Dot color & placement
        Path iconPath = PathParser.createPathFromPathData(
                context.getResources().getString(
                        com.android.internal.R.string.config_icon_mask));
        Matrix matrix = new Matrix();
        float scale = bubbleBitmapScale[0];
        float radius = BubbleView.DEFAULT_PATH_SIZE / 2f;
        matrix.setScale(scale /* x scale */, scale /* y scale */, radius /* pivot x */,
                radius /* pivot y */);
        iconPath.transform(matrix);
        dotPath = iconPath;
        dotColor = ColorUtils.blendARGB(badgeBitmapInfo.color,
                Color.WHITE, WHITE_SCRIM_ALPHA);


        LayoutInflater inflater = LayoutInflater.from(context);
        BubbleView bubbleView = (BubbleView) inflater.inflate(
                R.layout.bubblebar_item_view, bbv, false /* attachToRoot */);

        BubbleBarBubble bubble = new BubbleBarBubble(b, bubbleView,
                badgeBitmap, bubbleBitmap, dotColor, dotPath, appName);
        bubbleView.setBubble(bubble);
        return bubble;
    }
}

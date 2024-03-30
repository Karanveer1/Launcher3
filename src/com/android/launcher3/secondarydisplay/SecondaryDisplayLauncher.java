/*
 * Copyright (C) 2020 The Android Open Source Project
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
package com.android.launcher3.secondarydisplay;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AppOpsManager;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.android.launcher3.AbstractFloatingView;
import com.android.launcher3.BaseDraggingActivity;
import com.android.launcher3.BubbleTextView;
import com.android.launcher3.DragSource;
import com.android.launcher3.DropTarget;
import com.android.launcher3.InvariantDeviceProfile;
import com.android.launcher3.LauncherAppState;
import com.android.launcher3.LauncherModel;
import com.android.launcher3.LauncherPrefs;
import com.android.launcher3.LauncherSettings;
import com.android.launcher3.Manifest;
import com.android.launcher3.R;
import android.os.Process;
import android.widget.FrameLayout;

import com.android.launcher3.allapps.ActivityAllAppsContainerView;
import com.android.launcher3.allapps.AllAppsStore;
import com.android.launcher3.dragndrop.DragController;
import com.android.launcher3.dragndrop.DragOptions;
import com.android.launcher3.dragndrop.DraggableView;
import com.android.launcher3.graphics.DragPreviewProvider;
import com.android.launcher3.icons.FastBitmapDrawable;
import com.android.launcher3.model.BgDataModel;
import com.android.launcher3.model.StringCache;
import com.android.launcher3.model.data.AppInfo;
import com.android.launcher3.model.data.ItemInfo;
import com.android.launcher3.model.data.ItemInfoWithIcon;
import com.android.launcher3.popup.PopupContainerWithArrow;
import com.android.launcher3.popup.PopupDataProvider;
import com.android.launcher3.touch.ItemClickHandler.ItemClickProxy;
import com.android.launcher3.util.ComponentKey;
import com.android.launcher3.util.IntSet;
import com.android.launcher3.util.OnboardingPrefs;
import com.android.launcher3.util.PackageUserKey;
import com.android.launcher3.util.Preconditions;
import com.android.launcher3.util.Themes;
import com.android.launcher3.views.BaseDragLayer;
import com.skd.nubit.activities.HomeFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Launcher activity for secondary displays
 */
public class SecondaryDisplayLauncher extends BaseDraggingActivity
        implements BgDataModel.Callbacks, DragController.DragListener {

    private LauncherModel mModel;
    private BaseDragLayer mDragLayer;
    private FrameLayout frame_view;
    private SecondaryDragController mDragController;
    private ActivityAllAppsContainerView<SecondaryDisplayLauncher> mAppsView;
    private View mAppsButton;
    private boolean isLocationPermissionRequested = false;
    private boolean isUsageStatsPermissionRequested = false;
    private final int REQUEST_LOCATION_PERMISSION_CODE = 123;
    private final int REQUEST_USAGE_STATS_PERMISSION_CODE = 321;

    private PopupDataProvider mPopupDataProvider;

    private boolean mAppDrawerShown = false;

    private StringCache mStringCache;
    private OnboardingPrefs<?> mOnboardingPrefs;
    private boolean mBindingItems = false;
    private SecondaryDisplayPredictions mSecondaryDisplayPredictions;

    private final int[] mTempXY = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mModel = LauncherAppState.getInstance(this).getModel();
        mDragController = new SecondaryDragController(this);
        frame_view = findViewById(R.id.frame_view);
        mOnboardingPrefs = new OnboardingPrefs<>(this, LauncherPrefs.getPrefs(this));
        mSecondaryDisplayPredictions = SecondaryDisplayPredictions.newInstance(this);
        if (getWindow().getDecorView().isAttachedToWindow()) {
            initUi();
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        initUi();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.getDragController().removeDragListener(this);
    }

    private void initUi() {
        if (mDragLayer != null) {
            return;
        }
        InvariantDeviceProfile currentDisplayIdp = new InvariantDeviceProfile(
                this, getWindow().getDecorView().getDisplay());

        // Disable transpose layout and use multi-window mode so that the icons are scaled properly
        mDeviceProfile = currentDisplayIdp.getDeviceProfile(this)
                .toBuilder(this)
                .setMultiWindowMode(true)
                .setTransposeLayoutWithOrientation(false)
                .build();
        mDeviceProfile.autoResizeAllAppsCells();

        setContentView(R.layout.secondary_launcher);
        mDragLayer = findViewById(R.id.drag_layer);
        mAppsView = findViewById(R.id.apps_view);
        mAppsButton = findViewById(R.id.all_apps_button);

        mDragController.addDragListener(this);
        mPopupDataProvider = new PopupDataProvider(
                mAppsView.getAppsStore()::updateNotificationDots);

        mModel.addCallbacksAndLoad(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDragController.cancelDrag();
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (Intent.ACTION_MAIN.equals(intent.getAction())) {
            // Hide keyboard.
            final View v = getWindow().peekDecorView();
            if (v != null && v.getWindowToken() != null) {
                getSystemService(InputMethodManager.class).hideSoftInputFromWindow(
                        v.getWindowToken(), 0);
            }
        }

        // A new intent will bring the launcher to top. Hide the app drawer to reset the state.
        showAppDrawer(false);
    }
    private boolean checkLocationPermissionGranted() {
        return ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean checkAllPermissionsGranted() {
        return checkLocationPermissionGranted() && isUsageStatsPermissionRequested;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION_CODE) {
            // Check if all permissions are granted
            if (checkLocationPermissionGranted()) {
                requestUsageStatsPermission();
            } else {
                // Location permission denied, handle accordingly
                // You may show a message or take other actions here
                Log.e("MainActivity", "Location permission denied");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_USAGE_STATS_PERMISSION_CODE) {
            Log.e("checkrese", ">>" + resultCode);
            if (resultCode == RESULT_OK) {
                isUsageStatsPermissionRequested = true;
            } else {
                // Usage stats permission request returned
                requestUsageStatsPermission();
            }
        }
    }

    private void requestLocationPermission() {
        if (checkLocationPermissionGranted()) {
            // Location permission already granted, proceed to usage stats permission
            requestUsageStatsPermission();
        } else {
            // Location permission not granted, request it from the user
            isLocationPermissionRequested = true;
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION_CODE
            );
        }
    }

    private void requestUsageStatsPermission() {
        if (!isUsageStatsPermissionRequested) {
            AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOps.checkOpNoThrow(
                    AppOpsManager.OPSTR_GET_USAGE_STATS,
                    Process.myUid(),  // Correct usage of Process.myUid()
                    getPackageName()
            );

            if (mode == AppOpsManager.MODE_ALLOWED) {
                // Usage stats permission already granted, proceed to main screen
                goToMainScreen();
            } else {
                // Usage stats permission not granted, request it from the user
                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                startActivityForResult(intent, REQUEST_USAGE_STATS_PERMISSION_CODE);
            }
        }
    }

    private void goToMainScreen() {
        // Permissions granted, p       ` roceed to the main screen
        Log.d("MainActivity", "Permissions granted, navigating to main screen");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fragmentShowHide(HomeFragment.getInstance(), "HomeFragment");
                /* img_splash.setVisibility(View.VISIBLE); */
            }
        }, 100);
        // Add your code to navigate to the main screen here
    }

    public synchronized void fragmentShowHide(android.app.Fragment addFragment, String addedFragmentName) {
        android.app.FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        android.app.Fragment fm = fragmentManager.findFragmentByTag(addedFragmentName);
        if (fm == null) {
            try {
                // Assuming frame_view is a FrameLayout
                ft.replace(R.id.frame_view, addFragment, addedFragmentName);
                ft.addToBackStack(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            addFragment(fragmentManager, addFragment, addedFragmentName);
        } else {
            ft.show(fragmentManager.findFragmentByTag(addedFragmentName));
        }
    }

    private void addFragment(FragmentManager fragmentManager, android.app.Fragment fragment, String tag) {
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_view, fragment, tag);
        fragmentTransaction.commitAllowingStateLoss();
    }

    public DragController getDragController() {
        return mDragController;
    }

    @Override
    public void onBackPressed() {
        if (finishAutoCancelActionMode()) {
            return;
        }

        if (mDragController.isDragging()) {
            mDragController.cancelDrag();
            return;
        }

        // Note: There should be at most one log per method call. This is enforced implicitly
        // by using if-else statements.
        AbstractFloatingView topView = AbstractFloatingView.getTopOpenView(this);
        if (topView != null && topView.canHandleBack()) {
            // Handled by the floating view.
            topView.onBackInvoked();
        } else {
            showAppDrawer(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mModel.removeCallbacks(this);
    }

    public boolean isAppDrawerShown() {
        return mAppDrawerShown;
    }

    @Override
    public ActivityAllAppsContainerView<SecondaryDisplayLauncher> getAppsView() {
        return mAppsView;
    }

    @Override
    public <T extends View> T getOverviewPanel() {
        return null;
    }

    @Override
    public View getRootView() {
        return mDragLayer;
    }

    @Override
    protected void reapplyUi() { }

    @Override
    public BaseDragLayer getDragLayer() {
        return mDragLayer;
    }

    @Override
    public void bindIncrementalDownloadProgressUpdated(AppInfo app) {
        mAppsView.getAppsStore().updateProgressBar(app);
    }

    /**
     * Called when apps-button is clicked
     */
    public void onAppsButtonClicked(View v) {
        showAppDrawer(true);
    }

    /**
     * Show/hide app drawer card with animation.
     */
    public void showAppDrawer(boolean show) {
        if (show == mAppDrawerShown) {
            return;
        }

        float openR = (float) Math.hypot(mAppsView.getWidth(), mAppsView.getHeight());
        float closeR = Themes.getDialogCornerRadius(this);
        float startR = mAppsButton.getWidth() / 2f;

        float[] buttonPos = new float[]{startR, startR};
        mDragLayer.getDescendantCoordRelativeToSelf(mAppsButton, buttonPos);
        mDragLayer.mapCoordInSelfToDescendant(mAppsView, buttonPos);
        final Animator animator = ViewAnimationUtils.createCircularReveal(mAppsView,
                (int) buttonPos[0], (int) buttonPos[1],
                show ? closeR : openR, show ? openR : closeR);

        if (show) {
            mAppDrawerShown = true;
            mAppsView.setVisibility(View.VISIBLE);
            mAppsButton.setVisibility(View.INVISIBLE);
            mSecondaryDisplayPredictions.updateAppDivider();
        } else {
            mAppDrawerShown = false;
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mAppsView.setVisibility(View.INVISIBLE);
                    mAppsButton.setVisibility(View.VISIBLE);
                    mAppsView.getSearchUiManager().resetSearch();
                }
            });
        }
        animator.start();
    }

    @Override
    public OnboardingPrefs<?> getOnboardingPrefs() {
        return mOnboardingPrefs;
    }

    @Override
    public void startBinding() {
        mBindingItems = true;
        mDragController.cancelDrag();
    }

    @Override
    public boolean isBindingItems() {
        return mBindingItems;
    }

    @Override
    public void finishBindingItems(IntSet pagesBoundFirst) {
        mBindingItems = false;
    }

    @Override
    public void bindDeepShortcutMap(HashMap<ComponentKey, Integer> deepShortcutMap) {
        mPopupDataProvider.setDeepShortcutMap(deepShortcutMap);
    }

    @UiThread
    @Override
    public void bindAllApplications(AppInfo[] apps, int flags,
            Map<PackageUserKey, Integer> packageUserKeytoUidMap) {
        Preconditions.assertUIThread();
        AllAppsStore appsStore = mAppsView.getAppsStore();
        appsStore.setApps(apps, flags, packageUserKeytoUidMap);
        PopupContainerWithArrow.dismissInvalidPopup(this);
    }

    @Override
    public void bindExtraContainerItems(BgDataModel.FixedContainerItems item) {
        if (item.containerId == LauncherSettings.Favorites.CONTAINER_PREDICTION) {
            mSecondaryDisplayPredictions.setPredictedApps(item);
        }
    }

    public SecondaryDisplayPredictions getSecondaryDisplayPredictions() {
        return mSecondaryDisplayPredictions;
    }

    @Override
    public StringCache getStringCache() {
        return mStringCache;
    }

    @Override
    public void bindStringCache(StringCache cache) {
        mStringCache = cache;
    }

    public PopupDataProvider getPopupDataProvider() {
        return mPopupDataProvider;
    }

    @Override
    public OnClickListener getItemOnClickListener() {
        return this::onIconClicked;
    }

    private void onIconClicked(View v) {
        // Make sure that rogue clicks don't get through while allapps is launching, or after the
        // view has detached (it's possible for this to happen if the view is removed mid touch).
        if (v.getWindowToken() == null) return;

        Object tag = v.getTag();
        if (tag instanceof ItemClickProxy) {
            ((ItemClickProxy) tag).onItemClicked(v);
        } else if (tag instanceof ItemInfo) {
            ItemInfo item = (ItemInfo) tag;
            Intent intent;
            if (item instanceof ItemInfoWithIcon
                    && (((ItemInfoWithIcon) item).runtimeStatusFlags
                    & ItemInfoWithIcon.FLAG_INSTALL_SESSION_ACTIVE) != 0) {
                ItemInfoWithIcon appInfo = (ItemInfoWithIcon) item;
                intent = appInfo.getMarketIntent(this);
            } else {
                intent = item.getIntent();
            }
            if (intent == null) {
                throw new IllegalArgumentException("Input must have a valid intent");
            }
            startActivitySafely(v, intent, item);
        }
    }

    /**
     * Core functionality for beginning a drag operation for an item that will be dropped within
     * the secondary display grid home screen
     */
    public void beginDragShared(View child, DragSource source, DragOptions options) {
        Object dragObject = child.getTag();
        if (!(dragObject instanceof ItemInfo)) {
            String msg = "Drag started with a view that has no tag set. This "
                    + "will cause a crash (issue 11627249) down the line. "
                    + "View: " + child + "  tag: " + child.getTag();
            throw new IllegalStateException(msg);
        }
        beginDragShared(child, source, (ItemInfo) dragObject,
                new DragPreviewProvider(child), options);
    }

    private void beginDragShared(View child, DragSource source,
            ItemInfo dragObject, DragPreviewProvider previewProvider, DragOptions options) {

        float iconScale = 1f;
        if (child instanceof BubbleTextView) {
            FastBitmapDrawable icon = ((BubbleTextView) child).getIcon();
            if (icon != null) {
                iconScale = icon.getAnimatedScale();
            }
        }

        // clear pressed state if necessary
        child.clearFocus();
        child.setPressed(false);
        if (child instanceof BubbleTextView) {
            BubbleTextView icon = (BubbleTextView) child;
            icon.clearPressedBackground();
        }

        DraggableView draggableView = null;
        if (child instanceof DraggableView) {
            draggableView = (DraggableView) child;
        }

        final View contentView = previewProvider.getContentView();
        final float scale;
        // The draggable drawable follows the touch point around on the screen
        final Drawable drawable;
        if (contentView == null) {
            drawable = previewProvider.createDrawable();
            scale = previewProvider.getScaleAndPosition(drawable, mTempXY);
        } else {
            drawable = null;
            scale = previewProvider.getScaleAndPosition(contentView, mTempXY);
        }

        int dragLayerX = mTempXY[0];
        int dragLayerY = mTempXY[1];

        Rect dragRect = new Rect();
        if (draggableView != null) {
            draggableView.getSourceVisualDragBounds(dragRect);
            dragLayerY += dragRect.top;
        }

        if (options.preDragCondition != null) {
            int xOffSet = options.preDragCondition.getDragOffset().x;
            int yOffSet = options.preDragCondition.getDragOffset().y;
            if (xOffSet != 0 && yOffSet != 0) {
                dragLayerX += xOffSet;
                dragLayerY += yOffSet;
            }
        }

        if (contentView != null) {
            mDragController.startDrag(
                    contentView,
                    draggableView,
                    dragLayerX,
                    dragLayerY,
                    source,
                    dragObject,
                    dragRect,
                    scale * iconScale,
                    scale,
                    options);
        } else {
            mDragController.startDrag(
                    drawable,
                    draggableView,
                    dragLayerX,
                    dragLayerY,
                    source,
                    dragObject,
                    dragRect,
                    scale * iconScale,
                    scale,
                    options);
        }
    }

    @Override
    public void onDragStart(DropTarget.DragObject dragObject, DragOptions options) { }

    @Override
    public void onDragEnd() { }
}

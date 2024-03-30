package com.mtk.ext.clearall;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;

import androidx.annotation.VisibleForTesting;

import static com.android.launcher3.LauncherState.FLAG_NON_INTERACTIVE;
import static com.android.launcher3.Utilities.mapToRange;
import static com.android.launcher3.anim.Interpolators.LINEAR;

import com.android.launcher3.BaseDraggingActivity;
import com.android.launcher3.Launcher;
import com.android.launcher3.LauncherState;
import com.android.launcher3.R;
import com.android.quickstep.views.RecentsView;
import com.mtk.ext.BaseController;
import com.mtk.ext.FeatureOption;
import com.mtk.ext.LauncherAppMonitor;
import com.mtk.ext.LauncherAppMonitorCallback;
import com.mtk.ext.LogUtils;

public class ClearAllController extends BaseController {
    private static final String TAG = "ClearAllController";
    private static final long ANIM_DURATION = 120;

    private AnimatorSet mAmin;
    private boolean mHasTask;
    private ClearAllButton mClearAllButton;
    private ClearAllButton mDefaultClearAllButton;
    private ClearAllButton mOtherClearAllButton;
    private boolean mEnable = false;
    private boolean mIsSlitScreen = false;

    public ClearAllController(Context context, LauncherAppMonitor monitor) {
        super(context);
        monitor.registerCallback(mAppMonitorCallback);
    }

    private final LauncherAppMonitorCallback mAppMonitorCallback = new LauncherAppMonitorCallback() {

        @Override
        public void onLauncherCreated(Launcher launcher) {
            mDefaultClearAllButton = initClearAllButton(launcher);
        }

        @Override
        public void onRecentsActivityCreate(BaseDraggingActivity activity) {
            mOtherClearAllButton = initClearAllButton(activity);
        }

        @Override
        public void onQuickstepLauncherStart() {
            setClearAllButton(mDefaultClearAllButton);
            //Add for bug1701442:When configuration_CHANGED changes, the showOrHide(false) method is not executed
            //show when onStateSetEnd(OVERVIEW)
            showOrHide();
        }

        @Override
        public void onRecentsActivityStart() {
            setClearAllButton(mOtherClearAllButton);
            showOrHide(true);
        }

        @Override
        public void onStateSetStart(LauncherState toState) {
            if (!toState.overviewUi || toState.hasFlag(FLAG_NON_INTERACTIVE)) {
                showOrHide(false);
            }
        }

        @Override
        public void onStateSetEnd(LauncherState toState) {
            showOrHide(toState.overviewUi && !toState.hasFlag(FLAG_NON_INTERACTIVE) && !mIsSlitScreen);
            mIsSlitScreen = false;
        }

        @Override
        public void onTaskStackUpdated(boolean hasTask) {
            if (LogUtils.DEBUG_ALL) {
                LogUtils.d(TAG, "onTaskStackUpdated hastask: " + mHasTask + " --> " + hasTask);
            }
            mHasTask = hasTask;
            onOrientationStateChanged();
            if (isNeedRefresh()) {
                showOrHide();
            }
        }

        @Override
        public void onOrientationStateChanged() {
            if (mClearAllButton == null) {
                LogUtils.d(TAG, "mClearAllButton is null, return");
                return;
            }
            mClearAllButton.updateOrientationState();
        }
    };

    private ClearAllButton initClearAllButton(BaseDraggingActivity activity) {
        ClearAllButton clearAllButton = (ClearAllButton) LayoutInflater.from(activity)
                .inflate(R.layout.clearallbutton_ext, activity.getDragLayer(), false);
        RecentsView rv = activity.getOverviewPanel();
        clearAllButton.setRecentsView(rv);
        activity.getDragLayer().addView(clearAllButton);
        return clearAllButton;
    }

    public void setIsSlitScreen(boolean isSlitScreen) {
        mIsSlitScreen = isSlitScreen;
    }

    private void showOrHide(boolean enable) {
        if (LogUtils.DEBUG_ALL) {
            LogUtils.d(TAG, "showOrHide mEnable: " + mEnable + " --> " + enable);
        }
        mEnable = enable;
        if (isNeedRefresh()) {
            showOrHide();
        }
    }

    private void showOrHide() {
        if (LogUtils.DEBUG_ALL) {
            LogUtils.d(TAG, "showOrHide mEnable: " + mEnable);
        }
        // UNISOC: modify for bug 1345503:The animation starts before the view is initialized,don't start animation.
        if (mClearAllButton == null) {
            LogUtils.d(TAG, "mClearAllButton is null, return");
            return;
        }
        if (mAmin != null) {
            mAmin.cancel();
        }
        mAmin = new AnimatorSet();

        ObjectAnimator clearall = ObjectAnimator.ofFloat(mClearAllButton,
                ClearAllButton.CONTENT_ALPHA, mEnable && mHasTask ? 1f : 0f);
        // ObjectAnimator clearall = ObjectAnimator.ofFloat(mClearAllButton,
        //         ClearAllButton.CONTENT_ALPHA, false ? 1f : 0f);
        clearall.setDuration(ANIM_DURATION);
        clearall.setInterpolator(LINEAR);
        mAmin.play(clearall);
        mAmin.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAmin = null;
            }
        });
        mAmin.start();
    }

    private boolean isNeedRefresh() {
        if (mAmin != null) {
            return true;
        }

        // UNISOC: modify for bug 1345503:The animation starts before the view is initialized,don't start animation.
        if (mClearAllButton == null) {
            LogUtils.d(TAG, "mClearAllButton is null, return false");
            return false;
        }
        boolean needShow = mEnable && mHasTask;
        return (needShow && Float.compare(mClearAllButton.getContentAlpha(), 1f) != 0)
                || (!needShow && (Float.compare(mClearAllButton.getContentAlpha(), 0f) != 0));
    }

    public void setFullscreenProgress(float progress) {
        if (LogUtils.DEBUG_ALL) {
            LogUtils.d(TAG, "setProgress progress: " + progress);
        }
        // UNISOC: modify for bug 1371272:Open split screen on horizontal screen, memory information and clear all button are displayed on the desktop
        if (mClearAllButton != null && mEnable && mHasTask) {
            if (mAmin != null) {
                mAmin.cancel();
            }
            mClearAllButton.setContentAlpha(
                    mapToRange(progress, 0, 0.1f, 1f, 0f, LINEAR));
        }
    }

    public static float getClearAllButtonHeight(Resources res) {
        if (FeatureOption.SPRD_CLEAR_ALL_ON_BOTTOM_SUPPORT) {
            return res.getDimension(R.dimen.recents_clearall_height);
        }
        return 0f;
    }

    @VisibleForTesting
    public AnimatorSet getAmin() {
        return mAmin;
    }

    void setClearAllButton(ClearAllButton button) {
        mClearAllButton = button;
    }
}
package com.mtk.ext.meminfo;

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
import com.mtk.ext.LauncherAppMonitor;
import com.mtk.ext.LauncherAppMonitorCallback;
import com.mtk.ext.LogUtils;

public class MeminfoController extends BaseController {
    private static final String TAG = "Launcher MeminfoController";
    private static final long ANIM_DURATION = 120;

    private boolean mEnable = false;
    private boolean mIsSlitScreen = false;
    private AnimatorSet mAmin;
    private MeminfoView mMeminfoView;
    private MeminfoView mDefaultMeminfoView;
    private MeminfoView mOtherMeminfoView;

    public MeminfoController(Context context, LauncherAppMonitor monitor) {
        super(context);
        monitor.registerCallback(mAppMonitorCallback);
    }

    private final LauncherAppMonitorCallback mAppMonitorCallback = new LauncherAppMonitorCallback() {

        @Override
        public void onLauncherCreated(Launcher launcher) {
            // Launcher launcher = LauncherAppMonitor.getInstanceNoCreate().getLauncher();
            mDefaultMeminfoView = initMeminfoView(launcher);
        }

        @Override
        public void onRecentsActivityCreate(BaseDraggingActivity activity) {
            mOtherMeminfoView = initMeminfoView(activity);
        }

        @Override
        public void onQuickstepLauncherStart() {
            mMeminfoView = mDefaultMeminfoView;
            //show when onStateSetEnd(OVERVIEW)
            showOrHide(false);
        }

        @Override
        public void onRecentsActivityStart() {
            mMeminfoView = mOtherMeminfoView;
            showOrHide(true);
        }

        private MeminfoView initMeminfoView(BaseDraggingActivity activity) {
            MeminfoView meminfoView = (MeminfoView) LayoutInflater.from(activity)
                    .inflate(R.layout.memoryinfo_ext, activity.getDragLayer(), false);
            RecentsView rv = activity.getOverviewPanel();
            meminfoView.setRecentsView(rv);
            activity.getDragLayer().addView(meminfoView);
            return meminfoView;
        }

        @Override
        public void onTaskStackUpdated(boolean hasTask) {
            if (LogUtils.DEBUG_ALL) {
                LogUtils.d(TAG, "onTaskStackUpdated hastask: " + hasTask);
            }
            onOrientationStateChanged();
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
        public void onOrientationStateChanged() {
            if (mMeminfoView == null) {
                LogUtils.d(TAG, "mMeminfoView is null, return");
                return;
            }
            mMeminfoView.updateOrientationState();
        }
    };

    public void setIsSlitScreen(boolean isSlitScreen) {
        mIsSlitScreen = isSlitScreen;
    }

    private void showOrHide(boolean enable) {
        mEnable = enable;
        LogUtils.d(TAG, "showOrHide enable: " + enable);
        if (mMeminfoView == null) {
            LogUtils.d(TAG, "mMeminfoView is null, return");
            return;
        }
        if (mAmin != null) {
            mAmin.cancel();
        }
        mAmin = new AnimatorSet();
        ObjectAnimator meminfo = ObjectAnimator.ofFloat(mMeminfoView,
                MeminfoView.CONTENT_ALPHA, mEnable ? 1f : 0f);
        meminfo.setDuration(ANIM_DURATION);
        meminfo.setInterpolator(LINEAR);
        mAmin.play(meminfo);
        mAmin.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mAmin = null;
            }
        });
        mAmin.start();
    }

    public void setFullscreenProgress(float progress) {
        if (LogUtils.DEBUG_ALL) {
            LogUtils.d(TAG, "setProgress progress: " + progress);
        }
        if (mMeminfoView != null && mEnable) {
            if (mAmin != null) {
                mAmin.cancel();
            }
            mMeminfoView.setContentAlpha(
                    mapToRange(progress, 0, 0.1f, 1f, 0f, LINEAR));
        }
    }

    public static void allTasksCleared(Context context, boolean isRemoveView) {
        MeminfoHelper.getInstance(context).showReleaseMemoryToast(context, isRemoveView);
    }

    public static float getMeminfoViewHeight(Resources res) {
        return res.getDimension(R.dimen.recents_meminfo_height);
    }

    @VisibleForTesting
    public AnimatorSet getAmin() {
        return mAmin;
    }
}
package com.sprd.ext.lockicon;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TouchDelegate;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.Nullable;

import com.android.launcher3.BaseDraggingActivity;
import com.android.launcher3.R;
import com.android.launcher3.touch.PagedOrientationHandler;
import com.android.launcher3.util.TransformingTouchDelegate;
import com.android.systemui.shared.recents.model.Task;

import static android.view.Gravity.BOTTOM;
import static android.view.Gravity.END;
import static android.view.Gravity.START;
import static android.view.Gravity.TOP;
import static com.android.launcher3.Utilities.getDescendantCoordRelativeToAncestor;

public class FrameLayoutWithLockIcon extends FrameLayout {
    public static final boolean CLICKABLE = true;
    private String mStringKey;
    private boolean mLocked;
    private ImageView mLockIconView;
    private TransformingTouchDelegate mLockIconTouchDelegate;
    private final BaseDraggingActivity mDraggingActivity;

    public FrameLayoutWithLockIcon(Context context) {
        this(context, null);
    }

    public FrameLayoutWithLockIcon(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FrameLayoutWithLockIcon(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }
    public FrameLayoutWithLockIcon(
            Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mDraggingActivity = BaseDraggingActivity.fromContext(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLockIconView = (ImageView) LayoutInflater.from(getContext())
                .inflate(R.layout.lockicon_ext, this, false);
        mLockIconView.setVisibility(VISIBLE);
        addView(mLockIconView);
        if (CLICKABLE) {
            mLockIconView.setOnClickListener(v -> changeLockState(getContext()));
            mLockIconTouchDelegate = new TransformingTouchDelegate(mLockIconView);
        }
    }

    public boolean isLocked() {
        return mLocked;
    }

    protected void initLockedStatus(Task task) {
        if (mLockIconView != null) {
            mStringKey = TaskLockStatus.makeTaskStringKey(getContext(), task);
            mLocked = TaskLockStatus.isSavedLockedTask(getContext(), mStringKey);
            mLockIconView.setImageDrawable(getDrawable(getContext()));
        }
    }

    public void changeLockState(Context context) {
        if (mLockIconView != null && TaskLockStatus.setLockState(context, mStringKey, !isLocked())) {
            mLocked = !mLocked;
            mLockIconView.setImageDrawable(getDrawable(context));
        }
    }

    private Drawable getDrawable(Context context) {
        if (mLockIconView != null) {
            return isLocked() ? context.getDrawable(R.drawable.lock_icon_ext)
                    : context.getDrawable(R.drawable.unlock_icon_ext);
        }
        return null;
    }

    protected void setLockIconScale(float scale) {
        if (mLockIconView != null) {
            mLockIconView.setScaleX(scale);
            mLockIconView.setScaleY(scale);
        }
    }

    public void setOrientationState(PagedOrientationHandler orientationHandler,
            LayoutParams snapshotParams, boolean isRtl,
            int thumbnailPadding) {
        if (mLockIconView == null) {
            return;
        }

        LayoutParams iconParams = (LayoutParams) mLockIconView.getLayoutParams();
        switch (orientationHandler.getRotation()) {
            case Surface.ROTATION_90:
                iconParams.gravity = (isRtl ? START : END) | BOTTOM;
                iconParams.rightMargin = -thumbnailPadding;
                iconParams.leftMargin = 0;
                iconParams.topMargin = snapshotParams.topMargin;
                break;
            case Surface.ROTATION_180:
                iconParams.gravity = BOTTOM | START;
                iconParams.topMargin = -snapshotParams.topMargin + iconParams.height;
                iconParams.leftMargin = iconParams.bottomMargin = iconParams.rightMargin = 0;
                break;
            case Surface.ROTATION_270:
                iconParams.gravity = (isRtl ? END : START) | TOP;
                iconParams.leftMargin = -thumbnailPadding;
                iconParams.rightMargin = 0;
                iconParams.topMargin = snapshotParams.topMargin;
                break;
            case Surface.ROTATION_0:
            default:
                iconParams.gravity = Gravity.TOP | END;
                iconParams.topMargin = snapshotParams.topMargin - iconParams.height;
                iconParams.leftMargin = iconParams.bottomMargin = iconParams.rightMargin = 0;
                break;
        }

        mLockIconView.setLayoutParams(iconParams);
        mLockIconView.setRotation(orientationHandler.getDegreesRotated());
    }

    /* UNISOC: modify for Bug1363778
        ** Click the upper part of the task icon on the horizontal screen, the task menu is not opened.
        @{ */
    public TouchDelegate getLockIconTouchDelegate(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            computeAndSetIconTouchDelegate();
        }
        return mLockIconTouchDelegate;
    }

    private void computeAndSetIconTouchDelegate() {
        if (mLockIconTouchDelegate != null && mLockIconView != null) {
            final float[] iconCenterCoords = new float[2];
            float iconHalfSize = mLockIconView.getWidth() / 2f;
            iconCenterCoords[0] = iconCenterCoords[1] = iconHalfSize;
            getDescendantCoordRelativeToAncestor(mLockIconView, mDraggingActivity.getDragLayer(),
                    iconCenterCoords, false);
            mLockIconTouchDelegate.setBounds(
                    (int) (iconCenterCoords[0] - iconHalfSize),
                    (int) (iconCenterCoords[1] - iconHalfSize),
                    (int) (iconCenterCoords[0] + iconHalfSize),
                    (int) (iconCenterCoords[1] + iconHalfSize));
        }
    }
    /* @} */
}
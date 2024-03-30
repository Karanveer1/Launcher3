package com.mtk.ext.clearall;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.android.launcher3.Insettable;
import com.android.launcher3.R;
// import com.android.quickstep.util.SplitScreenBounds;
import com.android.quickstep.views.RecentsView;
import com.mtk.ext.LogUtils;
import com.mtk.ext.meminfo.MeminfoController;
// import com.mtk.ext.overviewadaptive.OverviewAdaptiveHelper;

import static android.view.Gravity.BOTTOM;
import static android.view.Gravity.CENTER_HORIZONTAL;
import static android.view.Gravity.CENTER_VERTICAL;
import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;

public class ClearAllButton extends Button implements Insettable {//, SplitScreenBounds.OnChangeListener
    private static final String TAG = "ClearAllButton";
    private float mContentAlpha = 0f;
    private final Context mContext;
    private RecentsView mRecentsView;
    private Rect mInsets = new Rect();

    public static final FloatProperty<ClearAllButton> CONTENT_ALPHA =
            new FloatProperty<ClearAllButton>("ClearAllButton") {
                @Override
                public void setValue(ClearAllButton view, float v) {
                    view.setContentAlpha(v);
                }

                @Override
                public Float get(ClearAllButton view) {
                    return view.getContentAlpha();
                }
            };

    public ClearAllButton(Context context) {
        this(context, null, 0);
    }

    public ClearAllButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClearAllButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public void setContentAlpha(float alpha) {
        if (LogUtils.DEBUG_ALL) {
            LogUtils.d(TAG, "getContentAlpha mContentAlpha: " + mContentAlpha + " --> " + alpha);
        }
        mContentAlpha = alpha;
        setClickable(Float.compare(alpha, 1f) == 0);
        setAlpha(alpha);
        if (getVisibility() != VISIBLE && Float.compare(alpha, 0f) > 0) {
            setVisibility(VISIBLE);
        } else if (getVisibility() != GONE && Float.compare(alpha, 0f) == 0) {
            setVisibility(GONE);
        }
    }

    public float getContentAlpha() {
        return mContentAlpha;
    }

    @Override
    public void setInsets(Rect insets) {
        if (LogUtils.DEBUG_ALL) {
            LogUtils.d(TAG, "setInsets insets: " + insets);
        }
        mInsets.set(insets);
        updateOrientationState();
    }

    public void updateOrientationState() {
        if (mRecentsView == null) {
            return;
        }
        int rotation = Surface.ROTATION_0;
        if (!mRecentsView.getPagedViewOrientedState().canRecentsActivityRotate()) {
            rotation = mRecentsView.getPagedOrientationHandler().getRotation();
        }
        if (getMeasuredWidth() == 0) {
            measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        }
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        int bottompadding = getResources().getDimensionPixelSize(R.dimen.recents_bottom_padding);
        // UNISOC add for bug1650446:Overlapping adaptation of the OverView interface on the Go version.
        // int overviewAdaptiveHeight = OverviewAdaptiveHelper.getOverviewAdaptiveHeight(mContext) * 2;
        float height = getResources().getDimension(R.dimen.recents_clearall_height);
        float displacement = getResources().getDimension(R.dimen.recents_tasklock_enddisplacement);
        float width = getMeasuredWidth();
        //UNISOC add for Bug1645875:In landscape mode, the clear-all button does not display completely
        float meminfoHeight = MeminfoController.getMeminfoViewHeight(getResources());
        int clearButtonBottomMargin = mRecentsView.getPaddingBottom() + mInsets.bottom /*+ overviewAdaptiveHeight*/
                - (int) (displacement + meminfoHeight + height);
        switch (rotation) {
            case Surface.ROTATION_90:
                params.gravity = CENTER_VERTICAL | LEFT;
                params.leftMargin = mInsets.left;
                params.rightMargin = params.bottomMargin = params.topMargin = 0;
                setTranslationX(height / 2 - width / 2 + bottompadding);
                break;
            case Surface.ROTATION_270:
                params.gravity = CENTER_VERTICAL | RIGHT;
                params.rightMargin = mInsets.right;
                params.leftMargin = params.bottomMargin = params.topMargin = 0;
                setTranslationX(width / 2 - height / 2 - bottompadding);
                break;
            case Surface.ROTATION_0:
            case Surface.ROTATION_180:
            default:
                params.gravity = BOTTOM | CENTER_HORIZONTAL;
                params.topMargin = mInsets.top;
                params.leftMargin = 0;
                params.rightMargin = 0;
                params.bottomMargin = clearButtonBottomMargin > 0 ? clearButtonBottomMargin : 0;
                setTranslationX(0);
                break;
        }
        if (LogUtils.DEBUG_ALL) {
            LogUtils.d(TAG, "updateOrientationState params: " + rotation
                    + "--" + params.leftMargin + "-" + params.topMargin
                    + "-" + params.rightMargin + "-" + params.bottomMargin);
        }
        setRotation(mRecentsView.getPagedOrientationHandler().getDegreesRotated());
        setLayoutParams(params);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            updateOrientationState();
        }
    }

    public void setRecentsView(RecentsView recentsView) {
        LogUtils.d(TAG, "setRecentsView:");
        mRecentsView = recentsView;
        setOnClickListener(mRecentsView::clearAllTasks);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // SplitScreenBounds.INSTANCE.addOnChangeListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // SplitScreenBounds.INSTANCE.removeOnChangeListener(this);
    }

    /**
    * UNISOC add for Bug1745405
    * update the layout parameters of clearAllButton when the Launcher of split screen size changes
    */
    // @Override
    // public void onSecondaryWindowBoundsChanged() {
    //     updateOrientationState();
    // }
}
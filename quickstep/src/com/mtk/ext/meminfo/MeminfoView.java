package com.mtk.ext.meminfo;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatTextView;

import com.android.launcher3.Insettable;
import com.android.launcher3.R;
// import com.android.quickstep.util.SplitScreenBounds;
import com.android.quickstep.views.RecentsView;
import com.mtk.ext.LogUtils;
import com.mtk.ext.meminfo.MeminfoHelper;
// import com.mtk.ext.overviewadaptive.OverviewAdaptiveHelper;
import static android.view.Gravity.BOTTOM;
import static android.view.Gravity.CENTER_HORIZONTAL;
import static android.view.Gravity.CENTER_VERTICAL;
import static android.view.Gravity.LEFT;
import static android.view.Gravity.RIGHT;
import static android.view.Surface.ROTATION_0;
import static android.view.Surface.ROTATION_90;
import static android.view.Surface.ROTATION_180;
import static android.view.Surface.ROTATION_270;

public class MeminfoView extends AppCompatTextView implements Insettable {
    private static final String TAG = "Launcher MeminfoView";
    private static final String SEPARATOR = " | ";
    private float mContentAlpha = 0f;
    private final Context mContext;
    private RecentsView mRecentsView;
    private Rect mInsets = new Rect();

    public static final FloatProperty<MeminfoView> CONTENT_ALPHA =
            new FloatProperty<MeminfoView>("MeminfoView") {
                @Override
                public void setValue(MeminfoView view, float v) {
                    view.setContentAlpha(v);
                }

                @Override
                public Float get(MeminfoView view) {
                    return view.getContentAlpha();
                }
            };

    public MeminfoView(Context context) {
        this(context, null, 0);
    }

    public MeminfoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MeminfoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        MeminfoHelper.getInstance(context).updateTotalMemory();
    }

    public void setContentAlpha(float alpha) {
        if (LogUtils.DEBUG_ALL) {
            LogUtils.d(TAG, "getContentAlpha mContentAlpha: " + mContentAlpha + " --> " + alpha);
        }
        mContentAlpha = alpha;
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
        int rotation = ROTATION_0;
        if (!mRecentsView.getPagedViewOrientedState().canRecentsActivityRotate()
                && mRecentsView.getTaskViewCount() != 0) {
            rotation = mRecentsView.getPagedOrientationHandler().getRotation();
        }
        if (getMeasuredWidth() == 0) {
            measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        }
        float degreesRotated = mRecentsView.getPagedOrientationHandler().getDegreesRotated();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
        // UNISOC add for bug1650446:Overlapping adaptation of the OverView interface on the Go version.
        // int overviewAdaptiveHeight = OverviewAdaptiveHelper.getOverviewAdaptiveHeight(mContext) * 2;
        float height = getResources().getDimension(R.dimen.recents_meminfo_height);
        float displacement = getResources().getDimension(R.dimen.recents_tasklock_enddisplacement);
        float width = getMeasuredWidth();
        //UNISOC add for Bug1645875:In landscape mode, the clear-all button does not display completely
        // int overviewTaskMargin = getResources().getDimensionPixelSize(R.dimen.overview_task_margin);
        int meminfoHeight = (int) MeminfoController.getMeminfoViewHeight(getResources());
        int meminfoBottomMargin = mRecentsView.getPaddingBottom() + mInsets.bottom /*+ overviewAdaptiveHeight*/
                        - (int) (displacement + height);
        switch (rotation) {
            case ROTATION_90:
                params.gravity = CENTER_VERTICAL | LEFT;
                params.leftMargin = mInsets.left;
                params.rightMargin = params.bottomMargin = params.topMargin = 0;
                setTranslationX(mRecentsView.getPaddingLeft() - displacement
                        - (width / 2 + height / 2));
                break;
            case ROTATION_270:
                params.gravity = CENTER_VERTICAL | RIGHT;
                params.rightMargin = mInsets.right;
                params.leftMargin = params.bottomMargin = params.topMargin = 0;
                setTranslationX(width / 2 + height / 2
                        - (mRecentsView.getPaddingRight() - displacement));
                break;
            case ROTATION_0:
            case ROTATION_180:
            default:
                params.gravity = BOTTOM | CENTER_HORIZONTAL;
                params.topMargin = mInsets.top;
                params.leftMargin = 0;
                params.rightMargin = 0;
                params.bottomMargin = (meminfoBottomMargin - meminfoHeight) > 0
                        ? meminfoBottomMargin
                        : meminfoBottomMargin;// + overviewTaskMargin;
                setTranslationX(0);
                break;
        }
        if (LogUtils.DEBUG_ALL) {
            LogUtils.d(TAG, "updateOrientationState params rotation: " + rotation
                    + "--" + params.leftMargin + "-" + params.topMargin
                    + "-" + params.rightMargin + "-" + params.bottomMargin);
        }
        setRotation(degreesRotated);
        setLayoutParams(params);
    }

    public void setMemoryinfoText(String availStr, String totalStr) {
        if (TextUtils.isEmpty(availStr) || TextUtils.isEmpty(totalStr)) {
            return;
        }
        String text = getContext().getString(R.string.recents_memory_avail, availStr) + SEPARATOR + totalStr;
        setText(text);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        if (visibility == VISIBLE) {
            MeminfoHelper meminfoHelper = MeminfoHelper.getInstance(mContext);
            meminfoHelper.updateAvailMemory(getContext());
            setMemoryinfoText(meminfoHelper.getAvailMemString(getContext()),
                    meminfoHelper.getTotalMem(getContext()));
            updateOrientationState();
        }
        super.onVisibilityChanged(changedView, visibility);
    }

    public void setRecentsView(RecentsView recentsView) {
        LogUtils.d(TAG, "setRecentsView:");
        mRecentsView = recentsView;
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
    * update the layout parameters of memInfoView when the Launcher of split screen size changes
    */
    // @Override
    // public void onSecondaryWindowBoundsChanged() {
    //     updateOrientationState();
    // }
}

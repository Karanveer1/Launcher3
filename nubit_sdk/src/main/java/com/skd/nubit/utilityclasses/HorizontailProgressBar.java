package com.skd.nubit.utilityclasses;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class HorizontailProgressBar extends View {


    public interface OnProgressBarListener {
        void onProgressChange(int current, int max);
    }

    OnProgressBarListener progressBarListener;

    private int mMaxProgress = 100;
    Context context;
    /**
     * Current progress, can not exceed the max progress.
     */
    private int mCurrentProgress = 0;

    /**
     * The progress area bar color.
     */
    private int mReachedBarColor = Color.parseColor("#000000");
    private float mReachedBarHeight = 6;

    /**
     * The Paint of the reached area.
     */
    private Paint mReachedBarPaint;
    private RectF mReachedRectF = new RectF(0, 0, 0, 0);


    /**
     * Listener
     */
//    private OnProgressBarListener mListener;

    public enum ProgressTextVisibility {
        Visible, Invisible
    }

    public HorizontailProgressBar(Context context) {
        this(context, null);
    }

    public HorizontailProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontailProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        initializePainters();
        setProgress(2);
        setMax(100);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec, true), measure(heightMeasureSpec, false));
    }

    private int measure(int measureSpec, boolean isWidth) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size);
                } else {
                    result = Math.min(result, size);
                }
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        calculateDrawRectF();
        canvas.drawRect(mReachedRectF, mReachedBarPaint);
    }

    private void initializePainters() {
        mReachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mReachedBarPaint.setColor(Color.parseColor("#000000"));

    }


    private void calculateDrawRectF() {

        if (getProgress() == 0) {
        } else {
            mReachedRectF.left = 0;
            mReachedRectF.top = 0;
            mReachedRectF.right = getProgress() * MyUtility.getScreenWidth(context) / 100;
            mReachedRectF.bottom = mReachedBarHeight;
        }
    }

    public int getReachedBarColor() {
        return mReachedBarColor;
    }

    public int getProgress() {
        return mCurrentProgress;
    }

    public int getMax() {
        return mMaxProgress;
    }

    public float getReachedBarHeight() {
        return mReachedBarHeight;
    }


    public void setMax(int maxProgress) {
        if (maxProgress > 0) {
            this.mMaxProgress = maxProgress;
            invalidate();
        }
    }


    public void setProgress(int progress) {
        if (progress <= getMax() && progress >= 0) {
            this.mCurrentProgress = progress;
            invalidate();
            if (progressBarListener != null) {
                progressBarListener.onProgressChange(mCurrentProgress, getMax());
            }
//            postInvalidate();
        }
    }


    public void setOnProgressBarListener(OnProgressBarListener listener) {
        progressBarListener = listener;
    }
}


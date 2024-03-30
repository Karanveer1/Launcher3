package com.skd.nubit.utilityclasses;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.skd.nubit.R;
import com.skd.nubit.models.Services_pojo;

import java.util.ArrayList;
import java.util.List;

public class DropDownViewControler extends RelativeLayout {


    boolean isViewInTop = false;
    List<Services_pojo> actualList;
    OnDropDownViewClick onDropDownViewClick;

    Context ctx;

    public interface OnDropDownViewClick {

        void onClick(List<Services_pojo> data);

    }

    public DropDownViewControler(Context context) {
        super(context);
//        init(context);
        ctx=context;
    }

    public DropDownViewControler(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx=context;
//        init(context);
    }

    public DropDownViewControler(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ctx=context;
//        init(context);
    }

    public void setDropDownClick(OnDropDownViewClick onDropDownViewClick) {
        this.onDropDownViewClick = onDropDownViewClick;
    }


    /**
     * remove objects from memory
     */
    public void flushViews() {
        setOnClickListener(null);
        onDropDownViewClick = null;
        actualList = null;
    }



    public void bindViews() {

        removeAllViews();
        View view = LayoutInflater.from(ctx).inflate(R.layout.layout_drowdown, null);

        final View viewLeft = view.findViewById(R.id.view_left);
        final View viewRight = view.findViewById(R.id.view_right);
        final View viewTop = view.findViewById(R.id.view_top);
        addView(view);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                final AnimatorSet moveSingleBarAnimSet = new AnimatorSet();

                ObjectAnimator leftObjectAnimator = ObjectAnimator.ofFloat(viewLeft, "translationX", 0, -MyUtility.dpToPx(10), 0);
                leftObjectAnimator.setDuration(400);

                ObjectAnimator rightobjectAnimator = ObjectAnimator.ofFloat(viewRight, "translationX", 0, MyUtility.dpToPx(10), 0);
                rightobjectAnimator.setDuration(400);

                ObjectAnimator topObjectAnimator = null;
                if (isViewInTop) {
                    isViewInTop = false;
                    topObjectAnimator = ObjectAnimator.ofFloat(viewTop, "translationY", 0, MyUtility.dpToPx(20));
                    topObjectAnimator.setDuration(200);
                    onDropDownViewClick.onClick(actualList.subList(0, 8));
                } else {
                    onDropDownViewClick.onClick(actualList);
                    isViewInTop = true;
                    topObjectAnimator = ObjectAnimator.ofFloat(viewTop, "translationY", MyUtility.dpToPx(20), 0);
                    topObjectAnimator.setDuration(200);

                }
                moveSingleBarAnimSet.play(leftObjectAnimator).with(rightobjectAnimator).with(topObjectAnimator);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        moveSingleBarAnimSet.start();
                    }
                }, 400);
            }
        });
    }


    public List<Services_pojo> setUpList(List<Services_pojo> dataList) {
        ObjectAnimator.ofFloat(getChildAt(0).findViewById(R.id.view_top), "translationY", 0, MyUtility.dpToPx(20)).start();
        isViewInTop = false;
        this.actualList = null;
        this.actualList = new ArrayList<>();
        actualList.addAll(dataList);
        return actualList.subList(0, 8);
    }



}

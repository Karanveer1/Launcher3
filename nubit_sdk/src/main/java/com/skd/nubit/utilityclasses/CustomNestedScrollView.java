package com.skd.nubit.utilityclasses;

import android.content.Context;
import androidx.core.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.skd.nubit.R;
/*import com.rkt.lava.mycallbacks.IsScrollingCallBack;*/
import com.skd.nubit.mycallbacks.IsScrollingCallBack;
import com.skd.nubit.mycallbacks.RktPlayerCallBack;

public class CustomNestedScrollView extends NestedScrollView {

    View controlview;
    boolean animationrunning = false;
    private final int SCROLL_TO_TOP_ARC = 900;

    private int scrollDifference = 0;
   /* private static final String TAG = CustomNestedScrollView.class.getName();*/

    public CustomNestedScrollView(Context context) {
        super(context);
        init();
    }

    public CustomNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void setUpScollview(View view) {
        this.controlview = view;
        controlview.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                final Animation myAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim
                        .bubble);
                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                myAnim.setInterpolator(interpolator);
                ((ViewGroup) controlview.getParent()).startAnimation(myAnim);
                CustomNestedScrollView.this.smoothScrollTo(0, 0);
                CustomNestedScrollView.this.scrollTo(0, 0);


                myAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        controlview.setVisibility(GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
    }


    private void init() {

        setOnScrollChangeListener(new OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int
                    oldScrollX, int oldScrollY) {
                if (scrollY > SCROLL_TO_TOP_ARC && controlview.getVisibility() == View.GONE &&
                        !animationrunning) {
                    controlview.setVisibility(VISIBLE);
                    RktPlayerCallBack.getInstance().notifyByPlayer(true, "scroll_up");



                } else if (scrollY < SCROLL_TO_TOP_ARC && controlview.getVisibility() == View
                        .VISIBLE) {
                    controlview.setVisibility(GONE);

                    RktPlayerCallBack.getInstance().notifyByPlayer(true, "scroll_down");

                }

                IsScrollingCallBack.getInstance().notifyonScroll(true,"Scroller",scrollY);
               /* else
                {
                    Log.d("scrolling",String.valueOf(scrollY));
                }*/
               /* MyApplication.app_editor.putString("drawer_status", "closed").apply();*/

               /* IsScrollingCallBack.getInstance().notifyonScroll(true,"Scroller",scrollY);*/
            }


        });

    }

/*
    public void revalanimation() {
        int centerX = (controlview.getLeft() + controlview.getRight()) / 2;
        int centerY = (controlview.getTop() + controlview.getBottom()) / 2;

        int startRadius = 0;
        int endRadius = Math.max(controlview.getWidth(), controlview.getHeight());
        Animator anim =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            animationrunning = true;
            anim = ViewAnimationUtils.createCircularReveal(controlview, centerX, centerY,
            startRadius, endRadius);
        } else {
            controlview.setVisibility(VISIBLE);
            animationrunning = false;
            return;
        }

// make the view visible and start the animation
        controlview.setVisibility(View.VISIBLE);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animationrunning = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.setDuration(800);
        anim.start();
    }*/


    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {

        return super.onStartNestedScroll(child, target, nestedScrollAxes);
    }

 /*   public void revelanimaitonTovisibltiyGone() {
        int centerX = (controlview.getLeft() + controlview.getRight()) / 2;
        int centerY = (controlview.getTop() + controlview.getBottom()) / 2;

        int endRadius = Math.max(controlview.getWidth(), controlview.getHeight());
        Animator anim =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            animationrunning = true;
            anim = ViewAnimationUtils.createCircularReveal(controlview, centerX, centerY,
            endRadius, 0);
        } else {
            animationrunning = false;
            controlview.setVisibility(View.GONE);
            return;
        }

// make the view visible and start the animation
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                controlview.setVisibility(View.GONE);
                animationrunning = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.setDuration(200);
        anim.start();
    }*/


    public int yesDifferenceFound() {

        return scrollDifference;
    }

}

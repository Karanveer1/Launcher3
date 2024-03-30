package com.skd.nubit.utilityclasses

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.widget.NestedScrollView
import androidx.core.widget.NestedScrollView.OnScrollChangeListener
import com.skd.nubit.R
import com.skd.nubit.mycallbacks.IsScrollingCallBack
import com.skd.nubit.mycallbacks.RktPlayerCallBack


class CustomNestedScrollView : NestedScrollView {
    var controlview: View? = null
    var animationrunning = false
    private val SCROLL_TO_TOP_ARC = 900
    private val scrollDifference = 0

    /* private static final String TAG = CustomNestedScrollView.class.getName();*/
    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    ) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!, attrs, defStyleAttr
    ) {
        init()
    }

    fun setUpScollview(view: View?) {
        controlview = view
        controlview!!.setOnClickListener { v ->
            val myAnim =
                AnimationUtils.loadAnimation(v.context, R.anim.bubble)
            val interpolator = MyBounceInterpolator(0.2, 20.0)
            myAnim.interpolator = interpolator
            (controlview!!.parent as ViewGroup).startAnimation(myAnim)
            this@CustomNestedScrollView.smoothScrollTo(0, 0)
            scrollTo(0, 0)
            myAnim.setAnimationListener(object :
                Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    controlview!!.visibility = View.GONE
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        }
    }

    private fun init() {
        setOnScrollChangeListener(OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > SCROLL_TO_TOP_ARC && controlview!!.visibility == View.GONE &&
                !animationrunning
            ) {
                controlview!!.visibility = View.VISIBLE
                RktPlayerCallBack.getInstance().notifyByPlayer(true, "scroll_up")
            } else if (scrollY < SCROLL_TO_TOP_ARC && controlview!!.visibility == View.VISIBLE) {
                controlview!!.visibility = View.GONE
                RktPlayerCallBack.getInstance().notifyByPlayer(true, "scroll_down")
            }
            IsScrollingCallBack.getInstance().notifyonScroll(true, "Scroller", scrollY)
            /* else
                    {
                        Log.d("scrolling",String.valueOf(scrollY));
                    }*/
            /* MyApplication.app_editor.putString("drawer_status", "closed").apply();*/

            /* IsScrollingCallBack.getInstance().notifyonScroll(true,"Scroller",scrollY);*/
        })
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
    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int): Boolean {
        return super.onStartNestedScroll(child, target, nestedScrollAxes)
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
    fun yesDifferenceFound(): Int {
        return scrollDifference
    }
}

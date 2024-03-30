package com.skd.nubit.utilityclasses;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class InterceptTouchListener implements View.OnTouchListener {
    private boolean enableAutoScroll;
    private GestureDetector detector;
    private OnItemClickListener listener;
    private float touchX;
    private float touchY;
    private int touchSlop = 5;
    private boolean isInSwipeMode;
    private boolean shouldSwipe;

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    //    public InterceptTouchListener() {
//        listener = null;
//        enableAutoScroll = false;
//    }
//
//    public InterceptTouchListener(boolean enableAutoScroll) {
//        listener = null;
//        this.enableAutoScroll = enableAutoScroll;
//    }
//
    public InterceptTouchListener(OnItemClickListener l) {
        listener = l;
        enableAutoScroll = false;
    }
//
//    public InterceptTouchListener(OnItemClickListener l, boolean enableAutoScroll) {
//        listener = l;
//        this.enableAutoScroll = enableAutoScroll;
//    }
//
//    public boolean isEnableAutoScroll() {
//        return enableAutoScroll;
//    }
//
//    public void setEnableAutoScroll(boolean enableAutoScroll) {
//        this.enableAutoScroll = enableAutoScroll;
//    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//
//        if (detector == null)
//            detector = new GestureDetector(v.getContext(), new DetectorListener(v));
//        detector.onTouchEvent(event);
//        int action = event.getActionMasked();
//        Log.e("motion event", " of " + v.getClass().getSimpleName() + " is " + (action == MotionEvent.ACTION_DOWN ? "down" : action == MotionEvent.ACTION_MOVE ? "move" : action == MotionEvent.ACTION_UP ? "up" : action == MotionEvent.ACTION_CANCEL ? "cancel" : action == MotionEvent.ACTION_OUTSIDE ? "outside" : action));
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                isInSwipeMode = false;
//                shouldSwipe = false;
//                touchX = event.getX();
//                touchY = event.getY();
//                if (v instanceof AutoScrollViewPager)
//                    ((AutoScrollViewPager) v).stopAutoScroll();
//                return true;
//            case MotionEvent.ACTION_UP:
//                if (v instanceof AutoScrollViewPager && enableAutoScroll)
////                    ((AutoScrollViewPager) v).startAutoScroll(Constants.PAGER_SCROLL_DELAY);
//                break;
//            case MotionEvent.ACTION_CANCEL:
////                if (v instanceof RecyclerView)
////                    Robi.getInstance().enableScrolling();
//                break;
//        }
//        return false;
//    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (detector == null)
            detector = new GestureDetector(v.getContext(), new DetectorListener(v));
        detector.onTouchEvent(event);
        int action = event.getActionMasked();
        Log.e("motion event", "is " + (action == MotionEvent.ACTION_DOWN ? "down" : action == MotionEvent.ACTION_MOVE ? "move" : action == MotionEvent.ACTION_UP ? "up" : action == MotionEvent.ACTION_CANCEL ? "cancel" : action == MotionEvent.ACTION_OUTSIDE ? "outside" : action));
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isInSwipeMode = false;
                shouldSwipe = false;
                touchX = event.getX();
                touchY = event.getY();
//                if (v instanceof AutoScrollViewPager && enableAutoScroll)
//                    ((AutoScrollViewPager) v).stopAutoScroll();
                if (v instanceof AutoScrollViewPager)
                    ((AutoScrollViewPager) v).requestDisallowInterceptTouchEvent(true);
                else
                if (v instanceof RecyclerView)
                    ((RecyclerView) v).requestDisallowInterceptTouchEvent(true);
//                v.getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) (event.getX() - touchX);
                int deltaY = (int) (event.getY() - touchY);
                Log.e("motion delta", "is: "+deltaX+" "+deltaY);
//                Robi.getInstance().disableScrolling();
                if (!isInSwipeMode && (Math.abs(deltaX) > touchSlop || Math.abs(deltaY) > touchSlop)) {
                    Log.e("motion inside", "if: ");
                    isInSwipeMode = true;
                    if (Math.abs(deltaX) > touchSlop) {
                        shouldSwipe = true;
                        if (v instanceof AutoScrollViewPager && enableAutoScroll)
                            ((AutoScrollViewPager) v).stopAutoScroll();
                    } else {
                        shouldSwipe = false;

                    }
                }
                if (isInSwipeMode) {
                    if (v instanceof AutoScrollViewPager)
                        ((AutoScrollViewPager) v).requestDisallowInterceptTouchEvent(shouldSwipe);
                    else
                    if (v instanceof RecyclerView)
                        ((RecyclerView) v).requestDisallowInterceptTouchEvent(shouldSwipe);

//                    v.getParent().requestDisallowInterceptTouchEvent(shouldSwipe);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                Log.e("motion swipe", "is: "+isInSwipeMode+" "+shouldSwipe);
                isInSwipeMode = false;
                shouldSwipe = false;
//                Robi.getInstance().enableScrolling();
//                if (v instanceof AutoScrollViewPager && enableAutoScroll)
//                    ((AutoScrollViewPager) v).startAutoScroll(Constants.PAGER_SCROLL_DELAY);
                if (v instanceof AutoScrollViewPager)
                    ((AutoScrollViewPager) v).requestDisallowInterceptTouchEvent(false);
                else if (v instanceof RecyclerView)
                    ((RecyclerView) v).requestDisallowInterceptTouchEvent(false);
//                v.getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return false;
    }

    private class DetectorListener extends GestureDetector.SimpleOnGestureListener {
        private View v;

        public DetectorListener(View v) {
            this.v = v;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (listener == null)
                return false;
            if (v instanceof AutoScrollViewPager) {
                int pos = ((AutoScrollViewPager) v).getCurrentItem();
                if (pos != -1)
                    listener.onItemClick(v, pos);
            } else if (v instanceof RecyclerView) {
                int pos = ((RecyclerView) v).getChildAdapterPosition(((RecyclerView) v).findChildViewUnder(e.getX(), e.getY()));
                if (pos != -1)
                    listener.onItemClick(v, pos);
            }
            return true;
        }
    }
}

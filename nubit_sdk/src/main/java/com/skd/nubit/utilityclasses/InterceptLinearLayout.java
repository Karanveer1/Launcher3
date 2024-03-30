package com.skd.nubit.utilityclasses;

import android.content.Context;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class InterceptLinearLayout extends FrameLayout {

    public InterceptLinearLayout(Context context) {
        super(context);
    }

    public InterceptLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InterceptLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public InterceptLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                Robi.getInstance().disableScrolling();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Robi.getInstance().disableScrolling();
//                break;
//            case MotionEvent.ACTION_CANCEL:
////                Robi.getInstance().enableScrolling();
//                break;
//            case MotionEvent.ACTION_UP:
//                Robi.getInstance().enableScrolling();
//                break;
//        }
//
//        return true;
//    }

    float preY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getActionMasked();
        Log.e("motion event", "intercept of intercept is " + (action == MotionEvent.ACTION_DOWN ? "down" : action == MotionEvent.ACTION_MOVE ? "move" : action == MotionEvent.ACTION_UP ? "up" : action == MotionEvent.ACTION_CANCEL ? "cancel" : action == MotionEvent.ACTION_OUTSIDE ? "outside" : action));
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                preY = ev.getY();
                MyApplication.app_editor.putString("drawer_status", "open").apply();
                break;
            case MotionEvent.ACTION_MOVE:
//                Robi.getInstance().disableScrolling();

//                if(preY!=ev.getY()){
//                    Robi.getInstance().enableScrolling();
//                }


                break;
            case MotionEvent.ACTION_CANCEL:
//                Robi.getInstance().enableScrolling();
                break;
            case MotionEvent.ACTION_UP:
                MyApplication.app_editor.putString("drawer_status", "closed").apply();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}

package com.android.launcher3;

import static com.android.launcher3.LauncherState.NORMAL;
import android.os.Handler;
import android.content.Context;
import android.view.ViewConfiguration;
import android.view.View;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.database.ContentObserver;
import android.os.Handler;
import android.view.MotionEvent;
import android.database.ContentObserver;
import android.os.PowerManager;

//created for mmx ISU-2021-0000160
//double tap to lock phone
public class DoubleTapToLockHelper {
    private long mLastClickTime = 0;
    float mLastClickPositionX = 0, mLastClickPositionY = 0;
    private int mDoubleTapTimeout;
    private int mDoubleTapSlop = 50 ;
    private final String DOUBLETAP_SLEEP_KEY = "doubletap_sleep";// option
    private final String LAUNCHER_DOUBLETAP_KEY = "launcher_doubletap";
    private int mLuncherDoubleTapNum = 0;
    private View mParent;
    private Context mContext;
    private PowerManager mPowerManager;
    private boolean mIsDoubleTapSleep = false;

    private final ContentObserver mDoubleTapSleepSettingsObserver = new ContentObserver(
            new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            mIsDoubleTapSleep = Settings.Global.getInt(mContext.getContentResolver(),DOUBLETAP_SLEEP_KEY, 0) != 0;
        }
    };

    private DoubleTapToLockHelper(View parent) {
        mContext = parent.getContext();
        mParent = parent;
        mIsDoubleTapSleep = Settings.Global.getInt(mContext.getContentResolver(),DOUBLETAP_SLEEP_KEY, 0) != 0;
        mLuncherDoubleTapNum = Settings.Global.getInt(mContext.getContentResolver(),LAUNCHER_DOUBLETAP_KEY, 0);
        mPowerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
    }

    public static DoubleTapToLockHelper getInstance(View parent) {
        return new DoubleTapToLockHelper(parent);
    }

    Runnable mPowerDownRunnable = new Runnable() {
        @Override
        public void run() {
            if(mParent.hasWindowFocus()) {
                android.util.Log.d("mmx","mPowerDownRunnable " + mLuncherDoubleTapNum);
                mLuncherDoubleTapNum++;
                Settings.Global.putInt(mContext.getContentResolver(),LAUNCHER_DOUBLETAP_KEY, mLuncherDoubleTapNum);
            }
        }
    };

    protected boolean onFinishInflate() {
        final ViewConfiguration configuration = ViewConfiguration.get(mContext);
        mDoubleTapTimeout = configuration.getDoubleTapTimeout();
        mContext.getContentResolver().registerContentObserver(
                Settings.Global.getUriFor(DOUBLETAP_SLEEP_KEY), true,
                mDoubleTapSleepSettingsObserver);
        return true;
    }

    public void onDetachedFromWindow() {
        try{
            mContext.getContentResolver().unregisterContentObserver(mDoubleTapSleepSettingsObserver);
            mContext = null;
            mParent = null;
        }catch (Exception e){
            android.util.Log.d("DoubleTapToLockHelper","onDetachedFromWindow "+e.toString());
        }
    }

    public void onInterceptTouchEvent(MotionEvent ev) {
        if(mIsDoubleTapSleep){
            switch (ev.getAction()){
                case MotionEvent.ACTION_DOWN:
                    Launcher mLauncher = Launcher.getLauncher(mContext);
                    if(mLauncher!=null && mLauncher.isInState(NORMAL)){
                        long currenClickTime = System.currentTimeMillis();
                        float currentClickPostionX = ev.getRawX();
                        float currentClickPostionY = ev.getRawY();
                        if(currenClickTime - mLastClickTime < mDoubleTapTimeout && currenClickTime - mLastClickTime > 50
                                && (Math.abs(currentClickPostionX - mLastClickPositionX) < mDoubleTapSlop && Math.abs(currentClickPostionY - mLastClickPositionY) < mDoubleTapSlop)){
                            android.util.Log.d("point","shut down");
                            mParent.postDelayed(mPowerDownRunnable,200);
                        }
                        mLastClickTime = currenClickTime;
                        mLastClickPositionX = currentClickPostionX;
                        mLastClickPositionY = currentClickPostionY;
                    }
                    break;
            }
        }
    }
}

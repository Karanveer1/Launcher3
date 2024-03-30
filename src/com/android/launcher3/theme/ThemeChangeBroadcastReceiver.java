package com.android.launcher3.theme;

import static android.content.Context.RECEIVER_EXPORTED;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.util.Log;

public class ThemeChangeBroadcastReceiver extends BroadcastReceiver {
    private static final String ACTION_THEME_CHANGED = "android.intent.action.THEME_CHANGED";
    private String TAG = "ThemeManager";
    private ThemeChangeListener mThemeChangeListener;

    public interface ThemeChangeListener {
        void onThemeChanged(String str);
    }

    public void register(Context context, ThemeChangeListener themeChangeListener) {
        this.mThemeChangeListener = themeChangeListener;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_THEME_CHANGED);
        context.registerReceiver(this, intentFilter, RECEIVER_EXPORTED);
    }

    public void unregister(Context context) {
        context.unregisterReceiver(this);
    }

    public void onReceive(Context context, Intent intent) {
        Log.d(this.TAG, "onReceive: " + intent.getAction());
        if (this.mThemeChangeListener != null) {
            try {
                this.mThemeChangeListener.onThemeChanged(Settings.System.getString(context.getContentResolver(), "theme_path"));
            } catch (Exception e) {
                Log.w(this.TAG, "theme receiver error: ", e);
            }
        }
    }
}
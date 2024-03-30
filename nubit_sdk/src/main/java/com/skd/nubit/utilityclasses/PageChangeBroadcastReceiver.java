package com.skd.nubit.utilityclasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PageChangeBroadcastReceiver extends BroadcastReceiver {
    public static final String ACTION_PAGE_CHANGED = "com.example.app.PAGE_CHANGED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(ACTION_PAGE_CHANGED)) {
            // Handle the page change event here
            Log.e("checkpageps",">>" + intent.getStringExtra("current_page"));
        }
    }
}
package com.android.launcher3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.os.Bundle;

//create for ISU-2023-0002900
public class LoaderIconCacheActivity extends Activity {

    private static final String LOG = "LoaderTask";
    private LauncherModel mModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Log.d(LOG, "LoaderIconCacheActivity loadWorkspaceCacheDuringOOBE");
            LauncherAppState app = LauncherAppState.getInstance(this);
            mModel = app.getModel();
            mModel.loadWorkspaceCacheDuringOOBE(this);
        } catch(Exception e) {
            e.printStackTrace();
            Log.d(LOG, "LoaderIconCacheActivity loadWorkspaceCacheDuringOOBE failed");
        }
        finish();
        overridePendingTransition(0,0);
    }
}

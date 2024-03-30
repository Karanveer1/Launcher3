package com.skd.nubit.mynotification;

import android.content.Context;
import android.os.StrictMode;

public class PushConfig {
    private static PushConfig instance;
    private transient Context context;

    private PushConfig() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public static PushConfig getInstance() {
        if (instance == null) {
            instance = new PushConfig();
        }

        return instance;
    }

    public void init(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }
}


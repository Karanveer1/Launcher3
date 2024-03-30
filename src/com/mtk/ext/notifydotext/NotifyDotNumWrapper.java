package com.mtk.ext.notifydotext;

import android.content.Context;

import com.mtk.ext.LauncherAppMonitor;
import com.mtk.ext.LogUtils;

public class NotifyDotNumWrapper {

    public static void setNotifyDotsExtPrefEnabled(Context context, boolean enable) {
        // add for bug 1672152.Coverity scan getInstance returned null
        LauncherAppMonitor launcherAppMonitor = LauncherAppMonitor.getInstance(context);
        if (launcherAppMonitor == null) {
            return;
        }
        NotifyDotNumController ndnc = launcherAppMonitor.getNotifiDotsNumController();
        if (null != ndnc) {
            ndnc.setNotifyDotsExtPrefEnabled(enable);
        }
    }

    public static boolean isFullFreshEnabled() {
        boolean fullFresh = false;
        LauncherAppMonitor lam = LauncherAppMonitor.getInstanceNoCreate();
        if(lam != null) {
            NotifyDotNumController ndnc = lam.getNotifiDotsNumController();
            if (ndnc != null) {
                fullFresh = ndnc.isFullFreshEnabled();
            }
        }
        return fullFresh;
    }

    public static void setFullFreshEnabled(boolean enabled) {
        LauncherAppMonitor lam = LauncherAppMonitor.getInstanceNoCreate();
        if(lam != null) {
            NotifyDotNumController ndnc = lam.getNotifiDotsNumController();
            if (ndnc != null) {
                ndnc.setFullFreshEnabled(enabled);
            }
        }

    }

    public static boolean showNotifyDotsNum(Context context) {
        boolean showNum = false;
        // add for bug 1672152.Coverity scan getInstance returned null
        LauncherAppMonitor launcherAppMonitor = LauncherAppMonitor.getInstance(context);
        if (launcherAppMonitor == null) {
            return showNum;
        }
        NotifyDotNumController ndnc = launcherAppMonitor.getNotifiDotsNumController();
        if (ndnc != null) {
            showNum = ndnc.isShowNotifyDotsNum();
        }
        return showNum;
    }
}

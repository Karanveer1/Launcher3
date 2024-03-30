package com.mtk.ext;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.UserHandle;

import com.android.launcher3.BaseDraggingActivity;
import com.android.launcher3.Launcher;
import com.android.launcher3.LauncherState;
import com.android.launcher3.model.data.AppInfo;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SPRD on 2017/7/6.
 */

public class LauncherAppMonitorCallback {
    //Launcher activity Callbacks
    public void onLauncherPreCreate(Launcher launcher) { }

    public void onLauncherCreated(Launcher launcher) { }

    public void onLauncherPreResume() { }

    public void onLauncherResumed() { }

    public void onLauncherStart() { }

    public void onLauncherStop() { }

    public void onLauncherPrePaused() { }

    public void onLauncherPaused() { }

    public void onLauncherDestroy() { }

    public void onLauncherRequestPermissionsResult(int requestCode, String[] permissions,
            int[] grantResults) { }

    public void onSettingsActivityRequestPermissionsResult(int requestCode, String[] permissions,
            int[] grantResults) { }

    public void onLauncherFocusChanged(boolean hasFocus) { }

    public void onQuickstepLauncherStart() { }

    public void onRecentsActivityCreate(BaseDraggingActivity activity) { }

    public void onRecentsActivityStart() { }

    public void onHomeIntent() { }

    public void onBindingWorkspaceFinish () { }

    public void onBindingAllAppFinish (AppInfo[] apps) { }

    //Launcher app Callbacks
    public void onAppCreated(Context context) { }

    public void onReceive(Intent intent) { }

    public void onUIConfigChanged() { }

    public void onThemeChanged() { }

    public void onGridChanged() { }

    public void onAppSharedPreferenceChanged(String key) { }

    public void onPackageRemoved(String packageName, UserHandle user) { }

    public void onPackageAdded(String packageName, UserHandle user) { }

    public void onPackageChanged(String packageName, UserHandle user) { }

    public void onPackagesAvailable(String[] packageNames, UserHandle user, boolean replacing) { }

    public void onPackagesUnavailable(String[] packageNames, UserHandle user, boolean replacing) { }

    public void onPackagesSuspended(String[] packageNames, UserHandle user) { }

    public void onPackagesUnsuspended(String[] packageNames, UserHandle user) { }

    public void onPackagesUpdated(String[] packageNames, UserHandle user, int op) { }

    public void onShortcutsChanged(String packageName, List<ShortcutInfo> shortcuts, UserHandle user) { }

    public void onAllAppsListUpdated(List<AppInfo> apps) { }

    public void onLauncherLocaleChanged() { }

    public void onLauncherOrientationChanged () { }

    public void onLauncherScreensizeChanged() { }

    public void onLoadAllAppsEnd(ArrayList<AppInfo> apps) { }

    public void onHomeStyleChanged(String style) { }

    //Launcher database callbacks
    public void onDbUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    public void onStateSetStart(LauncherState toState) { }

    public void onStateSetEnd(LauncherState toState) { }

    public void onTaskStackUpdated(boolean hasTask) { }

    // UNISOC: modify for bug 1346137
    public void onOrientationStateChanged() { }

    public void dump(String prefix, FileDescriptor fd, PrintWriter w, boolean dumpAll) { }
}

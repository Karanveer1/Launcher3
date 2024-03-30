package com.sprd.ext.lockicon;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.android.systemui.shared.recents.model.Task;

public class TaskLockStatus {
    private static final String TAG = "TaskLockStatus";
    private static final String LOCK_SHARED_PREFERENCES_KEY = "recents_lockedtasks";
    private static final String KEY_PREFIX = "u: ";
    private static final String KEY_SEPARATOR = ", ";

    static boolean isSavedLockedTask(Context context, String stringKey) {
        if (TextUtils.isEmpty(stringKey)) {
            return false;
        }
        boolean isLocked = getRecentsPrefs(context).contains(stringKey);
        return isLocked;
    }

    static boolean setLockState(Context context, String stringKey, boolean isLocked) {
        if (TextUtils.isEmpty(stringKey)) {
            return false;
        }
        SharedPreferences.Editor editor = getRecentsPrefs(context).edit();
        if (isLocked) {
            editor.putInt(stringKey, 0).apply();
        } else {
            editor.remove(stringKey).apply();
        }
        return true;
    }

    private static SharedPreferences getRecentsPrefs(Context context) {
        return context.getSharedPreferences(LOCK_SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    static String makeTaskStringKey(Context context, Task task) {
        if (task == null || task.key == null || task.key.baseIntent == null ||
                task.key.baseIntent.getComponent() == null) {
            return null;
        }

        ActivityInfo info;
        ComponentName cn = task.key.baseIntent.getComponent();
        try {
            PackageManager pm = context.getPackageManager();
            info = pm.getActivityInfo(cn, 0);
        } catch (PackageManager.NameNotFoundException nnfe) {
            info = null;
        }
        String name;
        if (info != null && !TextUtils.isEmpty(info.taskAffinity)) {
            name = info.taskAffinity;
        } else {
            name = cn.toString();
        }

        String key = KEY_PREFIX + task.key.userId + KEY_SEPARATOR + name;
        return key;
    }
}
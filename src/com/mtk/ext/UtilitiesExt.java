package com.mtk.ext;

//import static com.android.launcher3.InvariantDeviceProfile.KEY_MIGRATION_SRC_HOTSEAT_COUNT;
//import static com.android.launcher3.InvariantDeviceProfile.KEY_MIGRATION_SRC_WORKSPACE_SIZE;
import static com.android.launcher3.util.Executors.MAIN_EXECUTOR;
import static com.android.launcher3.util.Executors.MODEL_EXECUTOR;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;

import com.android.launcher3.LauncherAppState;
import com.android.launcher3.LauncherModel;
import com.android.launcher3.model.data.PackageItemInfo;
import com.android.launcher3.pm.UserCache;
import com.android.launcher3.util.ComponentKey;
import com.android.launcher3.util.LooperExecutor;
//import com.sprd.ext.multimode.MultiModeController;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

/**
 * Created by SPRD on 11/14/16.
 */

public class UtilitiesExt {
    private static final String TAG = "UtilitiesExt";
    private static final String IS_IN_ULTRA_SAVING_MODE = "is_in_ultra_saving_mode";
    private static Class<?> mStatusBarManagerClassType = null;
    private static Method mExpandNotificationsMethod = null;

    public static final int BASE_STACK_DEPTH = 3;

    public static final boolean IS_LOW_RAM = SystemPropertiesUtils.getBoolean("ro.config.low_ram", false);

    public static boolean isAppInstalled(Context context, String pkgName, UserHandle user) {
        final LauncherApps launcherApps = context.getSystemService(LauncherApps.class);
        return launcherApps != null && launcherApps.isPackageEnabled(pkgName, user);

    }

    public static void getAppIcon(Context context, String pkgName, final Consumer<Bitmap> consumer) {
        /* UNISOC: modify for bug 1644408 launcher control dynamic icon, Launcher
        crashed due to the updating of dynamic icon titles in settings. */
        final Looper looper = Looper.myLooper();
        MODEL_EXECUTOR.execute(() -> {
            PackageItemInfo pkgInfo = new PackageItemInfo(pkgName,Process.myUserHandle());
            LauncherAppState.INSTANCE.get(context).getIconCache().getTitleAndIconForApp(pkgInfo, false);
            new LooperExecutor(looper).execute(() -> consumer.accept(pkgInfo.bitmap.icon));
        });
    }

    public static void getAppLabelByPackageName(Context context, String pkgName, final Consumer<CharSequence> consumer) {
        final Looper looper = Looper.myLooper();
        MODEL_EXECUTOR.execute(() -> {
            PackageItemInfo pkgInfo = new PackageItemInfo(pkgName,Process.myUserHandle());
            LauncherAppState.INSTANCE.get(context).getIconCache().getTitleAndIconForApp(pkgInfo, false);
            new LooperExecutor(looper).execute(() -> consumer.accept(pkgInfo.title));
        });
    }

    public static Point getTextDrawPoint(Rect targetRect, Paint.FontMetrics fm) {
        Point p = new Point();
        int fontHeight = Math.round(fm.descent - fm.ascent);
        int paddingY = (targetRect.height() - fontHeight) >> 1;
        p.x = targetRect.centerX();
        p.y = targetRect.top + paddingY + Math.abs(Math.round(fm.ascent));
        return p;
    }

    /**
     * When set to true, apps will draw debugging information about their layouts.
     *
     * @see android.view.View
     */
    private static boolean enableDebugLayout() {
        //see View.java:DEBUG_LAYOUT_PROPERTY
        return SystemPropertiesUtils.getBoolean("debug.layout", false);
    }

    public static void drawDebugRect(final Canvas canvas, final Rect rect, final int color) {
        if (enableDebugLayout()) {
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(color);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(rect, paint);
        }
    }

    public static void DEBUG_PRINT_FUNCTIONNAME(int depth, String msg) {
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        String clsName = ste[1 + depth].getClassName();
        String tag = clsName.substring(clsName.lastIndexOf(".") + 1);
        LogUtils.d(tag, ste[1 + depth].getMethodName()
                + (TextUtils.isEmpty(msg) ? "" : ", " + msg));
    }

    public static void DEBUG_PRINT_FUNCTIONNAME(Object obj) {
        DEBUG_PRINT_FUNCTIONNAME(BASE_STACK_DEPTH, Object2String(obj));
    }

    public static void closeCursorSilently(Cursor cursor) {
        try {
            if (cursor != null) cursor.close();
        } catch (Throwable t) {
            LogUtils.w(TAG, "fail to close", t);
        }
    }

    private static Class<?> getStatusBarManagerClass() throws ClassNotFoundException {
        if (mStatusBarManagerClassType == null) {
            mStatusBarManagerClassType = Class.forName("android.app.StatusBarManager");
        }
        return mStatusBarManagerClassType;
    }

    private static Method getExpandNotificationsMethod() throws Exception {
        if (mExpandNotificationsMethod == null) {
            Class clazz = getStatusBarManagerClass();
            mExpandNotificationsMethod = clazz.getDeclaredMethod("expandNotificationsPanel");
        }
        return mExpandNotificationsMethod;
    }

    public static void openNotifications(Context context) {
        try {
            if (getExpandNotificationsMethod() != null) {
                Method method = getExpandNotificationsMethod();
                method.invoke(context.getSystemService("statusbar"));
            }
        } catch (Exception ex) {
            LogUtils.e(TAG, "expand notifications panel failed : ");
            ex.printStackTrace();
        }
    }

/*
    public static String getIdpWorkspaceSizeKey(Context context) {
        return MultiModeController.getKeyByMode(context, KEY_MIGRATION_SRC_WORKSPACE_SIZE);
    }

    public static String getIdpHotseatCountKey(Context context) {
        return MultiModeController.getKeyByMode(context, KEY_MIGRATION_SRC_HOTSEAT_COUNT);
    }

    public static String getDensityDpiKey(Context context) {
        return MultiModeController.getKeyByMode(context, KEY_MIGRATION_SRC_DENSITY_DPI);
    }
*/
    public static Point parsePoint(String point) {
        String[] split = point.split(",");
        return new Point(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    public static String getPointString(int x, int y) {
        return String.format(Locale.ENGLISH, "%d,%d", x, y);
    }

    // Returns the input value x clamped to the range [min, max].
    public static int clamp(int x, int min, int max) {
        if (x > max) return max;
        if (x < min) return min;
        return x;
    }

    public static String marshall(Parcelable parcelable) {
        Parcel p = Parcel.obtain();
        parcelable.writeToParcel(p, 0);
        byte[] bytes = p.marshall();
        p.recycle();

        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static <T> T unMarshall(String str, Parcelable.Creator<T> creator) {
        byte[] bytes = Base64.decode(str, Base64.DEFAULT);

        Parcel parcel = Parcel.obtain();
        parcel.unmarshall(bytes, 0, bytes.length);
        parcel.setDataPosition(0);
        T result = creator.createFromParcel(parcel);
        parcel.recycle();

        return result;
    }

    public static boolean getSystemBooleanRes(String resName) {
        Resources res = Resources.getSystem();
        int resId = res.getIdentifier(resName, "bool", "android");

        if (resId != 0) {
            return res.getBoolean(resId);
        } else {
            LogUtils.e(TAG, "Failed to get system resource ID. Incompatible framework version?");
            return false;
        }
    }

    public static float checkFloatPropertyValueValid(float value, String propertyName, float min, float max) {
        float defaultValue = 1.0f;
        if (value > min && value < max) {
            return value;
        }
        return defaultValue;
    }

    public static Rect getIconRect(int iconSize, View view) {
        Point center = new Point(view.getScrollX() + (view.getWidth() >> 1),
                view.getScrollY() + view.getPaddingTop() + (iconSize >> 1));
        Rect iconRect = new Rect();

        iconRect.left = center.x - (iconSize >> 1);
        iconRect.top = center.y - (iconSize >> 1);
        iconRect.right = iconRect.left + iconSize;
        iconRect.bottom = iconRect.top + iconSize;
        return iconRect;
    }

    public static String componentKeyToString(Context context, ComponentKey componentKey) {
        if (null == context || null == componentKey || null == componentKey.componentName) {
            return "";
        }
        String flattened = componentKey.componentName.flattenToShortString();
        if (null != componentKey.user) {
            flattened += "#" + UserCache.INSTANCE.get(context).getSerialNumberForUser(componentKey.user);
        }
        return flattened;
    }

    public static ComponentKey stringToComponentKey(Context context, String componentKeyStr) {

        ComponentName componentName;
        UserHandle user;
        int userDelimiterIndex = componentKeyStr.indexOf("#");
        if (userDelimiterIndex != -1) {
            String componentStr = componentKeyStr.substring(0, userDelimiterIndex);
            componentName = ComponentName.unflattenFromString(componentStr);
            long componentUser = Long.parseLong(componentKeyStr.substring(userDelimiterIndex + 1));
            user = UserCache.INSTANCE.get(context).getUserForSerialNumber(componentUser);
        } else {
            // No user provided, default to the current user
            componentName = ComponentName.unflattenFromString(componentKeyStr);
            user = Process.myUserHandle();
        }

        return new ComponentKey(componentName, user);
    }

    public static boolean isInUltraSavingMode(Context context) {
        ContentResolver resolver = context.getContentResolver();
        return Settings.System.getInt(resolver, IS_IN_ULTRA_SAVING_MODE, 0) == 1;
    }

    public static String Object2String(Object obj) {
        return obj != null ? obj.toString() : "NULL";
    }

    @SuppressWarnings("CallToSystemExit")
    public static void exitLauncher(Activity activity) {
        // Finish all parent activity.
        if (activity != null) {
            activity.finishAffinity();
        }
        System.exit(0);
    }
    public static boolean isRoboUnitTest() {
        return isASRoboUnitTest() || Build.UNKNOWN.equals(Build.DEVICE);
    }

    public static boolean isASRoboUnitTest() {
        return "robolectric".equals(Build.DEVICE);
    }

    public static boolean checkItemIsLauncherApp(Context context, ComponentName cn, UserHandle user) {
        LauncherModel lm = LauncherAppState.getInstance(context).getModel();
        if (lm.isModelLoaded()) {
            LogUtils.i(TAG, "LauncherModel isModelLoaded to find cn : " + cn);
            return lm.getBgAllAppsList().findAppInfo(cn, user) != null;
        } else {
            List<LauncherActivityInfo> activities = context.getSystemService(LauncherApps.class)
                    .getActivityList(cn.getPackageName(), user);
            for (LauncherActivityInfo laInfo : activities) {
                if (laInfo.getComponentName().equals(cn)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isUserTypeProfile (Context context, UserHandle userHandle, String type) {
        final UserManager userManager = (UserManager)context.getSystemService(UserManager.class);
        UserInfo userInfo = userManager.getUserInfo(userHandle.getIdentifier());
        boolean result = false;
        switch (type) {
            case UserManager.USER_TYPE_PROFILE_CLONE:
                result = userInfo != null && userInfo.isCloneProfile();
                break;
            case UserManager.USER_TYPE_PROFILE_MANAGED:
                result = userInfo != null && userInfo.isManagedProfile();
                break;
            default:
                break;
        }
        return result;
    }

    public static boolean isOnlyHasWorkProfile(Context context) {
        boolean result = false;
        List<UserHandle> profilesList = context.getSystemService(LauncherApps.class).getProfiles();
        int size = profilesList.size();
        if (size > 1) {
            for(UserHandle user : profilesList) {
               if (isUserTypeProfile(context, user,
                       UserManager.USER_TYPE_PROFILE_CLONE)) {
                   result = false;
                   break;
               }
               result = true;
            }
        }
        return result;
    }
}

package com.mtk.ext;

import android.util.Log;

import com.android.launcher3.Utilities;
import com.android.launcher3.config.FeatureFlags;
import com.android.launcher3.config.FeatureFlags.BooleanFlag;

/**
 * Defines a set of features used to control launcher feature.
 * All the feature should be defined here with appropriate default values.
 * This class is kept package-public to direct access.
 */

public final class FeatureOption {
    private static final String TAG = "FeatureOption";

    public static final boolean ENABLE_DEBUG = false;

    public static final boolean SPRD_ALLAPP_BG_TRANSPARENT_SUPPORT  =  false;
    /*
    public static boolean ENABLE_DEBUG = (FeatureOption.enableDebugFeatures()
            || Utilities.IS_DEBUG_DEVICE);

    public static final BooleanFlag SPRD_ALLAPP_BG_TRANSPARENT_SUPPORT = new FeatureFlags.DebugFlag(
            "SPRD_ALLAPP_BG_TRANSPARENT_SUPPORT",
            getProp("ro.launcher.allapp.bgtransp"),
            "enable can customize the allapps background's alpha");

    public static final BooleanFlag SPRD_ALLAPP_CUSTOMIZE_SUPPORT = new FeatureFlags.DebugFlag(
            "SPRD_ALLAPP_CUSTOMIZE_SUPPORT", getProp("ro.launcher.allapp.customize"),
            "enable can customize the allapp views app position");
    */
    public static final boolean SPRD_ALLAPP_CUSTOMIZE_SUPPORT = true;

    /*
    public static final BooleanFlag SPRD_ICON_LABEL_LINE_SUPPORT = new FeatureFlags.DebugFlag(
            "SPRD_ICON_LABEL_LINE_SUPPORT", getProp("ro.launcher.iconlabelline"),
            "icon label support display double lines");
    */
    public static final boolean SPRD_ICON_LABEL_LINE_SUPPORT = true;

    /*
    public static final BooleanFlag SPRD_ALLAPP_FUZZY_SEARCH_SUPPORT = new FeatureFlags.DebugFlag(
            "SPRD_ALLAPP_FUZZY_SEARCH_SUPPORT",
            getProp("ro.launcher.allapp.fuzzysearch", !UtilitiesExt.IS_LOW_RAM),
            "enable all apps searchbox can search any character");
    */

    public static final boolean SPRD_DISABLE_ROTATION = UtilitiesExt.IS_LOW_RAM ? true : false;
    /*
    public static final BooleanFlag SPRD_APP_REMOTE_ANIM_SUPPORT = new FeatureFlags.DebugFlag(
            "SPRD_APP_REMOTE_ANIM_SUPPORT", getProp("sys.app.remote.anim"),
            "Performance features: remote animation for app");

    public static final BooleanFlag SPRD_SDCARD_APP_ICON = new FeatureFlags.DebugFlag(
            "SPRD_SDCARD_APP_ICON", getProp("ro.launcher.sdcard.app", true),
            "Optimization: If the app is on sdcard, retain the icon when restart");

    public static final BooleanFlag SPRD_DEBUG_SEARCH_CUSTOMIZE_SUPPORT = new FeatureFlags.DebugFlag(
            "SPRD_DEBUG_SEARCH_CUSTOMIZE_SUPPORT", false,
            "Replace the search box with other search box");
    public static final BooleanFlag SPRD_DYNAMIC_ICON_SUPPORT = new FeatureFlags.DebugFlag(
            "SPRD_DYNAMIC_ICON_SUPPORT", getProp("ro.launcher.dynamic"),
            "enable the clock & calendar icon will dynamic update");
    */
    public static final boolean SPRD_DYNAMIC_ICON_SUPPORT = true;
    /*
    public static final BooleanFlag SPRD_FOLDER_ICON_MODE_SUPPORT = new FeatureFlags.DebugFlag(
            "SPRD_FOLDER_ICON_MODE_SUPPORT", getProp("ro.launcher.foldericonmode"),
            "enable folder icon support grid mode & aosp mode");

    public static final BooleanFlag SPRD_GESTURE_SUPPORT = new FeatureFlags.DebugFlag(
            "SPRD_GESTURE_SUPPORT", getProp("ro.launcher.gesture", true),
            "enable will support workspace pull down gestures");

    public static final BooleanFlag SPRD_CYCLE_SCROLL_SUPPORT = new FeatureFlags.DebugFlag(
            "SPRD_CYCLE_SCROLL_SUPPORT", getProp("ro.launcher.cyclescroll", true),
            "enable workspace will cycle scroll");

    // When enabled the hotseat icon will adaptive layout
    public static final BooleanFlag SPRD_HOTSEAT_ICON_ADAPTIVE_LAYOUT = new FeatureFlags.DebugFlag(
            "SPRD_HOTSEAT_ICON_ADAPTIVE_LAYOUT", getProp("ro.launcher.hs.adaptive", true),
            "enable the hotseat icon will adaptive layout");

    public static final BooleanFlag SPRD_CLOSE_SWITCH_WORKSPACE_ANIM = new FeatureFlags.DebugFlag(
            "SPRD_CLOSE_SWITCH_WORKSPACE_ANIM",
            getProp("ro.launcher.close.ws.anim", UtilitiesExt.IS_LOW_RAM),
            "Performance optimization: remove workspace switch anim");
    */
    public static final boolean SPRD_NOTIFICATION_DOT_COUNT = true;
    /*
    public static final BooleanFlag SPRD_NOTIFICATION_DOT_COUNT = new FeatureFlags.DebugFlag(
            "SPRD_NOTIFICATION_DOT_COUNT", getProp("ro.launcher.notifbadge.count"),
            "enable show the notification badge count");

    public static final BooleanFlag SPRD_FAST_UPDATE_LABEL = new FeatureFlags.DebugFlag(
            "SPRD_FAST_UPDATE_LABEL", getProp("ro.launcher.label.fastupdate", true),
            "Performance features:fast update label when language changing");

    public static final BooleanFlag SPRD_BADGE_SUPPORT = new FeatureFlags.DebugFlag(
            "SPRD_BADGE_SUPPORT", getProp("ro.launcher.badge"),
            "enable the phone & message & unisoc email & unisoc calendar " +
            "icon will show icon badge");

    public static final boolean SPRD_BADGE_PHONE_SUPPORT =
            getProp("ro.launcher.badge.phone", true);

    public static final boolean SPRD_BADGE_MESSAGE_SUPPORT =
            getProp("ro.launcher.badge.message", true);

    public static final boolean SPRD_BADGE_EMAIL_SUPPORT =
            getProp("ro.launcher.badge.email", true);

    public static final boolean SPRD_BADGE_CALENDAR_SUPPORT =
            getProp("ro.launcher.badge.calendar", true);

    public static final BooleanFlag SPRD_TASK_LOCK_SUPPORT = new FeatureFlags.DebugFlag(
            "SPRD_TASK_LOCK_SUPPORT", getProp("ro.launcher.tasklock", true),
            "only for recent:enable lock task");

    public static final BooleanFlag SPRD_SHOW_MEMINFO_SUPPORT = new FeatureFlags.DebugFlag(
            "SPRD_SHOW_MEMINFO_SUPPORT", getProp("ro.launcher.showmeminfo", true),
            "only for recent:enable show memory info");
    */
    //public static final boolean SPRD_NOTIFICATION_DOT_COUNT = true;
    public static final boolean SPRD_BADGE_SUPPORT = UtilitiesExt.IS_LOW_RAM ? true : false;
    public static final boolean SPRD_BADGE_PHONE_SUPPORT = true;
    public static final boolean SPRD_BADGE_MESSAGE_SUPPORT = true;
    public static final boolean SPRD_BADGE_EMAIL_SUPPORT = true;
    public static final boolean SPRD_BADGE_CALENDAR_SUPPORT = true;
    public static final boolean SPRD_TASK_LOCK_SUPPORT = true;
    public static final boolean SPRD_SHOW_MEMINFO_SUPPORT = true;
    public static final boolean SPRD_CLEAR_ALL_ON_BOTTOM_SUPPORT = true;
    public static final boolean SPRD_SHOW_CLEAR_MEM_SUPPORT = true;
    /*
    public static final BooleanFlag SPRD_CLEAR_ALL_ON_BOTTOM_SUPPORT = new FeatureFlags.DebugFlag(
            "SPRD_CLEAR_ALL_ON_BOTTOM_SUPPORT", getProp("ro.launcher.clearallonbottom", true),
            "only for recent:enable show clear all button under the recent task list");

    public static final BooleanFlag SPRD_SHOW_CLEAR_MEM_SUPPORT = new FeatureFlags.DebugFlag(
            "SPRD_SHOW_CLEAR_MEM_SUPPORT", getProp("ro.launcher.showclearmem", true),
            "only for recent:enable show toast when clear mem");

    public static final BooleanFlag SPRD_MULTI_MODE_SUPPORT = new FeatureFlags.DebugFlag(
            "SPRD_MULTI_MODE_SUPPORT", getProp("ro.launcher.multimode"),
            "enable user can select user aosp mode or singlelayer mode");

    private static boolean getProp(String prop) {
        return getProp(prop, false);
    }

    private static boolean getProp(String prop, boolean defaultValue) {
        boolean ret = false;

        try {
            ret = SystemPropertiesUtils.getBoolean(prop, defaultValue);
        } catch (Exception e) {
            LogUtils.e(TAG, "getProp:" + prop + " error." + e);
        }

        return ret;
    }
    */

    /**
     * To enable debug feature, execute the following command:
     * $ adb shell setprop log.tag.FeatureOption DEBUG
     */
    /*
    private static boolean enableDebugFeatures() {
        return Log.isLoggable(TAG, Log.DEBUG);
    }
    */
}

package com.mtk.ext;

import android.app.ActivityManager;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.android.launcher3.R;
import com.android.launcher3.SessionCommitReceiver;
import com.android.launcher3.Utilities;

import com.mtk.ext.icon.IconLabelController;


//porting change icon sharp feature
import android.content.Context;
import android.util.Log;
import java.util.List;
//end
import com.mtk.ext.notifydotext.NotifyDotNumController;

/**
 * Created by SPRD on 6/15/17.
 */

public class LauncherSettingsExtension {

    public static final String PREF_ENABLE_MINUS_ONE = "pref_enable_minus_one";
    private static final String PREF_KEY_DYNAMICICON = "pref_dynamicIcon";
    private static final String PREF_KEY_UNREAD = "pref_unread";
    public static final String PREF_NOTIFICATION_DOTS_EXT = "pref_notification_dots_ext";
    public static final String PREF_ONEFINGER_PULLDOWN = "pref_pulldown_action";
    public static final String PREF_CIRCULAR_SLIDE_KEY = "pref_circular_slide_switch";
    public static final String PREF_HOME_SCREEN_STYLE_KEY = "pref_home_screen_style";
    public static final String PREF_FOLDER_ICON_MODE_KEY = "pref_folder_icon_model";
    public static final String PREF_DESKTOP_GRID_KEY = "pref_desktop_grid";
    public static final String PREF_ICON_LABEL_KEY = "pref_icon_label_line";

    //porting change icon sharp
    public static final String PREF_ICON_SHARP = "pref_icon_sharp";
    public static final String PREF_ICON_SHAPE = "android.theme.customization.adaptive_icon_shape";
    //end

    private PreferenceFragmentCompat mFragment;
    private final LauncherAppMonitor mMonitor;

    public static boolean sIsUserAMonkey = false;

    public LauncherSettingsExtension(PreferenceFragmentCompat fragment) {
        mFragment = fragment;
        mMonitor = LauncherAppMonitor.getInstance(mFragment.getActivity());
    }

    public void initPreferences(Bundle savedInstanceState) {
        SwitchPreference addIconPref = (SwitchPreference) mFragment.findPreference(
                SessionCommitReceiver.ADD_ICON_PREFERENCE_KEY);
        /*if (MultiModeController.isSingleLayerMode(mFragment.getActivity())) {
            // Need Remove add icon to home preference in case Single layer mode
            mFragment.getPreferenceScreen().removePreference(addIconPref);
            */
        if (false) {
            
        } else {
            // If add icon Preference item exist. Add listener to set user action flag.
            addIconPref.setOnPreferenceChangeListener((preference, newValue) -> {
                SharedPreferences prefs = Utilities.getPrefs(preference.getContext());
                if (!prefs.contains(SessionCommitReceiver.ADD_ICON_PREFERENCE_INITIALIZED_KEY)) {
                    prefs.edit()
                            .putBoolean(SessionCommitReceiver.ADD_ICON_PREFERENCE_INITIALIZED_KEY, true)
                            .apply();
                }
                return true;
            });
        }
        mFragment.addPreferencesFromResource(R.xml.launcher_preferences_extension);

        /* add for app icon label display double lines */
        SwitchPreference iconLabelPref  =  mFragment.findPreference(PREF_ICON_LABEL_KEY);
        if (iconLabelPref != null) {
            IconLabelController ilc = mMonitor.getIconLabelController();
            if (ilc != null) {
                iconLabelPref.setOnPreferenceChangeListener(ilc);
            } else {
                mFragment.getPreferenceScreen().removePreference(iconLabelPref);
            }
        }

        /* Add for grid option feature */
        
        /* add for notification dots num */
        Preference notifyDotsPrefExt = mFragment.findPreference(PREF_NOTIFICATION_DOTS_EXT);
        NotifyDotNumController ndnc = mMonitor.getNotifiDotsNumController();
        if (null != ndnc) {
            ndnc.initPreference(notifyDotsPrefExt);
        } else {
            mFragment.getPreferenceScreen().removePreference(notifyDotsPrefExt);
        }
    }

    public void onLauncherSettingStart() {
        sIsUserAMonkey = ActivityManager.isUserAMonkey();
    }
}

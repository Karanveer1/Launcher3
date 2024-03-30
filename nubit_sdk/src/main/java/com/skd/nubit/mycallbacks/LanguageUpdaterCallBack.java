package com.skd.nubit.mycallbacks;

/**
 * Created by Robi on 27/9/18.
 */

public class LanguageUpdaterCallBack {

    private static LanguageUpdaterCallBack nubitFragmentUpdater;

    private LanguageChangeCallback languageChangeCallback;

    public interface LanguageChangeCallback {
        void ActionOnLanguageChange();
    }

    private LanguageUpdaterCallBack() {

    }

    public static LanguageUpdaterCallBack getInstance() {
        if (nubitFragmentUpdater == null) {
            nubitFragmentUpdater = new LanguageUpdaterCallBack();
        }
        return nubitFragmentUpdater;
    }


    public void initListenerForLanguage(LanguageChangeCallback languageChangeCallback) {
        this.languageChangeCallback = languageChangeCallback;
    }

    public void notifyNubitLanguageUpdater() {
        if (languageChangeCallback != null) {
            languageChangeCallback.ActionOnLanguageChange();
        }
    }

    public void flushNubitLanguageUpdater() {
        languageChangeCallback = null;
        nubitFragmentUpdater = null;
    }

}

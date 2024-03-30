package com.skd.nubit.utilityclasses;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.skd.nubit.googleanalytics.AnalyticsTrackers;
import com.skd.nubit.mynotification.PushConfig;

public class MyApplication extends Application {

    private static MyApplication mInstance;
    public static SharedPreferences app_sharedPreferences;
    public static SharedPreferences.Editor app_editor;

    public static Context ctx;

    private Thread.UncaughtExceptionHandler defaultHandler;


    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        app_sharedPreferences=getSharedPreferences("rkt_Pref",MODE_PRIVATE);
        app_editor=app_sharedPreferences.edit();

        try {
            AnalyticsTrackers.initialize(this);
            AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
        } catch (Exception e) {
            e.printStackTrace();
        }


        PushConfig.getInstance().init(getApplicationContext());

        this.ctx=getApplicationContext();
//        FirebaseCrash.enableCrash(true);
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);



        //Toast.makeText(getApplicationContext(),"Application Call",Toast.LENGTH_SHORT).show();
    }


   /* @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }*/

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }

    /***
     * Tracking screen view
     *
     * @param screenName screen name to be displayed on GA dashboard
     */
    public void trackScreenView(String screenName) {
        Tracker t = getGoogleAnalyticsTracker();

        // Set screen name.
        t.setScreenName(screenName);

        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());

        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }

    /***
     * Tracking exception
     *
     * @param e exception to be tracked
     */
    public void trackException(Exception e) {
        if (e != null) {
            Tracker t = getGoogleAnalyticsTracker();

            t.send(new HitBuilders.ExceptionBuilder()
                    .setDescription(
                            new StandardExceptionParser(this, null)
                                    .getDescription(Thread.currentThread().getName(), e))
                    .setFatal(false)
                    .build()
            );
        }
    }

    /***
     * Tracking event
     *
     * @param category event category
     * @param action   action of the event
     * @param label    label
     */
    public void trackEvent(String category, String action, String label) {
        Tracker t = getGoogleAnalyticsTracker();

        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }

    private Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            MyApplication.app_editor.clear().apply();
            defaultHandler.uncaughtException(t, e);
        }
    };



}


package com.skd.nubit.utilityclasses;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.gson.JsonArray;
import com.skd.nubit.R;
import com.skd.nubit.videoplayerstuffs.YouTube_PlayerActivity;
import com.skd.nubit.database.DbHandler;
import com.skd.nubit.database.TrackingDAO;
import com.skd.nubit.models.Like_DLike_Pojo;
import com.skd.nubit.models.LikesDAO;
import com.skd.nubit.models.TrackingPojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class MyUtility {


    public static final int APP_BUCKET_REQUEST_CODE = 233;

    public static String[] storagePermissionArray = new String[]{android.Manifest.permission
            .WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA};

    public static String[] locationPermissionArray = new String[]{android
            .Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission
            .ACCESS_COARSE_LOCATION};

    public static String[] contactsPermissionArray = new String[]{android.Manifest.permission
            .GET_ACCOUNTS};
    public static String[] smsPermissionArray = new String[]{android.Manifest.permission
            .READ_SMS};

    public static String[] phonePermissionArray = new String[]{android.Manifest.permission
            .READ_PHONE_STATE};


    public static boolean storage_sentToSettings = false;
    public static boolean location_sentToSettings = false;
    public static boolean contacts_sentToSettings = false;
    public static boolean sms_sentToSettings = false;
    public static boolean phone_sentToSettings = false;
    public static final int storage_PERMISSION_CALLBACK_CONSTANT = 711;
    public static final int location_PERMISSION_CALLBACK_CONSTANT = 712;
    public static final int contacts_PERMISSION_CALLBACK_CONSTANT = 713;
    public static final int sms_PERMISSION_CALLBACK_CONSTANT = 717;
    public static final int phone_PERMISSION_CALLBACK_CONSTANT = 722;
    public static final int storage_REQUEST_PERMISSION_SETTING = 714;
    public static final int location_REQUEST_PERMISSION_SETTING = 715;
    public static final int contacts_REQUEST_PERMISSION_SETTING = 716;
    public static final int sms_REQUEST_PERMISSION_SETTING = 718;
    public static final int phone_REQUEST_PERMISSION_SETTING = 721;

    public static String bucket_name = "calvry-v2";


    public static void saveLikesInDB(Context context, String eventType, String redirectURL, String
            pubName, String newsLanguage, String eventDate, String newsTitle) {

// redirectURL previously contains the redirectLink but now its contain outputLink. But in
// database column name is remain same REDIRECT_URL.

        Like_DLike_Pojo like_dLike_pojo;


        like_dLike_pojo = new Like_DLike_Pojo();
        like_dLike_pojo.setEventType(eventType);
        like_dLike_pojo.setRedirectURL(redirectURL);
        like_dLike_pojo.setPubName(pubName);
        like_dLike_pojo.setNewsLanguage(newsLanguage);
        like_dLike_pojo.setEventDate(eventDate);
        like_dLike_pojo.setNewsTitle(newsTitle);

        DbHandler dbHandler = DbHandler.getInstance(context);
        SQLiteDatabase _sqlDb = dbHandler.openDb(1);
        LikesDAO.getInstance().insertTrackRecords(_sqlDb, like_dLike_pojo);

    }

    public static MediaPlayer player;


    public static int CONTAINER_HEIGHT;
    public static int screenWidth;


    public static final String SHARED_PREF_LAUNCH_NUBIT = "launch_lfty";
    public static final String SHARED_PREF_KEY_SCROLL_SECTION = "key_scroll_section";

    public static final int MULTIPLE_PERMISSIONS = 10;
    public static final int Profile_PERMISSION_CALLBACK_CONSTANT = 100;
    public static final int showHide_PERMISSION_CALLBACK_CONSTANT = 100;
    public static final int Profile_REQUEST_PERMISSION_SETTING = 101;
    public static final int showHide_REQUEST_PERMISSION_SETTING = 101;

    public static boolean Profile_sentToSettings = false;
    public static boolean showHide_sentToSettings = false;
    public static String[] profile_permissionsRequired = new String[]{android.Manifest.permission
            .WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA};

    public static String[] onshowHide_permissionsRequired = new String[]{android.Manifest
            .permission.READ_PHONE_STATE, android.Manifest.permission
            .READ_SMS, android.Manifest.permission.GET_ACCOUNTS, android
            .Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission
            .ACCESS_COARSE_LOCATION};


    public static String[] permissions = {

            Manifest.permission.READ_PHONE_STATE};


    /*public static String getprofileDataAPI = "http://lava.nubit
    .in/CacheManagement/userProfileInfo";*/

    public static String getprofileDataAPI = "http://lava.nubit.in/RedisLavaApi/getUserProfile";
    public static String getvistoryDataAPI = "https://api.vistory.in/vistory/api/v1/articles/getArticles";
    public static String getmainDataAPI = "https://api.vidring.app/minus-one/app/getData";
    public static String getappsDataAPI = "https://api.vidring.app/minus-one/app/getDetail";
    public static String getbannersDataAPI = "https://api.vidring.app/minus-one/banner/getBannerDetails";
                                         //https://api.vidring.app/minus-one/app/getDetail
    public static String getModuleOrderApi = "https://api.vidring.app/minus-one/section/getSectionDetails";
    public static String getweatherDataAPI = "https://api.vidring.app/minus-one/weather/getWeatherDetails";
    public static String getversionAPI = "http://lava.nubit.in/RedisLavaApi/getVersionList";

    public static String getDataAPI = "http://lava.nubit.in/RedisLavaApi/get_data";
    public static String uploadLikesDisAPI = "http://lava.nubit" +
            ".in/RedisLavaApi/newsLikeDislike_tracking";
    public static String insert_profileDataAPI = "http://lava.nubit" +
            ".in/RedisLavaApi/UserProfileDetail";




    public static String[] camera_atHome_permissionsRequired = new String[]{Manifest.permission
            .CAMERA};
    public static String[] mic_permissionsRequired = new String[]{android.Manifest.permission
            .RECORD_AUDIO};
    /*public static String getversionAPI = "http://lava.nubit.in/ApiController/getVersionList";*/




    public static final int camera_atHome_PERMISSION_CALLBACK_CONSTANT = 100;
    public static final int mic_PERMISSION_CALLBACK_CONSTANT = 111;
    public static int camera_atHome_REQUEST_PERMISSION_SETTING = 101;
    public static int mic_REQUEST_PERMISSION_SETTING = 101;
    /*    public static String insert_profileDataAPI = "http://lava.nubit
    .in/ApiController/profileDetail";*/



    public static boolean camera_atHome_sentToSettings = false;
    public static boolean mic_sentToSettings = false;


    public static final String UBER_CLIENT_KEY = "bX7zXruiur7_GVQ_3O9JA-FYpVG8Cr2i";
    public static final String UBER_SERVER_TOKEN = "syeSxOd-eWyDyzBIJfs_bsVXRK-4DVsQOtn28s3X";
    public static final String UBER_REDIRECT_URL = "https://www.google.com";
    public static final int UBER_REQUEST_CODE = 122;


    public static String get_redirectedURL;
    public static String get_SourceURL;


    public static final String PUSH_TRACKS = "http://lava.nubit" +
            ".in/RedisLavaApi/track_data_from_mobile_db";
    public static String get_packageName;

    public static int wallpaer_refresh_number = 0;
    public static int app_refresh_number = 0;
    public static int game_refresh_number = 0;
    public static int external_game_refresh_number = 0;
    public static int mCurrRotation = 0;


    public static final int REQUEST_PERMISSION_FOR_LOCATION = 1000;
    /*public static String getDataAPI = "http://lava.nubit.in/FotaApiController_v2/get_data";*/

    public static String yuptv = "http://lava.nubit.in/RedisYupTvApi/getYupTvData";

    public static void saveTracksInDB(Context context, String url, String redirectURL, String
            category, String launchType, String title, String play_duration) {

// redirectURL previously contains the redirectLink but now its contain outputLink. But in
// database column name is remain same REDIRECT_URL.

        TrackingPojo mTrackingPojo;

        Long tsLong = System.currentTimeMillis();
        CharSequence date = DateFormat.format("yyyy-MM-dd HH:mm:ss", tsLong);

        mTrackingPojo = new TrackingPojo();
        mTrackingPojo.setUrl(url);
        mTrackingPojo.setRedirectURL(redirectURL);
        mTrackingPojo.setCategory(category);
        mTrackingPojo.setDate("" + date);
        mTrackingPojo.setLaunch("" + launchType);
        mTrackingPojo.setTitle(title);
        mTrackingPojo.setPlay_duration(play_duration);

        DbHandler dbHandler = DbHandler.getInstance(context);
        SQLiteDatabase _sqlDb = dbHandler.openDb(1);
        TrackingDAO.getInstance().insertTrackRecords(_sqlDb, mTrackingPojo);

    }


   /* public static void saveLikesInDB(Context context, String eventType, String redirectURL, String
            pubName, String newsLanguage, String eventDate, String newsTitle) {

// redirectURL previously contains the redirectLink but now its contain outputLink. But in
// database column name is remain same REDIRECT_URL.

        Like_DLike_Pojo like_dLike_pojo;


        like_dLike_pojo = new Like_DLike_Pojo();
        like_dLike_pojo.setEventType(eventType);
        like_dLike_pojo.setRedirectURL(redirectURL);
        like_dLike_pojo.setPubName(pubName);
        like_dLike_pojo.setNewsLanguage(newsLanguage);
        like_dLike_pojo.setEventDate(eventDate);
        like_dLike_pojo.setNewsTitle(newsTitle);

        DbHandler dbHandler = DbHandler.getInstance(context);
        SQLiteDatabase _sqlDb = dbHandler.openDb(1);
        LikesDAO.getInstance().insertTrackRecords(_sqlDb, like_dLike_pojo);

    }
*/

   /* public static void hideKeyboard(Activity context) {
        View view = context.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }*/


   /* public static String getLocationAddress_City(Context context, double latitude, double
            longitude) {
        String city = "";
        if (latitude == 0.0) {
            return city;

        } else {
            Geocoder geocoder = null;

            try {
                geocoder = new Geocoder(context, Locale.getDefault());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (geocoder == null) {
                return "";
            }

            city = "";

            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                city = addresses.get(0).getLocality();

            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (TextUtils.isEmpty(city)) {
                return "";
            }
            return city;
        }
    }*/


   /* public static String getLocationAddress_State(Context context, double latitude, double
            longitude) {

        String state = "";
        if (latitude == 0.0) {
            return state;

        } else {


            Geocoder geocoder = null;
            try {
                geocoder = new Geocoder(context, Locale.getDefault());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (geocoder == null) {
                return "";
            }

            state = "";

            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

                state = addresses.get(0).getAdminArea();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (TextUtils.isEmpty(state)) {
                return "";
            }
            return state;
        }
    }*/

    public static boolean isConnectedToInternet() {
        {
            ConnectivityManager
                    cm = (ConnectivityManager) MyApplication.getInstance().getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null
                    && activeNetwork.isConnectedOrConnecting();

        }
    }


    public static boolean isJSONValid(String test) {
        if (TextUtils.isEmpty(test)) {
            return false;
        }
        try {
            JSONObject jSONObject = new JSONObject(test);
        } catch (Exception e) {
            return false;

        }
        return true;
    }


    public static String getDeviceModelName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    public static String getUserAgent() {
        return System.getProperty("http.agent");
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        return !Character.isUpperCase(first) ? Character.toUpperCase(first) + s.substring(1) : s;
    }

    @SuppressLint("MissingPermission")
    public static ArrayList<String> getGmailId(Context context) {
        ArrayList<String> accountsList = new ArrayList();
        try {
            if (ContextCompat.checkSelfPermission(context, "android.permission.GET_ACCOUNTS") !=
                    0) {
                return null;
            }
            for (Account account : AccountManager.get(context).getAccountsByType("com.google")) {
                accountsList.add(account.name);
            }
            return accountsList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String getSimOperator(Context context) {
/*
        try {
            if (ContextCompat.checkSelfPermission(context, "android.permission.READ_PHONE_STATE")
                    != 0) {
                return "";
            }
            return ((TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE))
                    .getNetworkOperatorName();
        } catch (Exception e) {
            return "";
        }
*/

        return "empty";
    }

    public static String getPhoneIMEI(Context context) {
      /*  String str_imei = "";
        try {
            if (ContextCompat.checkSelfPermission(context, "android.permission.READ_PHONE_STATE")
                    == 0) {
                //  Log.d("opl", "fired");

                TelephonyInfo telephonyInfo = TelephonyInfo.getInstance(context);
                if (telephonyInfo.isDualSIM()) {
                    str_imei = telephonyInfo.getImsiSIM1() + "," + telephonyInfo.getImsiSIM2();
                    return str_imei;
                } else {
                    str_imei = telephonyInfo.getImsiSIM1();
                    return str_imei;
                }
            } else {
                str_imei = "";
                // Log.d("opl", "not fired");
            }


        } catch (Exception e) {
            return "";
        }
        return str_imei;*/

        return "empty";
    }


    public static void RedirectMe(Context ctx, String url, String openWith, String toolbar_Title) {


        if (!TextUtils.isEmpty(url)) {


            if (!TextUtils.isEmpty(openWith) && openWith.equalsIgnoreCase("0")) {

                if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith
                        ("Http://") && !url.startsWith("Https://")) {
                    url = "http://" + url;
                    OpenInChromeTab(ctx, url);

                } else {
                    OpenInChromeTab(ctx, url);
                }

            } else if (!TextUtils.isEmpty(openWith) && openWith.equalsIgnoreCase("1")) {
                if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith
                        ("Http://") && !url.startsWith("Https://")) {
                    url = "http://" + url;
                    OpenInExternalBrowser(ctx, url);

                } else {
                    OpenInExternalBrowser(ctx, url);
                }
            } else if (!TextUtils.isEmpty(openWith) && openWith.equalsIgnoreCase("2")) {
                if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith
                        ("Http://") && !url.startsWith("Https://")) {
                    url = "http://" + url;
                    OpenInNativeBrowser(ctx, url, toolbar_Title);

                } else {
                    OpenInNativeBrowser(ctx, url, toolbar_Title);
                }
            } else {
                if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith
                        ("Http://") && !url.startsWith("Https://")) {
                    url = "http://" + url;
                    OpenInNativeBrowser(ctx, url, toolbar_Title);

                } else {
                    OpenInNativeBrowser(ctx, url, toolbar_Title);
                }
            }


        } else {
            Toast.makeText(ctx, "Opps, something went wrong! Pls try after some time", Toast
                    .LENGTH_LONG).show();
        }


    }

    public static void OpenInChromeTab(Context ctx, String url) {
        try {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            builder.setToolbarColor(ctx.getResources().getColor(R.color.nubitColor));
            builder.setCloseButtonIcon(BitmapFactory.decodeResource(
                    ctx.getResources(), R.drawable.back_button));
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(ctx, Uri.parse(url));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void OpenInExternalBrowser(Context ctx, String url) {
        try {
            Intent sentToExternalBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            ctx.startActivity(sentToExternalBrowser);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void OpenInNativeBrowser(Context ctx, String url, String toolbar_Title) {

        try {
            ctx.startActivity(NativeWebViewActivity.newInstance(ctx, url, toolbar_Title));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static boolean isPackageInstalled(String packagename, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    public static boolean isAppInstalled(String packageName, Context ctx) {
        PackageManager pm = ctx.getPackageManager();
        boolean isInstalled = isPackageInstalled(packageName, pm);

        return isInstalled;

    }

    public static String userPhoneNumber(Context mcontext) {
       /* try {
            if (ContextCompat.checkSelfPermission(mcontext, "android.permission" +
                    ".READ_PHONE_STATE") != 0) {
                return "";
            }
            return ((TelephonyManager) mcontext.getSystemService(Context.TELEPHONY_SERVICE))
                    .getLine1Number();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }*/

        return "empty";
    }


    public static void OpenAppByPackageName(Context ctx, String packageName) {
        try {
            Intent intent_openApp = ctx.getPackageManager().getLaunchIntentForPackage(packageName);
            ctx.startActivity(intent_openApp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void NoInternet_Msg(Context ctx) {
        Toast toast = Toast.makeText(ctx, "No Internet Connection! Pls Check", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();

    }

    public static void My_share(Context cc, String extraText, String shareType) {

        String shareBody = "";
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        if (!TextUtils.isEmpty(shareType) && shareType.equalsIgnoreCase("news")) {
            shareBody = MyApplication.app_sharedPreferences.getString("news_share", "");
        } else {
            shareBody = MyApplication.app_sharedPreferences.getString("getShare", "");
        }


        String getAppName = MyApplication.app_sharedPreferences.getString("shareBy_brandName", "");
        if (TextUtils.isEmpty(getAppName)) {
            getAppName = "Lava Minus One";
        }


        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getAppName);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, extraText + " \n\n" + shareBody);
        cc.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public static void My_Videoshare(Context cc, String youTube_URL) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = Uri.parse(youTube_URL) + "\n\n" +
                MyApplication.app_sharedPreferences.getString("video_share", "");
        String getAppName = MyApplication.app_sharedPreferences.getString("shareBy_brandName", "");
        if (TextUtils.isEmpty(getAppName)) {
            getAppName = "Lava Minus One";
        }

        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getAppName);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        cc.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    public static String getDeviceID(Context context) {
        String deviceId = "";
        try {
            deviceId = Settings.Secure.getString(context.getContentResolver(), "android_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceId;
    }


    static public void rotateRefreshButton_Anim(Context ctx, ImageView wallpaper_ref) {

        mCurrRotation %= 360;
        float fromRotation = mCurrRotation;
        float toRotation = mCurrRotation += 360;

        final RotateAnimation rotateAnim = new RotateAnimation(
                fromRotation, toRotation, wallpaper_ref.getWidth() / 2, wallpaper_ref.getHeight()
                / 2);

        rotateAnim.setDuration(500); // Use 0 ms to rotate instantly
        rotateAnim.setFillAfter(true); // Must be true or the animation will reset

        wallpaper_ref.startAnimation(rotateAnim);


    }


    static public void scroll_app_RecyclerView(Context app_ctx, RecyclerView app_recycler, int
            app_scrollNumber) {


        app_refresh_number = app_refresh_number + app_scrollNumber;
        app_recycler.scrollToPosition(app_refresh_number);

        LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(app_recycler
                .getLayoutManager());
        int totalItemCount = layoutManager.getItemCount();
        int lastVisible = layoutManager.findLastVisibleItemPosition();

        boolean endHasBeenReached = lastVisible + 5 >= totalItemCount;
        if (totalItemCount > 0 && endHasBeenReached) {
            //you have reached to the bottom of your recycler view
            app_refresh_number = 0;
            app_recycler.smoothScrollToPosition(app_recycler.getAdapter().getItemViewType(0));
        }
    }

    static public void scroll_game_RecyclerView(Context game_ctx, RecyclerView game_recycler, int
            game_scrollNumber) {


        game_refresh_number = game_refresh_number + game_scrollNumber;
        game_recycler.scrollToPosition(game_refresh_number);

        LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(game_recycler
                .getLayoutManager());
        int totalItemCount = layoutManager.getItemCount();
        int lastVisible = layoutManager.findLastVisibleItemPosition();

        boolean endHasBeenReached = lastVisible + 5 >= totalItemCount;
        if (totalItemCount > 0 && endHasBeenReached) {
            //you have reached to the bottom of your recycler view
            game_refresh_number = 0;
            game_recycler.smoothScrollToPosition(game_recycler.getAdapter().getItemViewType(0));
        }
    }

    static public void scroll_externalgame_RecyclerView(Context game_ctx, RecyclerView
            externalGame_recycler, int game_scrollNumber) {


        external_game_refresh_number = external_game_refresh_number + game_scrollNumber;
        externalGame_recycler.scrollToPosition(external_game_refresh_number);

        LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(externalGame_recycler
                .getLayoutManager());
        int totalItemCount = layoutManager.getItemCount();
        int lastVisible = layoutManager.findLastVisibleItemPosition();

        boolean endHasBeenReached = lastVisible + 5 >= totalItemCount;
        if (totalItemCount > 0 && endHasBeenReached) {
            //you have reached to the bottom of your recycler view
            external_game_refresh_number = 0;
            externalGame_recycler.smoothScrollToPosition(externalGame_recycler.getAdapter()
                    .getItemViewType(0));
        }
    }


    static public void scroll_wallpaper_RecyclerView(Context wall_ctx, RecyclerView
            wallpaper_recycler, int wall_scrollNumber) {


        wallpaer_refresh_number = wallpaer_refresh_number + wall_scrollNumber;
        wallpaper_recycler.scrollToPosition(wallpaer_refresh_number);

        LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(wallpaper_recycler
                .getLayoutManager());
        int totalItemCount = layoutManager.getItemCount();
        int lastVisible = layoutManager.findLastVisibleItemPosition();

        boolean endHasBeenReached = lastVisible + 5 >= totalItemCount;
        if (totalItemCount > 0 && endHasBeenReached) {
            //you have reached to the bottom of your recycler view
            wallpaer_refresh_number = 0;
            wallpaper_recycler.smoothScrollToPosition(wallpaper_recycler.getAdapter()
                    .getItemViewType(0));
        }
    }


    public static String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while (itr.hasNext()) {

            String key = itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    public static String getPostDataStringArray(JSONArray params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (int i = 0; i < params.length(); i++) {
            JSONObject jsonObject = params.getJSONObject(i);
            String key = jsonObject.getString("key");
            String value = jsonObject.getString("value");

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value, "UTF-8"));
        }
        return result.toString();
    }


    public static String hitGetVersionAPI(Context ctx, String uRL) {

        String get_FCM_Token = MyApplication.app_sharedPreferences.getString("fcmToken", "");


        try {
            URL url = new URL(uRL);

            JSONObject postDataParams = new JSONObject();
            postDataParams.put("device_id", getDeviceID(ctx));
            postDataParams.put("advertisement_Id", MyApplication.app_sharedPreferences.getString
                    ("getAdvertisementId", ""));
            postDataParams.put("device_model", getDeviceModelName());
            postDataParams.put("operator", getSimOperator(ctx));
            postDataParams.put("device_token", get_FCM_Token);
            postDataParams.put("platform", "Android");

            // Log.d("getVersion",getDeviceID(ctx)+"   "+getDeviceModelName()+"
            // "+getSimOperator(ctx)+"  "+"Android" +"     "+get_FCM_Token);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(55000);
            conn.setConnectTimeout(55000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(MyUtility.getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                // Log.d("getVersion",sb.toString());
                return sb.toString();


            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }

    }


    public static String hitYupTv(Context ctx, String uRL) {


        try {
            URL url = new URL(uRL);

            JSONObject postDataParams = new JSONObject();
            postDataParams.put("device_id", getDeviceID(ctx));
            postDataParams.put("advertisement_Id", MyApplication.app_sharedPreferences.getString
                    ("getAdvertisementId", ""));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(55000);
            conn.setConnectTimeout(55000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(MyUtility.getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                // Log.d("getVersion",sb.toString());
                return sb.toString();


            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }

    }


    public static String hitGetDataAPI(Context ctx, String uRL) {

      /*  List<ApplicationInfo> applist = null;
        try {
            applist = checkForLaunchIntent(ctx.getPackageManager().getInstalledApplications
                    (PackageManager.GET_META_DATA), ctx);
        } catch (Exception e) {
            e.printStackTrace();


        }*/


        try {
            URL url = new URL(uRL);


            String getAdvertisementId = "";
            try {
                getAdvertisementId = MyApplication.app_sharedPreferences.getString
                        ("getAdvertisementId", "");

                if (TextUtils.isEmpty(getAdvertisementId)) {
                    getAdvertisementID(ctx);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            String getLat = MyApplication.app_sharedPreferences.getString("Latitude", "0.0");
            String getLong = MyApplication.app_sharedPreferences.getString("Longitude", "0.0");
            String getLanguage = MyApplication.app_sharedPreferences.getString
                    ("language_selection", "");

            String getAppsJson = "";
           /* try {
                getAppsJson = createUserAppsJson(ctx, applist);
            } catch (Exception e1) {
                e1.printStackTrace();
            }*/


            JSONObject postDataParams = new JSONObject();
            postDataParams.put("device_id", getDeviceID(ctx));
            postDataParams.put("device_model", getDeviceModelName());
            postDataParams.put("language", getLanguage);
            postDataParams.put("city", "");
            postDataParams.put("lat", getLat);
            postDataParams.put("long", getLong);
            postDataParams.put("advertisement_Id", MyApplication.app_sharedPreferences.getString
                    ("getAdvertisementId", ""));
            postDataParams.put("device_apps", "empty");
            postDataParams.put("state", "");
            postDataParams.put("operator", "empty");
            postDataParams.put("email", "empty");
            postDataParams.put("dataconnection","empty" );
            postDataParams.put("phone_number", "empty");
            postDataParams.put("device_imei", "");


            //  Log.d("MyGmail", "Gmail   " + getGmailId(ctx) + "   " + String.valueOf
            // (getPhoneIMEI(ctx)) + getLocationAddress_State(ctx, Double.parseDouble(getLat),
            // Double.parseDouble(getLong)) + getLocationAddress_City(ctx, Double.parseDouble
            // (getLat), Double.parseDouble(getLong)));


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(55000 /* milliseconds */);
            conn.setConnectTimeout(55000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(MyUtility.getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                // Log.d("Ratan", sb.toString());
                return sb.toString();


            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }

    }


    public static String hitGetProfileAPI(Context ctx, String uRL) {
        try {
            URL url = new URL(uRL);

            JSONObject postDataParams = new JSONObject();
            postDataParams.put("device_id", getDeviceID(ctx));
            postDataParams.put("advertisement_Id", MyApplication.app_sharedPreferences.getString
                    ("getAdvertisementId", ""));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(55000 /* milliseconds */);
            conn.setConnectTimeout(55000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(MyUtility.getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();

                return sb.toString();


            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }

    }


    public static String hitGetVistoryAPI(Context ctx, String uRL) {
        try {
            Log.e("checkvistorydata","2>uRL>" + uRL);

            String uReL = uRL + "?direction=ASC&pageNumber=0&pageSize=10";
            Log.e("checkvistorydata","2>uRL 33 >" + uReL);

            URL url = new URL(uReL);

//            JSONObject postDataParams = new JSONObject();
//            postDataParams.put("key", "CATEGORY");
//            postDataParams.put("value", "entertainment");
//            String postData = "[{\"key\":\"CATEGORY\",\"value\":\"entertainment\"}]";
            String postData = "[]";

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(55000 /* milliseconds */);
            conn.setConnectTimeout(55000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            Log.e("checkvistorydata","2>uRL 44 >" + uReL);

            conn.setRequestProperty("DEVICE-TYPE", "Web"); // Example header
            conn.setRequestProperty("VER", "1"); // Example header
            conn.setRequestProperty("Content-Type", "application/json"); // Example header
            Log.e("checkvistorydata","2>uRL 55 >" + uReL);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
//            writer.write(MyUtility.getPostDataString(postDataParams));
            writer.write(postData);

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            Log.e("checkvistorydata","2>uRL 66 >" + postData + "??" + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();

                return sb.toString();


            } else {
                Log.e("checkvistorydata","2>uRL 77 >" + responseCode);

                return "";
            }
        } catch (Exception e) {
            Log.e("checkvistorydata","2>eerre>" + e.getMessage());

            return "";
        }

    }

    public static String hitGetMainAPI(Context ctx, String uRL) {
        try {
            Log.e("hitGetMainAPI","2>uRL>" + uRL);

            String uReL = uRL + "?direction=ASC&pageNumber=0&pageSize=100";
            Log.e("hitGetMainAPI","2>uRL 33 >" + uReL);

            URL url = new URL(uReL);

            JSONArray postDacecetaParams = new JSONArray();
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("key", "STATUS");
            postDataParams.put("value", "1");
            postDacecetaParams.put(postDataParams);

            JSONObject postDataPardams = new JSONObject();
            postDataPardams.put("key", "CATEGORY");
            postDataPardams.put("value", "trending news");
            postDacecetaParams.put(postDataPardams);

//            String postData = "[{\"key\":\"AFTER\",\"value\":\"1\"}," +
            String postData = "[{\"key\":\"STATUS\",\"value\":\"1\"}," + "{\"key\":\"AFTER\",\"value\":\"1\"}," +
                    "{\"key\":\"CATEGORY\",\"value\":\"trending news\"}]";
//            String postData = "[]";

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(55000 /* milliseconds */);
            conn.setConnectTimeout(55000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            Log.e("hitGetMainAPI","2>uRL 44 >" + uReL);

            conn.setRequestProperty("DEVICE-TYPE", "Web"); // Example header
            conn.setRequestProperty("VER", "1"); // Example header
            conn.setRequestProperty("Content-Type", "application/json"); // Example header
            Log.e("hitGetMainAPI","2>uRL 55 >" + uReL);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            Log.e("hitGetMainAPI","2>postDacecetaParams 44 >" + postDacecetaParams);

//            writer.write(MyUtility.getPostDataStringArray(postDacecetaParams));
            writer.write(postData);

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            Log.e("hitGetMainAPI","2>uRL 66 >" + "??" + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();

                return sb.toString();


            } else {
                Log.e("hitGetMainAPI","2>uRL 77 >" + responseCode);

                return "";
            }
        } catch (Exception e) {
            Log.e("hitGetMainAPI","2>eerre>" + e.getMessage());

            return "";
        }

    }

    public static String hitGetVideoApi(Context ctx, String uRL) {
        try {
            Log.e("hitGetMainAPI","2>uRL>" + uRL);

            String uReL = uRL + "?direction=ASC&pageNumber=0&pageSize=100";
            Log.e("hitGetMainAPI","2>uRL 33 >" + uReL);

            URL url = new URL(uReL);

            JSONArray postDacecetaParams = new JSONArray();
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("key", "STATUS");
            postDataParams.put("value", "1");
            postDacecetaParams.put(postDataParams);

            JSONObject postDataPardams = new JSONObject();
            postDataPardams.put("key", "CATEGORY");
            postDataPardams.put("value", "news video");
            postDacecetaParams.put(postDataPardams);

            String postData = "[{\"key\":\"STATUS\",\"value\":\"1\"}," +
                    "{\"key\":\"CATEGORY\",\"value\":\"news video\"}]";
//            String postData = "[]";

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(55000 /* milliseconds */);
            conn.setConnectTimeout(55000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            Log.e("hitGetMainAPI","2>uRL 44 >" + uReL);

            conn.setRequestProperty("DEVICE-TYPE", "Web"); // Example header
            conn.setRequestProperty("VER", "1"); // Example header
            conn.setRequestProperty("Content-Type", "application/json"); // Example header
            Log.e("hitGetMainAPI","2>uRL 55 >" + uReL);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            Log.e("hitGetMainAPI","2>postDacecetaParams 44 >" + postDacecetaParams);

//            writer.write(MyUtility.getPostDataStringArray(postDacecetaParams));
            writer.write(postData);

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            Log.e("hitGetMainAPI","2>uRL 66 >" + "??" + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();

                return sb.toString();


            } else {
                Log.e("hitGetMainAPI","2>uRL 77 >" + responseCode);

                return "";
            }
        } catch (Exception e) {
            Log.e("hitGetMainAPI","2>eerre>" + e.getMessage());

            return "";
        }

    }

    public static String hitGetSportsAPI(Context ctx, String uRL) {
        try {
            Log.e("hitGetMainAPI","2>uRL>" + uRL);

            String uReL = uRL + "?direction=ASC&pageNumber=0&pageSize=100";
            Log.e("hitGetMainAPI","2>uRL 33 >" + uReL);

            URL url = new URL(uReL);

//            String postData = "{[\"key\":\"AFTER\",\"value\":\"1\"}," +
            String postData = "[{\"key\":\"ADS_STATUS\",\"value\":\"0\"}," + "{\"key\":\"AFTER\",\"value\":\"1\"}," +
                    "{\"key\":\"CATEGORY\",\"value\":\"sports news\"}]";
//            String postData = "[]";

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(55000 /* milliseconds */);
            conn.setConnectTimeout(55000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            Log.e("hitGeccetMainAPI","2>uRL postData >" + postData);

            conn.setRequestProperty("DEVICE-TYPE", "Web"); // Example header
            conn.setRequestProperty("VER", "1"); // Example header
            conn.setRequestProperty("Content-Type", "application/json"); // Example header
            Log.e("hitGetMainAPI","2>uRL 55 >" + uReL);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));

//            writer.write(MyUtility.getPostDataStringArray(postDacecetaParams));
            writer.write(postData);

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            Log.e("hitGetMainAPI","2>uRL 66 >" + "??" + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();

                return sb.toString();


            } else {
                Log.e("hitGetMainAPI","2>uRL 77 >" + responseCode);

                return "";
            }
        } catch (Exception e) {
            Log.e("hitGetMainAPI","2>eerre>" + e.getMessage());

            return "";
        }

    }

    public static String hitGetAppsAPI(Context ctx, String uRL) {
        try {
            Log.e("hitGetMainAPI","2>uRL>" + uRL);

            String uReL = uRL + "?direction=ASC&pageNumber=0&pageSize=10&sortingColumn=orderNo";
            Log.e("hitGetMainAPI","2>uRL 33 >" + uReL);

            URL url = new URL(uReL);

            JSONArray postDacecetaParams = new JSONArray();
//            JSONObject postDataParams = new JSONObject();
//            postDataParams.put("key", "STATUS");
//            postDataParams.put("value", "1");
//            postDacecetaParams.put(postDataParams);

            JSONObject postDataPardams = new JSONObject();
            postDataPardams.put("key", "CATEGORY");
            postDataPardams.put("value", "Apps");
            postDacecetaParams.put(postDataPardams);

//            String postData = "[" +
//                    "{\"key\":\"CATEGORY\",\"value\":\"Apps\"}]";
            String postData = "[{\"key\":\"STATUS\",\"value\":\"1\"}]";
//            String postData = [ { "key": "STATUS", "value": "1" }];

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(55000 /* milliseconds */);
            conn.setConnectTimeout(55000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            Log.e("hitGetMainAPI","2>uRL 44 >" + uReL);

            conn.setRequestProperty("DEVICE-TYPE", "Web"); // Example header
            conn.setRequestProperty("VER", "1"); // Example header
            conn.setRequestProperty("Content-Type", "application/json"); // Example header
            Log.e("hitGetMainAPI","2>uRL 55 >" + uReL);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            Log.e("hitGetMainAPI","2>postDacecetaParams 44 >" + postDacecetaParams);

//            writer.write(MyUtility.getPostDataStringArray(postDacecetaParams));
            writer.write(postData);

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            Log.e("hitGetMainAPI","2>uRL 66 >" + "??" + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();

                return sb.toString();


            } else {
                Log.e("hitGetMainAPI","2>uRL 77 >" + responseCode);

                return "";
            }
        } catch (Exception e) {
            Log.e("hitGetMainAPI","2>eerre>" + e.getMessage());

            return "";
        }

    }

    public static String hitGetBannersAPI(Context ctx, String uRL) {
        try {
            Log.e("hitGetBannersAPI","2>uRL>" + uRL);

            String uReL = uRL + "?direction=ASC&pageNumber=0&pageSize=20";
            Log.e("hitGetBannersAPI","2>uRL 33 >" + uReL);

            URL url = new URL(uReL);

            JSONArray postDacecetaParams = new JSONArray();
            JSONObject postDataPardams = new JSONObject();
            postDataPardams.put("key", "CATEGORY");
            postDataPardams.put("value", "Apps");
            postDacecetaParams.put(postDataPardams);

//            String postData = "[" +
//                    "{\"key\":\"CATEGORY\",\"value\":\"Apps\"}]";
            String postData = "[{\"key\":\"STATUS\",\"value\":\"1\"}," +  "{\"key\":\"CATEGORY\",\"value\":\"banner1\"}]";
//            String postData = [ { "key": "STATUS", "value": "1" }];

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(55000 /* milliseconds */);
            conn.setConnectTimeout(55000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            Log.e("hitGetBannersAPI","2>uRL 44 >" + uReL);

            conn.setRequestProperty("DEVICE-TYPE", "Web"); // Example header
            conn.setRequestProperty("VER", "1"); // Example header
            conn.setRequestProperty("Content-Type", "application/json"); // Example header
            Log.e("hitGetBannersAPI","2>uRL 55 >" + uReL);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            Log.e("hitGetBannersAPI","2>postDacecetaParams 44 >" + postDacecetaParams);

//            writer.write(MyUtility.getPostDataStringArray(postDacecetaParams));
            writer.write(postData);

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            Log.e("hitGetBannersAPI","2>uRL 66 >" + "??" + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();

                return sb.toString();


            } else {
                Log.e("hitGetBannersAPI","2>uRL 77 >" + responseCode);

                return "";
            }
        } catch (Exception e) {
            Log.e("hitGetBannersAPI","2>eerre>" + e.getMessage());

            return "";
        }

    }

    public static String getModuleOrderApi(Context ctx, String uRL) {
        try {
            Log.e("getModuleOrderApi","2>uRL>" + uRL);

            String uReL = uRL + "?direction=ASC&pageNumber=0&pageSize=10&sortingColumn=placementOrder";
            Log.e("getModuleOrderApi","2>uRL 33 >" + uReL);

            URL url = new URL(uReL);

//            JSONObject postDataParams = new JSONObject();
//            postDataParams.put("key", "CATEGORY");
//            postDataParams.put("value", "entertainment");
//            String postData = "[{\"key\":\"CATEGORY\",\"value\":\"entertainment\"}]";
            String postData = "[]";

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(55000 /* milliseconds */);
            conn.setConnectTimeout(55000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            Log.e("getModuleOrderApi","2>uRL 44 >" + uReL);

            conn.setRequestProperty("DEVICE-TYPE", "Web"); // Example header
            conn.setRequestProperty("VER", "1"); // Example header
            conn.setRequestProperty("Content-Type", "application/json"); // Example header
            Log.e("getModuleOrderApi","2>uRL 55 >" + uReL);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
//            writer.write(MyUtility.getPostDataString(postDataParams));
            writer.write(postData);

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            Log.e("getModuleOrderApi","2>uRL 66 >" + postData + "??" + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();

                return sb.toString();


            } else {
                Log.e("getModuleOrderApi","2>uRL 77 >" + responseCode);

                return "";
            }
        } catch (Exception e) {
            Log.e("getModuleOrderApi","2>eerre>" + e.getMessage());

            return "";
        }

    }

    public static String getRequest(Context ctx, String uRL, double lat, double lng) {
        try {
            String uReL = uRL + "?lat="+lat+"&lng="+lng;
            // Create URL object
            URL url = new URL(uReL);

            // Create HttpURLConnection object
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method
            connection.setRequestMethod("GET");

            // Set timeouts
            connection.setConnectTimeout(5000); // 5 seconds
            connection.setReadTimeout(5000); // 5 seconds
            Log.e("checkweatherdaeffta","2>uRL 11 >" + uReL);

            // Get the response code
            int responseCode = connection.getResponseCode();
            Log.e("checkweatherdaeffta","2>uRL 22 >" + responseCode);

            // Check if response code is OK
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Create BufferedReader to read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                // Read response line by line
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Return response as string
                return response.toString();
            } else {
                // If response code is not OK, return empty string or handle the error as needed
                return "";
            }
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            return "";
        }
    }
    public static String hitGetWeatherAPI(Context ctx, String uRL, double lat, double lng) throws IOException {
        try {
//            String uReL = uRL;
            String uReL = uRL + "?lat="+lat+"&lng="+lng;
            String postData = "[]";

            URL url = new URL(uReL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(55000 /* milliseconds */);
            conn.setConnectTimeout(55000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestProperty("DEVICE-TYPE", "Web"); // Example header
            conn.setRequestProperty("VER", "1"); // Example header
            conn.setRequestProperty("Content-Type", "application/json"); // Example header
            Log.e("checkweatherdata","2>uRL 55 >" + uReL);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
//            writer.write(MyUtility.getPostDataString(postDataParams));
            writer.write(postData);

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            Log.e("checkweatherdata","2>uRL 66 >" + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();

                return sb.toString();


            } else {
                Log.e("checkweatherdata","2>uRL 77 >" + responseCode);

                return "";
            }
        } catch (Exception e) {
            Log.e("checkweatherdata","2>eerre>" + e.getMessage());

            return "";
        }
    }


    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

//    public static String getNetworkTypeMethod2(Context ccc) {
//
//        TelephonyManager mTelephonyManager = (TelephonyManager)
//                ccc.getSystemService(Context.TELEPHONY_SERVICE);
//        int networkType = mTelephonyManager.getNetworkType();
//        switch (networkType) {
//            case TelephonyManager.NETWORK_TYPE_GPRS:
//            case TelephonyManager.NETWORK_TYPE_EDGE:
//            case TelephonyManager.NETWORK_TYPE_CDMA:
//            case TelephonyManager.NETWORK_TYPE_1xRTT:
//            case TelephonyManager.NETWORK_TYPE_IDEN:
//                return "2G";
//            case TelephonyManager.NETWORK_TYPE_UMTS:
//            case TelephonyManager.NETWORK_TYPE_EVDO_0:
//            case TelephonyManager.NETWORK_TYPE_EVDO_A:
//            case TelephonyManager.NETWORK_TYPE_HSDPA:
//            case TelephonyManager.NETWORK_TYPE_HSUPA:
//            case TelephonyManager.NETWORK_TYPE_HSPA:
//            case TelephonyManager.NETWORK_TYPE_EVDO_B:
//            case TelephonyManager.NETWORK_TYPE_EHRPD:
//            case TelephonyManager.NETWORK_TYPE_HSPAP:
//                return "3G";
//            case TelephonyManager.NETWORK_TYPE_LTE:
//                return "4G";
//            default:
//                return "Unknown";
//        }
//    }


    public static void handleItemClick(Context mContext, String packageName, String
            redirectionURL, String sourceURL, String sectionName, String openWith, String
                                               clickItemTitle) {

        if (MyUtility.isAppInstalled(packageName, mContext)) {
            MyUtility.OpenAppByPackageName(mContext, packageName);
            MyUtility.saveTracksInDB(mContext, sourceURL, redirectionURL, sectionName, "0",
                    clickItemTitle, "");


        } else {
            MyUtility.saveTracksInDB(mContext, sourceURL, redirectionURL, sectionName, "1",
                    clickItemTitle, "");
            if (MyUtility.isConnectedToInternet()) {
                MyUtility.RedirectMe(mContext, redirectionURL, openWith, clickItemTitle);


            } else {
                MyUtility.NoInternet_Msg(mContext);
            }
        }
    }


    public static void handleNewsItemClick(Context mContext, String redirectionURL, String
            openWith) {


        if (MyUtility.isConnectedToInternet()) {
          /*  MyUtility.saveTracksInDB(mContext, redirectionURL, redirectionURL, sectionName,
          "1");*/
            MyUtility.RedirectMe(mContext, redirectionURL, openWith, "Nubit");


        } else {
            MyUtility.NoInternet_Msg(mContext);


        }


    }

    public static String requestWebService(String requestURL, HashMap<String, String>
            postDataParams) {

        String response = "";
        URL url;

        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(60000);
            conn.setConnectTimeout(60000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("ClientVersion", "" + 1);
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream
                        ()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private static String getPostDataString(HashMap<String, String> params) throws
            UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            if (TextUtils.isEmpty(entry.getValue())) {
                result.append(URLEncoder.encode("", "UTF-8"));
            } else {
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }


        }
        return result.toString();
    }


    public static boolean checkDeviceforMarshmallow() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            return true;
        } else {
            return false;
        }
    }


    public static int setViewHeightSec(Context cc) {

        int screentWidth = MyUtility.getScreenWidth(cc);
        switch (screentWidth) {

            case 480:
                CONTAINER_HEIGHT = 215;
                break;
            case 720:
                CONTAINER_HEIGHT = 359;
                break;
            case 1080:
                CONTAINER_HEIGHT = 535; //535
                break;
            case 1440:
                CONTAINER_HEIGHT = 695;
                break;
            default:
                CONTAINER_HEIGHT = 359;
                break;

        }
        return CONTAINER_HEIGHT;
    }


    public static int getScreenWidth(Context con) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) con.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }
        return screenWidth;
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


    public static final int NEWS_CONTAINER_WIDTH = (int) (MyUtility.getScreenWidth(MyApplication
            .ctx) * 0.8);
    public static final int NEWS_IMAGE_HEIGHT = (int) (MyUtility.getScreenHeight(MyApplication
            .ctx) * 0.38);

    public static int getScreenHeight(Context c) {

        int screenHeight = 0;
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }
        return screenHeight;
    }

    public static void playYouTubeVideo(Context ctx, String youTubeID, String videoUrl, String
            videoType, String videoTitle) {
        if (MyUtility.isConnectedToInternet()) {

            Intent send_To_YouTubeActivity = new Intent(ctx, YouTube_PlayerActivity.class);
            send_To_YouTubeActivity.putExtra("get_YouTubeID", youTubeID);
            send_To_YouTubeActivity.putExtra("videoUrl", videoUrl);
            send_To_YouTubeActivity.putExtra("videoType", videoType);
            send_To_YouTubeActivity.putExtra("videoTitle", videoTitle);
            ctx.startActivity(send_To_YouTubeActivity);

        } else {
            MyUtility.NoInternet_Msg(ctx);
        }

    }

    public static void handleWhiteSpace_atBottom(Activity context, View view) {

        if (hasNavBar(context.getResources())) {
            view.getLayoutParams().height = getSoftButtonsBarHeight(context);
        } else {
            view.getLayoutParams().height = 0;
        }
    }

    public static boolean hasNavBar(Resources resources) {
        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        return id > 0 && resources.getBoolean(id);
    }

    public static int getSoftButtonsBarHeight(Activity mActivity) {
// getRealMetrics is only available with API 17 and +
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int usableHeight = metrics.heightPixels;
            mActivity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
            int realHeight = metrics.heightPixels;
            if (realHeight > usableHeight)
                return realHeight - usableHeight;
            else
                return 0;
        }
        return 0;
    }

    public static boolean checkBlueToothState() {
        boolean bt_state = false;
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter != null) {
            @SuppressLint("MissingPermission") boolean isEnabled = bluetoothAdapter.isEnabled();
            if (isEnabled) {
                bt_state = true;

            } else {
                bt_state = false;
            }
        }
        return bt_state;
    }


    static float preX = 0;
    static float preY = 0;

    public static boolean handleTouchEvent(MotionEvent ev, View view) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            preX = ev.getX();
            preY = ev.getY();
            return false;
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (Math.abs((preX - ev.getX())) > 8 || (Math.abs((preY - ev.getY())) > 8)) {
                preX = preY = 0;
                return false;
            } else {
                preX = preY = 0;
                return checkForClickEvent(ev, view);
            }

        }
        return false;
    }


    public static String mobin_View = makeSecure
            ("aHR0cDovL3d3dy5tb2JpYXBwZGV2ZWxvcGVyLmNvbS9BUEkvYXBpL3JrdF92aWV3X0wvMDk4NzY1NDMyMQ" +
                    "==");

    public static boolean checkForClickEvent(MotionEvent ev, View view) {
        if ((ev.getY() > view.getY() && ev.getY() < (view.getHeight() + view.getY())) && (ev.getX
                () > view.getX() && ev.getX() < (view.getX() + view.getWidth()))) {
            return true;
        } else {
            return false;
        }

    }


    public static boolean isUpdateRequired(Context context, String isFullScreenBanner) {

        try {


            if (!TextUtils.isEmpty(isFullScreenBanner) && isFullScreenBanner.equalsIgnoreCase("0")
                    || isFullScreenBanner.equalsIgnoreCase("1")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }


    public static String doHttpUrlConnectionAction(String desiredUrl)
            throws Exception {
        URL url;
        BufferedReader reader = null;
        StringBuilder stringBuilder;

        try {
            // create the HttpURLConnection
            url = new URL(desiredUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(30 * 1000);
            connection.connect();

            // read the output from the server
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),
                    "UTF-8"));
            stringBuilder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }


    public static String railResponceCode(String railQueryResponce) {


        if (TextUtils.isEmpty(railQueryResponce)) {
            railQueryResponce = "Value not found in database";
        } else if (!TextUtils.isEmpty(railQueryResponce) && railQueryResponce.equalsIgnoreCase
                ("200")) {
            railQueryResponce = "Success";
        } else if (!TextUtils.isEmpty(railQueryResponce) && railQueryResponce.equalsIgnoreCase
                ("210")) {
            railQueryResponce = "Train doesn’t run on the date queried.";
        } else if (!TextUtils.isEmpty(railQueryResponce) && railQueryResponce.equalsIgnoreCase
                ("211")) {
            railQueryResponce = "Train doesn’t have journey class queried.";
        } else if (!TextUtils.isEmpty(railQueryResponce) && railQueryResponce.equalsIgnoreCase
                ("220")) {
            railQueryResponce = "Flushed PNR.";
        } else if (!TextUtils.isEmpty(railQueryResponce) && railQueryResponce.equalsIgnoreCase
                ("221")) {
            railQueryResponce = "Invalid PNR.";
        } else if (!TextUtils.isEmpty(railQueryResponce) && railQueryResponce.equalsIgnoreCase
                ("304")) {
            railQueryResponce = "Data couldn’t be fetched. No Data available.";
        } else if (!TextUtils.isEmpty(railQueryResponce) && railQueryResponce.equalsIgnoreCase
                ("404")) {
            railQueryResponce = "Data couldn’t be fetched. Request couldn’t go through.";
        } else if (!TextUtils.isEmpty(railQueryResponce) && railQueryResponce.equalsIgnoreCase
                ("504")) {
            railQueryResponce = "You doing some thing wrong,Pls check and try again.";
        } else if (!TextUtils.isEmpty(railQueryResponce) && railQueryResponce.equalsIgnoreCase
                ("704")) {
            railQueryResponce = "Unauthorized user query. User account expired/exhausted or " +
                    "unregistered. Please write a email to admin at mynubit@gmail.com";
        } else if (railQueryResponce.equalsIgnoreCase("Train doesn’t run on the date queried.")
                || railQueryResponce.equalsIgnoreCase("Train doesn’t have journey class queried.")
                || railQueryResponce.equalsIgnoreCase("Flushed PNR.") || railQueryResponce
                .equalsIgnoreCase("Invalid PNR.") || railQueryResponce.equalsIgnoreCase("Data " +
                "couldn’t be fetched. No Data available.")
                || railQueryResponce.equalsIgnoreCase("Data couldn’t be fetched. Request couldn’t" +
                " go through.") || railQueryResponce.equalsIgnoreCase("You doing some thing " +
                "wrong,Pls check and try again.")
                || railQueryResponce.equalsIgnoreCase("Unauthorized user query. User account " +
                "expired/exhausted or unregistered. Please write a email to admin at " +
                "mynubit@gmail.com")) {
            railQueryResponce = "No Data found for you search, Pls double check your inputs!";
        }


        return railQueryResponce;
    }

    public static String getCountryName(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String get_country = "";
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            Address result;

            if (addresses != null && !addresses.isEmpty()) {
                get_country = addresses.get(0).getCountryName();
            }
        } catch (Exception ignored) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context
                    .TELEPHONY_SERVICE);
            get_country = tm.getNetworkCountryIso();

        }
        return get_country;
    }


    public static String hitLikeDisAPI(Context ctx, String uRL, JsonArray like_dislike) {
        try {
            URL url = new URL(uRL);

            JSONObject postDataParams = new JSONObject();


            postDataParams.put("like_dislike", like_dislike);
            postDataParams.put("device_id", getDeviceID(ctx));
            postDataParams.put("advertisement_Id", MyApplication.app_sharedPreferences.getString
                    ("getAdvertisementId", ""));

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(55000 /* milliseconds */);
            conn.setConnectTimeout(55000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(MyUtility.getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();
                // Log.d("Ratan", sb.toString());
                return sb.toString();


            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }

    }


    public static String getCurrentTime() {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
        String formattedDate = df.format(cal.getTime());


        return formattedDate;
    }

    public static String getCurrentTime_Irctc() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy");
        String formattedDate = df.format(cal.getTime());
        return formattedDate;
    }

    public static long getTimeDifference() {
        String get_WeatherOldTime = MyApplication.app_sharedPreferences.getString
                ("last_weatherUpdate", "");
        long diffMinutes = 0;
        if (!TextUtils.isEmpty(get_WeatherOldTime)) {
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
            try {
                d1 = format.parse(get_WeatherOldTime);
                d2 = format.parse(getCurrentTime());
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            long diff = d2.getTime() - d1.getTime();
            diffMinutes = diff / (60 * 1000);


        }
        return diffMinutes;
    }


    public static long getTimeDifference_nubit(String strValue) {
        String get_time_forBanner = "";
        if (strValue.equalsIgnoreCase("fullBanner")) {
            get_time_forBanner = MyApplication.app_sharedPreferences.getString
                    ("last_bannerShow", "");
        }
        if (strValue.equalsIgnoreCase("rktView")) {
            get_time_forBanner = MyApplication.app_sharedPreferences.getString
                    ("last_rktViewShow", "");
        }
        if (strValue.equalsIgnoreCase("rktInterstitial")) {
            get_time_forBanner = MyApplication.app_sharedPreferences.getString
                    ("last_Inter_BannerShow", "");
        }


        long diffMinutes = 0;
        if (!TextUtils.isEmpty(get_time_forBanner)) {
            Date d1 = null;
            Date d2 = null;
            SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
            try {
                d1 = format.parse(get_time_forBanner);
                d2 = format.parse(getCurrentTime());
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                long diff = d2.getTime() - d1.getTime();
                diffMinutes = diff / (60 * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return diffMinutes;
    }


    public static void init_Interstitial(Context interContext, String interstitial_id) {
        final InterstitialAd mInterstitialAd = new InterstitialAd(interContext);
        try {
            mInterstitialAd.setAdUnitId(interstitial_id);
            AdRequest adRequest = new AdRequest.Builder()
                    .build();
            mInterstitialAd.loadAd(adRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Create an Intent that will start the main activity.

            }

            @Override
            public void onAdLoaded() {

                try {
                    mInterstitialAd.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.

            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.

            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.

            }

        });

    }


    public static void rktInterstitial_setUP(Context cccc) {

        String interstitial_id = MyApplication.app_sharedPreferences.getString
                ("interstitial_id", "");

        String interstitial_model = MyApplication.app_sharedPreferences.getString
                ("interstitial_model", "");


        if (!TextUtils.isEmpty(interstitial_id)) {
            String lastInter_BannerShow = MyApplication.app_sharedPreferences.getString
                    ("last_Inter_BannerShow", "");

            String interstitial_duration = MyApplication.app_sharedPreferences.getString
                    ("interstitial_duration", "");

            if (!TextUtils.isEmpty(interstitial_model)) {
                if ((interstitial_model.toLowerCase()).contains(((MyUtility.getDeviceModelName())
                        .toLowerCase()))) {
                    if (TextUtils.isEmpty(lastInter_BannerShow)) {
                        MyApplication.app_editor.putString("last_Inter_BannerShow", MyUtility
                                .getCurrentTime())
                                .apply();
                        init_Interstitial(cccc, interstitial_id);
                    } else if (!TextUtils.isEmpty(lastInter_BannerShow) && TextUtils.isEmpty
                            (interstitial_duration)) {
                        MyApplication.app_editor.putString("last_Inter_BannerShow", MyUtility
                                .getCurrentTime())
                                .apply();
                        init_Interstitial(cccc, interstitial_id);
                    } else if (!TextUtils.isEmpty(interstitial_duration)) {
                        if ((MyUtility.getTimeDifference_nubit("rktInterstitial")) > (Long.parseLong
                                (interstitial_duration))) {
                            MyApplication.app_editor.putString("last_Inter_BannerShow", MyUtility
                                    .getCurrentTime())
                                    .apply();
                            init_Interstitial(cccc, interstitial_id);
                        }
                    }

                }
            } else if (TextUtils.isEmpty(interstitial_model)) {

                if (TextUtils.isEmpty(lastInter_BannerShow)) {
                    MyApplication.app_editor.putString("last_Inter_BannerShow", MyUtility
                            .getCurrentTime())
                            .apply();
                    init_Interstitial(cccc, interstitial_id);
                } else if (!TextUtils.isEmpty(lastInter_BannerShow) && TextUtils.isEmpty
                        (interstitial_duration)) {
                    MyApplication.app_editor.putString("last_Inter_BannerShow", MyUtility
                            .getCurrentTime())
                            .apply();
                    init_Interstitial(cccc, interstitial_id);
                } else if (!TextUtils.isEmpty(interstitial_duration)) {
                    if ((MyUtility.getTimeDifference_nubit("rktInterstitial")) > (Long.parseLong
                            (interstitial_duration))) {
                        MyApplication.app_editor.putString("last_Inter_BannerShow", MyUtility
                                .getCurrentTime())
                                .apply();
                        init_Interstitial(cccc, interstitial_id);
                    }
                }

            }


        }

    }


    public static boolean compare_Dates(String date1, String date2) {
        boolean date_status = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
            Date date_1 = sdf.parse(date1);
            Date date_2 = sdf.parse(date2);

            if (date_1.after(date_2)) {
                date_status = false;
            }

            if (date_1.before(date_2)) {
                date_status = true;
            }

            if (date_1.equals(date_2)) {
                date_status = true;
            }


        } catch (Exception e) {
            e.printStackTrace();
            date_status = false;
        }
        return date_status;
    }


    public static boolean setShowHide_Container(String get_ConName) {
        String get_old_hidden = MyApplication.app_sharedPreferences.getString
                ("hidden_cont", "");

        if (!TextUtils.isEmpty(get_old_hidden) && !TextUtils.isEmpty(get_ConName)) {
            if (get_old_hidden.contains(get_ConName)) {
                return true;
            } else {
                return false;
            }


        }

        return false;
    }

    public static String createUserAppsJson(Context cc, List<ApplicationInfo> applist) {

        JSONArray jsonArray_apps = new JSONArray();
        try {
            for (int i = 0; i < applist.size(); i++) {
                JSONObject jsonObject_apps = new JSONObject();
                ApplicationInfo applicationInfo = null;
                try {
                    applicationInfo = applist.get(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    jsonObject_apps.put("id", i);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                try {
                    jsonObject_apps.put("app_name", applicationInfo.loadLabel(cc
                            .getPackageManager())
                            .toString());
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                try {
                    jsonObject_apps.put("app_package", applicationInfo.packageName);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                jsonArray_apps.put(jsonObject_apps);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonArray_apps.toString();
    }


    private static List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list, Context
            ccc) {
        ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        try {
            for (ApplicationInfo info : list) {
                try {
                    if (null != ccc.getPackageManager().getLaunchIntentForPackage(info
                            .packageName)) {
                        applist.add(info);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return applist;
    }

    public static String getDayDate() {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat date_formate = new SimpleDateFormat("MMMM", Locale.getDefault());
            String month_name = date_formate.format(cal.getTime());
            String today_date = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
            String dayName = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale
                    .getDefault());
            return dayName + ", " + today_date + " " + month_name;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static void Glide_share(Context cc, Bitmap bitmap) {

        String shareBody = MyApplication.app_sharedPreferences.getString
                ("wallpaper_share", "");
        Uri uri = getImageUri(cc, bitmap);
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String getAppName = MyApplication.app_sharedPreferences.getString("shareBy_brandName", "");
        if (TextUtils.isEmpty(getAppName)) {
            getAppName = "Lava Minus One";
        }

        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getAppName);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        cc.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public static Uri getImageUri(Context ccc, Bitmap inImage) {
        try {
            Uri bmpUri = null;
            try {
                // This way, you don't need to request external read/write permission.
                File file = new File(ccc.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                        "share_image_" + System.currentTimeMillis() + ".png");
                FileOutputStream out = new FileOutputStream(file);
                inImage.compress(Bitmap.CompressFormat.PNG, 80, out);
                out.close();
                bmpUri = Uri.fromFile(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bmpUri;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static String makeSecure(String coded) {
        byte[] valueDecoded = new byte[0];
        try {
            valueDecoded = Base64.decode(coded.getBytes("UTF-8"), Base64.DEFAULT);
        } catch (Exception e) {
        }
        return new String(valueDecoded);
    }

    public static void getAdvertisementID(final Context cc) {


        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                AdvertisingIdClient.Info idInfo = null;
                try {
                    idInfo = AdvertisingIdClient.getAdvertisingIdInfo(cc);
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String advertId = "";
                try {
                    advertId = idInfo.getId();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return advertId;
            }

            @Override
            protected void onPostExecute(String advertId) {
                try {
                    if (!TextUtils.isEmpty(advertId)) {
                        MyApplication.app_editor.putString("getAdvertisementId", advertId).apply();
                    } else {
                        MyApplication.app_editor.putString("getAdvertisementId", "").apply();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
        task.execute();

    }


    /*public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(getPackageName(),
            PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("rkt_keyhash", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("rkt_keyhash", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("rkt_keyhash", "printHashKey()", e);
        }
    }*/


    public static String hitrktViewAPI(Context ctx, String uRL) {
        try {
            URL url = new URL(uRL);

            String getLat = MyApplication.app_sharedPreferences.getString("Latitude", "0.0");
            String getLong = MyApplication.app_sharedPreferences.getString("Longitude", "0.0");


            JSONObject postDataParams = new JSONObject();

            postDataParams.put("lat", getLat);
            postDataParams.put("long", getLong);
            postDataParams.put("device_id", getDeviceID(ctx));

            /*  postDataParams.put("device_imei", getPhoneIMEI(ctx));*/
            postDataParams.put("device_model", getDeviceModelName());
            postDataParams.put("operator", getSimOperator(ctx));


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(55000 /* milliseconds */);
            conn.setConnectTimeout(55000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(MyUtility.getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();

                return sb.toString();


            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        }

    }

    public static String getLocationAddress_State(Context context, double latitude, double
            longitude) {

        String state = "";
        if (latitude == 0.0) {
            return state;

        } else {


            Geocoder geocoder = null;
            try {
                geocoder = new Geocoder(context, Locale.getDefault());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (geocoder == null) {
                return "";
            }

            state = "";

            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

                state = addresses.get(0).getAdminArea();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (TextUtils.isEmpty(state)) {
                return "";
            }
            return state;
        }
    }

    public static void show_Progress_Popup(Context context) {
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setCancelable(true);
        pd.setCanceledOnTouchOutside(true);
        pd.setMessage("Pls wait sharing........");
        try {
            pd.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    pd.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000);


    }

    public static void setUpBannerAd(Context context, String bannerPlacementId, final
    RelativeLayout relativeLayout, String getAdType, final ImageView ad_cross, final String
                                             get_iscross_player) {
        final AdView adView = new AdView(context);
        adView.setAdUnitId(bannerPlacementId);
        if (getAdType.equalsIgnoreCase("MEDIUM_RECTANGLE")) {
            adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        } else if (getAdType.equalsIgnoreCase("BANNER")) {
            adView.setAdSize(AdSize.BANNER);
        } else if (getAdType.equalsIgnoreCase("SMART_BANNER")) {
            adView.setAdSize(AdSize.SMART_BANNER);
        } else if (getAdType.equalsIgnoreCase("LARGE_BANNER")) {
            adView.setAdSize(AdSize.LARGE_BANNER);
        } else if (getAdType.equalsIgnoreCase("LEADERBOARD")) {
            adView.setAdSize(AdSize.LEADERBOARD);
        } else {
            adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
        }

        try {
            adView.setId(R.id.adViews);
            adView.loadAd(new AdRequest.Builder().build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        relativeLayout.setVisibility(View.VISIBLE);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                /*  System.out.println("Ads " + " onAdFailedToLoad " + i);*/
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                /* System.out.println("Ads" + " onAdOpened");*/
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                try {
                    if (adView.getParent() == null) {
                        relativeLayout.addView(adView);


                    } else {
                        ((ViewGroup) adView.getParent()).removeView(adView);

                        relativeLayout.addView(adView);


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (TextUtils.isEmpty(get_iscross_player) && get_iscross_player.equalsIgnoreCase
                        ("1")) {
                    ad_cross.setVisibility(View.VISIBLE);

                }
            }
        });

    }


    public static void muteDeviceAudio(AudioManager mAudioManager) {


        try {

            mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_MUTE, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void UnmuteDeviceAudio(AudioManager mAudioManager) {


        try {

            mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                    AudioManager.ADJUST_UNMUTE, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

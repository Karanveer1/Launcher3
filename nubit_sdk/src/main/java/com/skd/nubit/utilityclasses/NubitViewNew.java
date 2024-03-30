package com.skd.nubit.utilityclasses;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.SearchManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;

import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.skd.nubit.activities.VideoFragment;
import com.skd.nubit.adapters.AppGridAdapter;
import com.skd.nubit.adapters.NewBannerAdapter;
import com.skd.nubit.adapters.RecyclerViewAdapter;
import com.skd.nubit.adapters.VideoAdapter;
import com.skd.nubit.adapters.VideoPagerDynamicAdapter;
import com.skd.nubit.adapters.ViewPagerAdapterMain;
import com.skd.nubit.activities.WebViewFragment;
import com.skd.nubit.apicalls.CommonResponse;
import com.skd.nubit.apicalls.PushTracksOnServerApiTask;
import com.skd.nubit.apicalls.TaskHelper;
import com.skd.nubit.R;
import com.skd.nubit.models.AppsModel;
import com.skd.nubit.models.BannersModel;
import com.skd.nubit.models.MediaObject;
import com.skd.nubit.models.NewModelTest;
import com.skd.nubit.models.OrderModel;
import com.skd.nubit.models.TrendingNewsModel;
import com.skd.nubit.models.VistoryModel;
import com.skd.nubit.models.youTubeVideos;
import com.skd.nubit.videoplayerstuffs.RktVideoView;
import com.skd.nubit.activities.Language_Set_Activity;
import com.skd.nubit.activities.ProfileActivity;
import com.skd.nubit.adapters.Bottom_Adv_Adapter;
import com.skd.nubit.adapters.Bottom_News_Adapter;
import com.skd.nubit.adapters.Entertainment_Adapter;
import com.skd.nubit.adapters.Floater_Category_Adapter;
import com.skd.nubit.adapters.GamesAdapter;
import com.skd.nubit.adapters.Grid_Floater_Sub_CatAdapter;
import com.skd.nubit.adapters.Grid_NewsPub;
import com.skd.nubit.adapters.Grid_VideoCat;
import com.skd.nubit.adapters.Middle_News_Adapter;
import com.skd.nubit.adapters.NewsByPub_Adapter;
import com.skd.nubit.adapters.Service_Adapter;
import com.skd.nubit.adapters.TopAppsAdapter;
import com.skd.nubit.adapters.TopBanner_Adapter;
import com.skd.nubit.adapters.Top_News_Adapter;
import com.skd.nubit.adapters.Wallpaper_Adapter;
import com.skd.nubit.database.DbHandler;
import com.skd.nubit.database.TrackingDAO;
import com.skd.nubit.models.Admob_nubitPojo;
import com.skd.nubit.models.Bottom_News_Pojo;
import com.skd.nubit.models.Bottom_adv_Pojo;
import com.skd.nubit.models.Configuration;
import com.skd.nubit.models.Data;
import com.skd.nubit.models.Entertainment_pojo;
import com.skd.nubit.models.External_game_pojo;
import com.skd.nubit.models.Floater_Category_Pojo;
import com.skd.nubit.models.Floater_sub_Pojo;
import com.skd.nubit.models.GamesPojo;
import com.skd.nubit.models.HomeVideoPojo;
import com.skd.nubit.models.Middle_News_Pojo;
import com.skd.nubit.models.NavigationPojo;
import com.skd.nubit.models.NewPubPojo;
import com.skd.nubit.models.NewsByPub_Pojo;
import com.skd.nubit.models.OtherServicesPojo;
import com.skd.nubit.models.Response;
import com.skd.nubit.models.Services_pojo;
import com.skd.nubit.models.TITLES_IN_DIFF_LANG;
import com.skd.nubit.models.Titles;
import com.skd.nubit.models.TopApps_Pojo;
import com.skd.nubit.models.Top_News_Pojo;
import com.skd.nubit.models.TrackingPojo;
import com.skd.nubit.models.VideoCat;
import com.skd.nubit.models.Wallpaper_Pojo;
import com.skd.nubit.mycallbacks.LanguageUpdaterCallBack;
import com.skd.nubit.mycallbacks.PagersAutoScrollControllerCallback;
import com.skd.nubit.mycallbacks.RefreshDataCallBack;
//import com.taboola.android.TaboolaWidget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;
import java.util.regex.Pattern;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;


class NubitViewNew extends FrameLayout implements View.OnClickListener,
        PagersAutoScrollControllerCallback
                .UpdateScroll, LanguageUpdaterCallBack.LanguageChangeCallback {
    boolean post_delay_running = false;


    String scrollStatus = "home";
    String title = "";
    int selectedVideoIndex = 0;
    PushTracksOnServerApiTask pushTracksOnServerApiTask;


    RecyclerView entertain_recyclerView, wallpaper_recyclerView, external_games_recyclerView,
            bottomNews_recyclerView, middleNews_recyclerView, bottomAdv_recyclerView,
            games_recyclerView,
            topapps_recyclerView, top_News_recyclerView, top_News_recyclerVie_test, floater_recyclerView,
            other_ser_recyclerView;

    View devider_below_btnService, devider_below_recommended;
    Response response;
    ArrayList<VistoryModel> vistoryResponse;
    AutoScrollViewPager home_topBanner_Pager;
    Button btn_add_pub;
    TextView btn_service_title, btn_bottom_news_title, title_middle_news,
            btn_news_title, btn_freq_apps,
            btn_topapps_title, title_recommended_news,
            btn_games_title, btn_wallpaper_title, btn_externalGames_title;

    RktVideoView mVideoView;


    CircleIndicator topBanner_indicator;

    GridView service_grid;
//    private TaboolaWidget taboolaWidget;

    public DrawerLayout drawerLayout;
    public FloatingActionButton nubit_floater;

    FrameLayout parent_taboola;

    public EditText edt_search;
    LinearLayout entertainment_parent, topapps_parent,
            games_parent, wallpaper_parent, bottom_adv_parent, middle_news_parent,
            powerdByLayout;
    Map<Integer, String> sort_Order_map = new HashMap<>();
    Map<String, View> view_Map = new HashMap<>();
    View devider_bottom_news_ref, devider_title_middle_news;
    DropDownViewControler dropDownViewControler;


    ProgressBar progress_home;
    NavigationView navigationView;
    CustomNestedScrollView nested_scroolview_home;
    NestedScrollView nestedScrollView;
    FloatingActionButton fab_scroll_to_top;


    RelativeLayout admob_rkt_one, admob_rkt_two, admob_rkt_three,
            admob_rkt_four, admob_rkt_five, layoutmovetop, bottomNews_parent;

    LinearLayout recomNewsParent_layout;
    ImageView nav_icon, nav_search;

    WebView rkt_webview;

    ImageView topapps_news_ref, img_games_ref, img_wallpaper_ref, img_externalGames_ref, ad_cross;

    LinearLayout layout_container_nubitview, externalGames_parent_layout;
    RelativeLayout topNewsParent_layout, parent_admob_one, parent_admob_two, parent_admob_four,
            parent_admob_three, topapps_ref_layout, games_ref_layout, parent_admob_five,
            wallpaper_ref_layout, service_layout_parent,
            externalGames_ref_layout, o_service_layout_parent;
    View devider_below_new_ref;
    LinearLayout top_banner_parent;

    int clickcount_floater = 0;
    FrameLayout fram_layout_floater;
    /*   LinearLayout ad_plus_floater_layout;*/

    private boolean shouldScroll = false;
    private Animation rotate_forward;
    private Animation rotate_backward;


    Context context;
    boolean isAsyncTaskRunning;


    RelativeLayout parent_nubitview, layoutAdMob;
    LinearLayout ll_cec;


    ArrayList<HomeVideoPojo> arraylist_top_adver;
    ArrayList<OtherServicesPojo> arraylist_other_ser;
    ArrayList<Top_News_Pojo> arraylist_top_news;

    ArrayList<Entertainment_pojo> arraylist_entertainment;
    ArrayList<TopApps_Pojo> arraylist_topApps;
    ArrayList<GamesPojo> arraylist_games;
    ArrayList<NewPubPojo> arraylist_newspub;
    ArrayList<NewsByPub_Pojo> arraylist_newsbyPub;
    ArrayList<NewModelTest> arrayTesting;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String COUNTER_KEY = "counter";

    ArrayList<HomeVideoPojo> updatedList;
    ArrayList<External_game_pojo> arraylist_Flashgames;
    ArrayList<AppsModel> arraylist_Flashgames_new;
    ArrayList<AppsModel> arraylist_both;
    ArrayList<Bottom_adv_Pojo> arraylist_b_a_banner;
    ArrayList<Wallpaper_Pojo> arraylist_wallpaper;
    ArrayList<Middle_News_Pojo> arraylist_middle_news;
    ArrayList<Bottom_News_Pojo> arraylist_bottom_news;
    ArrayList<Services_pojo> arraylist_services;
    ArrayList<NavigationPojo> arraylist_nav;
    ArrayList<VideoCat> arraylist_videocat;
    ArrayList<VistoryModel> arraylist_recom_new_new;
    ArrayList<TrendingNewsModel> arraylist_trending_new_new;
    ArrayList<TrendingNewsModel> arraylist_sports_new_new;
    ArrayList<BannersModel> arraylist_banner_new_new;
    ArrayList<AppsModel> arraylist_top_apps_new;
    ArrayList<TrendingNewsModel> arraylist_news_video_new;
    ArrayList<AppsModel> arraylist_top_games_new;
    ArrayList<Top_News_Pojo> arraylist_recom_news;
    ArrayList<Admob_nubitPojo> arraylist_admob_nubit;
    ArrayList<Floater_Category_Pojo> arraylist_floater_cate;
    ArrayList<MediaObject> videoList;
    List<Map.Entry<String, Long>> topApps;
    List<AppInfoNew> topAppNew = new ArrayList<>();
    ProgressBar pro_nointernet;
    Button btn_refresh;
    private boolean isRecyclerViewVisible = true;
    private static final String PREF_NAME = "MyPrefs";
    private static final String OPEN_COUNT_KEY = "openCount";
    RelativeLayout nointernet_parent;
    private int currentItem = 0;
    boolean isFirstRun = true;

    RecyclerView news_recom_recyclerView;
    WebView webviewVistory;
    VideoAdapter videoAdapter;
    private float x1, x2;
    static final int MIN_DISTANCE = 150;
    LinearLayout parentLayout;
    View myView;
    private VideoPlayerRecyclerView mRecyclerView;
    private FrameLayout frameLayout;
    private PlayerView videoSurfaceView;
    private SimpleExoPlayer videoPlayer;

    RecyclerView rvFreqApps;
    AdView ad_view_banner;
    AdRequest adRequesNew;
    //    RecyclerView recyclerView;
    RecyclerView recycler_vierw;
    TextView tv_teemp, tv_city, tv_weather;
    ViewPager viewPager;
    ViewPager2 viewPagerNew;
    ViewPagerAdapterMain adapter;
    VideoPagerDynamicAdapter adaptererg;
    Vector<youTubeVideos> youtubeVideos = new Vector<>();
    Vector<Drawable> drawableList = new Vector<>();
    Vector<String> linksList = new Vector<>();
    private BroadcastReceiver pageChangeReceiver;
    private float dX, initialX;

    public NubitViewNew(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        returnNubitView();
    }

    public NubitViewNew(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        returnNubitView();
    }

    public NubitViewNew(Context context) {
        super(context);
        this.context = context;
        returnNubitView();
    }

    WebViewFragment webViewFragment;

    // Method to pause video playback in the specified position
    public void pauseVideoPlayback(int position) {
//        videoAdapter.pauseCurrentVideoPlayback();
        VideoAdapter.VideoViewHolder holder = (VideoAdapter.VideoViewHolder) recycler_vierw.findViewHolderForAdapterPosition(position);
        if (holder != null) {
//            holder.pauseVideoPlayback();
        }
    }

    // Method to pause video playback in the specified position
    public void playVideoPlayback(int position) {
//        videoAdapter.pauseCurrentVideoPlayback();
        VideoAdapter.VideoViewHolder holder = (VideoAdapter.VideoViewHolder) recycler_vierw.findViewHolderForAdapterPosition(position);
        if (holder != null) {
//            holder.playVideoPlayback();
        }
    }

    // Method to pause video playback in the specified position
    public void playVidecoPlaybackce() {
        Log.e("ccecheckfrag", ">returnNubitView>");

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
//        adapter.notifyDataSetChanged();
    }

    // Method to pause video playback in the specified position
    public void playVidecoPlayback() {
        int currentItem = viewPager.getCurrentItem();
        Object fragmentObject = adapter.instantiateItem(viewPager, currentItem);
        if (fragmentObject instanceof WebViewFragment) {
            WebViewFragment fragment = (WebViewFragment) fragmentObject;
//            fragment.pauseVideoPlaybackFinal();
        }
    }

    private void setupViewPagerNews() {
        youtubeVideos.add(new youTubeVideos("<iframe class=\"youtube-player\" style=\"border: 0; width: 100%; height: 95%; padding:0px; margin:0px\" id=\"ytplayer\" type=\"text/html\" src=\"http://www.youtube.com/embed/"
                + "oilnExRnFKg?autoplay=1"
                + "&fs=0\" frameborder=\"0\">\n"
                + "</iframe>\n", R.drawable.cnn));
        youtubeVideos.add(new youTubeVideos("<iframe class=\"youtube-player\" style=\"border: 0; width: 100%; height: 95%; padding:0px; margin:0px\" id=\"ytplayer\" type=\"text/html\" src=\"http://www.youtube.com/embed/"
                + "MN8p-Vrn6G0?autoplay=1"
                + "&fs=0\" frameborder=\"0\">\n"
                + "</iframe>\n"
                , R.drawable.ndtv));
        youtubeVideos.add(new youTubeVideos("<iframe class=\"youtube-player\" style=\"border: 0; width: 100%; height: 95%; padding:0px; margin:0px\" id=\"ytplayer\" type=\"text/html\" src=\"http://www.youtube.com/embed/"
                + "qfrocHBy6RQ?autoplay=1"
                + "&fs=0\" frameborder=\"0\">\n"
                + "</iframe>\n", R.drawable.republic));
        youtubeVideos.add(new youTubeVideos("<iframe class=\"youtube-player\" style=\"border: 0; width: 100%; height: 95%; padding:0px; margin:0px\" id=\"ytplayer\" type=\"text/html\" src=\"http://www.youtube.com/embed/"
                + "4ct3P0OCggY?autoplay=1"
                + "&fs=0\" frameborder=\"0\">\n"
                + "</iframe>\n", R.drawable.times_now));
        youtubeVideos.add(new youTubeVideos("<iframe class=\"youtube-player\" style=\"border: 0; width: 100%; height: 95%; padding:0px; margin:0px\" id=\"ytplayer\" type=\"text/html\" src=\"http://www.youtube.com/embed/"
//            + "nyd-xznCpJc?autoplay=1"
                + "MN8p-Vrn6G0?autoplay=1"
//            + "9cC2tnfuBek?autoplay=1"
                + "&fs=0\" frameborder=\"0\">\n"
                + "</iframe>\n"
                , R.drawable.ndtv));
        youtubeVideos.add(new youTubeVideos("<iframe class=\"youtube-player\" style=\"border: 0; width: 100%; height: 95%; padding:0px; margin:0px\" id=\"ytplayer\" type=\"text/html\" src=\"http://www.youtube.com/embed/"
                + "oilnExRnFKg?autoplay=1"
                + "&fs=0\" frameborder=\"0\">\n"
                + "</iframe>\n", R.drawable.cnn));
        youtubeVideos.add(new youTubeVideos("<iframe class=\"youtube-player\" style=\"border: 0; width: 100%; height: 95%; padding:0px; margin:0px\" id=\"ytplayer\" type=\"text/html\" src=\"http://www.youtube.com/embed/"
                + "qfrocHBy6RQ?autoplay=1"
                + "&fs=0\" frameborder=\"0\">\n"
                + "</iframe>\n", R.drawable.republic));
        youtubeVideos.add(new youTubeVideos("<iframe class=\"youtube-player\" style=\"border: 0; width: 100%; height: 95%; padding:0px; margin:0px\" id=\"ytplayer\" type=\"text/html\" src=\"http://www.youtube.com/embed/"
                + "4ct3P0OCggY?autoplay=1"
                + "&fs=0\" frameborder=\"0\">\n"
                + "</iframe>\n", R.drawable.times_now));
        adapter = new ViewPagerAdapterMain(((FragmentActivity) context).getSupportFragmentManager());
        for (youTubeVideos videoUrl : youtubeVideos) {
            adapter.addVideo(videoUrl);
        }
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Not needed for playback control
            }

            @Override
            public void onPageSelected(int position) {
                // Not needed for playback control
                Log.e("checjposif", ">>" + position);
                if (position == 0) {
                    // Do something
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    // ViewPager has stopped scrolling
                    int currentItem = viewPager.getCurrentItem();
                    for (int i = 0; i < adapter.getCount(); i++) {
                        Fragment fragment = adapter.getItem(i);
                        if (fragment instanceof WebViewFragment) {
                            WebViewFragment webViewFragment = (WebViewFragment) fragment;
                            if (i == currentItem) {
                                // If the fragment is the current one, start playback
                                webViewFragment.setPlaying(true);
                            } else {
                                // Otherwise, pause playback
                                webViewFragment.setPlaying(false);
                            }
                        }
                    }
                }
            }
        });

        viewPager.setCurrentItem(1);
    }

    private void setParentRelativeLayoutScrollListener() {
        parent_nubitview.setOnScrollChangeListener(new OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // Check if the RecyclerView is fully visible or not
                isRecyclerViewVisible = isViewVisibleToUser(recycler_vierw);

                // If RecyclerView is no longer visible, stop video playback
                if (!isRecyclerViewVisible) {
                    Log.e("checknowstop", ">>");
//                    pauseVideoPlayback();
                }
            }
        });
    }

    private boolean isViewVisibleToUser(View view) {
        // Check if the view is visible to the user
        // Implement your logic to determine visibility here
        return view.getGlobalVisibleRect(new android.graphics.Rect());
    }

    @SuppressLint("ClickableViewAccessibility")
    private View returnNubitView() {


        Log.e("checkfrag", ">returnNubitView>");
        myView = inflate(context, R.layout.layout_return_view_new, this);
        arrayTesting = new ArrayList<>();

        NewModelTest newModelTest = new NewModelTest();
        newModelTest.setItem_key("design_one");
        newModelTest.setTitle("videos");

        NewModelTest newModelTest1 = new NewModelTest();
        newModelTest1.setItem_key("design_two");
        newModelTest1.setTitle("freq_apps");

        NewModelTest newModelTest2 = new NewModelTest();
        newModelTest2.setItem_key("design_three");
        newModelTest2.setTitle("trending_news");

        NewModelTest newModelTest3 = new NewModelTest();
        newModelTest3.setItem_key("design_four");
        newModelTest3.setTitle("games");
        arrayTesting.add(newModelTest);

        NewModelTest newModelTest4 = new NewModelTest();
        newModelTest4.setItem_key("design_two");
        newModelTest4.setTitle("games");
        arrayTesting.add(newModelTest);

        NewModelTest newModelTest5 = new NewModelTest();
        newModelTest5.setItem_key("design_three");
        newModelTest5.setTitle("games");
        arrayTesting.add(newModelTest);


//        MobileAds.initialize(this, "YOUR_ADMOB_APP_ID");
//        if (TextUtils.isEmpty(MyApplication.app_sharedPreferences.getString
//                ("getAdvertisementId", ""))) {
//            try {
//                MyUtility.getAdvertisementID(MyApplication.ctx);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

//        recyclerView.setLayoutManager( new LinearLayoutManager(context,
//            LinearLayoutManager.HORIZONTAL, false));

        // Add your drawable resources to the list
//        drawableList.add(context.getResources().getDrawable(R.drawable.banner_one));
//        drawableList.add(context.getResources().getDrawable(R.drawable.banner_two));
//        drawableList.add(context.getResources().getDrawable(R.drawable.banner_three));
//        drawableList.add(context.getResources().getDrawable(R.drawable.banner_four));
//        drawableList.add(context.getResources().getDrawable(R.drawable.banner_five));
//        drawableList.add(context.getResources().getDrawable(R.drawable.banner_six));
//        drawableList.add(context.getResources().getDrawable(R.drawable.banner_one));
//        drawableList.add(context.getResources().getDrawable(R.drawable.banner_two));
//        drawableList.add(context.getResources().getDrawable(R.drawable.banner_three));
//        drawableList.add(context.getResources().getDrawable(R.drawable.banner_four));
        // Add more drawables as needed
        linksList.add("https://www.netmeds.com/republic-day-sale");
        linksList.add("https://www.licious.in/chicken_cdh?category_id=650");
        linksList.add("https://sbi.co.in/web/personal-banking/accounts/saving-account/insta-saving-account");
        linksList.add("https://www.airtel.in/broadband/");
        linksList.add("https://www.spotify.com/in-en/premium/?ref=spotifycom_header_premium_button");
        linksList.add("https://www.netmeds.com/republic-day-sale");
        linksList.add("https://www.licious.in/chicken_cdh?category_id=650");
        linksList.add("https://sbi.co.in/web/personal-banking/accounts/saving-account/insta-saving-account");
        linksList.add("https://www.airtel.in/broadband/");
        linksList.add("https://www.spotify.com/in-en/premium/?ref=spotifycom_header_premium_button");

//        youtubeVideos.add( new youTubeVideos("<iframe class=\"youtube-player\" style=\"border: 0; width: 100%; height: 95%; padding:0px; margin:0px\" id=\"ytplayer\" type=\"text/html\" src=\"http://www.youtube.com/embed/"
//                + "oilnExRnFKg?autoplay=1"
//                + "&fs=0\" frameborder=\"0\">\n"
//                + "</iframe>\n",R.drawable.cnn) );
        youtubeVideos.add(new youTubeVideos("<iframe class=\"youtube-player\" style=\"border: 0; width: 100%; height: 95%; padding:0px; margin:0px\" id=\"ytplayer\" type=\"text/html\" src=\"http://www.youtube.com/embed/"
                + "MN8p-Vrn6G0?autoplay=1"
                + "&fs=0\" frameborder=\"0\">\n"
                + "</iframe>\n"
                , R.drawable.ndtv));
        youtubeVideos.add(new youTubeVideos("<iframe class=\"youtube-player\" style=\"border: 0; width: 100%; height: 95%; padding:0px; margin:0px\" id=\"ytplayer\" type=\"text/html\" src=\"http://www.youtube.com/embed/"
                + "qfrocHBy6RQ?autoplay=1"
                + "&fs=0\" frameborder=\"0\">\n"
                + "</iframe>\n", R.drawable.republic));
//        youtubeVideos.add( new youTubeVideos("<iframe class=\"youtube-player\" style=\"border: 0; width: 100%; height: 95%; padding:0px; margin:0px\" id=\"ytplayer\" type=\"text/html\" src=\"http://www.youtube.com/embed/"
//            + "4ct3P0OCggY?autoplay=1"
//            + "&fs=0\" frameborder=\"0\">\n"
//            + "</iframe>\n",R.drawable.times_now) );
        youtubeVideos.add(new youTubeVideos("<iframe class=\"youtube-player\" style=\"border: 0; width: 100%; height: 95%; padding:0px; margin:0px\" id=\"ytplayer\" type=\"text/html\" src=\"http://www.youtube.com/embed/"
                + "nyd-xznCpJc?autoplay=1"
//                + "MN8p-Vrn6G0?autoplay=1"
//            + "9cC2tnfuBek?autoplay=1"
                + "&fs=0\" frameborder=\"0\">\n"
                + "</iframe>\n"
                , R.drawable.ndtv));
        youtubeVideos.add(new youTubeVideos("<iframe class=\"youtube-player\" style=\"border: 0; width: 100%; height: 95%; padding:0px; margin:0px\" id=\"ytplayer\" type=\"text/html\" src=\"http://www.youtube.com/embed/"
                + "oilnExRnFKg?autoplay=1"
                + "&fs=0\" frameborder=\"0\">\n"
                + "</iframe>\n", R.drawable.cnn));

        layout_container_nubitview = myView.findViewById(R.id.layout_container_nubitview);
        parent_nubitview = myView.findViewById(R.id.parent_nubitview);

        init_Add_View(myView);

        layoutmovetop = myView.findViewById(R.id.layoutmovetop);
        nested_scroolview_home = myView.findViewById(R.id.nested_scroolview_home);
        nestedScrollView = myView.findViewById(R.id.nestedScrollView);
        fab_scroll_to_top = myView.findViewById(R.id.fab_scroll_to_top);
        nested_scroolview_home.setUpScollview(layoutmovetop);
        edt_search = myView.findViewById(R.id.edt_search);

        nointernet_parent = myView.findViewById(R.id.nointernet_parent);
        progress_home = myView.findViewById(R.id.progress_home);
        btn_refresh = myView.findViewById(R.id.btn_refresh);
        pro_nointernet = myView.findViewById(R.id.pro_nointernet);
        drawerLayout = myView.findViewById(R.id.drawer);
        navigationView = myView.findViewById(R.id.navigation_view);
        rkt_webview = myView.findViewById(R.id.rkt_webview);
        fram_layout_floater = myView.findViewById(R.id.fram_layout_floater);
        /* ad_plus_floater_layout = myView.findViewById(R.id.ad_plus_floater_layout);*/
        layoutAdMob = myView.findViewById(R.id.layoutAdMob);
        ad_cross = myView.findViewById(R.id.ad_cross);
        nav_icon = myView.findViewById(R.id.nav_icon);
        nav_search = myView.findViewById(R.id.nav_search);
        mVideoView = new RktVideoView(context);

        btn_refresh.setOnClickListener(this);
        nav_icon.setOnClickListener(this);
        nav_search.setOnClickListener(this);
        TrackSelector trackSelector =
                new DefaultTrackSelector(context);
        videoPlayer = new SimpleExoPlayer.Builder(context).setTrackSelector(trackSelector).build();

        videoSurfaceView = new PlayerView(this.context);
        videoSurfaceView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);

        nested_scroolview_home.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        Log.e("checkfragDrag", ">ACTION_DOWN>" + x2);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:

                        Log.e("checkfragDrag", ">ACTION_UP>" + x2);
                        x2 = event.getX();
                        float deltaX = x2 - x1;

                        if (Math.abs(deltaX) > MIN_DISTANCE) {
                            // Left to Right swipe action
                            if (x2 > x1) {
//                                Toast.makeText(context, "Left to Right swipe [Next]", Toast.LENGTH_SHORT).show ();
                            }

                            // Right to left swipe action
                            else {
//                                videoAdapter.stopVideoPlayback();
//                                videoAdapter.pauseCurrentVideoPlayback();
//                                context?.startHandlingTouches(touchDownY);
//                                Toast.makeText(context, "Right to Left swipe [Previous]", Toast.LENGTH_SHORT).show ();
//                                hideFragment(HomeFragment.getInstance().getView());
//                                hideFragment(HomeFragment.getInstance().getView());
                            }

                        } else {
                            // consider as something else - a screen tap for example
                        }
                        break;
                }
                return false;
            }
        });

//        mRecyclerView = findViewById(R.id.recycler_view);
        frameLayout = findViewById(R.id.media_containerf);

        videoSurfaceView.setUseController(false);
        videoSurfaceView.setPlayer(videoPlayer);

        IntentFilter intentFilter = new IntentFilter(PageChangeBroadcastReceiver.ACTION_PAGE_CHANGED);
        context.registerReceiver(pageChangeReceiver, intentFilter);
        videoPlayer.addListener(new Player.Listener() {
            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {

                    case Player.STATE_BUFFERING:
//                        Log.e(TAG, "onPlayerStateChanged: Buffering video.");
//                        if (progressBar != null) {
//                            progressBar.setVisibility(VISIBLE);
//                        }

                        break;
                    case Player.STATE_ENDED:
//                        Log.d(TAG, "onPlayerStateChanged: Video ended.");
                        videoPlayer.seekTo(0);
                        break;
                    case Player.STATE_IDLE:

                        break;
                    case Player.STATE_READY:
//                        Log.e(TAG, "onPlayerStateChanged: Ready to play.");
//                        if (progressBar != null) {
//                            progressBar.setVisibility(GONE);
//                        }
//                        if(!isVideoViewAdded){
                        addVideoView();
//                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }
        });
//
        frameLayout.addView(videoSurfaceView);
//        isVideoViewAdded = true;
        videoSurfaceView.requestFocus();
        videoSurfaceView.setVisibility(VISIBLE);
        videoSurfaceView.setAlpha(1);

//        thumbnail.setVisibility(GONE);
//        initRecyclerView();


        /* RefreshDataCallBack.getInstance().notifyOnRefreshStart();*/
        try {
            LanguageUpdaterCallBack.getInstance().initListenerForLanguage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            LanguageUpdaterCallBack.getInstance().initListenerForLanguage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
//            Video_Cat_CallBAck.getInstance().init_interface_VideoCat(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PagersAutoScrollControllerCallback.getInstance().setListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        hitGetOrderApi();
/*
        try {
            RktPlayerCallBack.getInstance().setListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        String get_SavedJson = MyApplication.app_sharedPreferences.getString
                ("save_app_order_Json",
                        "");
        if (!TextUtils.isEmpty(get_SavedJson)) {
            hitVersion();
            hitGetProfile();
            hitAppsDataApi();
            hitBannersDataApi();
            hitMainDataApi();
            hitSportsDataApi();
            hitGetVideoDataApi();
            hitWeatherApi();
        }

//        hitGetVistory();
        if (!TextUtils.isEmpty(MyApplication.app_sharedPreferences.getString("key_modifiedDat",
                ""))) {
            RefreshDataCallBack.getInstance().notifyOnRefreshStart();

            parseData();


        } else {


            if (MyUtility.isConnectedToInternet()) {
                /*Here notifying the launcher to start loading in case if user is new user*/
                RefreshDataCallBack.getInstance().notifyOnRefreshStart();

            } else {
                parent_nubitview.setVisibility(View.VISIBLE);
                RefreshDataCallBack.getInstance().notifyOnNoInternet();

            }
        }

        setParentRelativeLayoutScrollListener();
        /*    edt_search.setOnTouchListener(context);*/
        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }


                return false;
            }
        });
        if (shouldScroll == true) {
            start_Scroll();
        } else {
            stop_Scroll("home", 0);
        }


//        home_topBanner_Pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int
//                    positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                /*   Log.d("scroll_position", String.valueOf(position));*/
//               /* if (position == 5) {
//                    stop_Scroll();
//                }
//*/
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                if (ViewPager.SCROLL_STATE_DRAGGING == state) {
//                    home_topBanner_Pager.stopAutoScroll();
//
//
//                    if (shouldScroll == true) {
//
//                        final Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                if (scrollStatus.equalsIgnoreCase("loading")) {
//                                    home_topBanner_Pager.stopAutoScroll();
//                                } else {
//                                    scrollStatus = "home";
//                                    home_topBanner_Pager.startAutoScroll();
//
//
//                                }
//
//                                /* post_delay_running = true;*/
//                            }
//                        }, 3000);
//
//                    }
//
//                    //Log.d("SCROLL_STATE_DRAGGING", "Yes");
//
//                }
//            }
//        });


        try {
            // This is sending the analytical data to nubit server, but it should be call on
            // launcher hide like in nubit, not from here.


            pushTracksOnServer();
        } catch (Exception e) {
            e.printStackTrace();
        }

        videoSurfaceView.setPlayer(videoPlayer);

        return myView;
    }

//    public void setupViewPager(List<String> videoUrls) {
//        this.videoUrls = videoUrls;
//        adapter = new ViewPagerAdapter(((FragmentActivity) getContext()).getSupportFragmentManager(), videoUrls);
//        viewPager.setAdapter(adapter);
//    }

    private void hideFragment(View fragment) {
//        fragment.getRootView().findViewById(R.id.btn_close).callOnClick();
//        ObjectAnimator.ofFloat(fragment, "y", mScreenHeight).start();
//        ObjectAnimator animator = ObjectAnimator.ofFloat(fragment, "y", mScreenHeight);
//        animator.setDuration(300);
//        animator.setInterpolator(new DecelerateInterpolator());
//        animator.start();
//
//        getWindow().setNavigationBarColor(Color.TRANSPARENT);
//        home_screen_grid.fragmentCollapsed();
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                NubitFragment nubitFragment = (NubitFragment) fragment;
//                nubitFragment.onBackPressed();
////                if (fragment instanceof AllAppsFragment) {
////                    AllAppsFragment allAppsFragment = (AllAppsFragment) fragment;
////                    allAppsFragment.all_apps_grid.scrollToPosition(0);
////                    allAppsFragment.touchDownY = -1;
////                } else if (fragment instanceof WidgetsFragment) {
////                    WidgetsFragment widgetsFragment = (WidgetsFragment) fragment;
////                    widgetsFragment.widgets_list.scrollToPosition(0);
////                    widgetsFragment.touchDownY = -1;
////                } else if (fragment instanceof NubitFragment) {
////                    NubitFragment nubitFragment = (NubitFragment) fragment;
////                    nubitFragment.onBackPressed();
////                }
//            }
//        }, 300);
    }

    private void addVideoView() {
        Log.e("Checkvideoview", "1>>");
        frameLayout.addView(videoSurfaceView);
//        isVideoViewAdded = true;
        videoSurfaceView.requestFocus();
        videoSurfaceView.setVisibility(VISIBLE);
        videoSurfaceView.setAlpha(1);
//        thumbnail.setVisibility(GONE);
    }

//    @Override
//    protected void onDestroy() {
//        if(mRecyclerView!=null)
//            mRecyclerView.releasePlayer();
//        super.onDestroy();
//    }

    public String extractYoutubeUrl() {
        final String[] urll = {""};
        @SuppressLint("StaticFieldLeak") YouTubeExtractor mExtractor = new YouTubeExtractor(context) {
            @Override
            protected void onExtractionComplete(SparseArray<YtFile> sparseArray, VideoMeta videoMeta) {
                if (sparseArray != null) {
                    urll[0] = sparseArray.get(17).getUrl();
                }
            }
        };
        mExtractor.extract("https://www.youtube.com/watch?v=N2eWecDW1AI", true, true);
        return urll[0];
    }

    private void initRecyclerView() {
        videoList = new ArrayList<>();
        videoList.add(new MediaObject("AjjTak",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Rest+api+teaser+video.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Sending+Data+to+a+New+Activity+with+Intent+Extras.png",
                "Description for media object #1"));

        videoList.add(new MediaObject("Republic TV",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Rest+api+teaser+video.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/REST+API%2C+Retrofit2%2C+MVVM+Course+SUMMARY.png",
                "Description for media object #2"));

        videoList.add(new MediaObject("India Today",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Rest+api+teaser+video.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/mvvm+and+livedata.png",
                "Description for media object #3"));

        videoList.add(new MediaObject("NDTV",
//            extractYoutubeUrl(),
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Rest+api+teaser+video.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Swiping+Views+with+a+ViewPager.png",
                "Description for media object #4"));

        videoList.add(new MediaObject("CNN",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Rest+api+teaser+video.mp4",
                "https://s3.ca-central-1.amazonaws.com/codingwithmitch/media/VideoPlayerRecyclerView/Rest+API+Integration+with+MVVM.png",
                "Description for media object #5"));

//        LinearLayoutManager layoutManager = new LinearLayoutManager(context,
//            LinearLayoutManager.HORIZONTAL, false);
//        mRecyclerView.setLayoutManager(layoutManager);
//        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
//        mRecyclerView.addItemDecoration(itemDecorator);

//        ArrayList<MediaObject> mediaObjects = new ArrayList<MediaObject>(Arrays.asList(Resources.MEDIA_OBJECTS));
//        mRecyclerView.setMediaObjects(mediaObjects);
//        VideoPlayerRecyclerAdapter adapter = new VideoPlayerRecyclerAdapter(videoList, initGlide());
//        mRecyclerView.setAdapter(adapter);
    }

//    private RequestManager initGlide(){
//        RequestOptions options = new RequestOptions()
//                .placeholder(R.drawable.back_button)
//                .error(R.drawable.back_button);
//
//        return Glide.with(this)
//                .setDefaultRequestOptions(options);
//    }


    private void show_fullScreenBanner(String isFullScreenBanner, String advbanner_delay,
                                       String advbanner_banner_image, String advbanner_ad_unit_id,
                                       String advbanner_redirectLink, String advbanner_open_with,
                                       String advbanner_title, String webview_url, String
                                               splash_time) {

        String get_last_bannerShow = MyApplication.app_sharedPreferences
                .getString
                        ("last_bannerShow", "");

        if (TextUtils.isEmpty(get_last_bannerShow)) {

            DialogFragment dialogFragment = RobiUpdateDialog.newInstance
                    (isFullScreenBanner, advbanner_delay,
                            advbanner_banner_image,
                            advbanner_ad_unit_id, advbanner_redirectLink,
                            advbanner_open_with, advbanner_title, webview_url, splash_time);
            dialogFragment.show(((Activity) context).getFragmentManager(), "RobiUpdateDialog");

        } else if (!TextUtils.isEmpty(get_last_bannerShow) && !TextUtils.isEmpty
                (advbanner_delay) &&
                advbanner_delay.equalsIgnoreCase("100")) {

            DialogFragment dialogFragment = RobiUpdateDialog.newInstance
                    (isFullScreenBanner, advbanner_delay,
                            advbanner_banner_image,
                            advbanner_ad_unit_id, advbanner_redirectLink,
                            advbanner_open_with, advbanner_title, webview_url, splash_time);
            dialogFragment.show(((Activity) context).getFragmentManager(), "RobiUpdateDialog");


        } else if (!TextUtils.isEmpty(get_last_bannerShow) && !TextUtils.isEmpty
                (advbanner_delay)) {
            if (MyUtility.getTimeDifference_nubit("fullBanner") > Long.parseLong(advbanner_delay)) {
                DialogFragment dialogFragment = RobiUpdateDialog.newInstance
                        (isFullScreenBanner, advbanner_delay,
                                advbanner_banner_image,
                                advbanner_ad_unit_id, advbanner_redirectLink,
                                advbanner_open_with, advbanner_title, webview_url, splash_time);
                dialogFragment.show(((Activity) context).getFragmentManager
                        (), "RobiUpdateDialog");

            }
        }
    }


    private void init_Add_View(View view) {

        adddefaultView();
        home_topBanner_Pager = view.findViewById(R.id.viewpager_topBanner);
        topBanner_indicator = view.findViewById(R.id.indicator);
        other_ser_recyclerView = view.findViewById(R.id.o_services_recyclerView);
        service_layout_parent = view.findViewById(R.id.service_layout_parent);
        dropDownViewControler = view.findViewById(R.id.dropdown_below_service_grid);
        top_News_recyclerView = view.findViewById(R.id.news_top_recyclerView);
        top_News_recyclerVie_test = view.findViewById(R.id.news_top_recyclerVie_test);
        rvFreqApps = view.findViewById(R.id.rvFreqApps);
        ad_view_banner = view.findViewById(R.id.ad_view_banner);
        tv_teemp = view.findViewById(R.id.tv_teemp);
        tv_city = view.findViewById(R.id.tv_city);
        tv_weather = view.findViewById(R.id.tv_weather);
        news_recom_recyclerView = view.findViewById(R.id.news_recom_recyclerView);
        webviewVistory = view.findViewById(R.id.webviewVistory);
        topapps_recyclerView = view.findViewById(R.id.topapps_recyclerView);
        bottomNews_recyclerView = view.findViewById(R.id.bottomNews_recyclerView);
        service_grid = view.findViewById(R.id.service_grid);
        btn_add_pub = view.findViewById(R.id.btn_add_pub);
        entertain_recyclerView = view.findViewById(R.id.entertainment_recyclerView);
        bottomAdv_recyclerView = view.findViewById(R.id.bottom_adv_recyclerView);
        games_recyclerView = view.findViewById(R.id.games_recyclerView);
        external_games_recyclerView = view.findViewById(R.id.externalGames_recyclerView);
//        wallpaper_recyclerView = view.findViewById(R.id.wallpaper_recyclerView);
        middleNews_recyclerView = view.findViewById(R.id.middle_news_recyclerView);
        topapps_news_ref = view.findViewById(R.id.topapps_news_ref);
        img_games_ref = view.findViewById(R.id.img_games_ref);
//        img_wallpaper_ref = view.findViewById(R.id.img_wallpaper_ref);
        img_externalGames_ref = view.findViewById(R.id.img_externalGames_ref);
        entertainment_parent = view.findViewById(R.id.entertainment_parent);
        topapps_parent = view.findViewById(R.id.topapps_parent_layout);
        games_parent = view.findViewById(R.id.games_parent_layout);
//        wallpaper_parent = view.findViewById(R.id.wallpaper_parent_layout);
        parent_taboola = view.findViewById(R.id.parent_taboola);

        bottom_adv_parent = view.findViewById(R.id.bottom_adv_parent);
        middle_news_parent = view.findViewById(R.id.middle_news_parent);
        bottomNews_parent = view.findViewById(R.id.bottomNews_parent);
        recomNewsParent_layout = view.findViewById(R.id.recomNewsParent_layout);
        powerdByLayout = view.findViewById(R.id.powerdByLayout);
        service_layout_parent = view.findViewById(R.id.service_layout_parent);
        top_News_recyclerView = view.findViewById(R.id.news_top_recyclerView);
        top_News_recyclerVie_test = view.findViewById(R.id.news_top_recyclerVie_test);
//        wallpaper_recyclerView = view.findViewById(R.id.wallpaper_recyclerView);
        /* irctc_List = view.findViewById(R.id.irctc_List);*/
        external_games_recyclerView = view.findViewById(R.id.externalGames_recyclerView);
        bottomNews_recyclerView = view.findViewById(R.id.bottomNews_recyclerView);
        middleNews_recyclerView = view.findViewById(R.id.middle_news_recyclerView);
        bottomAdv_recyclerView = view.findViewById(R.id.bottom_adv_recyclerView);
        games_recyclerView = view.findViewById(R.id.games_recyclerView);
        topapps_recyclerView = view.findViewById(R.id.topapps_recyclerView);
        entertain_recyclerView = view.findViewById(R.id.entertainment_recyclerView);
        other_ser_recyclerView = view.findViewById(R.id.o_services_recyclerView);

        parent_admob_one = view.findViewById(R.id.parent_admob_one);
        parent_admob_two = view.findViewById(R.id.parent_admob_two);
        parent_admob_three = view.findViewById(R.id.parent_admob_three);
        parent_admob_four = view.findViewById(R.id.parent_admob_four);
        parent_admob_five = view.findViewById(R.id.parent_admob_five);


        btn_service_title = view.findViewById(R.id.btn_service_title);

        btn_news_title = view.findViewById(R.id.btn_news_title);
        btn_freq_apps = view.findViewById(R.id.btn_freq_apps);
        btn_bottom_news_title = view.findViewById(R.id.btn_bottom_news_title);
        title_middle_news = view.findViewById(R.id.title_middle_news);
        title_recommended_news = view.findViewById(R.id.title_recommended_news);
        btn_topapps_title = view.findViewById(R.id.btn_topapps_title);
        btn_games_title = view.findViewById(R.id.btn_games_title);
        btn_wallpaper_title = view.findViewById(R.id.btn_wallpaper_title);
        btn_externalGames_title = view.findViewById(R.id.btn_externalGames_title);

        service_grid = (MyGridView) view.findViewById(R.id.service_grid);
        dropDownViewControler = view.findViewById(R.id.dropdown_below_service_grid);
        devider_below_btnService = view.findViewById(R.id.devider_below_btnService);
        devider_below_recommended = view.findViewById(R.id.devider_below_recommended);


        admob_rkt_one = view.findViewById(R.id.admob_rkt_one);
        admob_rkt_two = view.findViewById(R.id.admob_rkt_two);
        admob_rkt_three = view.findViewById(R.id.admob_rkt_three);
        admob_rkt_four = view.findViewById(R.id.admob_rkt_four);
        admob_rkt_five = view.findViewById(R.id.admob_rkt_five);
        recycler_vierw = view.findViewById(R.id.recycler_vierw);

//        viewPager = findViewById(R.id.viewPager);
        viewPagerNew = findViewById(R.id.viewPagerNew);

//        recyclerView.setHasFixedSize(true);


        topapps_news_ref = view.findViewById(R.id.topapps_news_ref);
        img_games_ref = view.findViewById(R.id.img_games_ref);
//        img_wallpaper_ref = view.findViewById(R.id.img_wallpaper_ref);
        img_externalGames_ref = view.findViewById(R.id.img_externalGames_ref);
        topNewsParent_layout = view.findViewById(R.id.topNewsParent_layout);

//        o_service_layout_parent = view.findViewById(R.id.o_service_layout_parent);

        topapps_ref_layout = view.findViewById(R.id.topapps_ref_layout);
        top_banner_parent = view.findViewById(R.id.top_banner_parent);
        games_ref_layout = view.findViewById(R.id.games_ref_layout);
        externalGames_ref_layout = view.findViewById(R.id.externalGames_ref_layout);
//        wallpaper_ref_layout = view.findViewById(R.id.wallpaper_ref_layout);
        devider_bottom_news_ref = view.findViewById(R.id.devider_bottom_news_ref);
        devider_title_middle_news = view.findViewById(R.id.devider_title_middle_news);
        externalGames_parent_layout = view.findViewById(R.id.externalGames_parent_layout);
        devider_below_new_ref = view.findViewById(R.id.devider_below_new_ref);

        topapps_news_ref.setOnClickListener(this);
        img_games_ref.setOnClickListener(this);
//        img_wallpaper_ref.setOnClickListener(this);
        img_externalGames_ref.setOnClickListener(this);
        btn_add_pub.setOnClickListener(this);

        init_floater_veiw(view);

        getFrequentApps();

    }

    private void parseData() {


        // In mean time check if data already with you....show case that data to user.

        arraylist_other_ser = new ArrayList<>();
        arraylist_top_adver = new ArrayList<>();
        arraylist_top_news = new ArrayList<>();
        arraylist_entertainment = new ArrayList<>();

        arraylist_newspub = new ArrayList<>();
        arraylist_topApps = new ArrayList<>();
        arraylist_games = new ArrayList<>();
        arraylist_b_a_banner = new ArrayList<>();
        arraylist_wallpaper = new ArrayList<>();
        arraylist_middle_news = new ArrayList<>();
        arraylist_bottom_news = new ArrayList<>();
        arraylist_services = new ArrayList<>();
        arraylist_Flashgames = new ArrayList<>();
        arraylist_Flashgames_new = new ArrayList<>();
        arraylist_nav = new ArrayList<>();
        arraylist_videocat = new ArrayList<>();
        arraylist_newsbyPub = new ArrayList<>();
        arraylist_recom_news = new ArrayList<>();
        arraylist_recom_new_new = new ArrayList<>();
        arraylist_trending_new_new = new ArrayList<>();
        arraylist_admob_nubit = new ArrayList<>();
        arraylist_floater_cate = new ArrayList<>();


        String get_SavedJson = MyApplication.app_sharedPreferences.getString
                ("save_app_Json",
                        "");
        Log.e("gcjgc", ">>" + get_SavedJson);
        if (!TextUtils.isEmpty(get_SavedJson)) {
            Gson gson = new Gson();
            response = gson.fromJson(get_SavedJson, Response.class);
            Data data = response.getData();
            Configuration configuration = response.getConfiguration();
            try {
                arraylist_top_adver.addAll(data.getVideos());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (response.getSlot_rotation() != null && response.getSlot_rotation().size()
                        > 0) {
                    shouldScroll = true;
                    try {
                        PagersAutoScrollControllerCallback.getInstance().notify(true, "home",
                                0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    shouldScroll = false;
                    try {
                        PagersAutoScrollControllerCallback.getInstance().notify(false,
                                "home", 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                arraylist_other_ser.addAll(data.getOther_services());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                arraylist_services.addAll(data.getServices());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                arraylist_top_news.addAll(data.getNews1());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                arraylist_newspub.addAll(data.getNews_publishers());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                arraylist_recom_news.addAll(data.getNews4());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                arraylist_admob_nubit.addAll(data.getAdmob_nubit());
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                arraylist_topApps.addAll(data.getTop_apps());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                arraylist_newsbyPub.addAll(data.getNews_data());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                arraylist_bottom_news.addAll(data.getNews3());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                arraylist_entertainment.addAll(data.getEntertainment());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                arraylist_b_a_banner.addAll(data.getBottom_advertisement());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                arraylist_games.addAll(data.getGames());
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.e("checkapps",">>" + data.getExternal_game());
            try {
                //save_app_apps_new_Json
                ArrayList<AppsModel> newArray = new ArrayList<>();
                for (int i = 0; i < data.getExternal_game().size(); i++) {
                    External_game_pojo jsonObject = data.getExternal_game().get(i);
                    Log.e("GetMainmmmData",">>" + jsonObject.getTitle());
                    String id = jsonObject.getId();
                    String orderNo = jsonObject.getTitle();
                    String title = jsonObject.getTitle();
                    String category = jsonObject.getCategory();
                    String status = jsonObject.getPackage_name();
                    String redirectLink = jsonObject.getRedirect_link();
                    String bannerImage = jsonObject.getBanner_image();
                    String bannerThumbImage = jsonObject.getBanner_thumb_image();

                    AppsModel trendingNewsModel = new AppsModel(id, orderNo, title, category, status, redirectLink,
                            bannerImage, bannerThumbImage);
                    newArray.add(trendingNewsModel);
                }
                Log.e("GetMainmmmData","newArray>00>" + newArray.size() + ">>" + newArray.get(1).getTitle());
                JSONArray save_json_jsonobject = null;
                Gson gsocn = new Gson();
                String json = gsocn.toJson(newArray);
//                editor.putString(KEY_CUSTOM_MODEL_ARRAY, json);

                save_json_jsonobject = new JSONArray(newArray);

                if (save_json_jsonobject != null) {
                    if (!TextUtils.isEmpty(save_json_jsonobject.toString())) {
                        Log.e("GetMainmmmData","newArray>11>" + newArray.size());

                        MyApplication.app_editor.putString("save_app_app_design_new_Json", String.valueOf
                                (json));
                        Log.e("GetMainmmmData","newArray>33>" + json);

                        MyApplication.app_editor.apply();
                    }


                }
                arraylist_Flashgames.addAll(data.getExternal_game());
            } catch (Exception e) {
                e.printStackTrace();
            }

//            try {
//                arraylist_wallpaper.addAll(data.getWallpaper());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try {
//                arraylist_middle_news.addAll(data.getNews2());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try {
//                arraylist_videocat.addAll(data.getVideos_categories());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//            try {
//                arraylist_nav.addAll(data.getDrawer_item());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try {
//                arraylist_floater_cate.addAll(data.getFloater());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                if (data.getSearchbar() != null && data.getSearchbar().size() > 0) {
//                    MyApplication.app_editor.putString("search_redirection", data.getSearchbar()
//                            .get(0).getRedirect_link()).apply();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            String get_SavedJsoOrdern = MyApplication.app_sharedPreferences.getString
                    ("save_app_order_Json",
                            "");
            Log.e("chefnfckref", "get_SavedJsoOrdern>>" + get_SavedJsoOrdern);

            if (!TextUtils.isEmpty(get_SavedJsoOrdern)) {
                Log.e("chefef", ">>" + get_SavedJson);
                OrderModel[] sectionsArray = gson.fromJson(get_SavedJsoOrdern, OrderModel[].class);
                List<OrderModel> sections = new ArrayList<>();
                Collections.addAll(sections, sectionsArray);

                // Step 1: Sort the list based on placementOrder
                Collections.sort(sections, new Comparator<OrderModel>() {
                    @Override
                    public int compare(OrderModel s1, OrderModel s2) {
                        return Integer.compare(Integer.parseInt(s1.getPlacementOrder()), Integer.parseInt(s2.getPlacementOrder()));
                    }
                });

                // Step 2: Create a new ArrayList with elements sorted by name
                List<String> sortedNamesList = new ArrayList<>();
                for (OrderModel section : sections) {
                    sortedNamesList.add(section.getName());
                    Log.e("chcksot", ">>" + section.getPlacementOrder() + ">>" + section.getName());
                    sort_Order_map.put(Integer.parseInt(section.getPlacementOrder()),
                            section.getName());
                }
                Log.e("chefnfkref", "final>>" + sortedNamesList);
            }else {
//                hitGetOrderApi();
                ((Activity) context).finish();

// Restart the app by starting the launcher activity
                Intent intent = ((Activity) context).getPackageManager().getLaunchIntentForPackage(((Activity) context).getPackageName());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ((Activity) context).startActivity(intent);
            }

//            try {
//                if (data.getSort_order() != null && data.getSort_order().size() > 0) {
//                    for (SortOrder sortOrder : data.getSort_order()) {
//                        try {
//                            Log.e("chcksot",">>" + sortOrder.getSortOrder() + ">>" + sortOrder.getOrderType());
//                            sort_Order_map.put(Integer.parseInt(sortOrder.getSortOrder()),
//                                    sortOrder.getOrderType());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }


            if (!TextUtils.isEmpty(configuration.getYuptv_update_version())) {
                MyApplication.app_editor.putString("yuptv_update_version", configuration
                        .getYuptv_update_version()).apply();
            } else {
                MyApplication.app_editor.putString("yuptv_update_version", "").apply();
            }

            if (!TextUtils.isEmpty(configuration.getuTube_AdType())) {
                MyApplication.app_editor.putString("uTube_AdType", configuration
                        .getuTube_AdType()).apply();
            } else {
                MyApplication.app_editor.putString("uTube_AdType", "").apply();
            }

            if (!TextUtils.isEmpty(configuration.getuTube_AdId())) {
                MyApplication.app_editor.putString("uTube_AdId", configuration
                        .getuTube_AdId()).apply();
            } else {
                MyApplication.app_editor.putString("uTube_AdId", "").apply();
            }

            if (!TextUtils.isEmpty(configuration.getWallpaper_adID())) {
                MyApplication.app_editor.putString("wallpaper_adID", configuration
                        .getWallpaper_adID()).apply();
            } else {
                MyApplication.app_editor.putString("wallpaper_adID", "").apply();
            }

            if (TextUtils.isEmpty(configuration.getLike_dislike())) {
                MyApplication.app_editor.putString("like_dislike", "").commit();
            } else {
                MyApplication.app_editor.putString("like_dislike", configuration
                        .getLike_dislike()).apply();
            }


            if (!TextUtils.isEmpty(configuration.getAd_type_chanel())) {
                MyApplication.app_editor.putString("ad_type_chanel", configuration
                        .getAd_type_chanel()).apply();
            } else {
                MyApplication.app_editor.putString("ad_type_chanel", "").apply();
            }

            if (!TextUtils.isEmpty(configuration.getAd_id_chanel())) {
                MyApplication.app_editor.putString("ad_id_chanel", configuration
                        .getAd_id_chanel()).apply();
            } else {
                MyApplication.app_editor.putString("ad_id_chanel", "").apply();
            }


            if (!TextUtils.isEmpty(configuration.getAdmob_banner_placemnt_id())) {
                MyApplication.app_editor.putString("admob_banner_placemnt_id", configuration
                        .getAdmob_banner_placemnt_id()).apply();

                String get_iscross_player = "";
                if (!TextUtils.isEmpty(configuration.getIscross_home())) {
                      /*  MyApplication.app_editor.putString("iscross_home", configuration
                                .getIscross_home()).apply();*/
                    get_iscross_player = configuration.getIscross_home();
                } else {
                    get_iscross_player = "";
                    /*  MyApplication.app_editor.putString("iscross_home", "").apply();*/
                }

                String get_banner_types = "";
                if (!TextUtils.isEmpty(configuration.getBanner_type_home())) {
                    get_banner_types = configuration.getBanner_type_home();
                } else {
                    get_banner_types = "";

                }


                MyUtility.setUpBannerAd(context, configuration.getAdmob_banner_placemnt_id(),
                        layoutAdMob, get_banner_types, ad_cross, get_iscross_player);

            }


            if (TextUtils.isEmpty(configuration.getVideoShare_showHide())) {
                MyApplication.app_editor.putString("videoShare_showHide", "").commit();
            } else {
                MyApplication.app_editor.putString("videoShare_showHide", configuration
                        .getVideoShare_showHide
                                ()).apply();
            }
            if (TextUtils.isEmpty(configuration.getNews_pub_shwHide())) {
                MyApplication.app_editor.putString("news_pub_shwHide", "").commit();
            } else {
                MyApplication.app_editor.putString("news_pub_shwHide", configuration
                        .getNews_pub_shwHide
                                ()).apply();
            }

            if (!TextUtils.isEmpty(configuration.getBucket_name())) {
                MyApplication.app_editor.putString("bucket_name", configuration
                        .getBucket_name()).apply();
            } else {
                MyApplication.app_editor.putString("bucket_name", "").apply();
            }


            if (!TextUtils.isEmpty(configuration.getS3_secret_key())) {
                MyApplication.app_editor.putString("s3_secret_key", configuration
                        .getS3_secret_key()).apply();
            } else {
                MyApplication.app_editor.putString("s3_secret_key", "").apply();
            }

            if (!TextUtils.isEmpty(configuration.getS3_access_key())) {
                MyApplication.app_editor.putString("s3_access_key", configuration
                        .getS3_access_key()).apply();
            } else {
                MyApplication.app_editor.putString("s3_access_key", "").apply();
            }


            if (!TextUtils.isEmpty(configuration.getDefault_language())) {
                MyApplication.app_editor.putString("language_selection", configuration
                        .getDefault_language()).apply();
            } else {
                MyApplication.app_editor.putString("language_selection", "").apply();
            }

            if (!TextUtils.isEmpty(configuration.getAdmob_cache())) {
                if ((configuration.getAdmob_cache()).equalsIgnoreCase("yesrkt")) {
                    MyApplication.app_editor.remove("view_duration").apply();
                }

            }


            if (arraylist_newspub != null) {
                if (arraylist_newspub.size() > 0) {

                    String news_pub_shwHide = MyApplication.app_sharedPreferences.getString
                            ("news_pub_shwHide", "");
                    if (!TextUtils.isEmpty(news_pub_shwHide) & news_pub_shwHide
                            .equalsIgnoreCase("1")) {
                        btn_add_pub.setVisibility(View.VISIBLE);
                    } else {
                        btn_add_pub.setVisibility(View.GONE);
                    }


                } else {
                    btn_add_pub.setVisibility(View.GONE);
                }
            } else {
                btn_add_pub.setVisibility(View.GONE);
            }


            if (TextUtils.isEmpty(configuration.getVideoCat_showHide())) {
                MyApplication.app_editor.putString("videoCat_showHide", "").commit();
            } else {
                MyApplication.app_editor.putString("videoCat_showHide", configuration
                        .getVideoCat_showHide
                                ()).apply();
            }


            if (!TextUtils.isEmpty(configuration.getSocial_login())) {
                MyApplication.app_editor.putString("social_login_profile", configuration
                        .getSocial_login()).apply();
            } else {
                MyApplication.app_editor.putString("social_login_profile", "").apply();
            }
            if (TextUtils.isEmpty(configuration.getWallpaper_share_flg())) {
                MyApplication.app_editor.putString("wallpaper_share_flg", "").commit();
            } else {
                MyApplication.app_editor.putString("wallpaper_share_flg", configuration
                        .getWallpaper_share_flg()).apply();
            }

            if (TextUtils.isEmpty(configuration.getNews_share_flg())) {
                MyApplication.app_editor.putString("news_share_flg", "").commit();
            } else {
                MyApplication.app_editor.putString("news_share_flg", configuration
                        .getNews_share_flg
                                ()).apply();
            }


            if (TextUtils.isEmpty(configuration.getShareBy_brandName())) {
                MyApplication.app_editor.putString("shareBy_brandName", "").commit();
            } else {
                MyApplication.app_editor.putString("shareBy_brandName", configuration
                        .getShareBy_brandName
                                ()).apply();
            }

            if (TextUtils.isEmpty(configuration.getShare_content())) {
                MyApplication.app_editor.putString("getShare", "").commit();
            } else {
                MyApplication.app_editor.putString("getShare", configuration
                        .getShare_content
                                ()).apply();
            }
            if (TextUtils.isEmpty(configuration.getNews_share())) {
                MyApplication.app_editor.putString("news_share", "").commit();
            } else {
                MyApplication.app_editor.putString("news_share", configuration.getNews_share
                        ()).apply();
            }


            if (TextUtils.isEmpty(configuration.getWallpaper_share())) {
                MyApplication.app_editor.putString("wallpaper_share", "").commit();
            } else {
                MyApplication.app_editor.putString("wallpaper_share", configuration
                        .getWallpaper_share
                                ()).apply();
            }

            if (TextUtils.isEmpty(configuration.getVideo_share())) {
                MyApplication.app_editor.putString("video_share", "").commit();
            } else {
                MyApplication.app_editor.putString("video_share", configuration
                        .getVideo_share
                                ()).apply();
            }


        }


        String get_apps_SavedJson = MyApplication.app_sharedPreferences.getString
                ("save_app_video_Json",
                        "");
        if (!TextUtils.isEmpty(get_apps_SavedJson)) {
            Gson gson = new Gson();
            Log.e("chefef", ">>" + get_apps_SavedJson);
            if (get_apps_SavedJson != null) {
                Type type = new TypeToken<List<TrendingNewsModel>>() {
                }.getType();
                arraylist_news_video_new = gson.fromJson(get_apps_SavedJson, type);
            }
        }
//        Log.e("checknewvideo", ">nubit>" + arraylist_news_video_new.size());

        /*   progress_home.setVisibility(View.GONE);*/


        try {
            parent_nubitview.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = nestedScrollView.getScrollY();
                Log.e("chefnfkreef", "scrollY>>" + scrollY);
                if (scrollY > 0) {
//                    if (!isViewVisible(viewPagerNew)) {
//                        // Communicate with the fragment to stop the player
//                        stopPlayerInFragment();
//                    }
                    // Scrolled down, show the button
                    fab_scroll_to_top.setVisibility(View.VISIBLE);
                } else {
                    // At the top, hide the button
                    fab_scroll_to_top.setVisibility(View.GONE);
                }
            }
        });

        fab_scroll_to_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Scroll to the top
                smoothScrollToTop();
            }
        });

        /*Here notifying the launcher to stop the loading & to start showing UI*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RefreshDataCallBack.getInstance().notifyOnRefreshFinish();
            }
        }, 1000);

        /*All arrayList added to all adapters after parsing the data*/
        addAdapter_to_View();


        hit_admob_mobin();
        setUp_MobinView();



        /*Intialize navigation click listner*/
        onNav_ItemSelected();

    }

    private void smoothScrollToTop() {
        // Get current scroll position
        final int currentScrollY = nestedScrollView.getScrollY();

        // Create a ValueAnimator
        ValueAnimator animator = ValueAnimator.ofInt(currentScrollY, 0);
        animator.setDuration(1000); // Set the duration of the animation (adjust as needed)
        animator.setInterpolator(new DecelerateInterpolator()); // Use a decelerate interpolator for a more natural scroll feel

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // Get the animated value
                int animatedValue = (int) animation.getAnimatedValue();

                // Set the new scroll position
                nestedScrollView.scrollTo(0, animatedValue);
            }
        });

        // Start the animation
        animator.start();
    }

    private void addAdapter_to_View() {

        // Sort Order logic start here
//        add_sortedView();
//        // Sort Order logic end here
//
////        init_top_banner_view();
////        init_other_services_view();
////        init_service_view();
//        init_TopNews_Views();
////        init_TopNews_View_test();
//        populateGridView(topApps);
//        init_Recom_News_Views();
//        init_TopApps_Views();
//        init_BottomNews_Views();
//        init_Entertainment_Views();
//        init_BottomAdv_Views();
//        init_Games_Views();
//        init_external_game_view();
//        init_Wallpaper_Views();
//        init_MiddleNews_Views();
//        setUpNavigationViewData();
//        // init_TaboolaView();
//        init_Title_view();
    }

    // Method to check if a view is visible to the user
    private boolean isViewVisible(View view) {
        Rect scrollBounds = new Rect();
        nestedScrollView.getHitRect(scrollBounds);
        return view.getLocalVisibleRect(scrollBounds);
    }

    private void stopPlayerInFragment() {
        int currentItem = viewPagerNew.getCurrentItem();
        VideoFragment fragment = (VideoFragment) adaptererg.getFragment(currentItem);
        if (fragment != null) {
            fragment.stopPlayer();
        }

    }

    private void hitVersion() {


        if (!isAsyncTaskRunning) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                new CheckServerForNewData_asyn().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                new CheckServerForNewData_asyn().execute();
            }
        }
    }


    private void init_top_banner_view() {

        String cat_title = MyApplication.app_sharedPreferences.getString("cat_title",
                "");

        try {
            if (!TextUtils.isEmpty(cat_title)) {
                if (arraylist_top_adver != null) {
                    if (arraylist_top_adver.size() > 0) {

                        updatedList = new ArrayList<>();
                        for (int i = 0; i < arraylist_top_adver.size(); i++) {
                            HomeVideoPojo dataItem = arraylist_top_adver.get(i);

                            if (!TextUtils.isEmpty(dataItem.getCategory_name())) {
                                if (cat_title.contains(dataItem.getCategory_name())) {
                                    updatedList.add(dataItem);
                                }
                            }
                        }

                        if (updatedList != null) {
                            if (updatedList.size() > 0) {
                                TopBanner_Adapter topBanner_adapter = new TopBanner_Adapter
                                        (context, updatedList);
                                home_topBanner_Pager.setAdapter(topBanner_adapter);

                            }
                        }


                    } else {
                        updatedList = null;
                        TopBanner_Adapter _topBanner_adapter = new TopBanner_Adapter(context,
                                arraylist_top_adver);
                        home_topBanner_Pager.setAdapter(_topBanner_adapter);
                        _topBanner_adapter.notifyDataSetChanged();
                    }
                } else {
                    updatedList = null;
                    TopBanner_Adapter _topBanner_adapter = new TopBanner_Adapter(context,
                            arraylist_top_adver);
                    home_topBanner_Pager.setAdapter(_topBanner_adapter);
                    _topBanner_adapter.notifyDataSetChanged();
                }

            } else {
                updatedList = null;
                TopBanner_Adapter _topBanner_adapter = new TopBanner_Adapter(context,
                        arraylist_top_adver);
                home_topBanner_Pager.setAdapter(_topBanner_adapter);
                _topBanner_adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            updatedList = null;
            TopBanner_Adapter _topBanner_adapter = new TopBanner_Adapter(context,
                    arraylist_top_adver);
//            home_topBanner_Pager.setAdapter(_topBanner_adapter);
//            _topBanner_adapter.notifyDataSetChanged();
            e.printStackTrace();
        }


//        home_topBanner_Pager.setCurrentItem(0);
//        home_topBanner_Pager.setOffscreenPageLimit(1);
//        home_topBanner_Pager.getLayoutParams().height = (int) (MyUtility.getScreenWidth
//                (home_topBanner_Pager.getContext()) / (1.4));
//
//        topBanner_indicator.setViewPager(home_topBanner_Pager);
//        home_topBanner_Pager.setOnTouchListener(new InterceptTouchListener(new
//                                                                                   InterceptTouchListener.OnItemClickListener() {
//                                                                                       @Override
//                                                                                       public void
//                                                                                       onItemClick(View v, int position) {
//                                                                                           HomeVideoPojo top_banner_pojo;
//
//                                                                                           if
//                                                                                           (updatedList != null && updatedList.size() > 0) {
//                                                                                               top_banner_pojo = updatedList.get(position);
//                                                                                           } else {
//                                                                                               top_banner_pojo = arraylist_top_adver.get(position);
//                                                                                           }
//
//
//                                                                                           MyApplication.getInstance().trackEvent("Video", top_banner_pojo.getRedirect_link(), top_banner_pojo.getTitle());
//
//
//                                                                                           if
//                                                                                           (!TextUtils.isEmpty(top_banner_pojo.getVideo_table_type()) && top_banner_pojo.getVideo_table_type()
//                                                                                                   .equalsIgnoreCase("video")) {
//                                                                                               try {
//                                                                                                   PagersAutoScrollControllerCallback.getInstance().notify(false, "home", position);
//                                                                                               } catch (Exception e) {
//                                                                                                   e.printStackTrace();
//                                                                                               }
//                                                                                        /*
//                                                                                        MyUtility
//                                                                                        .saveTracksInDB(context, top_banner_pojo.getYoutube_link(),
//                                                                                                       top_banner_pojo.getYoutube_link(), "Video", "0", top_banner_pojo.getTitle());*/
//                                                                                               MyUtility.playYouTubeVideo(context, top_banner_pojo.getYoutube_id(), top_banner_pojo.getYoutube_link(), top_banner_pojo.getVideo_table_type(), top_banner_pojo.getTitle());
//                                                                                           } else if
//                                                                                           (!TextUtils.isEmpty(top_banner_pojo.getVideo_table_type()) && top_banner_pojo.getVideo_table_type()
//                                                                                                           .equalsIgnoreCase("other")) {
//                                                                                               try {
//                                                                                                   PagersAutoScrollControllerCallback.getInstance().notify(false, "home", position);
//                                                                                               } catch (Exception e) {
//                                                                                                   e.printStackTrace();
//                                                                                               }
//                                                                                          /*
//                                                                                          MyUtility.saveTracksInDB(context, top_banner_pojo.getYoutube_link(),
//                                                                                                       top_banner_pojo.getYoutube_link(), "Video", "0", top_banner_pojo.getTitle());*/
//                                                                                               Intent send = new Intent(context, PlayerActivity.class);
//                                                                                               send.putExtra("videoUrl", top_banner_pojo.getYoutube_link());
//                                                                                               send.putExtra("videoTitle", top_banner_pojo.getTitle());
//                                                                                               send.putExtra("imageURL", top_banner_pojo.getBanner_image());
//                                                                                               context.startActivity(send);
//                                                                                           } else if
//                                                                                           (!TextUtils.isEmpty(top_banner_pojo.getVideo_table_type()) && top_banner_pojo.getVideo_table_type()
//                                                                                                           .equalsIgnoreCase("chanel")) {
//                                                                                               try {
//                                                                                                   PagersAutoScrollControllerCallback.getInstance().notify(false, "home", position);
//                                                                                               } catch (Exception e) {
//                                                                                                   e.printStackTrace();
//                                                                                               }
//
//                                                                                               Intent send = new Intent(context, YouTube_Screen.class);
//                                                                                               send.putExtra("videoUrl", top_banner_pojo.getYoutube_link());
//                                                                                               send.putExtra("get_YouTubeID", top_banner_pojo.getYoutube_id());
//                                                                                               send.putExtra("videoTitle", top_banner_pojo.getTitle());
//
//                                                                                               context.startActivity(send);
//                                                                                           } else {
//
//                                                                                               MyUtility.handleItemClick(context, top_banner_pojo.getPackage_name(),
//                                                                                                       top_banner_pojo.getRedirect_link(), top_banner_pojo.getThumb(), "Video", top_banner_pojo.getOpen_with(), top_banner_pojo.getTitle());
//                                                                                           }
//                                                                                       }
//                                                                                   }
//
//        ));
    }

    private void populateGridView(List<Map.Entry<String, Long>> sortedApps) {
        // Create a custom adapter for the GridView
        // Set layout manager
        int numColumns = 4; // Change this as needed
        GridLayoutManager layoutManager = new GridLayoutManager(context, numColumns);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(context,
//                LinearLayoutManager.HORIZONTAL, false);
        Log.e("checkfreqapps", ">populateGridView>" + sortedApps.size());

//        rvFreqApps.setHasFixedSize(true);
//        rvFreqApps.setLayoutManager(layoutManager);
//        AppGridAdapter adapter = new AppGridAdapter(context, sortedApps, context.getPackageManager());
//        rvFreqApps.setAdapter(adapter);
//        Log.e("checkfreqapps", ">populateGridView222>" + rvFreqApps.getVisibility());
//        if (ad_view_banner != null) {
//            adRequesNew = new AdRequest.Builder().build();
//            ad_view_banner.loadAd(adRequesNew);
//        }
    }
    private void getFrequentApps() {
        Log.e("checkfreqapps", ">>");
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.getPackageName());
        if (mode == AppOpsManager.MODE_ALLOWED) {
            // Permission granted, proceed with fetching and displaying apps
            UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            long endTime = System.currentTimeMillis();
            long startTime = endTime - 24 * 60 * 60 * 1000; // 24 hours ago
            UsageEvents usageEvents = usageStatsManager.queryEvents(startTime, endTime);

            // Iterate through the events and collect app usage data
            Map<String, Long> appUsageMap = new HashMap<>();
            while (usageEvents.hasNextEvent()) {
                UsageEvents.Event event = new UsageEvents.Event();
                usageEvents.getNextEvent(event);
                if (event.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED) {
                    String packageName = event.getPackageName();
                    long usageCount = 0;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        usageCount = appUsageMap.getOrDefault(packageName, 0L);
                    }
                    appUsageMap.put(packageName, usageCount + 1);
                }
            }

            // Sort the apps by their usage count
            List<Map.Entry<String, Long>> sortedApps = new ArrayList<>(appUsageMap.entrySet());
            Collections.sort(sortedApps, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
            Log.e("cheedckfrag", ">sortedApps>" + sortedApps.size());

            // Retrieve application labels and icons
            PackageManager packageManager = context.getPackageManager();
            for (Map.Entry<String, Long> entry : sortedApps) {
                String packageName = entry.getKey();
                try {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
                    String appName = packageManager.getApplicationLabel(applicationInfo).toString();
                    Drawable appIcon = packageManager.getApplicationIcon(applicationInfo);
                    topAppNew.add(new AppInfoNew(appName, packageName, appIcon));
                    // Now you can use appName, packageName, and appIcon in your RecyclerView or wherever you display the app information
                    Log.e("checkfreqapps", "App Name: " + appName + ", Package Name: " + packageName);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }

            // Now you have the list of topApps containing app names, package names, and icons
            // You can use this list to populate your RecyclerView or wherever you display the app information
        } else {
            // Permission denied, request permission from the user
            context.startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        }
    }

    // Define a data class to hold app information


//    private void getFrequntApps() {
//        Log.e("checkfreqapps", ">>");
//        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
//        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.getPackageName());
//        if (mode == AppOpsManager.MODE_ALLOWED) {
//            // Permission granted, proceed with fetching and displaying apps
//            UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
//            long endTime = System.currentTimeMillis();
//            long startTime = endTime - 24 * 60 * 60 * 1000; // 24 hours ago
//            UsageEvents usageEvents = usageStatsManager.queryEvents(startTime, endTime);
//
//            // Iterate through the events and collect app usage data
//            Map<String, Long> appUsageMap = new HashMap<>();
//            while (usageEvents.hasNextEvent()) {
//                UsageEvents.Event event = new UsageEvents.Event();
//                usageEvents.getNextEvent(event);
//                if (event.getEventType() == UsageEvents.Event.ACTIVITY_RESUMED) {
//                    String packageName = event.getPackageName();
//                    long usageCount = 0;
//                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//                        usageCount = appUsageMap.getOrDefault(packageName, 0L);
//                    }
//                    appUsageMap.put(packageName, usageCount + 1);
//                }
//            }
//
//            // Sort the apps by their usage count
//            List<Map.Entry<String, Long>> sortedApps = new ArrayList<>(appUsageMap.entrySet());
//            Collections.sort(sortedApps, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
//            Log.e("cheedckfrag", ">sortedApps>" + sortedApps.size());
//            topApps = sortedApps.subList(0, Math.min(sortedApps.size(), 8));
//
//            // Now you can use sortedApps to populate your RecyclerView
//            // For example, you can create a data class representing an app and use it in the RecyclerView adapter
//        } else {
//            // Permission denied, request permission from the user
//            context.startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
//        }
//    }

    public void adddefaultView() {

        LayoutInflater inflator = LayoutInflater.from(context);
        //TODO
        View top_advertisement = inflator.inflate(R.layout.layout_top_banner, null);
        View news_video_layout = inflator.inflate(R.layout.layout_news_video, null);
        View other_services = inflator.inflate(R.layout.layout_other_services, null);
        View admob_rkt_one = inflator.inflate(R.layout.admob_rkt_one, null);
        View admob_one = inflator.inflate(R.layout.admob_nubit_one, null);
        View services = inflator.inflate(R.layout.layout_services, null);
        View freq_app_layout = inflator.inflate(R.layout.layout_freq_apps, null);
        View new_ads_layout = inflator.inflate(R.layout.layout_ads, null);
        View news1 = inflator.inflate(R.layout.layout_news_top, null);
        View news22 = inflator.inflate(R.layout.layout_news_to_test, null);
        View admob_rkt_two = inflator.inflate(R.layout.admob_rkt_two, null);
        View admob_two = inflator.inflate(R.layout.admob_nubit_two, null);
        View top_apps = inflator.inflate(R.layout.layout_topapps, null);
        View news3 = inflator.inflate(R.layout.layout_bottom_news, null);
        View admob_rkt_three = inflator.inflate(R.layout.admob_rkt_three, null);
        View admob_three = inflator.inflate(R.layout.admob_nubit_three, null);
        View entertainment = inflator.inflate(R.layout.layout_entertainment, null);
        View bottom_advertisement = inflator.inflate(R.layout.layout_bottom_adver, null);
        View games = inflator.inflate(R.layout.layout_games_home, null);
        View admob_rkt_four = inflator.inflate(R.layout.admob_rkt_four, null);
        View admob_four = inflator.inflate(R.layout.admob_nubit_four, null);
        View external_games = inflator.inflate(R.layout.layout_flash_games, null);
        View wallpaper = inflator.inflate(R.layout.layout_wallpaper, null);
        View admob_five = inflator.inflate(R.layout.admob_nubit_five, null);
        View news2 = inflator.inflate(R.layout.layout_middle_news, null);
        View taboola_view = inflator.inflate(R.layout.layout_taboola, null);
        View news4 = inflator.inflate(R.layout.layout_recom_news, null);
        View admob_rkt_five = inflator.inflate(R.layout.admob_rkt_five, null);
        View power_by = inflator.inflate(R.layout.layout_poweredby, null);


//        layout_container_nubitview.addView(top_advertisement);
//        layout_container_nubitview.addView(admob_rkt_one);
//        layout_container_nubitview.addView(admob_one);
//        layout_container_nubitview.addView(news_video_layout);
//        layout_container_nubitview.addView(other_services);
        layout_container_nubitview.addView(services);
        layout_container_nubitview.addView(new_ads_layout);
        layout_container_nubitview.addView(freq_app_layout);
        layout_container_nubitview.addView(news1);
        layout_container_nubitview.addView(news22);
//        layout_container_nubitview.addView(admob_rkt_two);
//        layout_container_nubitview.addView(admob_two);
        layout_container_nubitview.addView(top_apps);
        layout_container_nubitview.addView(news2);
//        layout_container_nubitview.addView(admob_rkt_three);
//        layout_container_nubitview.addView(admob_three);
        layout_container_nubitview.addView(external_games);
        layout_container_nubitview.addView(entertainment);
        layout_container_nubitview.addView(news4);
        layout_container_nubitview.addView(games);
//        layout_container_nubitview.addView(admob_rkt_four);
//        layout_container_nubitview.addView(admob_four);
//        layout_container_nubitview.addView(wallpaper);
//        layout_container_nubitview.addView(bottom_advertisement);
        layout_container_nubitview.addView(taboola_view);
//        layout_container_nubitview.addView(admob_rkt_five);
//        layout_container_nubitview.addView(admob_five);
        layout_container_nubitview.addView(news3);

        layout_container_nubitview.addView(power_by);
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Do something after 5s = 5000ms
//                Log.e("checkacrra",">>" + arrayTesting.size());
//                view_Map.put("top apps", top_apps);
//                for (int i = 0; i < arrayTesting.size(); i++) {
//                    if (arrayTesting.get(i).getItem_key().equals("design_one")){
//                        view_Map.put(arrayTesting.get(i).getTitle(), news_video_layout);
//                    }else if (arrayTesting.get(i).getItem_key().equals("design_two")){
//                        view_Map.put(arrayTesting.get(i).getTitle(), freq_app_layout);
//                    }else if (arrayTesting.get(i).getItem_key().equals("design_three")){
//                        view_Map.put(arrayTesting.get(i).getTitle(), news1);
//                    }else if (arrayTesting.get(i).getItem_key().equals("design_four")){
//                        view_Map.put(arrayTesting.get(i).getTitle(), top_apps);
//                    }else{
//                        view_Map.put(arrayTesting.get(i).getTitle(), external_games);
//                    }
//                }
//            }
//        }, 2000);


//        view_Map.put("news video", news_video_layout);
//        view_Map.put("Ads One", new_ads_layout);
//        view_Map.put("Ads Two", new_ads_layout);
//        view_Map.put("Ads Three", new_ads_layout);
//        view_Map.put("freq used apps", freq_app_layout);
//        view_Map.put("trending news", news1);
//        view_Map.put("top apps", top_apps);
//        view_Map.put("games", external_games);
//        view_Map.put("sports news", news2);
////        view_Map.put("news", news2);
//        view_Map.put("recommended news", news4);
//

//
//        view_Map.put("top_advertisement", top_advertisement);
//        view_Map.put("other_services", other_services);
//        view_Map.put("admob_rkt_one", admob_rkt_one);
//        view_Map.put("admob_one", admob_one);
////        view_Map.put("news_video", news_video_layout);
//
//        view_Map.put("services", services);
//        view_Map.put("news1", news22);
//        view_Map.put("admob_rkt_two", admob_rkt_two);
//        view_Map.put("admob_two", admob_two);
//
////        view_Map.put("services", services);
//////        view_Map.put("freqapps", freq_app_layout);
////        view_Map.put("news22", news22);
////        view_Map.put("admob_rkt_two", admob_rkt_two);
////        view_Map.put("admob_two", admob_two);
//
//        view_Map.put("entertainment", entertainment);
//        view_Map.put("news4", news4);
//        view_Map.put("admob_rkt_three", admob_rkt_three);
//        view_Map.put("admob_three", admob_three);
//
//
//        view_Map.put("top_apps", top_apps);
//        view_Map.put("games", games);
//        view_Map.put("flash_games", external_games);
//        view_Map.put("admob_rkt_four", admob_rkt_four);
//
//
//        view_Map.put("admob_four", admob_four);
////        view_Map.put("wallpaper", wallpaper);
//        view_Map.put("admob_rkt_five", admob_rkt_five);
//        view_Map.put("admob_five", admob_five);
//
//
//        view_Map.put("bottom_advertisement", bottom_advertisement);
//        view_Map.put("news2", news2);
//        view_Map.put("taboola_view", taboola_view);
//        view_Map.put("news3", news3);
//        view_Map.put("power_by", power_by);
    }


    private void init_other_services_view() {
//        Other_Services_Adapter other_services_adapter = new Other_Services_Adapter(context,
//                arraylist_other_ser);
//        other_ser_recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
//                LinearLayoutManager.HORIZONTAL, false);
//        other_ser_recyclerView.setLayoutManager(layoutManager);
//        other_ser_recyclerView.setAdapter(other_services_adapter);

    }

    private void init_service_view() {

        final Service_Adapter service_grid_adapter;
        if (arraylist_services != null) {
            if (arraylist_services.size() > 8) {
                dropDownViewControler.setVisibility(View.VISIBLE);
                dropDownViewControler.bindViews();
                service_grid_adapter = new Service_Adapter(context, dropDownViewControler
                        .setUpList(arraylist_services));
                service_grid.setAdapter(service_grid_adapter);
                dropDownViewControler.setDropDownClick(new DropDownViewControler
                        .OnDropDownViewClick() {
                    @Override
                    public void onClick(List<Services_pojo> data) {

                        service_grid_adapter.updateList(data);


                    }
                });
            } else {
                dropDownViewControler.setVisibility(View.GONE);
                service_grid_adapter = new Service_Adapter(context, arraylist_services);
                service_grid.setAdapter(service_grid_adapter);
            }
        }
//        MyGridView.setDynamicHeight(service_grid);
    }

    private void init_TopNews_Views() {
        String get_trending_SavedJson = MyApplication.app_sharedPreferences.getString
                ("save_app_trending_news_Json",
                        "");
        if (!TextUtils.isEmpty(get_trending_SavedJson)) {
            Gson gson = new Gson();
            Log.e("chefef", ">>" + get_trending_SavedJson);
            if (get_trending_SavedJson != null) {
                Type type = new TypeToken<List<TrendingNewsModel>>() {
                }.getType();
                arraylist_trending_new_new = gson.fromJson(get_trending_SavedJson, type);
            }
        }

        Log.e("checknews", ">" + arraylist_trending_new_new);
        Top_News_Adapter top_news_adapter = new Top_News_Adapter(context, arraylist_trending_new_new);
        top_News_recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        top_News_recyclerView.setLayoutManager(layoutManager);
        top_News_recyclerView.setNestedScrollingEnabled(false);
        top_News_recyclerView.setAdapter(top_news_adapter);


    }

    private void init_TopNews_View_test() {
//        Top_News_Adapter top_news_adapter = new Top_News_Adapter(context, arraylist_top_news);
//        top_News_recyclerVie_test.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
//                LinearLayoutManager.VERTICAL, false);
//        top_News_recyclerVie_test.setLayoutManager(layoutManager);
//        top_News_recyclerVie_test.setNestedScrollingEnabled(false);
//        top_News_recyclerVie_test.setAdapter(top_news_adapter);


    }

    private void init_Recom_News_Views() {
//        Recom_News_Adapter reco_news_adapter = new Recom_News_Adapter(context,
//                arraylist_recom_news);
        String get_SavedJson = MyApplication.app_sharedPreferences.getString
                ("save_app_vistory_Json",
                        "");
        if (!TextUtils.isEmpty(get_SavedJson)) {
            Gson gson = new Gson();
            Log.e("chefef", ">>" + get_SavedJson);
            if (get_SavedJson != null) {
                Type type = new TypeToken<List<VistoryModel>>() {
                }.getType();
                arraylist_recom_new_new = gson.fromJson(get_SavedJson, type);
            }
//            vistoryResponse = gson.fromJson(get_SavedJson, VistoryModel.class);
//            VistoryModel data = vistoryResponse;
//            arraylist_recom_new_new.add(data);
//            Configuration configuration = vistoryResponse.getConfiguration();
//            try {
//                arraylist_top_adver.addAll(data.getVideos());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }

        WebSettings s = webviewVistory.getSettings();
        s.setBuiltInZoomControls(false);
//        s.setAppCachePath(context.getCacheDir().getPath());
//        s.setAppCacheEnabled(true);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setJavaScriptEnabled(true);
        webviewVistory.getSettings().setDomStorageEnabled(true);
        webviewVistory.getSettings().setDatabaseEnabled(true);

        webviewVistory.getSettings().setJavaScriptEnabled(true); // Enable JavaScript execution

//        // Add JavaScript interface to listen for messages (framework-specific approach needed)
//        webviewVistory.addJavascriptInterface(new JavaScriptShareInterface(), "bridge"); // Example using custom interface
//
//        // Load HTML content
//        webviewVistory.loadUrl("your_web_page.html");

//        webviewVistory.getSettings().setJavaScriptEnabled(true);
//        webviewVistory.getSettings().setDomStorageEnabled(true);
//        webviewVistory.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webviewVistory.getSettings().setAllowFileAccess(true);
//        webviewVistory.getSettings().setAllowContentAccess(true);
//        webviewVistory.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webviewVistory.loadUrl("https://www.vistory.mobi/minus-one");
//        webviewVistory.setWebViewClient(new My WebViewClient());
        webviewVistory.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading (WebView view, String url){
                Log.e("checkurlss", ">>" + url);
                String[] parts = url.split("/");

                // Fetch the last part
                String lastPart = parts[parts.length - 1];

                System.out.println("Last part of the URL: " + correctUrl(lastPart));
                MyUtility.handleItemClick(context, "", url, "",
                        "Top News", "", correctUrl(lastPart));
                // Handle the URL here
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(intent);
                return true; // Returning true means the host application handles the URL
            }
        });

//        webviewVistory.setWebViewClient(new WebViewClient() {
//            @Override
//            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//                // Check if the request is for a specific resource
////                Log.e("checkurlss",">1>" + request.getUrl().getPath());
//                if (request.getUrl().getPath().equals("/vistory/api/v1/articles/getArticles")) {
//                    Log.e("checkurlss",">in>" + request.getUrl());
//
////                    try {
////                        // Load the local CSS file from the assets folder
////                        AssetManager assetManager = getAssets();
////                        InputStream inputStream = assetManager.open("styles.css");
////                        return new WebResourceResponse("text/css", "UTF-8", inputStream);
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                        return null;
////                    }
//                }
//                return super.shouldInterceptRequest(view, request);
//            }
//        });


//        Recom_News_Adapter reco_news_adapter = new Recom_News_Adapter(context,
//                arraylist_recom_new_new);
//        news_recom_recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
//                LinearLayoutManager.VERTICAL, false);
//        news_recom_recyclerView.setLayoutManager(layoutManager);
//        news_recom_recyclerView.setNestedScrollingEnabled(false);
//        news_recom_recyclerView.setAdapter(reco_news_adapter);


    }

    public void handleShareRequest(String path) {
        // Construct your intent with the path for sharing
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain"); // Adjust type based on your sharing content
        shareIntent.putExtra(Intent.EXTRA_TEXT, path);
        context.startActivity(Intent.createChooser(shareIntent, "Share"));

    }

    private String correctUrl(String url) {
        try {
            // Decode the URL to replace URL-encoded characters with their original representations
            String decodedUrl = URLDecoder.decode(url, "UTF-8");

            return decodedUrl;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return url; // Return original URL if decoding fails
        }
    }

    private String removeSymbols(String text) {
        // Define a regular expression to match symbols
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\s]");

        // Replace symbols with an empty string
        return pattern.matcher(text).replaceAll("");
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.e("checkurlss",">>" + url);
//            if (Uri.parse(url).getHost().equals("www.example.com")) {
//                // This is your website, so do not override; let WebView load the page
//                return false;
//            }
//
//            // Otherwise, the link is not for a page on your site, so launch another Activity
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            context.startActivity(intent);
            return true;
        }
    }

    private void init_TopApps_Views() {
        String get_apps_SavedJson = MyApplication.app_sharedPreferences.getString
                ("save_app_apps_new_Json",
                        "");
        if (!TextUtils.isEmpty(get_apps_SavedJson)) {
            Gson gson = new Gson();
            Log.e("chefef",">>" + get_apps_SavedJson);
            if (get_apps_SavedJson != null) {
                Type type = new TypeToken<List<AppsModel>>() {}.getType();
                arraylist_top_apps_new = gson.fromJson(get_apps_SavedJson, type);
            }
        }
//        AppGridAdapter adapter = new AppGridAdapter(context, topApps, context.getPackageManager());

        TopAppsAdapter topApps_adapter = new TopAppsAdapter(context, arraylist_top_apps_new);
        LinearLayoutManager layoutManager_topApps = new LinearLayoutManager(context,
                LinearLayoutManager
                        .HORIZONTAL, false);
        topapps_recyclerView.setLayoutManager(layoutManager_topApps);
        topapps_recyclerView.setAdapter(topApps_adapter);

        topapps_recyclerView.setOnTouchListener(new InterceptTouchListener(new
                                                                                   InterceptTouchListener.OnItemClickListener() {
                                                                                       @Override
                                                                                       public void
                                                                                       onItemClick(View v, int position) {

                                                                                           final AppsModel top_apps_pojo = arraylist_top_apps_new.get(position);
//                                                                                           MyApplication.getInstance().trackEvent("Apps", top_apps_pojo.getRedirect_link(), top_apps_pojo.getTitle());
//                                                                                           MyUtility.handleItemClick(context, top_apps_pojo.getPackage_name(),
//                                                                                                   top_apps_pojo.getRedirect_link(), top_apps_pojo.getBanner_thumb_image(), "Apps", top_apps_pojo.getOpen_with(), top_apps_pojo.getTitle());
                                                                                           MyUtility.handleItemClick(context, "", top_apps_pojo
                                                                                                   .getRedirectLink(), top_apps_pojo.getBannerImage(), "Apps", "", top_apps_pojo.getTitle());

                                                                                       }
                                                                                   }
        ));

    }

    private void init_BottomNews_Views() {

        bottomNews_recyclerView.setHasFixedSize(true);
        bottomNews_recyclerView.setNestedScrollingEnabled(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        bottomNews_recyclerView.setLayoutManager(layoutManager);


        String pub_name = MyApplication.app_sharedPreferences.getString("pub_name",
                "");

        try {
            if (!TextUtils.isEmpty(pub_name)) {


                if (arraylist_newsbyPub != null) {
                    if (arraylist_newsbyPub.size() > 0) {

                        ArrayList<NewsByPub_Pojo> updatedList = new ArrayList<>();

                        for (int i = 0; i < arraylist_newsbyPub.size(); i++) {
                            NewsByPub_Pojo dataItem = arraylist_newsbyPub.get(i);

                            if (!TextUtils.isEmpty(dataItem.getNewsBy())) {
                                if (pub_name.contains(dataItem.getNewsBy())) {
                                    updatedList.add(dataItem);
                                }
                            }


                        }

                        if (updatedList != null) {
                            if (updatedList.size() > 0) {
                                NewsByPub_Adapter newsByPub_adapter = new NewsByPub_Adapter
                                        (context, updatedList);

                                bottomNews_recyclerView.setAdapter(newsByPub_adapter);

                            }
                        }


                    } else {
                        Bottom_News_Adapter bottomNews_adapter = new Bottom_News_Adapter
                                (context,
                                        arraylist_bottom_news);
                        bottomNews_recyclerView.setAdapter(bottomNews_adapter);
                    }
                } else {
                    Bottom_News_Adapter bottomNews_adapter = new Bottom_News_Adapter
                            (context,
                                    arraylist_bottom_news);
                    bottomNews_recyclerView.setAdapter(bottomNews_adapter);
                }

            } else {
                Bottom_News_Adapter bottomNews_adapter = new Bottom_News_Adapter(context,
                        arraylist_bottom_news);
                bottomNews_recyclerView.setAdapter(bottomNews_adapter);
            }
        } catch (Exception e) {
            Bottom_News_Adapter bottomNews_adapter = new Bottom_News_Adapter(context,
                    arraylist_bottom_news);
            bottomNews_recyclerView.setAdapter(bottomNews_adapter);
            e.printStackTrace();
        }
    }

    private void init_Entertainment_Views() {
        Entertainment_Adapter enter_adapter = new Entertainment_Adapter(context,
                arraylist_entertainment);
        entertain_recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false);
        entertain_recyclerView.setLayoutManager(layoutManager);
        entertain_recyclerView.setAdapter(enter_adapter);
        /* entertain_recyclerView.addOnScrollListener(new CustomScrollListener());*/


        entertain_recyclerView.setOnTouchListener(new InterceptTouchListener(new
                                                                                     InterceptTouchListener.OnItemClickListener() {
                                                                                         @Override
                                                                                         public void
                                                                                         onItemClick(View v, int position) {
                                                                                             final Entertainment_pojo entertainment_pojo = arraylist_entertainment.get(position);
                                                                                             MyApplication.getInstance().trackEvent("Display Banner 1", entertainment_pojo.getRedirect_link(), entertainment_pojo.getTitle());

                                                                                             MyUtility.handleItemClick(context, entertainment_pojo.getPackage_name(),
                                                                                                     entertainment_pojo.getRedirect_link(), entertainment_pojo.getBanner_thumb_image()
                                                                                                     , "Display Banner 1", entertainment_pojo.getOpen_with(), entertainment_pojo.getTitle());
                                                                                         }
                                                                                     }));
    }

    private void init_BottomAdv_Views() {

        Bottom_Adv_Adapter bottomAdv_adapter = new Bottom_Adv_Adapter(context,
                arraylist_b_a_banner);
        bottomAdv_recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false);
        bottomAdv_recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setNestedScrollingEnabled(false);
        bottomAdv_recyclerView.setAdapter(bottomAdv_adapter);

        bottomAdv_recyclerView.setOnTouchListener(new InterceptTouchListener(new
                                                                                     InterceptTouchListener.OnItemClickListener() {
                                                                                         @Override
                                                                                         public void
                                                                                         onItemClick(View v, int position) {
                                                                                             Bottom_adv_Pojo bottomAdv_pojo = arraylist_b_a_banner.get(position);
                                                                                             MyApplication.getInstance().trackEvent("Display Banner 2", bottomAdv_pojo.getRedirect_link(), bottomAdv_pojo.getTitle());

                                                                                             MyUtility.handleItemClick(context, bottomAdv_pojo.getPackage_name(),
                                                                                                     bottomAdv_pojo.getRedirect_link(), bottomAdv_pojo.getBanner_image(), "Display Banner 2", bottomAdv_pojo.getOpen_with(), bottomAdv_pojo.getTitle());
                                                                                         }
                                                                                     }));

    }

    private void init_Games_Views() {
        String get_apps_SavedJson = MyApplication.app_sharedPreferences.getString
                ("save_app_games_new_Json",
                        "");
        if (!TextUtils.isEmpty(get_apps_SavedJson)) {
            Gson gson = new Gson();
            Log.e("chefef",">>" + get_apps_SavedJson);
            if (get_apps_SavedJson != null) {
                Type type = new TypeToken<List<AppsModel>>() {}.getType();
                arraylist_top_games_new = gson.fromJson(get_apps_SavedJson, type);
            }
        }
        GamesAdapter games_adapter = new GamesAdapter(context, arraylist_top_games_new);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false);
        games_recyclerView.setLayoutManager(layoutManager);
        games_recyclerView.setNestedScrollingEnabled(false);
        games_recyclerView.setAdapter(games_adapter);

        games_recyclerView.setOnTouchListener(new InterceptTouchListener(new
                                                                                 InterceptTouchListener.OnItemClickListener() {
                                                                                     @Override
                                                                                     public void
                                                                                     onItemClick
                                                                                             (View v, int
                                                                                                     position) {
                                                                                         final AppsModel games_pojo = arraylist_top_games_new.get(position);
                                                                                         MyApplication.getInstance().trackEvent("Games",
                                                                                                 games_pojo.getRedirectLink(), games_pojo.getTitle());

//                                                                                         MyUtility.handleItemClick(context, games_pojo.getPackage_name(), games_pojo
//                                                                                                         .getRedirectLink(), games_pojo.getBanner_thumb_image(), "Games",
//                                                                                                 games_pojo.getOpen_with(), games_pojo.getTitle());
                                                                                         MyUtility.handleItemClick(context, "", games_pojo
                                                                                                         .getRedirectLink(), games_pojo.getBannerImage(), "Games",
                                                                                                 "", games_pojo.getTitle());
                                                                                     }
                                                                                 }));

    }

    private void init_external_game_view() {

//        External_Game_Adapter external_Game_Adapter = new External_Game_Adapter(context,
//                arraylist_Flashgames);
//        external_games_recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
//                LinearLayoutManager.HORIZONTAL, false);
//        external_games_recyclerView.setLayoutManager(layoutManager);
//        external_games_recyclerView.setAdapter(external_Game_Adapter);
//
//        external_games_recyclerView.setOnTouchListener(new InterceptTouchListener(new
//                                                                                          InterceptTouchListener.OnItemClickListener() {
//                                                                                              @Override
//                                                                                              public void onItemClick(View v, int position) {
//                                                                                                  External_game_pojo externalGame_Pojo = arraylist_Flashgames.get(position);
//                                                                                                  MyApplication.getInstance().trackEvent("Html Games",
//                                                                                                          externalGame_Pojo.getRedirect_link(), externalGame_Pojo.getTitle());
//                                                                                                  MyUtility.handleItemClick(context, "", externalGame_Pojo.getRedirect_link()
//                                                                                                          , externalGame_Pojo.getBanner_thumb_image(), "HTML Game",
//                                                                                                          externalGame_Pojo.getOpen_with(), externalGame_Pojo.getTitle());
//                                                                                              }
//                                                                                          }));

    }

    private void init_Wallpaper_Views() {
        final Wallpaper_Adapter wallpaper_adapter = new Wallpaper_Adapter(context,
                arraylist_wallpaper);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false);
//        wallpaper_recyclerView.setLayoutManager(layoutManager);
//        wallpaper_recyclerView.setAdapter(wallpaper_adapter);

//        wallpaper_recyclerView.setOnTouchListener(new InterceptTouchListener(new
//                                                                                     InterceptTouchListener.OnItemClickListener() {
//                                                                                         @Override
//                                                                                         public void
//                                                                                         onItemClick(View v, int position) {
//                                                                                             final Wallpaper_Pojo wallpaper_pojo = arraylist_wallpaper.get(position);
////                                                                                             MyApplication.getInstance().trackEvent("Wallpaper",
////                                                                                                     wallpaper_pojo.getRedirect_link(), wallpaper_pojo.getTitle());
//                                                                                             MyUtility.saveTracksInDB(context, arraylist_wallpaper.get(position)
//                                                                                                             .getBanner_image(), arraylist_wallpaper.get(position).getRedirect_link(),
//                                                                                                     "Wallpaper", "0", arraylist_wallpaper.get(position).getTitle(), "");
//
//                                                                                             if
//                                                                                             (MyUtility.isConnectedToInternet()) {
//
//
//                                                                                                 if (!TextUtils.isEmpty(wallpaper_pojo.getWallpaper_type())) {
//                                                                                                     if ((wallpaper_pojo.getWallpaper_type()).equalsIgnoreCase("redirect")) {
//                                                                                                         MyUtility.handleItemClick(context, wallpaper_pojo
//                                                                                                                         .getPackage_name(), wallpaper_pojo.getRedirect_link(), wallpaper_pojo
//                                                                                                                         .getRedirect_link(), "wallpaper", wallpaper_pojo.getOpen_with(),
//                                                                                                                 getResources().getString(R.string.my_app_name));
//                                                                                                     } else {
//
//                                                                                                         Intent intent = new Intent(context, WallpaperActivity.class);
//                                                                                                         intent.putExtra("positionkey", position);
//                                                                                                         intent.putExtra("bundle", arraylist_wallpaper);
//                                                                                                         context.startActivity(intent);
//                                                                                                     }
//                                                                                                 } else {
//
//                                                                                                     Intent intent = new Intent(context, WallpaperActivity.class);
//                                                                                                     intent.putExtra("positionkey", position);
//                                                                                                     intent.putExtra("bundle", arraylist_wallpaper);
//                                                                                                     context.startActivity(intent);
//                                                                                                 }
//
//
//                                                                                             } else {
//                                                                                                 MyUtility.NoInternet_Msg(context);
//                                                                                             }
//                                                                                         }
//                                                                                     }));

    }


    private void hitMobinViewAPI() {

        String getLastrktViewShow = MyApplication.app_sharedPreferences.getString
                ("last_rktViewShow", "");
        String view_duration = MyApplication.app_sharedPreferences.getString("view_duration", "");

        if (!TextUtils.isEmpty(view_duration)) {
            try {
                if (Integer.parseInt(view_duration) > 500) {
                    MyApplication.app_editor.putString("view_duration", "2").apply();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (TextUtils.isEmpty(getLastrktViewShow)) {
            MyApplication.app_editor.putString("last_rktViewShow", MyUtility.getCurrentTime())
                    .apply();
            new hitMobinview_Async().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else if (!TextUtils.isEmpty(getLastrktViewShow) && TextUtils.isEmpty(view_duration)) {
            MyApplication.app_editor.putString("last_rktViewShow", MyUtility.getCurrentTime())
                    .apply();
            new hitMobinview_Async().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else if (!TextUtils.isEmpty(view_duration)) {
            if ((MyUtility.getTimeDifference_nubit("rktView")) > (Long.parseLong(view_duration))) {
                MyApplication.app_editor.putString("last_rktViewShow", MyUtility.getCurrentTime())
                        .apply();
                new hitMobinview_Async().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            }
        }


    }


    private void init_MiddleNews_Views() {

        String get_sports_SavedJson = MyApplication.app_sharedPreferences.getString
                ("save_app_sports_news_Json",
                        "");
        if (!TextUtils.isEmpty(get_sports_SavedJson)) {
            Gson gson = new Gson();
            Log.e("chefef",">>" + get_sports_SavedJson);
            if (get_sports_SavedJson != null) {
                Type type = new TypeToken<List<TrendingNewsModel>>() {}.getType();
                arraylist_sports_new_new = gson.fromJson(get_sports_SavedJson, type);
            }
        }
        final Middle_News_Adapter middle_news_adapter = new Middle_News_Adapter(context,
                arraylist_sports_new_new);
        middleNews_recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false);
        middleNews_recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setNestedScrollingEnabled(false);
        middleNews_recyclerView.setAdapter(middle_news_adapter);

        middleNews_recyclerView.setOnTouchListener(new InterceptTouchListener(new
                                                                                      InterceptTouchListener.OnItemClickListener() {
                                                                                          @Override
                                                                                          public void
                                                                                          onItemClick(View v, int position) {

                                                                                              TrendingNewsModel middleNewsPojo = arraylist_sports_new_new.get(position);
                                                                                              MyApplication.getInstance().trackEvent("Middle News", middleNewsPojo.getRedirectLink(), middleNewsPojo.getTitle());
                                                                                              MyUtility.handleItemClick(context, "", middleNewsPojo.getRedirectLink(), middleNewsPojo.getImageUrl(),
                                                                                                      "Middle News", "", middleNewsPojo.getTitle());
//                                                                                              MyApplication.getInstance().trackEvent("Middle News", middleNewsPojo.getRedirect_link(), middleNewsPojo.getTitle());
//                                                                                              MyUtility.handleItemClick(context, middleNewsPojo.getPackage_name(), middleNewsPojo.getRedirect_link(), middleNewsPojo.getImage(),
//                                                                                                      "Middle News", middleNewsPojo.getOpen_with(), middleNewsPojo.getTitle());
                                                                                          }
                                                                                      }));

    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.btn_refresh) {
            pro_nointernet.setVisibility(View.VISIBLE);
            if (MyUtility.isConnectedToInternet()) {
                hitVersion();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pro_nointernet.setVisibility(View.GONE);
                        nointernet_parent.setVisibility(View.GONE);
                        layout_container_nubitview.setVisibility(View.VISIBLE);
                    }
                }, 4000);


            } else {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pro_nointernet.setVisibility(View.GONE);
                        MyUtility.NoInternet_Msg(context);
                    }
                }, 2000);


            }

        } else if (i == R.id.nav_search) {
            String query = edt_search.getText().toString(); // Replace this with your actual search query
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra("query", query);

            // Check if there's an app to handle this intent before starting it
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                // Handle the case where there's no app to handle the intent
                // For example, show a toast or display a message
            }
        }  else if (i == R.id.nav_icon) {
            drawerLayout.openDrawer(Gravity.LEFT);
        } else if (i == R.id.topapps_news_ref) {

            MyUtility.rotateRefreshButton_Anim(context, topapps_news_ref);
            //   MyUtility.rotateRecyclerView(context,topapps_recyclerView);
            MyUtility.scroll_app_RecyclerView(context, topapps_recyclerView, 3);


        } else if (i == R.id.img_games_ref) {

            MyUtility.rotateRefreshButton_Anim(context, img_games_ref);
            MyUtility.scroll_game_RecyclerView(context, games_recyclerView, 3);

        } else if (i == R.id.img_externalGames_ref) {

            MyUtility.rotateRefreshButton_Anim(context, img_externalGames_ref);
            MyUtility.scroll_externalgame_RecyclerView(context,
                    external_games_recyclerView, 3);
        } else if (i == R.id.img_wallpaper_ref) {

//            MyUtility.rotateRefreshButton_Anim(context, img_wallpaper_ref);
//            MyUtility.scroll_wallpaper_RecyclerView(context, wallpaper_recyclerView, 2);


        } else if (i == R.id.btn_add_pub) {


            if (arraylist_newspub != null) {
                if (arraylist_newspub.size() > 0) {
                    show_publisher_popup(context, arraylist_newspub);
                } else {
                    Toast.makeText(context, "Opps, Something went wrong, Pls try later",
                            Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(context, "Opps, Something went wrong, Pls try later",
                        Toast.LENGTH_LONG).show();
            }


        }
    }

    @Override
    public void ActionOnLanguageChange() {
      /*  MyApplication.app_sharedPreferences.getString("key_modifiedDat","");
        MyApplication.app_editor.remove("key_modifiedDat");*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new HitGetData_forLanguage().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        } else {
            new HitGetData_forLanguage().execute();
        }
    }

//    @Override
//    public void onVideoCatSelection() {
//        show_videoCat_popup(context, arraylist_videocat);
//    }

    @Override
    public void updateScrollCallBack(boolean update, String callFrom, int position) {


        if (update == true) {
            if (shouldScroll == true) {
                start_Scroll();
                scrollStatus = "home";
            }

        } else {
            /*  shouldScroll=false;*/
            /*     topBanner_indicator.setVisibility(View.VISIBLE);*/
            stop_Scroll(callFrom, position);

            if (callFrom.equalsIgnoreCase("loading")) {
                /*       topBanner_indicator.setVisibility(View.GONE);*/
                scrollStatus = "loading";


            } else {
                scrollStatus = "home";
            }


        }
    }

    private void start_Scroll() {

//        home_topBanner_Pager.startAutoScroll();


    }

    private void stop_Scroll(final String callFrom, final int position) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

//                home_topBanner_Pager.stopAutoScroll();
//
//
//                if (callFrom.equalsIgnoreCase("bottom_news_play")) {
//                    home_topBanner_Pager.setCurrentItem(0);
//                } else if (callFrom.equalsIgnoreCase("adapter")) {
//                    home_topBanner_Pager.setCurrentItem(position);
//                }


            }
        }, 1000);

    }

 /*   @Override
    public void updatePlayerCallBack(boolean z, String playerState) {


        if (playerState.equalsIgnoreCase("loading")) {


        } else if (playerState.equalsIgnoreCase("completed")) {
            Log.d("playerState", "completed");

            if (playerState.equalsIgnoreCase("completed")) {
                if (shouldScroll == true) {
                    start_Scroll();
                }

            }
        }

    }*/


    class CheckServerForNewData_asyn extends AsyncTask<Void, Void, String> {


        String refreshMyView = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isAsyncTaskRunning = true;
            /*  RefreshDataCallBack.getInstance().notifyOnRefreshStart();*/

        }

        @Override
        protected String doInBackground(Void... params) {

            String str_last_modificationDate = "";
            JSONObject save_json_jsonobject = null;

            String downloadNewData = "";


            try {

                String isNewDataAvailable = MyUtility.hitGetVersionAPI(context,
                        MyUtility.getversionAPI);


                if (!TextUtils.isEmpty(isNewDataAvailable)) {
                    JSONObject getversionAPI_jsonobject = null;
                    try {
                        getversionAPI_jsonobject = new JSONObject(isNewDataAvailable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String str_checkNewData_Json = "";
                    try {
                        str_checkNewData_Json = getversionAPI_jsonobject.getString("data");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    JSONObject jsonObject_getDat = null;
                    try {
                        jsonObject_getDat = new JSONObject(str_checkNewData_Json);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String isFullScreenBanner = "";
                    try {
                        isFullScreenBanner = jsonObject_getDat.getString
                                ("full_screen_banner");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    try {
                        str_last_modificationDate = jsonObject_getDat.getString("get_data_date");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String get_YouTube_Key = "";
                    try {
                        get_YouTube_Key = jsonObject_getDat.getString("youtube_key");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String get_admob_app_id = "";
                    try {
//                        get_admob_app_id = jsonObject_getDat.getString("admob_app_id");

                        if (get_admob_app_id != null && !TextUtils.isEmpty(get_admob_app_id)) {
                            MyApplication.app_editor.putString("admob_app_id", get_admob_app_id)
                                    .apply();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    JSONArray get_languageList;
                    try {
                        get_languageList = jsonObject_getDat.getJSONArray("langauge_list");
                        if (get_languageList != null) {
                            if (!TextUtils.isEmpty(get_languageList.toString())) {
                                MyApplication.app_editor.putString("language_array",
                                        get_languageList.toString()).apply();
                            }
                        }
                        // Log.d("get_languageList",get_languageList.toString());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    String ga_Key = "";
                    try {
                        ga_Key = jsonObject_getDat.getString("ga_key");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String advbanner_delay = "";
                    String advbanner_redirectLink = "";
                    String advbanner_banner_image = "";
                    String advbanner_ad_unit_id = "";
                    String advbanner_open_with = "";
                    String advbanner_title = "";
                    String webview_url = "";
                    String splash_time = "";

                    try {

                        if (isFullScreenBanner.equalsIgnoreCase("0") ||
                                isFullScreenBanner.equalsIgnoreCase("1")) {


                            try {
                                advbanner_redirectLink = (jsonObject_getDat.getJSONArray
                                        ("advbanner").getJSONObject(0)).getString("redirect_link");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                webview_url = (jsonObject_getDat.getJSONArray
                                        ("advbanner").getJSONObject(0)).getString("webview_url");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                splash_time = (jsonObject_getDat.getJSONArray
                                        ("advbanner").getJSONObject(0)).getString("splash_time");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {
                                advbanner_delay = (jsonObject_getDat.getJSONArray
                                        ("advbanner").getJSONObject(0)).getString("delay");

                                MyApplication.app_editor.putString("advbanner_delay",
                                        advbanner_delay).apply();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {
                                advbanner_banner_image = (jsonObject_getDat.getJSONArray
                                        ("advbanner").getJSONObject(0)).getString("banner_image");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {
                                advbanner_ad_unit_id = (jsonObject_getDat.getJSONArray
                                        ("advbanner").getJSONObject(0)).getString("ad_unit_id");

                                MyApplication.app_editor.putString("advbanner_ad_unit_id",
                                        advbanner_ad_unit_id).apply();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                advbanner_open_with = (jsonObject_getDat.getJSONArray
                                        ("advbanner").getJSONObject(0)).getString("open_with");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                advbanner_title = (jsonObject_getDat.getJSONArray
                                        ("advbanner").getJSONObject(0)).getString("title");

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    String str_get_lastUpdateDate = MyApplication.app_sharedPreferences.getString
                            ("key_modifiedDat", "");

                    if (TextUtils.isEmpty(str_get_lastUpdateDate)) {

                        Log.d("NubitStatus", "First Time USer");


                        downloadNewData = MyUtility.hitGetDataAPI(context,
                                MyUtility.getDataAPI);
                        if (!TextUtils.isEmpty(downloadNewData)) {
                            try {
                                save_json_jsonobject = new JSONObject(downloadNewData);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            //  Log.d("downloadedJson", String.valueOf(save_json_jsonobject));
                            if (save_json_jsonobject != null) {
                                if (!TextUtils.isEmpty(save_json_jsonobject.toString())) {
                                    MyApplication.app_editor.putString("save_app_Json", String
                                            .valueOf(save_json_jsonobject)).apply();
                                }
                            }
                            if (!TextUtils.isEmpty(str_last_modificationDate)) {
                                MyApplication.app_editor.putString("key_modifiedDat",
                                        str_last_modificationDate).apply();
                            }
                            if (!TextUtils.isEmpty(get_YouTube_Key)) {
                                MyApplication.app_editor.putString("youtube_key",
                                        get_YouTube_Key).apply();
                            }
                            if (!TextUtils.isEmpty(ga_Key)) {
                                MyApplication.app_editor.putString("ga_Key", ga_Key).apply();
                            }

                            MyApplication.app_editor.putBoolean("update_UI", true).apply();
                            refreshMyView = "yes";


                        } else {
                            refreshMyView = "noresponse";
                            Log.d("NubitStatus", "getData API not working, Pls check");
                        }


                    } else if (str_last_modificationDate.equalsIgnoreCase(str_get_lastUpdateDate)) {

                        MyApplication.app_editor.putBoolean("update_UI", false).apply();
                        Log.d("NubitStatus", "Repeated User");


                        if (MyUtility.isUpdateRequired(context,
                                isFullScreenBanner)) {
                            if (!TextUtils.isEmpty(isFullScreenBanner)) {


                                show_fullScreenBanner(isFullScreenBanner, advbanner_delay,
                                        advbanner_banner_image, advbanner_ad_unit_id,
                                        advbanner_redirectLink, advbanner_open_with,
                                        advbanner_title, webview_url, splash_time);

                            }
                        }


                    } else {

                        if (MyUtility.isUpdateRequired(context,
                                isFullScreenBanner)) {


                            if (!TextUtils.isEmpty(isFullScreenBanner) &&
                                    isFullScreenBanner.equalsIgnoreCase("0")) {
                                show_fullScreenBanner(isFullScreenBanner, advbanner_delay,
                                        advbanner_banner_image, advbanner_ad_unit_id,
                                        advbanner_redirectLink, advbanner_open_with,
                                        advbanner_title, webview_url, splash_time);
                                hitMobinViewAPI();
                                downloadNewData = MyUtility.hitGetDataAPI(context, MyUtility
                                        .getDataAPI);
                                if (!TextUtils.isEmpty(downloadNewData)) {

                                    try {
                                        save_json_jsonobject = new JSONObject(downloadNewData);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (save_json_jsonobject != null) {
                                        if (!TextUtils.isEmpty(save_json_jsonobject.toString())) {
                                            MyApplication.app_editor.putString("save_app_Json",
                                                    String.valueOf(save_json_jsonobject)).apply();
                                        }

                                    }

                                    if (!TextUtils.isEmpty(str_last_modificationDate)) {
                                        MyApplication.app_editor.putString("key_modifiedDat",
                                                str_last_modificationDate).apply();
                                    }
                                    if (!TextUtils.isEmpty(get_YouTube_Key)) {
                                        MyApplication.app_editor.putString("youtube_key",
                                                get_YouTube_Key).apply();
                                    }
                                    if (!TextUtils.isEmpty(ga_Key)) {
                                        MyApplication.app_editor.putString("ga_Key", ga_Key)
                                                .apply();
                                    }

                                    MyApplication.app_editor.putBoolean("update_UI", true).apply();
                                    MyApplication.app_editor.apply();
                                    refreshMyView = "yes";
                                    Log.d("NubitStatus", "New Data Found");

                                } else {
                                    refreshMyView = "noresponse";
                                    Log.d("nubit_Problem", "getData API not working, Pls check");

                                }
                                Log.d("nubit_Problem", "New application_version found, Pls check");
                            }
                            if (!TextUtils.isEmpty(isFullScreenBanner) &&
                                    isFullScreenBanner.equalsIgnoreCase("1")) {
                                show_fullScreenBanner(isFullScreenBanner, advbanner_delay,
                                        advbanner_banner_image, advbanner_ad_unit_id,
                                        advbanner_redirectLink, advbanner_open_with,
                                        advbanner_title, webview_url, splash_time);
                            }


                        } else {
                            hitMobinViewAPI();
                            downloadNewData = MyUtility.hitGetDataAPI(context,
                                    MyUtility.getDataAPI);
                            if (!TextUtils.isEmpty(downloadNewData)) {

                                try {
                                    save_json_jsonobject = new JSONObject(downloadNewData);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (save_json_jsonobject != null) {
                                    if (!TextUtils.isEmpty(save_json_jsonobject.toString())) {
                                        MyApplication.app_editor.putString("save_app_Json",
                                                String.valueOf(save_json_jsonobject)).apply();
                                    }

                                }

                                if (!TextUtils.isEmpty(str_last_modificationDate)) {
                                    MyApplication.app_editor.putString("key_modifiedDat",
                                            str_last_modificationDate).apply();
                                }
                                if (!TextUtils.isEmpty(get_YouTube_Key)) {
                                    MyApplication.app_editor.putString("youtube_key",
                                            get_YouTube_Key).apply();
                                }
                                if (!TextUtils.isEmpty(ga_Key)) {
                                    MyApplication.app_editor.putString("ga_Key", ga_Key).apply();
                                }

                                MyApplication.app_editor.putBoolean("update_UI", true).apply();
                                refreshMyView = "yes";
                                Log.d("NubitStatus", "New Data Found");

                            } else {
                                refreshMyView = "noresponse";
                                Log.d("nubit_Problem", "getData API not working, Pls check");

                            }
                        }

                    }

                } else {

                    refreshMyView = "noresponse";
                    Log.d("nubit_Problem", "getVersionList API not working, Pls check");

                }

                return refreshMyView;
            } catch (Exception e) {
                return "";
            }


        }

        @Override
        public void onPostExecute(String refreshMyView) {


            if (!TextUtils.isEmpty(refreshMyView) && refreshMyView.equalsIgnoreCase("noresponse")) {

                /*This block execute when we getting no response from getVersion API...It can be by
                nointernet or API problem....Lets check in below conditions if it is new user or
                have old saved data to showcase*/


                if (TextUtils.isEmpty(MyApplication.app_sharedPreferences.getString
                        ("save_app_Json", ""))) {
                    /*Clear saved json data and show the nointernet UI to handle the nointernet
                    connection, Because this is new user and dont have any previously saved data*/
                    MyApplication.app_editor.clear().apply();
                    layout_container_nubitview.setVisibility(View.GONE);
                    nointernet_parent.setVisibility(View.VISIBLE);

                } else {
                    /*Might be getversion request broken or no internet, but we have saved data
                    to showcase for now*/

                    layout_container_nubitview.setVisibility(View.VISIBLE);
                    nointernet_parent.setVisibility(View.GONE);
                    parseData();
                    /* shouldViewRefresh("no");*/

                }

            } else if (TextUtils.isEmpty(refreshMyView) && !TextUtils.isEmpty(MyApplication
                    .app_sharedPreferences.getString
                            ("save_app_Json", ""))) {

                /*This block execute when we dont find new data to refresh, but we have old data
                to show case, It can be case of repeated user*/

                /*shouldViewRefresh("no");*/


            } else if (!TextUtils.isEmpty(refreshMyView) && refreshMyView.equalsIgnoreCase
                    ("yes")) {
                /* shouldViewRefresh(refreshMyView);*/
                RefreshDataCallBack.getInstance().notifyOnRefreshStart();
                parseData();

            }

            isAsyncTaskRunning = false;
        }
    }

    public void setUpNavigationViewData() {


        final Menu menu = navigationView.getMenu();

        if (menu != null) {
            menu.clear();
        }


        try {
            if (response.getData().getDrawer_item() == null || response.getData().getDrawer_item()
                    .size() <= 0) {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<NavigationPojo> navigationPojoList = null;
        try {
            navigationPojoList = response.getData().getDrawer_item();
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            for (int nav_items = 0; nav_items < navigationPojoList.size(); nav_items++) {

                NavigationPojo get_position = navigationPojoList.get(nav_items);


                if (!TextUtils.isEmpty(get_position.getDrawer_item_name())) {
                    menu.add(0, nav_items, 0, get_position.getDrawer_item_name());
                }

                if (!TextUtils.isEmpty(get_position.getDrawer_icon())) {
                    final int finalNav_items = nav_items;
//                    Glide.with(MyApplication.ctx).load(get_position.getDrawer_icon()).asBitmap()
//                            .into
//                                    (new
//                                             SimpleTarget<Bitmap>() {
//                                                 int z = finalNav_items;
//
//                                                 @Override
//                                                 public void onResourceReady(Bitmap resource,
//                                                                             GlideAnimation
//                                                                                     glideAnimation) {
//
//                                                     menu.getItem(z).setIcon(new GlideBitmapDrawable
//                                                             (getResources(), resource));
//                                                 }
//                                             });
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int openCount = sharedPreferences.getInt(OPEN_COUNT_KEY, 0) + 1;
        sharedPreferences.edit().putInt(OPEN_COUNT_KEY, openCount).apply();

        if (openCount % 5 == 0) {
            // Show custom dialog
//            showBannerDialog();
        }
//        showBannerDialog();
//        ArrayList<String> videoIds = new ArrayList<>();
//        String getSavedJson = MyApplication.app_sharedPreferences.getString("save_app_video_Json", "");
//        if (!TextUtils.isEmpty(getSavedJson)) {
//            Gson gson = new Gson();
//            Log.e("chefef", ">>" + getSavedJson);
//            if (getSavedJson != null) {
//                Type type = new TypeToken<List<TrendingNewsModel>>() {}.getType();
//                List<TrendingNewsModel> arraylist_recom_new_new = gson.fromJson(getSavedJson, type);
//
//                // Extract video IDs from the list of VistoryModel
//                for (TrendingNewsModel vistoryModel : arraylist_recom_new_new) {
//                    videoIds.add(vistoryModel.getRedirectLink());
//                }
//            }
//            Log.e("chececefef", ">1>" + videoIds);
//            adaptererg = new VideoPagerDynamicAdapter(((FragmentActivity) context),videoIds,0,"","online");
//            viewPagerNew.setAdapter(adaptererg);
//        }else{
//            final Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Gson gson = new Gson();
//                    Log.e("chefef", ">>" + getSavedJson);
//                    if (!getSavedJson.isEmpty()) {
//                        Type type = new TypeToken<List<TrendingNewsModel>>() {}.getType();
//                        List<TrendingNewsModel> arraylist_recom_new_new = gson.fromJson(getSavedJson, type);
//
//                        // Extract video IDs from the list of VistoryModel
//                        for (TrendingNewsModel vistoryModel : arraylist_recom_new_new) {
//                            videoIds.add(vistoryModel.getRedirectLink());
//                        }
//                        Log.e("chececefef", ">2>" + videoIds);
//                        adaptererg = new VideoPagerDynamicAdapter(((FragmentActivity) context),videoIds,0,"","online");
//                        viewPagerNew.setAdapter(adaptererg);
//                        isFirstRun = false;
//                    }
//
//                }
//            }, 3000);
//        }
//        recycler_vierw.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this));
        Lifecycle lifecycle = ((LifecycleOwner) context).getLifecycle();
        SnapHelperOneByOne linearSnapHelper = new SnapHelperOneByOne();
//        linearSnapHelper.attachToRecyclerView(recycler_vierw);
//        RecyclerViewAdapter videoAdapteNew = new RecyclerViewAdapter(videoIds, lifecycle);
//        recycler_vierw.setAdapter(videoAdapteNew);
//        recycler_vierw.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                // Update the current position when the RecyclerView scrolls
//                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
//                videoAdapteNew.setCurrentPosition(firstVisibleItemPosition);
//            }
//        });
//        if (context instanceof FragmentActivity) {
//            fragmentActivity = (FragmentActivity) context;
//        } else {
//            throw new IllegalArgumentException("Context must be a FragmentActivity");
//        }



//        viewPagerNew.setOffscreenPageLimit(1);
//        viewPagerNew.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//
//                Log.e("Checkdataoncliccek", ">0011>" + ">position>" + position);
//                int actualPosition = position % videoIds.size();
//                Log.e("Checkdataoncliccek", ">scroll visible 22>" + title + ">actualPosition>" + actualPosition);
//                Fragment fragment = ((FragmentActivity)getContext()).getSupportFragmentManager().findFragmentByTag("f" + actualPosition);
//                Log.e("Checkdataoncliccek", ">scroll visible fraggg>" + fragment);
//
//                if (fragment instanceof VideoFragment) {
//                    Log.e("Checkdataoncliccek", ">scroll visible 33>" + title + ">>" + actualPosition);
//                    selectedVideoIndex = actualPosition;
//                    title = videoIds.get(actualPosition).toString();
//                    ((VideoFragment) fragment).webView.setWebViewClient(new WebViewClient() {
//                        @Override
//                        public void onPageFinished(WebView view, String url) {
//                            super.onPageFinished(view, url);
//                            ((VideoFragment) fragment).restartVideo();
//                        }
//                    });
//
//                    Log.e("Checkdataoncliccek", "match 44>" + title + "> 44>" + videoIds.get(actualPosition).toString()
//                            + ">selectedVideoIndex 44>" + selectedVideoIndex);
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                super.onPageScrollStateChanged(state);
//
//                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
//                }
//            }
//        });
//        viewPagerNew.setCurrentItem(selectedVideoIndex, false);


//        recycler_vierw.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                    int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
////                    videoAdapteNew.setCurrentVisiblePosition(firstVisiblePosition);
////                    videoAdapteNew.startAutoPlay();
//                }
//            }
//        });
//        if (videoAdapteNew != null) {
//            startAutoplay();
//        }


//        videoAdapter = new VideoAdapter(youtubeVideos,context,drawableList,linksList,recyclerView);
//        recyclerView.setAdapter(videoAdapter);
//
//        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                int currentlyPlayingPosition = videoAdapter.getCurrentlyPlayingPosition();
//                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
//                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
//
//                Log.e("checkfefw",">>" + currentlyPlayingPosition + ">>" + firstVisibleItemPosition + ">>" + lastVisibleItemPosition);
//
//                // Pause playback for non-visible items
//                for (int position = firstVisibleItemPosition; position <= lastVisibleItemPosition; position++) {
//                    if (position != currentlyPlayingPosition) {
//                        videoAdapter.pauseVideoPlayback(position);
//                    }
//                }
//
//                // Resume playback for the currently playing item
//                videoAdapter.resumeVideoPlayback(currentlyPlayingPosition);
//            }
//        });
//        getFrequntApps();

    }

    private void startAutoplay() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recycler_vierw.getLayoutManager();
        if (layoutManager != null) {
            int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
            if (firstVisiblePosition != RecyclerView.NO_POSITION) {
                RecyclerView.ViewHolder viewHolder = recycler_vierw.findViewHolderForAdapterPosition(firstVisiblePosition);
                if (viewHolder != null && viewHolder instanceof RecyclerViewAdapter.YouTubePlayerViewHolder) {
                    RecyclerViewAdapter.YouTubePlayerViewHolder playerViewHolder = (RecyclerViewAdapter.YouTubePlayerViewHolder) viewHolder;
                    playerViewHolder.playVideo();
                }
            }
        }
    }

    public void pauseVideoPlaeyback(int position) {
        // Loop through all the visible ViewHolders in the RecyclerView
        for (int i = 0; i < recycler_vierw.getChildCount(); i++) {
            // Get the ViewHolder at this position
            VideoAdapter.VideoViewHolder viewHolder = (VideoAdapter.VideoViewHolder) recycler_vierw.getChildViewHolder(recycler_vierw.getChildAt(i));
            // Check if the ViewHolder's adapter position matches the position we want to pause
            if (viewHolder != null && viewHolder.getAdapterPosition() == position) {
                // Call the pauseVideo method of the ViewHolder
//                viewHolder.pauseVideoPlayback();
                // Break out of the loop since we found the ViewHolder we wanted
                break;
            }
        }
    }

    public void pauseVideoPladyback(int position) {
        // Get the holder for the given position
        VideoAdapter.VideoViewHolder holder = (VideoAdapter.VideoViewHolder) recycler_vierw.findViewHolderForAdapterPosition(position);
        if (holder != null) {
            // Call the pauseVideo method in the ViewHolder
//            holder.pauseVideo();
        }
    }

    public void onNav_ItemSelected() {


        navigationView.setNavigationItemSelectedListener(new NavigationView
                .OnNavigationItemSelectedListener() {
            @Override

            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();


                NavigationPojo itemId = arraylist_nav.get(id);


                String getNavURL = itemId.getRedirect_url();

                MyApplication.getInstance().trackEvent("Navigation Menu", getNavURL, itemId
                        .getDrawer_item_name());


                if (!TextUtils.isEmpty(getNavURL) && getNavURL.equalsIgnoreCase("http://www" +
                        ".nubit" +
                        ".profile")) {
                    Intent sendToProfile = new Intent(context, ProfileActivity.class);
                    context.startActivity(sendToProfile);
                    drawerLayout.closeDrawers();
                } else if (!TextUtils.isEmpty(getNavURL) && getNavURL.equalsIgnoreCase
                        ("http://www.nubit.language")) {
                    Intent sendToLanguage = new Intent(context, Language_Set_Activity
                            .class);
                    context.startActivity(sendToLanguage);
                    drawerLayout.closeDrawers();
                } else if (!TextUtils.isEmpty(getNavURL) && getNavURL.equalsIgnoreCase
                        ("http://www.nubit.share")) {
                    MyApplication.getInstance().trackEvent("App Share", "Nubit Shared",
                            MyUtility.getDeviceID(context));
                    MyUtility.My_share(context, "", "");
                    drawerLayout.closeDrawers();
                } else {
                    if (!TextUtils.isEmpty(getNavURL)) {
                        // MyUtility.handleNavClick(RobiLauncherActivity.this, getNavURL);

                        MyUtility.handleItemClick(context, "", getNavURL, getNavURL,
                                "Navigation Menu", itemId.getOpen_with(), itemId
                                        .getDrawer_item_name());

                        drawerLayout.closeDrawers();
                    }


                }


                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle((Activity) context,
                drawerLayout, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
                MyApplication.app_editor.putString("drawer_status", "closed").apply();
                MyApplication.getInstance().trackEvent("Navigation Menu", "Navigation Menu " +
                                "Closed",
                        "Navigation Menu");
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
                MyApplication.app_editor.putString("drawer_status", "open").apply();
                MyApplication.getInstance().trackEvent("Navigation Menu", "Navigation Menu " +
                                "Opened",
                        "Navigation Menu");
                layoutmovetop.setVisibility(View.GONE);

                setProfile_Nav();
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    public void setProfile_Nav() {

        View header = navigationView.getHeaderView(0);
        TextView profile_name_nav = header.findViewById(R.id.profile_name_nav);
        ImageView profile_nav = header.findViewById(R.id.profile_pic_nav);

        String getProfile = MyApplication.app_sharedPreferences.getString("userProfileIcon",
                "");
        String getName = MyApplication.app_sharedPreferences.getString("userProfileName", "");

        if (!TextUtils.isEmpty(getProfile)) {
//            Glide.with(MyApplication.ctx).load(getProfile).asBitmap().thumbnail(0.5f)
//                    .placeholder(R.drawable.placeholder_apps)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder_apps)
//                    .into(profile_nav);
        }
        if (!TextUtils.isEmpty(getName)) {
            profile_name_nav.setText(getName);
        }

        profile_nav.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                Intent sentToProfile = new Intent(context, ProfileActivity.class);
                context.startActivity(sentToProfile);
            }
        });
    }

    private void hitGetProfile() {
        String get_ProfileStatus = MyApplication.app_sharedPreferences.getString
                ("haveProfile", "");
        if (TextUtils.isEmpty(get_ProfileStatus)) {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                new GetProfileData().executeOnExecutor(AsyncTask
                        .THREAD_POOL_EXECUTOR);
            } else {
                new GetProfileData().execute();
            }
            MyApplication.app_editor.putString("haveProfile", "haveProfile")
                    .apply();


        }
    }

    private void hitGetVistory() {
//        String get_ProfileStatus = MyApplication.app_sharedPreferences.getString
//                ("haveProfile", "");
//        if (TextUtils.isEmpty(get_ProfileStatus)) {

        Log.e("checkvistorydata","1>>");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new GetVistoryData().executeOnExecutor(AsyncTask
                    .THREAD_POOL_EXECUTOR);
        } else {
            new GetVistoryData().execute();
        }
//            MyApplication.app_editor.putString("haveProfile", "haveProfile")
//                    .apply();


//        }
    }

    private void hitMainDataApi() {
        Log.e("hitMainDataApi","1>>");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new GetMainData().executeOnExecutor(AsyncTask
                    .THREAD_POOL_EXECUTOR);
        } else {
            new GetMainData().execute();
        }
//            MyApplication.app_editor.putString("haveProfile", "haveProfile")
//                    .apply();


//        }
    }

    private void hitGetVideoDataApi() {
        Log.e("hitGetVideoDataApi","1>>");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new GetVideoData().executeOnExecutor(AsyncTask
                    .THREAD_POOL_EXECUTOR);
        } else {
            new GetVideoData().execute();
        }
//            MyApplication.app_editor.putString("haveProfile", "haveProfile")
//                    .apply();


//        }
    }

    private void hitSportsDataApi() {
        Log.e("hitMainDataApi","1>>");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new GetSportsData().executeOnExecutor(AsyncTask
                    .THREAD_POOL_EXECUTOR);
        } else {
            new GetSportsData().execute();
        }
//            MyApplication.app_editor.putString("haveProfile", "haveProfile")
//                    .apply();


//        }
    }

    private void hitAppsDataApi() {
        Log.e("hitAppsDataApi","1>>");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new GetAppsData().executeOnExecutor(AsyncTask
                    .THREAD_POOL_EXECUTOR);
        } else {
            new GetAppsData().execute();
        }
//            MyApplication.app_editor.putString("haveProfile", "haveProfile")
//                    .apply();


//        }
    }

    private void hitBannersDataApi() {
        Log.e("hitAppsDataApi","1>>");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new GetBannersData().executeOnExecutor(AsyncTask
                    .THREAD_POOL_EXECUTOR);
        } else {
            new GetBannersData().execute();
        }
//            MyApplication.app_editor.putString("haveProfile", "haveProfile")
//                    .apply();


//        }
    }

    private void hitGamesDataApi() {
        Log.e("hitAppsDataApi","1>>");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new GetGamesData().executeOnExecutor(AsyncTask
                    .THREAD_POOL_EXECUTOR);
        } else {
            new GetGamesData().execute();
        }
//            MyApplication.app_editor.putString("haveProfile", "haveProfile")
//                    .apply();


//        }
    }

    private void hitGetOrderApi() {
//        String get_ProfileStatus = MyApplication.app_sharedPreferences.getString
//                ("haveProfile", "");
//        if (TextUtils.isEmpty(get_ProfileStatus)) {

        Log.e("checkvistorydata","1>>");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new GetModuleOrderApi().executeOnExecutor(AsyncTask
                    .THREAD_POOL_EXECUTOR);
        } else {
            new GetModuleOrderApi().execute();
        }

        String get_SavedJson = MyApplication.app_sharedPreferences.getString
                ("save_app_order_Json",
                        "");
        if (!TextUtils.isEmpty(get_SavedJson)) {
            Gson gson = new Gson();
            JSONArray jArray = new JSONArray();
            Log.e("ccecehefef",">>" + get_SavedJson);
            if (get_SavedJson != null) {
                OrderModel testWEbview = new OrderModel("","Vistory","video","11","Vistory","test","webview_layout",jArray);
                OrderModel[] sectionsArray = gson.fromJson(get_SavedJson, OrderModel[].class);
                List<OrderModel> sections = new ArrayList<>();
                Collections.addAll(sections, sectionsArray);
                sections.add(testWEbview);

                // Step 1: Sort the list based on placementOrder
                Collections.sort(sections, new Comparator<OrderModel>() {
                    @Override
                    public int compare(OrderModel s1, OrderModel s2) {
                        return Integer.compare(Integer.parseInt(s1.getPlacementOrder()), Integer.parseInt(s2.getPlacementOrder()));
                    }
                });

                // Step 2: Create a new ArrayList with elements sorted by name
                List<String> sortedNamesList = new ArrayList<>();
                for (OrderModel section : sections) {
                    sortedNamesList.add(section.getName());
                    if (section.getName().equals("freq used apps")){
                        btn_freq_apps.setText(section.getHeader());
                    }else if (section.getName().equals("recommended news")){
                        title_recommended_news.setText(section.getHeader());
                    }else if (section.getName().equals("trending news")){
                        btn_news_title.setText(section.getHeader());
                    }else if (section.getName().equals("sports news")){
                        title_middle_news.setText(section.getHeader());
                    }else if (section.getName().equals("top apps")){
                        btn_topapps_title.setText(section.getHeader());
                    }else if (section.getName().equals("games")){
                        btn_externalGames_title.setText(section.getHeader());
                    }
                }
                Log.e("chefnfkref","final>>" + sortedNamesList);

                System.out.println(sortedNamesList);

                if (ad_view_banner != null) {
                    adRequesNew = new AdRequest.Builder().build();
                    ad_view_banner.loadAd(adRequesNew);
                }

                parentLayout = findViewById(R.id.parentLayout);

//                try {
                    // Parse JSON
//                    JSONObject jsonObject = new JSONObject(get_SavedJson);
//                    JSONArray contentArray = jsonObject.getJSONObject("responseObject").getJSONArray("content");

                    for (int i = 0; i < sections.size(); i++) {
                        String name = sections.get(i).getName();
                        String header = sections.get(i).getHeader();
                        String sectionDesignElement = sections.get(i).getSectionDesignElement();

                        // Inflate layout dynamically based on sectionDesignElement
                        int layoutResId = 0;
                        Log.e("chccgcg", "fefefef>>" + sectionDesignElement + ">>" + header  + ">>" + name + ">>" + sections.size());
                        switch (sectionDesignElement) {
                            case "video_layout":
//                                layoutResId = R.layout.layout_news_video;
                                handleVideoLayout(R.layout.layout_news_video);
                                break;
                            case "middle_news_layout":
//                                layoutResId = R.layout.layout_top_banner;
                                handleVerticalNews(R.layout.layout_news_top,header);
                                break;
                            case "banner_layout":
//                                layoutResId = R.layout.layout_top_banner;
                                handleBannerNews(R.layout.layout_new_banner,header);
                                break;
                            case "horizontal_news_layout":
//                                layoutResId = R.layout.layout_middle_news;
                                handleMiddleNews(R.layout.layout_middle_news,header);
                                break;
                            case "vertical_news_layout":
//                                layoutResId = R.layout.layout_news_top;
                                handleVerticalNewsWebview(R.layout.layout_recom_news,header);
                                break;
                            case "ads_layout":
//                                layoutResId = R.layout.layout_ads;
                                handleAds(R.layout.layout_ads);
                                break;
                            case "apps_layout":
//                                layoutResId = R.layout.layout_topapps;
                                Log.e("checkappsflow", ">start>" + name);
                                String get_apps_SavedJson = MyApplication.app_sharedPreferences.getString
                                        ("save_app_apps_newm_Json",
                                                "");
                                Type type = new TypeToken<List<AppsModel>>() {}.getType();
                                ArrayList<AppsModel> appsList = new ArrayList<>();
                                Gson gsosn = new Gson();
                                ArrayList<AppsModel> tempList = gsosn.fromJson(get_apps_SavedJson, type);
                                Log.e("checkappsfloc", ">save_app_apps_newm_Json>" + get_apps_SavedJson);

                                if (!TextUtils.isEmpty(get_apps_SavedJson)) {
                                    for (AppsModel app : tempList) {
                                        Log.e("checkappsfloc", ">compare>" + app.getCategory() + ">>" + name);

                                        if (app.getCategory().equals(name)) {
                                            appsList.add(app);
                                        }
                                    }
                                }
                                TopAppsAdapter adapter = new TopAppsAdapter(context, appsList);
                                handleAppsLayout(R.layout.layout_topapps,appsList,adapter,header);
//                                if (name.equals("top apps")) {
//                                    Log.e("checkdappsfloc", ">333>" + gamesList.size() + ">>" + appsList.size());
//                                    TopAppsAdapter adapter = new TopAppsAdapter(context, appsList);
//                                    handleAppsLayout(R.layout.layout_topapps,appsList,adapter,header);
//                                } else if (name.equals("games")) {
//                                    Log.e("checkdappsfloc", ">444>" + gamesList.size() + ">>" + appsList.size());
//                                    TopAppsAdapter adapter = new TopAppsAdapter(context, gamesList);
//                                    handleAppsLayout(R.layout.layout_topapps,gamesList,adapter,header);
//                                }

                                break;
                            case "icon_layout":
//                                layoutResId = R.layout.layout_freq_apps;
                                handleAppsLayoutFreq(R.layout.layout_freq_apps,header);
                                break;
                            case "webview_layout":
//                                layoutResId = R.layout.layout_freq_apps;
                                handleVerticalNewsWebview(R.layout.layout_recom_news,header);
                                break;
                            default:
                                // Handle unknown layout type
                                break;
                        }

                        // Inflate the layout
                        if (layoutResId != 0) {
                            LayoutInflater inflator = LayoutInflater.from(context);
                            LinearLayout view = (LinearLayout) inflator.inflate(layoutResId, null);

                            // Add additional logic here to populate views with data from the JSON item

                            // Add the dynamically created view to the parent layout
                            parentLayout.addView(view);
                        }
                    }

                combineInPref();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Log.e("chccgcg",">eeee>" + e.getMessage());
                    // Handle JSON parsing errors
//                }
            }
        }
//        }
    }

    private void combineInPref() {
        String get_apps_SavedJsonApps = MyApplication.app_sharedPreferences.getString("save_app_apps_new_Json", "");
        String get_apps_SavedJsonGames = MyApplication.app_sharedPreferences.getString("save_app_app_design_new_Json", "");

        List<AppsModel> arraylist_apps = new ArrayList<>();
        List<AppsModel> arraylist_games = new ArrayList<>();
        List<AppsModel> combinedList = new ArrayList<>();

        // Check and parse JSON for Apps
        if (!TextUtils.isEmpty(get_apps_SavedJsonApps)) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<AppsModel>>() {}.getType();
            arraylist_apps = gson.fromJson(get_apps_SavedJsonApps, type);
            // Add a title key to each item in the list
            for (AppsModel app : arraylist_apps) {
                app.setStatus("top apps"); // You can set the title to any value you want
            }
        }

        // Check and parse JSON for Games
        if (!TextUtils.isEmpty(get_apps_SavedJsonGames)) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<AppsModel>>() {}.getType();
            arraylist_games = gson.fromJson(get_apps_SavedJsonGames, type);
            // Add a title key to each item in the list
            for (AppsModel game : arraylist_games) {
                game.setStatus("games"); // You can set the title to any value you want
            }
        }

        // Combine the two lists into one
        combinedList.addAll(arraylist_apps);
        combinedList.addAll(arraylist_games);

        // Convert the combined list to JSON
        String combinedJson = new Gson().toJson(combinedList);

        // Save the combined JSON to SharedPreferences under a new key
        SharedPreferences.Editor editor = MyApplication.app_sharedPreferences.edit();
        editor.putString("save_app_both_new_Json", combinedJson);
        Log.e("checkfinal",">>" + combinedJson);
        editor.apply();
    }

    private void handleBothData() {
        List<AppsModel> combinedList = new ArrayList<>();
        combinedList.addAll(arraylist_top_apps_new);
        combinedList.addAll(arraylist_Flashgames_new);

        String get_apps_SavedJsonApps = MyApplication.app_sharedPreferences.getString
                ("save_app_apps_new_Json",
                        "");
        if (!TextUtils.isEmpty(get_apps_SavedJsonApps)) {
            Gson gson = new Gson();
            Log.e("chefef",">>" + get_apps_SavedJsonApps);
            if (get_apps_SavedJsonApps != null) {
                Type type = new TypeToken<List<AppsModel>>() {}.getType();
                arraylist_both = gson.fromJson(get_apps_SavedJsonApps, type);
            }
        }
        String get_apps_SavedJsonGames = MyApplication.app_sharedPreferences.getString
                ("save_app_app_design_new_Json",
                        "");
        if (!TextUtils.isEmpty(get_apps_SavedJsonGames)) {
            Gson gson = new Gson();
            Log.e("chefef",">>" + get_apps_SavedJsonGames);
            if (get_apps_SavedJsonGames != null) {
                Type type = new TypeToken<List<AppsModel>>() {}.getType();
                arraylist_both = gson.fromJson(get_apps_SavedJsonGames, type);
            }
        }
    }

    private void handleAppsLayout(int layoutResId, ArrayList<AppsModel> appsList, TopAppsAdapter topApps_adapter, String header) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout inflatedLayout = (LinearLayout) inflater.inflate(layoutResId, null);

        RecyclerView topapps_recyclerView = inflatedLayout.findViewById(R.id.topapps_recyclerView);
        TextView btn_topapps_title = inflatedLayout.findViewById(R.id.btn_topapps_title);
        btn_topapps_title.setText(header);
//        AppGridAdapter adapter = new AppGridAdapter(context, topApps, context.getPackageManager());


//        TopAppsAdapter topApps_adapter = new TopAppsAdapter(context, appsList);
        LinearLayoutManager layoutManager_topApps = new LinearLayoutManager(context,
                LinearLayoutManager
                        .HORIZONTAL, false);
        topapps_recyclerView.setLayoutManager(layoutManager_topApps);
        topapps_recyclerView.setAdapter(topApps_adapter);

        topapps_recyclerView.setOnTouchListener(new InterceptTouchListener(new
                                                                                   InterceptTouchListener.OnItemClickListener() {
                                                                                       @Override
                                                                                       public void
                                                                                       onItemClick(View v, int position) {

                                                                                           final AppsModel top_apps_pojo = appsList.get(position);
//                                                                                           MyApplication.getInstance().trackEvent("Apps", top_apps_pojo.getRedirect_link(), top_apps_pojo.getTitle());
//                                                                                           MyUtility.handleItemClick(context, top_apps_pojo.getPackage_name(),
//                                                                                                   top_apps_pojo.getRedirect_link(), top_apps_pojo.getBanner_thumb_image(), "Apps", top_apps_pojo.getOpen_with(), top_apps_pojo.getTitle());
                                                                                           MyUtility.handleItemClick(context, "", top_apps_pojo
                                                                                                   .getRedirectLink(), top_apps_pojo.getBannerImage(), "Apps", "", top_apps_pojo.getTitle());

                                                                                       }
                                                                                   }
        ));


        // Adding the inflated layout to the parent layout
        parentLayout.addView(inflatedLayout);
    }

    private void handleMiddleNews(int layoutResId, String header) {
        Log.e("checksteps",">horizontal news sports>" + layoutResId);

        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout inflatedLayout = (LinearLayout) inflater.inflate(layoutResId, null);

        RecyclerView middleNews_recyclerView = inflatedLayout.findViewById(R.id.middle_news_recyclerView);
        TextView title_middle_news = inflatedLayout.findViewById(R.id.title_middle_news);
        title_middle_news.setText(header);
        String get_sports_SavedJson = MyApplication.app_sharedPreferences.getString
                ("save_app_sports_news_Json",
                        "");
        if (!TextUtils.isEmpty(get_sports_SavedJson)) {
            Gson gson = new Gson();
            Log.e("chefef",">>" + get_sports_SavedJson);
            if (get_sports_SavedJson != null) {
                Type type = new TypeToken<List<TrendingNewsModel>>() {}.getType();
                arraylist_sports_new_new = gson.fromJson(get_sports_SavedJson, type);
            }
            final Middle_News_Adapter middle_news_adapter = new Middle_News_Adapter(context,
                    arraylist_sports_new_new);
            middleNews_recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                    LinearLayoutManager.HORIZONTAL, false);
            middleNews_recyclerView.setLayoutManager(layoutManager);
            //recyclerView.setNestedScrollingEnabled(false);
            middleNews_recyclerView.setAdapter(middle_news_adapter);

            middleNews_recyclerView.setOnTouchListener(new InterceptTouchListener(new
                                                                                          InterceptTouchListener.OnItemClickListener() {
                                                                                              @Override
                                                                                              public void
                                                                                              onItemClick(View v, int position) {

                                                                                                  TrendingNewsModel middleNewsPojo = arraylist_sports_new_new.get(position);
                                                                                                  MyApplication.getInstance().trackEvent("Middle News", middleNewsPojo.getRedirectLink(), middleNewsPojo.getTitle());
                                                                                                  MyUtility.handleItemClick(context, "", middleNewsPojo.getRedirectLink(), middleNewsPojo.getImageUrl(),
                                                                                                          "Middle News", "", middleNewsPojo.getTitle());
//                                                                                              MyApplication.getInstance().trackEvent("Middle News", middleNewsPojo.getRedirect_link(), middleNewsPojo.getTitle());
//                                                                                              MyUtility.handleItemClick(context, middleNewsPojo.getPackage_name(), middleNewsPojo.getRedirect_link(), middleNewsPojo.getImage(),
//                                                                                                      "Middle News", middleNewsPojo.getOpen_with(), middleNewsPojo.getTitle());
                                                                                              }
                                                                                          }));
        }

        // Adding the inflated layout to the parent layout
        parentLayout.addView(inflatedLayout);

    }

    private void handleBannerNews(int layoutResId, String header) {
        Log.e("checksteps",">horizontal news sports>" + layoutResId);

        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout inflatedLayout = (LinearLayout) inflater.inflate(layoutResId, null);

        RecyclerView recycler_vierw = inflatedLayout.findViewById(R.id.recycler_vier_banner);

        String get_sports_SavedJson = MyApplication.app_sharedPreferences.getString
                ("save_app_banners_newm_Json",
                        "");
        Log.e("checjfef","get_sports_SavedJson>> " + get_sports_SavedJson);

        if (!TextUtils.isEmpty(get_sports_SavedJson)) {
            Gson gson = new Gson();
            Log.e("chefef",">>" + get_sports_SavedJson);
            if (get_sports_SavedJson != null) {
                Type type = new TypeToken<List<BannersModel>>() {}.getType();
                arraylist_banner_new_new = gson.fromJson(get_sports_SavedJson, type);
            }
            final NewBannerAdapter middle_news_adapter = new NewBannerAdapter(arraylist_banner_new_new,context);
            recycler_vierw.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                    LinearLayoutManager.HORIZONTAL, false);
            recycler_vierw.setLayoutManager(layoutManager);
            recycler_vierw.setAdapter(middle_news_adapter);

            SnapHelperOneByOne linearSnapHelper = new SnapHelperOneByOne();
            linearSnapHelper.attachToRecyclerView(recycler_vierw);
            recycler_vierw.setOnTouchListener(new InterceptTouchListener(new
                                                                                 InterceptTouchListener.OnItemClickListener() {
                                                                                     @Override
                                                                                     public void
                                                                                     onItemClick(View v, int position) {

//                                                                                              TrendingNewsModel middleNewsPojo = arraylist_sports_new_new.get(position);
//                                                                                              MyApplication.getInstance().trackEvent("Middle News", middleNewsPojo.getRedirectLink(), middleNewsPojo.getTitle());
                                                                                         MyUtility.handleItemClick(context, "", "","",
                                                                                                 "Middle News", "", "Banner " + position);
//                                                                                              MyApplication.getInstance().trackEvent("Middle News", middleNewsPojo.getRedirect_link(), middleNewsPojo.getTitle());
//                                                                                              MyUtility.handleItemClick(context, middleNewsPojo.getPackage_name(), middleNewsPojo.getRedirect_link(), middleNewsPojo.getImage(),
//                                                                                                      "Middle News", middleNewsPojo.getOpen_with(), middleNewsPojo.getTitle());
                                                                                     }
                                                                                 }));
        }
        // Adding the inflated layout to the parent layout
        parentLayout.addView(inflatedLayout);

    }

    private void handleVideoLayout(int layoutResId) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout inflatedLayout = (LinearLayout) inflater.inflate(layoutResId, null);

        ViewPager2 viewPagerNew = inflatedLayout.findViewById(R.id.viewPagerNew);
        ArrayList<String> videoIds = new ArrayList<>();
        String getSavedJson = MyApplication.app_sharedPreferences.getString("save_app_video_Json", "");
        Log.e("chefef", "handleVideoLayout>00>" + getSavedJson);

        if (!TextUtils.isEmpty(getSavedJson)) {
            Gson gson = new Gson();
            Log.e("chefef", "handleVideoLayout>>" + getSavedJson);
            if (getSavedJson != null) {
                Type type = new TypeToken<List<TrendingNewsModel>>() {}.getType();
                List<TrendingNewsModel> arraylist_recom_new_new = gson.fromJson(getSavedJson, type);

                // Extract video IDs from the list of VistoryModel
                for (TrendingNewsModel vistoryModel : arraylist_recom_new_new) {
                    videoIds.add(vistoryModel.getRedirectLink());
                }
            }
            Log.e("chececefef", ">1>" + videoIds);
            VideoPagerDynamicAdapter adaptererg = new VideoPagerDynamicAdapter(((FragmentActivity) context),videoIds,0,"","online");
            viewPagerNew.setAdapter(adaptererg);
        }else{
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onAttachedToWindow();
                    Gson gson = new Gson();
                    Log.e("chefef", ">>" + getSavedJson);
                    if (!getSavedJson.isEmpty()) {
                        Type type = new TypeToken<List<TrendingNewsModel>>() {}.getType();
                        List<TrendingNewsModel> arraylist_recom_new_new = gson.fromJson(getSavedJson, type);

                        // Extract video IDs from the list of VistoryModel
                        for (TrendingNewsModel vistoryModel : arraylist_recom_new_new) {
                            videoIds.add(vistoryModel.getRedirectLink());
                        }
                        Log.e("chececefef", ">2>" + videoIds);
                        VideoPagerDynamicAdapter adaptererg = new VideoPagerDynamicAdapter(((FragmentActivity) context),videoIds,0,"","online");
                        viewPagerNew.setAdapter(adaptererg);
                        isFirstRun = false;
                    }

                }
            }, 3000);
        }
        // Adding the inflated layout to the parent layout
        parentLayout.addView(inflatedLayout);

    }

    private void handleVerticalNews(int layoutResId, String header) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout inflatedLayout = (LinearLayout) inflater.inflate(layoutResId, null);

        RecyclerView news_top_recyclerView = inflatedLayout.findViewById(R.id.news_top_recyclerView);
        TextView btn_news_title = inflatedLayout.findViewById(R.id.btn_news_title);
        btn_news_title.setText(header);
        String get_trending_SavedJson = MyApplication.app_sharedPreferences.getString
                ("save_app_trending_news_Json",
                        "");
        if (!TextUtils.isEmpty(get_trending_SavedJson)) {
            Gson gson = new Gson();
            Log.e("chefef", ">handleVerticalNews>" + get_trending_SavedJson);
            if (get_trending_SavedJson != null) {
                Type type = new TypeToken<List<TrendingNewsModel>>() {
                }.getType();
                arraylist_trending_new_new = gson.fromJson(get_trending_SavedJson, type);
            }
        }else{

//            hitGetOrderApi();
        }

        Log.e("checknews", ">" + arraylist_trending_new_new);
        Top_News_Adapter top_news_adapter = new Top_News_Adapter(context, arraylist_trending_new_new);
        news_top_recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        news_top_recyclerView.setLayoutManager(layoutManager);
        news_top_recyclerView.setNestedScrollingEnabled(false);
        news_top_recyclerView.setAdapter(top_news_adapter);
        // Adding the inflated layout to the parent layout
        parentLayout.addView(inflatedLayout);

    }

    private void handleVerticalNewsWebview(int layoutResId, String header) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout inflatedLayout = (LinearLayout) inflater.inflate(layoutResId, null);

        WebView webviewVistory = inflatedLayout.findViewById(R.id.webviewVistory);
        WebSettings s = webviewVistory.getSettings();
        s.setBuiltInZoomControls(false);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setJavaScriptEnabled(true);
        webviewVistory.getSettings().setDomStorageEnabled(true);
        webviewVistory.getSettings().setDatabaseEnabled(true);

        webviewVistory.getSettings().setJavaScriptEnabled(true); // Enable JavaScript execution
        webviewVistory.loadUrl("https://www.vistory.mobi/minus-one");
        webviewVistory.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading (WebView view, String url){
                Log.e("checkurlss", ">>" + url);
                String[] parts = url.split("/");

                String lastPart = parts[parts.length - 1];

                System.out.println("Last part of the URL: " + correctUrl(lastPart));
                MyUtility.handleItemClick(context, "", url, "",
                        "Top News", "", correctUrl(lastPart));
                return true; // Returning true means the host application handles the URL
            }
        });

        // Adding the inflated layout to the parent layout
        parentLayout.addView(inflatedLayout);

    }
    private void handleAppsLayoutFreq(int layoutResId, String header) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout inflatedLayout = (LinearLayout) inflater.inflate(layoutResId, null);
        RecyclerView rvFreqApps = inflatedLayout.findViewById(R.id.rvFreqApps);
        TextView btn_freq_apps = inflatedLayout.findViewById(R.id.btn_freq_apps);
        btn_freq_apps.setText(header);
        int numColumns = 4; // Change this as needed
        GridLayoutManager layoutManager = new GridLayoutManager(context, numColumns);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(context,
//                LinearLayoutManager.HORIZONTAL, false);

        try {
            if (topAppNew.size()>0){
                rvFreqApps.setHasFixedSize(true);
                rvFreqApps.setLayoutManager(layoutManager);
                AppGridAdapter adapter = new AppGridAdapter(context, topAppNew, context.getPackageManager());
                rvFreqApps.setAdapter(adapter);
            }

        }catch (Exception e){

        }
        // Adding the inflated layout to the parent layout
        parentLayout.addView(inflatedLayout);

    }

    private void handleAds(int layoutResId) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout inflatedLayout = (LinearLayout) inflater.inflate(layoutResId, null);
        AdView ad_view_banner = inflatedLayout.findViewById(R.id.ad_view_banner);

        if (ad_view_banner != null) {
            adRequesNew = new AdRequest.Builder().build();
            ad_view_banner.loadAd(adRequesNew);
        }
        parentLayout.addView(inflatedLayout);

    }

    private void hitWeatherApi() {
        Log.e("hitWeatherApi","1>>");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new GetWeatherData().executeOnExecutor(AsyncTask
                    .THREAD_POOL_EXECUTOR);
        } else {
            new GetWeatherData().execute();
        }
        String get_SavedJson_weather = MyApplication.app_sharedPreferences.getString("save_app_weather_Json", "");

        Log.e("chefefeffef", "0>>" + get_SavedJson_weather);
        if (!TextUtils.isEmpty(get_SavedJson_weather)) {
            try {
                // Parse the JSON string
                JSONObject jsonObject = new JSONObject(get_SavedJson_weather);

                // Retrieve the value of the "cityName" key
                JSONObject nameValuePairs = jsonObject.getJSONObject("nameValuePairs");

                // Extract values from the nested JSONObject
                String cityName = nameValuePairs.getString("cityName");
                double temperature = nameValuePairs.getDouble("temprature"); // Note the key spelling
                int temperatureInt = (int) temperature;
                String weather = nameValuePairs.getString("weather");
                tv_teemp.setText(String.valueOf(temperatureInt) + " \u2103");
                tv_city.setText(cityName);
                tv_weather.setText(weather);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//            MyApplication.app_editor.putString("haveProfile", "haveProfile")
//                    .apply();
    }


    public void performSearch() {


        if (MyUtility.isConnectedToInternet()) {
            String get_search_text = edt_search.getText().toString();
            String get_SearchRedirectionURL = MyApplication.app_sharedPreferences.getString
                    ("search_redirection", null);

            if (get_SearchRedirectionURL != null || !get_SearchRedirectionURL.matches("")) {
                if (get_search_text == "" || get_search_text.equals("")) {
                    Toast.makeText(context, "Please enter something to search!", Toast
                            .LENGTH_SHORT).show();
                } else {
                    String get_redirectedURL = get_SearchRedirectionURL + get_search_text;
                    edt_search.setText("");
                    edt_search.clearFocus();
                    MyUtility.RedirectMe(context, get_redirectedURL, "0", "Nubit Search");


                    MyApplication.getInstance().trackEvent("Search",
                            get_redirectedURL, get_search_text);
                    MyUtility.saveTracksInDB(context, get_search_text, get_redirectedURL,
                            "Search", "1", get_search_text, "");

                }

            } else {
                try {


                    if (get_search_text == "" || get_search_text.equals("")) {
                        Toast.makeText(context, "Please enter something to search!", Toast
                                .LENGTH_SHORT).show();
                    } else {
                        edt_search.setText("");
                        edt_search.clearFocus();
                        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                        intent.putExtra(SearchManager.QUERY, get_search_text);
                        context.startActivity(intent);

                    }

                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        } else {
            MyUtility.NoInternet_Msg(context);
        }


    }

    class HitGetData_forLanguage extends AsyncTask<Void, Void, String> {


        String refreshMyFragment;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress_home.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... params) {


            JSONObject save_json_jsonobject = null;

            String downloadNewData = "";


            downloadNewData = MyUtility.hitGetDataAPI(context, MyUtility.getDataAPI);
            if (!TextUtils.isEmpty(downloadNewData)) {
                try {
                    save_json_jsonobject = new JSONObject(downloadNewData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (save_json_jsonobject != null) {
                    if (!TextUtils.isEmpty(save_json_jsonobject.toString())) {
                        MyApplication.app_editor.putString("save_app_Json", String.valueOf
                                (save_json_jsonobject));
                        MyApplication.app_editor.apply();
                    }


                }
                refreshMyFragment = "yes";
            } else {
                refreshMyFragment = "no";
            }


            return refreshMyFragment;
        }

        @Override
        public void onPostExecute(String args) {


            if (args.equalsIgnoreCase("yes")) {
                parseData();

            }
            progress_home.setVisibility(View.GONE);
        }
    }

    public void init_Title_view() {


        List<TITLES_IN_DIFF_LANG> diff_langList = null;
        try {
            diff_langList = response.getData().getTitles_in_diff_lang();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (diff_langList != null && diff_langList.size() > 0) {

            for (TITLES_IN_DIFF_LANG diffLang : diff_langList) {
                try {
                    btn_service_title.setText(diffLang.getServices());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {
//                    btn_topapps_title.setText(diffLang.getTop_apps());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    btn_bottom_news_title.setText(diffLang.getNews3());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    title_middle_news.setText(diffLang.getNews2());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
//                    title_recommended_news.setText(diffLang.getNews4());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    btn_games_title.setText(diffLang.getTrending_games());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    btn_wallpaper_title.setText(diffLang.getWallpapers());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {
//                    btn_externalGames_title.setText(diffLang.getFlash_games());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    btn_news_title.setText(diffLang.getTrending_news());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {
                    MyApplication.app_editor.putString("readFullStory_forTitle", diffLang
                            .getRead_full_story()).apply();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    MyApplication.app_editor.putString("select_publisher", diffLang
                            .getSelect_publisher()).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    MyApplication.app_editor.putString("pub_btn", diffLang
                            .getPub_btn()).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    MyApplication.app_editor.putString("vid_pop_title", diffLang
                            .getVid_pop_title()).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    if (!TextUtils.isEmpty(diffLang.getSelect_publisher())) {
                        btn_add_pub.setText(diffLang.getSelect_publisher());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }


        if (response != null && response.getData().getTitles() != null && response.getData()
                .getTitles().size() > 0) {

            String get_admob_block = MyApplication.app_sharedPreferences.getString
                    ("admob_block_rkt", "");

            Titles titles = response.getData().getTitles().get(0);


            if (TextUtils.isEmpty(titles.getServices())) {
                btn_service_title.setVisibility(View.GONE);
                devider_below_btnService.setVisibility(View.GONE);
            } else {

                btn_service_title.setVisibility(View.GONE);
                devider_below_btnService.setVisibility(View.GONE);

            }
            if (TextUtils.isEmpty(titles.getTaboola_view())) {
                parent_taboola.setVisibility(View.GONE);
            } else {

                parent_taboola.setVisibility(View.VISIBLE);

            }

            if (TextUtils.isEmpty(titles.getSearch())) {
                edt_search.setVisibility(View.GONE);
            } else {
                edt_search.setVisibility(View.VISIBLE);
            }

            if (titles.getServices() == null) {
                service_layout_parent.setVisibility(View.GONE);
            } else {

                if (MyUtility.setShowHide_Container(titles.getServices())) {
                    service_layout_parent.setVisibility(View.GONE);
                } else {
                    service_layout_parent.setVisibility(View.VISIBLE);
                }


            }

            if (titles.getAdmob_one() == null) {

                remove_givenView(parent_admob_one);

            } else {

                if (!TextUtils.isEmpty(get_admob_block)) {
                    if (!get_admob_block.contains("1")) {
                        parent_admob_one.setVisibility(View.VISIBLE);
                        hit_admob_function(0, parent_admob_one);
                    } else {
                        remove_givenView(parent_admob_one);
                    }
                } else {
                    parent_admob_one.setVisibility(View.VISIBLE);
                    hit_admob_function(0, parent_admob_one);
                }


            }
            if (titles.getAdmob_two() == null) {

                remove_givenView(parent_admob_two);


            } else {

                if (!TextUtils.isEmpty(get_admob_block)) {
                    if (!get_admob_block.contains("2")) {
                        parent_admob_two.setVisibility(View.VISIBLE);
                        hit_admob_function(1, parent_admob_two);
                    } else {
                        remove_givenView(parent_admob_two);
                    }
                } else {
                    parent_admob_two.setVisibility(View.VISIBLE);
                    hit_admob_function(1, parent_admob_two);
                }


            }
            if (titles.getAdmob_three() == null) {

                remove_givenView(parent_admob_three);

            } else {

                if (!TextUtils.isEmpty(get_admob_block)) {
                    if (!get_admob_block.contains("3")) {
                        parent_admob_three.setVisibility(View.VISIBLE);
                        hit_admob_function(2, parent_admob_three);
                    } else {
                        remove_givenView(parent_admob_three);
                    }
                } else {
                    parent_admob_three.setVisibility(View.VISIBLE);
                    hit_admob_function(2, parent_admob_three);
                }


            }
            if (titles.getAdmob_four() == null) {

                remove_givenView(parent_admob_four);

            } else {

                if (!TextUtils.isEmpty(get_admob_block)) {
                    if (!get_admob_block.contains("4")) {
                        parent_admob_four.setVisibility(View.VISIBLE);
                        hit_admob_function(3, parent_admob_four);
                    } else {
                        remove_givenView(parent_admob_four);
                    }

                } else {
                    parent_admob_four.setVisibility(View.VISIBLE);
                    hit_admob_function(3, parent_admob_four);
                }

            }


            if (titles.getAdmob_five() == null) {

                remove_givenView(parent_admob_five);

            } else {

                if (!TextUtils.isEmpty(get_admob_block)) {
                    if (!get_admob_block.contains("5")) {
                        parent_admob_five.setVisibility(View.VISIBLE);
                        hit_admob_function(4, parent_admob_five);
                    } else {
                        remove_givenView(parent_admob_five);
                    }

                } else {
                    parent_admob_five.setVisibility(View.VISIBLE);
                    hit_admob_function(4, parent_admob_five);
                }

            }


            if (titles.getOther_services() == null) {
//                o_service_layout_parent.setVisibility(View.GONE);
            } else {

                if (MyUtility.setShowHide_Container(titles.getOther_services())) {
//                    o_service_layout_parent.setVisibility(View.GONE);
                } else {
//                    o_service_layout_parent.setVisibility(View.VISIBLE);
                }

            }

            if (titles.getTop_advertisement() == null) {
//                top_banner_parent.setVisibility(View.GONE);
            } else {
//                top_banner_parent.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(titles.getNews1())) {
                btn_news_title.setVisibility(View.GONE);
                devider_below_new_ref.setVisibility(View.GONE);
            } else {
                btn_news_title.setVisibility(View.VISIBLE);
                devider_below_new_ref.setVisibility(View.GONE);
            }
            if (titles.getNews1() == null) {
                topNewsParent_layout.setVisibility(View.GONE);
            } else {

                if (MyUtility.setShowHide_Container(titles.getNews1())) {
                    topNewsParent_layout.setVisibility(View.GONE);
                } else {
                    topNewsParent_layout.setVisibility(View.VISIBLE);
                }
            }
            if (titles.getEntertainment() == null) {
                entertainment_parent.setVisibility(View.GONE);
            } else {
                entertainment_parent.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(titles.getTrending_apps())) {
                topapps_ref_layout.setVisibility(View.GONE);
            } else {
                topapps_ref_layout.setVisibility(View.VISIBLE);
            }
            if (titles.getTrending_apps() == null) {
                topapps_parent.setVisibility(View.GONE);
            } else {
                if (MyUtility.setShowHide_Container(titles.getTrending_apps())) {
                    topapps_parent.setVisibility(View.GONE);
                } else {
                    topapps_parent.setVisibility(View.VISIBLE);
                }

            }

            if (TextUtils.isEmpty(titles.getTrending_games())) {
                games_ref_layout.setVisibility(View.GONE);

            } else {
                games_ref_layout.setVisibility(View.VISIBLE);
            }
            if (titles.getTrending_games() == null) {
                games_parent.setVisibility(View.GONE);
            } else {

                games_parent.setVisibility(View.GONE);

            }

            if (TextUtils.isEmpty(titles.getFlash_games_home())) {
                externalGames_ref_layout.setVisibility(View.GONE);

            } else {
                externalGames_ref_layout.setVisibility(View.VISIBLE);
            }
            if (titles.getFlash_games_home() == null) {
                externalGames_parent_layout.setVisibility(View.GONE);
            } else {

                externalGames_parent_layout.setVisibility(View.VISIBLE);
            }


            if (titles.getBottom_advertisement() == null) {
                bottom_adv_parent.setVisibility(View.GONE);
            } else {
                bottom_adv_parent.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(titles.getWallpaper())) {
//                wallpaper_ref_layout.setVisibility(View.GONE);

            } else {
//                wallpaper_ref_layout.setVisibility(View.VISIBLE);
            }
            if (titles.getWallpaper() == null) {
//                wallpaper_parent.setVisibility(View.GONE);
            } else {

//                wallpaper_parent.setVisibility(View.VISIBLE);
            }


            if (TextUtils.isEmpty(titles.getNews2())) {
                title_middle_news.setVisibility(View.GONE);
                devider_title_middle_news.setVisibility(View.GONE);
            } else {
                title_middle_news.setVisibility(View.VISIBLE);
                devider_title_middle_news.setVisibility(View.VISIBLE);
            }
            if (titles.getNews2() == null) {
                middle_news_parent.setVisibility(View.GONE);
            } else {

                middle_news_parent.setVisibility(View.VISIBLE);


            }


            if (TextUtils.isEmpty(titles.getNews3())) {
                btn_bottom_news_title.setVisibility(View.GONE);
                devider_bottom_news_ref.setVisibility(View.GONE);
            } else {
                btn_bottom_news_title.setVisibility(View.GONE);
                devider_bottom_news_ref.setVisibility(View.GONE);
            }

            if (titles.getNews3() == null) {
                bottomNews_parent.setVisibility(View.GONE);
            } else {

                bottomNews_parent.setVisibility(View.VISIBLE);

            }


            if (TextUtils.isEmpty(titles.getNews4())) {
                title_recommended_news.setVisibility(View.GONE);
                devider_below_recommended.setVisibility(View.GONE);
            } else {
                title_recommended_news.setVisibility(View.VISIBLE);
                devider_below_recommended.setVisibility(View.VISIBLE);
            }


            if (titles.getNews4() == null) {
                recomNewsParent_layout.setVisibility(View.GONE);
            } else {

                recomNewsParent_layout.setVisibility(View.VISIBLE);

            }


            if (titles.getPowered_by() == null) {
                powerdByLayout.setVisibility(View.GONE);
            } else {
                powerdByLayout.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(titles.getFloater()) || (arraylist_floater_cate == null ||
                    arraylist_floater_cate.size() <= 0)) {
                fram_layout_floater.setVisibility(View.GONE);
            } else {
                fram_layout_floater.setVisibility(View.VISIBLE);
                Floater_Category_Adapter floater_adapter = new Floater_Category_Adapter
                        (context, arraylist_floater_cate, new Floater_Category_Adapter
                                .OnFloaterClick() {

                            @Override
                            public void onItemClick(int position, String title, String open_with,
                                                    String package_name,
                                                    String redirect_link,
                                                    ArrayList<Floater_sub_Pojo> list) {
                                if (TextUtils.isEmpty(open_with)) {
                                    if (list != null && list.size() > 0) {
                                        closed_floater_from_out();
                                        show_floater_popup(title, list);
                                        MyUtility.saveTracksInDB(context, "Floater Popup",
                                                "Floater Popup", "Floater", "0", title, "");
                                        MyApplication.getInstance().trackEvent("Floater",
                                                "Floater Popup",
                                                title);
                                    } else {
                                        closed_floater_from_out();

                                    }
                                } else {


                                    MyApplication.getInstance().trackEvent("Floater", redirect_link,
                                            title);
                                    MyUtility.handleItemClick(context, package_name,
                                            redirect_link, redirect_link, "Floater", open_with,
                                            title);
                                    closed_floater_from_out();

                                }


                            }
                        });
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                        LinearLayoutManager.VERTICAL, false);
                floater_recyclerView.setLayoutManager(layoutManager);
                floater_recyclerView.setAdapter(floater_adapter);
            }

            String view_hide_all = MyApplication.app_sharedPreferences.getString("view_hide_all",
                    "");
            String view_status = MyApplication.app_sharedPreferences.getString("view_status", "");
            if (view_hide_all.equalsIgnoreCase("yes") || view_hide_all.equalsIgnoreCase
                    ("noconditionhideall") && view_status.equalsIgnoreCase("yes")) {
                String view_model = MyApplication.app_sharedPreferences.getString("view_model", "");
                String view_states = MyApplication.app_sharedPreferences.getString("view_states",
                        "");
                boolean hide_statue = false;

                if (!TextUtils.isEmpty(view_model) && !TextUtils.isEmpty(view_states)) {
                    String getLat = MyApplication.app_sharedPreferences.getString("Latitude",
                            "0.0");
                    String getLong = MyApplication.app_sharedPreferences.getString("Longitude",
                            "0.0");
                    String rkt_state = "";
                    try {
                        rkt_state = MyUtility.getLocationAddress_State(context, Double
                                .parseDouble(getLat), Double.parseDouble(getLong));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!TextUtils.isEmpty(rkt_state) && ((view_states.toLowerCase()).contains
                            (rkt_state.toLowerCase())) && (view_model
                            .toLowerCase()).contains((MyUtility.getDeviceModelName()).toLowerCase
                            ())) {
                        hide_statue = true;
                    } else {
                        hide_statue = false;
                    }
                } else if (!TextUtils.isEmpty(view_states)) {
                    String getLat = MyApplication.app_sharedPreferences.getString("Latitude",
                            "0.0");
                    String getLong = MyApplication.app_sharedPreferences.getString("Longitude",
                            "0.0");
                    String rkt_state = "";
                    try {
                        rkt_state = MyUtility.getLocationAddress_State(context, Double
                                .parseDouble(getLat), Double.parseDouble(getLong));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!TextUtils.isEmpty(rkt_state) && ((view_states.toLowerCase()).contains
                            (rkt_state.toLowerCase()))) {
                        hide_statue = true;
                    } else {
                        hide_statue = false;
                    }
                } else if (!TextUtils.isEmpty(view_model)) {

                    if ((view_model.toLowerCase()).contains(((MyUtility.getDeviceModelName())
                            .toLowerCase()))) {
                        hide_statue = true;
                    } else {
                        hide_statue = false;
                    }
                } else if (view_hide_all.equalsIgnoreCase("noconditionhideall")) {

                    hide_statue = true;

                }


                if (hide_statue && (view_hide_all.equalsIgnoreCase("yes") || view_hide_all
                        .equalsIgnoreCase("noconditionhideall"))) {
                    parent_admob_one.setVisibility(View.GONE);
                    parent_admob_two.setVisibility(View.GONE);
                    parent_admob_three.setVisibility(View.GONE);
                    parent_admob_four.setVisibility(View.GONE);
                    parent_admob_five.setVisibility(View.GONE);
                    btn_service_title.setVisibility(View.GONE);
//                    o_service_layout_parent.setVisibility(View.GONE);
                    service_layout_parent.setVisibility(View.GONE);
                    top_banner_parent.setVisibility(View.GONE);
                    btn_news_title.setVisibility(View.GONE);
                    devider_below_new_ref.setVisibility(View.GONE);
                    topNewsParent_layout.setVisibility(View.GONE);
                    entertainment_parent.setVisibility(View.GONE);
                    topapps_ref_layout.setVisibility(View.GONE);
                    topapps_parent.setVisibility(View.GONE);
                    games_ref_layout.setVisibility(View.GONE);
                    games_parent.setVisibility(View.GONE);
                    externalGames_ref_layout.setVisibility(View.GONE);
                    externalGames_parent_layout.setVisibility(View.GONE);
                    bottom_adv_parent.setVisibility(View.GONE);
//                    wallpaper_ref_layout.setVisibility(View.GONE);
//                    wallpaper_parent.setVisibility(View.GONE);
                    middle_news_parent.setVisibility(View.GONE);
                    bottomNews_parent.setVisibility(View.GONE);
                    recomNewsParent_layout.setVisibility(View.GONE);
                    fram_layout_floater.setVisibility(View.GONE);
                    parent_taboola.setVisibility(View.GONE);

                }
            }


        } else {
            parent_admob_one.setVisibility(View.GONE);
            parent_admob_two.setVisibility(View.GONE);
            parent_admob_three.setVisibility(View.GONE);
            parent_admob_four.setVisibility(View.GONE);
            parent_admob_five.setVisibility(View.GONE);


            btn_service_title.setVisibility(View.GONE);

//            o_service_layout_parent.setVisibility(View.GONE);
            service_layout_parent.setVisibility(View.GONE);

            top_banner_parent.setVisibility(View.GONE);
            btn_news_title.setVisibility(View.GONE);
            devider_below_new_ref.setVisibility(View.GONE);
            topNewsParent_layout.setVisibility(View.GONE);
            entertainment_parent.setVisibility(View.GONE);
            powerdByLayout.setVisibility(View.GONE);
            topapps_ref_layout.setVisibility(View.GONE);
            topapps_parent.setVisibility(View.GONE);
            games_ref_layout.setVisibility(View.GONE);
            games_parent.setVisibility(View.GONE);
            externalGames_ref_layout.setVisibility(View.GONE);
            externalGames_parent_layout.setVisibility(View.GONE);
            bottom_adv_parent.setVisibility(View.GONE);
//            wallpaper_ref_layout.setVisibility(View.GONE);
//            wallpaper_parent.setVisibility(View.GONE);
            middle_news_parent.setVisibility(View.GONE);
            bottomNews_parent.setVisibility(View.GONE);
            recomNewsParent_layout.setVisibility(View.GONE);
            fram_layout_floater.setVisibility(View.GONE);
            parent_taboola.setVisibility(View.GONE);
        }


    }

    private void remove_givenView(RelativeLayout rr) {
        try {
            rr.removeAllViews();
            rr.setVisibility(View.GONE);
        } catch (Exception e) {
            try {
                rr.setVisibility(View.GONE);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

    }

    private void hit_admob_function(int admob_index, RelativeLayout rl) {

        if (arraylist_admob_nubit != null && arraylist_admob_nubit.size() > 0) {
            String admob_id = "";
            try {
                admob_id = arraylist_admob_nubit.get(admob_index).getAdmob_id()
                        .toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String admob_size = "";
            try {
                admob_size = arraylist_admob_nubit.get(admob_index).getAdmob_size()
                        .toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                setup_nubit_admob(admob_id, admob_size, rl);
            } catch (Exception e) {
                e.printStackTrace();
            }
/*
            We dont want parse to many AdmobIDs, so for sake of simplicity, I am saving the 3rd
            Index
            Id in sharedpreference for showing the ads in YoupTV Page......So in short if
            admob_nubit array in getData
             have value at 3rd index then it should show ads on homepage somewhere and also in
             YUPTV Screen at bottom.*/
            try {
                if (admob_index != 0 && admob_index == 3) {
                    MyApplication.app_editor.putString("yup_ad_id", admob_id).apply();
                    MyApplication.app_editor.putString("yup_ad_size", admob_size).apply();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }


    private void setup_nubit_admob(String admobId, String admobSize, final RelativeLayout
            rel_Layout) {
        /* adView.setAdUnitId("ca-app-pub-6136742319000358/2067197049");*/

        if (TextUtils.isEmpty(admobSize)) {
            admobSize = "MEDIUM_RECTANGLE";
        }


        if (!TextUtils.isEmpty(admobId) && rel_Layout != null) {
            final AdView adView = new AdView(context);
            adView.setAdUnitId(admobId);
            if (admobSize.equalsIgnoreCase("BANNER")) {
                adView.setAdSize(AdSize.BANNER);
            } else if (admobSize.equalsIgnoreCase("LARGE_BANNER")) {
                adView.setAdSize(AdSize.LARGE_BANNER);
            } else if (admobSize.equalsIgnoreCase("MEDIUM_RECTANGLE")) {
                adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
            } else if (admobSize.equalsIgnoreCase("SMART_BANNER")) {
                adView.setAdSize(AdSize.SMART_BANNER);
            } else if (admobSize.equalsIgnoreCase("LEADERBOARD")) {
                adView.setAdSize(AdSize.LEADERBOARD);
            } else {
                adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
            }
           /* AdRequest adRequest = new AdRequest.Builder().addTestDevice
                    ("68A6308D09F439DCC42E3F151794B949").build();
            adView.loadAd(adRequest);*/

            try {
                AdRequest adRequest = new AdRequest.Builder().build();
                adView.loadAd(adRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }

            rel_Layout.setVisibility(View.VISIBLE);

            adView.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                }

                @Override
                public void onAdFailedToLoad(int i) {
                    super.onAdFailedToLoad(i);


                    /*  rel_Layout.setVisibility(View.GONE);*/
                }

                @Override
                public void onAdLeftApplication() {
                    super.onAdLeftApplication();
                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();
                    //System.out.println(TAG + " onAdOpened");
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    try {
                        if (adView.getParent() == null) {
                            rel_Layout.addView(adView);
                        } else {
                            ((ViewGroup) adView.getParent()).removeView(adView);

                            rel_Layout.addView(adView);

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        }

    }

    private void closed_floater_from_out() {
        clickcount_floater = clickcount_floater + 1;
        nubit_floater.startAnimation(rotate_forward);
        fram_layout_floater.setBackgroundColor(Color.TRANSPARENT);
        floater_recyclerView.setVisibility(View.GONE);
    }

    public void show_floater_popup(String title, ArrayList<Floater_sub_Pojo> list) {

        final Dialog floater_dialog = new Dialog(context);
        floater_dialog.setContentView(R.layout.floater_sub_category);
        floater_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color
                .TRANSPARENT));
        TextView txt_dialog_title = floater_dialog.findViewById(R.id.txt_dialog_title);
        ImageView popup_cross_button = floater_dialog.findViewById(R.id.popup_cross_button);
        fram_layout_floater.setVisibility(View.VISIBLE);
        fram_layout_floater.setBackgroundColor(Color.parseColor("#99000000"));
        txt_dialog_title.setText(title);


        if (!((Activity) context).isFinishing()) {
            floater_dialog.show();
        }




        /* Log.d("MyList", String.valueOf(list));*/


        Grid_Floater_Sub_CatAdapter floater_subCate_grid_adapter = new
                Grid_Floater_Sub_CatAdapter(context, list);
        MyGridView floater_subCate_grid = floater_dialog.findViewById(R.id
                .floater_subcategory_grid);
        floater_subCate_grid.setAdapter(floater_subCate_grid_adapter);


        floater_dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                fram_layout_floater.setBackgroundColor(Color.TRANSPARENT);

            }
        });
        popup_cross_button.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                floater_dialog.dismiss();

                return false;
            }
        });


    }

    private void init_floater_veiw(View view) {

        nubit_floater = view.findViewById(R.id.nubit_floater);
        rotate_forward = AnimationUtils.loadAnimation(context, R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(context, R.anim.rotate_backward);
        floater_recyclerView = view.findViewById(R.id.floater_recyclerView);
        floater_recyclerView.setVisibility(View.GONE);

        nubit_floater.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().trackEvent("Floater", "Floater Clicked", "Floater");
                animateMyFloater();

            }
        });


    }


    public void animateMyFloater() {


        clickcount_floater = clickcount_floater + 1;
        if ((clickcount_floater % 2) == 0) {
            nubit_floater.startAnimation(rotate_forward);


            fram_layout_floater.setBackgroundColor(Color.TRANSPARENT);
            /* ad_plus_floater_layout.setBackgroundColor(Color.TRANSPARENT);*/
            floater_recyclerView.setVisibility(View.GONE);


        } else {
            nubit_floater.startAnimation(rotate_backward);
            fram_layout_floater.setBackgroundColor(Color.parseColor("#99000000"));
            /*   ad_plus_floater_layout.setBackgroundColor(Color.parseColor("#99000000"));*/
            floater_recyclerView.setVisibility(View.VISIBLE);


        }
    }

    private void showBannerDialog(){
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int counter = prefs.getInt(COUNTER_KEY, 0);
        final Dialog dialog = new Dialog(context);

        // Remove the default title bar
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set the custom layout for the dialog
        dialog.setContentView(R.layout.banner_ad_dialog);

        // Initialize AdView
        ImageView iv_banner = dialog.findViewById(R.id.iv_banner);
        iv_banner.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        // Set click listener for the close button
        ImageButton closeButton = dialog.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the dialog when close button is clicked
                dialog.dismiss();
            }
        });
        dialog.show();
        // Increment the counter
        counter++;

        // Save the updated counter value
        prefs.edit().putInt(COUNTER_KEY, counter).apply();
    }

    public void show_publisher_popup(Context cc, ArrayList<NewPubPojo> list) {

        final Dialog
                publisher_dialog = new Dialog(cc);
        publisher_dialog.setContentView(R.layout.publisher_pop_layout);
        publisher_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color
                .TRANSPARENT));
        TextView txt_dialog_title = publisher_dialog.findViewById(R.id.txt_dialog_title);
        ImageView popup_cross_button = publisher_dialog.findViewById(R.id.popup_cross_button);
        Button
                btn_pub_popup = publisher_dialog.findViewById(R.id.btn_pub_popup);

        String
                get_pubName = MyApplication.app_sharedPreferences.getString("select_publisher", "");
        String
                pub_btn = MyApplication.app_sharedPreferences.getString("pub_btn", "");

        if
        (!TextUtils.isEmpty(get_pubName)) {
            txt_dialog_title.setText(get_pubName);
        }
        if
        (!TextUtils.isEmpty(pub_btn)) {
            btn_pub_popup.setText(pub_btn);
        }

        if (!((Activity) context).isFinishing()) {
            publisher_dialog.show();
        }


        Grid_NewsPub grid_newsPub = new
                Grid_NewsPub(cc, list);
        GridView pub_grid = publisher_dialog.findViewById(R.id
                .news_pub_grid);
        pub_grid.setAdapter(grid_newsPub);


        publisher_dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                String pub_name = MyApplication.app_sharedPreferences.getString("pub_name",
                        "");
                MyApplication.getInstance().trackEvent("News Publisher Popup", pub_name, "News " +
                        "Publisher Popup");

                init_BottomNews_Views();

                MyUtility.saveTracksInDB(context, "", "", "News Publisher Popup", "1",
                        pub_name, "");
            }
        });
        popup_cross_button.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
/*
                String pub_name = MyApplication.app_sharedPreferences.getString("pub_name",
                        "");
                MyApplication.getInstance().trackEvent("News Publisher", pub_name, "News " +
                        "Publisher");*/
                publisher_dialog.dismiss();

                return false;
            }
        });

        btn_pub_popup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  String pub_name = MyApplication.app_sharedPreferences.getString("pub_name",
                        "");
                MyApplication.getInstance().trackEvent("News Publisher", pub_name, "News " +
                        "Publisher");*/
                publisher_dialog.dismiss();

            }
        });


    }

    public void show_videoCat_popup(Context cc, ArrayList<VideoCat> list) {

        final Dialog
                videoCat_dialog = new Dialog(cc);
        videoCat_dialog.setContentView(R.layout.publisher_pop_layout);
        videoCat_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color
                .TRANSPARENT));
        TextView txt_dialog_title = videoCat_dialog.findViewById(R.id.txt_dialog_title);
        ImageView popup_cross_button = videoCat_dialog.findViewById(R.id.popup_cross_button);
        Button btn_videocat_popup = videoCat_dialog.findViewById(R.id.btn_pub_popup);

        String vid_pop_title = MyApplication.app_sharedPreferences.getString
                ("vid_pop_title", "");
        String pub_btn = MyApplication.app_sharedPreferences.getString("pub_btn", "");

        if
        (!TextUtils.isEmpty(vid_pop_title)) {
            txt_dialog_title.setText(vid_pop_title);
        }
        if
        (!TextUtils.isEmpty(pub_btn)) {
            btn_videocat_popup.setText(pub_btn);
        }


        if (!((Activity) context).isFinishing()) {
            videoCat_dialog.show();
        }

        Grid_VideoCat grid_videoCat = new
                Grid_VideoCat(cc, list);
        GridView videoCat_grid = videoCat_dialog.findViewById(R.id
                .news_pub_grid);
        videoCat_grid.setNumColumns(2);
        videoCat_grid.setAdapter(grid_videoCat);


        videoCat_dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                String cat_title = MyApplication.app_sharedPreferences.getString("cat_title",
                        "");

                MyApplication.getInstance().trackEvent("Video Category Popup", cat_title, "Video " +
                        "Category Popup");

                init_top_banner_view();

                MyUtility.saveTracksInDB(context, "", "", "Video Category Popup", "1",
                        cat_title, "");

            }
        });
        popup_cross_button.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
           /*     String cat_title = MyApplication.app_sharedPreferences.getString("cat_title",
                        "");

                MyApplication.getInstance().trackEvent("Video Category", cat_title, "Video " +
                        "Category");
                init_top_banner_view();*/
                videoCat_dialog.dismiss();

                return false;
            }
        });

        btn_videocat_popup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
       /*         String cat_title = MyApplication.app_sharedPreferences.getString("cat_title",
                        "");

                MyApplication.getInstance().trackEvent("Video Category", cat_title, "Video " +
                        "Category");*/
                videoCat_dialog.dismiss();

            }
        });


    }

    public void add_sortedView() {


        Map<Integer, String> sortedMap = null;
        try {
            sortedMap = new TreeMap<>(sort_Order_map);
        } catch (Exception e1) {
            e1.printStackTrace();
        }


        try {
            layout_container_nubitview.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            layout_container_nubitview.addView(rkt_webview);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            if (view_Map.get(sortedMap.get(0)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(0)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            if (view_Map.get(sortedMap.get(1)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(1)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

//        try {
//            layout_container_nubitview.addView(admob_rkt_one);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
        try {
            if (view_Map.get(sortedMap.get(2)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(2)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            if (view_Map.get(sortedMap.get(3)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(3)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
//        try {
//            layout_container_nubitview.addView(admob_rkt_two);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
        try {
            if (view_Map.get(sortedMap.get(4)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(4)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        try {
            if (view_Map.get(sortedMap.get(5)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(5)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
//        try {
//            layout_container_nubitview.addView(admob_rkt_three);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }

        Log.e("checkorders","1>>" + sortedMap.get(8) + ">>" + view_Map.get(sortedMap.get(8)));
        try {
            if (view_Map.get(sortedMap.get(6)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(6)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
//        try {
//            layout_container_nubitview.addView(admob_rkt_four);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//
        try {
            if (view_Map.get(sortedMap.get(7)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(7)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
//        try {
//            layout_container_nubitview.addView(admob_rkt_five);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//
        try {
            if (view_Map.get(sortedMap.get(8)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(8)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }


        try {
            if (view_Map.get(sortedMap.get(9)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(9)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            if (view_Map.get(sortedMap.get(10)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(10)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
//        try {
//            if (view_Map.get(sortedMap.get(11)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(11)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(12)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(12)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(13)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(13)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(14)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(14)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(15)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(15)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(16)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(16)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(17)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(17)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(18)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(18)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(19)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(19)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(20)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(20)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(21)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(21)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(22)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(22)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(23)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(23)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(24)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(24)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(25)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(25)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//
//        try {
//            if (view_Map.get(sortedMap.get(26)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(26)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }

    }

    private static NubitViewNew nubitView;

    public static NubitViewNew getInstance(Context context) {
        if (nubitView == null) {
            nubitView = new NubitViewNew(context);
        }

        return nubitView;
    }

    private void hit_admob_mobin() {

        boolean hit_status = false;


        String admob_status_rkt = MyApplication.app_sharedPreferences.getString
                ("admob_status_rkt", "");
        if (!TextUtils.isEmpty(admob_status_rkt)) {
            String admob_states_rkt = MyApplication.app_sharedPreferences.getString
                    ("admob_states_rkt", "");
            String admob_model_rkt = MyApplication.app_sharedPreferences.getString
                    ("admob_model_rkt", "");
            String admob_id1_rkt = MyApplication.app_sharedPreferences.getString
                    ("admob_id1_rkt", "");
            String admob_id2_rkt = MyApplication.app_sharedPreferences.getString
                    ("admob_id2_rkt", "");
            String admob_id3_rkt = MyApplication.app_sharedPreferences.getString
                    ("admob_id3_rkt", "");
            String admob_id4_rkt = MyApplication.app_sharedPreferences.getString
                    ("admob_id4_rkt", "");
            String admob_id5_rkt = MyApplication.app_sharedPreferences.getString
                    ("admob_id5_rkt", "");


            // If admob_type_home is null then all the Ads on home screen should be
            // Medium_Ractangle, else as per admob_type_home
            String admob_type_home = MyApplication.app_sharedPreferences.getString
                    ("admob_type_home", "");


            if (admob_status_rkt.equalsIgnoreCase("yes")) {


                if (!TextUtils.isEmpty(admob_model_rkt) && !TextUtils.isEmpty
                        (admob_states_rkt)) {
                    String getLat = MyApplication.app_sharedPreferences.getString("Latitude",
                            "0.0");

                    String getLong = MyApplication.app_sharedPreferences.getString("Longitude",
                            "0.0");
                    String rkt_state = "";
                    try {
                        rkt_state = MyUtility.getLocationAddress_State(context, Double
                                .parseDouble(getLat), Double.parseDouble(getLong));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!TextUtils.isEmpty(rkt_state) && ((admob_states_rkt.toLowerCase())
                            .contains
                                    (rkt_state.toLowerCase())) && (admob_model_rkt
                            .toLowerCase()).contains((MyUtility.getDeviceModelName())
                            .toLowerCase
                                    ())) {
                        hit_status = true;
                    } else {
                        hit_status = false;
                    }
                } else if (!TextUtils.isEmpty(admob_states_rkt)) {
                    String getLat = MyApplication.app_sharedPreferences.getString("Latitude",
                            "0.0");
                    String getLong = MyApplication.app_sharedPreferences.getString("Longitude",
                            "0.0");
                    String rkt_state = "";
                    try {
                        rkt_state = MyUtility.getLocationAddress_State(context, Double
                                .parseDouble(getLat), Double.parseDouble(getLong));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (!TextUtils.isEmpty(rkt_state) && ((admob_states_rkt.toLowerCase())
                            .contains
                                    (rkt_state.toLowerCase()))) {
                        hit_status = true;
                    } else {
                        hit_status = false;
                    }
                } else if (!TextUtils.isEmpty(admob_model_rkt)) {

                    if ((admob_model_rkt.toLowerCase()).contains(((MyUtility
                            .getDeviceModelName())
                            .toLowerCase()))) {
                        hit_status = true;
                    } else {
                        hit_status = false;
                    }
                } else if (TextUtils.isEmpty(admob_model_rkt) && TextUtils.isEmpty
                        (admob_states_rkt)) {
                    hit_status = true;
                }

                if (!TextUtils.isEmpty(admob_id1_rkt) && hit_status) {
                    setup_nubit_admob(admob_id1_rkt, admob_type_home, admob_rkt_one);
                } else {
                    try {
                        admob_rkt_one.removeAllViews();
                        admob_rkt_one.setVisibility(View.GONE);
                    } catch (Exception e) {
                        admob_rkt_one.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                }
                if (!TextUtils.isEmpty(admob_id2_rkt) && hit_status) {
                    setup_nubit_admob(admob_id2_rkt, admob_type_home, admob_rkt_two);
                } else {
                    try {
                        admob_rkt_two.removeAllViews();
                        admob_rkt_two.setVisibility(View.GONE);
                    } catch (Exception e) {
                        admob_rkt_two.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                }
                if (!TextUtils.isEmpty(admob_id3_rkt) && hit_status) {
                    setup_nubit_admob(admob_id3_rkt, admob_type_home, admob_rkt_three);
                } else {
                    try {
                        admob_rkt_three.removeAllViews();
                        admob_rkt_three.setVisibility(View.GONE);
                    } catch (Exception e) {
                        admob_rkt_three.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                }

                if (!TextUtils.isEmpty(admob_id4_rkt) && hit_status) {
                    setup_nubit_admob(admob_id4_rkt, admob_type_home, admob_rkt_four);
                } else {
                    try {
                        admob_rkt_four.removeAllViews();
                        admob_rkt_four.setVisibility(View.GONE);
                    } catch (Exception e) {
                        admob_rkt_four.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                }

                if (!TextUtils.isEmpty(admob_id5_rkt) && hit_status) {
                    setup_nubit_admob(admob_id5_rkt, admob_type_home, admob_rkt_five);
                } else {
                    try {
                        admob_rkt_five.removeAllViews();
                        admob_rkt_five.setVisibility(View.GONE);
                    } catch (Exception e) {
                        admob_rkt_five.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                }


            }
        } else {
            try {
                admob_rkt_one.setVisibility(View.GONE);
                admob_rkt_two.setVisibility(View.GONE);
                admob_rkt_three.setVisibility(View.GONE);
                admob_rkt_four.setVisibility(View.GONE);
                admob_rkt_five.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private void setUp_MobinView() {

        String view_status = MyApplication.app_sharedPreferences.getString("view_status", "");

        if (TextUtils.isEmpty(view_status) || view_status.equalsIgnoreCase("no") || !view_status
                .equalsIgnoreCase("yes")) {
            rkt_webview.setVisibility(View.GONE);
            return;
        } else {
            String view_url = MyApplication.app_sharedPreferences.getString("view_url", "");
            String view_height = MyApplication.app_sharedPreferences.getString("view_height",
                    "");
            String view_model = MyApplication.app_sharedPreferences.getString("view_model", "");
            String view_states = MyApplication.app_sharedPreferences.getString("view_states",
                    "");


            if (!TextUtils.isEmpty(view_model) && !TextUtils.isEmpty(view_states)) {
                String getLat = MyApplication.app_sharedPreferences.getString("Latitude",
                        "0.0");
                String getLong = MyApplication.app_sharedPreferences.getString("Longitude",
                        "0.0");
                String rkt_state = "";
                try {
                    rkt_state = MyUtility.getLocationAddress_State(context, Double
                            .parseDouble(getLat), Double.parseDouble(getLong));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!TextUtils.isEmpty(rkt_state) && ((view_states.toLowerCase()).contains
                        (rkt_state.toLowerCase())) && (view_model
                        .toLowerCase()).contains((MyUtility.getDeviceModelName()).toLowerCase
                        ())) {
                    rkt_webview.setVisibility(View.VISIBLE);
                } else {
                    rkt_webview.setVisibility(View.GONE);
                }
            } else if (!TextUtils.isEmpty(view_states)) {
                String getLat = MyApplication.app_sharedPreferences.getString("Latitude",
                        "0.0");
                String getLong = MyApplication.app_sharedPreferences.getString("Longitude",
                        "0.0");
                String rkt_state = "";
                try {
                    rkt_state = MyUtility.getLocationAddress_State(context, Double
                            .parseDouble(getLat), Double.parseDouble(getLong));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (!TextUtils.isEmpty(rkt_state) && ((view_states.toLowerCase()).contains
                        (rkt_state.toLowerCase()))) {
                    rkt_webview.setVisibility(View.VISIBLE);
                } else {
                    rkt_webview.setVisibility(View.GONE);
                }
            } else if (!TextUtils.isEmpty(view_model)) {

                if ((view_model.toLowerCase()).contains(((MyUtility.getDeviceModelName())
                        .toLowerCase()))) {
                    rkt_webview.setVisibility(View.VISIBLE);
                } else {
                    rkt_webview.setVisibility(View.GONE);
                }
            } else if (TextUtils.isEmpty(view_model) && TextUtils.isEmpty(view_states)) {
                rkt_webview.setVisibility(View.VISIBLE);
            }


            if (!TextUtils.isEmpty(view_height)) {
                try {
                    rkt_webview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup
                            .LayoutParams.MATCH_PARENT, Integer.parseInt(view_height)));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


            WebSettings s = rkt_webview.getSettings();
            s.setBuiltInZoomControls(false);
//            s.setAppCachePath(context.getCacheDir().getPath());
//            s.setAppCacheEnabled(true);
            s.setCacheMode(WebSettings.LOAD_DEFAULT);
            s.setUseWideViewPort(true);
            s.setLoadWithOverviewMode(true);
            s.setJavaScriptEnabled(true);
            rkt_webview.getSettings().setDomStorageEnabled(true);
            rkt_webview.getSettings().setDatabaseEnabled(true);

            if (!TextUtils.isEmpty(view_url)) {
                try {
                    rkt_webview.loadUrl(view_url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }


    }

//    private void init_TaboolaView() {
//
//        try {
//            // Initialize the taboola instance
////            taboolaWidget = new TaboolaWidget(context);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//
//            LayoutParams params = new LayoutParams(
//                    LayoutParams.MATCH_PARENT,
//                    LayoutParams.MATCH_PARENT
//            );
//
//            params.setMargins(23, 10, 23, 10);
//            taboolaWidget.setLayoutParams(params);
//
//            parent_taboola.addView(taboolaWidget);
//            taboolaWidget.setPublisher("nubit-nubitappsdk")
//                    /*  .setMode("thumbnails-a")
//                      .setPlacement("SDK App Minus One Screen SC")
//                      .setPageUrl("https://www.nubit.com")
//                      .setPageType("Category")
//                      .setPageId("https://www.nubit.com"); //default value is the relative path of
//              // the pageUrl provided.*/
//
//                    .setMode("thumbnails-a")
//                    .setPlacement("SDK App Minus One Screen SC")
//                    .setPageType("category")
//                    .setPageId("https://www.nubit.com")
//                    .setPageUrl("https://www.nubit.com");
//
//
//            taboolaWidget.fetchContent();
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public void callPushTracksApi(String tracksVal, Context activity) {
        if (pushTracksOnServerApiTask != null && pushTracksOnServerApiTask.getStatus().equals
                (AsyncTask.Status.RUNNING)) {
            return;
        }

        String tracks = tracksVal;

        pushTracksOnServerApiTask = new PushTracksOnServerApiTask(activity, tracks, new
                PushTracksOnServerApiTask.PushTracksDataCallback() {
                    @Override
                    public void onSuccess(String response) {

                        pushTracksOnServerApiTask = null;
                        try {
                            if (!TextUtils.isEmpty(response) && MyUtility.isJSONValid(response)) {
                                Gson gson= new Gson();
                                CommonResponse commonResponse = gson.fromJson(response,
                                        CommonResponse
                                                .class);

                                if (commonResponse.getSuccess()) {
                                    deleteTracksFromDB();
                                } else {

                                }

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure() {
                        pushTracksOnServerApiTask = null;
                    }
                });

        if (MyUtility.isConnectedToInternet()) {
            TaskHelper.execute(pushTracksOnServerApiTask);
        }
    }

    private void deleteTracksFromDB() {

        try {
            DbHandler dbHandler = DbHandler.getInstance(context);
            SQLiteDatabase _sqlDb = dbHandler.openDb(1);
            TrackingDAO.getInstance().deleteAllData(_sqlDb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * prepare jsonObject for analytics data
     */
    private void createTracksJson(ArrayList<TrackingPojo> trackingPojoArrayList) {

        JSONObject tracks = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject jarrayOBJ = new JSONObject();
        try {
            tracks.put("device_id", MyUtility.getDeviceID(context));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            tracks.put("advertisement_Id", MyApplication.app_sharedPreferences.getString
                    ("getAdvertisementId", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            tracks.put("user_agent", MyUtility.getUserAgent());
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (int i = 0; i < trackingPojoArrayList.size(); i++) {
            jarrayOBJ = new JSONObject();

            try {
                jarrayOBJ.put("type", trackingPojoArrayList.get(i).getCategory());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (trackingPojoArrayList.get(i).getUrl() != null && !TextUtils.isEmpty
                        (trackingPojoArrayList.get(i).getUrl()))
                    jarrayOBJ.put("url", trackingPojoArrayList.get(i).getUrl());
                else
                    jarrayOBJ.put("url", "NF");
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                jarrayOBJ.put("title", trackingPojoArrayList.get(i).getTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                jarrayOBJ.put("redirect_url", trackingPojoArrayList.get(i).getRedirectURL());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                jarrayOBJ.put("play_duration", trackingPojoArrayList.get(i).getPlay_duration());
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                jarrayOBJ.put("launcher", trackingPojoArrayList.get(i).getLaunch());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                jarrayOBJ.put("datetime", trackingPojoArrayList.get(i).getDate());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                jarrayOBJ.put("timestamp", convertDateFormat(trackingPojoArrayList.get(i).getDate
                        ()) + i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            jsonArray.put(jarrayOBJ);

        }

        try {
            tracks.put("mobile_clicks", jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        }


        callPushTracksApi(tracks.toString(), context);
    }

    private String convertDateFormat(String dateVal) {

        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        Date date = null;
        try {
            date = originalFormat.parse(dateVal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(date);

        return formattedDate;
    }

    private void pushTracksOnServer() {
        ArrayList<TrackingPojo> trackingPojoArrayList = new ArrayList<>();

        DbHandler dbHandler = DbHandler.getInstance(context);
        SQLiteDatabase _sqlDb = dbHandler.openDb(1);
        trackingPojoArrayList = TrackingDAO.getInstance().getTracksFromDB(_sqlDb);

        if (trackingPojoArrayList != null && trackingPojoArrayList.size() > 0) {

            createTracksJson(trackingPojoArrayList);
        }

    }
}
//                .interface_VideoCat/*, RktPlayerCallBack.UpdateonPlayerInit*/ {
//    boolean post_delay_running = false;
//
//    String scrollStatus = "home";
//    PushTracksOnServerApiTask pushTracksOnServerApiTask;
//
//
//    RecyclerView entertain_recyclerView, wallpaper_recyclerView, external_games_recyclerView,
//            bottomNews_recyclerView, middleNews_recyclerView, bottomAdv_recyclerView,
//            games_recyclerView,
//            topapps_recyclerView, top_News_recyclerView, floater_recyclerView,
//            other_ser_recyclerView;
//
//    View devider_below_btnService, devider_below_recommended;
//    Response response;
//    AutoScrollViewPager home_topBanner_Pager;
//    Button btn_add_pub;
//    TextView btn_service_title, btn_bottom_news_title, title_middle_news,
//            btn_news_title,
//            btn_topapps_title, title_recommended_news,
//            btn_games_title, btn_wallpaper_title, btn_externalGames_title;
//
//    RktVideoView mVideoView;
//
//
//    CircleIndicator topBanner_indicator;
//
//    GridView service_grid;
////    private TaboolaWidget taboolaWidget;
//
//    public DrawerLayout drawerLayout;
//    public FloatingActionButton nubit_floater;
//
//    FrameLayout parent_taboola;
//
//    public EditText edt_search;
//    LinearLayout entertainment_parent, topapps_parent,
//            games_parent, wallpaper_parent, bottom_adv_parent, middle_news_parent,
//            powerdByLayout;
//    Map<Integer, String> sort_Order_map = new HashMap<>();
//    Map<String, View> view_Map = new HashMap<>();
//    View devider_bottom_news_ref, devider_title_middle_news;
//    DropDownViewControler dropDownViewControler;
//
//
//    ProgressBar progress_home;
//    NavigationView navigationView;
//    CustomNestedScrollView nested_scroolview_home;
//
//
//    RelativeLayout admob_rkt_one, admob_rkt_two, admob_rkt_three,
//            admob_rkt_four, admob_rkt_five, layoutmovetop, bottomNews_parent,
//            recomNewsParent_layout;
//    ImageView nav_icon;
//
//    WebView rkt_webview;
//
//    ImageView topapps_news_ref, img_games_ref, img_wallpaper_ref, img_externalGames_ref, ad_cross;
//
//    LinearLayout layout_container_nubitview, externalGames_parent_layout;
//    RelativeLayout topNewsParent_layout, parent_admob_one, parent_admob_two, parent_admob_four,
//            parent_admob_three, topapps_ref_layout, games_ref_layout, parent_admob_five,
//            wallpaper_ref_layout, service_layout_parent, top_banner_parent,
//            externalGames_ref_layout, o_service_layout_parent;
//    View devider_below_new_ref;
//
//    int clickcount_floater = 0;
//    FrameLayout fram_layout_floater;
//    /*   LinearLayout ad_plus_floater_layout;*/
//
//    private boolean shouldScroll = false;
//    private Animation rotate_forward;
//    private Animation rotate_backward;
//
//
//    Context context;
//    boolean isAsyncTaskRunning;
//
//
//    RelativeLayout parent_nubitview, layoutAdMob;
//
//
//    ArrayList<HomeVideoPojo> arraylist_top_adver;
//    ArrayList<OtherServicesPojo> arraylist_other_ser;
//    ArrayList<Top_News_Pojo> arraylist_top_news;
//
//    ArrayList<Entertainment_pojo> arraylist_entertainment;
//    ArrayList<TopApps_Pojo> arraylist_topApps;
//    ArrayList<GamesPojo> arraylist_games;
//    ArrayList<NewPubPojo> arraylist_newspub;
//    ArrayList<NewsByPub_Pojo> arraylist_newsbyPub;
//    ArrayList<HomeVideoPojo> updatedList;
//    ArrayList<External_game_pojo> arraylist_Flashgames;
//    ArrayList<Bottom_adv_Pojo> arraylist_b_a_banner;
//    ArrayList<Wallpaper_Pojo> arraylist_wallpaper;
//    ArrayList<Middle_News_Pojo> arraylist_middle_news;
//    ArrayList<Bottom_News_Pojo> arraylist_bottom_news;
//    ArrayList<Services_pojo> arraylist_services;
//    ArrayList<NavigationPojo> arraylist_nav;
//    ArrayList<VideoCat> arraylist_videocat;
//    ArrayList<Top_News_Pojo> arraylist_recom_news;
//    ArrayList<Admob_nubitPojo> arraylist_admob_nubit;
//    ArrayList<Floater_Category_Pojo> arraylist_floater_cate;
//
//    ProgressBar pro_nointernet;
//    Button btn_refresh;
//
//
//    RelativeLayout nointernet_parent;
//
//    RecyclerView news_recom_recyclerView;
//
//    View myView;
//
//
//    public NubitView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        this.context = context;
//        returnNubitView();
//    }
//
//    public NubitView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        this.context = context;
//        returnNubitView();
//    }
//
//    public NubitView(Context context) {
//        super(context);
//        this.context = context;
//        returnNubitView();
//    }
//
//
//    private View returnNubitView() {
//
//        myView = inflate(context, R.layout.layout_return_view, this);
//
//        if (TextUtils.isEmpty(MyApplication.app_sharedPreferences.getString
//                ("getAdvertisementId", ""))) {
//            try {
//                MyUtility.getAdvertisementID(MyApplication.ctx);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        layout_container_nubitview = myView.findViewById(R.id.layout_container_nubitview);
//        parent_nubitview = myView.findViewById(R.id.parent_nubitview);
//        init_Add_View(myView);
//        layoutmovetop = myView.findViewById(R.id.layoutmovetop);
//        nested_scroolview_home = myView.findViewById(R.id.nested_scroolview_home);
//        nested_scroolview_home.setUpScollview(layoutmovetop);
//        edt_search = myView.findViewById(R.id.edt_search);
//
//        nointernet_parent = myView.findViewById(R.id.nointernet_parent);
//        progress_home = myView.findViewById(R.id.progress_home);
//        btn_refresh = myView.findViewById(R.id.btn_refresh);
//        pro_nointernet = myView.findViewById(R.id.pro_nointernet);
//        drawerLayout = myView.findViewById(R.id.drawer);
//        navigationView = myView.findViewById(R.id.navigation_view);
//        rkt_webview = myView.findViewById(R.id.rkt_webview);
//        fram_layout_floater = myView.findViewById(R.id.fram_layout_floater);
//        /* ad_plus_floater_layout = myView.findViewById(R.id.ad_plus_floater_layout);*/
//        layoutAdMob = myView.findViewById(R.id.layoutAdMob);
//        ad_cross = myView.findViewById(R.id.ad_cross);
//        nav_icon = myView.findViewById(R.id.nav_icon);
//        mVideoView = new RktVideoView(context);
//
//        btn_refresh.setOnClickListener(this);
//        nav_icon.setOnClickListener(this);
//      /*  try {
//            // Initialize the taboola instance
//            taboolaWidget = new TaboolaWidget(context);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }*/
//
//
//
//
//
//        /* RefreshDataCallBack.getInstance().notifyOnRefreshStart();*/
//        try {
//            LanguageUpdaterCallBack.getInstance().initListenerForLanguage(this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            LanguageUpdaterCallBack.getInstance().initListenerForLanguage(this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            Video_Cat_CallBAck.getInstance().init_interface_VideoCat(this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            PagersAutoScrollControllerCallback.getInstance().setListener(this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
///*
//        try {
//            RktPlayerCallBack.getInstance().setListener(this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }*/
//
//
//        hitVersion();
//        hitGetProfile();
//        if (!TextUtils.isEmpty(MyApplication.app_sharedPreferences.getString("key_modifiedDat",
//                ""))) {
//            RefreshDataCallBack.getInstance().notifyOnRefreshStart();
//
//            parseData();
//
//
//        } else {
//
//
//            if (MyUtility.isConnectedToInternet()) {
//                /*Here notifying the launcher to start loading in case if user is new user*/
//                RefreshDataCallBack.getInstance().notifyOnRefreshStart();
//
//            } else {
//                parent_nubitview.setVisibility(View.VISIBLE);
//                RefreshDataCallBack.getInstance().notifyOnNoInternet();
//
//            }
//        }
//
//
//        /*    edt_search.setOnTouchListener(context);*/
//        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    performSearch();
//                    return true;
//                }
//
//
//                return false;
//            }
//        });
//        if (shouldScroll == true) {
//            start_Scroll();
//        } else {
//            stop_Scroll("home", 0);
//        }
//
//
//        home_topBanner_Pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int
//                    positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                /*   Log.d("scroll_position", String.valueOf(position));*/
//               /* if (position == 5) {
//                    stop_Scroll();
//                }
//*/
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                if (ViewPager.SCROLL_STATE_DRAGGING == state) {
//                    home_topBanner_Pager.stopAutoScroll();
//
//
//                    if (shouldScroll == true) {
//
//                        final Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                if (scrollStatus.equalsIgnoreCase("loading")) {
//                                    home_topBanner_Pager.stopAutoScroll();
//                                } else {
//                                    scrollStatus = "home";
//                                    home_topBanner_Pager.startAutoScroll();
//
//
//                                }
//
//                                /* post_delay_running = true;*/
//                            }
//                        }, 3000);
//
//                    }
//
//                    //Log.d("SCROLL_STATE_DRAGGING", "Yes");
//
//                }
//            }
//        });
//
//
//        try {
//            // This is sending the analytical data to nubit server, but it should be call on
//            // launcher hide like in nubit, not from here.
//
//
//            pushTracksOnServer();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        return myView;
//    }
//
//
//    private void show_fullScreenBanner(String isFullScreenBanner, String advbanner_delay,
//                                       String advbanner_banner_image, String advbanner_ad_unit_id,
//                                       String advbanner_redirectLink, String advbanner_open_with,
//                                       String advbanner_title, String webview_url, String
//                                               splash_time) {
//
//        String get_last_bannerShow = MyApplication.app_sharedPreferences
//                .getString
//                        ("last_bannerShow", "");
//
//        if (TextUtils.isEmpty(get_last_bannerShow)) {
//
//            DialogFragment dialogFragment = RobiUpdateDialog.newInstance
//                    (isFullScreenBanner, advbanner_delay,
//                            advbanner_banner_image,
//                            advbanner_ad_unit_id, advbanner_redirectLink,
//                            advbanner_open_with, advbanner_title, webview_url, splash_time);
//            dialogFragment.show(((Activity) context).getFragmentManager(), "RobiUpdateDialog");
//
//        } else if (!TextUtils.isEmpty(get_last_bannerShow) && !TextUtils.isEmpty
//                (advbanner_delay) &&
//                advbanner_delay.equalsIgnoreCase("100")) {
//
//            DialogFragment dialogFragment = RobiUpdateDialog.newInstance
//                    (isFullScreenBanner, advbanner_delay,
//                            advbanner_banner_image,
//                            advbanner_ad_unit_id, advbanner_redirectLink,
//                            advbanner_open_with, advbanner_title, webview_url, splash_time);
//            dialogFragment.show(((Activity) context).getFragmentManager(), "RobiUpdateDialog");
//
//
//        } else if (!TextUtils.isEmpty(get_last_bannerShow) && !TextUtils.isEmpty
//                (advbanner_delay)) {
//            if (MyUtility.getTimeDifference_nubit("fullBanner") > Long.parseLong(advbanner_delay)) {
//                DialogFragment dialogFragment = RobiUpdateDialog.newInstance
//                        (isFullScreenBanner, advbanner_delay,
//                                advbanner_banner_image,
//                                advbanner_ad_unit_id, advbanner_redirectLink,
//                                advbanner_open_with, advbanner_title, webview_url, splash_time);
//                dialogFragment.show(((Activity) context).getFragmentManager
//                        (), "RobiUpdateDialog");
//
//            }
//        }
//    }
//
//
//    private void init_Add_View(View view) {
//
//        adddefaultView();
//        home_topBanner_Pager = view.findViewById(R.id.viewpager_topBanner);
//        topBanner_indicator = view.findViewById(R.id.indicator);
//        other_ser_recyclerView = view.findViewById(R.id.o_services_recyclerView);
//        service_layout_parent = view.findViewById(R.id.service_layout_parent);
//        dropDownViewControler = view.findViewById(R.id.dropdown_below_service_grid);
//        top_News_recyclerView = view.findViewById(R.id.news_top_recyclerView);
//        news_recom_recyclerView = view.findViewById(R.id.news_recom_recyclerView);
//        topapps_recyclerView = view.findViewById(R.id.topapps_recyclerView);
//        bottomNews_recyclerView = view.findViewById(R.id.bottomNews_recyclerView);
//        service_grid = view.findViewById(R.id.service_grid);
//        btn_add_pub = view.findViewById(R.id.btn_add_pub);
//        entertain_recyclerView = view.findViewById(R.id.entertainment_recyclerView);
//        bottomAdv_recyclerView = view.findViewById(R.id.bottom_adv_recyclerView);
//        games_recyclerView = view.findViewById(R.id.games_recyclerView);
//        external_games_recyclerView = view.findViewById(R.id.externalGames_recyclerView);
//        wallpaper_recyclerView = view.findViewById(R.id.wallpaper_recyclerView);
//        middleNews_recyclerView = view.findViewById(R.id.middle_news_recyclerView);
//        topapps_news_ref = view.findViewById(R.id.topapps_news_ref);
//        img_games_ref = view.findViewById(R.id.img_games_ref);
//        img_wallpaper_ref = view.findViewById(R.id.img_wallpaper_ref);
//        img_externalGames_ref = view.findViewById(R.id.img_externalGames_ref);
//        entertainment_parent = view.findViewById(R.id.entertainment_parent);
//        topapps_parent = view.findViewById(R.id.topapps_parent_layout);
//        games_parent = view.findViewById(R.id.games_parent_layout);
//        wallpaper_parent = view.findViewById(R.id.wallpaper_parent_layout);
//        parent_taboola = view.findViewById(R.id.parent_taboola);
//
//        bottom_adv_parent = view.findViewById(R.id.bottom_adv_parent);
//        middle_news_parent = view.findViewById(R.id.middle_news_parent);
//        bottomNews_parent = view.findViewById(R.id.bottomNews_parent);
//        recomNewsParent_layout = view.findViewById(R.id.recomNewsParent_layout);
//        powerdByLayout = view.findViewById(R.id.powerdByLayout);
//        service_layout_parent = view.findViewById(R.id.service_layout_parent);
//        top_News_recyclerView = view.findViewById(R.id.news_top_recyclerView);
//        wallpaper_recyclerView = view.findViewById(R.id.wallpaper_recyclerView);
//        /* irctc_List = view.findViewById(R.id.irctc_List);*/
//        external_games_recyclerView = view.findViewById(R.id.externalGames_recyclerView);
//        bottomNews_recyclerView = view.findViewById(R.id.bottomNews_recyclerView);
//        middleNews_recyclerView = view.findViewById(R.id.middle_news_recyclerView);
//        bottomAdv_recyclerView = view.findViewById(R.id.bottom_adv_recyclerView);
//        games_recyclerView = view.findViewById(R.id.games_recyclerView);
//        topapps_recyclerView = view.findViewById(R.id.topapps_recyclerView);
//        entertain_recyclerView = view.findViewById(R.id.entertainment_recyclerView);
//        other_ser_recyclerView = view.findViewById(R.id.o_services_recyclerView);
//
//        parent_admob_one = view.findViewById(R.id.parent_admob_one);
//        parent_admob_two = view.findViewById(R.id.parent_admob_two);
//        parent_admob_three = view.findViewById(R.id.parent_admob_three);
//        parent_admob_four = view.findViewById(R.id.parent_admob_four);
//        parent_admob_five = view.findViewById(R.id.parent_admob_five);
//
//
//        btn_service_title = view.findViewById(R.id.btn_service_title);
//
//        btn_news_title = view.findViewById(R.id.btn_news_title);
//        btn_bottom_news_title = view.findViewById(R.id.btn_bottom_news_title);
//        title_middle_news = view.findViewById(R.id.title_middle_news);
//        title_recommended_news = view.findViewById(R.id.title_recommended_news);
//        btn_topapps_title = view.findViewById(R.id.btn_topapps_title);
//        btn_games_title = view.findViewById(R.id.btn_games_title);
//        btn_wallpaper_title = view.findViewById(R.id.btn_wallpaper_title);
//        btn_externalGames_title = view.findViewById(R.id.btn_externalGames_title);
//
//        service_grid = (MyGridView) view.findViewById(R.id.service_grid);
//        dropDownViewControler = view.findViewById(R.id.dropdown_below_service_grid);
//        devider_below_btnService = view.findViewById(R.id.devider_below_btnService);
//        devider_below_recommended = view.findViewById(R.id.devider_below_recommended);
//
//
//        admob_rkt_one = view.findViewById(R.id.admob_rkt_one);
//        admob_rkt_two = view.findViewById(R.id.admob_rkt_two);
//        admob_rkt_three = view.findViewById(R.id.admob_rkt_three);
//        admob_rkt_four = view.findViewById(R.id.admob_rkt_four);
//        admob_rkt_five = view.findViewById(R.id.admob_rkt_five);
//
//
//        topapps_news_ref = view.findViewById(R.id.topapps_news_ref);
//        img_games_ref = view.findViewById(R.id.img_games_ref);
//        img_wallpaper_ref = view.findViewById(R.id.img_wallpaper_ref);
//        img_externalGames_ref = view.findViewById(R.id.img_externalGames_ref);
//        topNewsParent_layout = view.findViewById(R.id.topNewsParent_layout);
//
//        o_service_layout_parent = view.findViewById(R.id.o_service_layout_parent);
//
//        topapps_ref_layout = view.findViewById(R.id.topapps_ref_layout);
//        top_banner_parent = view.findViewById(R.id.top_banner_parent);
//        games_ref_layout = view.findViewById(R.id.games_ref_layout);
//        externalGames_ref_layout = view.findViewById(R.id.externalGames_ref_layout);
//        wallpaper_ref_layout = view.findViewById(R.id.wallpaper_ref_layout);
//        devider_bottom_news_ref = view.findViewById(R.id.devider_bottom_news_ref);
//        devider_title_middle_news = view.findViewById(R.id.devider_title_middle_news);
//        externalGames_parent_layout = view.findViewById(R.id.externalGames_parent_layout);
//        devider_below_new_ref = view.findViewById(R.id.devider_below_new_ref);
//
//        topapps_news_ref.setOnClickListener(this);
//        img_games_ref.setOnClickListener(this);
//        img_wallpaper_ref.setOnClickListener(this);
//        img_externalGames_ref.setOnClickListener(this);
//        btn_add_pub.setOnClickListener(this);
//
//        init_floater_veiw(view);
//
//
//    }
//
//
//    private void parseData() {
//
//
//        // In mean time check if data already with you....show case that data to user.
//
//        arraylist_other_ser = new ArrayList<>();
//        arraylist_top_adver = new ArrayList<>();
//        arraylist_top_news = new ArrayList<>();
//        arraylist_entertainment = new ArrayList<>();
//
//        arraylist_newspub = new ArrayList<>();
//        arraylist_topApps = new ArrayList<>();
//        arraylist_games = new ArrayList<>();
//        arraylist_b_a_banner = new ArrayList<>();
//        arraylist_wallpaper = new ArrayList<>();
//        arraylist_middle_news = new ArrayList<>();
//        arraylist_bottom_news = new ArrayList<>();
//        arraylist_services = new ArrayList<>();
//        arraylist_Flashgames = new ArrayList<>();
//        arraylist_nav = new ArrayList<>();
//        arraylist_videocat = new ArrayList<>();
//        arraylist_newsbyPub = new ArrayList<>();
//        arraylist_recom_news = new ArrayList<>();
//        arraylist_admob_nubit = new ArrayList<>();
//        arraylist_floater_cate = new ArrayList<>();
//
//
//        String get_SavedJson = MyApplication.app_sharedPreferences.getString
//                ("save_app_Json",
//                        "");
//        if (!TextUtils.isEmpty(get_SavedJson)) {
//            Gson gson = new Gson();
//            response = gson.fromJson(get_SavedJson, Response.class);
//            Data data = response.getData();
//            Configuration configuration = response.getConfiguration();
//            try {
//                arraylist_top_adver.addAll(data.getVideos());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                if (response.getSlot_rotation() != null && response.getSlot_rotation().size()
//                        > 0) {
//                    shouldScroll = true;
//                    try {
//                        PagersAutoScrollControllerCallback.getInstance().notify(true, "home",
//                                0);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    shouldScroll = false;
//                    try {
//                        PagersAutoScrollControllerCallback.getInstance().notify(false,
//                                "home", 0);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                arraylist_other_ser.addAll(data.getOther_services());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                arraylist_services.addAll(data.getServices());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                arraylist_top_news.addAll(data.getNews1());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try {
//                arraylist_newspub.addAll(data.getNews_publishers());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                arraylist_recom_news.addAll(data.getNews4());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                arraylist_admob_nubit.addAll(data.getAdmob_nubit());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//            try {
//                arraylist_topApps.addAll(data.getTop_apps());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try {
//                arraylist_newsbyPub.addAll(data.getNews_data());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                arraylist_bottom_news.addAll(data.getNews3());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                arraylist_entertainment.addAll(data.getEntertainment());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                arraylist_b_a_banner.addAll(data.getBottom_advertisement());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                arraylist_games.addAll(data.getGames());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//            try {
//                arraylist_Flashgames.addAll(data.getExternal_game());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                arraylist_wallpaper.addAll(data.getWallpaper());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try {
//                arraylist_middle_news.addAll(data.getNews2());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try {
//                arraylist_videocat.addAll(data.getVideos_categories());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//            try {
//                arraylist_nav.addAll(data.getDrawer_item());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try {
//                arraylist_floater_cate.addAll(data.getFloater());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                if (data.getSearchbar() != null && data.getSearchbar().size() > 0) {
//                    MyApplication.app_editor.putString("search_redirection", data.getSearchbar()
//                            .get(0).getRedirect_link()).apply();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//            try {
//                if (data.getSort_order() != null && data.getSort_order().size() > 0) {
//                    for (SortOrder sortOrder : data.getSort_order()) {
//                        try {
//                            sort_Order_map.put(Integer.parseInt(sortOrder.getSortOrder()),
//                                    sortOrder
//                                            .getOrderType());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//            if (!TextUtils.isEmpty(configuration.getYuptv_update_version())) {
//                MyApplication.app_editor.putString("yuptv_update_version", configuration
//                        .getYuptv_update_version()).apply();
//            } else {
//                MyApplication.app_editor.putString("yuptv_update_version", "").apply();
//            }
//
//            if (!TextUtils.isEmpty(configuration.getuTube_AdType())) {
//                MyApplication.app_editor.putString("uTube_AdType", configuration
//                        .getuTube_AdType()).apply();
//            } else {
//                MyApplication.app_editor.putString("uTube_AdType", "").apply();
//            }
//
//            if (!TextUtils.isEmpty(configuration.getuTube_AdId())) {
//                MyApplication.app_editor.putString("uTube_AdId", configuration
//                        .getuTube_AdId()).apply();
//            } else {
//                MyApplication.app_editor.putString("uTube_AdId", "").apply();
//            }
//
//            if (!TextUtils.isEmpty(configuration.getWallpaper_adID())) {
//                MyApplication.app_editor.putString("wallpaper_adID", configuration
//                        .getWallpaper_adID()).apply();
//            } else {
//                MyApplication.app_editor.putString("wallpaper_adID", "").apply();
//            }
//
//            if (TextUtils.isEmpty(configuration.getLike_dislike())) {
//                MyApplication.app_editor.putString("like_dislike", "").commit();
//            } else {
//                MyApplication.app_editor.putString("like_dislike", configuration
//                        .getLike_dislike()).apply();
//            }
//
//
//            if (!TextUtils.isEmpty(configuration.getAd_type_chanel())) {
//                MyApplication.app_editor.putString("ad_type_chanel", configuration
//                        .getAd_type_chanel()).apply();
//            } else {
//                MyApplication.app_editor.putString("ad_type_chanel", "").apply();
//            }
//
//            if (!TextUtils.isEmpty(configuration.getAd_id_chanel())) {
//                MyApplication.app_editor.putString("ad_id_chanel", configuration
//                        .getAd_id_chanel()).apply();
//            } else {
//                MyApplication.app_editor.putString("ad_id_chanel", "").apply();
//            }
//
//
//            if (!TextUtils.isEmpty(configuration.getAdmob_banner_placemnt_id())) {
//                MyApplication.app_editor.putString("admob_banner_placemnt_id", configuration
//                        .getAdmob_banner_placemnt_id()).apply();
//
//                String get_iscross_player = "";
//                if (!TextUtils.isEmpty(configuration.getIscross_home())) {
//                      /*  MyApplication.app_editor.putString("iscross_home", configuration
//                                .getIscross_home()).apply();*/
//                    get_iscross_player = configuration.getIscross_home();
//                } else {
//                    get_iscross_player = "";
//                    /*  MyApplication.app_editor.putString("iscross_home", "").apply();*/
//                }
//
//                String get_banner_types = "";
//                if (!TextUtils.isEmpty(configuration.getBanner_type_home())) {
//                    get_banner_types = configuration.getBanner_type_home();
//                } else {
//                    get_banner_types = "";
//
//                }
//
//
//                MyUtility.setUpBannerAd(context, configuration.getAdmob_banner_placemnt_id(),
//                        layoutAdMob, get_banner_types, ad_cross, get_iscross_player);
//
//            }
//
//
//            if (TextUtils.isEmpty(configuration.getVideoShare_showHide())) {
//                MyApplication.app_editor.putString("videoShare_showHide", "").commit();
//            } else {
//                MyApplication.app_editor.putString("videoShare_showHide", configuration
//                        .getVideoShare_showHide
//                                ()).apply();
//            }
//            if (TextUtils.isEmpty(configuration.getNews_pub_shwHide())) {
//                MyApplication.app_editor.putString("news_pub_shwHide", "").commit();
//            } else {
//                MyApplication.app_editor.putString("news_pub_shwHide", configuration
//                        .getNews_pub_shwHide
//                                ()).apply();
//            }
//
//            if (!TextUtils.isEmpty(configuration.getBucket_name())) {
//                MyApplication.app_editor.putString("bucket_name", configuration
//                        .getBucket_name()).apply();
//            } else {
//                MyApplication.app_editor.putString("bucket_name", "").apply();
//            }
//
//
//            if (!TextUtils.isEmpty(configuration.getS3_secret_key())) {
//                MyApplication.app_editor.putString("s3_secret_key", configuration
//                        .getS3_secret_key()).apply();
//            } else {
//                MyApplication.app_editor.putString("s3_secret_key", "").apply();
//            }
//
//            if (!TextUtils.isEmpty(configuration.getS3_access_key())) {
//                MyApplication.app_editor.putString("s3_access_key", configuration
//                        .getS3_access_key()).apply();
//            } else {
//                MyApplication.app_editor.putString("s3_access_key", "").apply();
//            }
//
//
//            if (!TextUtils.isEmpty(configuration.getDefault_language())) {
//                MyApplication.app_editor.putString("language_selection", configuration
//                        .getDefault_language()).apply();
//            } else {
//                MyApplication.app_editor.putString("language_selection", "").apply();
//            }
//
//            if (!TextUtils.isEmpty(configuration.getAdmob_cache())) {
//                if ((configuration.getAdmob_cache()).equalsIgnoreCase("yesrkt")) {
//                    MyApplication.app_editor.remove("view_duration").apply();
//                }
//
//            }
//
//
//            if (arraylist_newspub != null) {
//                if (arraylist_newspub.size() > 0) {
//
//                    String news_pub_shwHide = MyApplication.app_sharedPreferences.getString
//                            ("news_pub_shwHide", "");
//                    if (!TextUtils.isEmpty(news_pub_shwHide) & news_pub_shwHide
//                            .equalsIgnoreCase("1")) {
//                        btn_add_pub.setVisibility(View.VISIBLE);
//                    } else {
//                        btn_add_pub.setVisibility(View.GONE);
//                    }
//
//
//                } else {
//                    btn_add_pub.setVisibility(View.GONE);
//                }
//            } else {
//                btn_add_pub.setVisibility(View.GONE);
//            }
//
//
//            if (TextUtils.isEmpty(configuration.getVideoCat_showHide())) {
//                MyApplication.app_editor.putString("videoCat_showHide", "").commit();
//            } else {
//                MyApplication.app_editor.putString("videoCat_showHide", configuration
//                        .getVideoCat_showHide
//                                ()).apply();
//            }
//
//
//            if (!TextUtils.isEmpty(configuration.getSocial_login())) {
//                MyApplication.app_editor.putString("social_login_profile", configuration
//                        .getSocial_login()).apply();
//            } else {
//                MyApplication.app_editor.putString("social_login_profile", "").apply();
//            }
//            if (TextUtils.isEmpty(configuration.getWallpaper_share_flg())) {
//                MyApplication.app_editor.putString("wallpaper_share_flg", "").commit();
//            } else {
//                MyApplication.app_editor.putString("wallpaper_share_flg", configuration
//                        .getWallpaper_share_flg()).apply();
//            }
//
//            if (TextUtils.isEmpty(configuration.getNews_share_flg())) {
//                MyApplication.app_editor.putString("news_share_flg", "").commit();
//            } else {
//                MyApplication.app_editor.putString("news_share_flg", configuration
//                        .getNews_share_flg
//                                ()).apply();
//            }
//
//
//            if (TextUtils.isEmpty(configuration.getShareBy_brandName())) {
//                MyApplication.app_editor.putString("shareBy_brandName", "").commit();
//            } else {
//                MyApplication.app_editor.putString("shareBy_brandName", configuration
//                        .getShareBy_brandName
//                                ()).apply();
//            }
//
//            if (TextUtils.isEmpty(configuration.getShare_content())) {
//                MyApplication.app_editor.putString("getShare", "").commit();
//            } else {
//                MyApplication.app_editor.putString("getShare", configuration
//                        .getShare_content
//                                ()).apply();
//            }
//            if (TextUtils.isEmpty(configuration.getNews_share())) {
//                MyApplication.app_editor.putString("news_share", "").commit();
//            } else {
//                MyApplication.app_editor.putString("news_share", configuration.getNews_share
//                        ()).apply();
//            }
//
//
//            if (TextUtils.isEmpty(configuration.getWallpaper_share())) {
//                MyApplication.app_editor.putString("wallpaper_share", "").commit();
//            } else {
//                MyApplication.app_editor.putString("wallpaper_share", configuration
//                        .getWallpaper_share
//                                ()).apply();
//            }
//
//            if (TextUtils.isEmpty(configuration.getVideo_share())) {
//                MyApplication.app_editor.putString("video_share", "").commit();
//            } else {
//                MyApplication.app_editor.putString("video_share", configuration
//                        .getVideo_share
//                                ()).apply();
//            }
//
//
//        }
//
//
//
//
//        /*   progress_home.setVisibility(View.GONE);*/
//
//
//        try {
//            parent_nubitview.setVisibility(View.VISIBLE);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        /*Here notifying the launcher to stop the loading & to start showing UI*/
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                RefreshDataCallBack.getInstance().notifyOnRefreshFinish();
//            }
//        }, 1000);
//
//        /*All arrayList added to all adapters after parsing the data*/
//        addAdapter_to_View();
//
//
//        hit_admob_mobin();
//        setUp_MobinView();
//
//
//
//        /*Intialize navigation click listner*/
//        onNav_ItemSelected();
//
//    }
//
//    private void addAdapter_to_View() {
//
//        // Sort Order logic start here
//        add_sortedView();
//        // Sort Order logic end here
//
//        init_top_banner_view();
//        init_other_services_view();
//        init_service_view();
//        init_TopNews_Views();
//        init_Recom_News_Views();
//        init_TopApps_Views();
//        init_BottomNews_Views();
//        init_Entertainment_Views();
//        init_BottomAdv_Views();
//        init_Games_Views();
//        init_external_game_view();
//        init_Wallpaper_Views();
//        init_MiddleNews_Views();
//        setUpNavigationViewData();
//       // init_TaboolaView();
//        init_Title_view();
//
//
//    }
//
//
//    private void hitVersion() {
//
//
//        if (!isAsyncTaskRunning) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                new CheckServerForNewData_asyn().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//            } else {
//                new CheckServerForNewData_asyn().execute();
//            }
//        }
//    }
//
//
//    private void init_top_banner_view() {
//
//        String cat_title = MyApplication.app_sharedPreferences.getString("cat_title",
//                "");
//
//        try {
//            if (!TextUtils.isEmpty(cat_title)) {
//                if (arraylist_top_adver != null) {
//                    if (arraylist_top_adver.size() > 0) {
//
//                        updatedList = new ArrayList<>();
//                        for (int i = 0; i < arraylist_top_adver.size(); i++) {
//                            HomeVideoPojo dataItem = arraylist_top_adver.get(i);
//
//                            if (!TextUtils.isEmpty(dataItem.getCategory_name())) {
//                                if (cat_title.contains(dataItem.getCategory_name())) {
//                                    updatedList.add(dataItem);
//                                }
//                            }
//                        }
//
//                        if (updatedList != null) {
//                            if (updatedList.size() > 0) {
//                                TopBanner_Adapter topBanner_adapter = new TopBanner_Adapter
//                                        (context, updatedList);
//                                home_topBanner_Pager.setAdapter(topBanner_adapter);
//
//                            }
//                        }
//
//
//                    } else {
//                        updatedList = null;
//                        TopBanner_Adapter _topBanner_adapter = new TopBanner_Adapter(context,
//                                arraylist_top_adver);
//                        home_topBanner_Pager.setAdapter(_topBanner_adapter);
//                        _topBanner_adapter.notifyDataSetChanged();
//                    }
//                } else {
//                    updatedList = null;
//                    TopBanner_Adapter _topBanner_adapter = new TopBanner_Adapter(context,
//                            arraylist_top_adver);
//                    home_topBanner_Pager.setAdapter(_topBanner_adapter);
//                    _topBanner_adapter.notifyDataSetChanged();
//                }
//
//            } else {
//                updatedList = null;
//                TopBanner_Adapter _topBanner_adapter = new TopBanner_Adapter(context,
//                        arraylist_top_adver);
//                home_topBanner_Pager.setAdapter(_topBanner_adapter);
//                _topBanner_adapter.notifyDataSetChanged();
//            }
//        } catch (Exception e) {
//            updatedList = null;
//            TopBanner_Adapter _topBanner_adapter = new TopBanner_Adapter(context,
//                    arraylist_top_adver);
//            home_topBanner_Pager.setAdapter(_topBanner_adapter);
//            _topBanner_adapter.notifyDataSetChanged();
//            e.printStackTrace();
//        }
//
//
//        home_topBanner_Pager.setCurrentItem(0);
//        home_topBanner_Pager.setOffscreenPageLimit(1);
//        home_topBanner_Pager.getLayoutParams().height = (int) (MyUtility.getScreenWidth
//                (home_topBanner_Pager.getContext()) / (1.4));
//
//        topBanner_indicator.setViewPager(home_topBanner_Pager);
//        home_topBanner_Pager.setOnTouchListener(new InterceptTouchListener(new
//                                                                                   InterceptTouchListener.OnItemClickListener() {
//                                                                                       @Override
//                                                                                       public void
//                                                                                       onItemClick(View v, int position) {
//                                                                                           HomeVideoPojo top_banner_pojo;
//
//                                                                                           if
//                                                                                           (updatedList != null && updatedList.size() > 0) {
//                                                                                               top_banner_pojo = updatedList.get(position);
//                                                                                           } else {
//                                                                                               top_banner_pojo = arraylist_top_adver.get(position);
//                                                                                           }
//
//
//                                                                                           MyApplication.getInstance().trackEvent("Video", top_banner_pojo.getRedirect_link(), top_banner_pojo.getTitle());
//
//
//                                                                                           if
//                                                                                           (!TextUtils.isEmpty(top_banner_pojo.getVideo_table_type()) && top_banner_pojo.getVideo_table_type()
//                                                                                                   .equalsIgnoreCase("video")) {
//                                                                                               try {
//                                                                                                   PagersAutoScrollControllerCallback.getInstance().notify(false, "home", position);
//                                                                                               } catch (Exception e) {
//                                                                                                   e.printStackTrace();
//                                                                                               }
//                                                                                        /*
//                                                                                        MyUtility
//                                                                                        .saveTracksInDB(context, top_banner_pojo.getYoutube_link(),
//                                                                                                       top_banner_pojo.getYoutube_link(), "Video", "0", top_banner_pojo.getTitle());*/
//                                                                                               MyUtility.playYouTubeVideo(context, top_banner_pojo.getYoutube_id(), top_banner_pojo.getYoutube_link(), top_banner_pojo.getVideo_table_type(), top_banner_pojo.getTitle());
//                                                                                           } else if
//                                                                                           (!TextUtils.isEmpty(top_banner_pojo.getVideo_table_type()) && top_banner_pojo.getVideo_table_type()
//                                                                                                           .equalsIgnoreCase("other")) {
//                                                                                               try {
//                                                                                                   PagersAutoScrollControllerCallback.getInstance().notify(false, "home", position);
//                                                                                               } catch (Exception e) {
//                                                                                                   e.printStackTrace();
//                                                                                               }
//                                                                                          /*
//                                                                                          MyUtility.saveTracksInDB(context, top_banner_pojo.getYoutube_link(),
//                                                                                                       top_banner_pojo.getYoutube_link(), "Video", "0", top_banner_pojo.getTitle());*/
//                                                                                               Intent send = new Intent(context, PlayerActivity.class);
//                                                                                               send.putExtra("videoUrl", top_banner_pojo.getYoutube_link());
//                                                                                               send.putExtra("videoTitle", top_banner_pojo.getTitle());
//                                                                                               send.putExtra("imageURL", top_banner_pojo.getBanner_image());
//                                                                                               context.startActivity(send);
//                                                                                           } else if
//                                                                                           (!TextUtils.isEmpty(top_banner_pojo.getVideo_table_type()) && top_banner_pojo.getVideo_table_type()
//                                                                                                           .equalsIgnoreCase("chanel")) {
//                                                                                               try {
//                                                                                                   PagersAutoScrollControllerCallback.getInstance().notify(false, "home", position);
//                                                                                               } catch (Exception e) {
//                                                                                                   e.printStackTrace();
//                                                                                               }
//
//                                                                                               Intent send = new Intent(context, YouTube_Screen.class);
//                                                                                               send.putExtra("videoUrl", top_banner_pojo.getYoutube_link());
//                                                                                               send.putExtra("get_YouTubeID", top_banner_pojo.getYoutube_id());
//                                                                                               send.putExtra("videoTitle", top_banner_pojo.getTitle());
//
//                                                                                               context.startActivity(send);
//                                                                                           } else {
//
//                                                                                               MyUtility.handleItemClick(context, top_banner_pojo.getPackage_name(),
//                                                                                                       top_banner_pojo.getRedirect_link(), top_banner_pojo.getThumb(), "Video", top_banner_pojo.getOpen_with(), top_banner_pojo.getTitle());
//                                                                                           }
//                                                                                       }
//                                                                                   }
//
//        ));
//    }
//
//
//    public void adddefaultView() {
//
//        LayoutInflater inflator = LayoutInflater.from(context);
//        View top_advertisement = inflator.inflate(R.layout.layout_top_banner, null);
//        View other_services = inflator.inflate(R.layout.layout_other_services, null);
//        View admob_rkt_one = inflator.inflate(R.layout.admob_rkt_one, null);
//        View admob_one = inflator.inflate(R.layout.admob_nubit_one, null);
//        View services = inflator.inflate(R.layout.layout_services, null);
//        View news1 = inflator.inflate(R.layout.layout_news_top, null);
//        View admob_rkt_two = inflator.inflate(R.layout.admob_rkt_two, null);
//        View admob_two = inflator.inflate(R.layout.admob_nubit_two, null);
//        View top_apps = inflator.inflate(R.layout.layout_topapps, null);
//        View news3 = inflator.inflate(R.layout.layout_bottom_news, null);
//        View admob_rkt_three = inflator.inflate(R.layout.admob_rkt_three, null);
//        View admob_three = inflator.inflate(R.layout.admob_nubit_three, null);
//        View entertainment = inflator.inflate(R.layout.layout_entertainment, null);
//        View bottom_advertisement = inflator.inflate(R.layout.layout_bottom_adver, null);
//        View games = inflator.inflate(R.layout.layout_games_home, null);
//        View admob_rkt_four = inflator.inflate(R.layout.admob_rkt_four, null);
//        View admob_four = inflator.inflate(R.layout.admob_nubit_four, null);
//        View external_games = inflator.inflate(R.layout.layout_flash_games, null);
//        View wallpaper = inflator.inflate(R.layout.layout_wallpaper, null);
//        View admob_five = inflator.inflate(R.layout.admob_nubit_five, null);
//        View news2 = inflator.inflate(R.layout.layout_middle_news, null);
//        View taboola_view = inflator.inflate(R.layout.layout_taboola, null);
//        View news4 = inflator.inflate(R.layout.layout_recom_news, null);
//        View admob_rkt_five = inflator.inflate(R.layout.admob_rkt_five, null);
//        View power_by = inflator.inflate(R.layout.layout_poweredby, null);
//
//
//        layout_container_nubitview.addView(top_advertisement);
//        layout_container_nubitview.addView(admob_rkt_one);
//        layout_container_nubitview.addView(admob_one);
//        layout_container_nubitview.addView(other_services);
//        layout_container_nubitview.addView(services);
//        layout_container_nubitview.addView(news1);
//        layout_container_nubitview.addView(admob_rkt_two);
//        layout_container_nubitview.addView(admob_two);
//        layout_container_nubitview.addView(top_apps);
//        layout_container_nubitview.addView(news2);
//        layout_container_nubitview.addView(admob_rkt_three);
//        layout_container_nubitview.addView(admob_three);
//        layout_container_nubitview.addView(external_games);
//        layout_container_nubitview.addView(entertainment);
//        layout_container_nubitview.addView(news4);
//        layout_container_nubitview.addView(games);
//        layout_container_nubitview.addView(admob_rkt_four);
//        layout_container_nubitview.addView(admob_four);
//        layout_container_nubitview.addView(wallpaper);
//        layout_container_nubitview.addView(bottom_advertisement);
//        layout_container_nubitview.addView(taboola_view);
//        layout_container_nubitview.addView(admob_rkt_five);
//        layout_container_nubitview.addView(admob_five);
//        layout_container_nubitview.addView(news3);
//
//        layout_container_nubitview.addView(power_by);
//
//
//        view_Map.put("top_advertisement", top_advertisement);
//        view_Map.put("other_services", other_services);
//        view_Map.put("admob_rkt_one", admob_rkt_one);
//        view_Map.put("admob_one", admob_one);
//
//        view_Map.put("services", services);
//        view_Map.put("news1", news1);
//        view_Map.put("admob_rkt_two", admob_rkt_two);
//        view_Map.put("admob_two", admob_two);
//
//        view_Map.put("entertainment", entertainment);
//        view_Map.put("news4", news4);
//        view_Map.put("admob_rkt_three", admob_rkt_three);
//        view_Map.put("admob_three", admob_three);
//
//
//        view_Map.put("top_apps", top_apps);
//        view_Map.put("games", games);
//        view_Map.put("flash_games", external_games);
//        view_Map.put("admob_rkt_four", admob_rkt_four);
//
//
//        view_Map.put("admob_four", admob_four);
//        view_Map.put("wallpaper", wallpaper);
//        view_Map.put("admob_rkt_five", admob_rkt_five);
//        view_Map.put("admob_five", admob_five);
//
//
//        view_Map.put("bottom_advertisement", bottom_advertisement);
//        view_Map.put("news2", news2);
//        view_Map.put("taboola_view", taboola_view);
//        view_Map.put("news3", news3);
//        view_Map.put("power_by", power_by);
//
//
//    }
//
//
//    private void init_other_services_view() {
//        Other_Services_Adapter other_services_adapter = new Other_Services_Adapter(context,
//                arraylist_other_ser);
//        other_ser_recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
//                LinearLayoutManager.HORIZONTAL, false);
//        other_ser_recyclerView.setLayoutManager(layoutManager);
//        other_ser_recyclerView.setAdapter(other_services_adapter);
//
//    }
//
//    private void init_service_view() {
//
//        final Service_Adapter service_grid_adapter;
//        if (arraylist_services != null) {
//            if (arraylist_services.size() > 8) {
//                dropDownViewControler.setVisibility(View.VISIBLE);
//                dropDownViewControler.bindViews();
//                service_grid_adapter = new Service_Adapter(context, dropDownViewControler
//                        .setUpList(arraylist_services));
//                service_grid.setAdapter(service_grid_adapter);
//                dropDownViewControler.setDropDownClick(new DropDownViewControler
//                        .OnDropDownViewClick() {
//                    @Override
//                    public void onClick(List<Services_pojo> data) {
//
//                        service_grid_adapter.updateList(data);
//
//
//                    }
//                });
//            } else {
//                dropDownViewControler.setVisibility(View.GONE);
//                service_grid_adapter = new Service_Adapter(context, arraylist_services);
//                service_grid.setAdapter(service_grid_adapter);
//            }
//        }
////        MyGridView.setDynamicHeight(service_grid);
//    }
//
//    private void init_TopNews_Views() {
//        Top_News_Adapter top_news_adapter = new Top_News_Adapter(context, arraylist_top_news);
//        top_News_recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
//                LinearLayoutManager.VERTICAL, false);
//        top_News_recyclerView.setLayoutManager(layoutManager);
//        top_News_recyclerView.setNestedScrollingEnabled(false);
//        top_News_recyclerView.setAdapter(top_news_adapter);
//
//
//    }
//
//    private void init_Recom_News_Views() {
//        Recom_News_Adapter reco_news_adapter = new Recom_News_Adapter(context,
//                arraylist_recom_news);
//        news_recom_recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
//                LinearLayoutManager.VERTICAL, false);
//        news_recom_recyclerView.setLayoutManager(layoutManager);
//        news_recom_recyclerView.setNestedScrollingEnabled(false);
//        news_recom_recyclerView.setAdapter(reco_news_adapter);
//
//
//    }
//
//
//    private void init_TopApps_Views() {
//
//        TopAppsAdapter topApps_adapter = new TopAppsAdapter(context, arraylist_topApps);
//        LinearLayoutManager layoutManager_topApps = new LinearLayoutManager(context,
//                LinearLayoutManager
//                        .HORIZONTAL, false);
//        topapps_recyclerView.setLayoutManager(layoutManager_topApps);
//        topapps_recyclerView.setAdapter(topApps_adapter);
//
//        topapps_recyclerView.setOnTouchListener(new InterceptTouchListener(new
//                                                                                   InterceptTouchListener.OnItemClickListener() {
//                                                                                       @Override
//                                                                                       public void
//                                                                                       onItemClick(View v, int position) {
//
//                                                                                           final TopApps_Pojo top_apps_pojo = arraylist_topApps.get(position);
//                                                                                           MyApplication.getInstance().trackEvent("Apps", top_apps_pojo.getRedirect_link(), top_apps_pojo.getTitle());
//                                                                                           MyUtility.handleItemClick(context, top_apps_pojo.getPackage_name(),
//                                                                                                   top_apps_pojo.getRedirect_link(), top_apps_pojo.getBanner_thumb_image(), "Apps", top_apps_pojo.getOpen_with(), top_apps_pojo.getTitle());
//
//                                                                                       }
//                                                                                   }
//        ));
//
//    }
//
//    private void init_BottomNews_Views() {
//        bottomNews_recyclerView.setHasFixedSize(true);
//        bottomNews_recyclerView.setNestedScrollingEnabled(false);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
//                LinearLayoutManager.VERTICAL, false);
//        bottomNews_recyclerView.setLayoutManager(layoutManager);
//
//
//        String pub_name = MyApplication.app_sharedPreferences.getString("pub_name",
//                "");
//
//        try {
//            if (!TextUtils.isEmpty(pub_name)) {
//
//
//                if (arraylist_newsbyPub != null) {
//                    if (arraylist_newsbyPub.size() > 0) {
//
//                        ArrayList<NewsByPub_Pojo> updatedList = new ArrayList<>();
//
//                        for (int i = 0; i < arraylist_newsbyPub.size(); i++) {
//                            NewsByPub_Pojo dataItem = arraylist_newsbyPub.get(i);
//
//                            if (!TextUtils.isEmpty(dataItem.getNewsBy())) {
//                                if (pub_name.contains(dataItem.getNewsBy())) {
//                                    updatedList.add(dataItem);
//                                }
//                            }
//
//
//                        }
//
//                        if (updatedList != null) {
//                            if (updatedList.size() > 0) {
//                                NewsByPub_Adapter newsByPub_adapter = new NewsByPub_Adapter
//                                        (context, updatedList);
//
//                                bottomNews_recyclerView.setAdapter(newsByPub_adapter);
//
//                            }
//                        }
//
//
//                    } else {
//                        Bottom_News_Adapter bottomNews_adapter = new Bottom_News_Adapter
//                                (context,
//                                        arraylist_bottom_news);
//                        bottomNews_recyclerView.setAdapter(bottomNews_adapter);
//                    }
//                } else {
//                    Bottom_News_Adapter bottomNews_adapter = new Bottom_News_Adapter
//                            (context,
//                                    arraylist_bottom_news);
//                    bottomNews_recyclerView.setAdapter(bottomNews_adapter);
//                }
//
//            } else {
//                Bottom_News_Adapter bottomNews_adapter = new Bottom_News_Adapter(context,
//                        arraylist_bottom_news);
//                bottomNews_recyclerView.setAdapter(bottomNews_adapter);
//            }
//        } catch (Exception e) {
//            Bottom_News_Adapter bottomNews_adapter = new Bottom_News_Adapter(context,
//                    arraylist_bottom_news);
//            bottomNews_recyclerView.setAdapter(bottomNews_adapter);
//            e.printStackTrace();
//        }
//    }
//
//    private void init_Entertainment_Views() {
//        Entertainment_Adapter enter_adapter = new Entertainment_Adapter(context,
//                arraylist_entertainment);
//        entertain_recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
//                LinearLayoutManager.HORIZONTAL, false);
//        entertain_recyclerView.setLayoutManager(layoutManager);
//        entertain_recyclerView.setAdapter(enter_adapter);
//        /* entertain_recyclerView.addOnScrollListener(new CustomScrollListener());*/
//
//
//        entertain_recyclerView.setOnTouchListener(new InterceptTouchListener(new
//                                                                                     InterceptTouchListener.OnItemClickListener() {
//                                                                                         @Override
//                                                                                         public void
//                                                                                         onItemClick(View v, int position) {
//                                                                                             final Entertainment_pojo entertainment_pojo = arraylist_entertainment.get(position);
//                                                                                             MyApplication.getInstance().trackEvent("Display Banner 1", entertainment_pojo.getRedirect_link(), entertainment_pojo.getTitle());
//
//                                                                                             MyUtility.handleItemClick(context, entertainment_pojo.getPackage_name(),
//                                                                                                     entertainment_pojo.getRedirect_link(), entertainment_pojo.getBanner_thumb_image()
//                                                                                                     , "Display Banner 1", entertainment_pojo.getOpen_with(), entertainment_pojo.getTitle());
//                                                                                         }
//                                                                                     }));
//    }
//
//    private void init_BottomAdv_Views() {
//
//        Bottom_Adv_Adapter bottomAdv_adapter = new Bottom_Adv_Adapter(context,
//                arraylist_b_a_banner);
//        bottomAdv_recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
//                LinearLayoutManager.HORIZONTAL, false);
//        bottomAdv_recyclerView.setLayoutManager(layoutManager);
//        //recyclerView.setNestedScrollingEnabled(false);
//        bottomAdv_recyclerView.setAdapter(bottomAdv_adapter);
//
//        bottomAdv_recyclerView.setOnTouchListener(new InterceptTouchListener(new
//                                                                                     InterceptTouchListener.OnItemClickListener() {
//                                                                                         @Override
//                                                                                         public void
//                                                                                         onItemClick(View v, int position) {
//                                                                                             Bottom_adv_Pojo bottomAdv_pojo = arraylist_b_a_banner.get(position);
//                                                                                             MyApplication.getInstance().trackEvent("Display Banner 2", bottomAdv_pojo.getRedirect_link(), bottomAdv_pojo.getTitle());
//
//                                                                                             MyUtility.handleItemClick(context, bottomAdv_pojo.getPackage_name(),
//                                                                                                     bottomAdv_pojo.getRedirect_link(), bottomAdv_pojo.getBanner_image(), "Display Banner 2", bottomAdv_pojo.getOpen_with(), bottomAdv_pojo.getTitle());
//                                                                                         }
//                                                                                     }));
//
//    }
//
//    private void init_Games_Views() {
//
//        GamesAdapter games_adapter = new GamesAdapter(context, arraylist_games);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
//                LinearLayoutManager.HORIZONTAL, false);
//        games_recyclerView.setLayoutManager(layoutManager);
//        games_recyclerView.setNestedScrollingEnabled(false);
//        games_recyclerView.setAdapter(games_adapter);
//
//        games_recyclerView.setOnTouchListener(new InterceptTouchListener(new
//                                                                                 InterceptTouchListener.OnItemClickListener() {
//                                                                                     @Override
//                                                                                     public void
//                                                                                     onItemClick
//                                                                                             (View v, int
//                                                                                                     position) {
//                                                                                         final GamesPojo games_pojo = arraylist_games.get(position);
//                                                                                         MyApplication.getInstance().trackEvent("Games",
//                                                                                                 games_pojo.getRedirect_link(), games_pojo.getTitle());
//
//                                                                                         MyUtility.handleItemClick(context, games_pojo.getPackage_name(), games_pojo
//                                                                                                         .getRedirect_link(), games_pojo.getBanner_thumb_image(), "Games",
//                                                                                                 games_pojo.getOpen_with(), games_pojo.getTitle());
//                                                                                     }
//                                                                                 }));
//
//    }
//
//    private void init_external_game_view() {
//
//        External_Game_Adapter external_Game_Adapter = new External_Game_Adapter(context,
//                arraylist_Flashgames);
//        external_games_recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
//                LinearLayoutManager.HORIZONTAL, false);
//        external_games_recyclerView.setLayoutManager(layoutManager);
//        external_games_recyclerView.setAdapter(external_Game_Adapter);
//
//        external_games_recyclerView.setOnTouchListener(new InterceptTouchListener(new
//                                                                                          InterceptTouchListener.OnItemClickListener() {
//                                                                                              @Override
//                                                                                              public void onItemClick(View v, int position) {
//                                                                                                  External_game_pojo externalGame_Pojo = arraylist_Flashgames.get(position);
//                                                                                                  MyApplication.getInstance().trackEvent("Html Games",
//                                                                                                          externalGame_Pojo.getRedirect_link(), externalGame_Pojo.getTitle());
//                                                                                                  MyUtility.handleItemClick(context, "", externalGame_Pojo.getRedirect_link()
//                                                                                                          , externalGame_Pojo.getBanner_thumb_image(), "HTML Game",
//                                                                                                          externalGame_Pojo.getOpen_with(), externalGame_Pojo.getTitle());
//                                                                                              }
//                                                                                          }));
//
//    }
//
//    private void init_Wallpaper_Views() {
//        final Wallpaper_Adapter wallpaper_adapter = new Wallpaper_Adapter(context,
//                arraylist_wallpaper);
//
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
//                LinearLayoutManager.HORIZONTAL, false);
//        wallpaper_recyclerView.setLayoutManager(layoutManager);
//        wallpaper_recyclerView.setAdapter(wallpaper_adapter);
//
//        wallpaper_recyclerView.setOnTouchListener(new InterceptTouchListener(new
//                                                                                     InterceptTouchListener.OnItemClickListener() {
//                                                                                         @Override
//                                                                                         public void
//                                                                                         onItemClick(View v, int position) {
//                                                                                             final Wallpaper_Pojo wallpaper_pojo = arraylist_wallpaper.get(position);
//                                                                                             MyApplication.getInstance().trackEvent("Wallpaper",
//                                                                                                     wallpaper_pojo.getRedirect_link(), wallpaper_pojo.getTitle());
//                                                                                             MyUtility.saveTracksInDB(context, arraylist_wallpaper.get(position)
//                                                                                                             .getBanner_image(), arraylist_wallpaper.get(position).getRedirect_link(),
//                                                                                                     "Wallpaper", "0", arraylist_wallpaper.get(position).getTitle(), "");
//
//                                                                                             if
//                                                                                             (MyUtility.isConnectedToInternet()) {
//
//
//                                                                                                 if (!TextUtils.isEmpty(wallpaper_pojo.getWallpaper_type())) {
//                                                                                                     if ((wallpaper_pojo.getWallpaper_type()).equalsIgnoreCase("redirect")) {
//                                                                                                         MyUtility.handleItemClick(context, wallpaper_pojo
//                                                                                                                         .getPackage_name(), wallpaper_pojo.getRedirect_link(), wallpaper_pojo
//                                                                                                                         .getRedirect_link(), "wallpaper", wallpaper_pojo.getOpen_with(),
//                                                                                                                 getResources().getString(R.string.my_app_name));
//                                                                                                     } else {
//
//                                                                                                         Intent intent = new Intent(context, WallpaperActivity.class);
//                                                                                                         intent.putExtra("positionkey", position);
//                                                                                                         intent.putExtra("bundle", arraylist_wallpaper);
//                                                                                                         context.startActivity(intent);
//                                                                                                     }
//                                                                                                 } else {
//
//                                                                                                     Intent intent = new Intent(context, WallpaperActivity.class);
//                                                                                                     intent.putExtra("positionkey", position);
//                                                                                                     intent.putExtra("bundle", arraylist_wallpaper);
//                                                                                                     context.startActivity(intent);
//                                                                                                 }
//
//
//                                                                                             } else {
//                                                                                                 MyUtility.NoInternet_Msg(context);
//                                                                                             }
//                                                                                         }
//                                                                                     }));
//
//    }
//
//
//    private void hitMobinViewAPI() {
//
//        String getLastrktViewShow = MyApplication.app_sharedPreferences.getString
//                ("last_rktViewShow", "");
//        String view_duration = MyApplication.app_sharedPreferences.getString("view_duration", "");
//
//        if (!TextUtils.isEmpty(view_duration)) {
//            try {
//                if (Integer.parseInt(view_duration) > 500) {
//                    MyApplication.app_editor.putString("view_duration", "2").apply();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//
//        if (TextUtils.isEmpty(getLastrktViewShow)) {
//            MyApplication.app_editor.putString("last_rktViewShow", MyUtility.getCurrentTime())
//                    .apply();
//            new hitMobinview_Async().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        } else if (!TextUtils.isEmpty(getLastrktViewShow) && TextUtils.isEmpty(view_duration)) {
//            MyApplication.app_editor.putString("last_rktViewShow", MyUtility.getCurrentTime())
//                    .apply();
//            new hitMobinview_Async().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//        } else if (!TextUtils.isEmpty(view_duration)) {
//            if ((MyUtility.getTimeDifference_nubit("rktView")) > (Long.parseLong(view_duration))) {
//                MyApplication.app_editor.putString("last_rktViewShow", MyUtility.getCurrentTime())
//                        .apply();
//                new hitMobinview_Async().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//
//            }
//        }
//
//
//    }
//
//
//    private void init_MiddleNews_Views() {
//        final Middle_News_Adapter middle_news_adapter = new Middle_News_Adapter(context,
//                arraylist_middle_news);
//
//        middleNews_recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
//                LinearLayoutManager.HORIZONTAL, false);
//        middleNews_recyclerView.setLayoutManager(layoutManager);
//        //recyclerView.setNestedScrollingEnabled(false);
//        middleNews_recyclerView.setAdapter(middle_news_adapter);
//
//        middleNews_recyclerView.setOnTouchListener(new InterceptTouchListener(new
//                                                                                      InterceptTouchListener.OnItemClickListener() {
//                                                                                          @Override
//                                                                                          public void
//                                                                                          onItemClick(View v, int position) {
//
//                                                                                              Middle_News_Pojo middleNewsPojo = arraylist_middle_news.get(position);
//                                                                                              MyApplication.getInstance().trackEvent("Middle News", middleNewsPojo.getRedirect_link(), middleNewsPojo.getTitle());
//                                                                                              MyUtility.handleItemClick(context, middleNewsPojo.getPackage_name(), middleNewsPojo.getRedirect_link(), middleNewsPojo.getImage(),
//                                                                                                      "Middle News", middleNewsPojo.getOpen_with(), middleNewsPojo.getTitle());
//                                                                                          }
//                                                                                      }));
//
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        int i = v.getId();
//        if (i == R.id.btn_refresh) {
//            pro_nointernet.setVisibility(View.VISIBLE);
//            if (MyUtility.isConnectedToInternet()) {
//                hitVersion();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        pro_nointernet.setVisibility(View.GONE);
//                        nointernet_parent.setVisibility(View.GONE);
//                        layout_container_nubitview.setVisibility(View.VISIBLE);
//                    }
//                }, 4000);
//
//
//            } else {
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        pro_nointernet.setVisibility(View.GONE);
//                        MyUtility.NoInternet_Msg(context);
//                    }
//                }, 2000);
//
//
//            }
//
//        } else if (i == R.id.nav_icon) {
//            drawerLayout.openDrawer(Gravity.LEFT);
//        } else if (i == R.id.topapps_news_ref) {
//
//            MyUtility.rotateRefreshButton_Anim(context, topapps_news_ref);
//            //   MyUtility.rotateRecyclerView(context,topapps_recyclerView);
//            MyUtility.scroll_app_RecyclerView(context, topapps_recyclerView, 3);
//
//
//        } else if (i == R.id.img_games_ref) {
//
//            MyUtility.rotateRefreshButton_Anim(context, img_games_ref);
//            MyUtility.scroll_game_RecyclerView(context, games_recyclerView, 3);
//
//        } else if (i == R.id.img_externalGames_ref) {
//
//            MyUtility.rotateRefreshButton_Anim(context, img_externalGames_ref);
//            MyUtility.scroll_externalgame_RecyclerView(context,
//                    external_games_recyclerView, 3);
//        } else if (i == R.id.img_wallpaper_ref) {
//
//            MyUtility.rotateRefreshButton_Anim(context, img_wallpaper_ref);
//            MyUtility.scroll_wallpaper_RecyclerView(context, wallpaper_recyclerView, 2);
//
//
//        } else if (i == R.id.btn_add_pub) {
//
//
//            if (arraylist_newspub != null) {
//                if (arraylist_newspub.size() > 0) {
//                    show_publisher_popup(context, arraylist_newspub);
//                } else {
//                    Toast.makeText(context, "Opps, Something went wrong, Pls try later",
//                            Toast.LENGTH_LONG).show();
//                }
//
//            } else {
//                Toast.makeText(context, "Opps, Something went wrong, Pls try later",
//                        Toast.LENGTH_LONG).show();
//            }
//
//
//        }
//    }
//
//    @Override
//    public void ActionOnLanguageChange() {
//      /*  MyApplication.app_sharedPreferences.getString("key_modifiedDat","");
//        MyApplication.app_editor.remove("key_modifiedDat");*/
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            new HitGetData_forLanguage().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//
//        } else {
//            new HitGetData_forLanguage().execute();
//        }
//    }
//
//    @Override
//    public void onVideoCatSelection() {
//        show_videoCat_popup(context, arraylist_videocat);
//    }
//
//    @Override
//    public void updateScrollCallBack(boolean update, String callFrom, int position) {
//
//
//        if (update == true) {
//            if (shouldScroll == true) {
//                start_Scroll();
//                scrollStatus = "home";
//            }
//
//        } else {
//            /*  shouldScroll=false;*/
//            /*     topBanner_indicator.setVisibility(View.VISIBLE);*/
//            stop_Scroll(callFrom, position);
//
//            if (callFrom.equalsIgnoreCase("loading")) {
//                /*       topBanner_indicator.setVisibility(View.GONE);*/
//                scrollStatus = "loading";
//
//
//            } else {
//                scrollStatus = "home";
//            }
//
//
//        }
//    }
//
//    private void start_Scroll() {
//
//        home_topBanner_Pager.startAutoScroll();
//
//
//    }
//
//    private void stop_Scroll(final String callFrom, final int position) {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                home_topBanner_Pager.stopAutoScroll();
//
//
//                if (callFrom.equalsIgnoreCase("bottom_news_play")) {
//                    home_topBanner_Pager.setCurrentItem(0);
//                } else if (callFrom.equalsIgnoreCase("adapter")) {
//                    home_topBanner_Pager.setCurrentItem(position);
//                }
//
//
//            }
//        }, 1000);
//
//    }
//
//
// /*   @Override
//    public void updatePlayerCallBack(boolean z, String playerState) {
//
//
//        if (playerState.equalsIgnoreCase("loading")) {
//
//
//        } else if (playerState.equalsIgnoreCase("completed")) {
//            Log.d("playerState", "completed");
//
//            if (playerState.equalsIgnoreCase("completed")) {
//                if (shouldScroll == true) {
//                    start_Scroll();
//                }
//
//            }
//        }
//
//    }*/
//
//
//    class CheckServerForNewData_asyn extends AsyncTask<Void, Void, String> {
//
//
//        String refreshMyView = "";
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            isAsyncTaskRunning = true;
//            /*  RefreshDataCallBack.getInstance().notifyOnRefreshStart();*/
//
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//
//            String str_last_modificationDate = "";
//            JSONObject save_json_jsonobject = null;
//
//            String downloadNewData = "";
//
//
//            try {
//
//                String isNewDataAvailable = MyUtility.hitGetVersionAPI(context,
//                        MyUtility.getversionAPI);
//
//
//                if (!TextUtils.isEmpty(isNewDataAvailable)) {
//                    JSONObject getversionAPI_jsonobject = null;
//                    try {
//                        getversionAPI_jsonobject = new JSONObject(isNewDataAvailable);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    String str_checkNewData_Json = "";
//                    try {
//                        str_checkNewData_Json = getversionAPI_jsonobject.getString("data");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    JSONObject jsonObject_getDat = null;
//                    try {
//                        jsonObject_getDat = new JSONObject(str_checkNewData_Json);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    String isFullScreenBanner = "";
//                    try {
//                        isFullScreenBanner = jsonObject_getDat.getString
//                                ("full_screen_banner");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//
//                    try {
//                        str_last_modificationDate = jsonObject_getDat.getString("get_data_date");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    String get_YouTube_Key = "";
//                    try {
//                        get_YouTube_Key = jsonObject_getDat.getString("youtube_key");
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    String get_admob_app_id = "";
//                    try {
//                        get_admob_app_id = jsonObject_getDat.getString("admob_app_id");
//
//                        if (get_admob_app_id != null && !TextUtils.isEmpty(get_admob_app_id)) {
//                            MyApplication.app_editor.putString("admob_app_id", get_admob_app_id)
//                                    .apply();
//                        }
//
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//
//                    JSONArray get_languageList;
//                    try {
//                        get_languageList = jsonObject_getDat.getJSONArray("langauge_list");
//                        if (get_languageList != null) {
//                            if (!TextUtils.isEmpty(get_languageList.toString())) {
//                                MyApplication.app_editor.putString("language_array",
//                                        get_languageList.toString()).apply();
//                            }
//                        }
//                        // Log.d("get_languageList",get_languageList.toString());
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//
//                    String ga_Key = "";
//                    try {
//                        ga_Key = jsonObject_getDat.getString("ga_key");
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    String advbanner_delay = "";
//                    String advbanner_redirectLink = "";
//                    String advbanner_banner_image = "";
//                    String advbanner_ad_unit_id = "";
//                    String advbanner_open_with = "";
//                    String advbanner_title = "";
//                    String webview_url = "";
//                    String splash_time = "";
//
//                    try {
//
//                        if (isFullScreenBanner.equalsIgnoreCase("0") ||
//                                isFullScreenBanner.equalsIgnoreCase("1")) {
//
//
//                            try {
//                                advbanner_redirectLink = (jsonObject_getDat.getJSONArray
//                                        ("advbanner").getJSONObject(0)).getString("redirect_link");
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                            try {
//                                webview_url = (jsonObject_getDat.getJSONArray
//                                        ("advbanner").getJSONObject(0)).getString("webview_url");
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            try {
//                                splash_time = (jsonObject_getDat.getJSONArray
//                                        ("advbanner").getJSONObject(0)).getString("splash_time");
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//
//                            try {
//                                advbanner_delay = (jsonObject_getDat.getJSONArray
//                                        ("advbanner").getJSONObject(0)).getString("delay");
//
//                                MyApplication.app_editor.putString("advbanner_delay",
//                                        advbanner_delay).apply();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//
//                            try {
//                                advbanner_banner_image = (jsonObject_getDat.getJSONArray
//                                        ("advbanner").getJSONObject(0)).getString("banner_image");
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//
//                            try {
//                                advbanner_ad_unit_id = (jsonObject_getDat.getJSONArray
//                                        ("advbanner").getJSONObject(0)).getString("ad_unit_id");
//
//                                MyApplication.app_editor.putString("advbanner_ad_unit_id",
//                                        advbanner_ad_unit_id).apply();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                            try {
//                                advbanner_open_with = (jsonObject_getDat.getJSONArray
//                                        ("advbanner").getJSONObject(0)).getString("open_with");
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            try {
//                                advbanner_title = (jsonObject_getDat.getJSONArray
//                                        ("advbanner").getJSONObject(0)).getString("title");
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//
//                    String str_get_lastUpdateDate = MyApplication.app_sharedPreferences.getString
//                            ("key_modifiedDat", "");
//
//                    if (TextUtils.isEmpty(str_get_lastUpdateDate)) {
//
//                        Log.d("NubitStatus", "First Time USer");
//
//
//                        downloadNewData = MyUtility.hitGetDataAPI(context,
//                                MyUtility.getDataAPI);
//                        if (!TextUtils.isEmpty(downloadNewData)) {
//                            try {
//                                save_json_jsonobject = new JSONObject(downloadNewData);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                            //  Log.d("downloadedJson", String.valueOf(save_json_jsonobject));
//                            if (save_json_jsonobject != null) {
//                                if (!TextUtils.isEmpty(save_json_jsonobject.toString())) {
//                                    MyApplication.app_editor.putString("save_app_Json", String
//                                            .valueOf(save_json_jsonobject)).apply();
//                                }
//                            }
//                            if (!TextUtils.isEmpty(str_last_modificationDate)) {
//                                MyApplication.app_editor.putString("key_modifiedDat",
//                                        str_last_modificationDate).apply();
//                            }
//                            if (!TextUtils.isEmpty(get_YouTube_Key)) {
//                                MyApplication.app_editor.putString("youtube_key",
//                                        get_YouTube_Key).apply();
//                            }
//                            if (!TextUtils.isEmpty(ga_Key)) {
//                                MyApplication.app_editor.putString("ga_Key", ga_Key).apply();
//                            }
//
//                            MyApplication.app_editor.putBoolean("update_UI", true).apply();
//                            refreshMyView = "yes";
//
//
//                        } else {
//                            refreshMyView = "noresponse";
//                            Log.d("NubitStatus", "getData API not working, Pls check");
//                        }
//
//
//                    } else if (str_last_modificationDate.equalsIgnoreCase(str_get_lastUpdateDate)) {
//
//                        MyApplication.app_editor.putBoolean("update_UI", false).apply();
//                        Log.d("NubitStatus", "Repeated User");
//
//
//                        if (MyUtility.isUpdateRequired(context,
//                                isFullScreenBanner)) {
//                            if (!TextUtils.isEmpty(isFullScreenBanner)) {
//
//
//                                show_fullScreenBanner(isFullScreenBanner, advbanner_delay,
//                                        advbanner_banner_image, advbanner_ad_unit_id,
//                                        advbanner_redirectLink, advbanner_open_with,
//                                        advbanner_title, webview_url, splash_time);
//
//                            }
//                        }
//
//
//                    } else {
//
//                        if (MyUtility.isUpdateRequired(context,
//                                isFullScreenBanner)) {
//
//
//                            if (!TextUtils.isEmpty(isFullScreenBanner) &&
//                                    isFullScreenBanner.equalsIgnoreCase("0")) {
//                                show_fullScreenBanner(isFullScreenBanner, advbanner_delay,
//                                        advbanner_banner_image, advbanner_ad_unit_id,
//                                        advbanner_redirectLink, advbanner_open_with,
//                                        advbanner_title, webview_url, splash_time);
//                                hitMobinViewAPI();
//                                downloadNewData = MyUtility.hitGetDataAPI(context, MyUtility
//                                        .getDataAPI);
//                                if (!TextUtils.isEmpty(downloadNewData)) {
//
//                                    try {
//                                        save_json_jsonobject = new JSONObject(downloadNewData);
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                    if (save_json_jsonobject != null) {
//                                        if (!TextUtils.isEmpty(save_json_jsonobject.toString())) {
//                                            MyApplication.app_editor.putString("save_app_Json",
//                                                    String.valueOf(save_json_jsonobject)).apply();
//                                        }
//
//                                    }
//
//                                    if (!TextUtils.isEmpty(str_last_modificationDate)) {
//                                        MyApplication.app_editor.putString("key_modifiedDat",
//                                                str_last_modificationDate).apply();
//                                    }
//                                    if (!TextUtils.isEmpty(get_YouTube_Key)) {
//                                        MyApplication.app_editor.putString("youtube_key",
//                                                get_YouTube_Key).apply();
//                                    }
//                                    if (!TextUtils.isEmpty(ga_Key)) {
//                                        MyApplication.app_editor.putString("ga_Key", ga_Key)
//                                                .apply();
//                                    }
//
//                                    MyApplication.app_editor.putBoolean("update_UI", true).apply();
//                                    MyApplication.app_editor.apply();
//                                    refreshMyView = "yes";
//                                    Log.d("NubitStatus", "New Data Found");
//
//                                } else {
//                                    refreshMyView = "noresponse";
//                                    Log.d("nubit_Problem", "getData API not working, Pls check");
//
//                                }
//                                Log.d("nubit_Problem", "New application_version found, Pls check");
//                            }
//                            if (!TextUtils.isEmpty(isFullScreenBanner) &&
//                                    isFullScreenBanner.equalsIgnoreCase("1")) {
//                                show_fullScreenBanner(isFullScreenBanner, advbanner_delay,
//                                        advbanner_banner_image, advbanner_ad_unit_id,
//                                        advbanner_redirectLink, advbanner_open_with,
//                                        advbanner_title, webview_url, splash_time);
//                            }
//
//
//                        } else {
//                            hitMobinViewAPI();
//                            downloadNewData = MyUtility.hitGetDataAPI(context,
//                                    MyUtility.getDataAPI);
//                            if (!TextUtils.isEmpty(downloadNewData)) {
//
//                                try {
//                                    save_json_jsonobject = new JSONObject(downloadNewData);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                                if (save_json_jsonobject != null) {
//                                    if (!TextUtils.isEmpty(save_json_jsonobject.toString())) {
//                                        MyApplication.app_editor.putString("save_app_Json",
//                                                String.valueOf(save_json_jsonobject)).apply();
//                                    }
//
//                                }
//
//                                if (!TextUtils.isEmpty(str_last_modificationDate)) {
//                                    MyApplication.app_editor.putString("key_modifiedDat",
//                                            str_last_modificationDate).apply();
//                                }
//                                if (!TextUtils.isEmpty(get_YouTube_Key)) {
//                                    MyApplication.app_editor.putString("youtube_key",
//                                            get_YouTube_Key).apply();
//                                }
//                                if (!TextUtils.isEmpty(ga_Key)) {
//                                    MyApplication.app_editor.putString("ga_Key", ga_Key).apply();
//                                }
//
//                                MyApplication.app_editor.putBoolean("update_UI", true).apply();
//                                refreshMyView = "yes";
//                                Log.d("NubitStatus", "New Data Found");
//
//                            } else {
//                                refreshMyView = "noresponse";
//                                Log.d("nubit_Problem", "getData API not working, Pls check");
//
//                            }
//                        }
//
//                    }
//
//                } else {
//
//                    refreshMyView = "noresponse";
//                    Log.d("nubit_Problem", "getVersionList API not working, Pls check");
//
//                }
//
//                return refreshMyView;
//            } catch (Exception e) {
//                return "";
//            }
//
//
//        }
//
//        @Override
//        public void onPostExecute(String refreshMyView) {
//
//
//            if (!TextUtils.isEmpty(refreshMyView) && refreshMyView.equalsIgnoreCase("noresponse")) {
//
//                /*This block execute when we getting no response from getVersion API...It can be by
//                nointernet or API problem....Lets check in below conditions if it is new user or
//                have old saved data to showcase*/
//
//
//                if (TextUtils.isEmpty(MyApplication.app_sharedPreferences.getString
//                        ("save_app_Json", ""))) {
//                    /*Clear saved json data and show the nointernet UI to handle the nointernet
//                    connection, Because this is new user and dont have any previously saved data*/
//                    MyApplication.app_editor.clear().apply();
//                    layout_container_nubitview.setVisibility(View.GONE);
//                    nointernet_parent.setVisibility(View.VISIBLE);
//
//                } else {
//                    /*Might be getversion request broken or no internet, but we have saved data
//                    to showcase for now*/
//
//                    layout_container_nubitview.setVisibility(View.VISIBLE);
//                    nointernet_parent.setVisibility(View.GONE);
//                    parseData();
//                    /* shouldViewRefresh("no");*/
//
//                }
//
//            } else if (TextUtils.isEmpty(refreshMyView) && !TextUtils.isEmpty(MyApplication
//                    .app_sharedPreferences.getString
//                            ("save_app_Json", ""))) {
//
//                /*This block execute when we dont find new data to refresh, but we have old data
//                to show case, It can be case of repeated user*/
//
//                /*shouldViewRefresh("no");*/
//
//
//            } else if (!TextUtils.isEmpty(refreshMyView) && refreshMyView.equalsIgnoreCase
//                    ("yes")) {
//                /* shouldViewRefresh(refreshMyView);*/
//                RefreshDataCallBack.getInstance().notifyOnRefreshStart();
//                parseData();
//
//            }
//
//            isAsyncTaskRunning = false;
//        }
//    }
//
//    public void setUpNavigationViewData() {
//
//
//        final Menu menu = navigationView.getMenu();
//
//        if (menu != null) {
//            menu.clear();
//        }
//
//
//        try {
//            if (response.getData().getDrawer_item() == null || response.getData().getDrawer_item()
//                    .size() <= 0) {
//                return;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        List<NavigationPojo> navigationPojoList = null;
//        try {
//            navigationPojoList = response.getData().getDrawer_item();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        try {
//            for (int nav_items = 0; nav_items < navigationPojoList.size(); nav_items++) {
//
//                NavigationPojo get_position = navigationPojoList.get(nav_items);
//
//
//                if (!TextUtils.isEmpty(get_position.getDrawer_item_name())) {
//                    menu.add(0, nav_items, 0, get_position.getDrawer_item_name());
//                }
//
//                if (!TextUtils.isEmpty(get_position.getDrawer_icon())) {
//                    final int finalNav_items = nav_items;
//                    Glide.with(MyApplication.ctx).load(get_position.getDrawer_icon()).asBitmap()
//                            .into
//                                    (new
//                                             SimpleTarget<Bitmap>() {
//                                                 int z = finalNav_items;
//
//                                                 @Override
//                                                 public void onResourceReady(Bitmap resource,
//                                                                             GlideAnimation
//                                                                                     glideAnimation) {
//
//                                                     menu.getItem(z).setIcon(new GlideBitmapDrawable
//                                                             (getResources(), resource));
//                                                 }
//                                             });
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    public void onNav_ItemSelected() {
//
//
//        navigationView.setNavigationItemSelectedListener(new NavigationView
//                .OnNavigationItemSelectedListener() {
//            @Override
//
//            public boolean onNavigationItemSelected(MenuItem menuItem) {
//
//                int id = menuItem.getItemId();
//
//
//                NavigationPojo itemId = arraylist_nav.get(id);
//
//
//                String getNavURL = itemId.getRedirect_url();
//
//                MyApplication.getInstance().trackEvent("Navigation Menu", getNavURL, itemId
//                        .getDrawer_item_name());
//
//
//                if (!TextUtils.isEmpty(getNavURL) && getNavURL.equalsIgnoreCase("http://www" +
//                        ".nubit" +
//                        ".profile")) {
//                    Intent sendToProfile = new Intent(context, ProfileActivity.class);
//                    context.startActivity(sendToProfile);
//                    drawerLayout.closeDrawers();
//                } else if (!TextUtils.isEmpty(getNavURL) && getNavURL.equalsIgnoreCase
//                        ("http://www.nubit.language")) {
//                    Intent sendToLanguage = new Intent(context, Language_Set_Activity
//                            .class);
//                    context.startActivity(sendToLanguage);
//                    drawerLayout.closeDrawers();
//                } else if (!TextUtils.isEmpty(getNavURL) && getNavURL.equalsIgnoreCase
//                        ("http://www.nubit.share")) {
//                    MyApplication.getInstance().trackEvent("App Share", "Nubit Shared",
//                            MyUtility.getDeviceID(context));
//                    MyUtility.My_share(context, "", "");
//                    drawerLayout.closeDrawers();
//                } else {
//                    if (!TextUtils.isEmpty(getNavURL)) {
//                        // MyUtility.handleNavClick(RobiLauncherActivity.this, getNavURL);
//
//                        MyUtility.handleItemClick(context, "", getNavURL, getNavURL,
//                                "Navigation Menu", itemId.getOpen_with(), itemId
//                                        .getDrawer_item_name());
//
//                        drawerLayout.closeDrawers();
//                    }
//
//
//                }
//
//
//                return true;
//            }
//        });
//
//
//        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle((Activity) context,
//                drawerLayout, R.string.drawer_open, R.string.drawer_close) {
//
//            @Override
//            public void onDrawerClosed(View v) {
//                super.onDrawerClosed(v);
//                MyApplication.app_editor.putString("drawer_status", "closed").apply();
//                MyApplication.getInstance().trackEvent("Navigation Menu", "Navigation Menu " +
//                                "Closed",
//                        "Navigation Menu");
//            }
//
//            @Override
//            public void onDrawerOpened(View v) {
//                super.onDrawerOpened(v);
//                MyApplication.app_editor.putString("drawer_status", "open").apply();
//                MyApplication.getInstance().trackEvent("Navigation Menu", "Navigation Menu " +
//                                "Opened",
//                        "Navigation Menu");
//                layoutmovetop.setVisibility(View.GONE);
//
//                setProfile_Nav();
//            }
//        };
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.syncState();
//    }
//
//    public void setProfile_Nav() {
//
//        View header = navigationView.getHeaderView(0);
//        TextView profile_name_nav = header.findViewById(R.id.profile_name_nav);
//        ImageView profile_nav = header.findViewById(R.id.profile_pic_nav);
//
//        String getProfile = MyApplication.app_sharedPreferences.getString("userProfileIcon",
//                "");
//        String getName = MyApplication.app_sharedPreferences.getString("userProfileName", "");
//
//        if (!TextUtils.isEmpty(getProfile)) {
//            Glide.with(MyApplication.ctx).load(getProfile).asBitmap().thumbnail(0.5f)
//                    .placeholder(R.drawable.placeholder_apps)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder_apps)
//                    .into(profile_nav);
//        }
//        if (!TextUtils.isEmpty(getName)) {
//            profile_name_nav.setText(getName);
//        }
//
//        profile_nav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.closeDrawers();
//                Intent sentToProfile = new Intent(context, ProfileActivity.class);
//                context.startActivity(sentToProfile);
//            }
//        });
//    }
//
//    private void hitGetProfile() {
//        String get_ProfileStatus = MyApplication.app_sharedPreferences.getString
//                ("haveProfile", "");
//        if (TextUtils.isEmpty(get_ProfileStatus)) {
//
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                new GetProfileData().executeOnExecutor(AsyncTask
//                        .THREAD_POOL_EXECUTOR);
//            } else {
//                new GetProfileData().execute();
//            }
//            MyApplication.app_editor.putString("haveProfile", "haveProfile")
//                    .apply();
//
//
//        }
//    }
//
//
//    public void performSearch() {
//
//
//        if (MyUtility.isConnectedToInternet()) {
//            String get_search_text = edt_search.getText().toString();
//            String get_SearchRedirectionURL = MyApplication.app_sharedPreferences.getString
//                    ("search_redirection", null);
//
//            if (get_SearchRedirectionURL != null || !get_SearchRedirectionURL.matches("")) {
//                if (get_search_text == "" || get_search_text.equals("")) {
//                    Toast.makeText(context, "Please enter something to search!", Toast
//                            .LENGTH_SHORT).show();
//                } else {
//                    String get_redirectedURL = get_SearchRedirectionURL + get_search_text;
//                    edt_search.setText("");
//                    edt_search.clearFocus();
//                    MyUtility.RedirectMe(context, get_redirectedURL, "0", "Nubit Search");
//
//
//                    MyApplication.getInstance().trackEvent("Search",
//                            get_redirectedURL, get_search_text);
//                    MyUtility.saveTracksInDB(context, get_search_text, get_redirectedURL,
//                            "Search", "1", get_search_text, "");
//
//                }
//
//            } else {
//                try {
//
//
//                    if (get_search_text == "" || get_search_text.equals("")) {
//                        Toast.makeText(context, "Please enter something to search!", Toast
//                                .LENGTH_SHORT).show();
//                    } else {
//                        edt_search.setText("");
//                        edt_search.clearFocus();
//                        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
//                        intent.putExtra(SearchManager.QUERY, get_search_text);
//                        context.startActivity(intent);
//
//                    }
//
//                } catch (Exception e) {
//                    // TODO: handle exception
//                }
//            }
//        } else {
//            MyUtility.NoInternet_Msg(context);
//        }
//
//
//    }
//
//
//    class HitGetData_forLanguage extends AsyncTask<Void, Void, String> {
//
//
//        String refreshMyFragment;
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            progress_home.setVisibility(View.VISIBLE);
//
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//
//
//            JSONObject save_json_jsonobject = null;
//
//            String downloadNewData = "";
//
//
//            downloadNewData = MyUtility.hitGetDataAPI(context, MyUtility.getDataAPI);
//            if (!TextUtils.isEmpty(downloadNewData)) {
//                try {
//                    save_json_jsonobject = new JSONObject(downloadNewData);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                if (save_json_jsonobject != null) {
//                    if (!TextUtils.isEmpty(save_json_jsonobject.toString())) {
//                        MyApplication.app_editor.putString("save_app_Json", String.valueOf
//                                (save_json_jsonobject));
//                        MyApplication.app_editor.apply();
//                    }
//
//
//                }
//                refreshMyFragment = "yes";
//            } else {
//                refreshMyFragment = "no";
//            }
//
//
//            return refreshMyFragment;
//        }
//
//        @Override
//        public void onPostExecute(String args) {
//
//
//            if (args.equalsIgnoreCase("yes")) {
//                parseData();
//
//            }
//            progress_home.setVisibility(View.GONE);
//        }
//    }
//
//    public void init_Title_view() {
//
//
//        List<TITLES_IN_DIFF_LANG> diff_langList = null;
//        try {
//            diff_langList = response.getData().getTitles_in_diff_lang();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (diff_langList != null && diff_langList.size() > 0) {
//
//            for (TITLES_IN_DIFF_LANG diffLang : diff_langList) {
//                try {
//                    btn_service_title.setText(diffLang.getServices());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//                try {
//                    btn_topapps_title.setText(diffLang.getTop_apps());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    btn_bottom_news_title.setText(diffLang.getNews3());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    title_middle_news.setText(diffLang.getNews2());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    title_recommended_news.setText(diffLang.getNews4());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    btn_games_title.setText(diffLang.getTrending_games());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                try {
//                    btn_wallpaper_title.setText(diffLang.getWallpapers());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//                try {
//                    btn_externalGames_title.setText(diffLang.getFlash_games());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                try {
//                    btn_news_title.setText(diffLang.getTrending_news());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//                try {
//                    MyApplication.app_editor.putString("readFullStory_forTitle", diffLang
//                            .getRead_full_story()).apply();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    MyApplication.app_editor.putString("select_publisher", diffLang
//                            .getSelect_publisher()).commit();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    MyApplication.app_editor.putString("pub_btn", diffLang
//                            .getPub_btn()).commit();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    MyApplication.app_editor.putString("vid_pop_title", diffLang
//                            .getVid_pop_title()).commit();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                try {
//                    if (!TextUtils.isEmpty(diffLang.getSelect_publisher())) {
//                        btn_add_pub.setText(diffLang.getSelect_publisher());
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//
//
//        if (response != null && response.getData().getTitles() != null && response.getData()
//                .getTitles().size() > 0) {
//
//            String get_admob_block = MyApplication.app_sharedPreferences.getString
//                    ("admob_block_rkt", "");
//
//            Titles titles = response.getData().getTitles().get(0);
//
//
//            if (TextUtils.isEmpty(titles.getServices())) {
//                btn_service_title.setVisibility(View.GONE);
//                devider_below_btnService.setVisibility(View.GONE);
//            } else {
//
//                btn_service_title.setVisibility(View.VISIBLE);
//                devider_below_btnService.setVisibility(View.VISIBLE);
//
//            }
//            if (TextUtils.isEmpty(titles.getTaboola_view())) {
//                parent_taboola.setVisibility(View.GONE);
//            } else {
//
//                parent_taboola.setVisibility(View.VISIBLE);
//
//            }
//
//            if (TextUtils.isEmpty(titles.getSearch())) {
//                edt_search.setVisibility(View.GONE);
//            } else {
//                edt_search.setVisibility(View.VISIBLE);
//            }
//
//            if (titles.getServices() == null) {
//                service_layout_parent.setVisibility(View.GONE);
//            } else {
//
//                if (MyUtility.setShowHide_Container(titles.getServices())) {
//                    service_layout_parent.setVisibility(View.GONE);
//                } else {
//                    service_layout_parent.setVisibility(View.VISIBLE);
//                }
//
//
//            }
//
//            if (titles.getAdmob_one() == null) {
//
//                remove_givenView(parent_admob_one);
//
//            } else {
//
//                if (!TextUtils.isEmpty(get_admob_block)) {
//                    if (!get_admob_block.contains("1")) {
//                        parent_admob_one.setVisibility(View.VISIBLE);
//                        hit_admob_function(0, parent_admob_one);
//                    } else {
//                        remove_givenView(parent_admob_one);
//                    }
//                } else {
//                    parent_admob_one.setVisibility(View.VISIBLE);
//                    hit_admob_function(0, parent_admob_one);
//                }
//
//
//            }
//            if (titles.getAdmob_two() == null) {
//
//                remove_givenView(parent_admob_two);
//
//
//            } else {
//
//                if (!TextUtils.isEmpty(get_admob_block)) {
//                    if (!get_admob_block.contains("2")) {
//                        parent_admob_two.setVisibility(View.VISIBLE);
//                        hit_admob_function(1, parent_admob_two);
//                    } else {
//                        remove_givenView(parent_admob_two);
//                    }
//                } else {
//                    parent_admob_two.setVisibility(View.VISIBLE);
//                    hit_admob_function(1, parent_admob_two);
//                }
//
//
//            }
//            if (titles.getAdmob_three() == null) {
//
//                remove_givenView(parent_admob_three);
//
//            } else {
//
//                if (!TextUtils.isEmpty(get_admob_block)) {
//                    if (!get_admob_block.contains("3")) {
//                        parent_admob_three.setVisibility(View.VISIBLE);
//                        hit_admob_function(2, parent_admob_three);
//                    } else {
//                        remove_givenView(parent_admob_three);
//                    }
//                } else {
//                    parent_admob_three.setVisibility(View.VISIBLE);
//                    hit_admob_function(2, parent_admob_three);
//                }
//
//
//            }
//            if (titles.getAdmob_four() == null) {
//
//                remove_givenView(parent_admob_four);
//
//            } else {
//
//                if (!TextUtils.isEmpty(get_admob_block)) {
//                    if (!get_admob_block.contains("4")) {
//                        parent_admob_four.setVisibility(View.VISIBLE);
//                        hit_admob_function(3, parent_admob_four);
//                    } else {
//                        remove_givenView(parent_admob_four);
//                    }
//
//                } else {
//                    parent_admob_four.setVisibility(View.VISIBLE);
//                    hit_admob_function(3, parent_admob_four);
//                }
//
//            }
//
//
//            if (titles.getAdmob_five() == null) {
//
//                remove_givenView(parent_admob_five);
//
//            } else {
//
//                if (!TextUtils.isEmpty(get_admob_block)) {
//                    if (!get_admob_block.contains("5")) {
//                        parent_admob_five.setVisibility(View.VISIBLE);
//                        hit_admob_function(4, parent_admob_five);
//                    } else {
//                        remove_givenView(parent_admob_five);
//                    }
//
//                } else {
//                    parent_admob_five.setVisibility(View.VISIBLE);
//                    hit_admob_function(4, parent_admob_five);
//                }
//
//            }
//
//
//            if (titles.getOther_services() == null) {
//                o_service_layout_parent.setVisibility(View.GONE);
//            } else {
//
//                if (MyUtility.setShowHide_Container(titles.getOther_services())) {
//                    o_service_layout_parent.setVisibility(View.GONE);
//                } else {
//                    o_service_layout_parent.setVisibility(View.VISIBLE);
//                }
//
//            }
//
//            if (titles.getTop_advertisement() == null) {
//                top_banner_parent.setVisibility(View.GONE);
//            } else {
//                top_banner_parent.setVisibility(View.VISIBLE);
//            }
//            if (TextUtils.isEmpty(titles.getNews1())) {
//                btn_news_title.setVisibility(View.GONE);
//                devider_below_new_ref.setVisibility(View.GONE);
//            } else {
//                btn_news_title.setVisibility(View.VISIBLE);
//                devider_below_new_ref.setVisibility(View.VISIBLE);
//            }
//            if (titles.getNews1() == null) {
//                topNewsParent_layout.setVisibility(View.GONE);
//            } else {
//
//                if (MyUtility.setShowHide_Container(titles.getNews1())) {
//                    topNewsParent_layout.setVisibility(View.GONE);
//                } else {
//                    topNewsParent_layout.setVisibility(View.VISIBLE);
//                }
//            }
//            if (titles.getEntertainment() == null) {
//                entertainment_parent.setVisibility(View.GONE);
//            } else {
//                entertainment_parent.setVisibility(View.VISIBLE);
//            }
//            if (TextUtils.isEmpty(titles.getTrending_apps())) {
//                topapps_ref_layout.setVisibility(View.GONE);
//            } else {
//                topapps_ref_layout.setVisibility(View.VISIBLE);
//            }
//            if (titles.getTrending_apps() == null) {
//                topapps_parent.setVisibility(View.GONE);
//            } else {
//                if (MyUtility.setShowHide_Container(titles.getTrending_apps())) {
//                    topapps_parent.setVisibility(View.GONE);
//                } else {
//                    topapps_parent.setVisibility(View.VISIBLE);
//                }
//
//            }
//
//            if (TextUtils.isEmpty(titles.getTrending_games())) {
//                games_ref_layout.setVisibility(View.GONE);
//
//            } else {
//                games_ref_layout.setVisibility(View.VISIBLE);
//            }
//            if (titles.getTrending_games() == null) {
//                games_parent.setVisibility(View.GONE);
//            } else {
//
//                games_parent.setVisibility(View.VISIBLE);
//
//            }
//
//            if (TextUtils.isEmpty(titles.getFlash_games_home())) {
//                externalGames_ref_layout.setVisibility(View.GONE);
//
//            } else {
//                externalGames_ref_layout.setVisibility(View.VISIBLE);
//            }
//            if (titles.getFlash_games_home() == null) {
//                externalGames_parent_layout.setVisibility(View.GONE);
//            } else {
//
//                externalGames_parent_layout.setVisibility(View.VISIBLE);
//            }
//
//
//            if (titles.getBottom_advertisement() == null) {
//                bottom_adv_parent.setVisibility(View.GONE);
//            } else {
//                bottom_adv_parent.setVisibility(View.VISIBLE);
//            }
//            if (TextUtils.isEmpty(titles.getWallpaper())) {
//                wallpaper_ref_layout.setVisibility(View.GONE);
//
//            } else {
//                wallpaper_ref_layout.setVisibility(View.VISIBLE);
//            }
//            if (titles.getWallpaper() == null) {
//                wallpaper_parent.setVisibility(View.GONE);
//            } else {
//
//                wallpaper_parent.setVisibility(View.VISIBLE);
//            }
//
//
//            if (TextUtils.isEmpty(titles.getNews2())) {
//                title_middle_news.setVisibility(View.GONE);
//                devider_title_middle_news.setVisibility(View.GONE);
//            } else {
//                title_middle_news.setVisibility(View.VISIBLE);
//                devider_title_middle_news.setVisibility(View.VISIBLE);
//            }
//            if (titles.getNews2() == null) {
//                middle_news_parent.setVisibility(View.GONE);
//            } else {
//
//                middle_news_parent.setVisibility(View.VISIBLE);
//
//
//            }
//
//
//            if (TextUtils.isEmpty(titles.getNews3())) {
//                btn_bottom_news_title.setVisibility(View.GONE);
//                devider_bottom_news_ref.setVisibility(View.GONE);
//            } else {
//                btn_bottom_news_title.setVisibility(View.VISIBLE);
//                devider_bottom_news_ref.setVisibility(View.VISIBLE);
//            }
//
//            if (titles.getNews3() == null) {
//                bottomNews_parent.setVisibility(View.GONE);
//            } else {
//
//                bottomNews_parent.setVisibility(View.VISIBLE);
//
//            }
//
//
//            if (TextUtils.isEmpty(titles.getNews4())) {
//                title_recommended_news.setVisibility(View.GONE);
//                devider_below_recommended.setVisibility(View.GONE);
//            } else {
//                title_recommended_news.setVisibility(View.VISIBLE);
//                devider_below_recommended.setVisibility(View.VISIBLE);
//            }
//
//
//            if (titles.getNews4() == null) {
//                recomNewsParent_layout.setVisibility(View.GONE);
//            } else {
//
//                recomNewsParent_layout.setVisibility(View.VISIBLE);
//
//            }
//
//
//            if (titles.getPowered_by() == null) {
//                powerdByLayout.setVisibility(View.GONE);
//            } else {
//                powerdByLayout.setVisibility(View.VISIBLE);
//            }
//            if (TextUtils.isEmpty(titles.getFloater()) || (arraylist_floater_cate == null ||
//                    arraylist_floater_cate.size() <= 0)) {
//                fram_layout_floater.setVisibility(View.GONE);
//            } else {
//                fram_layout_floater.setVisibility(View.VISIBLE);
//                Floater_Category_Adapter floater_adapter = new Floater_Category_Adapter
//                        (context, arraylist_floater_cate, new Floater_Category_Adapter
//                                .OnFloaterClick() {
//
//                            @Override
//                            public void onItemClick(int position, String title, String open_with,
//                                                    String package_name,
//                                                    String redirect_link,
//                                                    ArrayList<Floater_sub_Pojo> list) {
//                                if (TextUtils.isEmpty(open_with)) {
//                                    if (list != null && list.size() > 0) {
//                                        closed_floater_from_out();
//                                        show_floater_popup(title, list);
//                                        MyUtility.saveTracksInDB(context, "Floater Popup",
//                                                "Floater Popup", "Floater", "0", title, "");
//                                        MyApplication.getInstance().trackEvent("Floater",
//                                                "Floater Popup",
//                                                title);
//                                    } else {
//                                        closed_floater_from_out();
//
//                                    }
//                                } else {
//
//
//                                    MyApplication.getInstance().trackEvent("Floater", redirect_link,
//                                            title);
//                                    MyUtility.handleItemClick(context, package_name,
//                                            redirect_link, redirect_link, "Floater", open_with,
//                                            title);
//                                    closed_floater_from_out();
//
//                                }
//
//
//                            }
//                        });
//                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
//                        LinearLayoutManager.VERTICAL, false);
//                floater_recyclerView.setLayoutManager(layoutManager);
//                floater_recyclerView.setAdapter(floater_adapter);
//            }
//
//            String view_hide_all = MyApplication.app_sharedPreferences.getString("view_hide_all",
//                    "");
//            String view_status = MyApplication.app_sharedPreferences.getString("view_status", "");
//            if (view_hide_all.equalsIgnoreCase("yes") || view_hide_all.equalsIgnoreCase
//                    ("noconditionhideall") && view_status.equalsIgnoreCase("yes")) {
//                String view_model = MyApplication.app_sharedPreferences.getString("view_model", "");
//                String view_states = MyApplication.app_sharedPreferences.getString("view_states",
//                        "");
//                boolean hide_statue = false;
//
//                if (!TextUtils.isEmpty(view_model) && !TextUtils.isEmpty(view_states)) {
//                    String getLat = MyApplication.app_sharedPreferences.getString("Latitude",
//                            "0.0");
//                    String getLong = MyApplication.app_sharedPreferences.getString("Longitude",
//                            "0.0");
//                    String rkt_state = "";
//                    try {
//                        rkt_state = MyUtility.getLocationAddress_State(context, Double
//                                .parseDouble(getLat), Double.parseDouble(getLong));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    if (!TextUtils.isEmpty(rkt_state) && ((view_states.toLowerCase()).contains
//                            (rkt_state.toLowerCase())) && (view_model
//                            .toLowerCase()).contains((MyUtility.getDeviceModelName()).toLowerCase
//                            ())) {
//                        hide_statue = true;
//                    } else {
//                        hide_statue = false;
//                    }
//                } else if (!TextUtils.isEmpty(view_states)) {
//                    String getLat = MyApplication.app_sharedPreferences.getString("Latitude",
//                            "0.0");
//                    String getLong = MyApplication.app_sharedPreferences.getString("Longitude",
//                            "0.0");
//                    String rkt_state = "";
//                    try {
//                        rkt_state = MyUtility.getLocationAddress_State(context, Double
//                                .parseDouble(getLat), Double.parseDouble(getLong));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    if (!TextUtils.isEmpty(rkt_state) && ((view_states.toLowerCase()).contains
//                            (rkt_state.toLowerCase()))) {
//                        hide_statue = true;
//                    } else {
//                        hide_statue = false;
//                    }
//                } else if (!TextUtils.isEmpty(view_model)) {
//
//                    if ((view_model.toLowerCase()).contains(((MyUtility.getDeviceModelName())
//                            .toLowerCase()))) {
//                        hide_statue = true;
//                    } else {
//                        hide_statue = false;
//                    }
//                } else if (view_hide_all.equalsIgnoreCase("noconditionhideall")) {
//
//                    hide_statue = true;
//
//                }
//
//
//                if (hide_statue && (view_hide_all.equalsIgnoreCase("yes") || view_hide_all
//                        .equalsIgnoreCase("noconditionhideall"))) {
//                    parent_admob_one.setVisibility(View.GONE);
//                    parent_admob_two.setVisibility(View.GONE);
//                    parent_admob_three.setVisibility(View.GONE);
//                    parent_admob_four.setVisibility(View.GONE);
//                    parent_admob_five.setVisibility(View.GONE);
//                    btn_service_title.setVisibility(View.GONE);
//                    o_service_layout_parent.setVisibility(View.GONE);
//                    service_layout_parent.setVisibility(View.GONE);
//                    top_banner_parent.setVisibility(View.GONE);
//                    btn_news_title.setVisibility(View.GONE);
//                    devider_below_new_ref.setVisibility(View.GONE);
//                    topNewsParent_layout.setVisibility(View.GONE);
//                    entertainment_parent.setVisibility(View.GONE);
//                    topapps_ref_layout.setVisibility(View.GONE);
//                    topapps_parent.setVisibility(View.GONE);
//                    games_ref_layout.setVisibility(View.GONE);
//                    games_parent.setVisibility(View.GONE);
//                    externalGames_ref_layout.setVisibility(View.GONE);
//                    externalGames_parent_layout.setVisibility(View.GONE);
//                    bottom_adv_parent.setVisibility(View.GONE);
//                    wallpaper_ref_layout.setVisibility(View.GONE);
//                    wallpaper_parent.setVisibility(View.GONE);
//                    middle_news_parent.setVisibility(View.GONE);
//                    bottomNews_parent.setVisibility(View.GONE);
//                    recomNewsParent_layout.setVisibility(View.GONE);
//                    fram_layout_floater.setVisibility(View.GONE);
//                    parent_taboola.setVisibility(View.GONE);
//
//                }
//            }
//
//
//        } else {
//            parent_admob_one.setVisibility(View.GONE);
//            parent_admob_two.setVisibility(View.GONE);
//            parent_admob_three.setVisibility(View.GONE);
//            parent_admob_four.setVisibility(View.GONE);
//            parent_admob_five.setVisibility(View.GONE);
//
//
//            btn_service_title.setVisibility(View.GONE);
//
//            o_service_layout_parent.setVisibility(View.GONE);
//            service_layout_parent.setVisibility(View.GONE);
//
//            top_banner_parent.setVisibility(View.GONE);
//            btn_news_title.setVisibility(View.GONE);
//            devider_below_new_ref.setVisibility(View.GONE);
//            topNewsParent_layout.setVisibility(View.GONE);
//            entertainment_parent.setVisibility(View.GONE);
//            powerdByLayout.setVisibility(View.GONE);
//            topapps_ref_layout.setVisibility(View.GONE);
//            topapps_parent.setVisibility(View.GONE);
//            games_ref_layout.setVisibility(View.GONE);
//            games_parent.setVisibility(View.GONE);
//            externalGames_ref_layout.setVisibility(View.GONE);
//            externalGames_parent_layout.setVisibility(View.GONE);
//            bottom_adv_parent.setVisibility(View.GONE);
//            wallpaper_ref_layout.setVisibility(View.GONE);
//            wallpaper_parent.setVisibility(View.GONE);
//            middle_news_parent.setVisibility(View.GONE);
//            bottomNews_parent.setVisibility(View.GONE);
//            recomNewsParent_layout.setVisibility(View.GONE);
//            fram_layout_floater.setVisibility(View.GONE);
//            parent_taboola.setVisibility(View.GONE);
//        }
//
//
//    }
//
//    private void remove_givenView(RelativeLayout rr) {
//        try {
//            rr.removeAllViews();
//            rr.setVisibility(View.GONE);
//        } catch (Exception e) {
//            try {
//                rr.setVisibility(View.GONE);
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//            e.printStackTrace();
//        }
//
//    }
//
//    private void hit_admob_function(int admob_index, RelativeLayout rl) {
//
//        if (arraylist_admob_nubit != null && arraylist_admob_nubit.size() > 0) {
//            String admob_id = "";
//            try {
//                admob_id = arraylist_admob_nubit.get(admob_index).getAdmob_id()
//                        .toString();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            String admob_size = "";
//            try {
//                admob_size = arraylist_admob_nubit.get(admob_index).getAdmob_size()
//                        .toString();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                setup_nubit_admob(admob_id, admob_size, rl);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
///*
//            We dont want parse to many AdmobIDs, so for sake of simplicity, I am saving the 3rd
//            Index
//            Id in sharedpreference for showing the ads in YoupTV Page......So in short if
//            admob_nubit array in getData
//             have value at 3rd index then it should show ads on homepage somewhere and also in
//             YUPTV Screen at bottom.*/
//            try {
//                if (admob_index != 0 && admob_index == 3) {
//                    MyApplication.app_editor.putString("yup_ad_id", admob_id).apply();
//                    MyApplication.app_editor.putString("yup_ad_size", admob_size).apply();
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//        }
//
//
//    }
//
//
//    private void setup_nubit_admob(String admobId, String admobSize, final RelativeLayout
//            rel_Layout) {
//        /* adView.setAdUnitId("ca-app-pub-6136742319000358/2067197049");*/
//
//        if (TextUtils.isEmpty(admobSize)) {
//            admobSize = "MEDIUM_RECTANGLE";
//        }
//
//
//        if (!TextUtils.isEmpty(admobId) && rel_Layout != null) {
//            final AdView adView = new AdView(context);
//            adView.setAdUnitId(admobId);
//            if (admobSize.equalsIgnoreCase("BANNER")) {
//                adView.setAdSize(AdSize.BANNER);
//            } else if (admobSize.equalsIgnoreCase("LARGE_BANNER")) {
//                adView.setAdSize(AdSize.LARGE_BANNER);
//            } else if (admobSize.equalsIgnoreCase("MEDIUM_RECTANGLE")) {
//                adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
//            } else if (admobSize.equalsIgnoreCase("SMART_BANNER")) {
//                adView.setAdSize(AdSize.SMART_BANNER);
//            } else if (admobSize.equalsIgnoreCase("LEADERBOARD")) {
//                adView.setAdSize(AdSize.LEADERBOARD);
//            } else {
//                adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
//            }
//           /* AdRequest adRequest = new AdRequest.Builder().addTestDevice
//                    ("68A6308D09F439DCC42E3F151794B949").build();
//            adView.loadAd(adRequest);*/
//
//            try {
//                AdRequest adRequest = new AdRequest.Builder().build();
//                adView.loadAd(adRequest);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            rel_Layout.setVisibility(View.VISIBLE);
//
//            adView.setAdListener(new AdListener() {
//                @Override
//                public void onAdClosed() {
//                    super.onAdClosed();
//                }
//
//                @Override
//                public void onAdFailedToLoad(int i) {
//                    super.onAdFailedToLoad(i);
//
//
//                    /*  rel_Layout.setVisibility(View.GONE);*/
//                }
//
//                @Override
//                public void onAdLeftApplication() {
//                    super.onAdLeftApplication();
//                }
//
//                @Override
//                public void onAdOpened() {
//                    super.onAdOpened();
//                    //System.out.println(TAG + " onAdOpened");
//                }
//
//                @Override
//                public void onAdLoaded() {
//                    super.onAdLoaded();
//                    try {
//                        if (adView.getParent() == null) {
//                            rel_Layout.addView(adView);
//                        } else {
//                            ((ViewGroup) adView.getParent()).removeView(adView);
//
//                            rel_Layout.addView(adView);
//
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            });
//
//        }
//
//    }
//
//    private void closed_floater_from_out() {
//        clickcount_floater = clickcount_floater + 1;
//        nubit_floater.startAnimation(rotate_forward);
//        fram_layout_floater.setBackgroundColor(Color.TRANSPARENT);
//        floater_recyclerView.setVisibility(View.GONE);
//    }
//
//    public void show_floater_popup(String title, ArrayList<Floater_sub_Pojo> list) {
//
//        final Dialog floater_dialog = new Dialog(context);
//        floater_dialog.setContentView(R.layout.floater_sub_category);
//        floater_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics
//                .Color
//                .TRANSPARENT));
//        TextView txt_dialog_title = floater_dialog.findViewById(R.id.txt_dialog_title);
//        ImageView popup_cross_button = floater_dialog.findViewById(R.id.popup_cross_button);
//        fram_layout_floater.setVisibility(View.VISIBLE);
//        fram_layout_floater.setBackgroundColor(Color.parseColor("#99000000"));
//        txt_dialog_title.setText(title);
//
//
//        if (!((Activity) context).isFinishing()) {
//            floater_dialog.show();
//        }
//
//
//
//
//        /* Log.d("MyList", String.valueOf(list));*/
//
//
//        Grid_Floater_Sub_CatAdapter floater_subCate_grid_adapter = new
//                Grid_Floater_Sub_CatAdapter(context, list);
//        MyGridView floater_subCate_grid = floater_dialog.findViewById(R.id
//                .floater_subcategory_grid);
//        floater_subCate_grid.setAdapter(floater_subCate_grid_adapter);
//
//
//        floater_dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//
//                fram_layout_floater.setBackgroundColor(Color.TRANSPARENT);
//
//            }
//        });
//        popup_cross_button.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//
//                floater_dialog.dismiss();
//
//                return false;
//            }
//        });
//
//
//    }
//
//    private void init_floater_veiw(View view) {
//
//        nubit_floater = view.findViewById(R.id.nubit_floater);
//        rotate_forward = AnimationUtils.loadAnimation(context, R.anim.rotate_forward);
//        rotate_backward = AnimationUtils.loadAnimation(context, R.anim.rotate_backward);
//        floater_recyclerView = view.findViewById(R.id.floater_recyclerView);
//        floater_recyclerView.setVisibility(View.GONE);
//
//        nubit_floater.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MyApplication.getInstance().trackEvent("Floater", "Floater Clicked", "Floater");
//                animateMyFloater();
//
//            }
//        });
//
//
//    }
//
//
//    public void animateMyFloater() {
//
//
//        clickcount_floater = clickcount_floater + 1;
//        if ((clickcount_floater % 2) == 0) {
//            nubit_floater.startAnimation(rotate_forward);
//
//
//            fram_layout_floater.setBackgroundColor(Color.TRANSPARENT);
//            /* ad_plus_floater_layout.setBackgroundColor(Color.TRANSPARENT);*/
//            floater_recyclerView.setVisibility(View.GONE);
//
//
//        } else {
//            nubit_floater.startAnimation(rotate_backward);
//            fram_layout_floater.setBackgroundColor(Color.parseColor("#99000000"));
//            /*   ad_plus_floater_layout.setBackgroundColor(Color.parseColor("#99000000"));*/
//            floater_recyclerView.setVisibility(View.VISIBLE);
//
//
//        }
//    }
//
//
//    public void show_publisher_popup(Context cc, ArrayList<NewPubPojo> list) {
//
//        final Dialog
//                publisher_dialog = new Dialog(cc);
//        publisher_dialog.setContentView(R.layout.publisher_pop_layout);
//        publisher_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color
//                .TRANSPARENT));
//        TextView txt_dialog_title = publisher_dialog.findViewById(R.id.txt_dialog_title);
//        ImageView popup_cross_button = publisher_dialog.findViewById(R.id.popup_cross_button);
//        Button
//                btn_pub_popup = publisher_dialog.findViewById(R.id.btn_pub_popup);
//
//        String
//                get_pubName = MyApplication.app_sharedPreferences.getString("select_publisher", "");
//        String
//                pub_btn = MyApplication.app_sharedPreferences.getString("pub_btn", "");
//
//        if
//        (!TextUtils.isEmpty(get_pubName)) {
//            txt_dialog_title.setText(get_pubName);
//        }
//        if
//        (!TextUtils.isEmpty(pub_btn)) {
//            btn_pub_popup.setText(pub_btn);
//        }
//
//        if (!((Activity) context).isFinishing()) {
//            publisher_dialog.show();
//        }
//
//
//        Grid_NewsPub grid_newsPub = new
//                Grid_NewsPub(cc, list);
//        GridView pub_grid = publisher_dialog.findViewById(R.id
//                .news_pub_grid);
//        pub_grid.setAdapter(grid_newsPub);
//
//
//        publisher_dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                String pub_name = MyApplication.app_sharedPreferences.getString("pub_name",
//                        "");
//                MyApplication.getInstance().trackEvent("News Publisher Popup", pub_name, "News " +
//                        "Publisher Popup");
//
//                init_BottomNews_Views();
//
//                MyUtility.saveTracksInDB(context, "", "", "News Publisher Popup", "1",
//                        pub_name, "");
//            }
//        });
//        popup_cross_button.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
///*
//                String pub_name = MyApplication.app_sharedPreferences.getString("pub_name",
//                        "");
//                MyApplication.getInstance().trackEvent("News Publisher", pub_name, "News " +
//                        "Publisher");*/
//                publisher_dialog.dismiss();
//
//                return false;
//            }
//        });
//
//        btn_pub_popup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              /*  String pub_name = MyApplication.app_sharedPreferences.getString("pub_name",
//                        "");
//                MyApplication.getInstance().trackEvent("News Publisher", pub_name, "News " +
//                        "Publisher");*/
//                publisher_dialog.dismiss();
//
//            }
//        });
//
//
//    }
//
//    public void show_videoCat_popup(Context cc, ArrayList<VideoCat> list) {
//
//        final Dialog
//                videoCat_dialog = new Dialog(cc);
//        videoCat_dialog.setContentView(R.layout.publisher_pop_layout);
//        videoCat_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color
//                .TRANSPARENT));
//        TextView txt_dialog_title = videoCat_dialog.findViewById(R.id.txt_dialog_title);
//        ImageView popup_cross_button = videoCat_dialog.findViewById(R.id.popup_cross_button);
//        Button btn_videocat_popup = videoCat_dialog.findViewById(R.id.btn_pub_popup);
//
//        String vid_pop_title = MyApplication.app_sharedPreferences.getString
//                ("vid_pop_title", "");
//        String pub_btn = MyApplication.app_sharedPreferences.getString("pub_btn", "");
//
//        if
//        (!TextUtils.isEmpty(vid_pop_title)) {
//            txt_dialog_title.setText(vid_pop_title);
//        }
//        if
//        (!TextUtils.isEmpty(pub_btn)) {
//            btn_videocat_popup.setText(pub_btn);
//        }
//
//
//        if (!((Activity) context).isFinishing()) {
//            videoCat_dialog.show();
//        }
//
//        Grid_VideoCat grid_videoCat = new
//                Grid_VideoCat(cc, list);
//        GridView videoCat_grid = videoCat_dialog.findViewById(R.id
//                .news_pub_grid);
//        videoCat_grid.setNumColumns(2);
//        videoCat_grid.setAdapter(grid_videoCat);
//
//
//        videoCat_dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//
//                String cat_title = MyApplication.app_sharedPreferences.getString("cat_title",
//                        "");
//
//                MyApplication.getInstance().trackEvent("Video Category Popup", cat_title, "Video " +
//                        "Category Popup");
//
//                init_top_banner_view();
//
//                MyUtility.saveTracksInDB(context, "", "", "Video Category Popup", "1",
//                        cat_title, "");
//
//            }
//        });
//        popup_cross_button.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//           /*     String cat_title = MyApplication.app_sharedPreferences.getString("cat_title",
//                        "");
//
//                MyApplication.getInstance().trackEvent("Video Category", cat_title, "Video " +
//                        "Category");
//                init_top_banner_view();*/
//                videoCat_dialog.dismiss();
//
//                return false;
//            }
//        });
//
//        btn_videocat_popup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//       /*         String cat_title = MyApplication.app_sharedPreferences.getString("cat_title",
//                        "");
//
//                MyApplication.getInstance().trackEvent("Video Category", cat_title, "Video " +
//                        "Category");*/
//                videoCat_dialog.dismiss();
//
//            }
//        });
//
//
//    }
//
//    public void add_sortedView() {
//
//
//        Map<Integer, String> sortedMap = null;
//        try {
//            sortedMap = new TreeMap<>(sort_Order_map);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//
//
//        try {
//            layout_container_nubitview.removeAllViews();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            layout_container_nubitview.addView(rkt_webview);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(0)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(0)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(1)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(1)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//
//        try {
//            layout_container_nubitview.addView(admob_rkt_one);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(2)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(2)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(3)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(3)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            layout_container_nubitview.addView(admob_rkt_two);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(4)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(4)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//
//        try {
//            if (view_Map.get(sortedMap.get(5)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(5)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            layout_container_nubitview.addView(admob_rkt_three);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//
//
//        try {
//            if (view_Map.get(sortedMap.get(6)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(6)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            layout_container_nubitview.addView(admob_rkt_four);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//
//        try {
//            if (view_Map.get(sortedMap.get(7)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(7)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            layout_container_nubitview.addView(admob_rkt_five);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//
//        try {
//            if (view_Map.get(sortedMap.get(8)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(8)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//
//
//        try {
//            if (view_Map.get(sortedMap.get(9)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(9)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(10)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(10)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(11)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(11)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(12)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(12)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(13)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(13)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(14)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(14)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(15)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(15)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(16)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(16)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(17)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(17)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(18)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(18)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(19)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(19)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(20)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(20)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(21)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(21)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(22)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(22)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(23)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(23)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(24)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(24)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        try {
//            if (view_Map.get(sortedMap.get(25)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(25)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//
//        try {
//            if (view_Map.get(sortedMap.get(26)) != null) {
//                layout_container_nubitview.addView(view_Map.get(sortedMap.get(26)));
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//
//    }
//
//    private static NubitView nubitView;
//
//    public static NubitView getInstance(Context context) {
//        if (nubitView == null) {
//            nubitView = new NubitView(context);
//        }
//
//        return nubitView;
//    }
//
//    private void hit_admob_mobin() {
//
//        boolean hit_status = false;
//
//
//        String admob_status_rkt = MyApplication.app_sharedPreferences.getString
//                ("admob_status_rkt", "");
//        if (!TextUtils.isEmpty(admob_status_rkt)) {
//            String admob_states_rkt = MyApplication.app_sharedPreferences.getString
//                    ("admob_states_rkt", "");
//            String admob_model_rkt = MyApplication.app_sharedPreferences.getString
//                    ("admob_model_rkt", "");
//            String admob_id1_rkt = MyApplication.app_sharedPreferences.getString
//                    ("admob_id1_rkt", "");
//            String admob_id2_rkt = MyApplication.app_sharedPreferences.getString
//                    ("admob_id2_rkt", "");
//            String admob_id3_rkt = MyApplication.app_sharedPreferences.getString
//                    ("admob_id3_rkt", "");
//            String admob_id4_rkt = MyApplication.app_sharedPreferences.getString
//                    ("admob_id4_rkt", "");
//            String admob_id5_rkt = MyApplication.app_sharedPreferences.getString
//                    ("admob_id5_rkt", "");
//
//
//            // If admob_type_home is null then all the Ads on home screen should be
//            // Medium_Ractangle, else as per admob_type_home
//            String admob_type_home = MyApplication.app_sharedPreferences.getString
//                    ("admob_type_home", "");
//
//
//            if (admob_status_rkt.equalsIgnoreCase("yes")) {
//
//
//                if (!TextUtils.isEmpty(admob_model_rkt) && !TextUtils.isEmpty
//                        (admob_states_rkt)) {
//                    String getLat = MyApplication.app_sharedPreferences.getString("Latitude",
//                            "0.0");
//
//                    String getLong = MyApplication.app_sharedPreferences.getString("Longitude",
//                            "0.0");
//                    String rkt_state = "";
//                    try {
//                        rkt_state = MyUtility.getLocationAddress_State(context, Double
//                                .parseDouble(getLat), Double.parseDouble(getLong));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    if (!TextUtils.isEmpty(rkt_state) && ((admob_states_rkt.toLowerCase())
//                            .contains
//                                    (rkt_state.toLowerCase())) && (admob_model_rkt
//                            .toLowerCase()).contains((MyUtility.getDeviceModelName())
//                            .toLowerCase
//                                    ())) {
//                        hit_status = true;
//                    } else {
//                        hit_status = false;
//                    }
//                } else if (!TextUtils.isEmpty(admob_states_rkt)) {
//                    String getLat = MyApplication.app_sharedPreferences.getString("Latitude",
//                            "0.0");
//                    String getLong = MyApplication.app_sharedPreferences.getString("Longitude",
//                            "0.0");
//                    String rkt_state = "";
//                    try {
//                        rkt_state = MyUtility.getLocationAddress_State(context, Double
//                                .parseDouble(getLat), Double.parseDouble(getLong));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    if (!TextUtils.isEmpty(rkt_state) && ((admob_states_rkt.toLowerCase())
//                            .contains
//                                    (rkt_state.toLowerCase()))) {
//                        hit_status = true;
//                    } else {
//                        hit_status = false;
//                    }
//                } else if (!TextUtils.isEmpty(admob_model_rkt)) {
//
//                    if ((admob_model_rkt.toLowerCase()).contains(((MyUtility
//                            .getDeviceModelName())
//                            .toLowerCase()))) {
//                        hit_status = true;
//                    } else {
//                        hit_status = false;
//                    }
//                } else if (TextUtils.isEmpty(admob_model_rkt) && TextUtils.isEmpty
//                        (admob_states_rkt)) {
//                    hit_status = true;
//                }
//
//                if (!TextUtils.isEmpty(admob_id1_rkt) && hit_status) {
//                    setup_nubit_admob(admob_id1_rkt, admob_type_home, admob_rkt_one);
//                } else {
//                    try {
//                        admob_rkt_one.removeAllViews();
//                        admob_rkt_one.setVisibility(View.GONE);
//                    } catch (Exception e) {
//                        admob_rkt_one.setVisibility(View.GONE);
//                        e.printStackTrace();
//                    }
//                }
//                if (!TextUtils.isEmpty(admob_id2_rkt) && hit_status) {
//                    setup_nubit_admob(admob_id2_rkt, admob_type_home, admob_rkt_two);
//                } else {
//                    try {
//                        admob_rkt_two.removeAllViews();
//                        admob_rkt_two.setVisibility(View.GONE);
//                    } catch (Exception e) {
//                        admob_rkt_two.setVisibility(View.GONE);
//                        e.printStackTrace();
//                    }
//                }
//                if (!TextUtils.isEmpty(admob_id3_rkt) && hit_status) {
//                    setup_nubit_admob(admob_id3_rkt, admob_type_home, admob_rkt_three);
//                } else {
//                    try {
//                        admob_rkt_three.removeAllViews();
//                        admob_rkt_three.setVisibility(View.GONE);
//                    } catch (Exception e) {
//                        admob_rkt_three.setVisibility(View.GONE);
//                        e.printStackTrace();
//                    }
//                }
//
//                if (!TextUtils.isEmpty(admob_id4_rkt) && hit_status) {
//                    setup_nubit_admob(admob_id4_rkt, admob_type_home, admob_rkt_four);
//                } else {
//                    try {
//                        admob_rkt_four.removeAllViews();
//                        admob_rkt_four.setVisibility(View.GONE);
//                    } catch (Exception e) {
//                        admob_rkt_four.setVisibility(View.GONE);
//                        e.printStackTrace();
//                    }
//                }
//
//                if (!TextUtils.isEmpty(admob_id5_rkt) && hit_status) {
//                    setup_nubit_admob(admob_id5_rkt, admob_type_home, admob_rkt_five);
//                } else {
//                    try {
//                        admob_rkt_five.removeAllViews();
//                        admob_rkt_five.setVisibility(View.GONE);
//                    } catch (Exception e) {
//                        admob_rkt_five.setVisibility(View.GONE);
//                        e.printStackTrace();
//                    }
//                }
//
//
//            }
//        } else {
//            try {
//                admob_rkt_one.setVisibility(View.GONE);
//                admob_rkt_two.setVisibility(View.GONE);
//                admob_rkt_three.setVisibility(View.GONE);
//                admob_rkt_four.setVisibility(View.GONE);
//                admob_rkt_five.setVisibility(View.GONE);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//
//    }
//
//    private void setUp_MobinView() {
//
//        String view_status = MyApplication.app_sharedPreferences.getString("view_status", "");
//
//        if (TextUtils.isEmpty(view_status) || view_status.equalsIgnoreCase("no") || !view_status
//                .equalsIgnoreCase("yes")) {
//            rkt_webview.setVisibility(View.GONE);
//            return;
//        } else {
//            String view_url = MyApplication.app_sharedPreferences.getString("view_url", "");
//            String view_height = MyApplication.app_sharedPreferences.getString("view_height",
//                    "");
//            String view_model = MyApplication.app_sharedPreferences.getString("view_model", "");
//            String view_states = MyApplication.app_sharedPreferences.getString("view_states",
//                    "");
//
//
//            if (!TextUtils.isEmpty(view_model) && !TextUtils.isEmpty(view_states)) {
//                String getLat = MyApplication.app_sharedPreferences.getString("Latitude",
//                        "0.0");
//                String getLong = MyApplication.app_sharedPreferences.getString("Longitude",
//                        "0.0");
//                String rkt_state = "";
//                try {
//                    rkt_state = MyUtility.getLocationAddress_State(context, Double
//                            .parseDouble(getLat), Double.parseDouble(getLong));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                if (!TextUtils.isEmpty(rkt_state) && ((view_states.toLowerCase()).contains
//                        (rkt_state.toLowerCase())) && (view_model
//                        .toLowerCase()).contains((MyUtility.getDeviceModelName()).toLowerCase
//                        ())) {
//                    rkt_webview.setVisibility(View.VISIBLE);
//                } else {
//                    rkt_webview.setVisibility(View.GONE);
//                }
//            } else if (!TextUtils.isEmpty(view_states)) {
//                String getLat = MyApplication.app_sharedPreferences.getString("Latitude",
//                        "0.0");
//                String getLong = MyApplication.app_sharedPreferences.getString("Longitude",
//                        "0.0");
//                String rkt_state = "";
//                try {
//                    rkt_state = MyUtility.getLocationAddress_State(context, Double
//                            .parseDouble(getLat), Double.parseDouble(getLong));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                if (!TextUtils.isEmpty(rkt_state) && ((view_states.toLowerCase()).contains
//                        (rkt_state.toLowerCase()))) {
//                    rkt_webview.setVisibility(View.VISIBLE);
//                } else {
//                    rkt_webview.setVisibility(View.GONE);
//                }
//            } else if (!TextUtils.isEmpty(view_model)) {
//
//                if ((view_model.toLowerCase()).contains(((MyUtility.getDeviceModelName())
//                        .toLowerCase()))) {
//                    rkt_webview.setVisibility(View.VISIBLE);
//                } else {
//                    rkt_webview.setVisibility(View.GONE);
//                }
//            } else if (TextUtils.isEmpty(view_model) && TextUtils.isEmpty(view_states)) {
//                rkt_webview.setVisibility(View.VISIBLE);
//            }
//
//
//            if (!TextUtils.isEmpty(view_height)) {
//                try {
//                    rkt_webview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup
//                            .LayoutParams.MATCH_PARENT, Integer.parseInt(view_height)));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//
//            WebSettings s = rkt_webview.getSettings();
//            s.setBuiltInZoomControls(false);
//            s.setAppCachePath(context.getCacheDir().getPath());
//            s.setAppCacheEnabled(true);
//            s.setCacheMode(WebSettings.LOAD_DEFAULT);
//            s.setUseWideViewPort(true);
//            s.setLoadWithOverviewMode(true);
//            s.setJavaScriptEnabled(true);
//            rkt_webview.getSettings().setDomStorageEnabled(true);
//            rkt_webview.getSettings().setDatabaseEnabled(true);
//
//            if (!TextUtils.isEmpty(view_url)) {
//                try {
//                    rkt_webview.loadUrl(view_url);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//
//        }
//
//
//    }
//
////    private void init_TaboolaView() {
////
////        try {
////            // Initialize the taboola instance
//////            taboolaWidget = new TaboolaWidget(context);
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////
////        try {
////
////            LayoutParams params = new LayoutParams(
////                    LayoutParams.MATCH_PARENT,
////                    LayoutParams.MATCH_PARENT
////            );
////
////            params.setMargins(23, 10, 23, 10);
////            taboolaWidget.setLayoutParams(params);
////
////            parent_taboola.addView(taboolaWidget);
////            taboolaWidget.setPublisher("nubit-nubitappsdk")
////                    /*  .setMode("thumbnails-a")
////                      .setPlacement("SDK App Minus One Screen SC")
////                      .setPageUrl("https://www.nubit.com")
////                      .setPageType("Category")
////                      .setPageId("https://www.nubit.com"); //default value is the relative path of
////              // the pageUrl provided.*/
////
////                    .setMode("thumbnails-a")
////                    .setPlacement("SDK App Minus One Screen SC")
////                    .setPageType("category")
////                    .setPageId("https://www.nubit.com")
////                    .setPageUrl("https://www.nubit.com");
////
////
////            taboolaWidget.fetchContent();
////
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////
////    }
//
//    public void callPushTracksApi(String tracksVal, Context activity) {
//        if (pushTracksOnServerApiTask != null && pushTracksOnServerApiTask.getStatus().equals
//                (AsyncTask.Status.RUNNING)) {
//            return;
//        }
//
//        String tracks = tracksVal;
//
//        pushTracksOnServerApiTask = new PushTracksOnServerApiTask(activity, tracks, new
//                PushTracksOnServerApiTask.PushTracksDataCallback() {
//                    @Override
//                    public void onSuccess(String response) {
//
//                        pushTracksOnServerApiTask = null;
//                        try {
//                            if (!TextUtils.isEmpty(response) && MyUtility.isJSONValid(response)) {
//                               Gson gson= new Gson();
//                                CommonResponse commonResponse = gson.fromJson(response,
//                                        CommonResponse
//                                                .class);
//
//                                if (commonResponse.getSuccess()) {
//                                    deleteTracksFromDB();
//                                } else {
//
//                                }
//
//                            }
//
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void failure() {
//                        pushTracksOnServerApiTask = null;
//                    }
//                });
//
//        if (MyUtility.isConnectedToInternet()) {
//            TaskHelper.execute(pushTracksOnServerApiTask);
//        }
//    }
//
//    private void deleteTracksFromDB() {
//
//        try {
//            DbHandler dbHandler = DbHandler.getInstance(context);
//            SQLiteDatabase _sqlDb = dbHandler.openDb(1);
//            TrackingDAO.getInstance().deleteAllData(_sqlDb);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * prepare jsonObject for analytics data
//     */
//    private void createTracksJson(ArrayList<TrackingPojo> trackingPojoArrayList) {
//
//        JSONObject tracks = new JSONObject();
//        JSONArray jsonArray = new JSONArray();
//        JSONObject jarrayOBJ = new JSONObject();
//        try {
//            tracks.put("device_id", MyUtility.getDeviceID(context));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            tracks.put("advertisement_Id", MyApplication.app_sharedPreferences.getString
//                    ("getAdvertisementId", ""));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            tracks.put("user_agent", MyUtility.getUserAgent());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        for (int i = 0; i < trackingPojoArrayList.size(); i++) {
//            jarrayOBJ = new JSONObject();
//
//            try {
//                jarrayOBJ.put("type", trackingPojoArrayList.get(i).getCategory());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                if (trackingPojoArrayList.get(i).getUrl() != null && !TextUtils.isEmpty
//                        (trackingPojoArrayList.get(i).getUrl()))
//                    jarrayOBJ.put("url", trackingPojoArrayList.get(i).getUrl());
//                else
//                    jarrayOBJ.put("url", "NF");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//            try {
//                jarrayOBJ.put("title", trackingPojoArrayList.get(i).getTitle());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                jarrayOBJ.put("redirect_url", trackingPojoArrayList.get(i).getRedirectURL());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                jarrayOBJ.put("play_duration", trackingPojoArrayList.get(i).getPlay_duration());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//            try {
//                jarrayOBJ.put("launcher", trackingPojoArrayList.get(i).getLaunch());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                jarrayOBJ.put("datetime", trackingPojoArrayList.get(i).getDate());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            try {
//                jarrayOBJ.put("timestamp", convertDateFormat(trackingPojoArrayList.get(i).getDate
//                        ()) + i);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            jsonArray.put(jarrayOBJ);
//
//        }
//
//        try {
//            tracks.put("mobile_clicks", jsonArray);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        callPushTracksApi(tracks.toString(), context);
//    }
//
//    private String convertDateFormat(String dateVal) {
//
//        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
//        Date date = null;
//        try {
//            date = originalFormat.parse(dateVal);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String formattedDate = targetFormat.format(date);
//
//        return formattedDate;
//    }
//
//    private void pushTracksOnServer() {
//        ArrayList<TrackingPojo> trackingPojoArrayList = new ArrayList<>();
//
//        DbHandler dbHandler = DbHandler.getInstance(context);
//        SQLiteDatabase _sqlDb = dbHandler.openDb(1);
//        trackingPojoArrayList = TrackingDAO.getInstance().getTracksFromDB(_sqlDb);
//
//        if (trackingPojoArrayList != null && trackingPojoArrayList.size() > 0) {
//
//            createTracksJson(trackingPojoArrayList);
//        }
//
//    }
//
//}


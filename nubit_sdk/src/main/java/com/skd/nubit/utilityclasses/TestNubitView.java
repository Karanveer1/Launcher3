package com.skd.nubit.utilityclasses;

/*import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.rkt.lava.apicalls.CommonResponse;
import com.rkt.lava.apicalls.PushTracksOnServerApiTask;
import com.rkt.lava.apicalls.TaskHelper;
import com.rkt.lava.R;
import com.rkt.lava.videoplayerstuffs.PlayerActivity;
import com.rkt.lava.videoplayerstuffs.RktVideoView;
import com.rkt.lava.activities.Language_Set_Activity;
import com.rkt.lava.activities.ProfileActivity;
import com.rkt.lava.activities.WallpaperActivity;
import com.rkt.lava.adapters.Bottom_Adv_Adapter;
import com.rkt.lava.adapters.Bottom_News_Adapter;
import com.rkt.lava.adapters.Entertainment_Adapter;
import com.rkt.lava.adapters.External_Game_Adapter;
import com.rkt.lava.adapters.Floater_Category_Adapter;
import com.rkt.lava.adapters.GamesAdapter;
import com.rkt.lava.adapters.Grid_Floater_Sub_CatAdapter;
import com.rkt.lava.adapters.Grid_NewsPub;
import com.rkt.lava.adapters.Grid_VideoCat;
import com.rkt.lava.adapters.Middle_News_Adapter;
import com.rkt.lava.adapters.NewsByPub_Adapter;
import com.rkt.lava.adapters.Other_Services_Adapter;
import com.rkt.lava.adapters.Recom_News_Adapter;
import com.rkt.lava.adapters.Service_Adapter;
import com.rkt.lava.adapters.TopAppsAdapter;
import com.rkt.lava.adapters.TopBanner_Adapter;
import com.rkt.lava.adapters.Top_News_Adapter;
import com.rkt.lava.adapters.Wallpaper_Adapter;
import com.rkt.lava.database.DbHandler;
import com.rkt.lava.database.TrackingDAO;
import com.rkt.lava.models.Admob_nubitPojo;
import com.rkt.lava.models.Bottom_News_Pojo;
import com.rkt.lava.models.Bottom_adv_Pojo;
import com.rkt.lava.models.Configuration;
import com.rkt.lava.models.Data;
import com.rkt.lava.models.Entertainment_pojo;
import com.rkt.lava.models.External_game_pojo;
import com.rkt.lava.models.Floater_Category_Pojo;
import com.rkt.lava.models.Floater_sub_Pojo;
import com.rkt.lava.models.GamesPojo;
import com.rkt.lava.models.HomeVideoPojo;
import com.rkt.lava.models.Middle_News_Pojo;
import com.rkt.lava.models.NavigationPojo;
import com.rkt.lava.models.NewPubPojo;
import com.rkt.lava.models.NewsByPub_Pojo;
import com.rkt.lava.models.OtherServicesPojo;
import com.rkt.lava.models.Response;
import com.rkt.lava.models.Services_pojo;
import com.rkt.lava.models.SortOrder;
import com.rkt.lava.models.TITLES_IN_DIFF_LANG;
import com.rkt.lava.models.Titles;
import com.rkt.lava.models.TopApps_Pojo;
import com.rkt.lava.models.Top_News_Pojo;
import com.rkt.lava.models.TrackingPojo;
import com.rkt.lava.models.VideoCat;
import com.rkt.lava.models.Wallpaper_Pojo;
import com.rkt.lava.mycallbacks.LanguageUpdaterCallBack;
import com.rkt.lava.mycallbacks.PagersAutoScrollControllerCallback;
import com.rkt.lava.mycallbacks.RefreshDataCallBack;
import com.rkt.lava.mycallbacks.Video_Cat_CallBAck;
import com.taboola.android.TaboolaWidget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;




public class TestNubitView  extends FrameLayout implements View.OnClickListener,
        PagersAutoScrollControllerCallback
                .UpdateScroll, LanguageUpdaterCallBack.LanguageChangeCallback, Video_Cat_CallBAck
                .interface_VideoCat*//*, RktPlayerCallBack.UpdateonPlayerInit*//* {
    boolean post_delay_running = false;

    String scrollStatus = "home";
    PushTracksOnServerApiTask pushTracksOnServerApiTask;


    RecyclerView entertain_recyclerView, wallpaper_recyclerView, external_games_recyclerView,
            bottomNews_recyclerView, middleNews_recyclerView, bottomAdv_recyclerView,
            games_recyclerView,
            topapps_recyclerView, top_News_recyclerView, floater_recyclerView,
            other_ser_recyclerView;

    View devider_below_btnService, devider_below_recommended;
    Response response;
    AutoScrollViewPager home_topBanner_Pager;
    Button btn_add_pub;
    TextView btn_service_title, btn_bottom_news_title, title_middle_news,
            btn_news_title,
            btn_topapps_title, title_recommended_news,
            btn_games_title, btn_wallpaper_title, btn_externalGames_title;

    RktVideoView mVideoView;


    CircleIndicator topBanner_indicator;

    GridView service_grid;
    private TaboolaWidget taboolaWidget;

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


    RelativeLayout admob_rkt_one, admob_rkt_two, admob_rkt_three,
            admob_rkt_four, admob_rkt_five, layoutmovetop, bottomNews_parent,
            recomNewsParent_layout;
    ImageView nav_icon;

    WebView rkt_webview;

    ImageView topapps_news_ref, img_games_ref, img_wallpaper_ref, img_externalGames_ref, ad_cross;

    LinearLayout layout_container_nubitview, externalGames_parent_layout;
    RelativeLayout topNewsParent_layout, parent_admob_one, parent_admob_two, parent_admob_four,
            parent_admob_three, topapps_ref_layout, games_ref_layout, parent_admob_five,
            wallpaper_ref_layout, service_layout_parent, top_banner_parent,
            externalGames_ref_layout, o_service_layout_parent;
    View devider_below_new_ref;

    int clickcount_floater = 0;
    FrameLayout fram_layout_floater;
    *//*   LinearLayout ad_plus_floater_layout;*//*

    private boolean shouldScroll = false;
    private Animation rotate_forward;
    private Animation rotate_backward;


    Context context;
    boolean isAsyncTaskRunning;


    RelativeLayout parent_nubitview, layoutAdMob;


    ArrayList<HomeVideoPojo> arraylist_top_adver;
    ArrayList<OtherServicesPojo> arraylist_other_ser;
    ArrayList<Top_News_Pojo> arraylist_top_news;

    ArrayList<Entertainment_pojo> arraylist_entertainment;
    ArrayList<TopApps_Pojo> arraylist_topApps;
    ArrayList<GamesPojo> arraylist_games;
    ArrayList<NewPubPojo> arraylist_newspub;
    ArrayList<NewsByPub_Pojo> arraylist_newsbyPub;
    ArrayList<HomeVideoPojo> updatedList;
    ArrayList<External_game_pojo> arraylist_Flashgames;
    ArrayList<Bottom_adv_Pojo> arraylist_b_a_banner;
    ArrayList<Wallpaper_Pojo> arraylist_wallpaper;
    ArrayList<Middle_News_Pojo> arraylist_middle_news;
    ArrayList<Bottom_News_Pojo> arraylist_bottom_news;
    ArrayList<Services_pojo> arraylist_services;
    ArrayList<NavigationPojo> arraylist_nav;
    ArrayList<VideoCat> arraylist_videocat;
    ArrayList<Top_News_Pojo> arraylist_recom_news;
    ArrayList<Admob_nubitPojo> arraylist_admob_nubit;
    ArrayList<Floater_Category_Pojo> arraylist_floater_cate;

    ProgressBar pro_nointernet;
    Button btn_refresh;


    RelativeLayout nointernet_parent;

    RecyclerView news_recom_recyclerView;

    View myView;


    public TestNubitView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        returnTestNubitView(context);
    }

    public TestNubitView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        returnTestNubitView(context);
    }

    public TestNubitView(Context context) {
        super(context);
        this.context = context;
        returnTestNubitView(context);
    }

    private View returnTestNubitView(Context myContext) {

        myView = inflate(context, R.layout.layout_return_view, this);

        layout_container_nubitview = myView.findViewById(R.id.layout_container_nubitview);
        parent_nubitview = myView.findViewById(R.id.parent_nubitview);
        init_Add_View(myView);
        nested_scroolview_home = myView.findViewById(R.id.nested_scroolview_home);
        layoutmovetop = myView.findViewById(R.id.layoutmovetop);
        edt_search = myView.findViewById(R.id.edt_search);
        nested_scroolview_home.setUpScollview(layoutmovetop);
        nointernet_parent = myView.findViewById(R.id.nointernet_parent);
        progress_home = myView.findViewById(R.id.progress_home);
        btn_refresh = myView.findViewById(R.id.btn_refresh);
        pro_nointernet = myView.findViewById(R.id.pro_nointernet);
        drawerLayout = myView.findViewById(R.id.drawer);
        navigationView = myView.findViewById(R.id.navigation_view);
        rkt_webview = myView.findViewById(R.id.rkt_webview);
        fram_layout_floater = myView.findViewById(R.id.fram_layout_floater);
        *//* ad_plus_floater_layout = myView.findViewById(R.id.ad_plus_floater_layout);*//*
        layoutAdMob = myView.findViewById(R.id.layoutAdMob);
        ad_cross = myView.findViewById(R.id.ad_cross);
        nav_icon = myView.findViewById(R.id.nav_icon);
        mVideoView = new RktVideoView(myContext);

        btn_refresh.setOnClickListener(this);
        nav_icon.setOnClickListener(this);
      *//*  try {
            // Initialize the taboola instance
            taboolaWidget = new TaboolaWidget(context);
        } catch (Exception e) {
            e.printStackTrace();
        }*//*





        *//* RefreshDataCallBack.getInstance().notifyOnRefreshStart();*//*
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
            Video_Cat_CallBAck.getInstance().init_interface_VideoCat(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PagersAutoScrollControllerCallback.getInstance().setListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

*//*
        try {
            RktPlayerCallBack.getInstance().setListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }*//*


        hitVersion();
        hitGetProfile();
        if (!TextUtils.isEmpty(MyApplication.app_sharedPreferences.getString("key_modifiedDat",
                ""))) {
            RefreshDataCallBack.getInstance().notifyOnRefreshStart();

            parseData();


        } else {


            if (MyUtility.isConnectedToInternet()) {
                *//*Here notifying the launcher to start loading in case if user is new user*//*
                RefreshDataCallBack.getInstance().notifyOnRefreshStart();

            } else {
                parent_nubitview.setVisibility(View.VISIBLE);

            }
        }


        *//*    edt_search.setOnTouchListener(context);*//*
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


        home_topBanner_Pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                *//*   Log.d("scroll_position", String.valueOf(position));*//*
               *//* if (position == 5) {
                    stop_Scroll();
                }
*//*
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (ViewPager.SCROLL_STATE_DRAGGING == state) {
                    home_topBanner_Pager.stopAutoScroll();


                    if (shouldScroll == true) {

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                if (scrollStatus.equalsIgnoreCase("loading")) {
                                    home_topBanner_Pager.stopAutoScroll();
                                } else {
                                    scrollStatus = "home";
                                    home_topBanner_Pager.startAutoScroll();


                                }

                                *//* post_delay_running = true;*//*
                            }
                        }, 3000);

                    }

                    //Log.d("SCROLL_STATE_DRAGGING", "Yes");

                }
            }
        });


        try {
            // This is sending the analytical data to nubit server, but it should be call on
            // launcher hide like in nubit, not from here.


            pushTracksOnServer();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // Initialize the taboola instance
            taboolaWidget = new TaboolaWidget(myContext);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return myView;
    }


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
        news_recom_recyclerView = view.findViewById(R.id.news_recom_recyclerView);
        topapps_recyclerView = view.findViewById(R.id.topapps_recyclerView);
        bottomNews_recyclerView = view.findViewById(R.id.bottomNews_recyclerView);
        service_grid = view.findViewById(R.id.service_grid);
        btn_add_pub = view.findViewById(R.id.btn_add_pub);
        entertain_recyclerView = view.findViewById(R.id.entertainment_recyclerView);
        bottomAdv_recyclerView = view.findViewById(R.id.bottom_adv_recyclerView);
        games_recyclerView = view.findViewById(R.id.games_recyclerView);
        external_games_recyclerView = view.findViewById(R.id.externalGames_recyclerView);
        wallpaper_recyclerView = view.findViewById(R.id.wallpaper_recyclerView);
        middleNews_recyclerView = view.findViewById(R.id.middle_news_recyclerView);
        topapps_news_ref = view.findViewById(R.id.topapps_news_ref);
        img_games_ref = view.findViewById(R.id.img_games_ref);
        img_wallpaper_ref = view.findViewById(R.id.img_wallpaper_ref);
        img_externalGames_ref = view.findViewById(R.id.img_externalGames_ref);
        entertainment_parent = view.findViewById(R.id.entertainment_parent);
        topapps_parent = view.findViewById(R.id.topapps_parent_layout);
        games_parent = view.findViewById(R.id.games_parent_layout);
        wallpaper_parent = view.findViewById(R.id.wallpaper_parent_layout);
        parent_taboola = view.findViewById(R.id.parent_taboola);

        bottom_adv_parent = view.findViewById(R.id.bottom_adv_parent);
        middle_news_parent = view.findViewById(R.id.middle_news_parent);
        bottomNews_parent = view.findViewById(R.id.bottomNews_parent);
        recomNewsParent_layout = view.findViewById(R.id.recomNewsParent_layout);
        powerdByLayout = view.findViewById(R.id.powerdByLayout);
        service_layout_parent = view.findViewById(R.id.service_layout_parent);
        top_News_recyclerView = view.findViewById(R.id.news_top_recyclerView);
        wallpaper_recyclerView = view.findViewById(R.id.wallpaper_recyclerView);
        *//* irctc_List = view.findViewById(R.id.irctc_List);*//*
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


        topapps_news_ref = view.findViewById(R.id.topapps_news_ref);
        img_games_ref = view.findViewById(R.id.img_games_ref);
        img_wallpaper_ref = view.findViewById(R.id.img_wallpaper_ref);
        img_externalGames_ref = view.findViewById(R.id.img_externalGames_ref);
        topNewsParent_layout = view.findViewById(R.id.topNewsParent_layout);

        o_service_layout_parent = view.findViewById(R.id.o_service_layout_parent);

        topapps_ref_layout = view.findViewById(R.id.topapps_ref_layout);
        top_banner_parent = view.findViewById(R.id.top_banner_parent);
        games_ref_layout = view.findViewById(R.id.games_ref_layout);
        externalGames_ref_layout = view.findViewById(R.id.externalGames_ref_layout);
        wallpaper_ref_layout = view.findViewById(R.id.wallpaper_ref_layout);
        devider_bottom_news_ref = view.findViewById(R.id.devider_bottom_news_ref);
        devider_title_middle_news = view.findViewById(R.id.devider_title_middle_news);
        externalGames_parent_layout = view.findViewById(R.id.externalGames_parent_layout);
        devider_below_new_ref = view.findViewById(R.id.devider_below_new_ref);

        topapps_news_ref.setOnClickListener(this);
        img_games_ref.setOnClickListener(this);
        img_wallpaper_ref.setOnClickListener(this);
        img_externalGames_ref.setOnClickListener(this);
        btn_add_pub.setOnClickListener(this);

        init_floater_veiw(view);


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
        arraylist_nav = new ArrayList<>();
        arraylist_videocat = new ArrayList<>();
        arraylist_newsbyPub = new ArrayList<>();
        arraylist_recom_news = new ArrayList<>();
        arraylist_admob_nubit = new ArrayList<>();
        arraylist_floater_cate = new ArrayList<>();


        String get_SavedJson = MyApplication.app_sharedPreferences.getString
                ("save_app_Json",
                        "");
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


            try {
                arraylist_Flashgames.addAll(data.getExternal_game());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                arraylist_wallpaper.addAll(data.getWallpaper());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                arraylist_middle_news.addAll(data.getNews2());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                arraylist_videocat.addAll(data.getVideos_categories());
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                arraylist_nav.addAll(data.getDrawer_item());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                arraylist_floater_cate.addAll(data.getFloater());
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (data.getSearchbar() != null && data.getSearchbar().size() > 0) {
                    MyApplication.app_editor.putString("search_redirection", data.getSearchbar()
                            .get(0).getRedirect_link()).apply();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                if (data.getSort_order() != null && data.getSort_order().size() > 0) {
                    for (SortOrder sortOrder : data.getSort_order()) {
                        try {
                            sort_Order_map.put(Integer.parseInt(sortOrder.getSortOrder()),
                                    sortOrder
                                            .getOrderType());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


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


            if (!TextUtils.isEmpty(configuration.getAdmob_banner_placemnt_id())) {
                MyApplication.app_editor.putString("admob_banner_placemnt_id", configuration
                        .getAdmob_banner_placemnt_id()).apply();

                String get_iscross_player = "";
                if (!TextUtils.isEmpty(configuration.getIscross_home())) {
                      *//*  MyApplication.app_editor.putString("iscross_home", configuration
                                .getIscross_home()).apply();*//*
                    get_iscross_player = configuration.getIscross_home();
                } else {
                    get_iscross_player = "";
                    *//*  MyApplication.app_editor.putString("iscross_home", "").apply();*//*
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




        *//*   progress_home.setVisibility(View.GONE);*//*


        try {
            parent_nubitview.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        *//*Here notifying the launcher to stop the loading & to start showing UI*//*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RefreshDataCallBack.getInstance().notifyOnRefreshFinish();
            }
        }, 1000);

        *//*All arrayList added to all adapters after parsing the data*//*
        addAdapter_to_View();


        hit_admob_mobin();
        setUp_MobinView();



        *//*Intialize navigation click listner*//*
        onNav_ItemSelected();

    }

    private void addAdapter_to_View() {

        // Sort Order logic start here
        add_sortedView();
        // Sort Order logic end here

        init_top_banner_view();
        init_other_services_view();
        init_service_view();
        init_TopNews_Views();
        init_Recom_News_Views();
        init_TopApps_Views();
        init_BottomNews_Views();
        init_Entertainment_Views();
        init_BottomAdv_Views();
        init_Games_Views();
        init_external_game_view();
        init_Wallpaper_Views();
        init_MiddleNews_Views();
        setUpNavigationViewData();
      *//*  init_TaboolaView();*//*
        init_Title_view();


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
            home_topBanner_Pager.setAdapter(_topBanner_adapter);
            _topBanner_adapter.notifyDataSetChanged();
            e.printStackTrace();
        }


        home_topBanner_Pager.setCurrentItem(0);
        home_topBanner_Pager.setOffscreenPageLimit(1);
        home_topBanner_Pager.getLayoutParams().height = (int) (MyUtility.getScreenWidth
                (home_topBanner_Pager.getContext()) / (1.4));

        topBanner_indicator.setViewPager(home_topBanner_Pager);
        home_topBanner_Pager.setOnTouchListener(new InterceptTouchListener(new
                                                                                   InterceptTouchListener.OnItemClickListener() {
                                                                                       @Override
                                                                                       public void
                                                                                       onItemClick(View v, int position) {
                                                                                           HomeVideoPojo top_banner_pojo;

                                                                                           if
                                                                                                   (updatedList != null && updatedList.size() > 0) {
                                                                                               top_banner_pojo = updatedList.get(position);
                                                                                           } else {
                                                                                               top_banner_pojo = arraylist_top_adver.get(position);
                                                                                           }


                                                                                           MyApplication.getInstance().trackEvent("Video", top_banner_pojo.getRedirect_link(), top_banner_pojo.getTitle());


                                                                                           if
                                                                                                   (!TextUtils.isEmpty(top_banner_pojo.getVideoTableType()) & top_banner_pojo.getVideoTableType()
                                                                                                   .equalsIgnoreCase("video")) {
                                                                                               try {
                                                                                                   PagersAutoScrollControllerCallback.getInstance().notify(false, "home", position);
                                                                                               }
                                                                                               catch (Exception e) {
                                                                                                   e.printStackTrace();
                                                                                               }
                                                                                        *//*
                                                                                        MyUtility
                                                                                        .saveTracksInDB(context, top_banner_pojo.getYoutube_link(),
                                                                                                       top_banner_pojo.getYoutube_link(), "Video", "0", top_banner_pojo.getTitle());*//*
                                                                                               MyUtility.playYouTubeVideo(context, top_banner_pojo.getYoutubeId(), top_banner_pojo.getYoutube_link(), top_banner_pojo.getVideoTableType(), top_banner_pojo.getTitle());
                                                                                           } else if
                                                                                                   (!TextUtils.isEmpty(top_banner_pojo.getVideoTableType()) & top_banner_pojo.getVideoTableType()
                                                                                                           .equalsIgnoreCase("other")) {
                                                                                               try {
                                                                                                   PagersAutoScrollControllerCallback.getInstance().notify(false, "home", position);
                                                                                               }
                                                                                               catch (Exception e) {
                                                                                                   e.printStackTrace();
                                                                                               }
                                                                                          *//*
                                                                                          MyUtility.saveTracksInDB(context, top_banner_pojo.getYoutube_link(),
                                                                                                       top_banner_pojo.getYoutube_link(), "Video", "0", top_banner_pojo.getTitle());*//*
                                                                                               Intent send = new Intent(context, PlayerActivity.class);
                                                                                               send.putExtra("videoUrl", top_banner_pojo.getYoutube_link());
                                                                                               send.putExtra("videoTitle", top_banner_pojo.getTitle());
                                                                                               send.putExtra("imageURL", top_banner_pojo.getBannerImage());
                                                                                               context.startActivity(send);
                                                                                           } else {

                                                                                               MyUtility.handleItemClick(context, top_banner_pojo.getPackageName(),
                                                                                                       top_banner_pojo.getRedirect_link(), top_banner_pojo.getThumb(), "Video", top_banner_pojo.getOpenWith(), top_banner_pojo.getTitle());
                                                                                           }
                                                                                       }
                                                                                   }

        ));
    }


    public void adddefaultView() {

        LayoutInflater inflator = LayoutInflater.from(context);
        View top_advertisement = inflator.inflate(R.layout.layout_top_banner, null);
        View other_services = inflator.inflate(R.layout.layout_other_services, null);
        View admob_rkt_one = inflator.inflate(R.layout.admob_rkt_one, null);
        View admob_one = inflator.inflate(R.layout.admob_nubit_one, null);
        View services = inflator.inflate(R.layout.layout_services, null);
        View news1 = inflator.inflate(R.layout.layout_news_top, null);
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


        layout_container_nubitview.addView(top_advertisement);
        layout_container_nubitview.addView(admob_rkt_one);
        layout_container_nubitview.addView(admob_one);
        layout_container_nubitview.addView(other_services);
        layout_container_nubitview.addView(services);
        layout_container_nubitview.addView(news1);
        layout_container_nubitview.addView(admob_rkt_two);
        layout_container_nubitview.addView(admob_two);
        layout_container_nubitview.addView(top_apps);
        layout_container_nubitview.addView(news2);
        layout_container_nubitview.addView(admob_rkt_three);
        layout_container_nubitview.addView(admob_three);
        layout_container_nubitview.addView(external_games);
        layout_container_nubitview.addView(entertainment);
        layout_container_nubitview.addView(news4);
        layout_container_nubitview.addView(games);
        layout_container_nubitview.addView(admob_rkt_four);
        layout_container_nubitview.addView(admob_four);
        layout_container_nubitview.addView(wallpaper);
        layout_container_nubitview.addView(bottom_advertisement);
        layout_container_nubitview.addView(taboola_view);
        layout_container_nubitview.addView(admob_rkt_five);
        layout_container_nubitview.addView(admob_five);
        layout_container_nubitview.addView(news3);

        layout_container_nubitview.addView(power_by);


        view_Map.put("top_advertisement", top_advertisement);
        view_Map.put("other_services", other_services);
        view_Map.put("admob_rkt_one", admob_rkt_one);
        view_Map.put("admob_one", admob_one);

        view_Map.put("services", services);
        view_Map.put("news1", news1);
        view_Map.put("admob_rkt_two", admob_rkt_two);
        view_Map.put("admob_two", admob_two);

        view_Map.put("entertainment", entertainment);
        view_Map.put("news4", news4);
        view_Map.put("admob_rkt_three", admob_rkt_three);
        view_Map.put("admob_three", admob_three);


        view_Map.put("top_apps", top_apps);
        view_Map.put("games", games);
        view_Map.put("flash_games", external_games);
        view_Map.put("admob_rkt_four", admob_rkt_four);


        view_Map.put("admob_four", admob_four);
        view_Map.put("wallpaper", wallpaper);
        view_Map.put("admob_rkt_five", admob_rkt_five);
        view_Map.put("admob_five", admob_five);


        view_Map.put("bottom_advertisement", bottom_advertisement);
        view_Map.put("news2", news2);
        view_Map.put("taboola_view", taboola_view);
        view_Map.put("news3", news3);
        view_Map.put("power_by", power_by);


    }


    private void init_other_services_view() {
        Other_Services_Adapter other_services_adapter = new Other_Services_Adapter(context,
                arraylist_other_ser);
        other_ser_recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false);
        other_ser_recyclerView.setLayoutManager(layoutManager);
        other_ser_recyclerView.setAdapter(other_services_adapter);

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
        Top_News_Adapter top_news_adapter = new Top_News_Adapter(context, arraylist_top_news);
        top_News_recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        top_News_recyclerView.setLayoutManager(layoutManager);
        top_News_recyclerView.setNestedScrollingEnabled(false);
        top_News_recyclerView.setAdapter(top_news_adapter);


    }

    private void init_Recom_News_Views() {
        Recom_News_Adapter reco_news_adapter = new Recom_News_Adapter(context,
                arraylist_recom_news);
        news_recom_recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        news_recom_recyclerView.setLayoutManager(layoutManager);
        news_recom_recyclerView.setNestedScrollingEnabled(false);
        news_recom_recyclerView.setAdapter(reco_news_adapter);


    }


    private void init_TopApps_Views() {

        TopAppsAdapter topApps_adapter = new TopAppsAdapter(context, arraylist_topApps);
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

                                                                                           final
                                                                                           TopApps_Pojo top_apps_pojo = arraylist_topApps.get(position);
                                                                                           MyApplication.getInstance().trackEvent("Apps", top_apps_pojo.getRedirect_link(), top_apps_pojo.getTitle());
                                                                                           MyUtility.handleItemClick(context, top_apps_pojo.getPackage_name(),
                                                                                                   top_apps_pojo.getRedirect_link(), top_apps_pojo.getBanner_thumb_image(), "Apps", top_apps_pojo.getOpen_with(), top_apps_pojo.getTitle());

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
        *//* entertain_recyclerView.addOnScrollListener(new CustomScrollListener());*//*


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

        GamesAdapter games_adapter = new GamesAdapter(context, arraylist_games);
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
                                                                                         final
                                                                                         GamesPojo games_pojo = arraylist_games.get(position);
                                                                                         MyApplication.getInstance().trackEvent("Games",
                                                                                                 games_pojo.getRedirect_link(), games_pojo.getTitle());

                                                                                         MyUtility.handleItemClick(context, games_pojo.getPackage_name(), games_pojo
                                                                                                         .getRedirect_link(), games_pojo.getBanner_thumb_image(), "Games",
                                                                                                 games_pojo.getOpen_with(), games_pojo.getTitle());
                                                                                     }
                                                                                 }));

    }

    private void init_external_game_view() {

        External_Game_Adapter external_Game_Adapter = new External_Game_Adapter(context,
                arraylist_Flashgames);
        external_games_recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false);
        external_games_recyclerView.setLayoutManager(layoutManager);
        external_games_recyclerView.setAdapter(external_Game_Adapter);

        external_games_recyclerView.setOnTouchListener(new InterceptTouchListener(new
                                                                                          InterceptTouchListener.OnItemClickListener() {
                                                                                              @Override
                                                                                              public void onItemClick(View v, int position) {
                                                                                                  External_game_pojo externalGame_Pojo = arraylist_Flashgames.get(position);
                                                                                                  MyApplication.getInstance().trackEvent("Html Games",
                                                                                                          externalGame_Pojo.getRedirect_link(), externalGame_Pojo.getTitle());
                                                                                                  MyUtility.handleItemClick(context, "", externalGame_Pojo.getRedirect_link()
                                                                                                          , externalGame_Pojo.getBanner_thumb_image(), "HTML Game",
                                                                                                          externalGame_Pojo.getOpen_with(), externalGame_Pojo.getTitle());
                                                                                              }
                                                                                          }));

    }

    private void init_Wallpaper_Views() {
        final Wallpaper_Adapter wallpaper_adapter = new Wallpaper_Adapter(context,
                arraylist_wallpaper);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false);
        wallpaper_recyclerView.setLayoutManager(layoutManager);
        wallpaper_recyclerView.setAdapter(wallpaper_adapter);

        wallpaper_recyclerView.setOnTouchListener(new InterceptTouchListener(new
                                                                                     InterceptTouchListener.OnItemClickListener() {
                                                                                         @Override
                                                                                         public void
                                                                                         onItemClick(View v, int position) {
                                                                                             final Wallpaper_Pojo wallpaper_pojo = arraylist_wallpaper.get(position);
                                                                                             MyApplication.getInstance().trackEvent("Wallpaper",
                                                                                                     wallpaper_pojo.getRedirect_link(), wallpaper_pojo.getTitle());
                                                                                             MyUtility.saveTracksInDB(context, arraylist_wallpaper.get(position)
                                                                                                             .getBanner_image(), arraylist_wallpaper.get(position).getRedirect_link(),
                                                                                                     "Wallpaper", "0", arraylist_wallpaper.get(position).getTitle(), "");

                                                                                             if
                                                                                                     (MyUtility.isConnectedToInternet()) {


                                                                                                 if (!TextUtils.isEmpty(wallpaper_pojo.getWallpaper_type())) {
                                                                                                     if ((wallpaper_pojo.getWallpaper_type()).equalsIgnoreCase("redirect")) {
                                                                                                         MyUtility.handleItemClick(context, wallpaper_pojo
                                                                                                                         .getPackage_name(), wallpaper_pojo.getRedirect_link(), wallpaper_pojo
                                                                                                                         .getRedirect_link(), "wallpaper", wallpaper_pojo.getOpen_with(),
                                                                                                                 getResources().getString(R.string.my_app_name));
                                                                                                     } else {

                                                                                                         Intent intent = new Intent(context, WallpaperActivity.class);
                                                                                                         intent.putExtra("positionkey", position);
                                                                                                         intent.putExtra("bundle", arraylist_wallpaper);
                                                                                                         context.startActivity(intent);
                                                                                                     }
                                                                                                 } else {

                                                                                                     Intent intent = new Intent(context, WallpaperActivity.class);
                                                                                                     intent.putExtra("positionkey", position);
                                                                                                     intent.putExtra("bundle", arraylist_wallpaper);
                                                                                                     context.startActivity(intent);
                                                                                                 }


                                                                                             }
                                                                                             else {
                                                                                                 MyUtility.NoInternet_Msg(context);
                                                                                             }
                                                                                         }
                                                                                     }));

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
        final Middle_News_Adapter middle_news_adapter = new Middle_News_Adapter(context,
                arraylist_middle_news);

        middleNews_recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false);
        middleNews_recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setNestedScrollingEnabled(false);
        middleNews_recyclerView.setAdapter(middle_news_adapter);

        middleNews_recyclerView.setOnTouchListener(new InterceptTouchListener(new
                                                                                      InterceptTouchListener.OnItemClickListener() {
                                                                                          @Override
                                                                                          public
                                                                                          void
                                                                                          onItemClick(View v, int position) {

                                                                                              Middle_News_Pojo middleNewsPojo = arraylist_middle_news.get(position);

                                                                                              MyUtility.handleNewsItemClick(context, middleNewsPojo.getRedirect_link(),
                                                                                                      middleNewsPojo.getOpen_with());
                                                                                              MyApplication.getInstance().trackEvent("Middle News", middleNewsPojo.getRedirect_link(), middleNewsPojo.getTitle());
                                                                                              MyUtility.handleItemClick(context, middleNewsPojo.getPackage_name(), middleNewsPojo.getRedirect_link(), middleNewsPojo.getImage(),
                                                                                                      "Middle News", middleNewsPojo.getOpen_with(), middleNewsPojo.getTitle());
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
                }, 6000);


            } else {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pro_nointernet.setVisibility(View.GONE);
                        MyUtility.NoInternet_Msg(context);
                    }
                }, 2000);


            }

        } else if (i == R.id.nav_icon) {
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

            MyUtility.rotateRefreshButton_Anim(context, img_wallpaper_ref);
            MyUtility.scroll_wallpaper_RecyclerView(context, wallpaper_recyclerView, 2);


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
      *//*  MyApplication.app_sharedPreferences.getString("key_modifiedDat","");
        MyApplication.app_editor.remove("key_modifiedDat");*//*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new HitGetData_forLanguage().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        } else {
            new HitGetData_forLanguage().execute();
        }
    }

    @Override
    public void onVideoCatSelection() {
        show_videoCat_popup(context, arraylist_videocat);
    }

    @Override
    public void updateScrollCallBack(boolean update, String callFrom, int position) {


        if (update == true) {
            if (shouldScroll == true) {
                start_Scroll();
                scrollStatus = "home";
            }

        } else {
            *//*  shouldScroll=false;*//*
            *//*     topBanner_indicator.setVisibility(View.VISIBLE);*//*
            stop_Scroll(callFrom, position);

            if (callFrom.equalsIgnoreCase("loading")) {
                *//*       topBanner_indicator.setVisibility(View.GONE);*//*
                scrollStatus = "loading";


            } else {
                scrollStatus = "home";
            }


        }
    }

    private void start_Scroll() {

        home_topBanner_Pager.startAutoScroll();


    }

    private void stop_Scroll(final String callFrom, final int position) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                home_topBanner_Pager.stopAutoScroll();

                if (callFrom.equalsIgnoreCase("adapter")) {
                    home_topBanner_Pager.setCurrentItem(position);
                }

            }
        }, 1000);

    }


 *//*   @Override
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

    }*//*


    class CheckServerForNewData_asyn extends AsyncTask<Void, Void, String> {


        String refreshMyView = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isAsyncTaskRunning = true;
            *//*  RefreshDataCallBack.getInstance().notifyOnRefreshStart();*//*

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
                        get_admob_app_id = jsonObject_getDat.getString("admob_app_id");

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

                *//*This block execute when we getting no response from getVersion API...It can be by
                nointernet or API problem....Lets check in below conditions if it is new user or
                have old saved data to showcase*//*


                if (TextUtils.isEmpty(MyApplication.app_sharedPreferences.getString
                        ("save_app_Json", ""))) {
                    *//*Clear saved json data and show the nointernet UI to handle the nointernet
                    connection, Because this is new user and dont have any previously saved data*//*
                    MyApplication.app_editor.clear().apply();
                    layout_container_nubitview.setVisibility(View.GONE);
                    nointernet_parent.setVisibility(View.VISIBLE);

                } else {
                    *//*Might be getversion request broken or no internet, but we have saved data
                    to showcase for now*//*

                    layout_container_nubitview.setVisibility(View.VISIBLE);
                    nointernet_parent.setVisibility(View.GONE);
                    parseData();
                    *//* shouldViewRefresh("no");*//*

                }

            } else if (TextUtils.isEmpty(refreshMyView) && !TextUtils.isEmpty(MyApplication
                    .app_sharedPreferences.getString
                            ("save_app_Json", ""))) {

                *//*This block execute when we dont find new data to refresh, but we have old data
                to show case, It can be case of repeated user*//*

                *//*shouldViewRefresh("no");*//*


            } else if (!TextUtils.isEmpty(refreshMyView) && refreshMyView.equalsIgnoreCase
                    ("yes")) {
                *//* shouldViewRefresh(refreshMyView);*//*
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
                    Glide.with(MyApplication.ctx).load(get_position.getDrawer_icon()).asBitmap()
                            .into
                                    (new
                                             SimpleTarget<Bitmap>() {
                                                 int z = finalNav_items;

                                                 @Override
                                                 public void onResourceReady(Bitmap resource,
                                                                             GlideAnimation
                                                                                     glideAnimation) {

                                                     menu.getItem(z).setIcon(new GlideBitmapDrawable
                                                             (getResources(), resource));
                                                 }
                                             });
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
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
            Glide.with(MyApplication.ctx).load(getProfile).asBitmap().thumbnail(0.5f)
                    .placeholder(R.drawable.placeholder_apps)
                    .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder_apps)
                    .into(profile_nav);
        }
        if (!TextUtils.isEmpty(getName)) {
            profile_name_nav.setText(getName);
        }

        profile_nav.setOnClickListener(new View.OnClickListener() {
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
                    btn_topapps_title.setText(diffLang.getTop_apps());
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
                    title_recommended_news.setText(diffLang.getNews4());
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
                    btn_externalGames_title.setText(diffLang.getFlash_games());
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

                btn_service_title.setVisibility(View.VISIBLE);
                devider_below_btnService.setVisibility(View.VISIBLE);

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
                o_service_layout_parent.setVisibility(View.GONE);
            } else {

                if (MyUtility.setShowHide_Container(titles.getOther_services())) {
                    o_service_layout_parent.setVisibility(View.GONE);
                } else {
                    o_service_layout_parent.setVisibility(View.VISIBLE);
                }

            }

            if (titles.getTop_advertisement() == null) {
                top_banner_parent.setVisibility(View.GONE);
            } else {
                top_banner_parent.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(titles.getNews1())) {
                btn_news_title.setVisibility(View.GONE);
                devider_below_new_ref.setVisibility(View.GONE);
            } else {
                btn_news_title.setVisibility(View.VISIBLE);
                devider_below_new_ref.setVisibility(View.VISIBLE);
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

                games_parent.setVisibility(View.VISIBLE);

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
                wallpaper_ref_layout.setVisibility(View.GONE);

            } else {
                wallpaper_ref_layout.setVisibility(View.VISIBLE);
            }
            if (titles.getWallpaper() == null) {
                wallpaper_parent.setVisibility(View.GONE);
            } else {

                wallpaper_parent.setVisibility(View.VISIBLE);
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
                btn_bottom_news_title.setVisibility(View.VISIBLE);
                devider_bottom_news_ref.setVisibility(View.VISIBLE);
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
                    o_service_layout_parent.setVisibility(View.GONE);
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
                    wallpaper_ref_layout.setVisibility(View.GONE);
                    wallpaper_parent.setVisibility(View.GONE);
                    middle_news_parent.setVisibility(View.GONE);
                    bottomNews_parent.setVisibility(View.GONE);
                    recomNewsParent_layout.setVisibility(View.GONE);
                    fram_layout_floater.setVisibility(View.GONE);
                    parent_taboola.setVisibility(View.GONE);

                }
            }


        } else

        {
            parent_admob_one.setVisibility(View.GONE);
            parent_admob_two.setVisibility(View.GONE);
            parent_admob_three.setVisibility(View.GONE);
            parent_admob_four.setVisibility(View.GONE);
            parent_admob_five.setVisibility(View.GONE);


            btn_service_title.setVisibility(View.GONE);

            o_service_layout_parent.setVisibility(View.GONE);
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
            wallpaper_ref_layout.setVisibility(View.GONE);
            wallpaper_parent.setVisibility(View.GONE);
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
*//*
            We dont want parse to many AdmobIDs, so for sake of simplicity, I am saving the 3rd
            Index
            Id in sharedpreference for showing the ads in YoupTV Page......So in short if
            admob_nubit array in getData
             have value at 3rd index then it should show ads on homepage somewhere and also in
             YUPTV Screen at bottom.*//*
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
        *//* adView.setAdUnitId("ca-app-pub-6136742319000358/2067197049");*//*

        if (TextUtils.isEmpty(admobSize)) {
            admobSize = "MEDIUM_RECTANGLE";
        }


        if (!TextUtils.isEmpty(admobId) && rel_Layout != null)

        {
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
           *//* AdRequest adRequest = new AdRequest.Builder().addTestDevice
                    ("68A6308D09F439DCC42E3F151794B949").build();
            adView.loadAd(adRequest);*//*

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


                    *//*  rel_Layout.setVisibility(View.GONE);*//*
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
        floater_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics
                .Color
                .TRANSPARENT));
        TextView txt_dialog_title = floater_dialog.findViewById(R.id.txt_dialog_title);
        ImageView popup_cross_button = floater_dialog.findViewById(R.id.popup_cross_button);
        fram_layout_floater.setVisibility(View.VISIBLE);
        fram_layout_floater.setBackgroundColor(Color.parseColor("#99000000"));
        txt_dialog_title.setText(title);


        if (!((Activity) context).isFinishing()) {
            floater_dialog.show();
        }




        *//* Log.d("MyList", String.valueOf(list));*//*


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
        popup_cross_button.setOnTouchListener(new View.OnTouchListener() {
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

        nubit_floater.setOnClickListener(new View.OnClickListener() {
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
            *//* ad_plus_floater_layout.setBackgroundColor(Color.TRANSPARENT);*//*
            floater_recyclerView.setVisibility(View.GONE);


        } else {
            nubit_floater.startAnimation(rotate_backward);
            fram_layout_floater.setBackgroundColor(Color.parseColor("#99000000"));
            *//*   ad_plus_floater_layout.setBackgroundColor(Color.parseColor("#99000000"));*//*
            floater_recyclerView.setVisibility(View.VISIBLE);


        }
    }


    public void show_publisher_popup(Context cc, ArrayList<NewPubPojo> list) {

        final Dialog
                publisher_dialog = new Dialog(cc);
        publisher_dialog.setContentView(R.layout.publisher_pop_layout);
        publisher_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color
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
        popup_cross_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
*//*
                String pub_name = MyApplication.app_sharedPreferences.getString("pub_name",
                        "");
                MyApplication.getInstance().trackEvent("News Publisher", pub_name, "News " +
                        "Publisher");*//*
                publisher_dialog.dismiss();

                return false;
            }
        });

        btn_pub_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              *//*  String pub_name = MyApplication.app_sharedPreferences.getString("pub_name",
                        "");
                MyApplication.getInstance().trackEvent("News Publisher", pub_name, "News " +
                        "Publisher");*//*
                publisher_dialog.dismiss();

            }
        });


    }

    public void show_videoCat_popup(Context cc, ArrayList<VideoCat> list) {

        final Dialog
                videoCat_dialog = new Dialog(cc);
        videoCat_dialog.setContentView(R.layout.publisher_pop_layout);
        videoCat_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color
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
        popup_cross_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
           *//*     String cat_title = MyApplication.app_sharedPreferences.getString("cat_title",
                        "");

                MyApplication.getInstance().trackEvent("Video Category", cat_title, "Video " +
                        "Category");
                init_top_banner_view();*//*
                videoCat_dialog.dismiss();

                return false;
            }
        });

        btn_videocat_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
       *//*         String cat_title = MyApplication.app_sharedPreferences.getString("cat_title",
                        "");

                MyApplication.getInstance().trackEvent("Video Category", cat_title, "Video " +
                        "Category");*//*
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

        try {
            layout_container_nubitview.addView(admob_rkt_one);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
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
        try {
            layout_container_nubitview.addView(admob_rkt_two);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
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
        try {
            layout_container_nubitview.addView(admob_rkt_three);
        } catch (Exception e1) {
            e1.printStackTrace();
        }


        try {
            if (view_Map.get(sortedMap.get(6)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(6)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            layout_container_nubitview.addView(admob_rkt_four);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        try {
            if (view_Map.get(sortedMap.get(7)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(7)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            layout_container_nubitview.addView(admob_rkt_five);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

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
        try {
            if (view_Map.get(sortedMap.get(11)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(11)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            if (view_Map.get(sortedMap.get(12)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(12)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            if (view_Map.get(sortedMap.get(13)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(13)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            if (view_Map.get(sortedMap.get(14)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(14)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            if (view_Map.get(sortedMap.get(15)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(15)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            if (view_Map.get(sortedMap.get(16)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(16)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            if (view_Map.get(sortedMap.get(17)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(17)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            if (view_Map.get(sortedMap.get(18)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(18)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            if (view_Map.get(sortedMap.get(19)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(19)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            if (view_Map.get(sortedMap.get(20)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(20)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            if (view_Map.get(sortedMap.get(21)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(21)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            if (view_Map.get(sortedMap.get(22)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(22)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            if (view_Map.get(sortedMap.get(23)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(23)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            if (view_Map.get(sortedMap.get(24)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(24)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        try {
            if (view_Map.get(sortedMap.get(25)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(25)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        try {
            if (view_Map.get(sortedMap.get(26)) != null) {
                layout_container_nubitview.addView(view_Map.get(sortedMap.get(26)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

  *//*  private static TestNubitView nubitView;

    public static TestNubitView getInstance(Context context) {
        if (nubitView == null) {
            nubitView = new TestNubitView(context);
        }

        return nubitView;
    }*//*

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
            s.setAppCachePath(context.getCacheDir().getPath());
            s.setAppCacheEnabled(true);
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

    private void init_TaboolaView() {


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                try {

                    LayoutParams params = new LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT
                    );

                    params.setMargins(23, 10, 23, 10);
                    taboolaWidget.setLayoutParams(params);

                    parent_taboola.addView(taboolaWidget);
                    taboolaWidget.setPublisher("nubit-nubitappsdk")
                            *//*  .setMode("thumbnails-a")
                              .setPlacement("SDK App Minus One Screen SC")
                              .setPageUrl("https://www.nubit.com")
                              .setPageType("Category")
                              .setPageId("https://www.nubit.com"); //default value is the relative path of
                      // the pageUrl provided.*//*

                            .setMode("thumbnails-a")
                            .setPlacement("SDK App Minus One Screen SC")
                            .setPageType("category")
                            .setPageId("https://www.nubit.com")
                            .setPageUrl("https://www.nubit.com");


                    taboolaWidget.fetchContent();


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        },4000);




    }

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
                                com.amazonaws.com.google.gson.Gson gson = new com.amazonaws.com
                                        .google.gson.Gson();
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


    *//**
     * prepare jsonObject for analytics data
     *//*
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
                jarrayOBJ.put("device_id", MyUtility.getDeviceID(context));
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

}*/

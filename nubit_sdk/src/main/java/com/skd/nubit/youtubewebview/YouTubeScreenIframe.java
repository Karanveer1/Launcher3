package com.skd.nubit.youtubewebview;

import android.content.Context;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.skd.nubit.R;
import com.skd.nubit.adapters.YouTubeScreen_Adapter;
import com.skd.nubit.models.Data;
import com.skd.nubit.models.HomeVideoPojo;
import com.skd.nubit.models.Response;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;
import com.skd.nubit.utilityclasses.RecyclerItemClickListener;

import java.util.ArrayList;

public class YouTubeScreenIframe extends AppCompatActivity {

    private VideoEnabledWebView webView;
    private VideoEnabledWebChromeClient webChromeClient;


    String get_clicked_VideoID, videoUrl, videoTitle;
    RecyclerView youtube_recyclerView;


    View nonVideoLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_utube_screen_iframe);


        try {

            AudioManager mAudioManager = (AudioManager) getApplicationContext().getSystemService
                    (Context.AUDIO_SERVICE);
            MyUtility.UnmuteDeviceAudio(mAudioManager);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MyApplication.getInstance().trackScreenView("YouTube Chanel Screen");


        nonVideoLayout = findViewById(R.id.nonVideoLayout); // Your own view, read class
        // comments
        ViewGroup videoLayout = findViewById(R.id.videoLayout); // Your own view,
        // read class comments
        View loadingView = getLayoutInflater().inflate(R.layout.youtube_loadingview, null); //
        // Your own view, read class comments

        webView = findViewById(R.id.webView);


        try {

            AudioManager mAudioManager = (AudioManager) getApplicationContext().getSystemService
                    (Context.AUDIO_SERVICE);
            MyUtility.UnmuteDeviceAudio(mAudioManager);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MyApplication.getInstance().trackScreenView("YouTube Screen");
        get_clicked_VideoID = getIntent().getStringExtra("get_YouTubeID");
        videoUrl = getIntent().getStringExtra("videoUrl");
        videoTitle = getIntent().getStringExtra("videoTitle");


        MyApplication.getInstance().trackScreenView("YouTube Chanel Screen");
        get_clicked_VideoID = getIntent().getStringExtra("get_YouTubeID");


        videoUrl = getIntent().getStringExtra("videoUrl");
        videoTitle = getIntent().getStringExtra("videoTitle");


        youtube_recyclerView = findViewById(R.id.youtube_recyclerView);


        parseData();

        webChromeClient = new VideoEnabledWebChromeClient(nonVideoLayout, videoLayout,
                loadingView, webView) // See all available constructors...
        {
            // Subscribe to standard events, such as onProgressChanged()...
            @Override
            public void onProgressChanged(WebView view, int progress) {
//                View decorView = getWindow().getDecorView();
//                decorView.setSystemUiVisibility(
//                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
//                                | View.SYSTEM_UI_FLAG_IMMERSIVE);

            }
        };
        webChromeClient.setOnToggledFullscreen(new VideoEnabledWebChromeClient
                .ToggledFullscreenCallback() {
            @Override
            public void toggledFullscreen(boolean fullscreen) {
                // Your code to handle the full-screen change, for example showing and hiding the
                // title bar. Example:
                if (fullscreen) {
                    WindowManager.LayoutParams attrs = getWindow().getAttributes();
                    attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getWindow().setAttributes(attrs);
                    if (android.os.Build.VERSION.SDK_INT >= 14) {
                        //noinspection all
                        getWindow().getDecorView().setSystemUiVisibility(
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
                    }
                } else {
                    WindowManager.LayoutParams attrs = getWindow().getAttributes();
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getWindow().setAttributes(attrs);
                    if (android.os.Build.VERSION.SDK_INT >= 14) {
                        //noinspection all
                        getWindow().getDecorView().setSystemUiVisibility(View
                                .SYSTEM_UI_FLAG_LOW_PROFILE);
//
                    }
                }

            }
        });
        webView.setWebChromeClient(webChromeClient);

     /*   webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.getSettings().setAllowContentAccess(false);*/
        // Call private class InsideWebViewClient
        webView.setWebViewClient(new InsideWebViewClient());

        // Navigate anywhere you want, but consider that this classes have only been tested on
        // YouTube's mobile site
//        webView.loadUrl("http://m.youtube.com");

        initIframeView("https://www.youtube.com/embed/" + get_clicked_VideoID+"?playlist="+get_clicked_VideoID);


    }

    private void initIframeView(String myUrl) {


        String html = "<html><head><meta charset=\"utf-8\"><meta http-equiv=\"X-UA-Compatible\" " +
                "content=\"IE=edge\"><meta name=\"viewport\" content=\"width=device-width, " +
                "initial-scale=1\"><style>body{padding:0px;margin:0px;background-color:#000;" +
                "font-size:50%}img{width:100%}" +
                ".videoWrapper{position:relative;padding-bottom:0px;padding-top:0px;height:270px}" +
                ".videoWrapper iframe{position:absolute;top:0;left:0;width:100%;bottom:0;" +
                "height:100%}@media screen and (-webkit-min-device-pixel-ratio: 0)" +
                "{body{word-break:break-word}}</style></head><body><div " +
                "class=\"videoWrapper\"><iframe   src='" + myUrl + "' allowfullscreen " +
                "frameborder=\"0\"></iframe></div></body></html>";
        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }

    private class InsideWebViewClient extends WebViewClient {
        @Override
        // Force links to be opened inside WebView and not in Default Browser
        // Thanks http://stackoverflow.com/a/33681975/1815624
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            emulateClick(view);
        }
    }

    private void emulateClick(final WebView webview) {

        long delta = 100;
        long downTime = SystemClock.uptimeMillis();
        float x = webview.getLeft() + webview.getWidth() / 2; //in the middle of the webview
        float y = webview.getTop() + webview.getHeight() / 2;

        final MotionEvent downEvent = MotionEvent.obtain(downTime, downTime + delta, MotionEvent
                .ACTION_DOWN, x, y, 0);
        // change the position of touch event, otherwise, it'll show the menu.
        final MotionEvent upEvent = MotionEvent.obtain(downTime, downTime + delta, MotionEvent
                .ACTION_UP, x + 10, y + 10, 0);

        webview.post(new Runnable() {
            @Override
            public void run() {
                if (webview != null) {
                    webview.dispatchTouchEvent(downEvent);
                    webview.dispatchTouchEvent(upEvent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Notify the VideoEnabledWebChromeClient, and handle it ourselves if it doesn't handle it
        if (!webChromeClient.onBackPressed()) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                // Standard back button implementation (for example this could close the app)
                super.onBackPressed();
            }
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int i = newConfig.orientation;
        getResources().getConfiguration();
        if (i == 1) {
            WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
            attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
            getWindow().setAttributes(attrs);
            if (android.os.Build.VERSION.SDK_INT >= 14) {
                //noinspection all
                getWindow().getDecorView().setSystemUiVisibility(View
                        .SYSTEM_UI_FLAG_LOW_PROFILE);
//
            }

        } else {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }


    private void parseData() {


        // In mean time check if data already with you....show case that data to user.

        ArrayList arraylist_YouTube = new ArrayList<>();


        String get_SavedJson = MyApplication.app_sharedPreferences.getString
                ("save_app_Json",
                        "");
        if (!TextUtils.isEmpty(get_SavedJson)) {
            Gson gson = new Gson();
            Response response = gson.fromJson(get_SavedJson, Response.class);
            Data data = response.getData();

            try {
                arraylist_YouTube.addAll(data.getChanel_videos());
            } catch (Exception e) {
                e.printStackTrace();
            }


            /*All arrayList added to all adapters after parsing the data*/

            if (arraylist_YouTube != null && arraylist_YouTube.size() > 0) {
                addAdapter_to_View(arraylist_YouTube);

            }


        }


    }

    private void addAdapter_to_View(final ArrayList<HomeVideoPojo> arrayList) {


        YouTubeScreen_Adapter youTubeScreen_adapter = new YouTubeScreen_Adapter(YouTubeScreenIframe
                .this, arrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(YouTubeScreenIframe.this,
                LinearLayoutManager.VERTICAL, false);

        youtube_recyclerView.setLayoutManager(layoutManager);
        youtube_recyclerView.setAdapter(youTubeScreen_adapter);

        youtube_recyclerView.addOnItemTouchListener(new RecyclerItemClickListener
                (YouTubeScreenIframe
                        .this, new RecyclerItemClickListener.OnItemClickListener() {


                    @Override
                    public void onItemClick(View view, int position) {

                        HomeVideoPojo homeVideoPojo = arrayList.get(position);
                        get_clicked_VideoID = homeVideoPojo.getYoutube_id();
                        videoUrl = homeVideoPojo.getYoutube_link();
                        videoTitle = homeVideoPojo.getTitle();



                        MyApplication.getInstance().trackEvent("Video Channel", homeVideoPojo
                                .getRedirect_link(), homeVideoPojo.getTitle());

                        /*     if (shouldTrack) {*/
                        try {

                            MyUtility.saveTracksInDB(YouTubeScreenIframe.this, videoUrl,
                                    videoUrl, "Video Channel", "0", videoTitle, "");
                            /*   shouldTrack = false;*/
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        /*  }*/




                        if (!TextUtils.isEmpty(homeVideoPojo.getVideo_table_type()) && homeVideoPojo
                                .getVideo_table_type()
                                .equalsIgnoreCase("video")) {

                            initIframeView("https://www.youtube.com/embed/" + get_clicked_VideoID+"?playlist="+get_clicked_VideoID);

                        } else {

                            MyUtility.handleItemClick(YouTubeScreenIframe.this, homeVideoPojo
                                            .getPackage_name(),
                                    homeVideoPojo.getRedirect_link(), homeVideoPojo.getThumb(),
                                    "Video " +
                                            "Chanel", homeVideoPojo.getOpen_with(), homeVideoPojo
                                            .getTitle
                                                    ());
                        }


                    }
                }));

    }


}

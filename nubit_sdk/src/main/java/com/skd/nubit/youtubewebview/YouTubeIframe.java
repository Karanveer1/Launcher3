package com.skd.nubit.youtubewebview;

import android.content.Context;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.skd.nubit.R;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

public class YouTubeIframe extends AppCompatActivity {

    private VideoEnabledWebView webView;
    private VideoEnabledWebChromeClient webChromeClient;

    String getAdType, getAdID;
    String get_clicked_VideoID, videoUrl, videoTitle;
    RelativeLayout admob_youtube;


    View nonVideoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_webview);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

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


        admob_youtube = findViewById(R.id.admob_youtube);


        getAdType = MyApplication.app_sharedPreferences.getString("admob_u_type", "");
        getAdID = MyApplication.app_sharedPreferences.getString("admob_u_rkt", "");

        getAdType = "MEDIUM_RECTANGLE";
        getAdID = "ca-app-pub-3940256099942544/6300978111";


        if (TextUtils.isEmpty(getAdID)) {
            getAdType = MyApplication.app_sharedPreferences.getString("uTube_AdType", "");
            getAdID = MyApplication.app_sharedPreferences.getString("uTube_AdId", "");
        }


        if (!TextUtils.isEmpty(getAdType) && !TextUtils.isEmpty(getAdID)) {

            admob_youtube.setVisibility(View.VISIBLE);
            if (getAdType.equalsIgnoreCase("MEDIUM_RECTANGLE")) {
                RelativeLayout.LayoutParams layoutParams =
                        (RelativeLayout.LayoutParams) nonVideoLayout.getLayoutParams();
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                nonVideoLayout.setLayoutParams(layoutParams);
            } else {
                RelativeLayout.LayoutParams layoutParams =
                        (RelativeLayout.LayoutParams) nonVideoLayout.getLayoutParams();
                layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                nonVideoLayout.setLayoutParams(layoutParams);
            }
            setUpBannerAd(getAdID);
        } else {
            admob_youtube.setVisibility(View.GONE);
            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) nonVideoLayout.getLayoutParams();
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            nonVideoLayout.setLayoutParams(layoutParams);
        }


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

        String myUrl = "https://www.youtube.com/embed/" + get_clicked_VideoID+"?playlist="+get_clicked_VideoID;


  /*      String myUrl = "https://www.youtube.com/watch?v=" + get_clicked_VideoID;*/


        String html = "<html><head><meta charset=\"utf-8\"><meta http-equiv=\"X-UA-Compatible\" " +
                "content=\"IE=edge\"><meta name=\"viewport\" content=\"width=device-width, " +
                "initial-scale=1\"><style>body{padding:0px;margin:0px;background-color:#000;" +
                "font-size:50%}img{width:100%}" +
                ".videoWrapper{position:relative;padding-bottom:0px;padding-top:0px;height:300px}" +
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

    private void setUpBannerAd(String bannerPlacementId) {
        final AdView adView = new AdView(this);
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
        }

        adView.setId(R.id.adViews);
        adView.loadAd(new AdRequest.Builder().build());
        admob_youtube.setVisibility(View.VISIBLE);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                // System.out.println(TAG + " onAdFailedToLoad " + i);
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
                        admob_youtube.addView(adView);

                    } else {
                        ((ViewGroup) adView.getParent()).removeView(adView);

                        admob_youtube.addView(adView);

                    }
                    // System.out.println(TAG + " banneraddloaded");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}

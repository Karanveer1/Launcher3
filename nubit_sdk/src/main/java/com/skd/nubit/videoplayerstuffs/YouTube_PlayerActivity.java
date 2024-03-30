package com.skd.nubit.videoplayerstuffs;

import android.content.Context;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.skd.nubit.R;
import com.skd.nubit.mycallbacks.PagersAutoScrollControllerCallback;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

public class YouTube_PlayerActivity extends YouTubeBaseActivity implements YouTubePlayer
        .OnInitializedListener, YouTubePlayer.OnFullscreenListener, YouTubePlayer
        .PlayerStateChangeListener, YouTubePlayer.PlaybackEventListener {
    String id;
    YouTubePlayer mYouTubePlayer;
    YouTubePlayerView youtube_view;

    RelativeLayout admob_youtube;

    boolean shouldTrack;


    String getAdType, getAdID;
    String get_clicked_VideoID, videoUrl, videoTitle;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_you_tube);

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
        youtube_view = findViewById(R.id.youtube_view);


        getAdType = MyApplication.app_sharedPreferences.getString("admob_u_type", "");
        getAdID = MyApplication.app_sharedPreferences.getString("admob_u_rkt", "");


        if (TextUtils.isEmpty(getAdID)) {
            getAdType = MyApplication.app_sharedPreferences.getString("uTube_AdType", "");
            getAdID = MyApplication.app_sharedPreferences.getString("uTube_AdId", "");
        }


        if (!TextUtils.isEmpty(getAdType) && !TextUtils.isEmpty(getAdID)) {

            admob_youtube.setVisibility(View.VISIBLE);
            if (getAdType.equalsIgnoreCase("MEDIUM_RECTANGLE")) {
                RelativeLayout.LayoutParams layoutParams =
                        (RelativeLayout.LayoutParams) youtube_view.getLayoutParams();
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                youtube_view.setLayoutParams(layoutParams);
            } else {
                RelativeLayout.LayoutParams layoutParams =
                        (RelativeLayout.LayoutParams) youtube_view.getLayoutParams();
                layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                youtube_view.setLayoutParams(layoutParams);
            }
            setUpBannerAd(getAdID);
        } else {
            admob_youtube.setVisibility(View.GONE);
            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) youtube_view.getLayoutParams();
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            youtube_view.setLayoutParams(layoutParams);
        }

        this.youtube_view.initialize(MyApplication.app_sharedPreferences.getString
                ("youtube_key", ""), this);


    }


    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer
            youTubePlayer, boolean wasRestored) {
        if (!TextUtils.isEmpty(get_clicked_VideoID)) {
            this.mYouTubePlayer = youTubePlayer;
            this.mYouTubePlayer.setOnFullscreenListener(this);
            this.mYouTubePlayer.setPlayerStateChangeListener(this);
            this.mYouTubePlayer.setPlaybackEventListener(this);
            youTubePlayer.addFullscreenControlFlag(8);
            if (!wasRestored) {
                youTubePlayer.cueVideo(get_clicked_VideoID);
            }


            /*      Below line is used for forceing the Youtube player to work in portrate mode
            Only....It hide full screen scale button only*/
            /* mYouTubePlayer.setShowFullscreenButton(false);*/
        }
    }

    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult youTubeInitializationResult) {
    }

    public void onFullscreen(boolean b) {


        // getApplicationContext().setTheme(android.R.style
        // .Theme_DeviceDefault_Light_NoActionBar_Fullscreen);

        //  Log.d("Full Screen","Fired");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            PagersAutoScrollControllerCallback.getInstance().notify(true, "home", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();

    }

    public void onLoading() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("rkt_youtube", "onDestroy");

        if (shouldTrack) {
            try {

                MyUtility.saveTracksInDB(YouTube_PlayerActivity.this, videoUrl,
                        videoUrl, "Video", "0", videoTitle, String.valueOf((mYouTubePlayer.getCurrentTimeMillis()) / 1000));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public void onLoaded(String s) {
        this.mYouTubePlayer.play();

    }

    public void onAdStarted() {
    }

    public void onVideoStarted() {
        Log.d("rkt_youtube", "onVideoStarted");
        shouldTrack = true;
    }

    public void onVideoEnded() {

        try {

            MyUtility.saveTracksInDB(YouTube_PlayerActivity.this, videoUrl,
                    videoUrl, "Video", "0", videoTitle, String.valueOf((mYouTubePlayer.getCurrentTimeMillis()) / 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        shouldTrack = false;
        Log.d("rkt_youtube", "onVideoEnded");

    }

    public void onError(YouTubePlayer.ErrorReason errorReason) {

        /*   Log.d("YouTube error",errorReason.toString());*/

    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int i = newConfig.orientation;
        getResources().getConfiguration();
        if (i == 1) {
            admob_youtube.setVisibility(View.VISIBLE);

            this.mYouTubePlayer.setFullscreen(false);
        } else {
            admob_youtube.setVisibility(View.GONE);

            this.mYouTubePlayer.setFullscreen(true);
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

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {
     /*   try {

            MyUtility.saveTracksInDB(YouTube_PlayerActivity.this, videoUrl,
                    videoUrl, "Video", "0", videoTitle, String.valueOf((mYouTubePlayer.getCurrentTimeMillis()) / 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        shouldTrack = false;*/

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();
            try {

            MyUtility.saveTracksInDB(YouTube_PlayerActivity.this, videoUrl,
                    videoUrl, "Video", "0", videoTitle, String.valueOf((mYouTubePlayer.getCurrentTimeMillis()) / 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        shouldTrack = false;
    }
}

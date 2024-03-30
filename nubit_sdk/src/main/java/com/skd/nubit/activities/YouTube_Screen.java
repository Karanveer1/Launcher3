package com.skd.nubit.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.skd.nubit.R;

import com.skd.nubit.adapters.YouTubeScreen_Adapter;
import com.skd.nubit.models.Data;
import com.skd.nubit.models.HomeVideoPojo;
import com.skd.nubit.models.Response;
import com.skd.nubit.mycallbacks.PagersAutoScrollControllerCallback;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;
import com.skd.nubit.utilityclasses.RecyclerItemClickListener;

import java.util.ArrayList;

public class YouTube_Screen extends YouTubeBaseActivity implements YouTubePlayer
        .OnInitializedListener, YouTubePlayer.OnFullscreenListener, YouTubePlayer
        .PlayerStateChangeListener, YouTubePlayer.PlaybackEventListener {

    YouTubePlayer mYouTubePlayer;
    YouTubePlayerView youtube_view;

    RecyclerView youtube_recyclerView;

    boolean shouldTrack;


    String get_clicked_VideoID, videoUrl, videoTitle;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_youtube_screen);


        try {

            AudioManager mAudioManager = (AudioManager) getApplicationContext().getSystemService
                    (Context.AUDIO_SERVICE);
            MyUtility.UnmuteDeviceAudio(mAudioManager);
        } catch (Exception e) {
            e.printStackTrace();
        }


        MyApplication.getInstance().trackScreenView("YouTube Chanel Screen");
        get_clicked_VideoID = getIntent().getStringExtra("get_YouTubeID");


        videoUrl = getIntent().getStringExtra("videoUrl");
        videoTitle = getIntent().getStringExtra("videoTitle");


        youtube_recyclerView = findViewById(R.id.youtube_recyclerView);
        youtube_view = findViewById(R.id.youtube_view);


        initYouTube();
        parseData();


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

                MyUtility.saveTracksInDB(YouTube_Screen.this, videoUrl,
                        videoUrl, "Video Channel", "0", videoTitle, String.valueOf((mYouTubePlayer
                                .getCurrentTimeMillis()) / 1000));
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

        shouldTrack = true;
    }

    public void onVideoEnded() {

        try {

            MyUtility.saveTracksInDB(YouTube_Screen.this, videoUrl,
                    videoUrl, "Video Channel", "0", videoTitle, String.valueOf((mYouTubePlayer
                            .getCurrentTimeMillis()) / 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        shouldTrack = false;


    }

    public void onError(YouTubePlayer.ErrorReason errorReason) {

        /*   Log.d("YouTube error",errorReason.toString());*/

    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int i = newConfig.orientation;
        getResources().getConfiguration();
        if (i == 1) {
            youtube_recyclerView.setVisibility(View.VISIBLE);

            this.mYouTubePlayer.setFullscreen(false);
        } else {
            youtube_recyclerView.setVisibility(View.GONE);

            this.mYouTubePlayer.setFullscreen(true);
        }
    }


    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {


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

            MyUtility.saveTracksInDB(YouTube_Screen.this, videoUrl,
                    videoUrl, "Video Channel", "0", videoTitle, String.valueOf((mYouTubePlayer
                            .getCurrentTimeMillis()) / 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        shouldTrack = false;
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


        YouTubeScreen_Adapter youTubeScreen_adapter = new YouTubeScreen_Adapter(YouTube_Screen
                .this, arrayList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(YouTube_Screen.this,
                LinearLayoutManager.VERTICAL, false);

        youtube_recyclerView.setLayoutManager(layoutManager);
        youtube_recyclerView.setAdapter(youTubeScreen_adapter);

        youtube_recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(YouTube_Screen
                .this, new RecyclerItemClickListener.OnItemClickListener() {


            @Override
            public void onItemClick(View view, int position) {

                HomeVideoPojo homeVideoPojo = arrayList.get(position);
                get_clicked_VideoID = homeVideoPojo.getYoutube_id();
                videoUrl = homeVideoPojo.getYoutube_link();
                videoTitle = homeVideoPojo.getTitle();

                MyApplication.getInstance().trackEvent("Video Channel", homeVideoPojo
                        .getRedirect_link(), homeVideoPojo.getTitle());

                if (shouldTrack) {
                    try {

                        MyUtility.saveTracksInDB(YouTube_Screen.this, videoUrl,
                                videoUrl, "Video Channel", "0", videoTitle, String.valueOf(
                                        (mYouTubePlayer
                                                .getCurrentTimeMillis()) / 1000));
                        shouldTrack = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                if (!TextUtils.isEmpty(homeVideoPojo.getVideo_table_type()) && homeVideoPojo
                        .getVideo_table_type()
                        .equalsIgnoreCase("video")) {

                    if (mYouTubePlayer != null) {
                        /*  mYouTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);*/
                        mYouTubePlayer.loadVideo(get_clicked_VideoID);
                        mYouTubePlayer.play();


                    }


                } else {

                    MyUtility.handleItemClick(YouTube_Screen.this, homeVideoPojo.getPackage_name(),
                            homeVideoPojo.getRedirect_link(), homeVideoPojo.getThumb(), "Video " +
                                    "Chanel", homeVideoPojo.getOpen_with(), homeVideoPojo.getTitle
                                    ());
                }


            }
        }));

    }


    private void initYouTube() {
        this.youtube_view.initialize(MyApplication.app_sharedPreferences.getString
                ("youtube_key", ""), this);
    }


}

package com.skd.nubit.videoplayerstuffs;

import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.skd.nubit.R;
import com.skd.nubit.mycallbacks.PagersAutoScrollControllerCallback;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;


public class PlayerActivity extends AppCompatActivity implements UniversalVideoView
        .VideoViewCallback, View.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";
    /*  private static final String VIDEO_URL = "http://clips.vorwaerts-gmbh.de/big_buck_bunny
    .mp4";*/

    /* String VIDEO_URL = "http://leftynew.s3.amazonaws.com/1535627142school_child.mp4";*/

    /*  String videoURL = "http://leftynew.s3.amazonaws.com/1535628519test_video.mp4";*/
    /*String VIDEO_URL="http://leftynew.s3.amazonaws.com/1535634719laughing_shirt.mp4";*/

    UniversalVideoView mVideoView;
    UniversalMediaController mMediaController;

    View mVideoLayout;
    ImageView img_video_share;
    RelativeLayout admob_player;
    private int mSeekPosition;
    private int cachedHeight;
    private boolean isFullscreen;
    String videoUrl, imageURL,videoTitle;
    Boolean fromNotification;
    ImageView back_button;
    String getAdType, getAdID;
    ImageView ad_cross;
    boolean shouldTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                    .LayoutParams.FLAG_FULLSCREEN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.layout_universal);

        MyApplication.getInstance().trackScreenView("Video Player Screen");


        getAdType = MyApplication.app_sharedPreferences.getString("admob_u_type", "");
        getAdID = MyApplication.app_sharedPreferences.getString("admob_u_rkt", "");


        if (TextUtils.isEmpty(getAdID)) {
            getAdType = MyApplication.app_sharedPreferences.getString("uTube_AdType", "");
            getAdID = MyApplication.app_sharedPreferences.getString("uTube_AdId", "");
        }



        String get_iscross_player = MyApplication.app_sharedPreferences.getString
                ("iscross_player", "");


        mVideoLayout = findViewById(R.id.video_layout);
        admob_player = findViewById(R.id.admob_player);
        back_button = findViewById(R.id.back_button);
        ad_cross = findViewById(R.id.ad_cross);

        mVideoView = findViewById(R.id.videoView);
        mMediaController = findViewById(R.id.media_controller);
        img_video_share = findViewById(R.id.img_video_share);

        try {
            // hiding because it is not required to have this on player Activity.....but required for home screen when auto play
            mMediaController.hide_img_audio_controller();
            mMediaController.UnmuteAudio();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!TextUtils.isEmpty(getAdType) && !TextUtils.isEmpty(getAdID)) {
            MyUtility.setUpBannerAd(PlayerActivity.this, getAdID, admob_player, getAdType,
                    ad_cross, get_iscross_player);
        }

        back_button.setOnClickListener(this);
        ad_cross.setOnClickListener(this);
        img_video_share.setOnClickListener(this);
        mVideoView.setMediaController(mMediaController);
        setVideoAreaSize();
        mVideoView.setVideoViewCallback(this);


        videoUrl = getIntent().getStringExtra("videoUrl");
        imageURL = getIntent().getStringExtra("imageURL");
        videoTitle= getIntent().getStringExtra("videoTitle");
        fromNotification = getIntent().getBooleanExtra("fromNotification", false);

        if (!TextUtils.isEmpty(videoUrl)) {

            /*  mVideoView.setVideoPath(videoUrl);*/


            if (mSeekPosition > 0) {
                mVideoView.seekTo(mSeekPosition);
            }
            mVideoView.start();
        }


        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("rkt_youtube", "onDestroy");


                    try {

                        MyUtility.saveTracksInDB(PlayerActivity.this, videoUrl,
                                videoUrl, "Video", "0", videoTitle, String.valueOf(((mVideoView.getCurrentPosition())/1000)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
              shouldTrack=false;

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause ");
        if (mVideoView != null && mVideoView.isPlaying()) {
            mSeekPosition = mVideoView.getCurrentPosition();
            Log.d(TAG, "onPause mSeekPosition=" + mSeekPosition);
            mVideoView.pause();
        }

        try {

                 MyUtility.saveTracksInDB(PlayerActivity.this, videoUrl,
                    videoUrl, "Video", "0", videoTitle,String.valueOf((mVideoView.getCurrentPosition())/1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        shouldTrack = false;
    }


    private void setVideoAreaSize() {
        mVideoLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = mVideoLayout.getWidth();
                /*cachedHeight = (int) (width * 405f / 720f);*/
//                cachedHeight = (int) (width * 3f / 4f);
//                cachedHeight = (int) (width * 9f / 16f);
                ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                mVideoLayout.setLayoutParams(videoLayoutParams);
                mVideoView.setVideoPath(videoUrl);
                mVideoView.requestFocus();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("rkt_youtube", "onDestroy");

        if (shouldTrack) {
            try {

                MyUtility.saveTracksInDB(PlayerActivity.this, videoUrl,
                        videoUrl, "Video", "0", videoTitle, String.valueOf(((mVideoView.getCurrentPosition())/1000)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState Position=" + mVideoView.getCurrentPosition());
        outState.putInt(SEEK_POSITION_KEY, mSeekPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        mSeekPosition = outState.getInt(SEEK_POSITION_KEY);
        Log.d(TAG, "onRestoreInstanceState Position=" + mSeekPosition);
    }


    @Override
    public void onScaleChange(boolean isFullscreen) {
        this.isFullscreen = isFullscreen;
        if (isFullscreen) {
            back_button.setVisibility(View.GONE);
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mVideoLayout.setLayoutParams(layoutParams);


        } else {
            back_button.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mVideoLayout.setLayoutParams(layoutParams);

        }

        switchTitleBar(!isFullscreen);
    }

    private void switchTitleBar(boolean show) {
        androidx.appcompat.app.ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            if (show) {
                supportActionBar.show();
            } else {
                supportActionBar.hide();
            }
        }
    }

    @Override
    public void onPause(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onPause UniversalVideoView callback");
    }

    @Override
    public void onStart(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onStart UniversalVideoView callback");
    }

    @Override
    public void onBufferingStart(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onBufferingStart UniversalVideoView callback");
    }

    @Override
    public void onBufferingEnd(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onBufferingEnd UniversalVideoView callback");
    }

    @Override
    public void onBackPressed() {
        try {
            PagersAutoScrollControllerCallback.getInstance().notify(true, "home", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.isFullscreen) {
            mVideoView.setFullscreen(false);
        } else {
            super.onBackPressed();
        }


    }

    @Override
    public void onClick(View v) {


        int i = v.getId();
        if (i == R.id.ad_cross) {

            MyApplication.getInstance().trackEvent("Ads Closed ByUser", "Closed By Cross " +
                            "Button",
                    "Device ID : " + MyUtility.getDeviceID(PlayerActivity.this));

            try {
                if (admob_player.getVisibility() == View.VISIBLE) {
                    admob_player.setVisibility(View.GONE);
                    ad_cross.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (i == R.id.img_video_share) {
            MyApplication.getInstance().trackEvent("Video Share", "Shared text is : " + videoUrl,
                    MyUtility.getDeviceID(PlayerActivity.this));

            MyUtility.My_Videoshare(PlayerActivity.this, videoUrl);
        } else if (i == R.id.back_button) {
            onBackPressed();
        }






       /* Glide.with(PlayerActivity.this)
                .load(imageURL)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super
                            Bitmap> glideAnimation) {

                        try {
                            MyUtility.Glide_share(PlayerActivity.this, resource);
                        } catch (Exception e) {

                            MyUtility.My_share(PlayerActivity.this, "", "");
                            e.printStackTrace();
                        }


                    }
                });*/


    }



}
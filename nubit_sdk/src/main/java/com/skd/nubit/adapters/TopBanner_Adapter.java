package com.skd.nubit.adapters;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;

import androidx.viewpager.widget.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import com.skd.nubit.R;
import com.skd.nubit.videoplayerstuffs.UniversalMediaController;
import com.skd.nubit.videoplayerstuffs.UniversalVideoView;
import com.skd.nubit.models.HomeVideoPojo;
import com.skd.nubit.mycallbacks.PagersAutoScrollControllerCallback;
import com.skd.nubit.mycallbacks.RktPlayerCallBack;
import com.skd.nubit.mycallbacks.Video_Cat_CallBAck;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

import java.util.List;

public class TopBanner_Adapter extends PagerAdapter implements UniversalVideoView
        .VideoViewCallback, RktPlayerCallBack.UpdateonPlayerInit {

    Context context;

    HomeVideoPojo top_banner_pojo;
    List<HomeVideoPojo> arraylist;
    UniversalMediaController media_controller;
    View cont;

    String getShareFlag;
    String getCatFlag;

    FrameLayout mp4_layout, youtube_layout;
    UniversalVideoView mVideoView;
    private int mSeekPosition;
    /*  boolean shouldTrack;*/


    WebView youTubeWebview;

    /*YouTubePlayer mYouTubePlayer;
    YouTubePlayerView youtube_view;
    String get_clicked_VideoID;*/
    AudioManager mAudioManager;


    String videoTitle,videoUrl;


    int playerPosition;

    /* AutoScrollViewPager autoScrollViewPager;*/

    /* Interface_PlayerPosition sendPlayerPosition;*/

    public TopBanner_Adapter(Context context,
                             List<HomeVideoPojo> topBanner_arraylist) {

        this.context = context;
        this.arraylist = topBanner_arraylist;
        /*this.sendPlayerPosition=sendPlayerPosition;*/

        getShareFlag = MyApplication.app_sharedPreferences.getString("videoShare_showHide", "");
        getCatFlag = MyApplication.app_sharedPreferences.getString("videoCat_showHide", "");
        try {
            mAudioManager = (AudioManager) context.getSystemService(Context
                    .AUDIO_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            MyUtility.muteDeviceAudio(mAudioManager);

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            RktPlayerCallBack.getInstance().setListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {


        cont = LayoutInflater.from(container.getContext()).inflate(R.layout
                .top_banner_items_home, container, false);


        top_banner_pojo = arraylist.get(position);

        ImageView imgflag = cont.findViewById(R.id.img_top_banner);
        ImageView img_video_share = cont.findViewById(R.id.img_video_share);
        ImageButton video_spinner = cont.findViewById(R.id.video_spinner);
        TextView txt_video_title = cont.findViewById(R.id.txt_video_title);
        youTubeWebview = cont.findViewById(R.id.youTubeWebview);

        txt_video_title.setText(top_banner_pojo.getTitle());

        /* img_mute_unmute.setImageResource(R.drawable.search_mic);*/


        mp4_layout = cont.findViewById(R.id.mp4_layout);
        youtube_layout = cont.findViewById(R.id.youtube_layout);
        FrameLayout video_frameLayout = cont.findViewById(R.id.video_frameLayout);

        mVideoView = cont.findViewById(R.id.videoView);
        media_controller = cont.findViewById(R.id.media_controller);


        /*  youtube_view = cont.findViewById(R.id.youtube_view);*/


        mVideoView.setMediaController(media_controller);
        /*setVideoAreaSize();*/
        mVideoView.setVideoViewCallback(this);


        final ImageView VideoPreviewPlayButton = cont.findViewById(R.id
                .videoPreviewPlayButton);


        if (!TextUtils.isEmpty(getShareFlag) & getShareFlag.equalsIgnoreCase("1")) {
            img_video_share.setVisibility(View.VISIBLE);
        } else {
            img_video_share.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(getCatFlag) & getCatFlag.equalsIgnoreCase("1")) {
            video_spinner.setVisibility(View.VISIBLE);
        } else {
            video_spinner.setVisibility(View.GONE);
        }


        if (!TextUtils.isEmpty(top_banner_pojo.getIs_auto_play()) && top_banner_pojo
                .getIs_auto_play().equalsIgnoreCase("0")) {

            /*  media_controller.UnmuteAudio();*/

            /*try {
                PagersAutoScrollControllerCallback.getInstance().notify(true, "adapter",
                        position);
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            try {
                media_controller.hide_img_audio_controller();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (mVideoView.isPlaying()) {
                    mVideoView.stopPlayback();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

/*
            try {
                new YouTubeEmbededFragment().releasePlayer();
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            mp4_layout.setVisibility(View.GONE);
            youtube_layout.setVisibility(View.GONE);
            video_frameLayout.setVisibility(View.VISIBLE);


        } else if (!TextUtils.isEmpty(top_banner_pojo.getIs_auto_play()) && top_banner_pojo
                .getIs_auto_play().equalsIgnoreCase("1") && !TextUtils.isEmpty(top_banner_pojo
                .getVideo_table_type()) && top_banner_pojo
                .getVideo_table_type().equalsIgnoreCase("other")) {

            try {
                PagersAutoScrollControllerCallback.getInstance().notify(false,
                        "adapter",
                        position);
            } catch (Exception e) {
                e.printStackTrace();
            }

           /* try {
                new YouTubeEmbededFragment().releasePlayer();
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            youtube_layout.setVisibility(View.GONE);
            video_frameLayout.setVisibility(View.GONE);
            mp4_layout.setVisibility(View.VISIBLE);

            if (!TextUtils.isEmpty(top_banner_pojo.getYoutube_link())) {

                media_controller.hideScaleButtion();
                media_controller.show_img_audio_controller();
                MyUtility.muteDeviceAudio(mAudioManager);

                if (mSeekPosition > 0) {
                    mVideoView.seekTo(mSeekPosition);
                }
                setVideoAreaSize(top_banner_pojo.getYoutube_link());


            }

            videoTitle=top_banner_pojo.getTitle();
            videoUrl=top_banner_pojo.getYoutube_link();


        } else if (!TextUtils.isEmpty(top_banner_pojo.getIs_auto_play()) && top_banner_pojo
                .getIs_auto_play().equalsIgnoreCase("1") && !TextUtils.isEmpty(top_banner_pojo
                .getVideo_table_type()) && top_banner_pojo
                .getVideo_table_type().equalsIgnoreCase("video")) {
            mp4_layout.setVisibility(View.GONE);
            video_frameLayout.setVisibility(View.GONE);
            youtube_layout.setVisibility(View.VISIBLE);


            if (!TextUtils.isEmpty(top_banner_pojo.getYoutube_id())) {

                /* media_controller.hideScaleButtion();*/
                /* media_controller.show_img_audio_controller();*/
                MyUtility.muteDeviceAudio(mAudioManager);


                try {
                    PagersAutoScrollControllerCallback.getInstance().notify(false,
                            "adapter",
                            position);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                /*try {
                    YouTubeEmbededFragment f = YouTubeEmbededFragment.newInstance(top_banner_pojo
                            .getYoutubeId());
                    *//* context.getSupportFragmentManager().beginTransaction().replace(R.id
                    .youtube_layout, f).commit();*//*
                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.youtube_layout, f)
                            .commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
*/
                youTubeWebview.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        emulateClick(view);
                    }
                });
//                youTubeWebview.getSettings().setAppCacheEnabled(true);
                youTubeWebview.getSettings().setJavaScriptEnabled(true);
                youTubeWebview.getSettings().setDomStorageEnabled(true);
                /*    youTubeWebview.getSettings().setAppCachePath(getCacheDir().getAbsolutePath
                ());*/
                youTubeWebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                youTubeWebview.getSettings().setMediaPlaybackRequiresUserGesture(false);
                youTubeWebview.getSettings().setAllowContentAccess(false);

                //view.loadUrl("https://www.youtube.com/embed/bHQqvYy5KYo?autoplay=1");
                youTubeWebview.loadUrl(top_banner_pojo.getYoutube_link());


            }


        }


        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                try {

                    MyUtility.saveTracksInDB(context, videoUrl,
                            videoUrl, "Video", "0", videoTitle,
                            String.valueOf(((mVideoView.getCurrentPosition()) / 1000)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        img_video_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeVideoPojo videoPojo = arraylist.get(position);
                MyApplication.getInstance().trackEvent("Video Share", videoPojo.getRedirect_link
                        (), videoPojo.getTitle());
                MyUtility.My_Videoshare(context, videoPojo.getYoutube_link());

            }
        });

        video_spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Video_Cat_CallBAck.getInstance().notifyOnVideoCatSelection();

            }
        });


        try {
            if (top_banner_pojo.getVideo_table_type().equalsIgnoreCase("video") || top_banner_pojo
                    .getVideo_table_type().equalsIgnoreCase("")) {
                VideoPreviewPlayButton.setVisibility(View.VISIBLE);
            } else if (top_banner_pojo.getVideo_table_type().equalsIgnoreCase("banner")) {
                VideoPreviewPlayButton.setVisibility(View.GONE);
            } else if (top_banner_pojo.getVideo_table_type().equalsIgnoreCase("other")) {
                VideoPreviewPlayButton.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        Glide.with(MyApplication.ctx)
                .load(top_banner_pojo.getThumb()).placeholder(R.drawable.placeholder_news).error
                (R.drawable.placeholder_news).diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable>
                            target, boolean isFirstResource) {
                        VideoPreviewPlayButton.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model,
                                                   Target<GlideDrawable> target, boolean
                                                           isFromMemoryCache, boolean
                                                           isFirstResource) {

                        return false;
                    }
                })
                .into(imgflag);


        container.addView(cont, 0);


        return cont;
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
    public void destroyItem(ViewGroup arg0, int arg1, Object arg2) {
        arg0.removeView((View) arg2);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    public void returnPlayerPos() {

    }

    @Override
    public int getCount() {

        try {
            return arraylist.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    @Override
    public void onScaleChange(boolean isFullscreen) {

    }

    @Override
    public void onPause(MediaPlayer mediaPlayer) {
        Log.d("VideoPlayerState", "onPause");


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                try {
                    PagersAutoScrollControllerCallback.getInstance().notify(true, "adapter", 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 2000);

        try {

            MyUtility.saveTracksInDB(context, videoUrl,
                    videoUrl, "Video", "0", videoTitle,
                    String.valueOf(((mediaPlayer.getCurrentPosition()) / 1000)));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onStart(MediaPlayer mediaPlayer) {

        Log.d("VideoPlayerState", "onStart");


    }

    @Override
    public void onBufferingStart(MediaPlayer mediaPlayer) {

        Log.d("VideoPlayerState", "onBufferingStart");


    }

    @Override
    public void onBufferingEnd(MediaPlayer mediaPlayer) {
        Log.d("VideoPlayerState", "onBufferingEnd");

    }
/*
    @Override
    public void playCompleted(MediaPlayer mediaPlayer) {

    }

    @Override
    public void loadingStart(MediaPlayer mediaPlayer) {

    }*/

    private void setVideoAreaSize(final String myUrl) {
        mp4_layout.post(new Runnable() {
            @Override
            public void run() {
                int width = mp4_layout.getWidth();
                /*cachedHeight = (int) (width * 405f / 720f);*/
//                cachedHeight = (int) (width * 3f / 4f);
//                cachedHeight = (int) (width * 9f / 16f);
                ViewGroup.LayoutParams videoLayoutParams = mp4_layout.getLayoutParams();
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                mp4_layout.setLayoutParams(videoLayoutParams);


                mVideoView.setVideoPath(myUrl);
                mVideoView.seekTo(5);
                mVideoView.requestFocus();

                mVideoView.start();
            }
        });
    }

    @Override
    public void updatePlayerCallBack(boolean z, String playerState) {


        if (playerState.equalsIgnoreCase("scroll_up")) {
            PagersAutoScrollControllerCallback.getInstance().notify(true, "home", 0);




            try {


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PagersAutoScrollControllerCallback.getInstance().notify(false, "home", 0);
                    }
                }, 4000);
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (playerState.equalsIgnoreCase("scroll_down")) {
            PagersAutoScrollControllerCallback.getInstance().notify(true, "home", 0);

        } else if (playerState.equalsIgnoreCase("completed")) {
            PagersAutoScrollControllerCallback.getInstance().notify(true, "home", 0);



        } else if (playerState.equalsIgnoreCase("loading")) {
            PagersAutoScrollControllerCallback.getInstance().notify(false, "loading", 0);

        }

    }


}
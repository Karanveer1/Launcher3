package com.skd.nubit.videoplayerstuffs;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.skd.nubit.mycallbacks.PagersAutoScrollControllerCallback;
import com.skd.nubit.mycallbacks.RktPlayerCallBack;
import com.skd.nubit.utilityclasses.MyApplication;

public class YouTubeEmbededFragment extends YouTubePlayerSupportFragment {

    YouTubePlayer my_player;

    public YouTubeEmbededFragment() {

    }

    public static YouTubeEmbededFragment newInstance(String url) {

        YouTubeEmbededFragment f = new YouTubeEmbededFragment();

        Bundle b = new Bundle();
        b.putString("uTubeID", url);

        f.setArguments(b);
        f.init();

        return f;
    }

    private void init() {

        initialize(MyApplication.app_sharedPreferences.getString
                ("youtube_key", ""), new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0,
                                                YouTubeInitializationResult arg1) {
            }

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer
                    player, boolean wasRestored) {
                if (!wasRestored) {
                    player.cueVideo(getArguments().getString("uTubeID"));

                    try {
                        player.setFullscreen(false);
                        player.setShowFullscreenButton(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    my_player=player;


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                player.play();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, 2000);
                }



                YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new
                        YouTubePlayer.PlayerStateChangeListener() {


                    @Override
                    public void onLoading() {
                        Toast.makeText(getActivity(), "onLoading", Toast.LENGTH_LONG).show();
                        RktPlayerCallBack.getInstance().notifyByPlayer(true, "loading");
                    }

                    @Override
                    public void onLoaded(String s) {
                        Toast.makeText(getActivity(), "onLoaded", Toast.LENGTH_LONG).show();


                    }

                    @Override
                    public void onAdStarted() {

                    }

                    @Override
                    public void onVideoStarted() {

                    }

                    @Override
                    public void onVideoEnded() {
                    /*    PagersAutoScrollControllerCallback.getInstance().notify(true, "home", 0);*/
                        RktPlayerCallBack.getInstance().notifyByPlayer(true, "completed");

                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {

                    }
                };
                player.setPlayerStateChangeListener(playerStateChangeListener);


                YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer
                        .PlaybackEventListener() {
                    @Override
                    public void onPlaying() {
                  /*      PagersAutoScrollControllerCallback.getInstance().notify(false,
                                "adapter_hide_indicator",
                                0);*/
                    }

                    @Override
                    public void onPaused() {
/*
                        PagersAutoScrollControllerCallback.getInstance().notify(true, "home", 0);*/

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    PagersAutoScrollControllerCallback.getInstance().notify(true, "home", 0);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }, 2000);
                        Toast.makeText(getActivity(), "onPaused", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onStopped() {
                        Toast.makeText(getActivity(), "onStopped", Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onBuffering(boolean b) {
                        Toast.makeText(getActivity(), "onBuffering", Toast.LENGTH_LONG).show();
                     /*   PagersAutoScrollControllerCallback.getInstance().notify(false,
                                "adapter_hide_indicator",
                                0);*/
                    /*    PagersAutoScrollControllerCallback.getInstance().notify(false,
                                "adapter_hide_indicator",
                                0);*/
                    }

                    @Override
                    public void onSeekTo(int i) {

                    }
                };

                player.setPlaybackEventListener(playbackEventListener);


            }


        });
    }

    public void releasePlayer()
    {

        try {
            my_player.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}

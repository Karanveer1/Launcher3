package com.skd.nubit.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.skd.nubit.R;
import com.skd.nubit.utilityclasses.SnapHelperOneByOne;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.YouTubePlayerViewHolder> {
    private List<String> videoIds;
    private Lifecycle lifecycle;
    private boolean isAutoplayEnabled = true; // Flag to control autoplay
    private int currentPosition = 0;
    private YouTubePlayerViewHolder currentViewHolder;


    public RecyclerViewAdapter(List<String> videoIds, Lifecycle lifecycle) {
        this.videoIds = videoIds;
        this.lifecycle = lifecycle;
    }

    @NonNull
    @Override
    public YouTubePlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new YouTubePlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull YouTubePlayerViewHolder viewHolder, int position) {
        String video = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoIds.get(position) + "\"" +
                " title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; " +
                "encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
        viewHolder.webView.setWebChromeClient(new WebChromeClient());
        viewHolder.webView.loadData(video, "text/html","utf-8");
        WebSettings s = viewHolder.webView.getSettings();
        s.setBuiltInZoomControls(false);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setJavaScriptEnabled(true);
//        viewHolder.webView.getSettings().setDomStorageEnabled(true);
//        viewHolder.webView.getSettings().setDatabaseEnabled(true);
        Log.e("checkpos","binder>>" + position + ">>" + currentPosition);
        viewHolder.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (position == currentPosition) {
                    Log.e("checkpos","play>>" + position + ">currentPosition>" + currentPosition + ">viewHolder>" + viewHolder);
                    currentViewHolder = viewHolder;
//                    if (position == 0){
                        viewHolder.restartVideo();
//                    }else{
//                        viewHolder.restartVideo();
//                    }

                } else {
                    Log.e("checkpos","pause>>" + position + ">currentPosition>" + currentPosition + ">viewHolder>" + viewHolder);
                    // Pause video if this ViewHolder is not currently visible
                    viewHolder.pauseVideo();
                }
            }
        });

//        viewHolder.cueVideo(videoIds.get(position));
//
//        // Autoplay the video if it's the first item and autoplay is enabled
//        if (position == 0 && isAutoplayEnabled) {
//            viewHolder.playVideo();
//            isAutoplayEnabled = false; // Disable autoplay after the first item
//        }
    }

    @Override
    public int getItemCount() {
        return videoIds.size();
    }

    public class YouTubePlayerViewHolder extends RecyclerView.ViewHolder {
        private YouTubePlayerView youTubePlayerView;
        private YouTubePlayer youTubePlayer;
        private WebView webView;
        private String currentVideoId;

        public YouTubePlayerViewHolder(View itemView) {
            super(itemView);
            youTubePlayerView = itemView.findViewById(R.id.youtube_player_view);
            webView = itemView.findViewById(R.id.webView);
            lifecycle.addObserver(youTubePlayerView);


//            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//                @Override
//                public void onReady(@NonNull YouTubePlayer player) {
//                    youTubePlayer = player;
//                    if (currentVideoId != null) {
//                        youTubePlayer.cueVideo(currentVideoId, 0f);
//                    }
//                }
//            });
        }

        public void pauseVideo() {
            // Pause the video playback
            webView.loadUrl("javascript:document.getElementsByTagName('iframe')[0].contentWindow.postMessage('{'event':'command','func':'pauseVideo','args':''}', '*')");
        }

        public void restartVideo() {
            // Pause the video playback
            webView.loadUrl("javascript:(function() { " +
                    "var iframe = document.getElementsByTagName('iframe')[0]; " +
                    "var player = new YT.Player(iframe); " +
                    "player.playVideo();" +
                    "})()");
//            webView.loadUrl("javascript:document.getElementsByTagName('iframe')[0].contentWindow.postMessage('{'event':'command','func':'playVideo','args':''}', '*')");
        }

        public void cueVideo(String videoId) {
            currentVideoId = videoId;
            if (youTubePlayer != null) {
                youTubePlayer.cueVideo(videoId, 0f);
            }
        }

        public void playVideo() {
            if (youTubePlayer != null) {
                youTubePlayer.play();
            }
        }


    }
    // Add a method to update the current position
    public void setCurrentPosition(int position) {
        currentPosition = position;
    }
}
//
//public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.YouTubePlayerViewHolder> {
//    private List<String> videoIds;
//    private Lifecycle lifecycle;
//
//    public RecyclerViewAdapter(List<String> videoIds, Lifecycle lifecycle) {
//        this.videoIds = videoIds;
//        this.lifecycle = lifecycle;
//    }
//
//    @NonNull
//    @Override
//    public YouTubePlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
//        return new YouTubePlayerViewHolder(lifecycle, itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull YouTubePlayerViewHolder viewHolder, int position) {
//        viewHolder.cueVideo(videoIds.get(position).toString());
//    }
//
//    @Override
//    public int getItemCount() {
//        return videoIds.size();
//    }
//
//    /**
//     * ViewHolder containing a YouTubePlayer. When the list is scrolled only the video id changes.
//     */
//    public static class YouTubePlayerViewHolder extends RecyclerView.ViewHolder {
//        private YouTubePlayer youTubePlayer;
//        private String currentVideoId;
//
//        public YouTubePlayerViewHolder(Lifecycle lifecycle, View view) {
//            super(view);
//            YouTubePlayerView youTubePlayerView = view.findViewById(R.id.youtube_player_view);
////            lifecycle.addObserver(youTubePlayerView);
//
//            // the overlay view is used to intercept clicks when scrolling the recycler view
//            // without it touch events used to scroll might accidentally trigger clicks in the player
//            View overlayView = view.findViewById(R.id.overlay_view);
//            // when the overlay is clicked it starts playing the video
//            overlayView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (youTubePlayer != null) {
//                        youTubePlayer.play();
//                    }
//                }
//            });
//
//            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//                @Override
//                public void onReady(@NonNull YouTubePlayer player) {
//                    // store youtube player reference for later
//                    youTubePlayer = player;
//                    // cue the video if it's available
//                    if (currentVideoId != null) {
//                        youTubePlayer.cueVideo(currentVideoId, 0f);
//                    }
//                }
//
//                @Override
//                public void onStateChange(@NonNull YouTubePlayer player, @NonNull PlayerConstants.PlayerState state) {
//                    switch (state) {
//                        // when the video is CUED, show the overlay.
//                        case VIDEO_CUED:
//                            overlayView.setVisibility(View.VISIBLE);
//                            break;
//                        // remove the overlay for every other state, so that we don't intercept clicks and the
//                        // user can interact with the player.
//                        default:
//                            overlayView.setVisibility(View.GONE);
//                            break;
//                    }
//                }
//            });
//        }
//
//        public void cueVideo(String videoId) {
//            currentVideoId = videoId;
//            // cue the video if the youtube player is available
//            if (youTubePlayer != null) {
//                youTubePlayer.cueVideo(videoId, 0f);
//            }
//        }
//    }
//}
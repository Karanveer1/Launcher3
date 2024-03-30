package com.skd.nubit.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.skd.nubit.R;
import com.skd.nubit.models.youTubeVideos;
//import com.skd.nubit.utilityclasses.NubitView;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private List<youTubeVideos> youtubeVideoList;
    private List<Drawable> bannerList;
    private List<String> linkList;
    Context context;
    WebView ccc;
    private int currentlyPlayingPosition = 0;
    private RecyclerView recyclerView;

    public VideoAdapter(List<youTubeVideos> youtubeVideoList, Context context, List<Drawable> bannerList, List<String> linkList, RecyclerView recyclerView) {

        this.youtubeVideoList = youtubeVideoList;
        this.context = context;
        this.bannerList = bannerList;
        this.linkList = linkList;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_view, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        // Bind your data to the ViewHolder here
        // For example:
        youTubeVideos video = youtubeVideoList.get(position);
        String videoId = extractYouTubeVideoId(video.getVideoUrl());
        if (videoId != null) {
            holder.bindData(videoId,position);
        }
//        holder.bindData(video);
    }

    @Override
    public int getItemCount() {
        return youtubeVideoList.size();
    }

    // Method to pause video playback for a specific position
    public void pauseVideoPlayback(int position) {
        // Loop through all the visible ViewHolders in the RecyclerView
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            // Get the ViewHolder at this position
            VideoViewHolder viewHolder = (VideoViewHolder) recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
            // Check if the ViewHolder's adapter position matches the position we want to pause
            if (viewHolder != null && viewHolder.getAdapterPosition() == position) {
                // Call the pauseVideo method of the ViewHolder
                viewHolder.pauseVideo();
                // Break out of the loop since we found the ViewHolder we wanted
                break;
            }
        }
    }

    // Method to resume video playback for a specific position
    public void resumeVideoPlayback(int position) {
        // Loop through all the visible ViewHolders in the RecyclerView
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            // Get the ViewHolder at this position
            VideoViewHolder viewHolder = (VideoViewHolder) recyclerView.getChildViewHolder(recyclerView.getChildAt(i));
            // Check if the ViewHolder's adapter position matches the position we want to resume
            if (viewHolder != null && viewHolder.getAdapterPosition() == position) {
                // Call the resumeVideo method of the ViewHolder
                viewHolder.resumeVideo();
                currentlyPlayingPosition = position;
                // Break out of the loop since we found the ViewHolder we wanted
                break;
            }
        }
    }
    public int getCurrentlyPlayingPosition() {
        return currentlyPlayingPosition;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateCurrentlyPlayingPosition(int newPosition) {
        currentlyPlayingPosition = newPosition;
        notifyDataSetChanged();
    }
    public class VideoViewHolder extends RecyclerView.ViewHolder {
        private YouTubePlayerView youtubePlayerView;
        private boolean isVideoPaused;
        private String videoId;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            youtubePlayerView = itemView.findViewById(R.id.youtube_player_view);
            // Initialize your video player here
        }

        public void bindData(String video, int position) {
            // Load your video data into the ViewHolder here
            // For example, if using YouTubePlayerView:
            youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                    videoId = extractYouTubeVideoId(video.getVideoUrl());
                    videoId = video;
                    Log.e("checkfvide",">bindData>" + video);
                    youTubePlayer.loadVideo(video, 0f);
                    updateCurrentlyPlayingPosition(position);
                }
            });
        }

        // Method to pause video playback for this ViewHolder
        public void pauseVideo() {
            if (youtubePlayerView != null && !isVideoPaused) {
                youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        youTubePlayer.pause();
                    }
                });
//                youtubePlayerView.pause();
                isVideoPaused = true;
            }
        }

        // Method to resume video playback for this ViewHolder
        public void resumeVideo() {
            if (youtubePlayerView != null && isVideoPaused) {
                youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        Log.e("checkfvide",">resumeVideo>" + videoId);
                        youTubePlayer.loadVideo(videoId, 0f);
                    }
                });
//                youtubePlayerView.play();
                isVideoPaused = false;
            }
        }
    }

    // Helper method to extract YouTube video ID from URL
    public static String extractYouTubeVideoId(String htmlCode) {
        String videoId = null;
        // Define a regular expression to match YouTube video IDs
        Pattern pattern = Pattern.compile("youtube\\.com\\/embed\\/([\\w-]{11})");
        Matcher matcher = pattern.matcher(htmlCode);
        if (matcher.find()) {
            videoId = matcher.group(1);
        }
        return videoId;
    }

}

//public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
//    private List<youTubeVideos> youtubeVideoList;
//    private List<Drawable> bannerList;
//    private List<String> linkList;
//    Context context;
//    WebView ccc;
//    private int currentlyPlayingPosition = 0;
//    public VideoAdapter(List<youTubeVideos> youtubeVideoList, Context context, List<Drawable> bannerList, List<String> linkList) {
//
//        this.youtubeVideoList = youtubeVideoList;
//        this.context = context;
//        this.bannerList = bannerList;
//        this.linkList = linkList;
//    }
//    @Override
//    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from( parent.getContext()).inflate(R.layout.video_view, parent, false);
//        return new VideoViewHolder(view);
//    }
//    @SuppressLint("SetJavaScriptEnabled")
//    @Override
//    public void onBindViewHolder(VideoViewHolder holder, int position) {
//        Log.e("Checjcef",">21212>");
////            loadVideo(holder, position);
//
//        Log.e("checkdatarr", ">>" + position);
////        holder.youtube_player_view.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
////            @Override
////            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
////                // val videoId = "S0Q4gqBUs7c"
////                Log.e("Checjcef",">1>" + youtubeVideoList.get(position).getVideoUrl());
////                String videoId = extractYouTubeVideoId(youtubeVideoList.get(position).getVideoUrl());
////                Log.e("Checjcef",">2>" + videoId);
////                youTubePlayer.loadVideo(videoId, 0f);
////            }
////        });
//
//        holder.youtube_player_view.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                String videoId = extractYouTubeVideoId(youtubeVideoList.get(position).getVideoUrl());
//                youTubePlayer.loadVideo(videoId, 0f);
//
//                // Pause the previously playing video if any
//                if (currentlyPlayingPosition != -1 && currentlyPlayingPosition != position) {
//                    // Pause the video of the previous item
//                    youTubePlayer.pause();
//                }
//
//                // Update the currently playing position
//                currentlyPlayingPosition = position;
//            }
//        });
//    }
//    @Override
//    public int getItemCount() {
//        return youtubeVideoList.size();
//    }
//    public int getCurrentlyPlayingPosition() {
//        return currentlyPlayingPosition;
//    }
//    @Override
//    public void onViewAttachedToWindow(@NonNull VideoViewHolder holder) {
//        super.onViewAttachedToWindow(holder);
//        Log.e("checkviewflow",">onViewAttachedToWindow>" + currentlyPlayingPosition);
//        if (currentlyPlayingPosition != -1) {
////            loadVideo(holder,currentlyPlayingPosition);
////            (new NubitView(context)).playVideoPlayback(currentlyPlayingPosition);
////            currentlyPlayingPosition = -1;
//        }
//    }
//
//    @Override
//    public void onViewDetachedFromWindow(@NonNull VideoViewHolder holder) {
//        super.onViewDetachedFromWindow(holder);
//        Log.e("checkviewflow",">onViewDetachedFromWindow>" + currentlyPlayingPosition);
////        pauseCurrentVideoPlayback();
////        pauseideo(holder,currentlyPlayingPosition);
//
//    }
//
//    public static String extractYouTubeVideoId(String htmlCode) {
//        String videoId = null;
//        // Define a regular expression to match YouTube video IDs
//        Pattern pattern = Pattern.compile("youtube\\.com\\/embed\\/([\\w-]{11})");
//        Matcher matcher = pattern.matcher(htmlCode);
//        if (matcher.find()) {
//            videoId = matcher.group(1);
//        }
//        return videoId;
//    }

//    public void playVideoForVisibleItem(int position) {
//        if (position != currentlyPlayingPosition) {
//            // Stop the video for the previous visible item if any
//            stopVideoForVisibleItem();
//
//            // Start playing the video for the currently visible item
//            currentlyPlayingPosition = position;
//            notifyItemChanged(position);
//        }
//    }
//
//    // Method to stop video for the previously visible item
//    public void stopVideoForVisibleItem() {
//        if (currentlyPlayingPosition != -1) {
//            notifyItemChanged(currentlyPlayingPosition);
//            currentlyPlayingPosition = -1;
//        }
//    }
//
//
//    public class VideoViewHolder extends RecyclerView.ViewHolder{
//        WebView videoWeb;
//        ImageView thumbnail;
//        YouTubePlayerView youtube_player_view;
//        ImageView banner;
//        VideoViewHolder(View itemView) {
//            super(itemView);
//            videoWeb = itemView.findViewById(R.id.webView);
//            thumbnail = itemView.findViewById(R.id.thumbnail);
//            youtube_player_view = itemView.findViewById(R.id.youtube_player_view);
//            banner = itemView.findViewById(R.id.banner);
//            videoWeb.getSettings().setJavaScriptEnabled(true);
//            videoWeb.setWebChromeClient(new WebChromeClient() {
//            } );
//        }
//
//
//
//        public void pauseVideoPlayback() {
//            // Implement logic to pause video playback in the WebView
//            // For example: webView.onPause();
//            if (youtube_player_view != null) {
//                youtube_player_view.getYouTubePlayerWhenReady();
//            }
//
////            videoWeb.onPause();
//            Log.e("vcheckplay",">pauseVideoPlayback>");
//        }
//        public void playVideoPlayback() {
//            // Implement logic to pause video playback in the WebView
//            // For example: webView.onPause();
////            videoWeb.reload();
//            Log.e("vcheckplay",">playVideoPlayback>");
//        }
//    }
//}





//    public void stopVideoPlayback() {
//        // Stop video playback in the WebView
////        Log.e("checkdcata","ceef>>" + currentlyPlayingPosition + ">>" + ccc.getUrl());
////        ccc.onPause();
//
//
//        // You need to implement the logic to stop the YouTube video here
//
//    }
//
//    private void pauseideo(VideoViewHolder holder, int position) {
//        Log.e("checkdatarr", ">>" + position);
//        holder.youtube_player_view.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                // val videoId = "S0Q4gqBUs7c"
////                Log.e("Checjcef", ">1>" + youtubeVideoList.get(position).getVideoUrl());
////                String videoId = extractYouTubeVideoId(youtubeVideoList.get(position).getVideoUrl());
////                Log.e("Checjcef", ">2>" + videoId);
//                youTubePlayer.pause();
//            }
//        });
//    }
//    private void loadVideo(VideoViewHolder holder, int position) {
//        Log.e("checkdatarr", ">>" + position);
//        holder.youtube_player_view.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                // val videoId = "S0Q4gqBUs7c"
//                Log.e("Checjcef",">1>" + youtubeVideoList.get(position).getVideoUrl());
//                String videoId = extractYouTubeVideoId(youtubeVideoList.get(position).getVideoUrl());
//                Log.e("Checjcef",">2>" + videoId);
//                youTubePlayer.loadVideo(videoId, 0f);
//            }
//        });
//    }
//
//    public void pauseCurrentVideoPlayback() {
//        if (currentlyPlayingPosition != -1) {
////            (new NubitView(context)).pauseVideoPlayback(currentlyPlayingPosition);
////            currentlyPlayingPosition = -1;
//        }
//    }
package com.skd.nubit.activities;

import android.app.DownloadManager;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.skd.nubit.R;
import com.skd.nubit.models.TrendingNewsModel;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.VideoIdsProvider;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends Fragment{
    private YouTubePlayer youTubePlayerNew;
    NestedScrollView nestedScrollView;
    ArrayList<TrendingNewsModel> arraylist_news_video_new;
    private boolean isVideoPlaying = false;

    private static final String ARG_VIDEO_PATH = "videoPath";
    private static final String ARG_VIDEO_PREVIEW_PATH = "videoPathPreview";
    private static final String ARG_VIDEO_TITLE_PATH = "videoTitlePath";
    private static final String ARG_VIDEO_TITLE = "title";
    private static final String ARG_CAT_TYPE = "cat_type";
    private static final String ARG_ACT_TYPE = "act_type";

    public static VideoFragment newInstance(String videoPath, String videoTitlePath, String videoPathPreview, int title, String cat_type, String act_type) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_VIDEO_PATH, videoPath);
        args.putString(ARG_VIDEO_TITLE_PATH, videoTitlePath);
        args.putString(ARG_VIDEO_PREVIEW_PATH, videoPathPreview);
        args.putString(ARG_VIDEO_TITLE, String.valueOf(title));
        args.putString(ARG_CAT_TYPE, cat_type);
        args.putString(ARG_ACT_TYPE, act_type);
        fragment.setArguments(args);
        return fragment;
    }
    private String title = "";
    private String cat_type = "";
    YouTubePlayerView youTubePlayerView;
    private String videoPathTitleNew = "";
    private String videoPathTitleNewPreview = "";
    private boolean isPlaying = false;
    private boolean isMute = false;
    private long yourDownloadId = -1;
    private final int MIN_BUFFER_DURATION = 2000;
    private final int MAX_BUFFER_DURATION = 5000;
    private final int MIN_PLAYBACK_START_BUFFER = 1500;
    private final int MIN_PLAYBACK_RESUME_BUFFER = 2000;
    private int main_volume_level = 0;
    public WebView webView;
    private String video_layout_check = "";
    private YouTubePlayer initializedYouTubePlayer;
    private final String videoId = getNextVideoId();
    View overlayView;
//    private final String videoId = VideoIdsProvider.getNextVideoId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_item, container, false);
        webView = view.findViewById(R.id.webView);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void stopPlayer(){
        if (youTubePlayerNew!=null) {
            youTubePlayerNew.pause();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        overlayView = view.findViewById(R.id.overlay_view);

        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        filter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);

        title = getArguments().getString(ARG_VIDEO_TITLE_PATH);
        videoPathTitleNew = getArguments().getString(ARG_VIDEO_PATH);
        videoPathTitleNewPreview = getArguments().getString(ARG_VIDEO_PREVIEW_PATH);
        cat_type = getArguments().getString(ARG_CAT_TYPE);
        String selectedTitle = getArguments().getString(ARG_VIDEO_TITLE);

//        String video = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + title + "\"" +
//                " title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; " +
//                "encrypted-media; gyroscope; picture-in-picture; web-share\" allowfullscreen></iframe>";
//        webView.setWebChromeClient(new WebChromeClient());
//        webView.loadData(video, "text/html","utf-8");
//        WebSettings s = webView.getSettings();
//        s.setBuiltInZoomControls(false);
//        s.setCacheMode(WebSettings.LOAD_DEFAULT);
//        s.setUseWideViewPort(true);
//        s.setLoadWithOverviewMode(true);
//        s.setJavaScriptEnabled(true);
////        viewHolder.webView.getSettings().setDomStorageEnabled(true);
////        viewHolder.webView.getSettings().setDatabaseEnabled(true);
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                restartVideo();
//            }
//        });


//        ViewPager2 viewPager2 = requireActivity().findViewById(R.id.viewPagerNew); // Replace R.id.view_pager with your ViewPager2 ID
//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                // Autoplay the video when a new item is shown
//                Log.e("ccheckvideoFlow","pos>>" + viewPager2.getCurrentItem() + ">>" + position);
//
//                if (initializedYouTubePlayer != null && viewPager2.getCurrentItem() == position) {
//                    initializedYouTubePlayer.play();
//                }
//            }
//        });

        nestedScrollView = requireActivity().findViewById(R.id.nestedScrollView);
        ViewPager2 viewPagerNew = requireActivity().findViewById(R.id.viewPagerNew);
        nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = nestedScrollView.getScrollY();
                Log.e("chefnfkrceef", "scrollY>>" + scrollY);
                if (scrollY > 0) {
                    if (!isViewVisible(viewPagerNew)) {
                        Log.e("chefnfkrceef", "rnvierv>>" + scrollY);
                        stopPlayer();
                        // Communicate with the fragment to stop the player
//                        stopPlayerInFragment();
                    }
                } else {
                }
            }
        });

    }


    // Method to check if a view is visible to the user
    private boolean isViewVisible(View view) {
        Rect scrollBounds = new Rect();
        nestedScrollView.getHitRect(scrollBounds);
        return view.getLocalVisibleRect(scrollBounds);
    }


    private String getNextVideoId() {
        String nextVideoId = "";
        String getSavedJson = MyApplication.app_sharedPreferences.getString("save_app_video_Json", "");
        if (!TextUtils.isEmpty(getSavedJson)) {
            Gson gson = new Gson();
            Log.e("chefef", ">>" + getSavedJson);
            if (getSavedJson != null) {
                Type type = new TypeToken<List<TrendingNewsModel>>() {}.getType();
                List<TrendingNewsModel> arraylist_recom_new_new = gson.fromJson(getSavedJson, type);

                // Extract video IDs from the list of VistoryModel
                List<String> videoIds = new ArrayList<>();
                for (TrendingNewsModel vistoryModel : arraylist_recom_new_new) {
                    videoIds.add(vistoryModel.getRedirectLink());
                }

                // Get the next video ID
                nextVideoId = VideoIdsProvider.getNextVideoId(videoIds);
//                String nextVideoId = VideoIdsProvider.getRandomVideoId(videoIds);
                if (nextVideoId != null) {
                    // Use the nextVideoId as needed
                    Log.d("NextVideoId", nextVideoId);
                } else {
                    // Handle case when no video ID is available
                    Log.d("NextVideoId", "No video ID available");
                }
            }
        }
        return nextVideoId;
    }

    @Override
    public void onResume() {
        super.onResume();
        arraylist_news_video_new = new ArrayList<>();
        getLifecycle().addObserver(youTubePlayerView);
        String get_apps_SavedJson = MyApplication.app_sharedPreferences.getString
                ("save_app_video_Json",
                        "");
        if (!TextUtils.isEmpty(get_apps_SavedJson)) {
            Gson gson = new Gson();
            Log.e("chefef",">>" + get_apps_SavedJson);
            if (get_apps_SavedJson != null) {
                Type type = new TypeToken<List<TrendingNewsModel>>() {}.getType();
                arraylist_news_video_new = gson.fromJson(get_apps_SavedJson, type);
            }
        }
        if (arraylist_news_video_new.size() > 0){
            for (TrendingNewsModel vistoryModel : arraylist_news_video_new) {

                if (videoId.equals(vistoryModel.getRedirectLink())){
                    Log.e("checknewvideo","for>>" + videoId + ">>" + vistoryModel.getRedirectLink());
                    youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                            Log.e("ccheckvideoFlow","onReady>>" + videoId);
                            initializedYouTubePlayer = youTubePlayer;
                            youTubePlayerNew = youTubePlayer;
                            youTubePlayer.loadVideo(videoId, 0);
                            Log.e("ccheckvideoFlow","youTubePlayerNew>>" + youTubePlayerNew);

                            overlayView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.e("ccheckvideoFlow","1>>");
                                    if (youTubePlayer != null) {
                                        Log.e("ccheckvideoFlow", "2>>");
                                        // Check if the video is currently playing
                                        if (youTubePlayer != null) {
                                            Log.e("ccheckvideoFlow", "2>>");
                                            // Check the playback state and perform appropriate action
                                            if (isVideoPlaying) {
                                                // Pause the video
                                                youTubePlayer.pause();
                                                isVideoPlaying = false;
                                            } else {
                                                // Start playing the video
                                                youTubePlayer.play();
                                                isVideoPlaying = true;
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    });

                }

            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("checkvideoFlow","pauseVideo>>");
//        pauseVideo();
//        initializedYouTubePlayer.pause();
    }

    public void pauseVideo() {
        // Pause the video playback
        webView.loadUrl("javascript:document.getElementsByTagName('iframe')[0].contentWindow.postMessage('{'event':'command','func':'pauseVideo','args':''}', '*')");
    }

    public void restartVideo() {
        Log.e("checkvideoFlow","restartVideo>>");
        // Pause the video playback
        webView.loadUrl("javascript:(function() { " +
                "var iframe = document.getElementsByTagName('iframe')[0]; " +
                "var player = new YT.Player(iframe); " +
                "player.playVideo();" +
                "})()");
//            webView.loadUrl("javascript:document.getElementsByTagName('iframe')[0].contentWindow.postMessage('{'event':'command','func':'playVideo','args':''}', '*')");
    }
}
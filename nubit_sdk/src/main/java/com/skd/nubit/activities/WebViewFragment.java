package com.skd.nubit.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.google.android.youtube.player.YouTubePlayerView;
import com.skd.nubit.R;
import com.skd.nubit.models.youTubeVideos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebViewFragment extends Fragment {
    private WebView webView;
    private youTubeVideos videoUrl;
    private YouTubePlayerView youtube_player_view;
    private boolean isPlaying = false;
    private boolean isViewCreated = false;

    @SuppressLint({"SetJavaScriptEnabled", "MissingInflatedId"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        webView = view.findViewById(R.id.webViewM);
        youtube_player_view = view.findViewById(R.id.youtube_player_view);
        Log.e("checknjb", ">>" + videoUrl.getVideoUrl());
        return view;
    }

    private String extractYouTubeVideoId(String htmlCode) {
        String videoId = null;
        Pattern pattern = Pattern.compile("youtube\\.com\\/embed\\/([\\w-]{11})");
        Matcher matcher = pattern.matcher(htmlCode);
        if (matcher.find()) {
            videoId = matcher.group(1);
        }
        return videoId;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setPlaying(boolean playing) {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("checrfrefer", "webview>>" + isVisibleToUser + ">>" + isViewCreated);
    }

    private void startVideoPlayback() {
    }

    public void pauseVideoPlayback() {
    }

    public void pauseVideoPlaybackFinal() {
    }

    public static WebViewFragment newInstance(youTubeVideos videoUrl) {
        WebViewFragment fragment = new WebViewFragment();
        fragment.videoUrl = videoUrl;
        return fragment;
    }
}
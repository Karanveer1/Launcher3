package com.skd.nubit.models;

public class youTubeVideos {
    String videoUrl;
    Integer thumbnail;

    public youTubeVideos() {
    }
    public youTubeVideos(String videoUrl, Integer thumbnail) {
        this.videoUrl = videoUrl;
        this.thumbnail = thumbnail;
    }
    public String getVideoUrl() {
        return videoUrl;
    }
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Integer thumbnail) {
        this.thumbnail = thumbnail;
    }
}

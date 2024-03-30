package com.skd.nubit.models;

public class VideoModel {
    private String url;
    //    private String url_main;
    private String title;
    private String cat_type;
    private String thumbnail;

    public VideoModel(String url, String title, String cat_type, String thumbnail) {
        this.url = url;
//        this.url_main = url_main;
        this.title = title;
        this.cat_type = cat_type;
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

//    public String getUrl_main() {
//        return url_main;
//    }
//
//    public void setUrl_main(String url_main) {
//        this.url_main = url_main;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCat_type() {
        return cat_type;
    }

    public void setCat_type(String cat_type) {
        this.cat_type = cat_type;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
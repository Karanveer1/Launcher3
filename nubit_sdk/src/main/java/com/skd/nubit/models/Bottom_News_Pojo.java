package com.skd.nubit.models;



/**
 * Created by robi on 3/7/17.
 */

public class Bottom_News_Pojo {


    private String title;
    private String description;
    private String image;
    private String posted_date;
    private String posted_time;
    private String redirect_link;
    private String ad_unit_id;
    private String ad_network;
    private String ad_type;
    private String ad_display_type;


    public String getYoutube_id() {
        return youtube_id;
    }

    public void setYoutube_id(String youtube_id) {
        this.youtube_id = youtube_id;
    }

    private String youtube_id;


    public String getNews_type() {
        return news_type;
    }

    public void setNews_type(String news_type) {
        this.news_type = news_type;
    }

    private String news_type;

    private boolean isAddLoaded;

    private boolean isAddLoading;

    private String open_with;

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    private String package_name;

    public String getNewsBy() {
        return newsBy;
    }

    public void setNewsBy(String newsBy) {
        this.newsBy = newsBy;
    }

    private String newsBy;

    public String getOpen_with() {
        return open_with;
    }

    public void setOpen_with(String open_with) {
        this.open_with = open_with;
    }

    public boolean isAddLoaded() {
        return isAddLoaded;
    }

    public void setAddLoaded(boolean addLoaded) {
        isAddLoaded = addLoaded;
    }



    public boolean isAddLoading() {
        return isAddLoading;
    }

    public void setAddLoading(boolean addLoading) {
        isAddLoading = addLoading;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPosted_date() {
        return posted_date;
    }

    public void setPosted_date(String posted_date) {
        this.posted_date = posted_date;
    }

    public String getPosted_time() {
        return posted_time;
    }

    public void setPosted_time(String posted_time) {
        this.posted_time = posted_time;
    }

    public String getRedirect_link() {
        return redirect_link;
    }

    public void setRedirect_link(String redirect_link) {
        this.redirect_link = redirect_link;
    }

    public String getAd_unit_id() {
        return ad_unit_id;
    }

    public void setAd_unit_id(String ad_unit_id) {
        this.ad_unit_id = ad_unit_id;
    }
    public String getAd_network() {
        return ad_network;
    }
    public void setAd_network(String ad_network) {
        this.ad_network = ad_network;
    }

    public String getAd_type() {
        return ad_type;
    }

    public void setAd_type(String ad_type) {
        this.ad_type = ad_type;
    }

    public String getAd_display_type() {
        return ad_display_type;
    }

    public void setAd_display_type(String ad_display_type) {
        this.ad_display_type = ad_display_type;
    }

}
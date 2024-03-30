package com.skd.nubit.models;



public class TrendingNewsModel {
    private String id;
    private String isAd;
    private String title;
    private String redirectLink;
    private String postedDate;
    private String description;
    private String liveDate;
    private String postedTime;
    private String imageUrl;
    private String status;
    private String feedProvider;

    public TrendingNewsModel(String id, String isAd, String title, String redirectLink, String description, String postedDate
            , String postedTime, String imageUrl, String status, String feedProvider) {
        this.id = id;
        this.isAd = isAd;
        this.title = title;
        this.redirectLink = redirectLink;
        this.description = description;
        this.postedDate = postedDate;
        this.postedTime = postedTime;
        this.imageUrl = imageUrl;
        this.status = status;
        this.feedProvider = feedProvider;
    }

    public String getIsAd() {
        return isAd;
    }

    public void setIsAd(String isAd) {
        this.isAd = isAd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRedirectLink() {
        return redirectLink;
    }

    public void setRedirectLink(String redirectLink) {
        this.redirectLink = redirectLink;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLiveDate() {
        return liveDate;
    }

    public void setLiveDate(String liveDate) {
        this.liveDate = liveDate;
    }

    public String getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFeedProvider() {
        return feedProvider;
    }

    public void setFeedProvider(String feedProvider) {
        this.feedProvider = feedProvider;
    }
}

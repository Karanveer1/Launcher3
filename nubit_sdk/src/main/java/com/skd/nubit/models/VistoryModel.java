package com.skd.nubit.models;


public class VistoryModel {
    private String urlHash;
    private String category;
    private String mainImage;
    private String title;
    private String description;

    private String liveDate;

    public VistoryModel(String urlHash, String category, String mainImage, String title, String description, String liveDate) {
        this.urlHash = urlHash;
        this.category = category;
        this.mainImage = mainImage;
        this.title = title;
        this.description = description;
        this.liveDate = liveDate;
    }

    public String getUrlHash() {
        return urlHash;
    }

    public void setUrlHash(String urlHash) {
        this.urlHash = urlHash;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
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

    public String getLiveDate() {
        return liveDate;
    }

    public void setLiveDate(String liveDate) {
        this.liveDate = liveDate;
    }
}

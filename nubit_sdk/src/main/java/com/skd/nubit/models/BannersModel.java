package com.skd.nubit.models;

public class BannersModel {

    public BannersModel(String id, String title, String category, String status, String redirectLink, String imageUrl) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.status = status;
        this.redirectLink = redirectLink;
        this.imageUrl = imageUrl;
    }

    private String id;
    private String title;
    private String category;
    private String status;
    private String redirectLink;
    private String imageUrl;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRedirectLink() {
        return redirectLink;
    }

    public void setRedirectLink(String redirectLink) {
        this.redirectLink = redirectLink;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
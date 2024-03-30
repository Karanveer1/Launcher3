package com.skd.nubit.models;

public class AppsModel {

    public AppsModel(String id, String orderNo, String title, String category, String status, String redirectLink, String bannerImage, String bannerThumbImage) {
        this.id = id;
        this.orderNo = orderNo;
        this.title = title;
        this.category = category;
        this.status = status;
        this.redirectLink = redirectLink;
        this.bannerImage = bannerImage;
        this.bannerThumbImage = bannerThumbImage;
    }

    private String id;
    private String orderNo;
    private String title;
    private String category;
    private String status;
    private String redirectLink;
    private String bannerImage;
    private String bannerThumbImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public String getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(String bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getBannerThumbImage() {
        return bannerThumbImage;
    }

    public void setBannerThumbImage(String bannerThumbImage) {
        this.bannerThumbImage = bannerThumbImage;
    }
}
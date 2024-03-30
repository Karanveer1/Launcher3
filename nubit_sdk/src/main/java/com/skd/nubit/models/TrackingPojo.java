package com.skd.nubit.models;

/**
 * Created by robi on 12/08/17.
 */

public class TrackingPojo {

    private String rowID = "";
    private String id = "";
    private String deviceID = "";
    private String deviceModel = "";
    private String url = "";
    private String redirectURL = "";
    private String category = "";
    private String date = "";
    private String count = "";
    private String launch = "";
    private String ipAddress = "";
    private String userAgent = "";

    public String getPlay_duration() {
        return play_duration;
    }

    public void setPlay_duration(String play_duration) {
        this.play_duration = play_duration;
    }

    private String play_duration="";

    public String getContentCategory() {
        return contentCategory;
    }

    public void setContentCategory(String contentCategory) {
        this.contentCategory = contentCategory;
    }

    private String contentCategory = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title = "";

    public String getRowID() {
        return rowID;
    }

    public void setRowID(String rowID) {
        this.rowID = rowID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getLaunch() {
        return launch;
    }

    public void setLaunch(String launch) {
        this.launch = launch;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}

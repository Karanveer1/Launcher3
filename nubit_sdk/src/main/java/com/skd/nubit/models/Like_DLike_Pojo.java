package com.skd.nubit.models;

public class Like_DLike_Pojo {



    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getRedirectURL() {
        return redirectURL;
    }

    public void setRedirectURL(String redirectURL) {
        this.redirectURL = redirectURL;
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }




    public String getNewsLanguage() {
        return newsLanguage;
    }

    public void setNewsLanguage(String newsLanguage) {
        this.newsLanguage = newsLanguage;
    }

    private String newsLanguage;

    private String eventType;
    private String redirectURL;
    private String pubName;

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    private String newsTitle;

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    private String eventDate;

    public String getRowID() {
        return rowID;
    }

    public void setRowID(String rowID) {
        this.rowID = rowID;
    }

    private String rowID = "";

}

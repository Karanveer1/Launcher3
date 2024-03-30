package com.skd.nubit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by robi  on 27/12/17.
 */

public class Configuration {

    public String getVideo_share() {
        return video_share;
    }

    public void setVideo_share(String video_share) {
        this.video_share = video_share;
    }

    private String video_share;

    public String getSocial_login() {
        return social_login;
    }

    public void setSocial_login(String social_login) {
        this.social_login = social_login;
    }

    String social_login;

    public String getBanner_type_home() {
        return banner_type_home;
    }

    public void setBanner_type_home(String banner_type_home) {
        this.banner_type_home = banner_type_home;
    }

    private String banner_type_home;

    public String getAd_id_chanel() {
        return ad_id_chanel;
    }

    public void setAd_id_chanel(String ad_id_chanel) {
        this.ad_id_chanel = ad_id_chanel;
    }

    public String getAd_type_chanel() {
        return ad_type_chanel;
    }

    public void setAd_type_chanel(String ad_type_chanel) {
        this.ad_type_chanel = ad_type_chanel;
    }

    private String ad_id_chanel;
    private String ad_type_chanel;


    public String getIscross_home() {
        return iscross_home;
    }

    public void setIscross_home(String iscross_home) {
        this.iscross_home = iscross_home;
    }

    private String iscross_home;

    public String getuTube_AdType() {
        return uTube_AdType;
    }

    public void setuTube_AdType(String uTube_AdType) {
        this.uTube_AdType = uTube_AdType;
    }

    public String getuTube_AdId() {
        return uTube_AdId;
    }

    public String getWallpaper_adID() {
        return wallpaper_adID;
    }

    public void setWallpaper_adID(String wallpaper_adID) {
        this.wallpaper_adID = wallpaper_adID;
    }

    private String wallpaper_adID;

    public String getLike_dislike() {
        return like_dislike;
    }

    public void setLike_dislike(String like_dislike) {
        this.like_dislike = like_dislike;
    }

    private String like_dislike;

    public void setuTube_AdId(String uTube_AdId) {
        this.uTube_AdId = uTube_AdId;
    }

    private String uTube_AdType;
    private String uTube_AdId;

    public String getNews_share_flg() {
        return news_share_flg;
    }

    public void setNews_share_flg(String news_share_flg) {
        this.news_share_flg = news_share_flg;
    }

    private String news_share_flg;

    public String getWallpaper_share_flg() {
        return wallpaper_share_flg;
    }


    private String wallpaper_share_flg;

    public String getNews_pub_shwHide() {
        return news_pub_shwHide;
    }


    private String news_pub_shwHide;


    public String getVideoCat_showHide() {
        return videoCat_showHide;
    }


    public String getVideoShare_showHide() {
        return videoShare_showHide;
    }


    public String getWallpaper_share() {
        return wallpaper_share;
    }


    private String wallpaper_share;

    public String getYuptv_update_version() {
        return yuptv_update_version;
    }


    private String yuptv_update_version;


    private String videoCat_showHide;
    private String videoShare_showHide;

    public String getShareBy_brandName() {
        return shareBy_brandName;
    }

    public void setShareBy_brandName(String shareBy_brandName) {
        this.shareBy_brandName = shareBy_brandName;
    }

    private String shareBy_brandName;


    public String getS3_access_key() {
        return s3_access_key;
    }


    public String getS3_secret_key() {
        return s3_secret_key;
    }


    public void setWallpaper_share_flg(String wallpaper_share_flg) {
        this.wallpaper_share_flg = wallpaper_share_flg;
    }

    public void setNews_pub_shwHide(String news_pub_shwHide) {
        this.news_pub_shwHide = news_pub_shwHide;
    }

    public void setWallpaper_share(String wallpaper_share) {
        this.wallpaper_share = wallpaper_share;
    }

    public void setYuptv_update_version(String yuptv_update_version) {
        this.yuptv_update_version = yuptv_update_version;
    }

    public void setVideoCat_showHide(String videoCat_showHide) {
        this.videoCat_showHide = videoCat_showHide;
    }

    public void setVideoShare_showHide(String videoShare_showHide) {
        this.videoShare_showHide = videoShare_showHide;
    }

    public String getBucket_name() {
        return bucket_name;
    }

    public void setBucket_name(String bucket_name) {
        this.bucket_name = bucket_name;
    }

    public void setS3_access_key(String s3_access_key) {
        this.s3_access_key = s3_access_key;
    }

    public void setS3_secret_key(String s3_secret_key) {
        this.s3_secret_key = s3_secret_key;
    }

    public String getLanguageSettingsButton() {
        return languageSettingsButton;
    }

    public void setLanguageSettingsButton(String languageSettingsButton) {
        this.languageSettingsButton = languageSettingsButton;
    }

    public void setShare_content(String share_content) {
        this.share_content = share_content;
    }

    public void setDefault_language(String default_language) {
        this.default_language = default_language;
    }

    public void setAdmob_banner_placemnt_id(String admob_banner_placemnt_id) {
        this.admob_banner_placemnt_id = admob_banner_placemnt_id;
    }

    @SerializedName("bucket_name")
    @Expose
    private String bucket_name;

    @SerializedName("s3_access_key")
    @Expose
    private String s3_access_key;

    @SerializedName("s3_secret_key")
    @Expose
    private String s3_secret_key;
    @SerializedName("lng_setting_btn")
    @Expose
    private String languageSettingsButton;


    public String getAdmob_cache() {
        return admob_cache;
    }

    public void setAdmob_cache(String admob_cache) {
        this.admob_cache = admob_cache;
    }

    @SerializedName("admob_cache")
    @Expose
    private String admob_cache;


    private String share_content;


    public String getNews_share() {
        return news_share;
    }

    public void setNews_share(String news_share) {
        this.news_share = news_share;
    }

    private String news_share;


    public String getShare_content() {
        return share_content;
    }

    public String getDefault_language() {
        return default_language;
    }


    @SerializedName("default_language")
    @Expose
    private String default_language;


    private String admob_banner_placemnt_id;


    public String getAdmob_banner_placemnt_id() {
        return admob_banner_placemnt_id;
    }


}
package com.skd.nubit.models;



/**
 * Created by robi on 5/7/17.
 */

public class External_game_pojo {


    private String id;
    private String title;
    private String category;
    private String banner_image;
    private String banner_thumb_image;
    private String output_link;
    private String package_name;
    private String action;
    private String portrait;
    private String redirect_link;
    private String ad_unit_id;
    private String ad_network;


    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }
    public String getBtn_label() {
        return btn_label;
    }

    public void setBtn_label(String btn_label) {
        this.btn_label = btn_label;
    }

    private String btn_label;



    private boolean isAddLoaded;

    private boolean isAddLoading;
    private String open_with;
    private String ad_type;

    public String getAd_type() {
        return ad_type;
    }

    public void setAd_type(String ad_type) {
        this.ad_type = ad_type;
    }

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

    public String getBanner_image() {
        return banner_image;
    }

    public void setBanner_image(String banner_image) {
        this.banner_image = banner_image;
    }

    public String getBanner_thumb_image() {
        return banner_thumb_image;
    }

    public void setBanner_thumb_image(String banner_thumb_image) {
        this.banner_thumb_image = banner_thumb_image;
    }

    public String getOutput_link() {
        return output_link;
    }

    public void setOutput_link(String output_link) {
        this.output_link = output_link;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
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

}

package com.skd.nubit.models;



/**
 * Created by robi on 2/7/17.
 */

public class TopApps_Pojo {


    private String id;
    private String link_type;
    private String title;
    private String banner_thumb_image;
    private String output_link;

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    private String package_name;

    private String tag_name;
    private String color;
    private String redirect_link;
    private boolean isAddLoaded;

    private boolean isAddLoading;
    private String ad_unit_id;
    private String ad_network;
    private String open_with;
    private String ad_type;

    public String getButton_label() {
        return button_label;
    }

    public void setButton_label(String button_label) {
        this.button_label = button_label;
    }

    private String button_label;

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

    public String getLink_type() {
        return link_type;
    }

    public void setLink_type(String link_type) {
        this.link_type = link_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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


    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

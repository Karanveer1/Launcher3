package com.skd.nubit.models;

import java.util.List;

/**
 * Created by robi on 20/7/17.
 */

public class Floater_Category_Pojo {
    private String id;
    private String title;
    private String banner_image;

    public String getRedirect_link() {
        return redirect_link;
    }

    public void setRedirect_link(String redirect_link) {
        this.redirect_link = redirect_link;
    }

    public String getOpen_with() {
        return open_with;
    }

    public void setOpen_with(String open_with) {
        this.open_with = open_with;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    private String redirect_link;
    private String open_with;
    private String package_name;
    private List<Floater_sub_Pojo> data = null;

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

    public String getBanner_image() {
        return banner_image;
    }

    public void setBanner_image(String banner_image) {
        this.banner_image = banner_image;
    }

    public List<Floater_sub_Pojo> getData() {
        return data;
    }

    public void setData(List<Floater_sub_Pojo> data) {
        this.data = data;
    }

}


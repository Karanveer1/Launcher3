package com.skd.nubit.models;

/**
 * Created by robi on 24/7/17.
 */

public class Floater_sub_Pojo {

    private String title;
    private String banner_image;


    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    private String package_name;
    private String output_link;
    private String redirect_link;
    private String open_with;

    public String getOpen_with() {
        return open_with;
    }

    public void setOpen_with(String open_with) {
        this.open_with = open_with;
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



    public String getOutput_link() {
        return output_link;
    }

    public void setOutput_link(String output_link) {
        this.output_link = output_link;
    }


    public String getRedirect_link() {
        return redirect_link;
    }

    public void setRedirect_link(String redirect_link) {
        this.redirect_link = redirect_link;
    }

}
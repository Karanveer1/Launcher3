package com.skd.nubit.models;

/**
 * Created by robi on 27/11/17.
 */

public class SearchBar {

    String redirect_link;
    String id;
    String title;
    String banner_image;
    String output_link;
    String open_with;

    public String getRedirect_link() {
        return redirect_link;
    }

    public void setRedirect_link(String redirect_link) {
        this.redirect_link = redirect_link;
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

    public String getOpen_with() {
        return open_with;
    }

    public void setOpen_with(String open_with) {
        this.open_with = open_with;
    }
}

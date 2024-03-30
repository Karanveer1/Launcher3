package com.skd.nubit.models;

/**
 * Created by robi on 27/9/17.
 */

public class NavigationPojo {

    private String drawer_item_name;
    private String drawer_icon;
    private String redirect_url;
    private String open_with;

    public String getOpen_with() {
        return open_with;
    }

    public void setOpen_with(String open_with) {
        this.open_with = open_with;
    }

    public String getDrawer_item_name() {
        return drawer_item_name;
    }

    public void setDrawer_item_name(String drawer_item_name) {
        this.drawer_item_name = drawer_item_name;
    }

    public String getDrawer_icon() {
        return drawer_icon;
    }

    public void setDrawer_icon(String drawer_icon) {
        this.drawer_icon = drawer_icon;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }

}

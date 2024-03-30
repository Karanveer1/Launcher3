package com.skd.nubit.models;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by robi on 3/7/17.
 */

public class Wallpaper_Pojo implements Parcelable

{

    private String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
    private String banner_thumb_image;
    private String banner_image;
    private String redirect_link;
    private boolean isAddLoaded;

    private boolean isAddLoading;
    private String ad_unit_id;
    private String ad_network;

    private String open_with;

    private String ad_type;

    protected Wallpaper_Pojo(Parcel in) {
        id = in.readString();
        banner_thumb_image = in.readString();
        banner_image = in.readString();
        redirect_link = in.readString();
        isAddLoaded = in.readByte() != 0;
        isAddLoading = in.readByte() != 0;
        ad_unit_id = in.readString();
        ad_network = in.readString();
        open_with = in.readString();
        ad_type = in.readString();
        package_name = in.readString();
        wallpaper_type = in.readString();
    }

    public static final Creator<Wallpaper_Pojo> CREATOR = new Creator<Wallpaper_Pojo>() {
        @Override
        public Wallpaper_Pojo createFromParcel(Parcel in) {
            return new Wallpaper_Pojo(in);
        }

        @Override
        public Wallpaper_Pojo[] newArray(int size) {
            return new Wallpaper_Pojo[size];
        }
    };

    public String getAd_type() {
        return ad_type;
    }

    public void setAd_type(String ad_type) {
        this.ad_type = ad_type;
    }


    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    private String package_name;

    public String getWallpaper_type() {
        return wallpaper_type;
    }

    public void setWallpaper_type(String wallpaper_type) {
        this.wallpaper_type = wallpaper_type;
    }

    private String wallpaper_type;

    public String getOpen_with() {
        return open_with;
    }

    public void setOpen_with(String open_with) {
        this.open_with = open_with;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getBanner_thumb_image() {
        return banner_thumb_image;
    }

    public void setBanner_thumb_image(String banner_thumb_image) {
        this.banner_thumb_image = banner_thumb_image;
    }

    public String getBanner_image() {
        return banner_image;
    }

    public void setBanner_image(String banner_image) {
        this.banner_image = banner_image;
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

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(banner_thumb_image);
        parcel.writeString(banner_image);
        parcel.writeString(redirect_link);
        parcel.writeByte((byte) (isAddLoaded ? 1 : 0));
        parcel.writeByte((byte) (isAddLoading ? 1 : 0));
        parcel.writeString(ad_unit_id);
        parcel.writeString(ad_network);
        parcel.writeString(open_with);
        parcel.writeString(ad_type);
        parcel.writeString(package_name);
        parcel.writeString(wallpaper_type);
    }
}
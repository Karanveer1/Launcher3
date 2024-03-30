
package com.skd.nubit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("modal_num")
    @Expose
    private String modalNum;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String _long;
    @SerializedName("operator")
    @Expose
    private String operator;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("setting_btn_display")
    @Expose
    private String settingBtnDisplay;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("configuration")
    @Expose
    private Configuration configuration;


        private List<Object> slot_rotation;



    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getModalNum() {
        return modalNum;
    }

    public void setModalNum(String modalNum) {
        this.modalNum = modalNum;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSettingBtnDisplay() {
        return settingBtnDisplay;
    }

    public void setSettingBtnDisplay(String settingBtnDisplay) {
        this.settingBtnDisplay = settingBtnDisplay;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }


    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public List<Object> getSlot_rotation() {
        return slot_rotation;
    }

    public void setSlot_rotation(List<Object> slot_rotation) {
        this.slot_rotation = slot_rotation;
    }


}
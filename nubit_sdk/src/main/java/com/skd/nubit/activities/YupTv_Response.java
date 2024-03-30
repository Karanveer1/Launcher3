package com.skd.nubit.activities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


class YupTv_Response {


    public NubitYupTv getNubitYupTv() {
        return nubitYupTv;
    }

    public void setNubitYupTv(NubitYupTv nubitYupTv) {
        this.nubitYupTv = nubitYupTv;
    }

    @SerializedName("nubitYupTv")
    @Expose
    private NubitYupTv nubitYupTv;
}



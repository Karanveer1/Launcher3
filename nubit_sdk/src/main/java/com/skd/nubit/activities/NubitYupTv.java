package com.skd.nubit.activities;

import com.skd.nubit.models.YupTv_Chanel_Pojo;
import com.skd.nubit.models.Yuptv_lang_pojo;

import java.util.List;

class NubitYupTv {


    public List<Yuptv_lang_pojo> getLanguages() {
        return Languages;
    }

    public void setLanguages(List<Yuptv_lang_pojo> languages) {
        Languages = languages;
    }

    public List<YupTv_Chanel_Pojo> getChannel_list() {
        return channel_list;
    }

    public void setChannel_list(List<YupTv_Chanel_Pojo> channel_list) {
        this.channel_list = channel_list;
    }

    List<Yuptv_lang_pojo> Languages;
    List<YupTv_Chanel_Pojo> channel_list;
}

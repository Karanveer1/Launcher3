package com.skd.nubit.mycallbacks;

public class Video_Cat_CallBAck {

    private static Video_Cat_CallBAck video_cat_callBAck;

    private interface_VideoCat interface_videoCat;

    public static Video_Cat_CallBAck getInstance() {
        if (video_cat_callBAck == null) {
            video_cat_callBAck = new Video_Cat_CallBAck();
        }

        return video_cat_callBAck;
    }

    private Video_Cat_CallBAck() {

    }

    public interface interface_VideoCat {
        void onVideoCatSelection();

    }

    public void init_interface_VideoCat(interface_VideoCat interface_videoCat) {
        this.interface_videoCat = interface_videoCat;

    }

    public void notifyOnVideoCatSelection() {
        if (interface_videoCat != null) {
            interface_videoCat.onVideoCatSelection();
        }
    }

    public void releaseCallback() {
        interface_videoCat = null;
        video_cat_callBAck = null;

    }


}





package com.skd.nubit.mycallbacks;

public class IsScrollingCallBack {
    public static IsScrollingCallBack myIsScrollingCallBack;
    private IsScrollingCallBack.interfaceScrollUpdate update_scrool_listener;

    public interface interfaceScrollUpdate {
        void update_onScrollStart(boolean z,String callFrom,int position);
    }

    public static IsScrollingCallBack getInstance() {
        if (myIsScrollingCallBack == null) {
            myIsScrollingCallBack = new IsScrollingCallBack();
        }
        return myIsScrollingCallBack;
    }

    public void setListener(IsScrollingCallBack.interfaceScrollUpdate listener) {
        this.update_scrool_listener = listener;
    }

    public void notifyonScroll(boolean status,String callFrom,int position) {
        //Log.d("Update","Notify");
        if (this.update_scrool_listener != null) {
            this.update_scrool_listener.update_onScrollStart(status,callFrom,position);
            // Log.d("Update","Notify,listener not null");
        }
    }
}

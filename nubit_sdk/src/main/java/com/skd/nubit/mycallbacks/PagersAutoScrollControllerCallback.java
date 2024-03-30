package com.skd.nubit.mycallbacks;

public class PagersAutoScrollControllerCallback {
    public static PagersAutoScrollControllerCallback myPagersAutoScrollControllerCallback;
    private UpdateScroll update_scrool_listener;

    public interface UpdateScroll {
        void updateScrollCallBack(boolean z,String callFrom,int position);
    }

    public static PagersAutoScrollControllerCallback getInstance() {
        if (myPagersAutoScrollControllerCallback == null) {
            myPagersAutoScrollControllerCallback = new PagersAutoScrollControllerCallback();
        }
        return myPagersAutoScrollControllerCallback;
    }

    public void setListener(UpdateScroll listener) {
        this.update_scrool_listener = listener;
    }

    public void notify(boolean status,String callFrom,int position) {
        //Log.d("Update","Notify");
        if (this.update_scrool_listener != null) {
            this.update_scrool_listener.updateScrollCallBack(status,callFrom,position);
            // Log.d("Update","Notify,listener not null");
        }
    }
}

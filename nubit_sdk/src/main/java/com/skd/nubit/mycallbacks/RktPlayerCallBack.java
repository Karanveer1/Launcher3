package com.skd.nubit.mycallbacks;

public class RktPlayerCallBack {

    public static RktPlayerCallBack myRktPlayerCallBack;
    private RktPlayerCallBack.UpdateonPlayerInit update_scrool_listener;

    public interface UpdateonPlayerInit {
        void updatePlayerCallBack(boolean z,String callingFrom);
    }

    public static RktPlayerCallBack getInstance() {
        if (myRktPlayerCallBack == null) {
            myRktPlayerCallBack = new RktPlayerCallBack();
        }
        return myRktPlayerCallBack;
    }

    public void setListener(RktPlayerCallBack.UpdateonPlayerInit listener) {
        this.update_scrool_listener = listener;
    }

    public void notifyByPlayer(boolean status,String playerStatus) {
        //Log.d("Update","Notify");
        if (this.update_scrool_listener != null) {
            this.update_scrool_listener.updatePlayerCallBack(status,playerStatus);
            // Log.d("Update","Notify,listener not null");
        }
    }
}



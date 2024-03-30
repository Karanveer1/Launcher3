package com.skd.nubit.mycallbacks;

public class RefreshDataCallBack {
    private static RefreshDataCallBack refresh_cat_callBAck;

    private interface_RefreshData interface_RefreshData;

    public static RefreshDataCallBack getInstance() {
        if (refresh_cat_callBAck == null) {
            refresh_cat_callBAck = new RefreshDataCallBack();
        }

        return refresh_cat_callBAck;
    }

    private RefreshDataCallBack() {

    }

    public interface interface_RefreshData {
        void onDataLoadStart();

        void onDataLoadFinish();

        void noInternetConnection();

    }

    public void init_interface_RefreshData(interface_RefreshData interface_RefreshData) {
        this.interface_RefreshData = interface_RefreshData;

    }

    public void notifyOnRefreshFinish() {
        if (interface_RefreshData != null) {
            interface_RefreshData.onDataLoadFinish();
        }
    }

    public void notifyOnRefreshStart() {
        if (interface_RefreshData != null) {
            interface_RefreshData.onDataLoadStart();
        }
    }

    public void notifyOnNoInternet() {
        if (interface_RefreshData != null) {
            interface_RefreshData.noInternetConnection();
        }
    }

    public void releaseCallback() {
        interface_RefreshData = null;
        refresh_cat_callBAck = null;

    }


}




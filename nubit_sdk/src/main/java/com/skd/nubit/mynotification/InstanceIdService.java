/*
package com.rkt.lava.mynotification;

import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.rkt.lava.utilityclasses.MyApplication;





public class InstanceIdService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String str_token = FirebaseInstanceId.getInstance().getToken();
        if (!TextUtils.isEmpty(str_token)) {
            MyApplication.app_editor.putString("fcmToken", str_token).apply();

            Log.d("fcmToken",str_token);

        }


    }

}
*/

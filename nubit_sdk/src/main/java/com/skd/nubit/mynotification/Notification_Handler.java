package com.skd.nubit.mynotification;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.skd.nubit.R;
import com.skd.nubit.videoplayerstuffs.PlayerActivity;
import com.skd.nubit.videoplayerstuffs.YouTube_PlayerActivity;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

public class Notification_Handler extends AppCompatActivity {


    private String notification_id;
    private String notification_type;
    private String image_url;
    private String icon_url;
    private String title;
    private String redirect_url;
    private String notification_action;
    private String message;
    private String open_with;
    private String package_name;
    private String video_url;
    private String video_cat;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notification_handler);


        Intent notificationIntent = getIntent();

        notification_id = notificationIntent.getStringExtra("notification_id");
        notification_type = notificationIntent.getStringExtra("notification_type");
        image_url = notificationIntent.getStringExtra("image_url");
        title = notificationIntent.getStringExtra("title");
        redirect_url = notificationIntent.getStringExtra("redirect_url");
        notification_action = notificationIntent.getStringExtra("notification_action");
        message = notificationIntent.getStringExtra("message");
        open_with = notificationIntent.getStringExtra("open_with");
        package_name = notificationIntent.getStringExtra("package_name");
        icon_url = notificationIntent.getStringExtra("icon_url");
        video_url = notificationIntent.getStringExtra("video_url");
        video_cat = notificationIntent.getStringExtra("video_cat");


        MyApplication.getInstance().trackEvent("Notification Clicked", notification_id,
                MyUtility.getDeviceID(getApplicationContext()));

        if (!TextUtils.isEmpty(open_with)) {
            if (open_with.equalsIgnoreCase("4")) {
                Intent send = new Intent(this, PlayerActivity.class);
                send.putExtra("videoUrl", video_url);
                send.putExtra("fromNotification", true);
                send.putExtra("video_cat", video_cat);
                send.putExtra("imageURL", image_url);
                startActivity(send);
                finish();
            } else if (open_with.equalsIgnoreCase("5")) {
                Intent send = new Intent(this, YouTube_PlayerActivity.class);
                send.putExtra("get_YouTubeID", video_url);
                send.putExtra("fromNotification", true);
                send.putExtra("imageURL", image_url);
                startActivity(send);
                finish();
            } else {
                MyUtility.handleItemClick(this, package_name, redirect_url,
                        redirect_url, "Notification", open_with,
                        title);
                finish();
            }
        } else {
            MyUtility.handleItemClick(this, package_name, redirect_url,
                    redirect_url, "Notification", open_with, title);
            finish();
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*Intent sendToLog_reg = new Intent(Notification_Handler.this, MyApplication.class);
        startActivity(sendToLog_reg);*/
        Log.d("rkt_onBackPressed", "onBackPressed");
       /* MyUtility.OpenAppByPackageName(Notification_Handler.this, getPackageName());*/
    }
}

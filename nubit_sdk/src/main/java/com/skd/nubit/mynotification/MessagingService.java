
package com.skd.nubit.mynotification;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.skd.nubit.R;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


/**
 * Created by robi on 24/09/18.
 */


public class MessagingService extends FirebaseMessagingService {


    private String notification_id = "";
    private String notification_type = "";
    private String image_url = "";
    private String icon_url = "";
    private String video_url = "";
    private String title = "";
    private String redirect_url = "";
    private String notification_action = "";
    private String message = "";
    private String open_with = "";
    private String package_name = "";
    public static final String PRIMARY_CHANNEL = "default";

    @Override
    public void onNewToken(String newFcmToken) {
        super.onNewToken(newFcmToken);
        if (!TextUtils.isEmpty(newFcmToken)) {
            MyApplication.app_editor.putString("fcmToken", newFcmToken).apply();
        }

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        Log.d("fcmToken", "Msg Received");

        try {
            if (!remoteMessage.getData().isEmpty() && remoteMessage.getData().containsKey
                    ("notification_id")) {
                Map<String, String> data = null;
                try {
                    data = remoteMessage.getData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    notification_id = data.get("notification_id");
                } catch (Exception e) {
                    e.printStackTrace();
                    notification_id = "";
                }
                try {
                    notification_type = data.get("notification_type");
                } catch (Exception e) {
                    e.printStackTrace();
                    notification_type = "";
                }
                try {
                    image_url = data.get("image_url");
                } catch (Exception e) {
                    e.printStackTrace();
                    image_url = "";
                }
                try {
                    title = data.get("title");
                } catch (Exception e) {
                    e.printStackTrace();
                    title = "";
                }

                try {
                    video_url = data.get("video_url");
                } catch (Exception e) {
                    e.printStackTrace();
                    video_url = "";
                }
                try {
                    notification_action = data.get("notification_action");
                } catch (Exception e) {
                    e.printStackTrace();
                    notification_action = "";
                }
                try {
                    redirect_url = data.get("redirect_url");
                } catch (Exception e) {
                    e.printStackTrace();
                    redirect_url = "";
                }

                try {
                    package_name = data.get("package_name");
                } catch (Exception e) {
                    e.printStackTrace();
                    package_name = "";
                }
                try {
                    message = data.get("message");
                } catch (Exception e) {
                    e.printStackTrace();
                    message = "";
                }
                try {
                    open_with = data.get("open_with");
                } catch (Exception e) {
                    e.printStackTrace();
                    open_with = "";
                }

                try {
                    icon_url = data.get("icon_url");
                } catch (Exception e) {
                    e.printStackTrace();
                    icon_url = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            MyApplication.getInstance().trackEvent("Notification Received", notification_id,
                    MyUtility.getDeviceID(getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (!TextUtils.isEmpty(notification_type) && notification_type.equalsIgnoreCase("0")) {
            // notification_type=0 means, notification should be display in notification tray

            Big_Notification(notification_id, notification_type, image_url, title, redirect_url,
                    notification_action,
                    message, open_with, package_name, icon_url, video_url);
        }
        if (!TextUtils.isEmpty(notification_type) && notification_type.equalsIgnoreCase("1")) {
            // notification_type=1 means silent notification
            MyApplication.getInstance().trackEvent("Silent Notification", notification_id,
                    MyUtility.getDeviceID(getApplicationContext()));

            MyUtility.handleItemClick(getApplicationContext(), package_name, redirect_url,
                    redirect_url, "",
                    open_with, "");


        }


    }


    public void Big_Notification(String notification_id, String notification_type, String
            image_url, String title,
                                 String redirect_url, String notification_action,
                                 String message, String open_with, String package_name, String
                                         icon_url, String video_url) {
        try {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(PushConfig
                    .getInstance().getContext(), PRIMARY_CHANNEL);

            Intent notificationIntent = new Intent(getApplicationContext(), Notification_Handler
                    .class);
            notificationIntent.putExtra("notification_id", notification_id);
            notificationIntent.putExtra("notification_type", notification_type);
            notificationIntent.putExtra("image_url", image_url);
            notificationIntent.putExtra("title", title);
            notificationIntent.putExtra("redirect_url", redirect_url);
            notificationIntent.putExtra("notification_action", notification_action);
            notificationIntent.putExtra("message", message);
            notificationIntent.putExtra("open_with", open_with);
            notificationIntent.putExtra("package_name", package_name);
            notificationIntent.putExtra("icon_url", icon_url);
            notificationIntent.putExtra("video_url", video_url);


            Bitmap largeIcon = null;
            Bitmap bigBanner = null;

        /*    Bitmap appIcon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                    R.drawable.lnf_splash);*/


            NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();

            try {
                style.setBigContentTitle(title);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                style.setSummaryText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!TextUtils.isEmpty(icon_url)) {
                //Below code get the bitmap from URL image and then set it as Large Icon in
                // notification

                try {
                    largeIcon = getBitmapFromURL(icon_url);
                    builder.setLargeIcon(largeIcon);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            if (!TextUtils.isEmpty(image_url)) {
                //Below code get the bitmap from URL image and then set it as Large banner image
                // in notification

                try {
                    bigBanner = getBitmapFromURL(image_url);
                    style.bigPicture(bigBanner);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // Below two lines is used for showing the title, logo and big description.
                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(message)) {
                    try {
                        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message)).
                                setContentTitle(title);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (!TextUtils.isEmpty(title) && TextUtils.isEmpty(message)) {
                    try {
                        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message)).
                                setContentTitle(title);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }
            /*Below line was working good but I have commented this to get the white icon rid in
            few devices, So i added sdk check below....Lets see if it works in all devices*/

            /*  builder.setSmallIcon(R.mipmap.app_logo);*/

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder.setSmallIcon(R.drawable.nb_icon);
                builder.setColor(getResources().getColor(R.color.nubit_light_pink));
            } else {
                builder.setSmallIcon(R.mipmap.logo_nubit);
            }

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                PendingIntent contentIntent = PendingIntent.getActivity(this, Integer.parseInt
                        (notification_id), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setAutoCancel(true)

                        .setContentIntent(contentIntent);
            }else {

                PendingIntent contentIntent = PendingIntent.getActivity(this, Integer.parseInt
                        (notification_id), notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                builder.setAutoCancel(true)

                        .setContentIntent(contentIntent);

            }

            if (style != null && !TextUtils.isEmpty(image_url)) {
                builder.setStyle(style);
            }

  /*      if (views != null)
            builder.setContent(views);*/
            Notification notification = builder.build();
            notification.flags |= false ? Notification.FLAG_ONGOING_EVENT : Notification
                    .FLAG_AUTO_CANCEL;
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.defaults |= Notification.DEFAULT_VIBRATE;
            notification.defaults |= Notification.DEFAULT_ALL;
            NotificationManager notificationManager = (NotificationManager) getSystemService
                    (NOTIFICATION_SERVICE);

            NotificationChannel notificationChannel = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = new NotificationChannel(PRIMARY_CHANNEL,
                        "System Settings", NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableLights(true);
                notificationChannel.canShowBadge();
                notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                notificationChannel.enableVibration(true);
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                notificationChannel.setSound(uri, new AudioAttributes.Builder().setContentType
                        (AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setLegacyStreamType(AudioManager.STREAM_NOTIFICATION)
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION_EVENT).build());
                notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400,
                        300,
                        200, 400});
                notificationManager.createNotificationChannel(notificationChannel);
            }

            if (!TextUtils.isEmpty(notification_id)) {
                try {
                    notificationManager.notify(Integer.parseInt(notification_id), notification);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    notificationManager.notify(970970, notification);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private Bitmap getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }


}
package com.skd.nubit.utilityclasses;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.skd.nubit.R;

public class RobiUpdateDialog extends BaseDialogFragment {

    ImageView img_full_screen, imageView_close;
    FrameLayout fram_layout_fullscreen;
    WebView splash_webview;

    String isFullScreenBanner = "0";
    String delay = "";
    String banner_image = "";
    String ad_unit_id = "";
    String redirect_link = "";
    String open_with = "";
    String title = "";
    String webview_url = "";
    String splash_time = "";


    @Override
    public int getLayoutId() {
        return R.layout.layout_force_update;
    }

    public static RobiUpdateDialog newInstance(String isFullScreenBanner, String delay, String
            banner_image, String ad_unit_id, String redirect_link, String open_with, String
                                                       title, String webview_url, String
            splash_time) {

        Bundle args = new Bundle();
        if (TextUtils.isEmpty(webview_url)) {
            args.putString("isFullScreenBanner", isFullScreenBanner);
            args.putString("delay", delay);
            args.putString("banner_image", banner_image);
            args.putString("ad_unit_id", ad_unit_id);
            args.putString("redirect_link", redirect_link);
            args.putString("open_with", open_with);
            args.putString("title", title);

        } else {
            args.putString("webview_url", webview_url);
            args.putString("splash_time", splash_time);
        }


        RobiUpdateDialog fragment = new RobiUpdateDialog();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onDialogCreated(Bundle savedInstanceState) {

        imageView_close = (ImageView) findViewById(R.id.imageView_close);
        img_full_screen = (ImageView) findViewById(R.id.img_full_screen);
        splash_webview = (WebView) findViewById(R.id.splash_webview);
        fram_layout_fullscreen = (FrameLayout) findViewById(R.id.fram_layout_fullscreen);


        try {
            webview_url = getArguments().getString("webview_url");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(webview_url)) {
            splash_webview.setVisibility(View.GONE);

            try {
                isFullScreenBanner = getArguments().getString("isFullScreenBanner");
            } catch (Exception e) {
                e.printStackTrace();
                isFullScreenBanner = "";
            }

            try {
                banner_image = getArguments().getString("banner_image");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                delay = getArguments().getString("delay");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                redirect_link = getArguments().getString("redirect_link");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ad_unit_id = getArguments().getString("ad_unit_id");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                open_with = getArguments().getString("open_with");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                title = getArguments().getString("title");
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (isFullScreenBanner.equalsIgnoreCase("1")) {
                imageView_close.setVisibility(View.GONE);
                img_full_screen.setVisibility(View.VISIBLE);
                fram_layout_fullscreen.setVisibility(View.VISIBLE);

            }

            if (isFullScreenBanner.equalsIgnoreCase("0")) {
                imageView_close.setVisibility(View.VISIBLE);
                img_full_screen.setVisibility(View.VISIBLE);
                fram_layout_fullscreen.setVisibility(View.VISIBLE);

            }

            try {
                Glide.with(getActivity()).load(banner_image)
                        .crossFade().placeholder(R.drawable.placeholder_wallpaper)
                        .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable
                        .placeholder_wallpaper)
                        .into(img_full_screen);
            } catch (Exception e) {
                e.printStackTrace();
            }


            imageView_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDialog().dismiss();
                }
            });

            img_full_screen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!TextUtils.isEmpty(redirect_link) && !TextUtils.isEmpty(open_with)) {
                        MyApplication.app_editor.putString("last_bannerShow", MyUtility
                                .getCurrentTime())
                                .apply();
                        MyUtility.RedirectMe(getActivity(), redirect_link, open_with,
                                title);
                    }
             /*   Intent intent = new Intent("close_custom_window");
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);*/
                    getDialog().dismiss();
                }
            });
        } else {
            img_full_screen.setVisibility(View.GONE);
            imageView_close.setVisibility(View.GONE);
            splash_webview.setVisibility(View.VISIBLE);
            fram_layout_fullscreen.setVisibility(View.GONE);

            try {
                splash_time = getArguments().getString("splash_time");

            } catch (Exception e) {
                e.printStackTrace();
            }
            int splash_duration;
            try {
                splash_duration = Integer.parseInt(splash_time);
            } catch (Exception e) {
                splash_duration = 2000;
                e.printStackTrace();
            }


            WebSettings s = splash_webview.getSettings();
            s.setBuiltInZoomControls(false);
//            s.setAppCachePath(getActivity().getCacheDir().getPath());
//            s.setAppCacheEnabled(true);
            s.setCacheMode(WebSettings.LOAD_DEFAULT);
            s.setUseWideViewPort(true);
            s.setLoadWithOverviewMode(true);
            s.setJavaScriptEnabled(true);
            splash_webview.getSettings().setDomStorageEnabled(true);
            splash_webview.getSettings().setDatabaseEnabled(true);
            splash_webview.loadUrl(webview_url);

            splash_webview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MyApplication.app_editor.putString("last_bannerShow", MyUtility
                            .getCurrentTime())
                            .apply();
                    splash_webview.setVisibility(View.GONE);
                    getDialog().dismiss();

                }
            });


            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    MyApplication.app_editor.putString("last_bannerShow", MyUtility
                            .getCurrentTime())
                            .apply();
                    splash_webview.setVisibility(View.GONE);
                    getDialog().dismiss();
                }
            }, splash_duration);


        }


    }
}



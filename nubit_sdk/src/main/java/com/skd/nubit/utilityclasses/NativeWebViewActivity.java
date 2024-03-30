package com.skd.nubit.utilityclasses;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.skd.nubit.R;


public class NativeWebViewActivity extends BaseActivity {


    WebView webView;
    HorizontailProgressBar progressBar;
    String url = "";
    String title = "";
    RelativeLayout layoutAdMob;
    String get_yup_ad_id,get_yup_ad_size;


    public static Intent newInstance(Context context, String url, String title) {
        Intent intent = null;
        try {
            intent = new Intent(context, NativeWebViewActivity.class);
            intent.putExtra("url", url);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("title", title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intent;
    }


    private void gettingExtra() {

        try {
            url = getIntent().getStringExtra("url");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            title = getIntent().getStringExtra("title");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_web_view;
    }


    @Override
    protected void setupUI(Bundle bundle) {

        gettingExtra();

        try {
            setUpToolbar(TextUtils.isEmpty(title) ? "Loading url" : title)
                    .setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

        progressBar = findViewById(R.id.horizontalprogress);
        webView = findViewById(R.id.webview);
        layoutAdMob = findViewById(R.id.layoutAdMob);

        WebSettings s = webView.getSettings();
        s.setBuiltInZoomControls(false);
//        s.setAppCachePath(getCacheDir().getPath());
//        s.setAppCacheEnabled(true);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            try {
                webView.getSettings().setDatabasePath("/data/data/" + webView.getContext()
                        .getPackageName() + "/databases/");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            webView.loadUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);

            }
        });

        progressBar.setOnProgressBarListener(new HorizontailProgressBar.OnProgressBarListener() {
            @Override
            public void onProgressChange(int current, int max) {
                if (current == max) {
                    progressBar.setVisibility(View.GONE);
                } else if (progressBar.getVisibility() == View.GONE) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        //Below line for sowing the ads at bottom
        get_yup_ad_id = MyApplication.app_sharedPreferences.getString("admob_other_rkt", "");
        get_yup_ad_size = MyApplication.app_sharedPreferences.getString("admob_other_type",
                "");

        if(TextUtils.isEmpty(get_yup_ad_id))
        {
            get_yup_ad_id = MyApplication.app_sharedPreferences.getString("yup_ad_id", "");
            get_yup_ad_size = MyApplication.app_sharedPreferences.getString("yup_ad_size",
                    "");
        }


        if (!TextUtils.isEmpty(get_yup_ad_id)) {

            MyUtility.setUpBannerAd(NativeWebViewActivity.this, get_yup_ad_id, layoutAdMob,
                    get_yup_ad_size, null, "");
        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }
}


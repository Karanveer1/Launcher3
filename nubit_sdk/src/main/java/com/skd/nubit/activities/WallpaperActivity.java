package com.skd.nubit.activities;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.skd.nubit.R;
import com.skd.nubit.models.Wallpaper_Pojo;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by robi on 11/11/17.
 */

public class WallpaperActivity extends AppCompatActivity implements View.OnClickListener {


    private ViewPagerAdapter adapter;
    private ViewPager myPager;

    private ArrayList<Wallpaper_Pojo> data;

    private ProgressBar wallpaper_progress;

    String admob_w_id;

    int wallpaper_Click_posi;
    ImageView img_fullScreen_Wallpaper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wallpaper_viewpager);
        wallpaper_progress = findViewById(R.id.wallpaper_progress);
        ImageView btn_cancle = findViewById(R.id.btn_cancle);
        ImageView btn_go_back = findViewById(R.id.btn_go_back);
        ImageView btn_go_next = findViewById(R.id.btn_go_next);
        ImageView btn_set_wallpaper = findViewById(R.id.btn_set_wallpaper);
        ImageView img_wallpaper_share = findViewById(R.id.img_wallpaper_share);


        btn_cancle.setOnClickListener(this);
        btn_go_back.setOnClickListener(this);
        btn_go_next.setOnClickListener(this);
        btn_set_wallpaper.setOnClickListener(this);
        img_wallpaper_share.setOnClickListener(this);

        wallpaper_Click_posi = getIntent().getIntExtra("positionkey", 0);

        data = (ArrayList<Wallpaper_Pojo>) getIntent()
                .getSerializableExtra("bundle");
        adapter = new ViewPagerAdapter(this, data);
        myPager = findViewById(R.id.wallpaper_Pager);
        /*      myPager.setOffscreenPageLimit(0);*/
        myPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        myPager.setCurrentItem(wallpaper_Click_posi);


        admob_w_id = MyApplication.app_sharedPreferences.getString("interstitial_id_w", "");

        if (TextUtils.isEmpty(admob_w_id)) {
            admob_w_id = MyApplication.app_sharedPreferences.getString("wallpaper_adID", "");
        }

        if (!TextUtils.isEmpty(admob_w_id)) {
            init_Interstitial(WallpaperActivity.this, admob_w_id);
        }


        String wallpaper_share_flg = MyApplication.app_sharedPreferences.getString
                ("wallpaper_share_flg", "");
        if (!TextUtils.isEmpty(wallpaper_share_flg) & wallpaper_share_flg.equalsIgnoreCase("1")) {
            img_wallpaper_share.setVisibility(View.VISIBLE);
            /* img_wallpaper_share.setVisibility(View.GONE);*/
        } else {
            img_wallpaper_share.setVisibility(View.GONE);
        }

        MyApplication.getInstance().trackScreenView("Wallpaper Screen");

    }

    @Override
    public void onClick(View view) {


        int i = view.getId();
        if (i == R.id.btn_set_wallpaper) {
            new SetScreenWallpaper_Asyn().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


        } else if (i == R.id.btn_cancle) {
            finish();


        } else if (i == R.id.img_wallpaper_share) {
            Wallpaper_Pojo wallpaper_pojo = data.get(wallpaper_Click_posi);

            wallpaper_progress.setVisibility(View.VISIBLE);

            MyApplication.getInstance().trackEvent("Wallpaper Share", wallpaper_pojo
                    .getRedirect_link(), wallpaper_pojo.getTitle());
            Glide.with(WallpaperActivity.this)
                    .load(wallpaper_pojo.getBanner_thumb_image())
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super
                                Bitmap> glideAnimation) {
                            // you can do something with loaded bitmap here
                            wallpaper_progress.setVisibility(View.GONE);

                            try {
                                MyUtility.Glide_share(WallpaperActivity.this, resource);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });


        } else if (i == R.id.btn_go_back) {
            myPager.setCurrentItem(adapter.getItem(-1), true);

        } else if (i == R.id.btn_go_next) {
            myPager.setCurrentItem(adapter.getItem(+1), true);

        }

    }


    private class ViewPagerAdapter extends PagerAdapter {

        Context act;
        ArrayList<Wallpaper_Pojo> arraylist;

        public ViewPagerAdapter(Context act,
                                ArrayList<Wallpaper_Pojo> arraylist) {

            this.act = act;
            this.arraylist = arraylist;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);


            wallpaper_Click_posi = position;

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            LayoutInflater inflater = ((Activity) act).getLayoutInflater();
            View cont = inflater.inflate(R.layout.set_wallpaper, null,
                    true);
            final Wallpaper_Pojo resultp = arraylist.get(position);


            img_fullScreen_Wallpaper = cont.findViewById(R.id.img_fullScreen_Wallpaper);


            Glide.with(WallpaperActivity.this).load(resultp.getBanner_image()).thumbnail(0.5f)
                    .placeholder(R.drawable.placeholder_wallpaper)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.drawable
                    .placeholder_wallpaper)
                    .into(img_fullScreen_Wallpaper);

            try {
                if (!TextUtils.isEmpty(resultp.getAd_unit_id())) {
                /*    Log.d("Admob_Nubit",resultp.getAd_unit_id());*/

                    if (!TextUtils.isEmpty(admob_w_id)) {
                        init_Interstitial(WallpaperActivity.this, admob_w_id);
                    } else {
                        init_Interstitial(WallpaperActivity.this, resultp.getAd_unit_id());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            img_fullScreen_Wallpaper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if
                            (MyUtility.isConnectedToInternet()) {


                        if (!TextUtils.isEmpty(resultp.getWallpaper_type())) {
                            if ((resultp.getWallpaper_type()).equalsIgnoreCase
                                    ("set_and_redirect")) {
                                MyUtility.handleItemClick(WallpaperActivity.this, resultp
                                                .getPackage_name(), resultp.getRedirect_link(),
                                        resultp.getRedirect_link(), "wallpaper", resultp
                                                .getOpen_with(),
                                        getResources().getString(R.string.my_app_name));
                            }
                        }
                    } else {
                        MyUtility.NoInternet_Msg(WallpaperActivity.this);
                    }
                }
            });


            container.addView(cont, 0);

            return cont;
        }

        @Override
        public void destroyItem(ViewGroup arg0, int arg1, Object arg2) {
            arg0.removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == ((View) arg1);
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public int getCount() {

            try {
                return arraylist.size();
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }


        public int getItem(int i) {
            return myPager.getCurrentItem() + i;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }


    }


    private class SetScreenWallpaper_Asyn extends AsyncTask<Void, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            wallpaper_progress.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... params) {


            final WallpaperManager myWallpaperManager
                    = WallpaperManager.getInstance(WallpaperActivity.this);


            try {
                Wallpaper_Pojo res = data.get(wallpaper_Click_posi);
                InputStream ins = new URL(res.getBanner_image()).openStream();
                myWallpaperManager.setStream(ins);
                MyApplication.getInstance().trackEvent("Wallpaper Set",
                        res.getRedirect_link(), res.getTitle());


            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        public void onPostExecute(String args) {

            wallpaper_progress.setVisibility(View.GONE);
            Toast.makeText(WallpaperActivity.this, "Wallpaper has been set", Toast.LENGTH_SHORT)
                    .show();
            finish();

        }
    }


    public void init_Interstitial(Context interContext, String interstitial_id) {
        final InterstitialAd mInterstitialAd = new InterstitialAd(interContext);
        try {
            mInterstitialAd.setAdUnitId(interstitial_id);
            AdRequest adRequest = new AdRequest.Builder()
                    .build();
            mInterstitialAd.loadAd(adRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Create an Intent that will start the main activity.

            }

            @Override
            public void onAdLoaded() {

                try {
                    mInterstitialAd.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.

            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.

            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.

            }

        });

    }


}

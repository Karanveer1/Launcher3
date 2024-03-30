package com.skd.nubit.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.skd.nubit.R;
import com.skd.nubit.models.HomeVideoPojo;
import com.skd.nubit.utilityclasses.MyApplication;

import java.util.ArrayList;

public class YouTubeScreen_Adapter extends RecyclerView.Adapter<YouTubeScreen_Adapter
        .MyViewHolder> {
    private ArrayList<HomeVideoPojo> android;
    Context context;

    String getAdType, getAdID;


    public YouTubeScreen_Adapter(Context context, ArrayList<HomeVideoPojo> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public YouTubeScreen_Adapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .youtube_screen_items, viewGroup, false);

        YouTubeScreen_Adapter.MyViewHolder mViewHold = new YouTubeScreen_Adapter.MyViewHolder(view);

        getAdType = MyApplication.app_sharedPreferences.getString("admob_u_type", "");
        getAdID = MyApplication.app_sharedPreferences.getString("admob_u_rkt", "");


        if (TextUtils.isEmpty(getAdID)) {
            getAdType = MyApplication.app_sharedPreferences.getString("ad_type_chanel", "");
            getAdID = MyApplication.app_sharedPreferences.getString("ad_id_chanel", "");
        }
        return mViewHold;
    }

    @Override
    public void onBindViewHolder(YouTubeScreen_Adapter.MyViewHolder viewHolder, int i) {

        /*Log.d("TopYoutubeAdapter","TopYoutubeAdapter Calling");*/

        final HomeVideoPojo dataItem = android.get(i);
        final YouTubeScreen_Adapter.MyViewHolder myViewHolder = viewHolder;
        Glide.with(MyApplication.ctx).load(dataItem.getThumb())
                .crossFade().placeholder(R.drawable.placeholder_apps)
                .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder_apps)
                .into(myViewHolder.img_youtube);

     /*   Typeface custom_font = null;
        try {
            custom_font = Typeface.createFromAsset(context.getResources().getAssets(),
                    "fonts/MontserratAlternates-Regular.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        myViewHolder.txt_youtube_title.setText(dataItem.getTitle());
        myViewHolder.txt_channel_name.setText(dataItem.getCategory_name());

        if (!TextUtils.isEmpty(dataItem.getCategory_name())) {
            myViewHolder.txt_channel_name.setText(dataItem.getCategory_name());
           /* try {

                myViewHolder.txt_youtube_des.setTypeface(custom_font);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }


        if (!TextUtils.isEmpty(dataItem.getDescription())) {
            myViewHolder.txt_youtube_des.setText(dataItem.getDescription());
           /* try {

                myViewHolder.txt_youtube_des.setTypeface(custom_font);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }


        if (!TextUtils.isEmpty(getAdID)) {
            try {
                if ((i & 1) == 0 && (i != 2) && (i != 6) && (i != 10) && (i != 14) && (i != 18)
                        && (i
                        != 22) && (i != 26) && (i != 30) && (i != 34)) {


                    myViewHolder.txt_youtube_title.setVisibility(View.GONE);
                    myViewHolder.txt_channel_name.setVisibility(View.GONE);
                    myViewHolder.txt_youtube_des.setVisibility(View.GONE);
                    myViewHolder.layout_admob.setVisibility(View.VISIBLE);
                    setUpBannerAd(getAdID, myViewHolder.layout_admob);


                } else {

                    myViewHolder.txt_youtube_title.setVisibility(View.VISIBLE);
                    myViewHolder.txt_channel_name.setVisibility(View.VISIBLE);
                    myViewHolder.txt_youtube_des.setVisibility(View.VISIBLE);
                    myViewHolder.layout_admob.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                myViewHolder.txt_youtube_title.setVisibility(View.VISIBLE);
                myViewHolder.txt_channel_name.setVisibility(View.VISIBLE);
                myViewHolder.txt_youtube_des.setVisibility(View.VISIBLE);
                myViewHolder.layout_admob.setVisibility(View.GONE);
            }
        } else {
            myViewHolder.txt_youtube_title.setVisibility(View.VISIBLE);
            myViewHolder.txt_channel_name.setVisibility(View.VISIBLE);
            myViewHolder.txt_youtube_des.setVisibility(View.VISIBLE);
            myViewHolder.layout_admob.setVisibility(View.GONE);
        }












     /*   try {

            myViewHolder.txt_youtube_title.setTypeface(custom_font);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

      /*  try {

            myViewHolder.txt_channel_name.setTypeface(custom_font);
        } catch (Exception e) {
            e.printStackTrace();
        }*/


      /*  myViewHolder.layout_topYoutubeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MyUtility.isConnectedToInternet()) {
                    MyApplication.getInstance().trackEvent("Top Youtube", dataItem.getRedirect_link
                            (), dataItem.getTitle());
                    MyUtility.handleItemClick(context, dataItem.getPackageName(), dataItem
                                    .getRedirect_link(), dataItem.getBannerImage(),
                            "Top Youtube", dataItem.getOpenWith(), dataItem.getTitle());

                } else {
                    MyUtility.NoInternet_Msg(context);
                }


            }
        });*/


    }

    @Override
    public int getItemCount() {
        try {
            return android.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_youtube;

        private TextView txt_youtube_title, txt_channel_name, txt_youtube_des;
        RelativeLayout layout_topYoutubeItems;

        FrameLayout layout_admob;

        public MyViewHolder(View view) {
            super(view);
            img_youtube = view.findViewById(R.id.img_youtubes);
            txt_youtube_title = view.findViewById(R.id.txt_youtube_title);


            txt_channel_name = view.findViewById(R.id.txt_channel_name);
            txt_youtube_des = view.findViewById(R.id.txt_youtube_des);
            layout_topYoutubeItems = view.findViewById(R.id.layout_topYoutubeItems);
            layout_admob = view.findViewById(R.id.layout_admob);


        }


    }


    private void setUpBannerAd(String bannerPlacementId, final FrameLayout view) {
        final AdView adView = new AdView(context);
        adView.setAdUnitId(bannerPlacementId);
        /*  if (getAdType.equalsIgnoreCase("MEDIUM_RECTANGLE")) {*/
        adView.setAdSize(AdSize.MEDIUM_RECTANGLE);
       /* } else if (getAdType.equalsIgnoreCase("BANNER")) {
            adView.setAdSize(AdSize.BANNER);
        } else if (getAdType.equalsIgnoreCase("SMART_BANNER")) {
            adView.setAdSize(AdSize.SMART_BANNER);
        } else if (getAdType.equalsIgnoreCase("LARGE_BANNER")) {
            adView.setAdSize(AdSize.LARGE_BANNER);
        } else if (getAdType.equalsIgnoreCase("LEADERBOARD")) {
            adView.setAdSize(AdSize.LEADERBOARD);
        }*/

        adView.setId(R.id.adViews);
        adView.loadAd(new AdRequest.Builder().build());
        view.setVisibility(View.VISIBLE);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                // System.out.println(TAG + " onAdFailedToLoad " + i);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                //System.out.println(TAG + " onAdOpened");
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                try {
                    if (adView.getParent() == null) {
                        view.addView(adView);

                    } else {
                        ((ViewGroup) adView.getParent()).removeView(adView);

                        view.addView(adView);

                    }
                    // System.out.println(TAG + " banneraddloaded");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
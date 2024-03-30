package com.skd.nubit.adapters;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.skd.nubit.R;
import com.skd.nubit.models.TrendingNewsModel;
import com.skd.nubit.models.TrendingNewsModel;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Top_News_Adapter extends RecyclerView.Adapter<Top_News_Adapter.MyViewHolder> {
    private ArrayList<TrendingNewsModel> android;
    Context context;
    AdRequest adRequesNew;


    public Top_News_Adapter(Context context, ArrayList<TrendingNewsModel> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public Top_News_Adapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .top_news_adapter_new, viewGroup, false);

        MyViewHolder mViewHold = new MyViewHolder(view);
        return mViewHold;
    }

    @Override
    public void onBindViewHolder(Top_News_Adapter.MyViewHolder viewHolder, int i) {

        /*Log.d("TopNewsAdapter","TopNewsAdapter Calling");*/

        final TrendingNewsModel dataItem = android.get(i);
        final MyViewHolder myViewHolder = viewHolder;
        Glide.with(MyApplication.ctx).load(dataItem.getImageUrl())
                .crossFade().placeholder(R.drawable.placeholder_apps)
                .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder_apps)
                .into(myViewHolder.img_top_new);

     /*   Typeface custom_font = null;
        try {
            custom_font = Typeface.createFromAsset(context.getResources().getAssets(),
                    "fonts/MontserratAlternates-Regular.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        if (TextUtils.isEmpty(dataItem.getTitle())) {
            myViewHolder.txt_top_new_title.setVisibility(View.GONE);
            myViewHolder.txt_top_new_des.setTextSize(13);
            myViewHolder.txt_top_new_des.setTextColor(context.getResources().getColor(R.color
                    .txt_perm_des));


        } else {
            myViewHolder.txt_top_new_title.setVisibility(View.VISIBLE);
            myViewHolder.txt_top_new_title.setText(dataItem.getTitle());
            myViewHolder.txt_top_new_des.setTextSize(13);
            myViewHolder.txt_top_new_title.setTextColor(context.getResources().getColor(R.color
                    .black));
            myViewHolder.txt_top_new_des.setTextColor(context.getResources().getColor(R.color
                    .txt_perm_des));
        /*    try {

                myViewHolder.txt_top_new_title.setTypeface(custom_font);
            } catch (Exception e) {
                e.printStackTrace();
            }
*/

        }

     /*   try {

            myViewHolder.txt_top_new_des.setTypeface(custom_font);
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd, MMM");

        Date inputDate = null;
        try {
            inputDate = inputDateFormat.parse(dataItem.getPostedDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String outputDateString = outputDateFormat.format(inputDate);
        myViewHolder.txt_top_new_date.setText(outputDateString);
        myViewHolder.txt_news_prov.setText(dataItem.getFeedProvider());
        myViewHolder.txt_top_new_des.setText(dataItem.getDescription());


        if (myViewHolder.trending_news_ads != null) {
            adRequesNew = new AdRequest.Builder().build();
            myViewHolder.trending_news_ads.loadAd(adRequesNew);
        }
        Log.e("chjeckcef",">>" + android.get(i).getIsAd());
        if (android.get(i).getIsAd().equals("1")) {
            myViewHolder.trending_news_ads.setVisibility(View.VISIBLE);
            myViewHolder.rl_trending.setVisibility(View.GONE);
        }else{
            myViewHolder.trending_news_ads.setVisibility(View.GONE);
            myViewHolder.rl_trending.setVisibility(View.VISIBLE);
        }

        Log.e("GetMdainData","getIsAd>00>" + dataItem.getIsAd());
        myViewHolder.layout_topNewsItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MyUtility.isConnectedToInternet()) {
                    MyApplication.getInstance().trackEvent("Top News", dataItem.getRedirectLink(), dataItem.getTitle());


                    if (TextUtils.isEmpty(dataItem.getTitle())) {
                        MyUtility.handleItemClick(context, "", dataItem
                                        .getRedirectLink(), dataItem.getImageUrl(),
                                "Top News", "", dataItem.getDescription());
                    } else {
                        MyUtility.handleItemClick(context, "", dataItem
                                        .getRedirectLink(), dataItem.getImageUrl(),
                                "Top News", "", dataItem.getTitle());
                    }
//                    if (TextUtils.isEmpty(dataItem.getTitle())) {
//                        MyUtility.handleItemClick(context, dataItem.getPackage_name(), dataItem
//                                        .getRedirect_link(), dataItem.getImage(),
//                                "Top News", dataItem.getOpen_with(), dataItem.getDescription());
//                    } else {
//                        MyUtility.handleItemClick(context, dataItem.getPackage_name(), dataItem
//                                        .getRedirect_link(), dataItem.getImage(),
//                                "Top News", dataItem.getOpen_with(), dataItem.getTitle());
//                    }


                } else {
                    MyUtility.NoInternet_Msg(context);
                }


            }
        });


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

        private ImageView img_top_new;
        AdView trending_news_ads;

        private TextView txt_top_new_title, txt_top_new_date, txt_top_new_des, txt_news_prov;
        RelativeLayout layout_topNewsItems,rl_trending;

        public MyViewHolder(View view) {
            super(view);
            img_top_new = view.findViewById(R.id.img_top_news);
            txt_top_new_title = view.findViewById(R.id.txt_top_new_title);
            trending_news_ads = view.findViewById(R.id.trending_news_ads);
            rl_trending = view.findViewById(R.id.rl_trending);


            txt_top_new_date = view.findViewById(R.id.txt_top_new_date);
            txt_top_new_des = view.findViewById(R.id.txt_top_new_des);
            txt_news_prov = view.findViewById(R.id.txt_news_prov);
            layout_topNewsItems = view.findViewById(R.id.layout_topNewsItems);


        }


    }


}
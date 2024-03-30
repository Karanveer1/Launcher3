package com.skd.nubit.adapters;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.skd.nubit.R;
import com.skd.nubit.models.Middle_News_Pojo;
import com.skd.nubit.models.TrendingNewsModel;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by robi on 23/9/18.
 */

public class Middle_News_Adapter extends RecyclerView.Adapter<Middle_News_Adapter.MyViewHolder>
       {
    private ArrayList<TrendingNewsModel> android;
    Context context;
    int imageheight;
    AdRequest adRequesNew;


    public Middle_News_Adapter(Context context, ArrayList<TrendingNewsModel> android) {
        this.android = android;
        this.context = context;
        imageheight = (int) (MyUtility.getScreenWidth(context) / 2.0);
    }

    @Override
    public Middle_News_Adapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .middle_news_adapter_items, viewGroup, false);

        Middle_News_Adapter.MyViewHolder mViewHold = new Middle_News_Adapter.MyViewHolder(view);
        return mViewHold;
    }

    @Override
    public void onBindViewHolder(Middle_News_Adapter.MyViewHolder viewHolder, int i) {

        final TrendingNewsModel dataItem = android.get(i);
        final Middle_News_Adapter.MyViewHolder myViewHolder = viewHolder;

        if (!android.get(i).getIsAd().equals("1")) {
            Glide.with(MyApplication.ctx).load(dataItem.getImageUrl()).thumbnail(0.5f)
                    .crossFade().placeholder(R.drawable.placeholder_you_tube)
                    .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder_you_tube)
                    .into(myViewHolder.img_middle_news);

            myViewHolder.txt_middle_news_title.setText(dataItem.getTitle());
            myViewHolder.txt_middle_news_des.setText(dataItem.getDescription());
            myViewHolder.txt_news_by.setText(dataItem.getFeedProvider());

            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd, MMM");
            Log.e("Checkadass",">>" + android.get(i).getIsAd());
            Date inputDate = null;
            try {
                inputDate = inputDateFormat.parse(dataItem.getPostedDate());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            String outputDateString = outputDateFormat.format(inputDate);

            myViewHolder.txt_middle_news_date.setText(outputDateString);

        }

        if (myViewHolder.trending_news_ads != null) {
            adRequesNew = new AdRequest.Builder().build();
            myViewHolder.trending_news_ads.loadAd(adRequesNew);
        }
        Log.e("chjeckcef",">>" + android.get(i).getIsAd());
        if (android.get(i).getIsAd().equals("1")) {
            myViewHolder.trending_news_ads.setVisibility(View.VISIBLE);
            myViewHolder.middleNews_layout.setVisibility(View.GONE);
        }else{
            myViewHolder.trending_news_ads.setVisibility(View.GONE);
            myViewHolder.middleNews_layout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return android.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_middle_news;
        RelativeLayout middleNews_layout;
        RelativeLayout cardView;
        AdView trending_news_ads;

        FrameLayout admob_blank_layout;

        private TextView txt_middle_news_title, txt_middle_news_des, txt_middle_news_date,
                txt_news_by;

        public MyViewHolder(View view) {
            super(view);
            img_middle_news = view.findViewById(R.id.img_middle_news);
            txt_middle_news_title = view.findViewById(R.id.txt_middle_news_title);
            txt_middle_news_des = view.findViewById(R.id.txt_middle_news_des);
            txt_middle_news_date = view.findViewById(R.id.txt_middle_news_date);
            txt_news_by = view.findViewById(R.id.txt_news_by);
            cardView = view.findViewById(R.id.layout_cardveiw);
            trending_news_ads = view.findViewById(R.id.trending_news_ads);

            middleNews_layout = view.findViewById(R.id.middleNews_layout);
//            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)
// middleNews_layout.getLayoutParams();
//            layoutParams.width = MyUtility.NEWS_CONTAINER_WIDTH;
//            middleNews_layout.setLayoutParams(layoutParams);

            cardView.getLayoutParams().width = MyUtility.NEWS_CONTAINER_WIDTH;
            //img_middle_news.getLayoutParams().height=MyUtility.NEWS_IMAGE_HEIGHT;

            img_middle_news.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup
                    .LayoutParams.MATCH_PARENT, imageheight));
            img_middle_news.setScaleType(ImageView.ScaleType.FIT_XY);
            img_middle_news.setScaleType(ImageView.ScaleType.FIT_XY);


        }


    }
}
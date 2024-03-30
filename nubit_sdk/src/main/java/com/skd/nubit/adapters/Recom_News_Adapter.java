package com.skd.nubit.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.skd.nubit.R;
import com.skd.nubit.models.Top_News_Pojo;
import com.skd.nubit.models.VistoryModel;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Recom_News_Adapter extends RecyclerView.Adapter<Recom_News_Adapter.MyViewHolder>
{
    private ArrayList<VistoryModel> android;
    Context context;


    public Recom_News_Adapter(Context context, ArrayList<VistoryModel> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .top_news_adapter_item, viewGroup, false);

        MyViewHolder mViewHold = new MyViewHolder(view);
        return mViewHold;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {

        /*Log.d("TopNewsAdapter","TopNewsAdapter Calling");*/

        final VistoryModel dataItem = android.get(i);
        final MyViewHolder myViewHolder = viewHolder;
//        Glide.with(MyApplication.ctx).load(dataItem.getImage())
//                .crossFade().placeholder(R.drawable.placeholder_apps)
//                .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder_apps)
//                .into(myViewHolder.img_top_new);
        Glide.with(MyApplication.ctx).load(dataItem.getMainImage()).into(myViewHolder.img_top_new);

        myViewHolder.txt_top_new_title.setText(dataItem.getTitle());
        myViewHolder.txt_top_new_category.setText(dataItem.getCategory());
//        myViewHolder.txt_top_new_date.setText(dataItem.getTitle() + ",  " + dataItem
//                .getPosted_time());

        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd, MMM");

        Date inputDate = null;
//        try {
//            inputDate = inputDateFormat.parse(dataItem.getLiveDate());
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        String outputDateString = outputDateFormat.format(inputDate);

//        myViewHolder.txt_top_new_date.setText(outputDateString);
        myViewHolder.txt_top_new_des.setText(dataItem.getDescription());


        myViewHolder.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cat = dataItem.getCategory();
                String link = "https://www.vistory.mobi/" + cat + "/detail/" + dataItem
                        .getUrlHash();
                MyUtility.My_share(context, link + " \n\n" + dataItem
                        .getTitle(), "Trending");
            }
        });


        myViewHolder.layout_topNewsItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cat = dataItem.getCategory();
                String link = "https://www.vistory.mobi/" + cat + "/detail/" + dataItem
                        .getUrlHash();
                if (MyUtility.isConnectedToInternet()) {
                    Log.e("checjkurl",">>" + link);
//                    MyApplication.getInstance().trackEvent("Recommended News", dataItem.getUrlHash()
//                            (), dataItem.getTitle());
                    MyUtility.handleItemClick(context, dataItem.getTitle(), link, dataItem.getMainImage(),
                            "Trending", "", dataItem.getTitle());

                } else {
                    MyUtility.NoInternet_Msg(context);
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return android.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView img_top_new,ivShare;

        private TextView txt_top_new_title, txt_top_new_date, txt_top_new_des, txt_news_prov, txt_top_new_category;
        RelativeLayout layout_topNewsItems;
        WebView webview;

        public MyViewHolder(View view) {
            super(view);
            img_top_new = view.findViewById(R.id.img_top_news);
            txt_top_new_title = view.findViewById(R.id.txt_top_new_title);
            txt_top_new_category = view.findViewById(R.id.txt_top_new_category);
            webview = view.findViewById(R.id.webview);
            ivShare = view.findViewById(R.id.ivShare);


            txt_top_new_date = view.findViewById(R.id.txt_top_new_date);
            txt_top_new_des = view.findViewById(R.id.txt_top_new_des);
            txt_news_prov = view.findViewById(R.id.txt_news_prov);
            layout_topNewsItems = view.findViewById(R.id.layout_topNewsItems);


        }


    }


}

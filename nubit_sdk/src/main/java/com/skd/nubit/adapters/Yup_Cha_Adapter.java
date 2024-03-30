package com.skd.nubit.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.skd.nubit.R;
import com.skd.nubit.models.YupTv_Chanel_Pojo;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

import java.util.ArrayList;

public class Yup_Cha_Adapter extends RecyclerView.Adapter<Yup_Cha_Adapter.MyViewHolder> {
    private ArrayList<YupTv_Chanel_Pojo> android;
    Context context;


    public Yup_Cha_Adapter(Context context, ArrayList<YupTv_Chanel_Pojo> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public Yup_Cha_Adapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .yup_chan_adapter_item, viewGroup, false);
        Yup_Cha_Adapter.MyViewHolder mViewHold = new Yup_Cha_Adapter.MyViewHolder(view);
        return mViewHold;
    }

    @Override
    public void onBindViewHolder(Yup_Cha_Adapter.MyViewHolder viewHolder, final int i) {

        final YupTv_Chanel_Pojo dataItem = android.get(i);
        Yup_Cha_Adapter.MyViewHolder myViewHolder = viewHolder;


        Glide.with(MyApplication.ctx).load(dataItem.getChannelIcon()).thumbnail(0.5f)
                .crossFade().placeholder(R.drawable.placeholder_news)
                .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder_news)
                .into(myViewHolder.img_chan_icon);

        try {
            myViewHolder.txt_chan_name.setText(dataItem.getChannelName());
        } catch (Exception e) {
            e.printStackTrace();
        }


        myViewHolder.adapter_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyApplication.getInstance().trackEvent("Live Tv",dataItem.getChannelName(), dataItem
                        .getStreaming_url());

                MyUtility.handleItemClick(context, dataItem.getPackage_name(),
                        dataItem.getStreaming_url(), dataItem.getStreaming_url(), "Live Tv",
                        dataItem.getOpen_with(), dataItem.getChannelName());

            }
        });

    }

    @Override
    public int getItemCount() {
        return android.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_chan_name;
        ImageView img_chan_icon;
        RelativeLayout adapter_parent;


        public MyViewHolder(View view) {
            super(view);
            txt_chan_name = view.findViewById(R.id.txt_chan_name);
            img_chan_icon = view.findViewById(R.id.img_chan_icon);
            adapter_parent=view.findViewById(R.id.adapter_parent);

        }
    }


}

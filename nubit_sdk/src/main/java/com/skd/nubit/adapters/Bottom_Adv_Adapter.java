package com.skd.nubit.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.skd.nubit.R;
import com.skd.nubit.models.Bottom_adv_Pojo;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

import java.util.ArrayList;

public class Bottom_Adv_Adapter extends RecyclerView.Adapter<Bottom_Adv_Adapter.MyViewHolder>  {
    private ArrayList<Bottom_adv_Pojo> android;
    Context context;


    public Bottom_Adv_Adapter(Context context, ArrayList<Bottom_adv_Pojo> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public Bottom_Adv_Adapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bottom_adv_adapter_items, viewGroup, false);

        Bottom_Adv_Adapter.MyViewHolder mViewHold = new Bottom_Adv_Adapter.MyViewHolder(view);
        return  mViewHold;
    }

    @Override
    public void onBindViewHolder(Bottom_Adv_Adapter.MyViewHolder viewHolder, int i) {

        final Bottom_adv_Pojo dataItem = android.get(i);
        final Bottom_Adv_Adapter.MyViewHolder myViewHolder = viewHolder;
        Glide.with(MyApplication.ctx).load(dataItem.getBanner_image()).crossFade().placeholder(R.drawable.placeholder_bottom_adv)
                .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder_bottom_adv)
                .into(myViewHolder.bottom_adv_banner);





    }

    @Override
    public int getItemCount() {
        try {
            return android.size();
        } catch (Exception e) {
            e.printStackTrace();
            return  0;
        }
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView bottom_adv_banner;
        private FrameLayout admob_blank_layout;
        private RelativeLayout bottom_adv_items_layout;



        public MyViewHolder(View view) {
            super(view);
            bottom_adv_banner = view.findViewById(R.id.bottom_adv_banner);

            bottom_adv_items_layout= view.findViewById(R.id.bottom_adv_items_layout);

            bottom_adv_items_layout.getLayoutParams().height= MyUtility.dpToPx(140);
            bottom_adv_items_layout.getLayoutParams().width=MyUtility.dpToPx(250);
            bottom_adv_banner.requestLayout();




        }




    }}
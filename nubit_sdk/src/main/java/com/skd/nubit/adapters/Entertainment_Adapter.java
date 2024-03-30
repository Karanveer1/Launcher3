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
import com.skd.nubit.models.Entertainment_pojo;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

import java.util.ArrayList;

public class Entertainment_Adapter extends RecyclerView.Adapter<Entertainment_Adapter
        .MyViewHolder> {
    private ArrayList<Entertainment_pojo> android;
    Context context;


    public Entertainment_Adapter(Context context, ArrayList<Entertainment_pojo> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public Entertainment_Adapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .entertainment_adapter_items, viewGroup, false);

        MyViewHolder mViewHold = new MyViewHolder(view);
        return mViewHold;
    }

    @Override
    public void onBindViewHolder(Entertainment_Adapter.MyViewHolder viewHolder, int i) {

        final Entertainment_pojo dataItem = android.get(i);
        final MyViewHolder myViewHolder = viewHolder;
        Glide.with(MyApplication.ctx).load(dataItem.getBanner_thumb_image()).thumbnail(0.5f)
                .crossFade().placeholder(R.drawable.placeholder_bottom_adv)
                .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder_bottom_adv)
                .into(myViewHolder.entertainment_banner);


    }

    @Override
    public int getItemCount() {
        return android.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView entertainment_banner;
        private FrameLayout admob_blank_layout;
        private RelativeLayout entertainment_items_layout;


        public MyViewHolder(View view) {
            super(view);
            entertainment_banner = view.findViewById(R.id.entertainment_banner);

            entertainment_items_layout = view.findViewById(R.id.entertainment_items_layout);

            entertainment_items_layout.getLayoutParams().height = MyUtility.dpToPx(140);
            entertainment_items_layout.getLayoutParams().width = MyUtility.dpToPx(250);
            entertainment_banner.requestLayout();


        }


    }
}

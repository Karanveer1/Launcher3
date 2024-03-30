package com.skd.nubit.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.skd.nubit.R;
import com.skd.nubit.activities.YupTv_Activity;
import com.skd.nubit.models.OtherServicesPojo;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

import java.util.ArrayList;

public class Other_Services_Adapter extends RecyclerView.Adapter<Other_Services_Adapter
        .MyViewHolder> {
    private ArrayList<OtherServicesPojo> android;
    Context context;


    public Other_Services_Adapter(Context context, ArrayList<OtherServicesPojo> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public Other_Services_Adapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .other_services_items, viewGroup, false);
        Other_Services_Adapter.MyViewHolder mViewHold = new Other_Services_Adapter.MyViewHolder
                (view);
        return mViewHold;
    }

    @Override
    public void onBindViewHolder(Other_Services_Adapter.MyViewHolder viewHolder, final int i) {

        final OtherServicesPojo dataItem = android.get(i);
        Other_Services_Adapter.MyViewHolder myViewHolder = viewHolder;


        myViewHolder.txt_other_ser.setText(dataItem.getTitle());


        Glide.with(MyApplication.ctx).load(dataItem.getIcon()).thumbnail(0.5f)
                .crossFade().placeholder(R.drawable.placeholder_apps).error(R.drawable
                .placeholder_apps)
                .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder_apps)
                .into(myViewHolder.img_other_ser);

        myViewHolder.layout_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyApplication.getInstance().trackEvent("Other Services", dataItem
                        .getRedirect(), dataItem.getTitle());
                if (TextUtils.isEmpty(dataItem.getRedirect())) {

                    MyUtility.saveTracksInDB(context, dataItem.getIcon(), dataItem.getTitle(),
                            "Other Services", "0", dataItem.getTitle(),"");
                    Intent send_to_next = new Intent(
                            context,
                            YupTv_Activity.class);
                    send_to_next.putExtra("title", dataItem.getTitle());
                    context.startActivity(send_to_next);
                } else {
                    MyUtility.handleItemClick(context, dataItem.getPackage_name(), dataItem
                                    .getRedirect(), dataItem.getRedirect(), "Other Services",
                            dataItem.getOpen_with(), dataItem.getTitle());
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
            return 0;
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_other_ser;
        ImageView img_other_ser;
        RelativeLayout layout_parent;


        public MyViewHolder(View view) {
            super(view);
            txt_other_ser = view.findViewById(R.id.txt_other_ser);
            img_other_ser = view.findViewById(R.id.img_other_ser);
            layout_parent = view.findViewById(R.id.layout_o_s);
         /*   layout_parent.getLayoutParams().width = (int) (MyUtility.getScreenWidth(img_other_ser
                    .getContext()) / 4.6);*/

        }
    }


}
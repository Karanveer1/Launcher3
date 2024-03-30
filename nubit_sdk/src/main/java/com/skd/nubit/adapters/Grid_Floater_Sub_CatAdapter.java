package com.skd.nubit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.skd.nubit.R;
import com.skd.nubit.models.Floater_sub_Pojo;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

import java.util.ArrayList;


/**
 * Created by robi kumar tomar on 05/10/18.
 */



public class Grid_Floater_Sub_CatAdapter extends BaseAdapter {
    private Context mContext;


    private ArrayList<Floater_sub_Pojo> arrayList_Service;

    public Grid_Floater_Sub_CatAdapter(Context c, ArrayList<Floater_sub_Pojo> list) {
        mContext = c;
        this.arrayList_Service=list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        try {
            return arrayList_Service.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            final Floater_sub_Pojo dataItem = arrayList_Service.get(position);
            //view = new View(mContext);
            view = inflater.inflate(R.layout.service_grid_items, null);
            TextView service_grid_text = (TextView) view.findViewById(R.id.service_grid_text);
            ImageView imageView = (ImageView)view.findViewById(R.id.service_grid_image);
            service_grid_text.setText(dataItem.getTitle());

            Glide.with(MyApplication.ctx).load(dataItem.getBanner_image()).thumbnail(0.5f)
                    .crossFade().placeholder(R.drawable.placeholder_apps)
                    .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder_apps)
                    .into(imageView);





            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MyApplication.getInstance().trackEvent("Floater", dataItem.getRedirect_link(), dataItem.getTitle());
                    MyUtility.handleItemClick(mContext, dataItem.getPackage_name(), dataItem.getRedirect_link(),dataItem.getOutput_link(),"Floater",dataItem.getOpen_with(),dataItem.getTitle());
                }
            });

        } else {
            view = convertView;
        }

        return view;
    }
}
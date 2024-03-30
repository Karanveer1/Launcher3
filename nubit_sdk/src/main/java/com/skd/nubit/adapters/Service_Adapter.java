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
import com.skd.nubit.models.Services_pojo;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

import java.util.ArrayList;
import java.util.List;

public class Service_Adapter extends BaseAdapter {
    private Context mContext;


    private List<Services_pojo> arrayList_Service;

    public Service_Adapter(Context c, List list) {
        mContext = c;
        this.arrayList_Service = list;
    }

    public void updateList(List<Services_pojo> arrayList) {
        arrayList_Service = new ArrayList<>();
        arrayList_Service.addAll(arrayList);
        notifyDataSetChanged();

    }


    @Override
    public int getCount() {
        return arrayList_Service.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    MyViewHolder myViewHolder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        final Services_pojo dataItem = arrayList_Service.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //view = new View(mContext);
            convertView = inflater.inflate(R.layout.service_grid_items, null);

            myViewHolder = new MyViewHolder(convertView);
//            convertView.setTag(myViewHolder);
//            myViewHolder.service_grid_text = (TextView) convertView.findViewById(R.id
// .service_grid_text);
//            myViewHolder.imageView = (ImageView) convertView.findViewById(R.id
// .service_grid_image);

            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }



            myViewHolder.service_grid_text.setText(dataItem.getTitle());


               Glide.with(MyApplication.ctx).load(dataItem.getBanner_image()).thumbnail(0.5f)
                    .crossFade().placeholder(R.drawable.placeholder_apps).error(R.drawable
                    .placeholder_apps)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(myViewHolder.imageView);



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyApplication.getInstance().trackEvent("Services", dataItem.getOutput_link(),
                        dataItem.getTitle());


                MyUtility.handleItemClick(mContext, dataItem.getPackage_name(), dataItem
                        .getRedirect_link(), dataItem.getBanner_image(), "Services", dataItem
                        .getOpen_with(), dataItem.getTitle());


            }
        });


        return convertView;

    }

    public class MyViewHolder {

        TextView service_grid_text;
        ImageView imageView;


        public MyViewHolder(View view) {
            service_grid_text = view.findViewById(R.id.service_grid_text);
            imageView = view.findViewById(R.id.service_grid_image);
        }
    }

}



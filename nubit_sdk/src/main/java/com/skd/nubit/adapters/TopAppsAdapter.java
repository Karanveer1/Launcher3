package com.skd.nubit.adapters;

import android.content.Context;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.skd.nubit.R;
import com.skd.nubit.models.AppsModel;
import com.skd.nubit.models.TopApps_Pojo;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

import java.util.ArrayList;
import java.util.List;


public class TopAppsAdapter extends RecyclerView.Adapter<TopAppsAdapter.MyViewHolder> {
    private List<? extends AppsModel> dataList;
    Context context;

    public TopAppsAdapter(Context context, List<? extends AppsModel> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_apps_adapter_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AppsModel dataItem = dataList.get(position);
        holder.bindData(dataItem);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView topapps_icon;
        private Button btn_install_topapps;
        TextView txt_topapps_name;
        CardView topapps_cardview;

        public MyViewHolder(View view) {
            super(view);
            topapps_icon = view.findViewById(R.id.topapps_icon);
            btn_install_topapps = view.findViewById(R.id.btn_install_topapps);
            txt_topapps_name = view.findViewById(R.id.txt_topapps_name);
            topapps_cardview = view.findViewById(R.id.topapps_cardview);
            topapps_cardview.getLayoutParams().width = MyUtility.getScreenWidth(topapps_icon.getContext()) / 3;
        }

        public void bindData(AppsModel item) {
            // Bind data based on the actual type of the object
            if (item instanceof AppsModel) {
                AppsModel appsModel = (AppsModel) item;
                Glide.with(context).load(appsModel.getBannerImage()).thumbnail(0.5f).crossFade()
                        .placeholder(R.drawable.placeholder_apps).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.placeholder_apps).into(topapps_icon);
                txt_topapps_name.setText(appsModel.getTitle());
                btn_install_topapps.setText(appsModel.getTitle());

                btn_install_topapps.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyApplication.getInstance().trackEvent("Apps", appsModel.getRedirectLink(), appsModel.getTitle());
                        MyUtility.handleItemClick(context, "", appsModel.getRedirectLink(), appsModel.getBannerImage(),
                                "Apps", "", appsModel.getTitle());
                    }
                });
            }
        }
    }
}
//
//public class TopAppsAdapter extends RecyclerView.Adapter<TopAppsAdapter.MyViewHolder> {
//    private ArrayList<AppsModel> android;
//    Context context;
//
//
//    public TopAppsAdapter(Context context, ArrayList<AppsModel> android) {
//        this.android = android;
//        this.context = context;
//    }
//
//    @Override
//    public TopAppsAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
//                .top_apps_adapter_items, viewGroup, false);
//
//        TopAppsAdapter.MyViewHolder mViewHold = new TopAppsAdapter.MyViewHolder(view);
//        return mViewHold;
//    }
//
//    @Override
//    public void onBindViewHolder(TopAppsAdapter.MyViewHolder viewHolder, int i) {
//
//        final AppsModel dataItem = android.get(i);
//        final TopAppsAdapter.MyViewHolder myViewHolder = viewHolder;
//
//
//       /* if (MyUtility.isAppInstalled(dataItem.getPackage_name(), context)) {
//            Log.d("Top Apps",dataItem.getTitle()+" Is installed in your device");
//
//        } else {
//            Glide.with(MyApplication.ctx).load(dataItem.getBanner_thumb_image()).thumbnail(0.5f)
//                    .crossFade().placeholder(R.drawable.placeholder_apps)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder_apps)
//                    .into(myViewHolder.topapps_icon);
//
//
//            String get_colorCode = "";
//            try {
//                get_colorCode = dataItem.getColor();
//                if (!TextUtils.isEmpty(get_colorCode) && get_colorCode != null) {
//                    myViewHolder.btn_topapps_type.setBackgroundColor(Color.parseColor
//                            (get_colorCode));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//            myViewHolder.txt_topapps_name.setText(dataItem.getTitle());
//            myViewHolder.btn_topapps_type.setText(dataItem.getTag_name());
//            myViewHolder.btn_install_topapps.setText(dataItem.getButton_label());
//        }
//*/
//        Glide.with(MyApplication.ctx).load(dataItem.getBannerImage()).thumbnail(0.5f)
//                .crossFade().placeholder(R.drawable.placeholder_apps)
//                .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder_apps)
//                .into(myViewHolder.topapps_icon);
//
//
//      /*  String get_colorCode = "";
//        try {
//            get_colorCode = dataItem.getColor();
//            if (!TextUtils.isEmpty(get_colorCode) && get_colorCode != null) {
//                myViewHolder.btn_topapps_type.setBackgroundColor(Color.parseColor
//                        (get_colorCode));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }*/
//
//
//        myViewHolder.txt_topapps_name.setText(dataItem.getTitle());
//      /*  myViewHolder.btn_topapps_type.setText(dataItem.getTag_name());*/
//        myViewHolder.btn_install_topapps.setText(dataItem.getTitle());
//
//
//        myViewHolder.btn_install_topapps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                MyApplication.getInstance().trackEvent("Apps", dataItem.getRedirectLink(),
//                        dataItem.getTitle());
//                Log.e("checkjlog",">>" + dataItem.getRedirectLink());
//                MyUtility.handleItemClick(context, "", dataItem
//                        .getRedirectLink(), dataItem.getBannerImage(), "Apps", "", dataItem.getTitle());
////                MyUtility.handleItemClick(context, dataItem.getPackage_name(), dataItem
////                        .getRedirect_link(), dataItem.getBanner_thumb_image(), "Apps", dataItem
////                        .getOpen_with(), dataItem.getTitle());
//
//            }
//        });
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return android.size();
//    }
//
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//
//        private ImageView topapps_icon;
//        private Button  btn_install_topapps;
//        TextView txt_topapps_name;
//
//
//        CardView topapps_cardview;
//
//
//        public MyViewHolder(View view) {
//            super(view);
//            topapps_icon = view.findViewById(R.id.topapps_icon);
//      /*      btn_topapps_type = view.findViewById(R.id.btn_topapps_type);*/
//            btn_install_topapps = view.findViewById(R.id.btn_install_topapps);
//            txt_topapps_name = view.findViewById(R.id.txt_topapps_name);
//            topapps_cardview = view.findViewById(R.id.topapps_cardview);
//            topapps_cardview.getLayoutParams().width = MyUtility.getScreenWidth(topapps_icon
//                    .getContext()) / 3;
//
//
//        }
//
//
//    }
//}
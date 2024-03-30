package com.skd.nubit.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.skd.nubit.R;
import com.skd.nubit.models.Floater_Category_Pojo;
import com.skd.nubit.models.Floater_sub_Pojo;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

import org.json.JSONArray;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by robi kumar tomar on 05/10/18.
 */

public class Floater_Category_Adapter extends RecyclerView.Adapter<Floater_Category_Adapter
        .MyViewHolder> {
    private ArrayList<Floater_Category_Pojo> android;
    Context context;
    OnFloaterClick onFloaterClick;

    public interface OnFloaterClick {
        void onItemClick(int position, String title, String open_with, String package_name,
                         String redirect_link, ArrayList<Floater_sub_Pojo> list);
    }


    public Floater_Category_Adapter(Context context, ArrayList<Floater_Category_Pojo> android,
                                    OnFloaterClick onFloaterClick) {
        this.android = android;
        this.context = context;
        this.onFloaterClick = onFloaterClick;
    }

    @Override
    public Floater_Category_Adapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.floater_c_items,
                viewGroup, false);

        Floater_Category_Adapter.MyViewHolder mViewHold = new Floater_Category_Adapter
                .MyViewHolder(view);
        return mViewHold;
    }

    @Override
    public void onBindViewHolder(Floater_Category_Adapter.MyViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {

        final Floater_Category_Pojo dataItem = android.get(i);
        final Floater_Category_Adapter.MyViewHolder myViewHolder = viewHolder;
        Glide.with(MyApplication.ctx).load(dataItem.getBanner_image()).thumbnail(0.5f)
                .crossFade().placeholder(R.drawable.placeholder_apps)
                .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder_apps)
                .into(myViewHolder.img_floater_category);

        myViewHolder.txt_floater_category_title.setText(dataItem.getTitle());


        myViewHolder.layout_floaterContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*MyApplication.getInstance().trackEvent("Floater", dataItem.getBanner_image(),
                        dataItem.getTitle());*/
               /* MyUtility.saveTracksInDB(context, dataItem.getRedirect_link(), dataItem.getRedirect_link(), "Floater", "0", dataItem.getTitle());*/


//                RobiLauncherActivity.closed_floater_from_out();


                if (MyUtility.isConnectedToInternet()) {
                    JSONObject jsonObject_fetchAllData, jsonObject_fetchAllSubCate;
                    JSONArray jsonarray_fetch_floater_cate, jsonarray_fetch_floater_subCate;

                    ArrayList<Floater_sub_Pojo> arrayList_floater_subCate = new ArrayList<>();
                     /*   MyUtility.get_redirectedURL=dataItem.getRedirect_link();
                        MyUtility.RedirectMe(MyUtility.get_redirectedURL,context);*/


                    try {

                        String get_SavedJson = MyApplication.app_sharedPreferences.getString
                                ("save_app_Json", null);
                        jsonObject_fetchAllData = new JSONObject(String.valueOf(get_SavedJson));
                        ;
                        jsonObject_fetchAllData = jsonObject_fetchAllData.getJSONObject("data");
                        jsonarray_fetch_floater_cate = jsonObject_fetchAllData.getJSONArray
                                ("floater");


                        jsonObject_fetchAllData = jsonarray_fetch_floater_cate.getJSONObject(i);


                        jsonarray_fetch_floater_subCate = jsonObject_fetchAllData.getJSONArray
                                ("data");
                        for (int j = 0; j < jsonarray_fetch_floater_subCate.length(); j++) {
                            Floater_sub_Pojo floater_sub_pojo = new Floater_sub_Pojo();
                            jsonObject_fetchAllSubCate = jsonarray_fetch_floater_subCate
                                    .getJSONObject(j);

                            String title = null;
                            try {
                                title = jsonObject_fetchAllSubCate.getString("title");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            String banner_image = null;
                            try {
                                banner_image = jsonObject_fetchAllSubCate.getString("banner_image");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            String package_name = null;
                            try {
                                package_name = jsonObject_fetchAllSubCate.getString("package_name");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            String output_link = null;
                            try {
                                output_link = jsonObject_fetchAllSubCate.getString("output_link");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            String redirect_link = null;
                            try {
                                redirect_link = jsonObject_fetchAllSubCate.getString
                                        ("redirect_link");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            String open_with = null;
                            try {
                                open_with = jsonObject_fetchAllSubCate.getString("open_with");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            floater_sub_pojo.setTitle(title);
                            floater_sub_pojo.setBanner_image(banner_image);
                            floater_sub_pojo.setPackage_name(package_name);
                            floater_sub_pojo.setOutput_link(output_link);
                            floater_sub_pojo.setRedirect_link(redirect_link);
                            floater_sub_pojo.setOpen_with(open_with);
                            arrayList_floater_subCate.add(floater_sub_pojo);


                        }


                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                    onFloaterClick.onItemClick(i, dataItem.getTitle(), dataItem.getOpen_with(),
                            dataItem.getPackage_name(), dataItem.getRedirect_link(),
                            arrayList_floater_subCate);

//                    MyUtility.show_floater_popup(context, dataItem.getTitle(),
// arrayList_floater_subCate);


                } else {
                    onFloaterClick.onItemClick(i, dataItem.getTitle(), dataItem.getOpen_with(),
                            dataItem.getPackage_name(), dataItem.getRedirect_link(), null);
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

        private ImageView img_floater_category;
        TextView txt_floater_category_title;
        LinearLayout layout_floaterContainer;

        public MyViewHolder(View view) {
            super(view);
            img_floater_category = view.findViewById(R.id.img_floater_category);
            txt_floater_category_title = view.findViewById(R.id
                    .txt_floater_category_title);
            layout_floaterContainer = view.findViewById(R.id
                    .layout_floaterContainer);

        }


    }


}

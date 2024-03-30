package com.skd.nubit.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skd.nubit.R;
import com.skd.nubit.models.VideoCat;
import com.skd.nubit.utilityclasses.MyApplication;

import java.util.ArrayList;

public class Grid_VideoCat extends BaseAdapter {
    private Context mContext;


    private ArrayList<VideoCat> arrayList_vid;

    public Grid_VideoCat(Context c, ArrayList<VideoCat> list) {
        mContext = c;
        this.arrayList_vid = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        try {
            return arrayList_vid.size();
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
        final View view;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            final VideoCat dataItem = arrayList_vid.get(position);
            //view = new View(mContext);
            view = inflater.inflate(R.layout.vid_cat_grid_items, null);
            TextView service_grid_text = view.findViewById(R.id.vid_gridpop_text);
            service_grid_text.setText(dataItem.getCat_val());

            if (!TextUtils.isEmpty(MyApplication.app_sharedPreferences.getString("cat_title",
                    ""))) {
                if ((MyApplication.app_sharedPreferences.getString("cat_title",
                        "")).contains(dataItem.getCat_title())) {
                    view.setBackgroundResource(R.drawable.news_style_selected);
                }


            }


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* Toast.makeText(mContext, dataItem.getTitle(), Toast.LENGTH_SHORT).show();*/
                    String cat_title = MyApplication.app_sharedPreferences.getString
                            ("cat_title",
                                    "");
                    if (TextUtils.isEmpty(cat_title)) {

                        MyApplication.app_editor.putString("cat_title", dataItem.getCat_title())
                                .commit();
                        view.setBackgroundResource(R.drawable.news_style_selected);
                      /*  Log.d("cat_id", "Pub Name was blank");
                        Log.d("cat_id", "Now " + cat_id + " is added");*/
                    } else if (cat_title.contains(dataItem.getCat_title())) {
                        if (!cat_title.contains(",")) {
                            MyApplication.app_editor.remove("cat_title").commit();
                            view.setBackgroundResource(R.drawable.news_pop_style);
                            // Log.d("cat_id", cat_id + "was the last item in
                            // list, now removed");
                        } else if (cat_title.startsWith(dataItem.getCat_title())) {
                            view.setBackgroundResource(R.drawable.news_pop_style);
                            String update_str = cat_title.replace(dataItem.getCat_title(), "");
                            MyApplication.app_editor.putString("cat_title", update_str)
                                    .commit();
                           /* Log.d("cat_id", dataItem.getTitle() + "is removed,Updated
                           list is :" +
                                    " " + update_str);*/
                        } else {
                            view.setBackgroundResource(R.drawable.news_pop_style);
                            String update_str = cat_title.replace("," +
                                    (dataItem.getCat_title()), "");
                            MyApplication.app_editor.putString("cat_title", update_str)
                                    .commit();
                           /* Log.d("cat_id", dataItem.getTitle() + "is removed,Updated
                           list is :" +
                                    " " + update_str);*/
                        }

                    } else if (!cat_title.contains(dataItem.getCat_title())) {
                        view.setBackgroundResource(R.drawable.news_style_selected);
                        MyApplication.app_editor.putString("cat_title", cat_title + ","
                                + dataItem
                                .getCat_title())
                                .commit();
                       /* Log.d("cat_id", dataItem.getTitle() + "is added,Updated list is
                        : " +
                                cat_id + "," + dataItem
                                .getTitle());*/
                    }


                }
            });

        } else {
            view = convertView;
        }

        return view;
    }
}


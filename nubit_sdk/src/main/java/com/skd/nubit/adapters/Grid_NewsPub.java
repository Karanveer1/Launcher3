package com.skd.nubit.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.skd.nubit.R;
import com.skd.nubit.models.NewPubPojo;
import com.skd.nubit.utilityclasses.MyApplication;

import java.util.ArrayList;

public class Grid_NewsPub extends BaseAdapter {
    private Context mContext;


    private ArrayList<NewPubPojo> arrayList_pub;

    public Grid_NewsPub(Context c, ArrayList<NewPubPojo> list) {
        mContext = c;
        this.arrayList_pub = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        try {
            return arrayList_pub.size();
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
            final NewPubPojo dataItem = arrayList_pub.get(position);
            //view = new View(mContext);
            view = inflater.inflate(R.layout.news_grid_pop_items, null);
            /*   TextView service_grid_text = view.findViewById(R.id.news_grid_text);*/
            TextView txt_news_cat = view.findViewById(R.id.txt_news_cat);

            txt_news_cat.setText(dataItem.getTitle());





            if (!TextUtils.isEmpty(MyApplication.app_sharedPreferences.getString("pub_name",
                    ""))) {
                if ((MyApplication.app_sharedPreferences.getString("pub_name",
                        "")).contains(dataItem.getTitle())) {
                    view.setBackgroundResource(R.drawable.news_style_selected);
                }


            }


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* Toast.makeText(mContext, dataItem.getTitle(), Toast.LENGTH_SHORT).show();*/
                    String pub_name = MyApplication.app_sharedPreferences.getString("pub_name",
                            "");
                    if (TextUtils.isEmpty(pub_name)) {

                        MyApplication.app_editor.putString("pub_name", dataItem.getTitle())
                                .commit();
                        view.setBackgroundResource(R.drawable.news_style_selected);
                      /*  Log.d("pub_name", "Pub Name was blank");
                        Log.d("pub_name", "Now " + pub_name + " is added");*/
                    } else if (pub_name.contains(dataItem.getTitle())) {
                        if (!pub_name.contains(",")) {
                            MyApplication.app_editor.remove("pub_name").commit();
                            view.setBackgroundResource(R.drawable.news_pop_style);
                            //Log.d("pub_name", pub_name + "was the last item in list, now
                            // removed");
                        } else if (pub_name.startsWith(dataItem.getTitle())) {
                            view.setBackgroundResource(R.drawable.news_pop_style);
                            String update_str = pub_name.replace(dataItem.getTitle(), "");
                            MyApplication.app_editor.putString("pub_name", update_str)
                                    .commit();
                           /* Log.d("pub_name", dataItem.getTitle() + "is removed,Updated list is
                            :" +
                                    " " + update_str);*/
                        } else {
                            view.setBackgroundResource(R.drawable.news_pop_style);
                            String update_str = pub_name.replace("," +
                                    (dataItem.getTitle()), "");
                            MyApplication.app_editor.putString("pub_name", update_str)
                                    .commit();
                           /* Log.d("pub_name", dataItem.getTitle() + "is removed,Updated list is
                            :" +
                                    " " + update_str);*/
                        }

                    } else if (!pub_name.contains(dataItem.getTitle())) {
                        view.setBackgroundResource(R.drawable.news_style_selected);
                        MyApplication.app_editor.putString("pub_name", pub_name + "," + dataItem
                                .getTitle())
                                .commit();
                       /* Log.d("pub_name", dataItem.getTitle() + "is added,Updated list is : " +
                                pub_name + "," + dataItem
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

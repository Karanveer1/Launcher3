package com.skd.nubit.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.skd.nubit.R;
import com.skd.nubit.adapters.Yup_Cha_Adapter;
import com.skd.nubit.adapters.Yup_Lan_Adapter;
import com.skd.nubit.models.YupTv_Chanel_Pojo;
import com.skd.nubit.models.Yuptv_lang_pojo;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

import java.util.ArrayList;

public class YupTv_Activity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progress;

    ArrayList<Yuptv_lang_pojo> list_language;
    ArrayList<YupTv_Chanel_Pojo> list_chanel;
    ArrayList<YupTv_Chanel_Pojo> filtered_list_chanel;

    RecyclerView recycler_chanel, recycler_language;
    TextView txt_title;

    ImageView back_livetv;
    RelativeLayout layoutAdMob;
     String get_yup_ad_id,get_yup_ad_size;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_yuptv);
        progress = findViewById(R.id.progress);
        recycler_chanel = findViewById(R.id.recycler_chanel);
        recycler_language = findViewById(R.id.recycler_language);
        layoutAdMob = findViewById(R.id.layoutAdMob);
        back_livetv = findViewById(R.id.back_livetv);
        txt_title = findViewById(R.id.txt_title);
        back_livetv.setOnClickListener(this);

        MyApplication.getInstance().trackScreenView("YupTv Screen");


        if (!TextUtils.isEmpty(getIntent().getStringExtra("title"))) {
            txt_title.setText(getIntent().getStringExtra("title"));
        }

        String get_yupTv_Json = MyApplication.app_sharedPreferences.getString("yupTv_Json", "");
        String yuptv_update_version = MyApplication.app_sharedPreferences.getString
                ("yuptv_update_version", "");
        String yuptv_old_version = MyApplication.app_sharedPreferences.getString
                ("yuptv_old_version", "");
        if (!TextUtils.isEmpty(get_yupTv_Json)) {
            if (yuptv_update_version.equalsIgnoreCase(yuptv_old_version)) {
                parsingData();
            } else {
                MyApplication.app_editor.putString("yuptv_old_version", yuptv_update_version)
                        .apply();
                new HitYupTv().execute();
            }

        } else {
            MyApplication.app_editor.putString("yuptv_old_version", yuptv_update_version).apply();
            new HitYupTv().execute();
        }



        //Below line for sowing the ads at bottom
        get_yup_ad_id = MyApplication.app_sharedPreferences.getString("admob_other_rkt", "");
        get_yup_ad_size = MyApplication.app_sharedPreferences.getString("admob_other_type",
                "");

        if(TextUtils.isEmpty(get_yup_ad_id))
        {
            get_yup_ad_id = MyApplication.app_sharedPreferences.getString("yup_ad_id", "");
            get_yup_ad_size = MyApplication.app_sharedPreferences.getString("yup_ad_size",
                    "");
        }


        if (!TextUtils.isEmpty(get_yup_ad_id)) {

            MyUtility.setUpBannerAd(YupTv_Activity.this, get_yup_ad_id, layoutAdMob,
                    get_yup_ad_size, back_livetv, "");
        }


    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.back_livetv) {
            finish();

        }

    }


    class HitYupTv extends AsyncTask<Void, Void, String> {


        String response = "no";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progress.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(Void... params) {


            String downloadNewData = "";


            downloadNewData = MyUtility.hitYupTv(YupTv_Activity.this, MyUtility.yuptv);
            if (!TextUtils.isEmpty(downloadNewData)) {
                MyApplication.app_editor.putString("yupTv_Json", String.valueOf
                        (downloadNewData));
                MyApplication.app_editor.apply();
                response = "yes";
            } else {
                response = "no";
            }
            return response;
        }

        @Override
        public void onPostExecute(String args) {


            if (args.equalsIgnoreCase("yes")) {
                parsingData();
            } else {
                Toast.makeText(getApplicationContext(), "Something went wrong, Pls try later",
                        Toast.LENGTH_LONG).show();
                progress.setVisibility(View.GONE);
                finish();
            }

        }
    }

    private void parsingData() {
        list_chanel = new ArrayList<>();
        list_language = new ArrayList<>();
        filtered_list_chanel = new ArrayList<>();
        try {
            String get_SavedJson = MyApplication.app_sharedPreferences.getString("yupTv_Json",
                    "");
            if (!TextUtils.isEmpty(get_SavedJson)) {
                YupTv_Response yupTv_response = null;
                NubitYupTv nubitYupTv = null;

                try {
                    Gson gson = new Gson();
                    yupTv_response = gson.fromJson(get_SavedJson, YupTv_Response.class);
                    nubitYupTv = yupTv_response.getNubitYupTv();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    list_language.addAll(nubitYupTv.getLanguages());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    list_chanel.addAll(nubitYupTv.getChannel_list());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progress.setVisibility(View.GONE);

                try {
                    Yup_Lan_Adapter yup_lan_adapter = new Yup_Lan_Adapter(YupTv_Activity.this,
                            list_language);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager
                            (YupTv_Activity
                                    .this,
                                    LinearLayoutManager.HORIZONTAL, false);
                    recycler_language.setLayoutManager(layoutManager);
                    recycler_language.setAdapter(yup_lan_adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {
                    for (int i = 0; i < list_chanel.size(); i++) {
                        YupTv_Chanel_Pojo yupTv_chanel_pojo = list_chanel.get(i);
                        if (yupTv_chanel_pojo.getLangCode().equalsIgnoreCase(list_chanel.get(0)
                                .getLangCode())) {
                            yupTv_chanel_pojo.setChannelName(yupTv_chanel_pojo.getChannelName());
                            yupTv_chanel_pojo.setChannelIcon(yupTv_chanel_pojo.getChannelIcon());
                            yupTv_chanel_pojo.setChannelCode(yupTv_chanel_pojo.getChannelCode());
                            yupTv_chanel_pojo.setStreaming_url(yupTv_chanel_pojo.getStreaming_url
                                    ());
                            filtered_list_chanel.add(yupTv_chanel_pojo);
                        }

                    }


                    Yup_Cha_Adapter yup_cha_adapter = new Yup_Cha_Adapter(YupTv_Activity.this,
                            filtered_list_chanel);
                    RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager
                            (YupTv_Activity
                                    .this,
                                    LinearLayoutManager.VERTICAL, false);
                    recycler_chanel.setLayoutManager(layoutManager2);
                    recycler_chanel.setAdapter(yup_cha_adapter);


                    /*recycler_chanel.addOnItemTouchListener(new RecyclerItemClickListener
                            (YupTv_Activity.this, new RecyclerItemClickListener
                                    .OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            YupTv_Chanel_Pojo dataItem = filtered_list_chanel.get(position);

                            MyApplication.getInstance().trackEvent("Live Tv", dataItem
                                    .getStreaming_url(), dataItem.getChannelName());

                            MyUtility.handleItemClick(YupTv_Activity.this, dataItem
                                            .getPackage_name(),
                                    dataItem.getStreaming_url(), dataItem.getStreaming_url(),
                                    "Live Tv",
                                    dataItem.getOpen_with(), dataItem.getChannelName());

                        }
                    }));*/

                } catch (Exception e) {
                    Toast.makeText(YupTv_Activity.this, "Something went wrong,pls try again",
                            Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            Toast.makeText(YupTv_Activity.this, "Something went wrong,pls try again", Toast
                    .LENGTH_SHORT).show();
            progress.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public void updateChanel(String lanCode) {

        try {
            filtered_list_chanel.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            for (int i = 0; i < list_chanel.size(); i++) {
                YupTv_Chanel_Pojo yupTv_chanel_pojo = list_chanel.get(i);
                if (yupTv_chanel_pojo.getLangCode().equalsIgnoreCase(lanCode)) {
                    yupTv_chanel_pojo.setChannelName(yupTv_chanel_pojo.getChannelName());
                    yupTv_chanel_pojo.setChannelIcon(yupTv_chanel_pojo.getChannelIcon());
                    yupTv_chanel_pojo.setChannelCode(yupTv_chanel_pojo.getChannelCode());
                    yupTv_chanel_pojo.setStreaming_url(yupTv_chanel_pojo.getStreaming_url());
                    filtered_list_chanel.add(yupTv_chanel_pojo);
                }

            }


            Yup_Cha_Adapter yup_cha_adapter = new Yup_Cha_Adapter(YupTv_Activity.this,
                    filtered_list_chanel);
            RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(YupTv_Activity
                    .this,
                    LinearLayoutManager.VERTICAL, false);
            recycler_chanel.setLayoutManager(layoutManager2);
            recycler_chanel.setAdapter(yup_cha_adapter);
        } catch (Exception e) {
            Toast.makeText(YupTv_Activity.this, "Something went wrong,pls try again", Toast
                    .LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}


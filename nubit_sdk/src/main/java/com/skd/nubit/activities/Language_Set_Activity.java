package com.skd.nubit.activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.skd.nubit.R;
import com.skd.nubit.adapters.Language_Adapter;
import com.skd.nubit.models.LanguagePojo;
import com.skd.nubit.mycallbacks.LanguageUpdaterCallBack;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Language_Set_Activity extends Activity implements View.OnClickListener {

    ProgressBar progressbar_Language;
    private ArrayList<LanguagePojo> arrayList_language;
    private RecyclerView language_recycler;

    ImageView btn_back;

    Button btn_set_Language;

    String my_response;
    TextView txt_setLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_language_set);
        progressbar_Language = (ProgressBar) findViewById(R.id.progressbar_Language);
        language_recycler = (RecyclerView) findViewById(R.id.language_recyclerView);
        btn_set_Language = (Button) findViewById(R.id.btn_set_Language);
        btn_back = (ImageView) findViewById(R.id.btn_back);
        txt_setLanguage = (TextView) findViewById(R.id.txt_setLanguage);
        btn_back.setOnClickListener(this);
        btn_set_Language.setOnClickListener(this);
        btn_set_Language.setVisibility(View.GONE);

        MyApplication.getInstance().trackScreenView("Language Screen");


        if (!isAsyncTaskRunning) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                new Get_LanguageList().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                new Get_LanguageList().execute();
            }
        }
    }

    @Override
    public void onClick(View v) {


        int i1 = v.getId();
        if (i1 == R.id.btn_back) {
            finish();


        } else if (i1 == R.id.btn_set_Language) {
            if (MyUtility.isConnectedToInternet()) {

                for (int i = 0; i < arrayList_language.size(); i++) {
                    if (arrayList_language.get(i).isSelected()) {
                        try {
                            String language_selection = arrayList_language.get(i).getId();
                            MyApplication.app_editor.putString("language_selection",
                                    language_selection).apply();
                            LanguageUpdaterCallBack.getInstance().notifyNubitLanguageUpdater();

                            MyApplication.getInstance().trackEvent("Language Change",
                                    "Language ID is : " +
                                            language_selection, "Language Change Done : " +
                                            language_selection);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        finish();

                    }

                }


            } else {
                MyUtility.NoInternet_Msg(Language_Set_Activity.this);
            }


        }


    }

    boolean isAsyncTaskRunning = false;

    public class Get_LanguageList extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isAsyncTaskRunning = true;

            progressbar_Language.setVisibility(View.VISIBLE);
            txt_setLanguage.setText("Please wait, Loading...");

            // Toast.makeText(getApplicationContext(),"Showing all available languages.....",
            // Toast.LENGTH_SHORT).show();
        }


        protected String doInBackground(String... arg0) {

            try {

                String str_getJsonArray = MyApplication.app_sharedPreferences.getString
                        ("language_array", "");


                try {
                    arrayList_language = new ArrayList<>();
                    JSONArray get_languageList = new JSONArray(str_getJsonArray);
                    if (get_languageList != null && get_languageList.length() > 0) {
                        for (int i = 0; i < get_languageList.length(); i++) {
                            LanguagePojo language_pojo = new LanguagePojo();
                            JSONObject jsonObject_getDat = get_languageList.getJSONObject(i);

                            String id = null;
                            try {
                                id = jsonObject_getDat.getString("id");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            String language = null;
                            try {
                                language = jsonObject_getDat.getString("language");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (!TextUtils.isEmpty(MyApplication.app_sharedPreferences.getString
                                    ("language_selection", "")) && MyApplication
                                    .app_sharedPreferences.getString("language_selection", "")
                                    .equalsIgnoreCase(id)) {
                                language_pojo.setSelected(true);
                            }

                            language_pojo.setId(id);
                            language_pojo.setLanguage(language);


                            arrayList_language.add(language_pojo);

                            // Log.d("language","counter");


                        }

                        my_response = "good";
                    } else {
                        my_response = "not good";
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return my_response;
            } catch (Exception e) {
                return e.getMessage();
            }

        }


        @Override
        protected void onPostExecute(String result) {


            progressbar_Language.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(result) && result != null && result.equalsIgnoreCase("good")) {
                setLanguage_Adapter();
                btn_set_Language.setVisibility(View.VISIBLE);
                txt_setLanguage.setText("Select Language");
                isAsyncTaskRunning = false;
            } else {
                Toast.makeText(getApplicationContext(), "Opps something went wrong,Pls try after " +
                        "some " +
                        "time", Toast.LENGTH_LONG).show();
            }


        }
    }


    private void setLanguage_Adapter() {
        Language_Adapter language_adapter = new Language_Adapter(Language_Set_Activity.this,
                arrayList_language);
        language_recycler.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Language_Set_Activity.this,
                LinearLayoutManager.VERTICAL, false);
        language_recycler.setLayoutManager(layoutManager);
        language_recycler.setNestedScrollingEnabled(false);
        language_recycler.setAdapter(language_adapter);

    }

}

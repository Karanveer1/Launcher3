package com.skd.nubit.utilityclasses;


import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.skd.nubit.models.AppsModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

class GetGamesData extends AsyncTask<Void, Void, String>

{

    String str_vistoryData;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(Void... params) {


        try {
            Log.e("GetMainData","2>>" + params);

            String str_response = MyUtility.hitGetAppsAPI(MyApplication.ctx, MyUtility
                    .getappsDataAPI);

            if (str_response != null && !TextUtils.isEmpty(str_response)) {
                Log.e("GetMainData",">>" + str_response);
                JSONObject getProfile_jsonobject = new JSONObject(str_response);

                String str_getData = getProfile_jsonobject.getString("responseObject");
                JSONObject jsonObject_getDat = new JSONObject(str_getData);
                // String get_device_id = jsonObject_getDat.getString("device_id");
                ArrayList<AppsModel> newArray = new ArrayList<>();
                for (int i = 0; i < jsonObject_getDat.getJSONArray("content").length(); i++) {
                    JSONObject jsonObject = jsonObject_getDat.getJSONArray("content").getJSONObject(i);
                    Log.e("GetMainData",">>" + jsonObject.optString("title"));

                    String id = jsonObject.optString("id");
                    String orderNo = jsonObject.optString("orderNo");
                    String title = jsonObject.optString("title");
                    String category = jsonObject.optString("category");
                    String status = jsonObject.optString("status");
                    String redirectLink = jsonObject.optString("redirectLink");
                    String bannerImage = jsonObject.optString("bannerImage");
                    String bannerThumbImage = jsonObject.optString("bannerThumbImage");

                    AppsModel trendingNewsModel = new AppsModel(id, orderNo, title, category, status, redirectLink,
                            bannerImage, bannerThumbImage);
                    newArray.add(trendingNewsModel);
                }
                Log.e("GetMainData","newArray>00>" + newArray.size() + ">>" + newArray.get(1).getTitle());
                JSONArray save_json_jsonobject = null;
                Gson gson = new Gson();
                String json = gson.toJson(newArray);
//                editor.putString(KEY_CUSTOM_MODEL_ARRAY, json);

                save_json_jsonobject = new JSONArray(newArray);

                if (save_json_jsonobject != null) {
                    if (!TextUtils.isEmpty(save_json_jsonobject.toString())) {
                        Log.e("GetMainData","newArray>11>" + newArray.size());

                        MyApplication.app_editor.putString("save_app_games_new_Json", String.valueOf
                                (json));
                        Log.e("GetMainData","newArray>33>" + json);

                        MyApplication.app_editor.apply();
                    }


                }
//                    refreshMyFragment = "yes";
//                } else {
//                    refreshMyFragment = "no";
//                }


//                String get_image = "";
//                try {
//                    get_image = jsonObject_getDat.getString("image");
//                    MyApplication.app_editor.putString("userProfileIcon", get_image);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                String get_name = "";
//                try {
//                    get_name = jsonObject_getDat.getString("name");
//                    MyApplication.app_editor.putString("userProfileName", get_name);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                String get_phone_no = "";
//                try {
//                    get_phone_no = jsonObject_getDat.getString("phone_no");
//                    MyApplication.app_editor.putString("userProfileMob", get_phone_no);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                String get_dob = "";
//                try {
//                    get_dob = jsonObject_getDat.getString("dob");
//                    MyApplication.app_editor.putString("userProfileDOB", get_dob);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                String get_email = "";
//                try {
//                    get_email = jsonObject_getDat.getString("email");
//                    MyApplication.app_editor.putString("userProfileEMail", get_email);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                String get_occupation = "";
//                try {
//                    get_occupation = jsonObject_getDat.getString("occupation");
//
//                    MyApplication.app_editor.putString("userProfileProf", get_occupation);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
                MyApplication.app_editor.apply();

            } else {
                Log.d("nubit_problem", "userProfileInfo API not working,Pls check");
            }

            return str_vistoryData;
        } catch (Exception e) {
            Log.d("checkvistorydata", "userProfileInfo API not working,Pls check" + e.getMessage());
            e.printStackTrace();
            return "";
        }


    }

    @Override
    public void onPostExecute(String args) {


    }
}


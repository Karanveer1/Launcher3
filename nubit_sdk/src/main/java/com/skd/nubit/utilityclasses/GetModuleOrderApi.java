package com.skd.nubit.utilityclasses;


import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.skd.nubit.models.OrderModel;
import com.skd.nubit.models.VistoryModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

class GetModuleOrderApi extends AsyncTask<Void, Void, String>

{

    String str_vistoryData;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(Void... params) {


        try {
            Log.e("checkvistorydata","2>>" + params);

            String str_response = MyUtility.getModuleOrderApi(MyApplication.ctx, MyUtility
                    .getModuleOrderApi);

            if (str_response != null && !TextUtils.isEmpty(str_response)) {
                Log.e("checkvistorydata",">>" + str_response);
                JSONObject getProfile_jsonobject = new JSONObject(str_response);

                String str_getData = getProfile_jsonobject.getString("responseObject");
                JSONObject jsonObject_getDat = new JSONObject(str_getData);
                // String get_device_id = jsonObject_getDat.getString("device_id");
                ArrayList<OrderModel> newArray = new ArrayList<>();
                for (int i = 0; i < jsonObject_getDat.getJSONArray("content").length(); i++) {
                    JSONObject jsonObject = jsonObject_getDat.getJSONArray("content").getJSONObject(i);
                    Log.e("checkarray",">>" + jsonObject.optString("id"));

                    String id = jsonObject.optString("id");
                    String name = jsonObject.optString("name");
                    String sectionType = jsonObject.optString("sectionType");
                    String placementOrder = jsonObject.optString("placementOrder");
                    String header = jsonObject.optString("header");
                    String description = jsonObject.optString("description");
                    String sectionDesignElement = jsonObject.optString("sectionDesignElement");
                    JSONArray urlDetails = jsonObject.optJSONArray("urlDetails");
                    Log.e("checkarray","urlDetails>00>" + urlDetails.toString());

                    OrderModel vistoryModel = new OrderModel(id, name, sectionType, placementOrder, header, description, sectionDesignElement, urlDetails);
                    newArray.add(vistoryModel);
                }
                Log.e("checkarray","newArray>00>" + newArray.size() + ">>" + newArray.get(1).getName());
                JSONArray save_json_jsonobject = null;
                Gson gson = new Gson();
                String json = gson.toJson(newArray);
//                editor.putString(KEY_CUSTOM_MODEL_ARRAY, json);

                save_json_jsonobject = new JSONArray(newArray);

                if (save_json_jsonobject != null) {
                    if (!TextUtils.isEmpty(save_json_jsonobject.toString())) {
                        Log.e("checkarray","newArray>11>" + newArray.size());

                        MyApplication.app_editor.putString("save_app_order_Json", String.valueOf
                                (json));
                        Log.e("checkarray","newArray>33>" + json);

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


package com.skd.nubit.utilityclasses;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;


import org.json.JSONObject;

class GetProfileData extends AsyncTask<Void, Void, String>

{

    String str_profileData;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(Void... params) {


        try {

            String str_response = MyUtility.hitGetProfileAPI(MyApplication.ctx, MyUtility
                    .getprofileDataAPI);

            if (str_response != null && !TextUtils.isEmpty(str_response)) {

                JSONObject getProfile_jsonobject = new JSONObject(str_response);


                String str_getData = getProfile_jsonobject.getString("data");
                JSONObject jsonObject_getDat = new JSONObject(str_getData);
                // String get_device_id = jsonObject_getDat.getString("device_id");
                String get_image = "";
                try {
                    get_image = jsonObject_getDat.getString("image");
                    MyApplication.app_editor.putString("userProfileIcon", get_image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String get_name = "";
                try {
                    get_name = jsonObject_getDat.getString("name");
                    MyApplication.app_editor.putString("userProfileName", get_name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String get_phone_no = "";
                try {
                    get_phone_no = jsonObject_getDat.getString("phone_no");
                    MyApplication.app_editor.putString("userProfileMob", get_phone_no);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String get_dob = "";
                try {
                    get_dob = jsonObject_getDat.getString("dob");
                    MyApplication.app_editor.putString("userProfileDOB", get_dob);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String get_email = "";
                try {
                    get_email = jsonObject_getDat.getString("email");
                    MyApplication.app_editor.putString("userProfileEMail", get_email);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String get_occupation = "";
                try {
                    get_occupation = jsonObject_getDat.getString("occupation");

                    MyApplication.app_editor.putString("userProfileProf", get_occupation);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                MyApplication.app_editor.apply();

            } else {
                Log.d("nubit_problem", "userProfileInfo API not working,Pls check");
            }

            return str_profileData;
        } catch (Exception e) {
            return "";
        }


    }

    @Override
    public void onPostExecute(String args) {


    }
}


package com.skd.nubit.utilityclasses;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

class GetWeatherData extends AsyncTask<Void, Void, String>

{

    String str_vistoryData;
    private static final int REQUEST_LOCATION_PERMISSION = 1001;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(Void... params) {


//        try {
//            Log.e("checkvistorydata","2>>" + params);
//
//            String str_response = MyUtility.hitGetWeatherAPI(MyApplication.ctx, MyUtility
//                    .getweatherDataAPI);
//
//            if (str_response != null && !TextUtils.isEmpty(str_response)) {
//                Log.e("checkvistorydata",">>" + str_response);
//                JSONObject getProfile_jsonobject = new JSONObject(str_response);
//
//                String str_getData = getProfile_jsonobject.getString("responseObject");
//                JSONArray save_json_jsonobject = null;
//                Gson gson = new Gson();
//                String json = gson.toJson(str_getData);
//
//                if (save_json_jsonobject != null) {
//                    if (!TextUtils.isEmpty(save_json_jsonobject.toString())) {
//
//                        MyApplication.app_editor.putString("save_app_weather_Json", String.valueOf
//                                (json));
//                        Log.e("checkarray","newArray>33>" + json);
//
//                        MyApplication.app_editor.apply();
//                    }
//                }
//                MyApplication.app_editor.apply();
//
//            } else {
//                Log.d("nubit_problem", "userProfileInfo API not working,Pls check");
//            }
//
//            return str_vistoryData;
//        } catch (Exception e) {
//            Log.d("checkvistorydata", "userProfileInfo API not working,Pls check" + e.getMessage());
//            e.printStackTrace();
//            return "";
//        }

        try {
            // Check and request location permission if not granted
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    ActivityCompat.checkSelfPermission(MyApplication.ctx, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                // You need to request the permission.
                // Provide an appropriate rationale and proceed to request the permission.
                // Check whether you've already shown rationale for this permission request
                // and whether the user has previously denied the request.
                // You can use ActivityCompat.shouldShowRequestPermissionRationale() method
                // to provide more context for the user.

                // Request the permission
                ActivityCompat.requestPermissions((Activity) MyApplication.ctx,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            } else {
                // Permission has already been granted
                // Fetch the location
                LocationFetcher locationFetcher = new LocationFetcher(MyApplication.ctx);
                Location location = locationFetcher.getLastLocation();

                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    Log.e("checkweatherdata",">latlng>" + latitude + ">>" + longitude);

                    // Now you can pass latitude and longitude to your API
                    String str_response = MyUtility.getRequest(MyApplication.ctx, MyUtility
                            .getweatherDataAPI,latitude,longitude);
//                    String str_response = MyUtility.hitGetWeatherAPI(MyApplication.ctx, MyUtility
//                            .getweatherDataAPI,latitude,longitude);

                    if (str_response != null && !TextUtils.isEmpty(str_response)) {
                        Log.e("checkweatherdata",">>" + str_response);
                        JSONObject getProfile_jsonobject = new JSONObject(str_response);

                        JSONObject str_getData = getProfile_jsonobject.getJSONObject("responseObject");
                        Gson gson = new Gson();
                        String json = gson.toJson(str_getData);
                        Log.e("checkweatherdata",">json>" + json);

                        if (json != null) {
                            if (!TextUtils.isEmpty(json)) {

                                MyApplication.app_editor.putString("save_app_weather_Json", String.valueOf
                                        (json));
                                Log.e("checkacecrrray","newArray>33>" + json);

                                MyApplication.app_editor.apply();
                            }
                        }
                        MyApplication.app_editor.apply();

                    } else {
                        Log.d("nubit_problem", "userProfileInfo API not working,Pls check");
                    }
                } else {
                    Log.d("nubit_problem", "Location is null");
                }
            }
        } catch (Exception e) {
            Log.d("nubit_problem", "Error fetching weather data: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onPostExecute(String args) {


    }
}


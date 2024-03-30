package com.skd.nubit.apicalls;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.skd.nubit.utilityclasses.MyUtility;

import java.util.HashMap;



/**
 * Created by robi on 16/12/18.
 */

public class PushTracksOnServerApiTask extends AsyncTask<Void, Void, String> {

    PushTracksDataCallback mCallback;
    String jsonVal;
    Context mContext;
    String mResponse;

    public PushTracksOnServerApiTask(Context context, String value, PushTracksDataCallback pushTracksDataCallback) {
        mCallback = pushTracksDataCallback;
        jsonVal = value;
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {

        HashMap<String, String> mMap = new HashMap<>();
        mMap.put("mobile_clicks", jsonVal);

        String url = MyUtility.PUSH_TRACKS;

        mResponse = MyUtility.requestWebService(url, mMap);

        return mResponse;
    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);

        if (!TextUtils.isEmpty(aVoid)) {
            mCallback.onSuccess(aVoid);
        } else {
            mCallback.failure();
        }

    }

    public interface PushTracksDataCallback {
        void onSuccess(String response);

        void failure();
    }

}

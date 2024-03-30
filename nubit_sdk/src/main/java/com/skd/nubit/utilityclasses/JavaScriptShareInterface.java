package com.skd.nubit.utilityclasses;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class JavaScriptShareInterface {
    @JavascriptInterface
    public void share(String url) {
        // your share action
        Log.e("checkshare", ">>");
    }
}
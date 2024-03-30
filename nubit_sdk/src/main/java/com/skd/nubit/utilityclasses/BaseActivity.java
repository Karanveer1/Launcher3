package com.skd.nubit.utilityclasses;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.skd.nubit.R;

public abstract class BaseActivity extends AppCompatActivity {

//    private Alerts alerts;

    protected abstract int getLayoutResId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutResId() != 0)
            setContentView(getLayoutResId());

        setupUI(savedInstanceState);
    }


    public Toolbar setUpToolbar(String title) {

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return toolbar;

    }

    protected abstract void setupUI(Bundle bundle);

//    public final void showToast(String message) {
//        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//    }
//
//    public final void showToast(int resId) {
//        Toast.makeText(getApplicationContext(), getResources().getString(resId), Toast.LENGTH_SHORT).show();
//    }
//
//    public void showNetworkAlert(int resId, OnMethodCallListener listener) {
////        if (alerts == null)
////            alerts = new Alerts(getWindow().getContext());
////        alerts.showNetworkAlert(resId, listener);
//    }
}


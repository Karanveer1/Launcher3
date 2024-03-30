package com.skd.nubit.activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skd.nubit.R;


public class HomeFragment extends Fragment {

    View view;

    Context context;




    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {

        if (view == null) {

            view = inflater.inflate(R.layout.fragment_home_sdk, container, false);
        }


        return view;
    }

    private static HomeFragment homeFragment;

    public static HomeFragment getInstance() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();

        }
        return homeFragment;
    }
}




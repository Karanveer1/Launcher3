package com.skd.nubit.utilityclasses;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.skd.nubit.R;

public abstract class BaseDialogFragment extends DialogFragment {


    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
    }

    public View findViewById(int id) {
        return view.findViewById(id);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.window;
        getDialog().setCancelable(false);
        onDialogCreated(savedInstanceState);
    }


    public void updateDialogAnimation(int resourceid) {
        getDialog().getWindow()
                .getAttributes().windowAnimations = resourceid;
    }

    public abstract int getLayoutId();

    public abstract void onDialogCreated(Bundle savedInstanceState);

}

package com.skd.nubit.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.skd.nubit.R;
import com.skd.nubit.activities.YupTv_Activity;
import com.skd.nubit.models.Yuptv_lang_pojo;
import com.skd.nubit.utilityclasses.MyApplication;

import java.util.ArrayList;

public class Yup_Lan_Adapter extends RecyclerView.Adapter<Yup_Lan_Adapter.MyViewHolder> {
    private ArrayList<Yuptv_lang_pojo> android;
    Context context;


    int row_index = 0;


    public Yup_Lan_Adapter(Context context, ArrayList<Yuptv_lang_pojo> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public Yup_Lan_Adapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .yup_lan_adapter_items, viewGroup, false);
        Yup_Lan_Adapter.MyViewHolder mViewHold = new Yup_Lan_Adapter.MyViewHolder(view);
        return mViewHold;
    }

    @Override
    public void onBindViewHolder(Yup_Lan_Adapter.MyViewHolder viewHolder, final int i) {

        final Yuptv_lang_pojo dataItem = android.get(i);
        final Yup_Lan_Adapter.MyViewHolder myViewHolder = viewHolder;

        try {
            myViewHolder.txt_language.setText(dataItem.getLanguagename());
        } catch (Exception e) {
            e.printStackTrace();
        }


        myViewHolder.txt_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = i;


                MyApplication.getInstance().trackEvent("Live Tv", dataItem
                        .getLanguageid(), dataItem.getLanguagename());
                try {
                    if (context instanceof YupTv_Activity) {
                        ((YupTv_Activity) context).updateChanel(dataItem.getLanguageid());
                    }
                } catch (Exception e) {
                    Toast.makeText(context, "Something went wrong,pls try again", Toast
                            .LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                notifyDataSetChanged();

            }
        });
        if (row_index == i) {
            myViewHolder.txt_language.setBackgroundResource(R.drawable.yup_lan_selected);
        } else {
            myViewHolder.txt_language.setBackgroundResource(R.drawable.yup_lan_unse);
        }

    }

    @Override
    public int getItemCount() {
        return android.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_language;


        public MyViewHolder(View view) {
            super(view);
            txt_language = view.findViewById(R.id.txt_language);

        }
    }


}
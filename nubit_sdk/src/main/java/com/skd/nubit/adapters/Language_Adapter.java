package com.skd.nubit.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.skd.nubit.R;
import com.skd.nubit.models.LanguagePojo;

import java.util.ArrayList;

public class Language_Adapter   extends RecyclerView.Adapter<Language_Adapter.MyViewHolder> {
    private ArrayList<LanguagePojo> arrayList_Language;
    Context context;

    Integer selected_position = -1;


    public Language_Adapter(Context context, ArrayList<LanguagePojo> arrayList_Language) {
        this.arrayList_Language = arrayList_Language;
        this.context = context;
    }

    @Override
    public Language_Adapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .layout_language_item, viewGroup, false);

        Language_Adapter.MyViewHolder mViewHold = new Language_Adapter.MyViewHolder(view);
        return mViewHold;
    }

    @Override
    public void onBindViewHolder(Language_Adapter.MyViewHolder viewHolder, final int pos) {

        final LanguagePojo dataItem = arrayList_Language.get(pos);
        final Language_Adapter.MyViewHolder myViewHolder = viewHolder;


        String getLanguageText = dataItem.getLanguage().toString();
        String upperString = getLanguageText.substring(0, 1).toUpperCase() + getLanguageText
                .substring(1);
        myViewHolder.txt_languageTitle.setText(upperString);


        myViewHolder.language_checkbox.setChecked(dataItem.isSelected());
        myViewHolder.language_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for (int i = 0; i < arrayList_Language.size(); i++) {
                    arrayList_Language.get(i).setSelected(false);
                }
                arrayList_Language.get(pos).setSelected(true);
                notifyDataSetChanged();


            }
        });


    }


    @Override
    public int getItemCount() {
        return arrayList_Language.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_languageTitle;
        private CheckBox language_checkbox;

        public MyViewHolder(View view) {
            super(view);

            txt_languageTitle = view.findViewById(R.id.txt_language);
            language_checkbox = view.findViewById(R.id.language_checkbox);


        }


    }


}
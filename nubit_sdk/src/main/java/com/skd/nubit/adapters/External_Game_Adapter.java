package com.skd.nubit.adapters;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.skd.nubit.R;
import com.skd.nubit.models.AppsModel;
import com.skd.nubit.models.External_game_pojo;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

import java.util.ArrayList;



/**
 * Created by robi on 5/7/17.
 */

public class External_Game_Adapter extends RecyclerView.Adapter<External_Game_Adapter
        .MyViewHolder> {
    private ArrayList<AppsModel> android;
    Context context;


    public External_Game_Adapter(Context context, ArrayList<AppsModel> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public External_Game_Adapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .external_game_items, viewGroup, false);

        External_Game_Adapter.MyViewHolder mViewHold = new External_Game_Adapter.MyViewHolder(view);
        return mViewHold;
    }

    @Override
    public void onBindViewHolder(External_Game_Adapter.MyViewHolder viewHolder, int i) {

        final AppsModel dataItem = android.get(i);
        final External_Game_Adapter.MyViewHolder myViewHolder = viewHolder;
        Glide.with(MyApplication.ctx).load(dataItem.getBannerImage())
                .crossFade().placeholder(R.drawable.placeholder_apps)
                .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder_apps)
                .into(myViewHolder.externalGames_icon);
        myViewHolder.btn_install_externalGames.setText(dataItem.getTitle());
        myViewHolder.txt_externalGames_name.setText(dataItem.getTitle());


        myViewHolder.btn_install_externalGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyApplication.getInstance().trackEvent("Html Games", dataItem.getRedirectLink(),
                        dataItem.getTitle());
                MyUtility.handleItemClick(context, "", dataItem
                        .getRedirectLink(), dataItem.getBannerThumbImage(), "Html Games",
                        "", dataItem.getTitle());
//                MyUtility.handleItemClick(context, dataItem.getPackage_name(), dataItem
//                        .getRedirect_link(), dataItem.getBannerThumbImage(), "Html Games",
//                        dataItem.getOpen_with(), dataItem.getTitle());

            }
        });



    }

    @Override
    public int getItemCount() {
        return android.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView externalGames_icon;
        private Button btn_install_externalGames;
        TextView txt_externalGames_name;

        private FrameLayout admob_blank_layout;

        private CardView externalGames_cardview;

        public MyViewHolder(View view) {
            super(view);
            externalGames_icon = (ImageView) view.findViewById(R.id.externalGames_icon);
            btn_install_externalGames = (Button) view.findViewById(R.id.btn_install_externalGames);
            txt_externalGames_name = (TextView) view.findViewById(R.id.txt_externalGames_name);
            externalGames_cardview = (CardView) view.findViewById(R.id.externalGames_cardview);
            externalGames_cardview.getLayoutParams().width = MyUtility.getScreenWidth
                    (externalGames_cardview.getContext()) / 3;




        }

    }
}

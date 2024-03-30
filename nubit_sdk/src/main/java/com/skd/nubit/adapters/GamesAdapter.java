package com.skd.nubit.adapters;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.skd.nubit.R;
import com.skd.nubit.models.AppsModel;
import com.skd.nubit.models.GamesPojo;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

import java.util.ArrayList;

/**
 * Created by robi on 3/7/17.
 */

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.MyViewHolder> {
    private ArrayList<AppsModel> android;
    Context context;


    public GamesAdapter(Context context, ArrayList<AppsModel> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public GamesAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .games_adapter_items, viewGroup, false);

        GamesAdapter.MyViewHolder mViewHold = new GamesAdapter.MyViewHolder(view);
        return mViewHold;
    }

    @Override
    public void onBindViewHolder(GamesAdapter.MyViewHolder viewHolder, int i) {

        final AppsModel dataItem = android.get(i);
        GamesAdapter.MyViewHolder myViewHolder = viewHolder;
        Glide.with(MyApplication.ctx).load(dataItem.getBannerImage()).thumbnail(0.5f)
                .crossFade().placeholder(R.drawable.placeholder_apps)
                .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder_apps)
                .into(myViewHolder.games_icon);
        myViewHolder.btn_install_games.setText(dataItem.getTitle());

        myViewHolder.txt_games_name.setText(dataItem.getTitle());
        myViewHolder.btn_install_games.setText(dataItem.getTitle());


        myViewHolder.btn_install_games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyApplication.getInstance().trackEvent("Games", dataItem.getRedirectLink(),
                        dataItem.getTitle());

//                MyUtility.handleItemClick(context, dataItem.getPackage_name(), dataItem
//                        .getRedirect_link(), dataItem.getBanner_thumb_image(), "Games", dataItem
//                        .getOpen_with(), dataItem.getTitle());
                MyUtility.handleItemClick(context, "", dataItem
                        .getRedirectLink(), dataItem.getBannerImage(), "Games", ""
                        , dataItem.getTitle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return android.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener */ {

        private ImageView games_icon;
        private Button btn_install_games;
        TextView txt_games_name;

        private CardView games_cardview;


        public MyViewHolder(View view) {
            super(view);
            games_icon = (ImageView) view.findViewById(R.id.games_icon);
            btn_install_games = (Button) view.findViewById(R.id.btn_install_games);
            txt_games_name = (TextView) view.findViewById(R.id.txt_games_name);

            games_cardview = (CardView) view.findViewById(R.id.games_cardview);
            games_cardview.getLayoutParams().width = MyUtility.getScreenWidth(games_icon
                    .getContext()) / 3;


        }
    }
}

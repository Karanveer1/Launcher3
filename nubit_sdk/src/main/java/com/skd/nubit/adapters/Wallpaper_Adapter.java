package com.skd.nubit.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.skd.nubit.R;
import com.skd.nubit.models.Wallpaper_Pojo;
import com.skd.nubit.utilityclasses.MyApplication;
import com.skd.nubit.utilityclasses.MyUtility;

import java.util.ArrayList;

/**
 * Created by robi on 3/7/17.
 */

public class Wallpaper_Adapter extends RecyclerView.Adapter<Wallpaper_Adapter.MyViewHolder> {
    private ArrayList<Wallpaper_Pojo> android;
    Context context;


    public Wallpaper_Adapter(Context context, ArrayList<Wallpaper_Pojo> android) {
        this.android = android;
        this.context = context;
    }

    @Override
    public Wallpaper_Adapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
                .wallpaper_adapter_items, viewGroup, false);
        Wallpaper_Adapter.MyViewHolder mViewHold = new Wallpaper_Adapter.MyViewHolder(view);
        return mViewHold;
    }

    @Override
    public void onBindViewHolder(Wallpaper_Adapter.MyViewHolder viewHolder, final int i) {

        final Wallpaper_Pojo dataItem = android.get(i);
        final Wallpaper_Adapter.MyViewHolder myViewHolder = viewHolder;
        Glide.with(MyApplication.ctx).load(dataItem.getBanner_thumb_image()).crossFade()
                .placeholder(R
                        .drawable.placeholder_wallpaper)
                .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.placeholder_wallpaper)
                .into(myViewHolder.wallpaper_icon);


        myViewHolder.img_wall_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wallpaper_Pojo wallpaper_pojo = android.get(i);

                MyApplication.getInstance().trackEvent("Wallpaper Share", wallpaper_pojo
                        .getRedirect_link(), wallpaper_pojo.getTitle());

                MyUtility.show_Progress_Popup(context);


                Glide.with(MyApplication.ctx)
                        .load(wallpaper_pojo.getBanner_thumb_image())
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super
                                    Bitmap> glideAnimation) {
                                // you can do something with loaded bitmap here

                                try {
                                    MyUtility.Glide_share(MyApplication.ctx, resource);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });

            }
        });


    }

    @Override
    public int getItemCount() {
        try {
            return android.size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView wallpaper_icon, img_wall_share;
        FrameLayout admob_blank_layout;

        private CardView wallpaper_cardview;


        public MyViewHolder(View view) {
            super(view);
            wallpaper_icon = view.findViewById(R.id.wallpaper_banner);
            img_wall_share = view.findViewById(R.id.img_wall_share);

            wallpaper_cardview = view.findViewById(R.id.wallpaper_cardview);
            wallpaper_cardview.getLayoutParams().width = MyUtility.getScreenWidth(wallpaper_icon
                    .getContext()) / 3;
            wallpaper_cardview.getLayoutParams().height = (int) (MyUtility.getScreenWidth
                    (wallpaper_icon.getContext()) / (1.8));
            wallpaper_icon.setScaleType(ImageView.ScaleType.CENTER_CROP);

            String wallpaper_share_flg = MyApplication.app_sharedPreferences.getString
                    ("wallpaper_share_flg", "");
            if (!TextUtils.isEmpty(wallpaper_share_flg) & wallpaper_share_flg.equalsIgnoreCase
                    ("1")) {
                img_wall_share.setVisibility(View.VISIBLE);
                /*img_wall_share.setVisibility(View.GONE);*/
            } else {
                img_wall_share.setVisibility(View.GONE);
            }


        }
    }


}

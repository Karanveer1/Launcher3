package com.skd.nubit.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.skd.nubit.R;
import com.skd.nubit.models.BannersModel;
import com.skd.nubit.utilityclasses.MyApplication;

import java.util.List;

public class NewBannerAdapter extends RecyclerView.Adapter<NewBannerAdapter.YouTubePlayerViewHolder> {
    private List<BannersModel> videoIds;
    private boolean isAutoplayEnabled = true; // Flag to control autoplay
    private int currentPosition = 0;
    private Context context;
    private YouTubePlayerViewHolder currentViewHolder;


    public NewBannerAdapter(List<BannersModel> videoIds, Context lifecycle) {
        this.videoIds = videoIds;
        this.context = lifecycle;
    }

    @NonNull
    @Override
    public YouTubePlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_banner_item, parent, false);
        return new YouTubePlayerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull YouTubePlayerViewHolder viewHolder, int position) {
        Log.e("checjfef",">> " + videoIds.get(position).getImageUrl());
        Glide.with(MyApplication.ctx).load(videoIds.get(position).getImageUrl()).asBitmap()
                .placeholder(R.drawable.banner_placholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.banner_placholder)
                .into(viewHolder.iv_banner_new);
    }

    @Override
    public int getItemCount() {
        return videoIds.size();
    }

    public class YouTubePlayerViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_banner_new;

        public YouTubePlayerViewHolder(View itemView) {
            super(itemView);
            iv_banner_new = itemView.findViewById(R.id.iv_banner_new);
        }
    }
    // Add a method to update the current position
    public void setCurrentPosition(int position) {
        currentPosition = position;
    }
}
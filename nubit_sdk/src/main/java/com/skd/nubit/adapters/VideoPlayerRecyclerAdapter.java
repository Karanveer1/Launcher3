package com.skd.nubit.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.skd.nubit.R;
import com.skd.nubit.models.MediaObject;

import java.util.ArrayList;

public class VideoPlayerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<MediaObject> mediaObjects;
    private RequestManager requestManager;


    public VideoPlayerRecyclerAdapter(ArrayList<MediaObject> mediaObjects, RequestManager requestManager) {
        this.mediaObjects = mediaObjects;
        this.requestManager = requestManager;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VideoPlayerViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_video_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((VideoPlayerViewHolder)viewHolder).onBind(mediaObjects.get(i), requestManager);
    }

    @Override
    public int getItemCount() {
        return mediaObjects.size();
    }

    public class VideoPlayerViewHolder extends RecyclerView.ViewHolder {

        public FrameLayout media_container;
        public TextView title;
        public ImageView thumbnail, volumeControl;
        public ProgressBar progressBar;
        public View parent;
        public RequestManager requestManager;

        public VideoPlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView;
            media_container = itemView.findViewById(R.id.media_container);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.title);
            progressBar = itemView.findViewById(R.id.progressBar);
            volumeControl = itemView.findViewById(R.id.volume_control);
        }

        public void onBind(MediaObject mediaObject, RequestManager requestManager) {
            this.requestManager = requestManager;
            parent.setTag(this);
            title.setText(mediaObject.getTitle());
            Log.e("cechec","1>>" + mediaObject.getThumbnail());
            this.requestManager
                    .load(mediaObject.getThumbnail())
                    .into(thumbnail);
        }

    }
}

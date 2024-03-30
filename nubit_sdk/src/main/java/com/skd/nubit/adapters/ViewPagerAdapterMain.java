package com.skd.nubit.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.skd.nubit.activities.WebViewFragment;
import com.skd.nubit.models.youTubeVideos;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapterMain extends FragmentStatePagerAdapter {
    private List<youTubeVideos> videos;

    public ViewPagerAdapterMain(FragmentManager fragmentManager) {
        super(fragmentManager);
        videos = new ArrayList<>();
        // Add video URLs here
    }

    public void addVideo(youTubeVideos videoUrl) {
        videos.add(videoUrl);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return WebViewFragment.newInstance(videos.get(position));
    }

    @Override
    public int getCount() {
        return videos.size();
    }
}
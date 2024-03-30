package com.skd.nubit.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.skd.nubit.activities.VideoFragment;
import com.skd.nubit.models.VideoModel;

import java.util.ArrayList;

public class VideoPagerDynamicAdapter extends FragmentStateAdapter {
    private ArrayList<String> numVideoFragments;
    private int selectedVideoIndex;
    private String cat_type;
    private String activity;

    public VideoPagerDynamicAdapter(FragmentActivity fragmentActivity,
                                    ArrayList<String> numVideoFragments,
                                    int selectedVideoIndex,
                                    String cat_type,
                                    String activity) {
        super(fragmentActivity);
        this.numVideoFragments = numVideoFragments;
        this.selectedVideoIndex = selectedVideoIndex;
        this.cat_type = cat_type;
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        return numVideoFragments.size(); // Set a large number to make it seem infinite
    }

    @Override
    public Fragment createFragment(int position) {
        // Calculate the actual position by taking the modulo of the number of fragments
        int actualPosition = position % numVideoFragments.size();
        System.out.println("checkadapterflow" + " >createFragment title>" + numVideoFragments.get(actualPosition));
//                ">url>" + numVideoFragments.get(actualPosition).getUrl() +
//                ">preview>" + numVideoFragments.get(actualPosition).getThumbnail() +
//                ">>" + numVideoFragments.size());

        return VideoFragment.newInstance(numVideoFragments.get(actualPosition),
                numVideoFragments.get(actualPosition),
                numVideoFragments.get(actualPosition),
                selectedVideoIndex,
                cat_type,
                activity);
    }

    public Fragment getFragment(int position) {
        // Calculate the actual position by taking the modulo of the number of fragments
        int actualPosition = position % numVideoFragments.size();
        return createFragment(actualPosition);
    }

    public int getSelectedVideoIndex() {
        return selectedVideoIndex;
    }
}
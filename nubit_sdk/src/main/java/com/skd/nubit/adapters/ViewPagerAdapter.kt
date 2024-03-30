package com.simplemobiletools.test.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.simplemobiletools.test.fragments.WebViewFragment
import com.simplemobiletools.test.models.youTubeVideos


class ViewPagerAdapter(fragmentManager: FragmentManager?) :
    FragmentStatePagerAdapter(fragmentManager!!) {
    private val videos: MutableList<youTubeVideos>

    init {
        videos = ArrayList()
        // Add video URLs here
    }

    fun addVideo(videoUrl: youTubeVideos) {
        videos.add(videoUrl)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return WebViewFragment.newInstance(videos[position])
    }

    override fun getCount(): Int {
        return videos.size
    }
}
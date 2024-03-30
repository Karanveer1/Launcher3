package com.android.launcher3.theme;
 
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.graphics.Bitmap;

import androidx.viewpager.widget.PagerAdapter;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private final List<Bitmap> mPic;

    public ViewPagerAdapter(List<Bitmap> pic){
        mPic = pic;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = position % mPic.size();
        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageBitmap(mPic.get(realPosition));
        container.addView(imageView);
        return imageView;
    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public float getPageWidth(int position) {
        return (float) 0.8;
    }

    @Override
    public int getCount() {
        if (mPic != null) {
            return mPic.size();
        }
        return 0;
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}

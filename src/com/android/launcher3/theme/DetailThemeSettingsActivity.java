package com.android.launcher3.theme;
 
import android.app.Activity;
 
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Button;
import androidx.viewpager.widget.ViewPager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.GridView;
import android.widget.AdapterView;
import android.app.ActionBar;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.android.launcher3.R;
import android.app.ProgressDialog;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import android.os.Handler;

public class DetailThemeSettingsActivity extends Activity{
    private ViewPager mViewPager;
    private DotsPageIndicator mPageIndicator;
    private Button mApplyButton;
    private static final List<Bitmap> mPic = new ArrayList<>();

    private ViewPagerAdapter mAdapter;
    private Handler mHandler;
    private boolean mIsTouch = false;
    private String mThemeName;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_themesettings);
        final ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        } else {
            Log.d("theme", "DetailThemeSettingsActivity actionbar is null");
        }
        mThemeName = getIntent().getStringExtra("themeName");
        Log.d("theme", "DetailThemeSettingsActivity mThemeName " + mThemeName);
        if(TextUtils.isEmpty(mThemeName)) {
            finish();
            Toast.makeText(this, R.string.theme_apply_success, Toast.LENGTH_SHORT).show();
        }
        initView();
    }
 
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }
 
    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public boolean onNavigateUp() {
        finish();
        return super.onNavigateUp();
    }

    private void initView() {
        mPic.clear();
        mPic.add(getPreviewFromThemeToList("preview01"));
        mPic.add(getPreviewFromThemeToList("preview02"));
        mPic.add(getPreviewFromThemeToList("preview03"));
        //https://www.yisu.com/ask/22044833.html
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getResources().getString(R.string.theme_applying));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);

        mApplyButton = (Button)this.findViewById(R.id.apply);
        mPageIndicator = (DotsPageIndicator) this.findViewById(R.id.page_indicator);
        mViewPager = this.findViewById(R.id.view_pager);
        mAdapter = new ViewPagerAdapter(mPic);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(0,true);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setPageMargin(10); 

        mPageIndicator.setViewPager(mViewPager);
        mPageIndicator.setVisibility(View.VISIBLE);
        mPageIndicator.setOnPageChangeListener(null);

        mApplyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mProgressDialog.show();
                setCurrentTheme(mThemeName);
            }
        });
        //for debug only
        /*ImageView test = (ImageView)findViewById(R.id.grid_img);
        //new ColorDrawable(0x0100ff00)
        AdaptiveIconDrawable adaptiveIconDrawable = new AdaptiveIconDrawable(null, new BitmapDrawable(this.getResources(), getICON("settings")), null);
        test.setImageDrawable(adaptiveIconDrawable);*/
        //end
    }

    public Bitmap getICON(String source) {
        InputStream is = null;
        Bitmap bm = null;
        try {
            is = getInputStream("icon" + File.separator + source + ".png", new ZipFile("/system/etc/theme/CelebrateEaster.theme"));
            if (is != null) {
                bm = BitmapFactory.decodeStream(is);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(is != null) {
                    is.close();
                }
            } catch(Exception e) {

            }
        }
        return bm;
    }

    private void setCurrentTheme(String themepath) {
        Settings.System.putString(this.getContentResolver(), "theme_path", themepath);
        Intent intent = new Intent("android.intent.action.THEME_CHANGED");
        intent.setPackage("com.android.launcher3");
        sendBroadcast(intent);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                try {
                    if(mProgressDialog != null)
                    mProgressDialog.dismiss();
                } catch(Exception e) {
                    //ignore
                }
            }
        },8000);
    }

    public Bitmap getPreviewFromThemeToList(String source) {
        InputStream is = null;
        Bitmap bm = null;
        try {
            is = getInputStream("res" + File.separator + source + ".jpg", new ZipFile(mThemeName));
            if (is != null) {
                bm = BitmapFactory.decodeStream(is);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(is != null) {
                    is.close();
                }
            } catch(Exception e) {

            }
        }
        return bm;
    }

    private InputStream getInputStream(String path, ZipFile zipFile) {
        if (zipFile == null) {
            return null;
        }
        try {
            ZipEntry entry = zipFile.getEntry(path);
            if (entry != null) {
                return zipFile.getInputStream(entry);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

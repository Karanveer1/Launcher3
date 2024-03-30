package com.android.launcher3.theme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.provider.Settings;
import com.android.launcher3.R;
import android.widget.GridView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.content.Intent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.GridView;
import android.widget.AdapterView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ThemeSettingsActivity extends Activity {
    private static final String LOG = "ThemeSettingsActivity";
    private GridView gridView;
    private List<Map<String, Object>> list;
    private int images[];
    private String THEME_FILE_PATH = "/system/etc/theme";
    private File[] mFiles;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_themesettings);
        gridView = (GridView) findViewById(R.id.gv);
        list = new ArrayList<Map<String, Object>>();
        File file = new File(THEME_FILE_PATH);
        mFiles = file.listFiles();
        for (int i = 0; i < mFiles.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", getPreviewFromTheme(mFiles[i].getAbsolutePath()));
            map.put("text",  mFiles[i].getName().substring(0, mFiles[i].getName().indexOf(".")));
            list.add(map);
        }
        MyAdapter adapter = new MyAdapter();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String themeName = mFiles[position].getAbsolutePath();
                Log.d("theme","onItemClick position " + position + " themeName " + themeName);
                launcherDetailThemePreview(themeName);
            }
        });
    }

    private void launcherDetailThemePreview(String themepath) {
        Intent intent = new Intent("sprocomm.intent.action.THEME_DETAIL");
        intent.putExtra("themeName", themepath);
        intent.setPackage("com.android.launcher3");
        startActivity(intent);
    }

    public Bitmap getPreviewFromTheme(String themePath) {
        if (TextUtils.isEmpty(themePath)) {
            return null;
        }
        InputStream is = null;
        Bitmap bm = null;
        try {
            is = getInputStream("preview" + File.separator + "min_preview" + ".jpg", new ZipFile(themePath));
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

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(ThemeSettingsActivity.this).inflate(R.layout.item_themesettings, null);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.grid_img);
                holder.textView = (TextView) convertView.findViewById(R.id.grid_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.imageView.setImageBitmap((Bitmap)list.get(position).get("image"));
            holder.textView.setText((String) list.get(position).get("text"));
            return convertView;
        }
    }
 
    class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}

package com.skd.nubit.adapters;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.health.connect.datatypes.AppInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.skd.nubit.R;
import com.skd.nubit.utilityclasses.AppInfoNew;

import java.util.List;
import java.util.Map;

public class AppGridAdapter extends RecyclerView.Adapter<AppGridAdapter.ViewHolder> {

    private final Context context;
    private final List<AppInfoNew> sortedApps;
    private final PackageManager packageManager;

    public AppGridAdapter(Context context, List<AppInfoNew> sortedApps, PackageManager packageManager) {
        this.context = context;
        this.sortedApps = sortedApps;
        this.packageManager = packageManager;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app_grid, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = 34)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("checkfreqapps",">adapter> " + position);

//        Map.Entry<AppInfo> appEntry = sortedApps.get(position);
//        long usageCount = sortedApps.get(position).getValue();
        String appName = sortedApps.get(position).getAppName().toString();
        String packageName = sortedApps.get(position).getPackageName().toString();
        holder.appNameTextView.setText(appName);

        Drawable appIcon = sortedApps.get(position).getAppIcon();
        if (appIcon != null) {
            holder.topapps_icon.setImageDrawable(appIcon);
        } else {
            holder.topapps_icon.setImageResource(R.drawable.placeholder_apps);
        }

        holder.itemView.setOnClickListener(v -> openApp(packageName));
    }

    private void openApp(String packageName) {
        Intent launchIntent = packageManager.getLaunchIntentForPackage(packageName);
        if (launchIntent != null) {
            context.startActivity(launchIntent);
        } else {
            // App not found, handle accordingly (e.g., show a toast)
        }
    }

    @Override
    public int getItemCount() {
        Log.e("checkfreqapps",">adapter size> " + sortedApps.size());
        if (sortedApps.size()>6){
            return 6;
        }else{
            return sortedApps.size();
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView appNameTextView;
        ImageView topapps_icon;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            appNameTextView = itemView.findViewById(R.id.service_grid_text);
            topapps_icon = itemView.findViewById(R.id.service_grid_image);
        }
    }

    private Drawable getAppIcon(String packageName) {
        try {
            return packageManager.getApplicationIcon(packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getAppName(String packageName) {
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName, 0);
            return (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return packageName; // Return package name as a fallback
        }
    }
}

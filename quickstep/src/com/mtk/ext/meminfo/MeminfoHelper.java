package com.mtk.ext.meminfo;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.text.format.Formatter;
import android.widget.Toast;

import androidx.annotation.VisibleForTesting;

import com.android.launcher3.R;
import com.android.launcher3.util.MainThreadInitializedObject;
import com.mtk.ext.SystemPropertiesUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.util.Log;
import android.text.TextUtils;

public class MeminfoHelper {
    private static final String TAG = "Meminfo";
    private static final long INVALID_MEM_SIZE = -1L;
    private static final long PROCESS_REMOVETASKS_DELAY_MS = 200L;
    private static final String CONFIG_RAM_SIZE = "ro.deviceinfo.ram";
    private static final String RAM_SIZE = "ro.boot.ddrsize";
    private static final String DEFAULT_CONFIG = "unconfig";

    private static final MainThreadInitializedObject<MeminfoHelper> INSTANCE =
            new MainThreadInitializedObject<>(MeminfoHelper::new);

    private long mTotalMem = INVALID_MEM_SIZE;
    private long mAvailMem = INVALID_MEM_SIZE;

    private static final int SI_KUNIT = 1000;
    private static final int KUINT = 1024;

    private MeminfoHelper(Context context) {
    }

    public static MeminfoHelper getInstance(final Context context) {
        return INSTANCE.get(context.getApplicationContext());
    }

    public void showReleaseMemoryToast(Context context, boolean isRemoveView) {
        long availSizeOriginal = mAvailMem != INVALID_MEM_SIZE
                ? mAvailMem : getSystemAvailMemory(context);
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            mAvailMem = getSystemAvailMemory(context);
            long releaseMem = isRemoveView ? mAvailMem - availSizeOriginal : 0;

            if (releaseMem <= 0) {
                String release = Formatter.formatShortFileSize(context, Math.abs(releaseMem));
                String avail = getTotalMem(context);
                String vram = getVramStorage(context);

                Toast.makeText(context,releaseMem == 0?
                        context.getString((R.string.recents_nothing_to_clear)):
                        context.getString(R.string.recents_clean_finished_toast,
                                release.toUpperCase(), avail.toUpperCase()), Toast.LENGTH_SHORT)
                        .show();
            } else {
                String release = Formatter.formatShortFileSize(context, releaseMem);
                String avail = getTotalMem(context);
                String vram = getVramStorage(context);
                Toast.makeText(context,
                        context.getString(R.string.recents_clean_finished_toast,
                                release.toUpperCase(),  avail.toUpperCase()), Toast.LENGTH_SHORT)
                        .show();
            }
        }, PROCESS_REMOVETASKS_DELAY_MS);
    }

    void updateTotalMemory() {
        mTotalMem = getSystemTotalMemory();
    }

    void updateAvailMemory(Context context) {
        mAvailMem = getSystemAvailMemory(context);
    }

    String getTotalMemString(Context context) {
        if (mTotalMem == INVALID_MEM_SIZE) {
            updateTotalMemory();
        }
        return Formatter.formatShortFileSize(context, mTotalMem);
    }

    String getAvailMemString(Context context) {
        if (mAvailMem == INVALID_MEM_SIZE) {
            updateAvailMemory(context);
        }
        return Formatter.formatShortFileSize(context, mAvailMem);
    }

    private String getVramStorage(Context context) {
        Log.d("whn", "getVramStorage persist.sys.ddrenable: " + SystemProperties.getBoolean("persist.sys.ddrenable", false));
        if(!SystemProperties.getBoolean("persist.sys.ddrenable", false)) {
            return "";
        }
        String vram = SystemProperties.get("persist.sys.ddrindex", "4");
        Log.d("whn", "getVramStorage persist.sys.ddrindex: " + vram);
        if ("0".equals(vram)){
            return "";
        } else {
            String totalMemory = Formatter.formatShortFileSize(context, (long) 1000 * 1000 * 1000 * Integer.parseInt(vram));
            Log.d("whn", "getVramStorage totalMemory: " + totalMemory);
            String addMemory = " + ";
            return addMemory + totalMemory;
        }
    }

    private long getSystemAvailMemory(Context context) {
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        if (activityManager != null) {
            activityManager.getMemoryInfo(memoryInfo);
        }
        String vram = SystemProperties.get("persist.sys.ddrindex", "4");
        Log.d("whn", "getVramStorage persist.sys.ddrindex: " + vram);
        android.util.Log.d(TAG, "getSystemAvailMemory:  " + memoryInfo.availMem);
        return memoryInfo.availMem + (long) 1000 * 1000 * 1000 * Integer.parseInt(vram);
    }

    public String getTotalRAM(Context context) {
        String vram = getVramStorage(context);
        return getTotalMem(context) + vram;
    }

    public String getTotalMem(Context context) {
        long size = 0;
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(context.ACTIVITY_SERVICE);
        MemoryInfo outInfo = new MemoryInfo();
        activityManager.getMemoryInfo(outInfo);
        size = outInfo.totalMem;
        double si_size = (double) size/(SI_KUNIT*SI_KUNIT*SI_KUNIT);
        // return Math.round(si_size)+" GB";
        int ddrindex = 0;
        String vram = SystemProperties.get("persist.sys.ddrindex", "4");
        try {
            ddrindex =Integer.parseInt(vram);
        } catch(NumberFormatException e){
            android.util.Log.d(TAG, "ddrindex: " + ddrindex);
        }
        String totalMemory = 
            Formatter.formatShortFileSize(context, (long) 1000 * 1000 * 1000 * ((int)Math.round(si_size) + ddrindex));
        return totalMemory;
    }

    long getSystemTotalMemory() {
        String ramConfig = getConfig(CONFIG_RAM_SIZE);
        if (!DEFAULT_CONFIG.equals(ramConfig)) {
            long configTotalRam = Long.parseLong(ramConfig);
            return configTotalRam;
        }

        ramConfig = getConfig(RAM_SIZE);
        if (DEFAULT_CONFIG.equals(ramConfig)) {
            android.util.Log.d(TAG, "error, property value is:" + ramConfig);
            return INVALID_MEM_SIZE;
        }
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(ramConfig);
        ramConfig = m.replaceAll("").trim();
        long ramSize = Long.parseLong(ramConfig);
        return covertUnitsToSI(ramSize);
    }

    /**
     * SI_KUNIT = 1000bytes; KUINT = 1024bytes
     * 512MB = 512 * 1000 * 1000
     * 2048MB = 2048/1024 * 1000 * 1000 * 1000
     * 2000MB = 2000 * 1000 * 1000
     */
    long covertUnitsToSI(long size) {
        if (size > SI_KUNIT && size % KUINT == 0) {
            return size / KUINT * SI_KUNIT * SI_KUNIT * SI_KUNIT;
        }
        return size * SI_KUNIT * SI_KUNIT;
    }

    String getConfig(String key) {
        return SystemPropertiesUtils.get(key, DEFAULT_CONFIG);
    }

    @VisibleForTesting
    public void setAvailMemSize(long size) {
        mAvailMem = size;
    }

    @VisibleForTesting
    public void setTotalMemSize(long size) {
        mTotalMem = size;
    }
}

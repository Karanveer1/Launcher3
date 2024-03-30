/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mtk.ext.managedprofile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Process;
import android.os.UserHandle;

import static com.android.launcher3.util.Executors.MAIN_EXECUTOR;
import com.android.launcher3.LauncherFiles;
import com.android.launcher3.LauncherModel;
import com.android.launcher3.pm.UserCache;
import com.android.launcher3.R;
import com.android.launcher3.Utilities;
import com.android.launcher3.model.BgDataModel;
import com.android.launcher3.model.ModelWriter;
import com.android.launcher3.model.data.FolderInfo;
import com.android.launcher3.model.data.ItemInfo;
import com.android.launcher3.model.data.WorkspaceItemInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import android.util.Log;

/**
 * Handles addition of app shortcuts for managed profiles.
 */
public class ManagedProfileController {

    private static final String USER_FOLDER_ID_PREFIX = "user_folder_";

    /**
     * Utility class to help workspace icon addition.
     */
    public static class UserFolderInfo {

        final ArrayList<WorkspaceItemInfo> mPendingItems = new ArrayList<>();

        final UserHandle mUser;

        final long mUserSerial;

        final String mFolderIdKey;
        final SharedPreferences mPrefs;

        final boolean mFolderAlreadyCreated;
        final FolderInfo mFolderInfo;

        boolean mFolderPendingAddition;
        private final UserCache mUserManager;

        public UserFolderInfo(Context context, UserHandle user, BgDataModel dataModel) {
            this.mUser = user;

            mUserManager = UserCache.INSTANCE.get(context);
            mUserSerial = mUserManager.getSerialNumberForUser(user);

            mFolderIdKey = USER_FOLDER_ID_PREFIX + mUserSerial;
            mPrefs = prefs(context);

            mFolderAlreadyCreated = mPrefs.contains(mFolderIdKey);

            if (dataModel != null) {
                if (mFolderAlreadyCreated) {
                    int folderId = mPrefs.getInt(mFolderIdKey, ItemInfo.NO_ID);
                    mFolderInfo = dataModel.folders.get(folderId);
                } else {
                    mFolderInfo = new FolderInfo();
                    mFolderInfo.title = context.getResources().getString(R.string.work_folder_name);
                    mFolderInfo.setOption(FolderInfo.FLAG_WORK_FOLDER, true, null);
                    mFolderPendingAddition = true;
                }
            } else {
                mFolderInfo = null;
            }
        }

        /**
         * Returns the ItemInfo which should be added to the workspace.
         */
        public ItemInfo convertToWorkspaceItem(WorkspaceItemInfo item) {
            if (mFolderAlreadyCreated) {
                if (mFolderInfo == null) {
                    // Work folder was deleted by user, add icon to home screen.
                    return item;
                } else {
                    // Add item to work folder instead. Nothing needs to be added
                    // on the homescreen.
                    mPendingItems.add(item);
                    return null;
                }
            }

            mPendingItems.add(item);
            mFolderInfo.add(item, false);
            if (mFolderPendingAddition) {
                mFolderPendingAddition = false;
                return mFolderInfo;
            } else {
                // WorkFolder already requested to be added. Nothing new needs to be added.
                return null;
            }
        }

        public void applyPendingState(ModelWriter writer) {
            if (mFolderInfo == null) {
                return;
            }

            int startingRank = 0;
            if (mFolderAlreadyCreated) {
                startingRank = mFolderInfo.contents.size();
            }

            for (WorkspaceItemInfo info : mPendingItems) {
                info.rank = startingRank++;
                String targetPackage = info.getTargetPackage();
                writer.addItemToDatabase(info, mFolderInfo.id, 0, 0, 0);
            }

            if (mFolderAlreadyCreated) {
                // FolderInfo could already be bound. We need to add items on the UI thread.
                MAIN_EXECUTOR.execute(new Runnable() {
                    @Override
                    public void run() {
                        for (WorkspaceItemInfo info : mPendingItems) {
                            mFolderInfo.add(info, false);
                        }
                    }
                });
            } else {
                mPrefs.edit().putInt(mFolderIdKey, mFolderInfo.id).apply();
            }
        }
    }

    /**
     * Verifies that entries corresponding to {@param users} exist and removes all invalid entries.
     */
    public static void processAllUsers(List<UserHandle> users, Context context) {
        UserCache userCache = UserCache.INSTANCE.get(context);
        HashSet<String> validKeys = new HashSet<>();
        for (UserHandle user : users) {
            validKeys.add(USER_FOLDER_ID_PREFIX + userCache.getSerialNumberForUser(user));
        }

        SharedPreferences mPrefs = prefs(context);
        SharedPreferences.Editor editor = mPrefs.edit();
        for (String key : mPrefs.getAll().keySet()) {
            if (!validKeys.contains(key)) {
                editor.remove(key);
            }
        }
        editor.apply();
    }

    public static SharedPreferences prefs(Context context) {
        return context.getSharedPreferences(
                LauncherFiles.MANAGED_USER_PREFERENCES_KEY, Context.MODE_PRIVATE);
    }
}
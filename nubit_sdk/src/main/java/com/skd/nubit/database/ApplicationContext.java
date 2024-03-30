package com.skd.nubit.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Robi on 19/09/18.
 */
public class ApplicationContext {

    private static DbHandler mDbHandler;


    public static void setDbHandler(DbHandler mDbHandler) {
        ApplicationContext.mDbHandler = mDbHandler;
    }

    public static SQLiteDatabase getDBObject(int isWrtitable) {
        return mDbHandler.openDb(isWrtitable);
    }
}

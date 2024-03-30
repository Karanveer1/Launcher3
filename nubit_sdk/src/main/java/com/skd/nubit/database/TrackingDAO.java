package com.skd.nubit.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skd.nubit.models.TrackingPojo;

import java.util.ArrayList;



/**
 * Created by robi on 19/09/18.
 */

public class TrackingDAO {

    private static TrackingDAO mTrackingDAO = new TrackingDAO();
    private TrackingPojo mTrackingPojo = new TrackingPojo();

    private TrackingDAO() {

    }

    public static TrackingDAO getInstance() {
        return mTrackingDAO;
    }


    public void insertTrackRecords(SQLiteDatabase db, TrackingPojo mPojo) {


        try {
            int count = 0;
            mTrackingPojo = new TrackingPojo();
            mTrackingPojo = mPojo;

            ContentValues values = new ContentValues();


            values.put(DbHandler.URL, mTrackingPojo.getUrl());
            values.put(DbHandler.REDIRECT_URL, mTrackingPojo.getRedirectURL());
            values.put(DbHandler.CATEGORY, mTrackingPojo.getCategory());
            values.put(DbHandler.DATE, mTrackingPojo.getDate());
            values.put(DbHandler.LAUNCH, mTrackingPojo.getLaunch());
            values.put(DbHandler.TITLE, mTrackingPojo.getTitle());
            values.put(DbHandler.PLAY_DURATION,mTrackingPojo.getPlay_duration());

            // insert row
            db.insert(DbHandler.TRACKING_TABLE, null, values);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

    }

    public ArrayList<TrackingPojo> getTracksFromDB(SQLiteDatabase sqlDb) {


        ArrayList<TrackingPojo> mList = new ArrayList<>();
        Cursor cursor = null;
        TrackingPojo trackingPojo;
        int count = 0;
        String selectQuery = "SELECT  * FROM " +
                DbHandler.TRACKING_TABLE;

        try {
            cursor = sqlDb.rawQuery(selectQuery, null);
            count = cursor.getCount();
            if (cursor.moveToFirst()) {
                do {
                    trackingPojo = new TrackingPojo();
                    trackingPojo.setRowID(cursor.getString(0));
                    trackingPojo.setUrl(cursor.getString(1));
                    trackingPojo.setRedirectURL(cursor.getString(2));
                    trackingPojo.setCategory(cursor.getString(3));
                    trackingPojo.setDate(cursor.getString(4));
                    trackingPojo.setLaunch(cursor.getString(5));
                    trackingPojo.setTitle(cursor.getString(6));
                    trackingPojo.setPlay_duration(cursor.getString(7));
                    mList.add(trackingPojo);

                } while (cursor.moveToNext());
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            sqlDb.close();
        }


        return mList;
    }

    public void deleteAllData(SQLiteDatabase db) {
        try {
            db.delete(DbHandler.TRACKING_TABLE, null,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
}

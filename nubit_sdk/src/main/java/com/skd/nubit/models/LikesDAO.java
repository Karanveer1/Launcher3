package com.skd.nubit.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;


/**
 * Created by robi kumar tomar on 02/10/18.
 */

public class LikesDAO {

    private static LikesDAO mLikesDAO = new LikesDAO();
    private Like_DLike_Pojo mLike_DLike_Pojo = new Like_DLike_Pojo();

    public static String newsLanguage="newsLanguage";
    public static String eventType="eventType";
    public static String redirectURL="redirectURL";
    public static String pubName="pubName";
    public static String eventDate="eventDate";
    public static String ROW_ID="row_id";
    public static String newsTitle="newsTitle";
    public static final String TABLE_NAME = "news_likes_table";

    private LikesDAO() {

    }

    public static LikesDAO getInstance() {
        return mLikesDAO;
    }


    public void insertTrackRecords(SQLiteDatabase db, Like_DLike_Pojo mPojo) {


        try {
            int count = 0;
            mLike_DLike_Pojo = new Like_DLike_Pojo();
            mLike_DLike_Pojo = mPojo;

            ContentValues values = new ContentValues();

            values.put(newsLanguage, mLike_DLike_Pojo.getNewsLanguage());
            values.put(eventType, mLike_DLike_Pojo.getEventType());
            values.put(redirectURL, mLike_DLike_Pojo.getRedirectURL());
            values.put(pubName, mLike_DLike_Pojo.getPubName());
            values.put(eventDate, mLike_DLike_Pojo.getEventDate());
            values.put(newsTitle, mLike_DLike_Pojo.getNewsTitle());


            // insert row
            db.insert(TABLE_NAME, null, values);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

    }

    public ArrayList<Like_DLike_Pojo> getTracksFromDB(SQLiteDatabase sqlDb) {


        ArrayList<Like_DLike_Pojo> mList = new ArrayList<>();
        Cursor cursor = null;
        Like_DLike_Pojo Like_DLike_Pojo;
        int count = 0;
        String selectQuery = "SELECT  * FROM " +
                TABLE_NAME;

        try {
            cursor = sqlDb.rawQuery(selectQuery, null);
            count = cursor.getCount();
            if (cursor.moveToFirst()) {
                do {

                    Like_DLike_Pojo = new Like_DLike_Pojo();
                    Like_DLike_Pojo.setRowID(cursor.getString(0));
                    Like_DLike_Pojo.setNewsLanguage(cursor.getString(1));
                    Like_DLike_Pojo.setEventType(cursor.getString(2));
                    Like_DLike_Pojo.setRedirectURL(cursor.getString(3));
                    Like_DLike_Pojo.setPubName(cursor.getString(4));
                    Like_DLike_Pojo.setEventDate(cursor.getString(5));
                    Like_DLike_Pojo.setNewsTitle(cursor.getString(6));
                    mList.add(Like_DLike_Pojo);

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
            db.delete(TABLE_NAME, null,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
}

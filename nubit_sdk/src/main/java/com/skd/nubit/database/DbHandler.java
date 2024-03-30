package com.skd.nubit.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.skd.nubit.models.LikesDAO;


/**
 * Created by robi on 04/10/18.
 */
public class DbHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "lava_sdk.db";
    private static final int DATABASE_VERSION = 1;


    public static final String TRACKING_TABLE = "tracking_table";

    public static final String ROW_ID = "row_id";

    public static final String ID = "id";
    public static final String DEVICE_ID = "device_id";
    public static final String DEVICE_MODEL = "device_model";
    public static final String URL = "url";
    public static final String REDIRECT_URL = "redirect_url";
    public static final String CATEGORY = "category";
    public static final String DATE = "date";
    public static final String COUNT = "count";
    public static final String LAUNCH = "launch";
    public static final String TITLE = "title";
    public static final String PLAY_DURATION = "play_duration";


    private static final String CREATE_TRACKING_TABLE = "CREATE TABLE " + TRACKING_TABLE
            + "("
            + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + URL + " TEXT, "
            + REDIRECT_URL + " TEXT, "
            + CATEGORY + " TEXT, "
            + DATE + " TEXT, "
            + LAUNCH + " TEXT, "
            + TITLE + " TEXT, "
            + PLAY_DURATION + " TEXT)";

    private static final String CREATE_NEWS_LIKES_TABLE = "CREATE TABLE IF NOT EXISTS "
            + LikesDAO.TABLE_NAME + " ("
            + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + LikesDAO.newsLanguage + " TEXT, "
            + LikesDAO.eventType + " TEXT,"
            + LikesDAO.redirectURL + " TEXT UNIQUE,"
            + LikesDAO.pubName + " TEXT,"
            + LikesDAO.eventDate + " TEXT,"
            + LikesDAO.newsTitle + " TEXT)";


    private static Context mContext;
    private static DbHandler mDbHandler = null;

    public static DbHandler getInstance(Context context) {

        if (mDbHandler == null) {
            mDbHandler = new DbHandler(context);
        }

        ApplicationContext.setDbHandler(mDbHandler);
        return mDbHandler;
    }

    public SQLiteDatabase openDb(int op) {
        return (op == 1) ? this.getWritableDatabase() : this.getReadableDatabase();
    }


    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TRACKING_TABLE);
        sqLiteDatabase.execSQL(CREATE_NEWS_LIKES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
//        if (i < i1) {
//            sqLiteDatabase.execSQL(CREATE_TRACKING_TABLE);
//            sqLiteDatabase.execSQL(CREATE_APP_BUCKET_TABLE);
//        }
    }
}

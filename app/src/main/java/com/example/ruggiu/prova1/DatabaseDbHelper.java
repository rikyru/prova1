package com.example.ruggiu.prova1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Database.db";

    public DatabaseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USERS);
        db.execSQL(SQL_CREATE_TABLE_MEASURES);
        db.execSQL(SQL_CREATE_TABLE_MEASURE_TYPES);//se creo piu tabelle devo passardli le funzioni qua?
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_TABLE_USERS);
        db.execSQL(SQL_DELETE_TABLE_MEASURES);
        db.execSQL(SQL_DELETE_TABLE_MEASURE_TYPES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    private static final String SQL_CREATE_TABLE_USERS = //Riprendi da qua
            "CREATE TABLE " + DatabaseContract.FeedEntry.TABLE_NAME_USERS + " (" +
                    DatabaseContract.FeedEntry.COLUMN_NAME_ID_USER + " INTEGER PRIMARY KEY," +
                    DatabaseContract.FeedEntry.COLUMN_NAME_USERNAME + " TEXT," +
                    DatabaseContract.FeedEntry.COLUMN_NAME_PASSWORD + " TEXT," +
                    DatabaseContract.FeedEntry.COLUMN_NAME_DATE_OF_BIRTH + " TEXT," +
                    DatabaseContract.FeedEntry.COLUMN_NAME_DR_EMAIL +  " TEXT" + ")";

    private static final String SQL_CREATE_TABLE_MEASURES = //Riprendi da qua
            "CREATE TABLE " + DatabaseContract.FeedEntry.TABLE_NAME_MEASURES + " (" +
                    DatabaseContract.FeedEntry.COLUMN_NAME_ID_MEASURE + " INTEGER PRIMARY KEY," +
                    DatabaseContract.FeedEntry.COLUMN_NAME_ID_USER + " TEXT," +
                    DatabaseContract.FeedEntry.COLUMN_NAME_TMSTMP + " TEXT," +
                    DatabaseContract.FeedEntry.COLUMN_NAME_ID_TYPE_MEASURE + " TEXT," +
                    DatabaseContract.FeedEntry.COLUMN_NAME_VALUE +  " TEXT" + ")";

    private static final String SQL_CREATE_TABLE_MEASURE_TYPES = //Riprendi da qua
            "CREATE TABLE " + DatabaseContract.FeedEntry.TABLE_NAME_MEASURE_TYPES + " (" +
                    DatabaseContract.FeedEntry.COLUMN_NAME_ID_TYPE_MEASURE + " INTEGER PRIMARY KEY," +
                    DatabaseContract.FeedEntry.COLUMN_NAME_MEASURE_NAME +  " TEXT" + ")";

    private static final String SQL_DELETE_TABLE_USERS =
            "DROP TABLE IF EXISTS " + DatabaseContract.FeedEntry.TABLE_NAME_USERS;
    private static final String SQL_DELETE_TABLE_MEASURES =
            "DROP TABLE IF EXISTS " + DatabaseContract.FeedEntry.TABLE_NAME_MEASURES;
    private static final String SQL_DELETE_TABLE_MEASURE_TYPES =
            "DROP TABLE IF EXISTS " + DatabaseContract.FeedEntry.TABLE_NAME_MEASURE_TYPES;
}

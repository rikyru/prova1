package com.example.ruggiu.prova1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PressureReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "PressureReader.db";

    public PressureReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PressureReaderContract.FeedEntry.TABLE_NAME + " (" +
                    PressureReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    PressureReaderContract.FeedEntry.COLUMN_NAME_SYS + " TEXT," +
                    PressureReaderContract.FeedEntry.COLUMN_NAME_DIA + " TEXT," +
                    PressureReaderContract.FeedEntry.COLUMN_NAME_TIME +  " TEXT" + ")"; //occhio con gli spazi dopo il +

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PressureReaderContract.FeedEntry.TABLE_NAME;
}

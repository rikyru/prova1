package com.example.groupi.heartattapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.arch.persistence.room.RoomMasterTable.TABLE_NAME;
import static android.content.Context.MODE_PRIVATE;

public class DatabaseDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Database.db";
    public static final String user_table = DatabaseContract.FeedEntry.TABLE_NAME_USERS;


    public DatabaseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USERS);
        db.execSQL(SQL_CREATE_TABLE_MEASURES);
        db.execSQL(SQL_CREATE_TABLE_MEASURE_TYPES);//se creo piu tabelle devo passardli le funzioni qua?
    }
     @Override
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
                    DatabaseContract.FeedEntry.COLUMN_NAME_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DatabaseContract.FeedEntry.COLUMN_NAME_USERNAME + " TEXT," +
                    DatabaseContract.FeedEntry.COLUMN_NAME_PASSWORD + " TEXT," +
                    DatabaseContract.FeedEntry.COLUMN_NAME_NAME + " TEXT," +
                    DatabaseContract.FeedEntry.COLUMN_NAME_SURNAME + " TEXT," +
                    DatabaseContract.FeedEntry.COLUMN_NAME_DATE_OF_BIRTH + " TEXT," +
                    DatabaseContract.FeedEntry.COLUMN_NAME_DR_EMAIL +  " TEXT" + ")";

    private static final String SQL_CREATE_TABLE_MEASURES = //Riprendi da qua
            "CREATE TABLE " + DatabaseContract.FeedEntry.TABLE_NAME_MEASURES + " (" +
                    DatabaseContract.FeedEntry.COLUMN_NAME_ID_MEASURE + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DatabaseContract.FeedEntry.COLUMN_NAME_ID_USER + " TEXT," +
                    DatabaseContract.FeedEntry.COLUMN_NAME_TMSTMP + " TEXT," +
                    DatabaseContract.FeedEntry.COLUMN_NAME_ID_TYPE_MEASURE + " TEXT," +
                    DatabaseContract.FeedEntry.COLUMN_NAME_VALUE +  " TEXT" + ")";

    private static final String SQL_CREATE_TABLE_MEASURE_TYPES = //Riprendi da qua
            "CREATE TABLE " + DatabaseContract.FeedEntry.TABLE_NAME_MEASURE_TYPES + " (" +
                    DatabaseContract.FeedEntry.COLUMN_NAME_ID_TYPE_MEASURE + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    DatabaseContract.FeedEntry.COLUMN_NAME_MEASURE_NAME +  " TEXT" + ")";

    private static final String SQL_DELETE_TABLE_USERS =
            "DROP TABLE IF EXISTS " + DatabaseContract.FeedEntry.TABLE_NAME_USERS;
    private static final String SQL_DELETE_TABLE_MEASURES =
            "DROP TABLE IF EXISTS " + DatabaseContract.FeedEntry.TABLE_NAME_MEASURES;
    private static final String SQL_DELETE_TABLE_MEASURE_TYPES =
            "DROP TABLE IF EXISTS " + DatabaseContract.FeedEntry.TABLE_NAME_MEASURE_TYPES;

    public boolean insertUser(String name, String surname, String DOB, String username, String password, String physician, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.FeedEntry.COLUMN_NAME_NAME, name);
        contentValues.put(DatabaseContract.FeedEntry.COLUMN_NAME_SURNAME, surname);
        contentValues.put(DatabaseContract.FeedEntry.COLUMN_NAME_DATE_OF_BIRTH, DOB);
        contentValues.put(DatabaseContract.FeedEntry.COLUMN_NAME_USERNAME, username);
        contentValues.put(DatabaseContract.FeedEntry.COLUMN_NAME_PASSWORD, password);
        contentValues.put(DatabaseContract.FeedEntry.COLUMN_NAME_DR_EMAIL, physician);

        long result = db.insert(DatabaseContract.FeedEntry.TABLE_NAME_USERS, null, contentValues);
        db.close();

        if (result == -1) {
            return false;
        } else {
            String PREFERENCE_FILENAME = "UserLogged"; //per tenere traccia di chi è loggato
            SharedPreferences User = context.getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); //i dont know what im doing
            SharedPreferences.Editor prefEditor = User.edit();
            prefEditor.putString("user_id", String.valueOf(result)); //dobbiamo fare la query
            prefEditor.apply();

            return true;
        }
    }

    //To read all users
    public Cursor getAllUsers() { //per Debug
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DatabaseContract.FeedEntry.TABLE_NAME_USERS;
        Cursor res = db.rawQuery(query, null);
        db.close();
        return res;
    }


    public boolean updateUser(String id, String name, String surname, String DOB, String username, String password, String physician) { //per il profile update
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.FeedEntry.COLUMN_NAME_ID_USER, id);
        contentValues.put(DatabaseContract.FeedEntry.COLUMN_NAME_NAME, name);
        contentValues.put(DatabaseContract.FeedEntry.COLUMN_NAME_SURNAME, surname);
        contentValues.put(DatabaseContract.FeedEntry.COLUMN_NAME_DATE_OF_BIRTH, DOB);
        contentValues.put(DatabaseContract.FeedEntry.COLUMN_NAME_USERNAME, username);
        contentValues.put(DatabaseContract.FeedEntry.COLUMN_NAME_PASSWORD, password);
        contentValues.put(DatabaseContract.FeedEntry.COLUMN_NAME_DR_EMAIL, physician);

        int result = db.update(DatabaseContract.FeedEntry.TABLE_NAME_USERS, contentValues, "id = ?", new String[]{id});
        db.close();

        if (result == -1)
            return false;
        else
            return true;

    }


    public Integer deleteUser(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int res = db.delete(DatabaseContract.FeedEntry.TABLE_NAME_USERS, DatabaseContract.FeedEntry.COLUMN_NAME_ID_USER + " = ?", new String[]{id});
        return res;
    }


    //Check if email exists
    public boolean searchUser(String username) {

        SQLiteDatabase db = this.getReadableDatabase();
        //String query = "Select * from user_table where username=?", new String[]{username});
        Cursor uCursor = db.rawQuery("Select * from users  where username=?", new String[]{username}); //potrebbe non funzionare //TODO: vedere se si ouo migliorare la query
        db.close();

        if (uCursor.getCount() > 0)
            return true;
        else
            return false;

    }


    //Check credentials
    public boolean emailPassword(String checkUsername, String checkPassword, Context context) {


        SQLiteDatabase db = this.getReadableDatabase();
        //String query = "SELECT USERNAME, PASSWORD FROM " + TABLE_NAME;
        Cursor pCursor = db.rawQuery("select * from users where username=? and password=?", new String[]{checkUsername, checkPassword});


        if (pCursor.getCount() > 0) {
            String PREFERENCE_FILENAME = "UserLogged"; //per tenere traccia di chi è loggato
            SharedPreferences User = context.getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE); //i dont know what im doing
            SharedPreferences.Editor prefEditor = User.edit();
            while(pCursor.moveToNext()) {
                prefEditor.putString("user_id", pCursor.getString(0)); //dobbiamo fare la query
            }
            prefEditor.apply();

            db.close();

            return true;
        }
        else {
            db.close();
            return false;
        }

    }

    public BloodPressureMeasurement getLastMeasureBP(Context context) {
        SharedPreferences user = context.getSharedPreferences("UserLogged", MODE_PRIVATE);
        SQLiteDatabase db = this.getReadableDatabase();
        String systolic = "";
        String diastolic = "";
        String formattedDate = "";
                String[] projection = {
                DatabaseContract.FeedEntry.COLUMN_NAME_ID_MEASURE,
                DatabaseContract.FeedEntry.COLUMN_NAME_VALUE,
                DatabaseContract.FeedEntry.COLUMN_NAME_ID_USER,
                DatabaseContract.FeedEntry.COLUMN_NAME_TMSTMP
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseContract.FeedEntry.COLUMN_NAME_ID_USER + " = ?" + " AND " + DatabaseContract.FeedEntry.COLUMN_NAME_ID_TYPE_MEASURE + " = 2";
        //String query = "Select * from user_table where username=?", new String[]{username});
        String[] selectionArgs = { user.getString("user_id","") }; //query per user_id da shared preferences

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                DatabaseContract.FeedEntry.COLUMN_NAME_TMSTMP + " DESC";

        Cursor cursor = db.query(
                DatabaseContract.FeedEntry.TABLE_NAME_MEASURES,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection, //selection,              // The columns for the WHERE clause
                selectionArgs, //selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        if (cursor.moveToFirst()) {
            String measure = cursor.getString(1); //la stringa è un JSON
            Long timestamp = cursor.getLong(3); //timestamp come LONG
            JSONObject reader = null;
            try {
                reader = new JSONObject(measure);
                systolic = reader.getString("systolic");
                diastolic = reader.getString("diastolic");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Date date = new java.util.Date(timestamp * 1000L); //conversione in data leggibile
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEE, dd/MM/yyyy HH:mm:ss");
            sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+2"));
            formattedDate = sdf.format(date);
        }
        cursor.close();

        BloodPressureMeasurement blood = new BloodPressureMeasurement(); //TODO: farlo con un costruttore
        blood.systolic=systolic;
        blood.diastolic=diastolic;
        blood.date=formattedDate;
        db.close();

        return blood;


    }

    public User getUserInfo(Context context) {
        SharedPreferences user = context.getSharedPreferences("UserLogged", MODE_PRIVATE);
        SQLiteDatabase db = this.getReadableDatabase();
        String name = "";
        String username = "";
        String surname = "";
        String password = "";
        String DOB = "";
        String dr_email = "";

        String selection = DatabaseContract.FeedEntry.COLUMN_NAME_ID_USER + " = ?";
        String[] selectionArgs = { user.getString("user_id","") }; //query per user_id da shared preferences

        // How you want the results sorted in the resulting Cursor

        Cursor cursor = db.query(
                DatabaseContract.FeedEntry.TABLE_NAME_USERS,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection, //selection,              // The columns for the WHERE clause
                selectionArgs, //selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null          // The sort order
        );
        while (cursor.moveToNext()) {
            username = cursor.getString(1);
            password = cursor.getString(2);
            name = cursor.getString(3);
            surname = cursor.getString(4);
            DOB = cursor.getString(5); //TODO: implementare stringa data
            dr_email = cursor.getString(6);

        }
        cursor.close();

        User record = new User(); //TODO: farlo con un costruttore
        record.name=name;
        record.surname=surname;
        record.username=username;
        record.DOB=DOB;
        record.password=password;
        record.dr_email=dr_email;

        db.close();

        return record;


    }

    public boolean insert_BP(BloodPressureMeasurement bloodPressureMeasurement, Context context){

        SQLiteDatabase db = this.getWritableDatabase();
        String systolic=bloodPressureMeasurement.systolic;
        String diastolic=bloodPressureMeasurement.diastolic;
        String measure="{systolic:" + systolic + ", diastolic:" + diastolic + "}";
        SharedPreferences user = context.getSharedPreferences("UserLogged", MODE_PRIVATE);
        String user_id=user.getString("user_id","");
        String measure_type_id="2"; //TODO: mappare i tipi di misure (2 è pressione, 1 HR, 3 Sveglie)

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.FeedEntry.COLUMN_NAME_VALUE, measure); //devo importare la stringa
        String unixtimestamp = String.valueOf(System.currentTimeMillis() / 1000L); //TODO: passare la stringa quando la chiamo
        values.put(DatabaseContract.FeedEntry.COLUMN_NAME_TMSTMP, unixtimestamp );
        values.put(DatabaseContract.FeedEntry.COLUMN_NAME_ID_TYPE_MEASURE, measure_type_id);
        values.put(DatabaseContract.FeedEntry.COLUMN_NAME_ID_USER, user_id );

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DatabaseContract.FeedEntry.TABLE_NAME_MEASURES, null, values);

        db.close();

        if (newRowId == -1)
            return false;
        else
            return true;

    }

    public long insert_Alarm(Long alarm, Context context){

        SQLiteDatabase db = this.getWritableDatabase();
        SharedPreferences user = context.getSharedPreferences("UserLogged", MODE_PRIVATE);
        String user_id=user.getString("user_id","");
        String measure_type_id="3"; //TODO: mappare i tipi di misure (2 è pressione, 1 HR, 3 Sveglie)

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.FeedEntry.COLUMN_NAME_VALUE, alarm); //devo importare la stringa
        String unixtimestamp = String.valueOf(System.currentTimeMillis() / 1000L); //TODO: passare la stringa quando la chiamo
        values.put(DatabaseContract.FeedEntry.COLUMN_NAME_TMSTMP, unixtimestamp );
        values.put(DatabaseContract.FeedEntry.COLUMN_NAME_ID_TYPE_MEASURE, measure_type_id);
        values.put(DatabaseContract.FeedEntry.COLUMN_NAME_ID_USER, user_id );

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(DatabaseContract.FeedEntry.TABLE_NAME_MEASURES, null, values);

        db.close();

        return newRowId;

    }

    public Long getAlarms(Context context) {
        SharedPreferences user = context.getSharedPreferences("UserLogged", MODE_PRIVATE);
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = DatabaseContract.FeedEntry.COLUMN_NAME_ID_USER + " = ?" + " AND " + DatabaseContract.FeedEntry.COLUMN_NAME_ID_TYPE_MEASURE + " = 3";
        String[] selectionArgs = { user.getString("user_id","") }; //query per user_id da shared preferences
        String sortOrder =
                DatabaseContract.FeedEntry.COLUMN_NAME_TMSTMP + " DESC";



        // How you want the results sorted in the resulting Cursor

        Cursor cursor = db.query(
                DatabaseContract.FeedEntry.TABLE_NAME_MEASURES,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection, //selection,              // The columns for the WHERE clause
                selectionArgs, //selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder         // The sort order
        );
        //String [] Alarms = new String[cursor.getCount()];
        int i=0;

        if (cursor.moveToFirst()) {
            Long Alarms=cursor.getLong(4);
            Log.d("alarm",String.valueOf(Alarms));
            cursor.close();
            db.close();
            return Alarms;


        }
        else{
            Long nothing=Long.valueOf("no alarm set");
            cursor.close();
            db.close();
            return nothing;
        }





    }

}

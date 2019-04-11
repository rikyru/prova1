package com.example.groupi.heartattapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONException;
import org.json.JSONObject;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Graphs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);
        RecordAdapter recordAdapter;

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = ((Menu) menu).getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        Intent intent_home = new Intent(Graphs.this, MainActivity.class);
                        startActivity(intent_home);
                        break;
                    case R.id.navigation_planning:
                        Intent intent_planning = new Intent(Graphs.this, Planning.class);
                        startActivity(intent_planning);
                        break;
                    case R.id.navigation_measure:
                        Intent intent_measurements = new Intent(Graphs.this, Measurments.class);
                        startActivity(intent_measurements);
                        break;
                    case R.id.navigation_graphs:
                        break;
                    case R.id.navigation_profile:
                        Intent intent_profile = new Intent(Graphs.this, Profile.class);
                        startActivity(intent_profile);
                        break;
                }
                return false;
            }
        });
        DatabaseDbHelper dbHelper = new DatabaseDbHelper(Graphs.this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DatabaseContract.FeedEntry.COLUMN_NAME_ID_MEASURE,
                DatabaseContract.FeedEntry.COLUMN_NAME_VALUE,
                DatabaseContract.FeedEntry.COLUMN_NAME_ID_USER,
                DatabaseContract.FeedEntry.COLUMN_NAME_TMSTMP
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseContract.FeedEntry.COLUMN_NAME_ID_USER + " = ?" + " AND " + DatabaseContract.FeedEntry.COLUMN_NAME_ID_TYPE_MEASURE + " = 2";
        SharedPreferences user = getSharedPreferences("UserLogged", MODE_PRIVATE);
        String[] selectionArgs = {user.getString("user_id", "")}; //query per user_id da shared preferences

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
        recordAdapter = new RecordAdapter(this, new ArrayList<Record>()); //TODO: implementare diversi recordadapter per pressione e heart rate
        final ListView recordsView = (ListView) findViewById(R.id.records_view);
        recordsView.setAdapter(recordAdapter);
        while (cursor.moveToNext()) {
            String measure = cursor.getString(1); //la stringa è un JSON
            String dia = cursor.getString(2); //prendo user_id mi pare
            Long timestamp = cursor.getLong(3); //timestamp come LONG
            Record record = new Record();
            JSONObject reader = null;
            try {
                reader = new JSONObject(measure);
                record.systolic = reader.getString("systolic");
                record.diastolic = reader.getString("diastolic");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Date date = new java.util.Date(timestamp * 1000L); //conversione in data leggibile
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+2"));
            String formattedDate = sdf.format(date);
            record.timestamp = formattedDate;
            recordAdapter.add(record);

        }
        cursor.close();

    }
}

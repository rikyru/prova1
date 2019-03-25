package com.example.ruggiu.prova1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Graphs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);
        RecordAdapter recordAdapter;

        BottomNavigationView bottomNavigationView =(BottomNavigationView) findViewById(R.id.navigation);

        Menu menu= bottomNavigationView.getMenu();
        MenuItem menuItem = ((Menu) menu).getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        Intent intent_home=new Intent(Graphs.this, MainActivity.class);
                        startActivity(intent_home);
                        break;
                    case R.id.navigation_planning:
                        Intent intent_planning=new Intent(Graphs.this, Planning.class);
                        startActivity(intent_planning);
                        break;
                    case R.id.navigation_measure:
                        Intent intent_measurements=new Intent(Graphs.this, Measurments.class);
                        startActivity(intent_measurements);
                        break;
                    case R.id.navigation_graphs:
                        break;
                    case R.id.navigation_profile:
                        Intent intent_profile=new Intent(Graphs.this, Profile.class);
                        startActivity(intent_profile);
                        break;
                }
                return false;
            }
        });
        PressureReaderDbHelper dbHelper= new PressureReaderDbHelper(Graphs.this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                PressureReaderContract.FeedEntry.COLUMN_NAME_SYS,
                PressureReaderContract.FeedEntry.COLUMN_NAME_DIA,
                PressureReaderContract.FeedEntry.COLUMN_NAME_TIME
        };

        // Filter results WHERE "title" = 'My Title'
       // String selection = PressureReaderContract.FeedEntry.COLUMN_NAME_TITLE + " = ?";
        //String[] selectionArgs = { "My Title" };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                PressureReaderContract.FeedEntry.COLUMN_NAME_DIA + "";

        Cursor cursor = db.query(
                PressureReaderContract.FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null, //selection,              // The columns for the WHERE clause
                null, //selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        recordAdapter = new RecordAdapter(this, new ArrayList<Record>());
        final ListView recordsView = (ListView) findViewById(R.id.records_view);
        recordsView.setAdapter(recordAdapter);
        while(cursor.moveToNext()) {
            String sys=cursor.getString(1);
            String dia=cursor.getString(2);
            String timestamp=cursor.getString(3);
            Record record = new Record();
            record.systolic=sys;
            record.diastolic=dia;
            record.timestamp=timestamp;
            recordAdapter.add(record);

        }
        cursor.close();

    }
}

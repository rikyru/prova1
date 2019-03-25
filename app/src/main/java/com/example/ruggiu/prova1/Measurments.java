package com.example.ruggiu.prova1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Measurments extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurments);
        BottomNavigationView bottomNavigationView =(BottomNavigationView) findViewById(R.id.navigation);

        Menu menu= bottomNavigationView.getMenu();
        MenuItem menuItem = ((Menu) menu).getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        Intent intent_home=new Intent(Measurments.this, MainActivity.class);
                        startActivity(intent_home);
                        break;
                    case R.id.navigation_planning:
                        Intent intent_planning=new Intent(Measurments.this, Planning.class);
                        startActivity(intent_planning);
                        break;
                    case R.id.navigation_measure:
                        break;
                    case R.id.navigation_graphs:
                        Intent intent_graphs=new Intent(Measurments.this, Graphs.class);
                        startActivity(intent_graphs);
                        break;
                    case R.id.navigation_profile:
                        Intent intent_profile=new Intent(Measurments.this, Profile.class);
                        startActivity(intent_profile);
                        break;
                }
                return false;

            }
        });


    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            //INSERT IN DB
            String word = data.getStringExtra(NewWordActivity.EXTRA_REPLY);
            PressureReaderDbHelper dbHelper = new PressureReaderDbHelper(Measurments.this);
            // Gets the data repository in write mode
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(PressureReaderContract.FeedEntry.COLUMN_NAME_SYS, word);
            values.put(PressureReaderContract.FeedEntry.COLUMN_NAME_DIA, word);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd,MMMM,yyyy hh,mm,a");
            String strDate = sdf.format(c.getTime());
            values.put(PressureReaderContract.FeedEntry.COLUMN_NAME_TIME, strDate); //mi da errore nell'aggiungere questa colonna nonostante sia dichiarata

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(PressureReaderContract.FeedEntry.TABLE_NAME, null, values);


        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
    public void AddMeasure(View view) {
        Intent intent = new Intent(Measurments.this, NewWordActivity.class);
        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
    }


}

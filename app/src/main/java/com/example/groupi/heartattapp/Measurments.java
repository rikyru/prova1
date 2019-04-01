package com.example.groupi.heartattapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
            String systolic = data.getStringExtra("systolic");
            String diastolic = data.getStringExtra("diastolic");
            DatabaseDbHelper dbHelper = new DatabaseDbHelper(Measurments.this);
            // Gets the data repository in write mode
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String measure="{systolic:" + systolic + ", diastolic:" + diastolic + "}";
            SharedPreferences user = getSharedPreferences("UserLogged", MODE_PRIVATE);
            String user_id=user.getString("user_id",""); //
            String measure_type_id="2"; //TODO: mappare i tipi di misure (2 è pressione, 1 HR, 3 Sveglie)

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.FeedEntry.COLUMN_NAME_VALUE, measure); //devo importare la stringa
            String unixtimestamp = String.valueOf(System.currentTimeMillis() / 1000L);
            values.put(DatabaseContract.FeedEntry.COLUMN_NAME_TMSTMP, unixtimestamp );
            values.put(DatabaseContract.FeedEntry.COLUMN_NAME_ID_TYPE_MEASURE, measure_type_id);
            values.put(DatabaseContract.FeedEntry.COLUMN_NAME_ID_USER, user_id );

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(DatabaseContract.FeedEntry.TABLE_NAME_MEASURES, null, values);


        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
    public void AddMeasure(View view) {
        Intent intent = new Intent(Measurments.this, NewMeasureActivity.class);
        startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
    }


}

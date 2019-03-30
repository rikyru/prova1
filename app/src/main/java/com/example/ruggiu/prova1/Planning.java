package com.example.ruggiu.prova1;

import android.arch.persistence.room.Database;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;

public class Planning extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = ((Menu) menu).getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        Intent intent_home = new Intent(Planning.this, MainActivity.class);
                        startActivity(intent_home);
                        break;
                    case R.id.navigation_planning:
                        break;
                    case R.id.navigation_measure:
                        Intent intent_measurements = new Intent(Planning.this, Measurments.class);
                        startActivity(intent_measurements);
                        break;
                    case R.id.navigation_graphs:
                        Intent intent_graphs = new Intent(Planning.this, Graphs.class);
                        startActivity(intent_graphs);
                        break;
                    case R.id.navigation_profile:
                        Intent intent_profile = new Intent(Planning.this, Profile.class);
                        startActivity(intent_profile);
                        break;
                }
                return false;
            }
        });
    }
}

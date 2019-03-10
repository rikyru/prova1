package com.example.ruggiu.prova1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigationView =(BottomNavigationView) findViewById(R.id.navigation);

        Menu menu= bottomNavigationView.getMenu();
        MenuItem menuItem = ((Menu) menu).getItem(4);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        Intent intent_home=new Intent(Profile.this, MainActivity.class);
                        startActivity(intent_home);
                        break;
                    case R.id.navigation_planning:
                        Intent intent_planning=new Intent(Profile.this, Planning.class);
                        startActivity(intent_planning);
                        break;
                    case R.id.navigation_measure:
                        Intent intent_measurements=new Intent(Profile.this, Measurments.class);
                        startActivity(intent_measurements);
                        break;
                    case R.id.navigation_graphs:
                        Intent intent_graphs=new Intent(Profile.this, Graphs.class);
                        startActivity(intent_graphs);
                        break;
                    case R.id.navigation_profile:
                        break;
                }
                return false;
            }
        });
    }
}

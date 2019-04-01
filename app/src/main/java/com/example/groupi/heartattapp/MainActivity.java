package com.example.groupi.heartattapp;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    /*private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String sys = "Systolic: " + intent.getStringExtra("systolic") + "mmHg"; //TODO:Sistemare, buttare le misure sul db
            String dia = "Diastolic: "+ intent.getStringExtra("diastolic") + "mmHg";
            String pulse = intent.getStringExtra("pulse");
            Toast.makeText(context, "New Measurement!", Toast.LENGTH_SHORT).show();
            TextView textViewsys = findViewById(R.id.textViewsys);
            textViewsys.setText(sys);
            TextView textViewdia = findViewById(R.id.textViewdia);
            textViewdia.setText(dia);
            System.out.println(sys);
        }
    };
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = ((Menu) menu).getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        break;
                    case R.id.navigation_planning:
                        Intent intent_planning = new Intent(MainActivity.this, Planning.class);
                        startActivity(intent_planning);
                        break;
                    case R.id.navigation_measure:
                        Intent intent_measurements = new Intent(MainActivity.this, Measurments.class);
                        startActivity(intent_measurements);
                        break;
                    case R.id.navigation_graphs:
                        Intent intent_graphs = new Intent(MainActivity.this, Graphs.class);
                        startActivity(intent_graphs);
                        break;
                    case R.id.navigation_profile:
                        Intent intent_profile = new Intent(MainActivity.this, Profile.class);
                        startActivity(intent_profile);
                        break;
                }
                return false;
            }
        });
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String description = "description";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel("CHANNEL_PROVA", "channel", importance);
            channel.setDescription(description);
            channel.enableVibration(false);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        else {
            Intent i = new Intent(getApplicationContext(), MyService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(i);
            } else {
                startService(i);
            }
        }
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(
                mMessageReceiver, new IntentFilter("BP Measure Update"));
                */ //TODO:implementare a parte il Bluetooth
        DatabaseDbHelper db = new DatabaseDbHelper(MainActivity.this);
        BloodPressureMeasurement result;
        result = db.getLastMeasureBP(MainActivity.this);
        TextView textViewlastpressure = findViewById(R.id.textViewlastpressure);
        textViewlastpressure.setText("Last update: " + result.date);
        TextView textViewsys = findViewById(R.id.textViewsys);
        textViewsys.setText("Systolic: " + result.systolic +" mmHg");
        TextView textViewdia = findViewById(R.id.textViewdia);
        textViewdia.setText("Diastolic: " + result.diastolic +" mmHg");
    }

    
}


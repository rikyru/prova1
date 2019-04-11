package com.example.groupi.heartattapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Planning extends AppCompatActivity implements  TimePickerDialog.OnTimeSetListener
   {
        private TextView mTextView;
        int alarmID = 1;



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
            mTextView = findViewById(R.id.Alarm);

            Button buttonTimePicker = findViewById(R.id.Button);
            buttonTimePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment timePicker = new TimePickerFragment();
                    timePicker.show(getSupportFragmentManager(), "time picker");
                }
            });

        /* Button buttonCancelAlarm = findViewById(R.id.button_cancel);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });*/
        updateTimeText();
    }
       @Override
       public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
           Calendar c = Calendar.getInstance();
           c.set(Calendar.HOUR_OF_DAY, hourOfDay);
           c.set(Calendar.MINUTE, minute);
           c.set(Calendar.SECOND, 0);


           Long strDate = c.getTimeInMillis();
           DatabaseDbHelper dbHelper = new DatabaseDbHelper(Planning.this);
           dbHelper.insert_Alarm(strDate,Planning.this);
           startAlarm(c, alarmID);
           alarmID = alarmID + 1;
           updateTimeText();
       }

       private void updateTimeText() {
           String timeText = "Alarm set for: ";

           DatabaseDbHelper dbHelper = new DatabaseDbHelper(Planning.this);
           Long alarm;

           alarm=dbHelper.getAlarms(Planning.this);
           SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
           String alarmString = formatter.format(new Date(alarm));
           timeText += alarmString;
           /*TODO: COLLEGAMENTO A DB PER SALVARE SVEGLIA PER UN UTENTE PARTICOLARE*/
           //public boolean insertAlarm(int id, String timetext) -> da dichiarare in db
           //query su measure table + alcuni field vuoti


           //Al posto mettere query che legge tutte le sveglie per quell'utente
           //showAlarms(int id);
           //query su ID = 1 e measure type = 3
           mTextView.setText(timeText);
       }

       private void startAlarm(Calendar c, int id) {
           AlarmManager alarmManage = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
           Intent intent = new Intent(this, AlertReceiver.class);
           PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);

           if (c.before(Calendar.getInstance())) {
               c.add(Calendar.DATE, 1);
           }

           alarmManage.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 1000*60*60*24, pendingIntent);

       }

       private void cancelAlarm(int id) {
           AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
           Intent intent = new Intent(this, AlertReceiver.class);
           PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0);

           alarmManager.cancel(pendingIntent);
           mTextView.setText("Alarm canceled");
       }
}

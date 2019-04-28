package com.example.groupi.heartattapp;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Measurments extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private EditText mEditSystolicPressure;
    private EditText mEditDiastolicPressure;
    private EditText edit;
    private RadioGroup rg1;
    private String op1;
    private Boolean isEmpty;
    private BloodPressureMeasurement measure;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String sys = "HR mean of 5: " + intent.getStringExtra("meanHR"); //TODO:Sistemare, buttare le misure sul db
            Toast.makeText(context, "New Measurement!", Toast.LENGTH_SHORT).show();
            System.out.println(sys);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurments);
        BottomNavigationView bottomNavigationView =(BottomNavigationView) findViewById(R.id.navigation);

        Menu menu= bottomNavigationView.getMenu();
        MenuItem menuItem = ((Menu) menu).getItem(2);
        menuItem.setChecked(true);
        measure=new BloodPressureMeasurement();
        op1="";

        rg1 = (RadioGroup) findViewById(R.id.radioGroup);
        rg1.setOnCheckedChangeListener(this);
        edit = (EditText) findViewById(R.id.editText6);
        actv(false);
        mEditSystolicPressure = findViewById(R.id.editText4);
        mEditDiastolicPressure = findViewById(R.id.editText5);

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

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEditSystolicPressure.getText())) { //TODO mettere un or anche sulla diastolica
                    isEmpty=Boolean.TRUE;
                } else {
                    measure.systolic = mEditSystolicPressure.getText().toString();
                    measure.diastolic = mEditDiastolicPressure.getText().toString();
                    if (op1.equals("other")){
                        String symptoms = edit.getText().toString();
                        Log.d("sintomo", symptoms);
                    }
                    else {
                        String symptoms = op1;
                    }

                     //TODO:edit box che si riempie con le misurazioni prese dal sensore
                    isEmpty=Boolean.FALSE;
                    DatabaseDbHelper dbHelper = new DatabaseDbHelper(Measurments.this);
                    Boolean result=dbHelper.insert_BP(measure, Measurments.this);
                    if (result==Boolean.FALSE)
                    {
                        Log.e("error db insert", "error in insert");
                    }
                    else
                    {
                        Toast.makeText(Measurments.this,"Successfully added!",Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                Measurments.this.startActivity(new Intent(Measurments.this,MainActivity.class));

                            }
                        }, 500);

                    }
                }
            }
        });
        final Button HRButton = findViewById(R.id.HR_Button);
        HRButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String description = "description";
                    int importance = NotificationManager.IMPORTANCE_LOW;
                    NotificationChannel channel = new NotificationChannel("CHANNEL_HR", "channel_hr", importance);
                    channel.setDescription(description);
                    channel.enableVibration(false);
                    // Register the channel with the system; you can't change the importance
                    // or other notification behaviors after this
                    NotificationManager notificationManager = getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(channel);
                }
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    ActivityCompat.requestPermissions(Measurments.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
                else {
                    Intent i = new Intent(getApplicationContext(), HRService.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startService(i);//startForegroundService(i);
                    } else {
                        startService(i);
                    }
                }
                LocalBroadcastManager.getInstance(Measurments.this).registerReceiver(
                        mMessageReceiver, new IntentFilter("HR Measure Update"));

            }
        });


    }
    private void actv(final boolean active)
    {
        edit.setEnabled(active);
        if (active)
        {
            edit.requestFocus();
            edit.setVisibility(View.VISIBLE);
            edit.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        }
        else
        {
            edit.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButton7:
                op1="other";
                actv(true);
                break;

            case R.id.radioButton:
                actv(false);
                op1="chest";
                break;

            case R.id.radioButton2:
                actv(false);
                op1="shortness";
                break;

            case R.id.radioButton3:
                actv(false);
                op1="palpitations";
                break;

            case R.id.radioButton4:
                actv(false);
                op1="sweling";
                break;

            case R.id.radioButton5:
                actv(false);
                op1="diziness";
                break;

            case R.id.radioButton6:
                actv(false);
                op1="fatigue";
                break;

        }

    }


}

package com.example.groupi.heartattapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    private TextView etName, etSurname, etDOB, etUsername, etPassword, etPhysician;

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

        etName = (TextView) findViewById(R.id.etName);
        //etAge = (EditText) findViewById(R.id.etAge);
        etSurname = (TextView) findViewById(R.id.etSurname);
        etDOB = (TextView) findViewById(R.id.etDateOfBirth);
        etUsername = (TextView) findViewById(R.id.etUsername);
        etPassword = (TextView) findViewById(R.id.etPassword);
        etPhysician = (TextView) findViewById(R.id.etPhysicianEmail);

        DatabaseDbHelper db = new DatabaseDbHelper(Profile.this);
        User result;
        result = db.getUserInfo(Profile.this);
        Log.d("DOB", result.DOB);
        etName.setText(result.name);
        etSurname.setText(result.surname);
        etDOB.setText(result.DOB);
        etPassword.setText(result.password);
        etUsername.setText(result.username);
        etPhysician.setText(result.dr_email);

        final Button button = findViewById(R.id.button_logout);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences user = getSharedPreferences("UserLogged", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = user.edit();
                prefEditor.putString("user_id", "");
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Profile.this.startActivity(new Intent(Profile.this,myLogin.class));

                    }
                }, 500);
            }

            });



    }
}

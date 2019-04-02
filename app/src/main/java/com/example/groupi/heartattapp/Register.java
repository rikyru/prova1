package com.example.groupi.heartattapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Register extends AppCompatActivity {

    private static final String TAG = "Register";

    EditText etName, etSurname, etUsername, etPassword, etPhysicianEmail;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Button bRegister;
    DatabaseDbHelper myDB;
    String date="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        myDB = new DatabaseDbHelper(this);

        etName = (EditText) findViewById(R.id.etName);
        //etAge = (EditText) findViewById(R.id.etAge);
        etSurname = (EditText) findViewById(R.id.etSurname);
        mDisplayDate = (TextView) findViewById(R.id.tvDate);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etPhysicianEmail = (EditText) findViewById(R.id.etPhysicianEmail);

        bRegister = (Button) findViewById(R.id.bRegister);


        //Button on click
        /* bRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.bRegister:
                        String name = etName.getText().toString();
                        String surname = etSurname.getText().toString();
                        String username = etUsername.getText().toString();
                        String password = etPassword.getText().toString();
                        //int age = Integer.parseInt(etAge.getText().toString());

                        //User userRegistered = new User(name, surname, username, password);

                        startActivity(new Intent(Register.this, myWelcome.class));
                        break;
            }
        }});*/

        // Date on click
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Register.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day

                );

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;

                String monthABC = null;
                switch (month){
                    case 1:
                        monthABC = "January";
                        break;
                    case 2:
                        monthABC = "February";
                        break;
                    case 3:
                        monthABC = "March";
                        break;
                    case 4:
                        monthABC = "April";
                        break;
                    case 5:
                        monthABC = "May";
                        break;
                    case 6:
                        monthABC = "June";
                        break;
                    case 7:
                        monthABC = "July";
                        break;
                    case 8:
                        monthABC = "August";
                        break;
                    case 9:
                        monthABC = "September";
                        break;
                    case 10:
                        monthABC = "October";
                        break;
                    case 11:
                        monthABC = "November";
                        break;
                    case 12:
                        monthABC = "December";
                        break;
                }

                date = dayOfMonth + " " + monthABC + " " + year;
                mDisplayDate.setText(date);

            }
        };

        AddUser();
        //Button on click
        /*bRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.bRegister:
                        String name = etName.getText().toString();
                        String surname = etSurname.getText().toString();
                        String username = etUsername.getText().toString();
                        String password = etPassword.getText().toString();
                        //int age = Integer.parseInt(etAge.getText().toString());

                        //User userRegistered = new User(name, surname, username, password);

                        startActivity(new Intent(Register.this, myWelcome.class));
                        break;
                }
            }}) */;


    }

    public void AddUser() {
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username;
                username = etUsername.getText().toString();
                boolean isInserted;

                boolean check = myDB.searchUser(username);


                if(check == false){
                    isInserted = myDB.insertUser(etName.getText().toString(),
                            etSurname.getText().toString(),
                            date, etUsername.getText().toString(), etPassword.getText().toString(),
                            etPhysicianEmail.getText().toString(), Register.this);

                    if (isInserted == true)
                        Toast.makeText(Register.this, "Sign up correctly", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(Register.this, "Data not inserted", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(Register.this, myWelcome.class));

                }

                else
                {
                    Toast.makeText(Register.this, "Username already exists", Toast.LENGTH_SHORT).show();
                }
            }});
    }

}
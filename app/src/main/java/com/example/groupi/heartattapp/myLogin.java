package com.example.groupi.heartattapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.groupi.heartattapp.R;


public class myLogin extends AppCompatActivity {

    Button bLogin;
    EditText etUsername;
    EditText etPassword;
    TextView tvRegisterLink;
    DatabaseDbHelper myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_login);
        //DB instance declaration
        myDB = new DatabaseDbHelper(this);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        bLogin = (Button) findViewById(R.id.bLogin);
        tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);

        //Login button
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean checkUserPass = false;
                String user = etUsername.getText().toString();
                String pass = etPassword.getText().toString();

                if (user.equals(" ") || pass.equals(" ")) {
                    Toast.makeText(myLogin.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    checkUserPass = myDB.emailPassword(user, pass, myLogin.this);
                    if (checkUserPass == true) {

                        startActivity(new Intent(myLogin.this, MainActivity.class));
                    }
                    else
                        Toast.makeText(myLogin.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Sign up
        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(myLogin.this, Register.class));
            }
        });
    }
}
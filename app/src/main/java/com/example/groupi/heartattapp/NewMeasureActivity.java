package com.example.groupi.heartattapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;

public class NewMeasureActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mEditSystolicPressure;
    private EditText mEditDiastolicPressure;
    private EditText edit;
    private RadioGroup rg1;
    private String op1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_measure);
        rg1 = (RadioGroup) findViewById(R.id.radioGroup);
        rg1.setOnCheckedChangeListener(this);
        edit = (EditText) findViewById(R.id.editText6);
        actv(false);
        mEditSystolicPressure = findViewById(R.id.editText4);
        mEditDiastolicPressure = findViewById(R.id.editText5);


        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditSystolicPressure.getText())) { //mettere un or anche sulla diastolica
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String systolic = mEditSystolicPressure.getText().toString();
                    String diastolic = mEditDiastolicPressure.getText().toString();
                    replyIntent.putExtra("systolic", systolic);
                    replyIntent.putExtra("diastolic", diastolic); //TODO:edit box che si riempie con le misurazioni prese dal sensore
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
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
package com.example.ruggiu.prova1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewMeasureActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText mEditSystolicPressure;
    private EditText mEditDiastolicPressure;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_measure);
        mEditSystolicPressure = findViewById(R.id.edit_pressure_systolic);
        mEditDiastolicPressure = findViewById(R.id.edit_pressure_diastolic);

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
                    replyIntent.putExtra("diastolic", diastolic);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
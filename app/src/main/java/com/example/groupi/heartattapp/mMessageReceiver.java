package com.example.groupi.heartattapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class mMessageReceiver {
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String sys = intent.getStringExtra("systolic");
            String dia = intent.getStringExtra("diastolic");
            String pulse = intent.getStringExtra("pulse");
            Toast.makeText(context, sys, Toast.LENGTH_SHORT).show();
            System.out.println(sys);
        }
    };
}

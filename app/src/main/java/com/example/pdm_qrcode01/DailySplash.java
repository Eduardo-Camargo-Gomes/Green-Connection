package com.example.pdm_qrcode01;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class DailySplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_splash);

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentMainAcitivty = new Intent(
                        DailySplash.this,
                        MainActivity.class);
                startActivity(intentMainAcitivty);
                finish();
            }
        },1000);

    }
}
package com.example.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {
    //private static int SPLASH_SCREEN = 3000;
    private static int SPLASH_SCREEN = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        //bắt đầu mainactivity sau 5 giây
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //bắt đầu SplashActivity
                Intent intent = new Intent(SplashActivity.this, introActivity.class);
//                Intent intent = new Intent(SplashActivity.this, Home.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}
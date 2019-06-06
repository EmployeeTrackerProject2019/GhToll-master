package com.example.serly.ghtoll;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splashscreen extends AppCompatActivity {
    private static int SplashTime_out=1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent loginpage= new Intent(Splashscreen.this,Welcome.class);
                startActivity(loginpage);
                finish();
            }
        },SplashTime_out);

    }
}

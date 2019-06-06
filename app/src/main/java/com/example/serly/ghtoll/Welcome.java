package com.example.serly.ghtoll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }


    public void doAction(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                startActivity(new Intent(Welcome.this, LogInActivity.class));
                break;
            case R.id.btnSignUp:
                startActivity(new Intent(Welcome.this,Register.class));
                break;
        }
    }
}

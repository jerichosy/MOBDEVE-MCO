package com.mobdeve.s17.samirsattendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        getSupportActionBar().hide();
    }

    public void onLogin(View v) {
        Intent i = new Intent(this, HomeActivity.class);
        startActivity(i);
    }
}
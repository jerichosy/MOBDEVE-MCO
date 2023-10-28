package com.mobdeve.s17.samirsattendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextFirstName;
    EditText editTextLastName;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        getSupportActionBar().hide();

        editTextFirstName = (EditText) findViewById(R.id.register_et_firstName);
        editTextLastName = (EditText) findViewById(R.id.register_et_lastName);
        editTextEmail = (EditText) findViewById(R.id.register_et_email);
        editTextPassword = (EditText) findViewById(R.id.register_et_password);
        editTextConfirmPassword = (EditText) findViewById(R.id.register_et_confirmPassword);
    }

    public void onRegister(View v) {
        Intent i = new Intent(this, LoginActivity.class);

        // Code here to check input, insert DB
        Toast.makeText(this, "Registration successful! Please login.", Toast.LENGTH_SHORT).show();
        startActivity(i);
        finish();
    }
}
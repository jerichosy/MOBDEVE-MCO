package com.mobdeve.s17.samirsattendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateClass extends AppCompatActivity {

    EditText et_className;
    EditText et_maxMembers;
    EditText et_classSchedule;
    Button btn_createClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);

        et_className = (EditText) findViewById(R.id.et_className);
        et_maxMembers = (EditText) findViewById(R.id.et_maxMembers);
        et_classSchedule = (EditText) findViewById(R.id.et_classSchedule);
        btn_createClass = (Button) findViewById(R.id.btn_createClass);
        btn_createClass.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Class created!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
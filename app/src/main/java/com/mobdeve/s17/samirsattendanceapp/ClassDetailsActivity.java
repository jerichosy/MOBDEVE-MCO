package com.mobdeve.s17.samirsattendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ClassDetailsActivity extends AppCompatActivity {

    TextView tvClassName;
    TextView tvClassSchedule;

    Button btnAttend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);

        tvClassName = (TextView) findViewById(R.id.tv_class_name);
        tvClassSchedule = (TextView) findViewById(R.id.tv_class_schedule);

        tvClassName.setText(getIntent().getStringExtra("className"));
        tvClassSchedule.setText(getIntent().getStringExtra("classSchedule"));

        btnAttend = (Button) findViewById(R.id.btn_attend);
        btnAttend.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Attendance recorded!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
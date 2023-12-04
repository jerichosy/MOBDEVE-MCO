package com.mobdeve.s17.samirsattendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ClassDetailsActivity extends AppCompatActivity {

    TextView tvClassName;
    TextView tvClassSchedule;
    TextView tvFaculty;
    TextView tvMembers;
    Button btnAttend;
    private String join_code;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_details);

        tvClassName = (TextView) findViewById(R.id.tv_class_name);
        tvClassSchedule = (TextView) findViewById(R.id.tv_class_schedule);
        tvFaculty = (TextView) findViewById(R.id.tv_faculty);
        tvMembers = (TextView) findViewById(R.id.tv_members);
        this.join_code = getIntent().getStringExtra("classJoinCode");

        tvClassName.setText(getIntent().getStringExtra("className"));
        tvClassSchedule.setText(getIntent().getStringExtra("classSchedule"));
        tvFaculty.setText(getIntent().getStringExtra("classCreatorDisplayName"));
        tvMembers.setText(getIntent().getStringExtra("classMembers") + " / " + getIntent().getStringExtra("classCapacity"));

        btnAttend = (Button) findViewById(R.id.btn_attend);
        btnAttend.setOnClickListener(v -> {
            mAuth = FirebaseAuth.getInstance();
            db = FirebaseFirestore.getInstance();
            db.collection("attendance").add(new AttendanceData(Objects.requireNonNull(mAuth.getCurrentUser()).getUid(), join_code));
            Toast.makeText(getApplicationContext(), "Attendance recorded!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
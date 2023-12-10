package com.mobdeve.s17.samirsattendanceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class ClassCreateActivity extends AppCompatActivity {

    EditText et_className;
    EditText et_maxMembers;
    EditText et_scheduleDays;
    EditText et_scheduleStartTime;
    EditText et_scheduleEndTime;
    EditText et_learningMode;
    Button btn_createClass;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_create);

        et_className = (EditText) findViewById(R.id.et_className);
        et_maxMembers = (EditText) findViewById(R.id.et_maxMembers);
        et_scheduleDays = (EditText) findViewById(R.id.et_scheduleDays);
        et_scheduleStartTime = (EditText) findViewById(R.id.et_scheduleStartTime);
        et_scheduleEndTime = (EditText) findViewById(R.id.et_scheduleEndTime);
        et_learningMode = (EditText) findViewById(R.id.et_learningMode);
        btn_createClass = (Button) findViewById(R.id.btn_createClass);
        this.db = FirebaseFirestore.getInstance();
        btn_createClass.setOnClickListener(v -> {
            String id = java.util.UUID.randomUUID().toString();
            String className = et_className.getText().toString();
            int maxMembers = Integer.parseInt(et_maxMembers.getText().toString());
            String classSchedule = et_scheduleDays.getText().toString() + ";" + et_scheduleStartTime.getText().toString() + "-" + et_scheduleEndTime.getText().toString();
            String classCode = generateJoinCode(className);
            String learningMode = et_learningMode.getText().toString().toUpperCase();
            String creator = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
            String creatorDisplayName = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName();
            ClassData newClass = new ClassData(id, className, classSchedule, learningMode, maxMembers, classCode, creator, creatorDisplayName);
            db.collection("classes").add(newClass).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(getApplicationContext(), "Class created!", Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(e -> {
                Toast.makeText(getApplicationContext(), "Class creation failed!", Toast.LENGTH_SHORT).show();
            });
            finish();
        });
    }

    private String generateJoinCode(String className) {
        StringBuilder joinCode = new StringBuilder();
        Random r = new Random();
        joinCode.append(className.toLowerCase().replace(" ", "-"));
        joinCode.append('-');
        for (int i = 0; i < 5; i++) {
            int letter = r.nextInt(26) + 65;
            joinCode.append((char) letter);
        }
        return joinCode.toString();
    }
}
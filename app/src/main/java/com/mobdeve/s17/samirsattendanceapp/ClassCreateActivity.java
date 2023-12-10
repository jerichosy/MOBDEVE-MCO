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
            String className = et_className.getText().toString();
            int maxMembers = Integer.parseInt(et_maxMembers.getText().toString());
            int currentMembers = 0;
            String classSchedule = et_scheduleDays.getText().toString() + ";" + et_scheduleStartTime.getText().toString() + "-" + et_scheduleEndTime.getText().toString();
            String classCode = (className.toLowerCase() + " " + classSchedule).replace(" ", "_");
            String learningMode = et_learningMode.getText().toString().toUpperCase();
            Map<String, Object> new_class = new HashMap<>();
            new_class.put("name", className);
            new_class.put("capacity", maxMembers);
            new_class.put("members", currentMembers);
            new_class.put("schedule", classSchedule);
            new_class.put("join_code", classCode);
            new_class.put("learning_mode", learningMode);
            new_class.put("creator", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid());
            new_class.put("creator_display_name", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName());

            db.collection("classes").add(new_class).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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
}
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
    EditText et_classSchedule;
    Button btn_createClass;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_create);

        et_className = (EditText) findViewById(R.id.et_className);
        et_maxMembers = (EditText) findViewById(R.id.et_maxMembers);
        et_classSchedule = (EditText) findViewById(R.id.et_scheduleTime);
        btn_createClass = (Button) findViewById(R.id.btn_createClass);
        this.db = FirebaseFirestore.getInstance();
        btn_createClass.setOnClickListener(v -> {
            String className = et_className.getText().toString();
            int maxMembers = Integer.parseInt(et_maxMembers.getText().toString());
            int currentMembers = 0;
            String classSchedule = et_classSchedule.getText().toString();
            String classCode = (className.toLowerCase() + " " + classSchedule).replace(" ", "_");
            Map<String, Object> new_class = new HashMap<>();
            new_class.put("name", className);
            new_class.put("capacity", maxMembers);
            new_class.put("members", currentMembers);
            new_class.put("schedule", classSchedule);
            new_class.put("join_code", classCode);
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
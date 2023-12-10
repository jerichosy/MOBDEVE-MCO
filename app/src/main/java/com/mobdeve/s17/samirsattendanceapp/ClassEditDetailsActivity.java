package com.mobdeve.s17.samirsattendanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class ClassEditDetailsActivity extends AppCompatActivity {

    EditText et_className;
    EditText et_maxMembers;
    EditText et_classSchedule;
    Button btn_editClass;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_edit_details);

        et_className = (EditText) findViewById(R.id.et_className);
        et_maxMembers = (EditText) findViewById(R.id.et_maxMembers);
        et_classSchedule = (EditText) findViewById(R.id.et_scheduleDays);
        btn_editClass = (Button) findViewById(R.id.btn_editClass);

        // Get class data from intent
        Intent i = getIntent();
        String classId = i.getStringExtra("classId");
        String className = i.getStringExtra("className");
        String classSchedule = i.getStringExtra("classSchedule");
        int classCapacity = i.getIntExtra("classCapacity", 999);
        System.out.println("classCapacity: " + classCapacity);

        et_className.setText(className);
        et_classSchedule.setText(classSchedule);
        et_maxMembers.setText(String.valueOf(classCapacity));

        // Update class data
        this.db = FirebaseFirestore.getInstance();

        btn_editClass.setOnClickListener(v -> {
            String newClassName = et_className.getText().toString();
            int maxMembers = Integer.parseInt(et_maxMembers.getText().toString());
            String newClassSchedule = et_classSchedule.getText().toString();
            String classCode = (newClassName.toLowerCase() + " " + newClassSchedule).replace(" ", "_");

            db.collection("classes").document(classId)
                    .update("name", newClassName,
                            "capacity", maxMembers,
                            "schedule", newClassSchedule,
                            "join_code", classCode)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(), "Class edited!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error updating document", Toast.LENGTH_SHORT).show();
                        }
                    });
            finish();  // FIXME: This should be in onSuccess
        });
    }
}

//        db.collection("classes").document(i.getStringExtra("classId")).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Toast.makeText(getApplicationContext(), "Class deleted!", Toast.LENGTH_SHORT).show();
//            }
//        });

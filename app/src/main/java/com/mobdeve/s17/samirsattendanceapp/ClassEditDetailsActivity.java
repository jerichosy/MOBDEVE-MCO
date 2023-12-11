package com.mobdeve.s17.samirsattendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ClassEditDetailsActivity extends AppCompatActivity {

    EditText et_className;
    EditText et_maxMembers;
    EditText et_classSchedule;
    EditText et_learningMode;
    Button btn_editClass;
    Button btn_deleteClass;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_edit_details);

        et_className = findViewById(R.id.et_className);
        et_maxMembers = findViewById(R.id.et_maxMembers);
        et_classSchedule = findViewById(R.id.et_scheduleDays);
        et_learningMode = findViewById(R.id.et_learningModeEdit);
        btn_editClass = findViewById(R.id.btn_editClass);
        btn_deleteClass = findViewById(R.id.btn_deleteClass);

        // Get class data from intent
        Intent i = getIntent();
        String classId = i.getStringExtra("classId");
        String className = i.getStringExtra("className");
        String classSchedule = i.getStringExtra("classSchedule");
        String classLearningMode = i.getStringExtra("classLearningMode");
        int classCapacity = i.getIntExtra("classCapacity", 999);
        System.out.println("classCapacity: " + classCapacity);

        et_className.setText(className);
        et_classSchedule.setText(classSchedule);
        et_maxMembers.setText(String.valueOf(classCapacity));
        et_learningMode.setText(classLearningMode);

        // Update class data
        this.db = FirebaseFirestore.getInstance();

        btn_editClass.setOnClickListener(v -> {
            String newClassName = et_className.getText().toString();
            int maxMembers = Integer.parseInt(et_maxMembers.getText().toString());
            String newClassSchedule = et_classSchedule.getText().toString();
            String newLearningMode = et_learningMode.getText().toString().toUpperCase();
            db.collection("classes").whereEqualTo("classId", classId).get().addOnSuccessListener(queryDocumentSnapshots -> {
                DocumentReference docRef = queryDocumentSnapshots.getDocuments().get(0).getReference();
                docRef.update("className", newClassName,
                                "classCapacity", maxMembers,
                                "classSchedule", newClassSchedule,
                                "classLearningMode", newLearningMode)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getApplicationContext(), "Class edited!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Error updating document", Toast.LENGTH_SHORT).show());
            });
        });

        btn_deleteClass.setOnClickListener(v -> db.collection("classes").whereEqualTo("classId", classId).get().addOnSuccessListener(queryDocumentSnapshots -> {
            DocumentReference docRef = queryDocumentSnapshots.getDocuments().get(0).getReference();
            docRef.delete().addOnSuccessListener(aVoid -> {
                Toast.makeText(getApplicationContext(), "Class deleted!", Toast.LENGTH_SHORT).show();
                finish();
            }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Error deleting document", Toast.LENGTH_SHORT).show());
        }));
    }
}

//        db.collection("classes").document(i.getStringExtra("classId")).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Toast.makeText(getApplicationContext(), "Class deleted!", Toast.LENGTH_SHORT).show();
//            }
//        });

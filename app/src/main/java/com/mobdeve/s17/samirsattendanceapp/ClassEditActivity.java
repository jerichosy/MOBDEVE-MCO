package com.mobdeve.s17.samirsattendanceapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ClassEditActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_edit);

        ClassEditAdapter adapter = new ClassEditAdapter(Collections.emptyList());

        RecyclerView recyclerView = findViewById(R.id.rv_editclass_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        this.db = FirebaseFirestore.getInstance();
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        db.collection("classes")
                .whereEqualTo("classCreator", uid)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Create an empty list to hold ClassData objects
                        List<ClassData> classDataList = new ArrayList<>();

                        // Iterate through the query results
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Create a new ClassData object with the retrieved data
                            ClassData classData = document.toObject(ClassData.class);
                            // Add the created ClassData object to the list
                            classDataList.add(classData);
                        }

                        // At this point, 'classDataList' contains all ClassData objects
                        // You can now use this list for your application's purposes

                        // Update the data in adapter and notify the adapter for changes.
                        adapter.setClassDataList(classDataList);
                        adapter.notifyDataSetChanged();

                    } else {
                        // Handle the error
                        Log.d("FirestoreError", "Error getting documents: ", task.getException());
                    }
                });
    }
}
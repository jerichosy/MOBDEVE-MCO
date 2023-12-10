package com.mobdeve.s17.samirsattendanceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ClassEditActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_edit);

        ClassEditAdapter adapter = new ClassEditAdapter(Arrays.asList(new ClassData[0]));

        RecyclerView recyclerView = findViewById(R.id.rv_editclass_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        this.db = FirebaseFirestore.getInstance();
        db.collection("classes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Create an empty list to hold ClassData objects
                            List<ClassData> classDataList = new ArrayList<>();

                            // Iterate through the query results
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Extract class name and schedule from the document
                                String classId = document.getId();
                                String className = Objects.requireNonNull(document.getData().get("name")).toString();
                                String classSchedule = Objects.requireNonNull(document.getData().get("schedule")).toString();
                                String classLearningMode = Objects.requireNonNull(document.getData().get("learning_mode")).toString();
                                int classCapacity = Integer.parseInt(Objects.requireNonNull(document.getData().get("capacity")).toString());
//                                System.out.println("\nwowwwww\n");
//                                System.out.println(className);
//                                System.out.println(classSchedule);
                                System.out.println(document.getData());

                                // Create a new ClassData object with the retrieved data
                                ClassData classData = new ClassData(classId, className, classSchedule, classLearningMode,classCapacity);

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
                    }
                });
    }
}
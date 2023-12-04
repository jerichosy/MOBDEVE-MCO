package com.mobdeve.s17.samirsattendanceapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobdeve.s17.samirsattendanceapp.ClassData;
import com.mobdeve.s17.samirsattendanceapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize the RecyclerView and your custom adapter
        recyclerView = binding.rvClassList; // Make sure you have 'recyclerView' in your fragment_home.xml

//        ClassData[] classData = new ClassData[]{
//            new ClassData("STINTSY S14", "MH 9:15-10:45"),
//            new ClassData("CSOPESY S13", "S 9:15-12:30")
//        };
//        ClassAdapter adapter = new ClassAdapter(classData); // Initialize your adapter, here an empty list is passed

        ClassAdapter adapter = new ClassAdapter(Arrays.asList(new ClassData[0]));

        // Set LayoutManager and Adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
                                String className = Objects.requireNonNull(document.getData().get("name")).toString();
                                String classSchedule = Objects.requireNonNull(document.getData().get("schedule")).toString();
//                                System.out.println("\nwowwwww\n");
//                                System.out.println(className);
//                                System.out.println(classSchedule);
                                System.out.println(document.getData());

                                // Create a new ClassData object with the retrieved data
                                ClassData classData = new ClassData(className, classSchedule);

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

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

//        // Assume you have a method in your ViewModel to fetch list data.
//        homeViewModel.getData().observe(getViewLifecycleOwner(), data -> {
//            // Update the data in adapter and notify the adapter for changes.
//            adapter.updateData(data);
//        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
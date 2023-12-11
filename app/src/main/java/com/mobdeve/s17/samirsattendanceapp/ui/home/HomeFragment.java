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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobdeve.s17.samirsattendanceapp.ClassData;
import com.mobdeve.s17.samirsattendanceapp.TimeParser;
import com.mobdeve.s17.samirsattendanceapp.databinding.FragmentHomeBinding;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView rvClassList;
    private RecyclerView rvUpcomingList;
    private FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize the RecyclerView and your custom adapter
        rvClassList = binding.rvClassList; // Make sure you have 'recyclerView' in your fragment_home.xml
        rvUpcomingList = binding.rvUpcomingList;

        ClassAdapter classListAdapter = new ClassAdapter(Arrays.asList(new ClassData[0]));
        ClassAdapter upcomingListAdapter = new ClassAdapter(Arrays.asList(new ClassData[0]));

        // Set LayoutManager and Adapter
        rvClassList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvClassList.setAdapter(classListAdapter);
        rvUpcomingList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvUpcomingList.setAdapter(upcomingListAdapter);

        this.db = FirebaseFirestore.getInstance();
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        db.collection("classes")
                .where(Filter.or(
                        Filter.equalTo("classCreator", uid),
                        Filter.arrayContains("classMembers", uid)
                ))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Create an empty list to hold ClassData objects
                            List<ClassData> classList = new ArrayList<>();
                            List<ClassData> upcomingList = new ArrayList<>();

                            // Iterate through the query results
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ClassData classData = document.toObject(ClassData.class);
                                // Add the created ClassData object to the list if the user is a member
                                classList.add(classData);
                            }

                            // At this point, 'classDataList' contains all ClassData objects
                            // You can now use this list for your application's purposes
                            for (ClassData item : classList) {
                                // TODO: Filter based on time
                                upcomingList.add(item);
                            }

                            // Update the data in adapter and notify the adapter for changes.
                            classListAdapter.setClassDataList(classList);
                            classListAdapter.notifyDataSetChanged();
                            upcomingListAdapter.setClassDataList(upcomingList);
                            upcomingListAdapter.notifyDataSetChanged();

                        } else {
                            // Handle the error
                            Log.d("FirestoreError", "Error getting documents: ", task.getException());
                        }
                    }
                });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
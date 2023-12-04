package com.mobdeve.s17.samirsattendanceapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.samirsattendanceapp.ClassData;
import com.mobdeve.s17.samirsattendanceapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize the RecyclerView and your custom adapter
        recyclerView = binding.rvClassList; // Make sure you have 'recyclerView' in your fragment_home.xml
        ClassData[] classData = new ClassData[]{
            new ClassData("STINTSY S14", "MH 9:15-10:45", 40, 30, "ABC123"),
            new ClassData("CSOPESY S13", "S 9:15-12:30", 40, 20, "ABC234"),
        };
        ClassAdapter adapter = new ClassAdapter(classData); // Initialize your adapter, here an empty list is passed

        // Set LayoutManager and Adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

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
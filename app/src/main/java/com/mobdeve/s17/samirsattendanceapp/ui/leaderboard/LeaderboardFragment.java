package com.mobdeve.s17.samirsattendanceapp.ui.leaderboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.samirsattendanceapp.ClassData;
import com.mobdeve.s17.samirsattendanceapp.databinding.FragmentLeaderboardBinding;
import com.mobdeve.s17.samirsattendanceapp.ui.home.ClassAdapter;

public class LeaderboardFragment extends Fragment {

    private FragmentLeaderboardBinding binding;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LeaderboardViewModel leaderboardViewModel =
                new ViewModelProvider(this).get(LeaderboardViewModel.class);

        binding = FragmentLeaderboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize the RecyclerView and your custom adapter
        recyclerView = binding.rvLeaderboardList; // Make sure you have 'recyclerView' in your fragment_home.xml
        ClassData[] leaderboardData = new ClassData[]{ // temporary use class data since its placeholders
                new ClassData("Samir Car Driver", "50"),
                new ClassData("Vladimir Cyka Rush", "48")
        };
        LeaderboardAdapter adapter = new LeaderboardAdapter(leaderboardData); // Initialize your adapter, here an empty list is passed

        // Set LayoutManager and Adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        final TextView textView = binding.textLeaderboard;
        leaderboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Switch sw = binding.switchLeaderboard;
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sw.setText(sw.isChecked() ? "Hall of Fame" : "Hall of Shame");
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
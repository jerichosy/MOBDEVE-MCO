package com.mobdeve.s17.samirsattendanceapp.ui.settings;

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

import com.mobdeve.s17.samirsattendanceapp.ClassCreateActivity;
import com.mobdeve.s17.samirsattendanceapp.SettingsData;
import com.mobdeve.s17.samirsattendanceapp.databinding.FragmentSettingsBinding;
import com.mobdeve.s17.samirsattendanceapp.ui.leaderboard.LeaderboardAdapter;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SettingsViewModel settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize the RecyclerView and your custom adapter
        recyclerView = binding.rvSettingsList; // Make sure you have 'recyclerView' in your fragment_home.xml
        SettingsData[] settingsData = new SettingsData[]{ // temporary use class data since its placeholders
                new SettingsData("Create Class", ClassCreateActivity.class),
                new SettingsData("Join Class"),
                new SettingsData("Edit Class"),
                new SettingsData("Settings"),
                new SettingsData("Logout")
        };
        SettingsAdapter adapter = new SettingsAdapter(settingsData); // Initialize your adapter, here an empty list is passed

        // Set LayoutManager and Adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

//        final TextView textView = binding.textSettings;
//        settingsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
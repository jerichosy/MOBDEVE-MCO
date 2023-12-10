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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mobdeve.s17.samirsattendanceapp.ClassData;
import com.mobdeve.s17.samirsattendanceapp.StudentRecord;
import com.mobdeve.s17.samirsattendanceapp.databinding.FragmentLeaderboardBinding;
import com.mobdeve.s17.samirsattendanceapp.ui.home.ClassAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeaderboardFragment extends Fragment {

    private FragmentLeaderboardBinding binding;
    private RecyclerView recyclerView;
    private FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LeaderboardViewModel leaderboardViewModel =
                new ViewModelProvider(this).get(LeaderboardViewModel.class);

        binding = FragmentLeaderboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize the RecyclerView and your custom adapter
        recyclerView = binding.rvLeaderboardList; // Make sure you have 'recyclerView' in your fragment_home.xml
        LeaderboardAdapter adapter = new LeaderboardAdapter(Arrays.asList(new StudentRecord[0])); // Initialize your adapter, here an empty list is passed

        this.db = FirebaseFirestore.getInstance();
        // Get attendance_total from Firestore and pass it to the adapter as a List<StudentRecord>
        db.collection("attendance_total")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<StudentRecord> leaderboardDataList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Create a StudentRecord object from the document
                                StudentRecord studentRecord = document.toObject(StudentRecord.class);
                                // Add the StudentRecord object to the list
                                leaderboardDataList.add(studentRecord);
                            }
                            leaderboardDataList.sort((o1, o2) -> o2.getAttendance() - o1.getAttendance());
                            adapter.setLeaderboardData(leaderboardDataList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });


        // Set LayoutManager and Adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.mobdeve.s17.samirsattendanceapp.ui.leaderboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.samirsattendanceapp.ClassData;
import com.mobdeve.s17.samirsattendanceapp.R;
import com.mobdeve.s17.samirsattendanceapp.StudentRecord;

import org.w3c.dom.Text;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    List<StudentRecord> leaderboardData;

    public LeaderboardAdapter(List<StudentRecord> leaderboardDataList) {
        this.leaderboardData = leaderboardDataList;
    }

    @NonNull
    @Override
    public LeaderboardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // TODO: Not sure if importing R like this instead of using context.getResources().getStringArray(R...) is okay
        View view = inflater.inflate(R.layout.leaderboard_item_list, parent, false);
        LeaderboardAdapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardAdapter.ViewHolder holder, int position) {
        final StudentRecord classDataList = leaderboardData.get(position);
        holder.studentName.setText(classDataList.getStudentName());
        holder.attendance.setText(String.valueOf(classDataList.getAttendance()));
    }

    @Override
    public int getItemCount() {
        return leaderboardData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView studentName;
        TextView attendance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.studentName);
            attendance = itemView.findViewById(R.id.attendance);
        }
    }

    public void setLeaderboardData(List<StudentRecord> leaderboardData) {
        this.leaderboardData = leaderboardData;
        notifyDataSetChanged();
    }
}

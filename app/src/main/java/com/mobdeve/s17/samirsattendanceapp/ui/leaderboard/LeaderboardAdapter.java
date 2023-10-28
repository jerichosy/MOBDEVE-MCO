package com.mobdeve.s17.samirsattendanceapp.ui.leaderboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.samirsattendanceapp.ClassData;
import com.mobdeve.s17.samirsattendanceapp.R;

import org.w3c.dom.Text;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.ViewHolder> {

    ClassData[] leaderboardData;

    public LeaderboardAdapter(ClassData[] leaderboardData) {
        this.leaderboardData = leaderboardData;
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
        final ClassData classDataList = leaderboardData[position];
        holder.studentName.setText(classDataList.getClassName());
        holder.attendance.setText(classDataList.getClassSchedule());
    }

    @Override
    public int getItemCount() {
        return leaderboardData.length;
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
}

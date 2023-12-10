package com.mobdeve.s17.samirsattendanceapp.ui.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.samirsattendanceapp.ClassData;
import com.mobdeve.s17.samirsattendanceapp.ClassDetailsActivity;
import com.mobdeve.s17.samirsattendanceapp.R;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    private List<ClassData> classDataList;

    public ClassAdapter(List<ClassData> classDataList) {
        this.classDataList = classDataList;
    }

    @NonNull
    @Override
    public ClassAdapter.ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // TODO: Not sure if importing R like this instead of using context.getResources().getStringArray(R...) is okay
        View view = inflater.inflate(R.layout.class_item_list, parent, false);
        ClassAdapter.ClassViewHolder viewHolder = new ClassViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassAdapter.ClassViewHolder holder, int position) {
        final ClassData classData = classDataList.get(position);
        holder.className.setText(classData.getClassName());
        holder.classSchedule.setText(classData.getClassSchedule());
        holder.classLearningMode.setText(classData.getClassLearningMode());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ClassDetailsActivity.class);
                i.putExtra("className", classData.getClassName());
                i.putExtra("classSchedule", classData.getClassSchedule());
                i.putExtra("classCreator", classData.getClassCreator());
                i.putExtra("classCreatorDisplayName", classData.getClassCreatorDisplayName());
                i.putExtra("classCapacity", String.valueOf(classData.getClassCapacity()));
                i.putExtra("classMembers", String.valueOf(classData.getClassMembers()));
                i.putExtra("classJoinCode", classData.getClassJoinCode());
                i.putExtra("classLearningMode", classData.getClassLearningMode());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return classDataList.size();
    }

    public class ClassViewHolder extends RecyclerView.ViewHolder {

        TextView className;
        TextView classSchedule;
        TextView classLearningMode;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.className);
            classSchedule = itemView.findViewById(R.id.classSchedule);
            classLearningMode = itemView.findViewById(R.id.classLearningMode);
        }
    }

    // Method to update the internal list of the adapter
    public void setClassDataList(List<ClassData> newData) {
        this.classDataList = newData;
        notifyDataSetChanged(); // Notify the adapter to refresh the RecyclerView
    }
}

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

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    ClassData[] classData;

    public ClassAdapter(ClassData[] classData) {
        this.classData = classData;
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
        final ClassData classDataList = classData[position];
        holder.className.setText(classDataList.getClassName());
        holder.classSchedule.setText(classDataList.getClassSchedule());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ClassDetailsActivity.class);
                i.putExtra("className", classDataList.getClassName());
                i.putExtra("classSchedule", classDataList.getClassSchedule());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return classData.length;
    }

    public class ClassViewHolder extends RecyclerView.ViewHolder {

        TextView className;
        TextView classSchedule;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.className);
            classSchedule = itemView.findViewById(R.id.classSchedule);
        }
    }
}

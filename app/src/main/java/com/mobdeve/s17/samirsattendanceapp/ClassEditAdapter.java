package com.mobdeve.s17.samirsattendanceapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ClassEditAdapter extends RecyclerView.Adapter<ClassEditAdapter.ViewHolder> {

    private List<ClassData> classDataList;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView className;
        private final TextView classSchedule;
        private final TextView classLearningMode;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            className = view.findViewById(R.id.className);
            classSchedule = view.findViewById(R.id.classSchedule);
            classLearningMode = view.findViewById(R.id.classLearningMode);
        }

        public TextView getClassName() {
            return className;
        }
        public TextView getClassSchedule() {
            return classSchedule;
        }
        public TextView getClassLearningMode() {
            return classLearningMode;
        }
    }

    /**
     * Initialize the dataset of the Adapter
     *
     * @param classDataList List<ClassData> containing the data to populate views to be used
     * by RecyclerView
     */
    public ClassEditAdapter(List<ClassData> classDataList) {
        this.classDataList = classDataList;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.class_item_list, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        final ClassData classData = classDataList.get(position);
        viewHolder.getClassName().setText(classData.getClassName());
        viewHolder.getClassSchedule().setText(classData.getClassSchedule());
        viewHolder.getClassLearningMode().setText(classData.getClassLearningMode());

        viewHolder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), ClassEditDetailsActivity.class);
            i.putExtra("classId", classData.getClassId());
            i.putExtra("className", classData.getClassName());
            i.putExtra("classSchedule", classData.getClassSchedule());
            i.putExtra("classCapacity", classData.getClassCapacity());
            i.putExtra("classLearningMode", classData.getClassLearningMode());
            v.getContext().startActivity(i);
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return classDataList.size();
    }

    // Method to update the internal list of the adapter
    public void setClassDataList(List<ClassData> newData) {
        this.classDataList = newData;
        notifyDataSetChanged(); // Notify the adapter to refresh the RecyclerView
    }
}

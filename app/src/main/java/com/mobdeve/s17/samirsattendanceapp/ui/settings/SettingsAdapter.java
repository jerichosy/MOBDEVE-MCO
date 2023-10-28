package com.mobdeve.s17.samirsattendanceapp.ui.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s17.samirsattendanceapp.ClassData;
import com.mobdeve.s17.samirsattendanceapp.R;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder>{

    ClassData[] settingsData;

    public SettingsAdapter(ClassData[] settingsData) {
        this.settingsData = settingsData;
    }

    @NonNull
    @Override
    public SettingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // TODO: Not sure if importing R like this instead of using context.getResources().getStringArray(R...) is okay
        View view = inflater.inflate(R.layout.leaderboard_item_list, parent, false);
        SettingsAdapter.ViewHolder viewHolder = new SettingsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsAdapter.ViewHolder holder, int position) {
        final ClassData classDataList = settingsData[position];
        holder.settingName.setText(classDataList.getClassName());
    }

    @Override
    public int getItemCount() {
        return settingsData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView settingName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            settingName = itemView.findViewById(R.id.settingName);
        }
    }
}

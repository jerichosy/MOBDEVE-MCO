package com.mobdeve.s17.samirsattendanceapp.ui.settings;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.mobdeve.s17.samirsattendanceapp.MainActivity;
import com.mobdeve.s17.samirsattendanceapp.R;
import com.mobdeve.s17.samirsattendanceapp.SettingsData;
//import com.mobdeve.s17.samirsattendanceapp.ui.settings;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder>{

    SettingsData[] settingsData;
    private final FirebaseAuth mAuth;

    public SettingsAdapter(SettingsData[] settingsData) {
        this.settingsData = settingsData;
        this.mAuth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public SettingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // TODO: Not sure if importing R like this instead of using context.getResources().getStringArray(R...) is okay
        View view = inflater.inflate(R.layout.settings_item_list, parent, false);
        SettingsAdapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsAdapter.ViewHolder holder, int position) {
        final SettingsData settingsDataList = settingsData[position];
        holder.settingName.setText(settingsDataList.getSettingsName());

        Class settingsCls = settingsDataList.getSettingsCls();

        holder.itemView.setOnClickListener(v -> {
            if (settingsCls != null) {
                Intent i = new Intent(v.getContext(), settingsCls);
                v.getContext().startActivity(i);
            } else if (position == settingsData.length - 1) { // This should be logout
                mAuth.signOut();
                // To close all activities and go back to the login screen, create a new Intent with the CLEAR_TOP flag.
                Intent logoutIntent = new Intent(v.getContext(), MainActivity.class);
                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                // Clear all the previous activities and bring LoginActivity to the top of stack
                v.getContext().startActivity(logoutIntent);
                // Optionally, if you want to animate the transition, you can use an overridePendingTransition after startActivity
                // This needs a `Context context` field/member received from the constructor
//                if (context instanceof Activity) {
//                    ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return settingsData.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView settingName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            settingName = itemView.findViewById(R.id.settingName);
        }
    }
}

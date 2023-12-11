package com.mobdeve.s17.samirsattendanceapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class ReminderBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");

        // Set the flag to FLAG_IMMUTABLE
        int flags = PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, flags);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "weekly_reminder_id")
                .setSmallIcon(R.drawable.samirs_attendance_app_logo) // replace with your own drawable
                .setContentTitle("Check how you did in your classes this week!")
                .setContentText("Take a moment to check out your attendance in your classes this week.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(contentIntent)
                .setAutoCancel(true);

        notificationManager.notify(200, builder.build());
    }
}
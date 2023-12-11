package com.mobdeve.s17.samirsattendanceapp;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.mobdeve.s17.samirsattendanceapp.databinding.ActivityHomeBinding;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_leaderboard, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        createNotificationChannel();
        scheduleWeeklyReminder();
        createNotificationChannel2();
        this.db = FirebaseFirestore.getInstance();
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        db.collection("classes")
                .where(Filter.or(
                        Filter.equalTo("classCreator", uid),
                        Filter.arrayContains("classMembers", uid)
                ))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Iterate through the query results
                        int j = 10;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            j += 10;
                            ClassData classData = document.toObject(ClassData.class);
                            // Add the created ClassData object to the list if the user is a member
                            TimeParser parser = new TimeParser(classData.getClassSchedule());
                            System.out.println(parser.getStartTime());
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");
                            LocalTime time = LocalTime.parse(parser.getStartTime().toUpperCase(), formatter);
                            System.out.println(time);

                            String soon = "";
                            if (classData.getClassLearningMode().equals("ONLINE")) {
                                time = time.minusMinutes(30);
                                soon = "30 minutes";
                            } else {
                                time = time.minusHours(3);
                                soon = "3 hours";
                            }
                            System.out.println(time);

                            // loop through days
                            String schedDays = parser.getSchedDays();
                            for (int i = 0; i < schedDays.length(); i++) {
                                // get day
                                String day = schedDays.charAt(i) + "";
                                int dayOfWeek = Calendar.SUNDAY;
                                if (day.equals("M")) {
                                    dayOfWeek = Calendar.MONDAY;
                                } else if (day.equals("T")) {
                                    dayOfWeek = Calendar.TUESDAY;
                                } else if (day.equals("W")) {
                                    dayOfWeek = Calendar.WEDNESDAY;
                                } else if (day.equals("H")) {
                                    dayOfWeek = Calendar.THURSDAY;
                                } else if (day.equals("F")) {
                                    dayOfWeek = Calendar.FRIDAY;
                                } else if (day.equals("S")) {
                                    dayOfWeek = Calendar.SATURDAY;
                                }

                                scheduleEventNotification(time, dayOfWeek, classData.getClassName(), soon, i+j);
                            }
                        }

                        // At this point, 'classDataList' contains all ClassData objects
                        // You can now use this list for your application's purposes
                    } else {
                        // Handle the error
                        Log.d("FirestoreError", "Error getting documents: ", task.getException());
                    }
                });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Weekly Reminder Channel";
            String description = "Channel for weekly app reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("weekly_reminder_id", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createNotificationChannel2() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Class Reminder Channel";
            String description = "Channel for class reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("class_reminder_id", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void scheduleWeeklyReminder() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Set the flag to FLAG_IMMUTABLE
        int flags = PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT;

        Intent intent = new Intent(this, ReminderBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, flags);

        // Set the alarm to start at a specific time.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        // SCHEDULING
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        System.out.println("This works: " + calendar);

        // Check we aren't setting it in the past which would trigger it immediately
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }

        // Repeat the alarm every week.
        long repeatInterval = AlarmManager.INTERVAL_DAY * 7;

        // For API 19 and above, setRepeating has been replaced with setInexactRepeating.
        // If you need exact timing use setExact (or setExactAndAllowWhileIdle for Doze mode).
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), repeatInterval, pendingIntent);
    }

    public void scheduleEventNotification(LocalTime time, int dayOfWeek, String className, String soon, int i) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, EventBroadcastReceiver.class);
//        intent.putExtra("event_id", event.getId()); // Use the event's ID to identify the event
        intent.putExtra("className", className);
        intent.putExtra("soon", soon);

        int pendingIntentFlags = Build.VERSION.SDK_INT >= 31 ? PendingIntent.FLAG_IMMUTABLE : 0;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, i, intent, pendingIntentFlags); // Unique requestCode using event ID

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        calendar.set(Calendar.HOUR_OF_DAY, time.getHour());
        calendar.set(Calendar.MINUTE, time.getMinute());
        calendar.set(Calendar.SECOND, time.getSecond());
        System.out.println("Scheduled: " + calendar);

        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }

        long repeatInterval = AlarmManager.INTERVAL_DAY * 7;

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), repeatInterval, pendingIntent);
    }
}
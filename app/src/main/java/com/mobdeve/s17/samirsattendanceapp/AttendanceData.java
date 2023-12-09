package com.mobdeve.s17.samirsattendanceapp;

import android.os.Build;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AttendanceData {
    private String uid;
    private String display_name;
    private String join_code;
    private String date;
    public AttendanceData() {
        // empty constructor needed for firebase
    }

    public AttendanceData(String uid, String display_name, String class_id) {
        this.uid = uid;
        this.display_name = display_name;
        this.join_code = class_id;
        Date currentDate = new Date();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.US);
        this.date = dateFormat.format(currentDate);
    }

    public String getUid() {
        return uid;
    }

    public String getDisplay_name() { return display_name;}

    public String getJoin_code() {
        return join_code;
    }

    public String getDate() {
        return date;
    }
}

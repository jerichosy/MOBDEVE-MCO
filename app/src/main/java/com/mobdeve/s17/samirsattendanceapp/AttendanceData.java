package com.mobdeve.s17.samirsattendanceapp;

import android.os.Build;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AttendanceData {
    private String uid;
    private String join_code;
    private String date;
    public AttendanceData() {
        // empty constructor needed for firebase
    }

    public AttendanceData(String uid, String class_id) {
        this.uid = uid;
        this.join_code = class_id;
        Date currentDate = new Date();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.US);
        this.date = dateFormat.format(currentDate);
    }

    public AttendanceData(String uid, String class_id, String date) {
        this.uid = uid;
        this.join_code = class_id;
        this.date = date;
    }

    public String getUid() {
        return uid;
    }

    public String getJoin_code() {
        return join_code;
    }

    public String getDate() {
        return date;
    }
}

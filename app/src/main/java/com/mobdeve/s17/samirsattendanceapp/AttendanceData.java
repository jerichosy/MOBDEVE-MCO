package com.mobdeve.s17.samirsattendanceapp;

public class AttendanceData {
    private String uid;
    private String display_name;
    private String join_code;
    private String date;
    public AttendanceData() {
        // empty constructor needed for firebase
    }

    public AttendanceData(String uid, String date, String display_name, String class_id) {
        this.uid = uid;
        this.display_name = display_name;
        this.join_code = class_id;
        this.date = date;
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

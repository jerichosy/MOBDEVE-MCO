package com.mobdeve.s17.samirsattendanceapp;

public class StudentRecord {

    private String studentName;
    private String uid;
    private int attendance;
    public StudentRecord() {
        // empty constructor needed for firebase
    }

    public StudentRecord(String studentName, String uid, int attendance) {
        this.studentName = studentName;
        this.uid = uid;
        this.attendance = attendance;
    }

    public String getStudentName() {
        return studentName;
    }
    public String getUid() { return uid; }

    public int getAttendance() { return attendance; }

    public boolean compareUid(String uid) { return this.uid.equals(uid); }
}

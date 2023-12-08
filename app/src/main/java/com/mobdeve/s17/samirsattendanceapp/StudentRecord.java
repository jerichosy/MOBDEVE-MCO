package com.mobdeve.s17.samirsattendanceapp;

public class StudentRecord {

    public String studentName;
    public int present;
    public int absent;
    public StudentRecord() {
        // empty constructor needed for firebase
    }

    public StudentRecord(String studentName, int present, int absent) {
        this.studentName = studentName;
        this.present = present;
        this.absent = absent;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getPresent() {
        return present;
    }

    public int getAbsent() {
        return absent;
    }
}

package com.mobdeve.s17.samirsattendanceapp;

public class ClassData {
    private String name;  // this includes the section
    private String schedule;
    private int capacity;
    private int members;
    private String join_code;
    private String creator;

    public ClassData() {
        // empty constructor needed for firebase
    }

    public ClassData(String className, String classSchedule) {
        this.name = className;
        this.schedule = classSchedule;
        this.capacity = 0;
        this.members = 0;
        this.join_code = "";
        this.creator = "yes";
    }

    public ClassData(String className, String classSchedule, int capacity, int members, String joinCode) {
        this.name = className;
        this.schedule = classSchedule;
        this.capacity = capacity;
        this.members = members;
        this.join_code = joinCode;
        this.creator = "yes";
    }

    public ClassData(String className, String classSchedule, int capacity, int members, String joinCode, String creator) {
        this.name = className;
        this.schedule = classSchedule;
        this.capacity = capacity;
        this.members = members;
        this.join_code = joinCode;
        this.creator = creator;
    }

    public String getClassName() {
        return name;
    }

    public void setClassName(String className) {
        this.name = className;
    }

    public String getClassSchedule() {
        return schedule;
    }

    public void setClassSchedule(String classSchedule) {
        this.schedule = classSchedule;
    }

    @Override
    public String toString() {
        return "Class Name: " + name;
    }
}

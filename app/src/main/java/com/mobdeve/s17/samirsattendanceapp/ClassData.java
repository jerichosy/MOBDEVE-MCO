package com.mobdeve.s17.samirsattendanceapp;

public class ClassData {
    private String className;  // this includes the section
    private String classSchedule;
    private int capacity;
    private int members;
    private String joinCode;

    public ClassData(String className, String classSchedule, int capacity, int members, String joinCode) {
        this.className = className;
        this.classSchedule = classSchedule;
        this.capacity = capacity;
        this.members = members;
        this.joinCode = joinCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassSchedule() {
        return classSchedule;
    }

    public void setClassSchedule(String classSchedule) {
        this.classSchedule = classSchedule;
    }
}

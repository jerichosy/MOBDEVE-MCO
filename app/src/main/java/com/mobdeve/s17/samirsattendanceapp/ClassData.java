package com.mobdeve.s17.samirsattendanceapp;

public class ClassData {
    private String className;  // this includes the section
    private String classSchedule;

    public ClassData(String className, String classSchedule) {
        this.className = className;
        this.classSchedule = classSchedule;
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

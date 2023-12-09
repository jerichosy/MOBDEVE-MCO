package com.mobdeve.s17.samirsattendanceapp;

public class ClassData {
    private String id;
    private String name;  // this includes the section
    private String schedule;
    private int capacity;
    private int members;
    private String join_code;
    private String creator;
    private String creator_display_name;

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
        this.creator_display_name = "";
    }

    public ClassData(String id, String className, String classSchedule, int classCapacity) {
        this.id = id;
        this.name = className;
        this.schedule = classSchedule;
        this.capacity = classCapacity;
        this.members = 0;
        this.join_code = "";
        this.creator = "yes";
    }

    public ClassData(String className, String classSchedule, int capacity, int members, String joinCode, String creator, String creator_display_name) {
        this.name = className;
        this.schedule = classSchedule;
        this.capacity = capacity;
        this.members = members;
        this.join_code = joinCode;
        this.creator = creator;
        this.creator_display_name = creator_display_name;
    }

    public ClassData(String className, String classSchedule, int capacity, int members, String joinCode, String creator) {
        this.name = className;
        this.schedule = classSchedule;
        this.capacity = capacity;
        this.members = members;
        this.join_code = joinCode;
        this.creator = creator;
        this.creator_display_name = "";
    }

    public String getClassId() {
        return id;
    }

    public void setClassId(String id) {
        this.id = id;
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

    public int getClassCapacity() {
        return capacity;
    }

    public void setClassCapacity(int classCapacity) {
        this.capacity = classCapacity;
    }

    public int getClassMemberCount() {
        return members;
    }

    public void setClassMemberCount(int classMemberCount) {
        this.members = classMemberCount;
    }

    public int getClassMembers() {
        return members;
    }

    public void setClassMembers(int classMembers) {
        this.members = classMembers;
    }

    public String getClassJoinCode() {
        return join_code;
    }

    public void setClassJoinCode(String classJoinCode) {
        this.join_code = classJoinCode;
    }

    public String getClassCreator() {
        return creator;
    }

    public void setClassCreator(String classCreator) {
        this.creator = classCreator;
    }

    public String getClassCreatorDisplayName() {
        return creator_display_name;
    }

    @Override
    public String toString() {
        return "Class Name: " + name;
    }
}

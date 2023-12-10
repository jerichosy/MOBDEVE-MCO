package com.mobdeve.s17.samirsattendanceapp;

public class ClassData {
    private String id;
    private String name;  // this includes the section
    private String schedule;
    private int capacity;
    private int members;
    private String join_code;
    private String learning_mode;
    private String creator;
    private String creator_display_name;

    public ClassData() {
        // empty constructor needed for firebase
    }

    public ClassData(String id, String className, String classSchedule, int classCapacity) {
        this.id = id;
        this.name = className;
        this.schedule = classSchedule;
        this.capacity = classCapacity;
        this.members = 0;
        this.join_code = "";
        this.creator = "yes";
        this.creator_display_name = "";
    }

    public ClassData(String className, String classSchedule, int capacity, int members, String joinCode, String creator, String creator_display_name, String learning_mode) {
        this.name = className;
        this.schedule = classSchedule;
        this.capacity = capacity;
        this.members = members;
        this.join_code = joinCode;
        this.creator = creator;
        this.creator_display_name = creator_display_name;
        this.learning_mode = learning_mode;
    }

    public String getClassId() {
        return id;
    }

    public String getClassName() {
        return name;
    }

    public String getClassSchedule() {
        return schedule;
    }

    public int getClassCapacity() {
        return capacity;
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

    public String getClassCreator() {
        return creator;
    }

    public String getClassCreatorDisplayName() {
        return creator_display_name;
    }

    public String getClassLearningMode() {
        return learning_mode;
    }

    @Override
    public String toString() {
        return "Class Name: " + name;
    }
}

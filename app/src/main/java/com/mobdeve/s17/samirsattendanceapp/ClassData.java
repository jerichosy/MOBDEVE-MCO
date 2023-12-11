package com.mobdeve.s17.samirsattendanceapp;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ClassData {
    private String id;
    private String name;  // this includes the section
    private String schedule;
    private int capacity;
    private List<String> members;
    private String join_code;
    private String learning_mode;
    private String creator;
    private String creator_display_name;

    public ClassData() {
        // empty constructor needed for firebase
    }

    public ClassData(String id, String className, String classSchedule, String classLearningMode, int classCapacity) {
        this.id = id;
        this.name = className;
        this.schedule = classSchedule;
        this.capacity = classCapacity;
        this.learning_mode = classLearningMode;
    }

    public ClassData(String id, String className, String classSchedule, String classLearningMode, int classCapacity, String classCode, String creator, String creatorDisplayName) {
        this.id = id;
        this.name = className;
        this.schedule = classSchedule;
        this.capacity = classCapacity;
        this.learning_mode = classLearningMode;
        this.join_code = classCode;
        this.creator = creator;
        this.creator_display_name = creatorDisplayName;
        this.members = new ArrayList<>();
    }

    public String getClassId() { return id; }

    public String getClassName() { return name; }

    public String getClassSchedule() { return schedule; }

    public int getClassCapacity() { return capacity; }

    public List<String> getClassMembers() { return members; }

    public String getClassJoinCode() {
        return join_code;
    }

    public String getClassCreator() {
        return creator;
    }

    public String getClassCreatorDisplayName() {
        return creator_display_name;
    }

    public String getClassLearningMode() { return learning_mode; }

    public void setClassId(String id) { this.id = id; }

    public void setClassName(String name) { this.name = name; }

    public void setClassSchedule(String schedule) { this.schedule = schedule; }

    public void setClassCapacity(int capacity) { this.capacity = capacity; }

    public void setClassMembers(List<String> members) { this.members = members; }

    public void setClassJoinCode(String join_code) { this.join_code = join_code; }

    public void setClassLearningMode(String learning_mode) { this.learning_mode = learning_mode; }

    public void setClassCreator(String creator) { this.creator = creator; }

    public void setClassCreatorDisplayName(String creator_display_name) { this.creator_display_name = creator_display_name; }

    @NonNull
    @Override
    public String toString() {
        return "Class Name: " + name;
    }
}

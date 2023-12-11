package com.mobdeve.s17.samirsattendanceapp;

public class TimeParser {
    private final String fullSched;
    private final String schedDays;
    private final String startTime;
    private final String endTime;
    public TimeParser(String fullSched) {
        this.fullSched = fullSched;
        this.schedDays = fullSched.split(";")[0];
        this.startTime = fullSched.split(";")[1].split("-")[0];
        this.endTime = fullSched.split(";")[1].split("-")[1];
    }

    public String getFullSched() {
        return this.fullSched;
    }

    public String getSchedDays() {
        return this.schedDays;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }
}

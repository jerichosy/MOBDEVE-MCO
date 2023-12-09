package com.mobdeve.s17.samirsattendanceapp;

public class MembershipData {
    private String uid;
    private String join_code;

    public MembershipData() {
        // empty constructor needed for firebase
    }

    public MembershipData(String uid, String join_code) {
        this.uid = uid;
        this.join_code = join_code;
    }

    public String getUid() {
        return uid;
    }

    public String getJoin_code() {
        return join_code;
    }
}

package com.mobdeve.s17.samirsattendanceapp;

public class SettingsData {
    private String settingsName;
    private Class settingsCls;

    public SettingsData(String settingsName, Class settingsCls) {
        this.settingsName = settingsName;
        this.settingsCls = settingsCls;
    }

    public SettingsData(String settingsName) {
        this.settingsName = settingsName;
    }

    public String getSettingsName() {
        return settingsName;
    }

    public void setSettingsName(String settingsName) {
        this.settingsName = settingsName;
    }

    public Class getSettingsCls() {
        return settingsCls;
    }

    public void setSettingsCls(Class settingsView) {
        this.settingsCls = settingsView;
    }
}

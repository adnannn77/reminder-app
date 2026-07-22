package com.reminder.app.model;

public class ReminderDraft {

    private int id;

    private boolean editMode;

    private String title;
    private String note;
    private String reminderDate;
    private String reminderTime;

    private String locationName;
    private double latitude;
    private double longitude;

    private String url;

    private String priority;
    private String repeat;

    private String label;
    private String reminderBefore;

    private String color;

    public ReminderDraft() {

        id = 0;

        editMode = false;

        title = "";
        note = "";

        reminderDate = "";
        reminderTime = "";

        locationName = "";
        latitude = 0;
        longitude = 0;

        url = "";

        priority = "Normal";
        repeat = "Tidak Pernah";

        label = "Pengingat";
        reminderBefore = "Saat Waktu Tiba";

        color = "#007AFF";

    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getReminderBefore() {
        return reminderBefore;
    }

    public void setReminderBefore(String reminderBefore) {
        this.reminderBefore = reminderBefore;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

}
package com.reminder.app.model;

public class Reminder {

    private int id;
    private int userId;

    private String title;
    private String note;

    private String reminderDate;
    private String reminderTime;

    private String color;

    private String latitude;
    private String longitude;

    private boolean completed;

    public Reminder() {
    }

    public Reminder(
            int id,
            int userId,
            String title,
            String note,
            String reminderDate,
            String reminderTime,
            String color,
            String latitude,
            String longitude,
            boolean completed
    ) {

        this.id = id;
        this.userId = userId;
        this.title = title;
        this.note = note;
        this.reminderDate = reminderDate;
        this.reminderTime = reminderTime;
        this.color = color;
        this.latitude = latitude;
        this.longitude = longitude;
        this.completed = completed;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}
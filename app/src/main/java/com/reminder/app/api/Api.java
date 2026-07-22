package com.reminder.app.api;

public class Api {

    // ======================================
    // BASE URL
    // ======================================

    public static final String BASE_URL =
            "http://10.0.2.2:3000/api/";

    // ======================================
    // AUTH
    // ======================================

    public static final String LOGIN =
            BASE_URL + "login";

    public static final String REGISTER =
            BASE_URL + "register";

    // ======================================
    // REMINDER
    // ======================================

    public static final String REMINDER =
            BASE_URL + "reminders";

    public static final String REMINDER_BY_USER =
            BASE_URL + "reminders/user/";

    public static final String WEATHER =
            BASE_URL + "external/weather";

    public static final String GEOCODE =
            BASE_URL + "external/geocode";

    public static final String REMINDER_ARCHIVE =
            BASE_URL + "reminders/";

}
package com.reminder.app.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "ReminderSession";

    private static final String KEY_IS_LOGIN = "isLogin";
    private static final String KEY_ID = "id";
    private static final String KEY_NAMA = "nama";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHOTO = "photo";

    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;

    public SessionManager(Context context) {

        preferences = context.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
        );

        editor = preferences.edit();

    }

    public void createSession(
            int id,
            String nama,
            String email,
            String photo
    ) {

        editor.putBoolean(KEY_IS_LOGIN, true);
        editor.putInt(KEY_ID, id);
        editor.putString(KEY_NAMA, nama);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHOTO, photo);

        editor.apply();

    }

    public boolean isLogin() {

        return preferences.getBoolean(
                KEY_IS_LOGIN,
                false
        );

    }

    public int getId() {

        return preferences.getInt(
                KEY_ID,
                0
        );

    }

    public String getNama() {

        return preferences.getString(
                KEY_NAMA,
                ""
        );

    }

    public String getEmail() {

        return preferences.getString(
                KEY_EMAIL,
                ""
        );

    }

    public String getPhoto() {

        return preferences.getString(
                KEY_PHOTO,
                ""
        );

    }

    public void logout() {

        editor.clear();
        editor.apply();

    }

}
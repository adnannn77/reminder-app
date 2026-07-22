package com.reminder.app.utils;

import com.reminder.app.model.ReminderDraft;

public class DraftManager {

    private static ReminderDraft draft = new ReminderDraft();

    public static ReminderDraft getDraft() {

        return draft;

    }

    public static void saveDraft(ReminderDraft reminderDraft) {

        draft = reminderDraft;

    }

    public static void resetDraft() {

        draft = new ReminderDraft();

    }

}
package com.reminder.app.activity;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.reminder.app.R;
import com.reminder.app.model.ReminderDraft;
import com.reminder.app.utils.DraftManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeActivity extends AppCompatActivity {

    private TextView txtCancel;
    private TextView txtDone;
    private TextView txtPreview;

    private TimePicker timePicker;

    private ReminderDraft draft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_time);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(WindowInsetsCompat.Type.systemBars());

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;

                });

        draft = DraftManager.getDraft();

        initView();

        initTime();

        initListener();

    }

    private void initView() {

        txtCancel = findViewById(R.id.txtCancel);
        txtDone = findViewById(R.id.txtDone);
        txtPreview = findViewById(R.id.txtPreview);

        timePicker = findViewById(R.id.timePicker);

        timePicker.setIs24HourView(true);

    }

    private void initTime() {

        Calendar calendar = Calendar.getInstance();

        if (!draft.getReminderTime().isEmpty()) {

            try {

                String[] split = draft.getReminderTime().split(":");

                calendar.set(
                        Calendar.HOUR_OF_DAY,
                        Integer.parseInt(split[0])
                );

                calendar.set(
                        Calendar.MINUTE,
                        Integer.parseInt(split[1])
                );

            } catch (Exception ignored) {

            }

        }

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            timePicker.setHour(hour);
            timePicker.setMinute(minute);

        } else {

            timePicker.setCurrentHour(hour);
            timePicker.setCurrentMinute(minute);

        }

        updatePreview(hour, minute);

    }

    private void initListener() {

        txtCancel.setOnClickListener(v -> finish());

        txtDone.setOnClickListener(v -> saveTime());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            timePicker.setOnTimeChangedListener(
                    (view, hourOfDay, minute) ->
                            updatePreview(hourOfDay, minute)
            );

        } else {

            timePicker.setOnTimeChangedListener(
                    (view, hourOfDay, minute) ->
                            updatePreview(hourOfDay, minute)
            );

        }

    }

    private void updatePreview(int hour, int minute) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        String result =
                new SimpleDateFormat(
                        "HH:mm",
                        Locale.getDefault()
                ).format(calendar.getTime());

        txtPreview.setText(result);

    }

    private void saveTime() {

        int hour;
        int minute;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            hour = timePicker.getHour();
            minute = timePicker.getMinute();

        } else {

            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();

        }

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        String result =
                new SimpleDateFormat(
                        "HH:mm:ss",
                        Locale.getDefault()
                ).format(calendar.getTime());

        draft.setReminderTime(result);

        DraftManager.saveDraft(draft);

        finish();

    }

}
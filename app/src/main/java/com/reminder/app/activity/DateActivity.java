package com.reminder.app.activity;

import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

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

public class DateActivity extends AppCompatActivity {

    private TextView txtBack;
    private TextView txtDone;
    private TextView txtSelectedDate;

    private DatePicker datePicker;

    private ReminderDraft draft;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_date);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(
                                    WindowInsetsCompat.Type.systemBars()
                            );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;

                });

        draft = DraftManager.getDraft();

        calendar = Calendar.getInstance();

        initView();

        loadDraft();

        initListener();

    }

    private void initView() {

        txtBack = findViewById(R.id.txtBack);
        txtDone = findViewById(R.id.txtDone);

        txtSelectedDate = findViewById(R.id.txtSelectedDate);

        datePicker = findViewById(R.id.datePicker);

    }

    private void loadDraft() {

        if (!draft.getReminderDate().isEmpty()) {

            try {

                SimpleDateFormat format =
                        new SimpleDateFormat(
                                "yyyy-MM-dd",
                                Locale.getDefault()
                        );

                calendar.setTime(format.parse(draft.getReminderDate()));

            } catch (Exception ignored) {
            }

        }

        datePicker.updateDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        updatePreview();

    }

    private void initListener() {

        txtBack.setOnClickListener(v -> finish());

        txtDone.setOnClickListener(v -> {

            SimpleDateFormat dbFormat =
                    new SimpleDateFormat(
                            "yyyy-MM-dd",
                            Locale.getDefault()
                    );

            draft.setReminderDate(
                    dbFormat.format(calendar.getTime())
            );

            DraftManager.saveDraft(draft);

            finish();

        });

        datePicker.setOnDateChangedListener(

                (view, year, month, dayOfMonth) -> {

                    calendar.set(year, month, dayOfMonth);

                    updatePreview();

                }

        );

    }

    private void updatePreview() {

        SimpleDateFormat displayFormat =
                new SimpleDateFormat(
                        "dd MMMM yyyy",
                        new Locale("id", "ID")
                );

        txtSelectedDate.setText(
                displayFormat.format(calendar.getTime())
        );

    }

}
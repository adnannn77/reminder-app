package com.reminder.app.activity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.reminder.app.R;
import com.reminder.app.model.ReminderDraft;
import com.reminder.app.utils.DraftManager;

public class ReminderBeforeActivity extends AppCompatActivity {

    private TextView txtBack;
    private TextView txtDone;

    private RadioGroup radioGroup;

    private RadioButton rb0;
    private RadioButton rb5;
    private RadioButton rb15;
    private RadioButton rb30;
    private RadioButton rb60;
    private RadioButton rb1440;

    private ReminderDraft draft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_reminder_before);

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

        loadData();

        initListener();

    }

    private void initView() {

        txtBack = findViewById(R.id.txtBack);
        txtDone = findViewById(R.id.txtDone);

        radioGroup = findViewById(R.id.radioGroup);

        rb0 = findViewById(R.id.rb0);
        rb5 = findViewById(R.id.rb5);
        rb15 = findViewById(R.id.rb15);
        rb30 = findViewById(R.id.rb30);
        rb60 = findViewById(R.id.rb60);
        rb1440 = findViewById(R.id.rb1440);

    }

    private void loadData() {

        String before = draft.getReminderBefore();

        switch (before) {

            case "5 Menit Sebelumnya":
                rb5.setChecked(true);
                break;

            case "15 Menit Sebelumnya":
                rb15.setChecked(true);
                break;

            case "30 Menit Sebelumnya":
                rb30.setChecked(true);
                break;

            case "1 Jam Sebelumnya":
                rb60.setChecked(true);
                break;

            case "1 Hari Sebelumnya":
                rb1440.setChecked(true);
                break;

            default:
                rb0.setChecked(true);
                break;

        }

    }

    private void initListener() {

        txtBack.setOnClickListener(v -> finish());

        txtDone.setOnClickListener(v -> {

            saveReminderBefore();

            finish();

        });

    }

    private void saveReminderBefore() {

        String value = "Saat Waktu Tiba";

        int checkedId = radioGroup.getCheckedRadioButtonId();

        if (checkedId == R.id.rb5) {

            value = "5 Menit Sebelumnya";

        } else if (checkedId == R.id.rb15) {

            value = "15 Menit Sebelumnya";

        } else if (checkedId == R.id.rb30) {

            value = "30 Menit Sebelumnya";

        } else if (checkedId == R.id.rb60) {

            value = "1 Jam Sebelumnya";

        } else if (checkedId == R.id.rb1440) {

            value = "1 Hari Sebelumnya";

        }

        draft.setReminderBefore(value);

        DraftManager.saveDraft(draft);

    }

}
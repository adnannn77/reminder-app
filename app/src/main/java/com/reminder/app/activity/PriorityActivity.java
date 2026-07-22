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

public class PriorityActivity extends AppCompatActivity {

    private TextView txtBack;

    private RadioGroup radioPriority;

    private RadioButton rbLow;
    private RadioButton rbNormal;
    private RadioButton rbHigh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_priority);

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

        initView();

        loadData();

        initListener();

    }

    private void initView() {

        txtBack = findViewById(R.id.txtBack);

        radioPriority = findViewById(R.id.radioPriority);

        rbLow = findViewById(R.id.rbLow);

        rbNormal = findViewById(R.id.rbNormal);

        rbHigh = findViewById(R.id.rbHigh);

    }

    private void loadData() {

        ReminderDraft draft =
                DraftManager.getDraft();

        if (draft == null) {

            return;

        }

        switch (draft.getPriority()) {

            case "low":

                rbLow.setChecked(true);

                break;

            case "high":

                rbHigh.setChecked(true);

                break;

            default:

                rbNormal.setChecked(true);

                break;

        }

    }

    private void initListener() {

        txtBack.setOnClickListener(v -> finish());

        radioPriority.setOnCheckedChangeListener((group, checkedId) -> {

            ReminderDraft draft =
                    DraftManager.getDraft();

            if (draft == null) {

                draft = new ReminderDraft();

            }

            if (checkedId == R.id.rbLow) {

                draft.setPriority("low");

            } else if (checkedId == R.id.rbNormal) {

                draft.setPriority("normal");

            } else if (checkedId == R.id.rbHigh) {

                draft.setPriority("high");

            }

            DraftManager.saveDraft(draft);

        });

    }

}
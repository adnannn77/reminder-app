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

public class ColorActivity extends AppCompatActivity {

    private TextView txtBack;
    private TextView txtDone;

    private RadioGroup radioGroup;

    private RadioButton rbBlue;
    private RadioButton rbGreen;
    private RadioButton rbYellow;
    private RadioButton rbOrange;
    private RadioButton rbRed;
    private RadioButton rbPurple;
    private RadioButton rbGray;

    private ReminderDraft draft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_color);

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

        rbBlue = findViewById(R.id.rbBlue);
        rbGreen = findViewById(R.id.rbGreen);
        rbYellow = findViewById(R.id.rbYellow);
        rbOrange = findViewById(R.id.rbOrange);
        rbRed = findViewById(R.id.rbRed);
        rbPurple = findViewById(R.id.rbPurple);
        rbGray = findViewById(R.id.rbGray);

    }

    private void loadData() {

        String color = draft.getColor();

        switch (color) {

            case "#34C759":
                rbGreen.setChecked(true);
                break;

            case "#FFD60A":
                rbYellow.setChecked(true);
                break;

            case "#FF9500":
                rbOrange.setChecked(true);
                break;

            case "#FF3B30":
                rbRed.setChecked(true);
                break;

            case "#AF52DE":
                rbPurple.setChecked(true);
                break;

            case "#8E8E93":
                rbGray.setChecked(true);
                break;

            default:
                rbBlue.setChecked(true);
                break;

        }

    }

    private void initListener() {

        txtBack.setOnClickListener(v -> finish());

        txtDone.setOnClickListener(v -> {

            saveColor();

            finish();

        });

    }

    private void saveColor() {

        String color = "#007AFF";

        int checkedId = radioGroup.getCheckedRadioButtonId();

        if (checkedId == R.id.rbGreen) {

            color = "#34C759";

        } else if (checkedId == R.id.rbYellow) {

            color = "#FFD60A";

        } else if (checkedId == R.id.rbOrange) {

            color = "#FF9500";

        } else if (checkedId == R.id.rbRed) {

            color = "#FF3B30";

        } else if (checkedId == R.id.rbPurple) {

            color = "#AF52DE";

        } else if (checkedId == R.id.rbGray) {

            color = "#8E8E93";

        }

        draft.setColor(color);

        DraftManager.saveDraft(draft);

    }

}
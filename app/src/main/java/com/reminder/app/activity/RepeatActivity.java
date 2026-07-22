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

public class RepeatActivity extends AppCompatActivity {

    private TextView txtBack;
    private TextView txtDone;

    private RadioGroup radioGroup;

    private RadioButton rbNever;
    private RadioButton rbDaily;
    private RadioButton rbWeekly;
    private RadioButton rbMonthly;
    private RadioButton rbYearly;

    private ReminderDraft draft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_repeat);

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

        rbNever = findViewById(R.id.rbNever);
        rbDaily = findViewById(R.id.rbDaily);
        rbWeekly = findViewById(R.id.rbWeekly);
        rbMonthly = findViewById(R.id.rbMonthly);
        rbYearly = findViewById(R.id.rbYearly);

    }

    private void loadData() {

        String repeat = draft.getRepeat();

        if (repeat == null) {
            repeat = "Tidak Pernah";
        }

        switch (repeat) {

            case "Setiap Hari":
                rbDaily.setChecked(true);
                break;

            case "Setiap Minggu":
                rbWeekly.setChecked(true);
                break;

            case "Setiap Bulan":
                rbMonthly.setChecked(true);
                break;

            case "Setiap Tahun":
                rbYearly.setChecked(true);
                break;

            default:
                rbNever.setChecked(true);
                break;

        }

    }

    private void initListener() {

        txtBack.setOnClickListener(v -> finish());

        txtDone.setOnClickListener(v -> {

            saveRepeat();

            finish();

        });

    }

    private void saveRepeat() {

        String repeat = "Tidak Pernah";

        int checkedId = radioGroup.getCheckedRadioButtonId();

        if (checkedId == R.id.rbDaily) {

            repeat = "Setiap Hari";

        } else if (checkedId == R.id.rbWeekly) {

            repeat = "Setiap Minggu";

        } else if (checkedId == R.id.rbMonthly) {

            repeat = "Setiap Bulan";

        } else if (checkedId == R.id.rbYearly) {

            repeat = "Setiap Tahun";

        }

        draft.setRepeat(repeat);

        DraftManager.saveDraft(draft);

    }

}
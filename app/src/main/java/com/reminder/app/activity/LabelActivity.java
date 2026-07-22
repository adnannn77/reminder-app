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

public class LabelActivity extends AppCompatActivity {

    private TextView txtBack;
    private TextView txtDone;

    private RadioGroup radioGroup;

    private RadioButton rbReminder;
    private RadioButton rbWork;
    private RadioButton rbStudy;
    private RadioButton rbPersonal;

    private ReminderDraft draft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_label);

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

        rbReminder = findViewById(R.id.rbReminder);
        rbWork = findViewById(R.id.rbWork);
        rbStudy = findViewById(R.id.rbStudy);
        rbPersonal = findViewById(R.id.rbPersonal);

    }

    private void loadData() {

        String label = draft.getLabel();

        if (label == null) {
            label = "Pengingat";
        }

        switch (label) {

            case "Pekerjaan":
                rbWork.setChecked(true);
                break;

            case "Kuliah":
                rbStudy.setChecked(true);
                break;

            case "Pribadi":
                rbPersonal.setChecked(true);
                break;

            default:
                rbReminder.setChecked(true);
                break;

        }

    }

    private void initListener() {

        txtBack.setOnClickListener(v -> finish());

        txtDone.setOnClickListener(v -> {

            saveLabel();

            finish();

        });

    }

    private void saveLabel() {

        String label = "Pengingat";

        int checkedId = radioGroup.getCheckedRadioButtonId();

        if (checkedId == R.id.rbWork) {

            label = "Pekerjaan";

        } else if (checkedId == R.id.rbStudy) {

            label = "Kuliah";

        } else if (checkedId == R.id.rbPersonal) {

            label = "Pribadi";

        }

        draft.setLabel(label);

        DraftManager.saveDraft(draft);

    }

}
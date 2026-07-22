package com.reminder.app.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.reminder.app.R;
import com.reminder.app.model.ReminderDraft;
import com.reminder.app.utils.DraftManager;

public class UrlActivity extends AppCompatActivity {

    private TextView txtBack;
    private TextView txtDone;

    private EditText edtUrl;

    private ReminderDraft draft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_url);

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

        initView();

        loadData();

        initListener();

    }

    private void initView() {

        txtBack = findViewById(R.id.txtBack);
        txtDone = findViewById(R.id.txtDone);

        edtUrl = findViewById(R.id.edtUrl);

    }

    private void loadData() {

        edtUrl.setText(
                draft.getUrl()
        );

    }

    private void initListener() {

        txtBack.setOnClickListener(v -> finish());

        txtDone.setOnClickListener(v -> {

            draft.setUrl(
                    edtUrl.getText().toString().trim()
            );

            DraftManager.saveDraft(draft);

            finish();

        });

    }

}
package com.reminder.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.reminder.app.R;
import com.reminder.app.model.ReminderDraft;
import com.reminder.app.utils.DraftManager;

public class DetailActivity extends AppCompatActivity {

    private TextView txtBack;
    private TextView txtDone;

    private TextView txtDate;
    private TextView txtTime;

    private TextView txtLocation;
    private TextView txtUrl;

    private TextView txtPriority;
    private TextView txtRepeat;
    private TextView txtLabel;
    private TextView txtReminderBefore;

    private View viewColor;

    private LinearLayout layoutDate;
    private LinearLayout layoutTime;
    private LinearLayout layoutLocation;
    private LinearLayout layoutUrl;

    private LinearLayout layoutPriority;
    private LinearLayout layoutRepeat;
    private LinearLayout layoutLabel;
    private LinearLayout layoutReminderBefore;
    private LinearLayout layoutColor;

    private ReminderDraft draft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_detail);

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

        loadDraft();

        initListener();

    }

    @Override
    protected void onResume() {

        super.onResume();

        draft = DraftManager.getDraft();

        loadDraft();

    }

    private void initView() {

        txtBack = findViewById(R.id.txtBack);
        txtDone = findViewById(R.id.txtDone);

        txtDate = findViewById(R.id.txtDate);
        txtTime = findViewById(R.id.txtTime);

        txtLocation = findViewById(R.id.txtLocation);
        txtUrl = findViewById(R.id.txtUrl);

        txtPriority = findViewById(R.id.txtPriority);
        txtRepeat = findViewById(R.id.txtRepeat);
        txtLabel = findViewById(R.id.txtLabel);
        txtReminderBefore = findViewById(R.id.txtReminderBefore);

        viewColor = findViewById(R.id.viewColor);

        layoutDate = findViewById(R.id.layoutDate);
        layoutTime = findViewById(R.id.layoutTime);

        layoutLocation = findViewById(R.id.layoutLocation);
        layoutUrl = findViewById(R.id.layoutUrl);

        layoutPriority = findViewById(R.id.layoutPriority);
        layoutRepeat = findViewById(R.id.layoutRepeat);
        layoutLabel = findViewById(R.id.layoutLabel);
        layoutReminderBefore = findViewById(R.id.layoutReminderBefore);
        layoutColor = findViewById(R.id.layoutColor);

    }

    private void loadDraft() {

        txtDate.setText(
                draft.getReminderDate().isEmpty()
                        ? "Tidak Ada"
                        : draft.getReminderDate()
        );

        txtTime.setText(
                draft.getReminderTime().isEmpty()
                        ? "Tidak Ada"
                        : draft.getReminderTime()
        );

        txtLocation.setText(
                draft.getLocationName().isEmpty()
                        ? "Tidak Ada"
                        : draft.getLocationName()
        );

        txtUrl.setText(
                draft.getUrl().isEmpty()
                        ? "Tidak Ada"
                        : draft.getUrl()
        );

        txtPriority.setText(draft.getPriority());

        txtRepeat.setText(draft.getRepeat());

        txtLabel.setText(draft.getLabel());

        txtReminderBefore.setText(
                draft.getReminderBefore()
        );

        try {

            viewColor.setBackgroundColor(
                    Color.parseColor(draft.getColor())
            );

        } catch (Exception ignored) {

        }

    }

    private void initListener() {

        txtBack.setOnClickListener(v -> finish());

        txtDone.setOnClickListener(v -> finish());

        layoutDate.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                DateActivity.class
                        )
                )
        );

        layoutTime.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                TimeActivity.class
                        )
                )
        );

        layoutLocation.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                LocationActivity.class
                        )
                )
        );

        layoutPriority.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                PriorityActivity.class
                        )
                )
        );

        layoutRepeat.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                RepeatActivity.class
                        )
                )
        );

        layoutLabel.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                LabelActivity.class
                        )
                )
        );

        layoutReminderBefore.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                ReminderBeforeActivity.class
                        )
                )
        );

        layoutColor.setOnClickListener(v ->
                startActivity(
                        new Intent(
                                this,
                                ColorActivity.class
                        )
                )
        );

        layoutUrl.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                this,
                                UrlActivity.class
                        )
                )

        );

    }

}
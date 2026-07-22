package com.reminder.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.reminder.app.R;
import com.reminder.app.api.Api;
import com.reminder.app.api.VolleySingleton;
import com.reminder.app.session.SessionManager;

import java.util.HashMap;
import java.util.Map;

public class AddReminderActivity extends AppCompatActivity {

    private TextView txtCancel;
    private TextView txtSave;
    private TextView txtDelete;
    private EditText edtTitle;
    private EditText edtNote;

    private LinearLayout layoutDetail;
    private LinearLayout layoutList;
    private com.google.android.material.card.MaterialCardView cardDelete;
    private LinearLayout layoutDelete;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_add_reminder);

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

        sessionManager = new SessionManager(this);

        initView();

        loadDraft();

        initListener();

    }

    private void initView() {

        txtCancel = findViewById(R.id.txtCancel);
        txtSave = findViewById(R.id.txtSave);

        txtDelete = findViewById(R.id.txtDelete);

        edtTitle = findViewById(R.id.edtTitle);
        edtNote = findViewById(R.id.edtNote);

        layoutDetail = findViewById(R.id.layoutDetail);
        layoutList = findViewById(R.id.layoutList);
        layoutDelete = findViewById(R.id.layoutDelete);
        cardDelete = findViewById(R.id.cardDelete);

    }


    private void loadDraft() {

        com.reminder.app.model.ReminderDraft draft =
                com.reminder.app.utils.DraftManager.getDraft();

        edtTitle.setText(draft.getTitle());
        edtNote.setText(draft.getNote());

        if (draft.isEditMode()) {

            txtSave.setText("Simpan");

            ((TextView) findViewById(R.id.txtTitleBar))
                    .setText("Edit Pengingat");

            cardDelete.setVisibility(View.VISIBLE);

        } else {

            txtSave.setText("Tambah");

            ((TextView) findViewById(R.id.txtTitleBar))
                    .setText("Pengingat Baru");

            cardDelete.setVisibility(View.GONE);

        }

    }

    private void initListener() {

        txtCancel.setOnClickListener(v -> showCancelDialog());

        txtSave.setOnClickListener(v -> saveReminder());

        layoutDetail.setOnClickListener(v -> {

            saveDraft();

            startActivity(
                    new android.content.Intent(
                            AddReminderActivity.this,
                            DetailActivity.class
                    )
            );

        });

        layoutList.setOnClickListener(v -> {

            saveDraft();

            startActivity(
                    new Intent(
                            this,
                            LabelActivity.class
                    )
            );

        });

        layoutDelete.setOnClickListener(v -> {

            new AlertDialog.Builder(this)

                    .setTitle("Hapus Reminder?")

                    .setMessage("Reminder akan dihapus permanen.")

                    .setNegativeButton(
                            "Batal",
                            null
                    )

                    .setPositiveButton(
                            "Hapus",
                            (dialog, which) -> deleteReminder()
                    )

                    .show();

        });

    }


    private void saveDraft() {

        com.reminder.app.model.ReminderDraft draft =
                com.reminder.app.utils.DraftManager.getDraft();

        draft.setTitle(
                edtTitle.getText().toString().trim()
        );

        draft.setNote(
                edtNote.getText().toString().trim()
        );

        com.reminder.app.utils.DraftManager.saveDraft(draft);

    }

    private void showCancelDialog() {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        builder.setTitle("Buang Perubahan?");

        builder.setMessage(
                "Jika Anda kembali sekarang, perubahan yang belum disimpan akan hilang."
        );

        builder.setNegativeButton(
                "Lanjut Mengedit",
                (dialog, which) -> dialog.dismiss()
        );

        builder.setPositiveButton(
                "Buang Perubahan",
                (dialog, which) -> finish()
        );

        builder.show();

    }

    private void saveReminder() {

        String title = edtTitle.getText().toString().trim();
        String note = edtNote.getText().toString().trim();

        com.reminder.app.model.ReminderDraft draft =
                com.reminder.app.utils.DraftManager.getDraft();

        if (title.isEmpty()) {

            Toast.makeText(
                    this,
                    "Judul pengingat wajib diisi",
                    Toast.LENGTH_SHORT
            ).show();

            return;

        }

        int method;

        String url;

        if (draft.isEditMode()) {

            method = Request.Method.PUT;

            url = Api.REMINDER + "/" + draft.getId();

        } else {

            method = Request.Method.POST;

            url = Api.REMINDER;

        }

        StringRequest request =
                new StringRequest(

                        method,

                        url,

                        response -> {

                            Toast.makeText(
                                    this,
                                    draft.isEditMode()
                                            ? "Reminder berhasil diperbarui"
                                            : "Reminder berhasil ditambahkan",
                                    Toast.LENGTH_SHORT
                            ).show();

                            finish();

                        },

                        error ->

                                Toast.makeText(
                                        this,
                                        "Gagal menambahkan reminder",
                                        Toast.LENGTH_SHORT
                                ).show()

                ) {

                    @Override
                    protected Map<String, String> getParams() {

                        Map<String, String> params = new HashMap<>();

                        params.put(
                                "user_id",
                                String.valueOf(sessionManager.getId())
                        );

                        params.put("label_id", "3");
                        params.put("repeat_type_id", "1");

                        params.put("title", title);
                        params.put("note", note);

                        params.put("url", "");

                        params.put(
                                "reminder_date",
                                draft.getReminderDate()
                        );

                        params.put(
                                "reminder_time",
                                draft.getReminderTime()
                        );

                        params.put(
                                "location_name",
                                draft.getLocationName()
                        );

                        params.put(
                                "latitude",
                                String.valueOf(draft.getLatitude())
                        );

                        params.put(
                                "longitude",
                                String.valueOf(draft.getLongitude())
                        );

                        params.put(
                                "priority",
                                draft.getPriority()
                        );

                        params.put(
                                "reminder_before",
                                String.valueOf(draft.getReminderBefore())
                        );

                        params.put(
                                "color",
                                draft.getColor()
                        );

                        android.util.Log.d("SAVE_REMINDER", "==========================");
                        android.util.Log.d("SAVE_REMINDER", "Title = " + title);
                        android.util.Log.d("SAVE_REMINDER", "Date = " + draft.getReminderDate());
                        android.util.Log.d("SAVE_REMINDER", "Time = " + draft.getReminderTime());
                        android.util.Log.d("SAVE_REMINDER", "Location = " + draft.getLocationName());
                        android.util.Log.d("SAVE_REMINDER", "Latitude = " + draft.getLatitude());
                        android.util.Log.d("SAVE_REMINDER", "Longitude = " + draft.getLongitude());
                        android.util.Log.d("SAVE_REMINDER", "Priority = " + draft.getPriority());
                        android.util.Log.d("SAVE_REMINDER", "Reminder Before = " + draft.getReminderBefore());
                        android.util.Log.d("SAVE_REMINDER", "Color = " + draft.getColor());
                        android.util.Log.d("SAVE_REMINDER", "==========================");


                        return params;

                    }

                };

        VolleySingleton
                .getInstance(this)
                .addToRequestQueue(request);

    }

    private void deleteReminder() {

        com.reminder.app.model.ReminderDraft draft =
                com.reminder.app.utils.DraftManager.getDraft();

        StringRequest request =
                new StringRequest(

                        Request.Method.DELETE,

                        Api.REMINDER + "/" + draft.getId(),

                        response -> {

                            Toast.makeText(
                                    this,
                                    "Reminder berhasil dihapus",
                                    Toast.LENGTH_SHORT
                            ).show();

                            com.reminder.app.utils.DraftManager.resetDraft();

                            finish();

                        },

                        error ->

                                Toast.makeText(
                                        this,
                                        "Gagal menghapus reminder",
                                        Toast.LENGTH_SHORT
                                ).show()

                );

        VolleySingleton
                .getInstance(this)
                .addToRequestQueue(request);

    }

    @Override
    public void onBackPressed() {

        showCancelDialog();

    }

}
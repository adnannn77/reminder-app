package com.reminder.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.content.Intent;
import com.reminder.app.utils.DraftManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.reminder.app.R;
import com.reminder.app.adapter.ReminderAdapter;
import com.reminder.app.api.Api;
import com.reminder.app.api.VolleySingleton;
import com.reminder.app.model.Reminder;
import com.reminder.app.session.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReminderListActivity extends AppCompatActivity {

    private TextView txtBack;
    private TextView txtTotal;

    private RecyclerView recyclerReminder;

    private SearchView searchReminder;

    private LinearLayout layoutEmpty;

    private ReminderAdapter adapter;

    private final List<Reminder> reminderList = new ArrayList<>();

    private final List<Reminder> allReminderList = new ArrayList<>();

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_reminder_list);

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

        initRecycler();

        initListener();

        loadReminder();

    }

    private void initView() {

        txtBack = findViewById(R.id.txtBack);
        txtTotal = findViewById(R.id.txtTotal);

        recyclerReminder = findViewById(R.id.recyclerReminder);

        searchReminder = findViewById(R.id.searchReminder);

        layoutEmpty = findViewById(R.id.layoutEmpty);

    }

    private void initRecycler() {

        adapter = new ReminderAdapter(

                reminderList,

                new ReminderAdapter.OnReminderClickListener() {

                    @Override
                    public void onReminderClick(Reminder reminder) {

                        com.reminder.app.model.ReminderDraft draft =
                                DraftManager.getDraft();

                        draft.setId(reminder.getId());

                        draft.setEditMode(true);

                        draft.setTitle(reminder.getTitle());

                        draft.setNote(reminder.getNote());

                        draft.setReminderDate(
                                reminder.getReminderDate()
                        );

                        draft.setReminderTime(
                                reminder.getReminderTime()
                        );

                        draft.setColor(
                                reminder.getColor()
                        );

                        DraftManager.saveDraft(draft);

                        startActivity(
                                new Intent(
                                        ReminderListActivity.this,
                                        AddReminderActivity.class
                                )
                        );

                    }

                    @Override
                    public void onCompleteClick(Reminder reminder) {

                        completeReminder(reminder);

                    }

                }

        );

        recyclerReminder.setLayoutManager(
                new LinearLayoutManager(this)
        );

        recyclerReminder.setAdapter(adapter);

    }

    private void initListener() {

        txtBack.setOnClickListener(v -> finish());

        searchReminder.setOnQueryTextListener(

                new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String query) {

                        filterReminder(query);

                        return true;

                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {

                        filterReminder(newText);

                        return true;

                    }

                }

        );

    }

    private void loadReminder() {

        String url =
                Api.REMINDER_BY_USER + sessionManager.getId();

        StringRequest request =
                new StringRequest(

                        Request.Method.GET,

                        url,

                        response -> {

                            try {

                                reminderList.clear();
                                allReminderList.clear();

                                JSONObject object =
                                        new JSONObject(response);

                                JSONArray data =
                                        object.getJSONArray("data");

                                txtTotal.setText(
                                        data.length() + " Pengingat"
                                );

                                for (int i = 0; i < data.length(); i++) {

                                    JSONObject item =
                                            data.getJSONObject(i);

                                    android.util.Log.d(
                                            "REMINDER_JSON",
                                            item.toString()
                                    );

                                    Reminder reminder =
                                            new Reminder();

                                    reminder.setId(
                                            item.getInt("id")
                                    );

                                    reminder.setUserId(
                                            item.getInt("user_id")
                                    );

                                    reminder.setTitle(
                                            item.getString("title")
                                    );

                                    reminder.setNote(
                                            item.optString("note", "")
                                    );

                                    reminder.setReminderDate(
                                            item.optString("reminder_date", "")
                                    );

                                    reminder.setReminderTime(
                                            item.optString("reminder_time", "")
                                    );

                                    reminder.setColor(
                                            item.optString("color", "#007AFF")
                                    );

                                    reminder.setLatitude(
                                            item.optString("latitude", "")
                                    );

                                    reminder.setLongitude(
                                            item.optString("longitude", "")
                                    );

                                    reminder.setCompleted(
                                            item.optInt("is_completed", 0) == 1
                                    );

                                    reminderList.add(reminder);
                                    allReminderList.add(reminder);

                                }

                                adapter.notifyDataSetChanged();

                                filterReminder(searchReminder.getQuery().toString());

                                if (reminderList.isEmpty()) {

                                    layoutEmpty.setVisibility(View.VISIBLE);

                                    recyclerReminder.setVisibility(View.GONE);

                                } else {

                                    layoutEmpty.setVisibility(View.GONE);

                                    recyclerReminder.setVisibility(View.VISIBLE);

                                }

                            } catch (Exception e) {

                                e.printStackTrace();

                            }

                        },

                        error -> {

                            txtTotal.setText("0 Pengingat");

                            layoutEmpty.setVisibility(View.VISIBLE);

                            recyclerReminder.setVisibility(View.GONE);

                        }

                );

        VolleySingleton
                .getInstance(this)
                .addToRequestQueue(request);

    }

    private void completeReminder(Reminder reminder) {

        StringRequest request =
                new StringRequest(

                        Request.Method.PATCH,

                        Api.REMINDER + "/"
                                + reminder.getId()
                                + "/complete",

                        response -> {

                            android.widget.Toast.makeText(
                                    this,
                                    "Reminder selesai",
                                    android.widget.Toast.LENGTH_SHORT
                            ).show();

                            loadReminder();

                        },

                        error ->

                                android.widget.Toast.makeText(
                                        this,
                                        "Gagal menyelesaikan reminder",
                                        android.widget.Toast.LENGTH_SHORT
                                ).show()

                );

        VolleySingleton
                .getInstance(this)
                .addToRequestQueue(request);

    }

    private void filterReminder(String keyword) {

        reminderList.clear();

        if (keyword == null || keyword.trim().isEmpty()) {

            reminderList.addAll(allReminderList);

        } else {

            keyword = keyword.toLowerCase();

            for (Reminder reminder : allReminderList) {

                boolean cocokJudul =
                        reminder.getTitle() != null &&
                                reminder.getTitle()
                                        .toLowerCase()
                                        .contains(keyword);

                boolean cocokCatatan =
                        reminder.getNote() != null &&
                                reminder.getNote()
                                        .toLowerCase()
                                        .contains(keyword);

                if (cocokJudul || cocokCatatan) {

                    reminderList.add(reminder);

                }

            }

        }

        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {

        super.onResume();

        loadReminder();

    }

}
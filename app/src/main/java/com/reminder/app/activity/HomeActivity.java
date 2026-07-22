package com.reminder.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.LinearLayout;

import com.reminder.app.adapter.ReminderAdapter;
import com.reminder.app.model.Reminder;
import com.reminder.app.utils.DraftManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.reminder.app.R;
import com.reminder.app.api.Api;
import com.reminder.app.api.VolleySingleton;
import com.reminder.app.session.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {

    private SessionManager sessionManager;

    private TextView txtGreeting;
    private TextView txtUserName;

    private TextView txtTodayCount;
    private TextView txtScheduleCount;
    private TextView txtAllCount;
    private TextView txtCompletedCount;

    private TextView txtReminderTotal;

    private CardView cardReminderList;

    private FloatingActionButton fabAddReminder;

    private SearchView searchReminder;

    private RecyclerView recyclerSearch;

    private LinearLayout layoutSearchEmpty;

    private ReminderAdapter searchAdapter;

    private final List<Reminder> reminderList = new ArrayList<>();
    private final List<Reminder> filteredList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_home);

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

        sessionManager = new SessionManager(this);

        initView();

        initListener();

        initSearch();

        loadUser();

        loadReminderStatistic();

    }

    private void initView() {

        txtGreeting = findViewById(R.id.txtGreeting);

        txtUserName = findViewById(R.id.txtUserName);

        txtTodayCount = findViewById(R.id.txtTodayCount);

        txtScheduleCount = findViewById(R.id.txtScheduleCount);

        txtAllCount = findViewById(R.id.txtAllCount);

        txtCompletedCount = findViewById(R.id.txtCompletedCount);

        txtReminderTotal = findViewById(R.id.txtReminderTotal);

        cardReminderList = findViewById(R.id.cardReminderList);

        fabAddReminder = findViewById(R.id.fabAddReminder);

        searchReminder = findViewById(R.id.searchReminder);

        recyclerSearch = findViewById(R.id.recyclerSearch);

        layoutSearchEmpty = findViewById(R.id.layoutSearchEmpty);

    }

    private void initListener() {

        fabAddReminder.setOnClickListener(v -> {

            DraftManager.resetDraft();

            startActivity(
                    new Intent(
                            HomeActivity.this,
                            AddReminderActivity.class
                    )
            );

        });

        cardReminderList.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            HomeActivity.this,
                            ReminderListActivity.class
                    )
            );

        });

    }

    private void initSearch() {

        searchAdapter = new ReminderAdapter(

                filteredList,

                new ReminderAdapter.OnReminderClickListener() {

                    @Override
                    public void onReminderClick(Reminder reminder) {

                        startActivity(
                                new Intent(
                                        HomeActivity.this,
                                        ReminderListActivity.class
                                )
                        );

                    }

                    @Override
                    public void onCompleteClick(Reminder reminder) {

                    }

                }

        );

        recyclerSearch.setLayoutManager(
                new androidx.recyclerview.widget.LinearLayoutManager(this)
        );

        recyclerSearch.setAdapter(searchAdapter);

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

    private void loadUser() {

        txtGreeting.setText("Selamat Datang");

        txtUserName.setText(sessionManager.getNama());

    }

    private void loadReminderStatistic() {

        String url =
                Api.REMINDER_BY_USER + sessionManager.getId();

        StringRequest request =
                new StringRequest(

                        Request.Method.GET,

                        url,

                        response -> {

                            try {

                                JSONObject object =
                                        new JSONObject(response);

                                JSONArray data =
                                        object.getJSONArray("data");

                                int total = data.length();

                                int today = 0;
                                int scheduled = 0;
                                int completed = 0;

                                String todayDate =
                                        new java.text.SimpleDateFormat(
                                                "yyyy-MM-dd",
                                                java.util.Locale.getDefault()
                                        ).format(new java.util.Date());

                                reminderList.clear();

                                for (int i = 0; i < data.length(); i++) {

                                    JSONObject item =
                                            data.getJSONObject(i);

                                    Reminder reminder = new Reminder();

                                    reminder.setId(item.getInt("id"));

                                    reminder.setUserId(item.getInt("user_id"));

                                    reminder.setTitle(item.getString("title"));

                                    reminder.setReminderDate(item.getString("reminder_date"));

                                    reminder.setReminderTime(item.getString("reminder_time"));

                                    reminder.setNote(item.optString("note", ""));

                                    reminder.setColor(item.optString("color", "#007AFF"));

                                    reminder.setLatitude(item.optString("latitude", "0"));

                                    reminder.setLongitude(item.optString("longitude", "0"));

                                    reminder.setCompleted(item.getInt("is_completed") == 1);

                                    reminderList.add(reminder);

                                    String reminderDate =
                                            item.getString("reminder_date");

                                    boolean isCompleted =
                                            item.getInt("is_completed") == 1;

                                    if (todayDate.equals(reminderDate)) {

                                        today++;

                                    }

                                    if (isCompleted) {

                                        completed++;

                                    } else {

                                        scheduled++;

                                    }

                                }

                                txtTodayCount.setText(String.valueOf(today));

                                txtScheduleCount.setText(String.valueOf(scheduled));

                                txtAllCount.setText(String.valueOf(total));

                                txtCompletedCount.setText(String.valueOf(completed));

                                txtReminderTotal.setText(String.valueOf(total));

                            } catch (Exception e) {

                                e.printStackTrace();

                            }

                        },

                        error -> {

                            txtTodayCount.setText("0");

                            txtScheduleCount.setText("0");

                            txtAllCount.setText("0");

                            txtCompletedCount.setText("0");

                            txtReminderTotal.setText("0");

                        }

                );

        VolleySingleton
                .getInstance(this)
                .addToRequestQueue(request);

    }

    private void filterReminder(String keyword) {

        filteredList.clear();

        if (keyword.trim().isEmpty()) {

            recyclerSearch.setVisibility(View.GONE);

            layoutSearchEmpty.setVisibility(View.GONE);

            return;

        }

        for (Reminder reminder : reminderList) {

            if (reminder.getTitle().toLowerCase()
                    .contains(keyword.toLowerCase())) {

                filteredList.add(reminder);

            }

        }

        searchAdapter.notifyDataSetChanged();

        if (filteredList.isEmpty()) {

            recyclerSearch.setVisibility(View.GONE);

            layoutSearchEmpty.setVisibility(View.VISIBLE);

        } else {

            recyclerSearch.setVisibility(View.VISIBLE);

            layoutSearchEmpty.setVisibility(View.GONE);

        }

    }

    @Override
    protected void onResume() {

        super.onResume();

        loadReminderStatistic();

    }

}
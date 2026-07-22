package com.reminder.app.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.reminder.app.R;
import com.reminder.app.api.Api;
import com.reminder.app.api.VolleySingleton;
import com.reminder.app.model.Reminder;

import org.json.JSONObject;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ViewHolder> {

    private final List<Reminder> reminderList;
    private final OnReminderClickListener listener;

    public interface OnReminderClickListener {

        void onReminderClick(Reminder reminder);

        void onCompleteClick(Reminder reminder);

    }

    public ReminderAdapter(
            List<Reminder> reminderList,
            OnReminderClickListener listener
    ) {

        this.reminderList = reminderList;
        this.listener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reminder, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {

        Reminder reminder = reminderList.get(position);

        holder.txtTitle.setText(reminder.getTitle());

        holder.txtDate.setText(
                reminder.getReminderDate()
                        + " • "
                        + reminder.getReminderTime()
        );

        holder.txtNote.setText(reminder.getNote());

        try {

            holder.colorIndicator.setBackgroundColor(
                    Color.parseColor(reminder.getColor())
            );

        } catch (Exception e) {

            holder.colorIndicator.setBackgroundColor(Color.GRAY);

        }

        // ==========================
        // WEATHER
        // ==========================

        if (reminder.getLatitude() == null
                || reminder.getLongitude() == null
                || reminder.getLatitude().equals("0.00000000")
                || reminder.getLongitude().equals("0.00000000")
                || reminder.getLatitude().isEmpty()
                || reminder.getLongitude().isEmpty()) {

            holder.txtWeather.setVisibility(View.GONE);

        } else {

            holder.txtWeather.setVisibility(View.VISIBLE);

            String url =
                    Api.WEATHER
                            + "?lat="
                            + reminder.getLatitude()
                            + "&lon="
                            + reminder.getLongitude();

            StringRequest request = new StringRequest(

                    Request.Method.GET,

                    url,

                    response -> {

                        try {

                            JSONObject object =
                                    new JSONObject(response);

                            JSONObject data =
                                    object.getJSONObject("data");

                            int temperature =
                                    (int) data.getDouble("temperature");

                            int code =
                                    data.getInt("weather_code");

                            holder.txtWeather.setText(
                                    getWeatherIcon(code)
                                            + " "
                                            + getWeatherName(code)
                                            + " • "
                                            + temperature
                                            + "°C"
                            );

                        } catch (Exception e) {

                            holder.txtWeather.setVisibility(View.GONE);

                        }

                    },

                    error -> holder.txtWeather.setVisibility(View.GONE)

            );

            VolleySingleton
                    .getInstance(holder.itemView.getContext())
                    .addToRequestQueue(request);

        }

        holder.itemView.setOnClickListener(v -> {

            listener.onReminderClick(reminder);

        });

        holder.txtComplete.setOnClickListener(v -> {

            listener.onCompleteClick(reminder);

        });

    }

    @Override
    public int getItemCount() {

        return reminderList.size();

    }

    private String getWeatherIcon(int code) {

        if (code == 0) return "☀";

        if (code <= 3) return "⛅";

        if (code <= 67) return "🌧";

        if (code <= 77) return "❄";

        if (code <= 99) return "⛈";

        return "🌤";

    }

    private String getWeatherName(int code) {

        if (code == 0) return "Cerah";

        if (code == 1) return "Sebagian Cerah";

        if (code == 2) return "Berawan";

        if (code == 3) return "Mendung";

        if (code >= 45 && code <= 48) return "Berkabut";

        if (code >= 51 && code <= 67) return "Hujan";

        if (code >= 71 && code <= 77) return "Salju";

        if (code >= 80 && code <= 99) return "Badai";

        return "Cuaca";

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View colorIndicator;

        TextView txtComplete;
        TextView txtTitle;
        TextView txtDate;
        TextView txtWeather;
        TextView txtNote;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            colorIndicator =
                    itemView.findViewById(R.id.viewColor);

            txtComplete =
                    itemView.findViewById(R.id.txtComplete);

            txtTitle =
                    itemView.findViewById(R.id.txtTitle);

            txtDate =
                    itemView.findViewById(R.id.txtDate);

            txtWeather =
                    itemView.findViewById(R.id.txtWeather);

            txtNote =
                    itemView.findViewById(R.id.txtNote);

        }

    }

}
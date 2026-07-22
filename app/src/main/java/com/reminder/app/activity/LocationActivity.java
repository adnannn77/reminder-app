package com.reminder.app.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.reminder.app.R;
import com.reminder.app.api.VolleySingleton;
import com.reminder.app.model.ReminderDraft;
import com.reminder.app.utils.DraftManager;
import com.reminder.app.utils.LocationHelper;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_CODE = 100;

    private TextView txtCancel;
    private TextView txtDone;
    private TextView txtLocation;

    private Button btnCurrentLocation;
    private ProgressBar progressBar;

    private double latitude = 0;
    private double longitude = 0;

    private String locationName = "";

    private ReminderDraft draft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_location);

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

    private void initView() {

        txtCancel = findViewById(R.id.txtCancel);
        txtDone = findViewById(R.id.txtDone);
        txtLocation = findViewById(R.id.txtLocation);

        btnCurrentLocation = findViewById(R.id.btnCurrentLocation);

        progressBar = findViewById(R.id.progressBar);

    }

    private void loadDraft() {

        latitude = draft.getLatitude();
        longitude = draft.getLongitude();

        locationName = draft.getLocationName();

        if (!locationName.isEmpty()) {

            txtLocation.setText(locationName);

        } else {

            txtLocation.setText("Belum dipilih");

        }

    }

    private void initListener() {

        txtCancel.setOnClickListener(v -> finish());

        txtDone.setOnClickListener(v -> saveLocation());

        btnCurrentLocation.setOnClickListener(v -> checkPermission());

    }

    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED) {

            getCurrentLocation();

        } else {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    LOCATION_PERMISSION_CODE
            );

        }

    }

    private void getCurrentLocation() {

        progressBar.setVisibility(ProgressBar.VISIBLE);

        btnCurrentLocation.setEnabled(false);

        LocationHelper.getCurrentLocation(
                this,

                new LocationHelper.LocationCallback() {

                    @Override
                    public void onLocationSuccess(Location location) {

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                        getAddressFromApi();

                    }

                    @Override
                    public void onLocationFailed(String message) {

                        progressBar.setVisibility(ProgressBar.GONE);

                        btnCurrentLocation.setEnabled(true);

                        Toast.makeText(
                                LocationActivity.this,
                                message,
                                Toast.LENGTH_SHORT
                        ).show();

                    }

                }

        );

    }

    private void getAddressFromApi() {

        String url =
                "https://nominatim.openstreetmap.org/reverse?format=jsonv2"
                        + "&lat=" + latitude
                        + "&lon=" + longitude;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,

                response -> {

                    progressBar.setVisibility(ProgressBar.GONE);

                    btnCurrentLocation.setEnabled(true);

                    try {

                        locationName = response.getString("display_name");

                        txtLocation.setText(locationName);

                    } catch (Exception e) {

                        getAddressWithGeocoder();

                    }

                },

                error -> {

                    progressBar.setVisibility(ProgressBar.GONE);

                    btnCurrentLocation.setEnabled(true);

                    getAddressWithGeocoder();

                }

        );

        VolleySingleton
                .getInstance(this)
                .addToRequestQueue(request);

    }

    private void getAddressWithGeocoder() {

        try {

            Geocoder geocoder =
                    new Geocoder(this, Locale.getDefault());

            List<Address> list =
                    geocoder.getFromLocation(
                            latitude,
                            longitude,
                            1
                    );

            if (list != null && !list.isEmpty()) {

                locationName =
                        list.get(0).getAddressLine(0);

                txtLocation.setText(locationName);

            } else {

                locationName = latitude + ", " + longitude;

                txtLocation.setText(locationName);

            }

        } catch (IOException e) {

            locationName = latitude + ", " + longitude;

            txtLocation.setText(locationName);

        }

    }

    private void saveLocation() {

        draft.setLatitude(latitude);
        draft.setLongitude(longitude);

        draft.setLocationName(locationName);

        DraftManager.saveDraft(draft);

        finish();

    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {

        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
        );

        if (requestCode == LOCATION_PERMISSION_CODE) {

            if (grantResults.length > 0
                    && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED) {

                getCurrentLocation();

            } else {

                Toast.makeText(
                        this,
                        "Izin lokasi ditolak",
                        Toast.LENGTH_SHORT
                ).show();

            }

        }

    }

}
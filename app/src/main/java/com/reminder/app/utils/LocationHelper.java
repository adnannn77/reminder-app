package com.reminder.app.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;

import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnTokenCanceledListener;

public class LocationHelper {

    public static final int LOCATION_PERMISSION_REQUEST = 1001;

    public interface LocationCallback {
        void onLocationSuccess(Location location);
        void onLocationFailed(String message);
    }

    public static void getCurrentLocation(
            Activity activity,
            LocationCallback callback
    ) {

        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    LOCATION_PERMISSION_REQUEST
            );

            callback.onLocationFailed("Permission belum diberikan");
            return;
        }

        FusedLocationProviderClient fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(activity);

        fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                new CancellationToken() {

                    @NonNull
                    @Override
                    public CancellationToken onCanceledRequested(
                            @NonNull OnTokenCanceledListener listener
                    ) {
                        return this;
                    }

                    @Override
                    public boolean isCancellationRequested() {
                        return false;
                    }

                }

        ).addOnSuccessListener(activity, location -> {

            if (location != null) {

                callback.onLocationSuccess(location);

            } else {

                callback.onLocationFailed(
                        "Lokasi tidak ditemukan"
                );

            }

        }).addOnFailureListener(e ->

                callback.onLocationFailed(
                        e.getMessage()

                )

        );

    }

    public static boolean hasLocationPermission(
            Activity activity
    ) {

        return ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;

    }

    public static void requestPermission(
            Activity activity
    ) {

        ActivityCompat.requestPermissions(
                activity,
                new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                },
                LOCATION_PERMISSION_REQUEST
        );

    }

}
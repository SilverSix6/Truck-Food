package com.example.truck_food.Location;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.example.truck_food.R;
import com.example.truck_food.databinding.ActivityUpdateLocationBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class UpdateLocation extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityUpdateLocationBinding binding;

    // Used to ask user for their location
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        binding = ActivityUpdateLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        // Create a LatLng object for the location of Kelowna, BC
        LatLng kelowna = new LatLng(49.8877, -119.4961);

        // Move the camera to Kelowna and zoom in
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kelowna, 10.0f));
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Asks the user for current location
        currentLocation(new View(this));
        // Set a click listener for the map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                // Clear all markers
                mMap.clear();

                // Add a marker at the clicked location
                mMap.addMarker(new MarkerOptions().position(point));

                // TODO: Save the location to Firebase
            }
        });
    }


    public void mainMenu(View v) {
        // Create an AlertDialog.Builder instance
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Do you want to go back to the main menu?")
                // If the user confirms, finish the activity
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                // If the user cancels, just close the dialog
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void currentLocation(View v) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permissions if they were not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        } else {
            // Get the last known location
            fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(currentLocation));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 10.0f));
                    }
                }
            });
        }
    }


}
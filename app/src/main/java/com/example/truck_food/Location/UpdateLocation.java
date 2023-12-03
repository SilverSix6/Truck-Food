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
import android.widget.Toast;

import com.example.truck_food.Database.DatabaseCompleteListener;
import com.example.truck_food.Login.LoginScreen;
import com.example.truck_food.R;
import com.example.truck_food.User.User;
import com.example.truck_food.User.Vendor;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import com.example.truck_food.Database.Database;

import java.util.HashMap;

public class UpdateLocation extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityUpdateLocationBinding binding;

    // Used to ask user for their location
    private FusedLocationProviderClient fusedLocationClient;

    // LatLng for getting the user's location
    // Doesn't matter if it's a point/click or using current location
    private LatLng userLocation;

    // The Vendors HashMap  used to retrieve the Vendors in Firebase

    // MIGHT DELETE THIS because apparently I can retrieve the log in info from LoginScreen
    private HashMap<String, Vendor> vendors;
    private Vendor loggedInVendor;

    private String vendorId;

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

        // Initialize the user that's logged in
        loggedInVendor = (Vendor) LoginScreen.account;
        vendorId = LoginScreen.accountId;
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

                // Sets user's location based on click/tap
                userLocation = new LatLng(point.latitude, point.longitude);
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
                        // Sets the user's location to the current one
                        userLocation = currentLocation;
                    }
                }
            });
        }
    }

    public void confirm(View v) {
        // Checks if user first enters a valid location (i.e not outside of Kelowna)
        // These values are approximations I got from Google Maps so they may not be too accurate
        // Upon testing, it actually goes up to a little bit of West Kelowna so that's cool.
        LatLng northeast = new LatLng(50.02, -119.374);
        LatLng southwest = new LatLng(49.77, -119.589);

        // Create a LatLngBounds object
        // This is the boundary of Kelowna
        LatLngBounds kelownaBounds = new LatLngBounds(southwest, northeast);

        // If the user IS NOT within Kelowna
        // Set the location somewhere in Downtown and print a toast message
        if (!kelownaBounds.contains(userLocation)) {
            // Again these are rough estimations so it's somewhere in Downtown Kelowna
            LatLng downtownKelowna = new LatLng(49.88, -119.493);
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(downtownKelowna).title("Downtown Kelowna"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(downtownKelowna));

            // Make the toast message
            Toast.makeText(this, "Your location is too far! Please stay within the Kelowna area and try again.", Toast.LENGTH_LONG).show();
        }
        //Otherwise, update the vendor location in Firebase
        else {
            // Create an AlertDialog.Builder instance
            new AlertDialog.Builder(this)
                    .setTitle("Confirmation")
                    .setMessage("Do you want to confirm the location?")
                    // If the user confirms, update the location and finish() the activity
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            loggedInVendor.setLatitude(userLocation.latitude);
                            loggedInVendor.setLongitude(userLocation.longitude);
                            Database.updateVendor(vendorId, loggedInVendor);
                            finish();
                        }
                    })
                    // If the user cancels, just close the dialog
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        }
    }


}
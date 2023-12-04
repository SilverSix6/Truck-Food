package com.example.truck_food.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.example.truck_food.Database.Database;
import com.example.truck_food.Database.DatabaseCompleteListener;
import com.example.truck_food.R;
import com.example.truck_food.User.Vendor;
import com.example.truck_food.databinding.ActivityViewAllActiveVendorsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;

public class ViewAllActiveVendors extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    HashMap<String, Vendor> vendors;

    private ActivityViewAllActiveVendorsBinding binding;

    // Used to ask user for their location
    private FusedLocationProviderClient fusedLocationClient;

    private LatLng currentLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewAllActiveVendorsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);


        //Add all locations of vendors to map
        vendors = Database.getVendors(new DatabaseCompleteListener() {
            @Override
            public void databaseComplete() {
                addMarkers();
            }
        });

        //Get current location data
        currentLocation(new View(this));
    }

    public void addMarkers(){
        for (Vendor vendor: vendors.values()){
            double latitude = vendor.getLatitude();
            double longitude = vendor.getLongitude();
            if(latitude != 0 & longitude != 0){
                String truckName = vendor.getTruckName();
                LatLng downtown = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(downtown).title(truckName));
            }
        }
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

                        //Check if user's current location is in Kelowna boundary
                        LatLng northeast = new LatLng(50.02,-119.374);
                        LatLng southwest = new LatLng(49.77, -119.589);
                        LatLngBounds kelownaBounds = new LatLngBounds(southwest, northeast);

                        if(!kelownaBounds.contains(currentLocation)){
                            //Move camera to downtown Kelowna
                            LatLng downtownKelowna = new LatLng(49.885, -119.493);
                            mMap.addMarker(new MarkerOptions().position(downtownKelowna).title("You are Here")
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(downtownKelowna, 15));
                        }
                        else{
                            //User's location is in boundary so move camera to their position
                            mMap.addMarker(new MarkerOptions().position(currentLocation).title("You are Here")
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15.0f));
                        }
                    }
                }
            });
        }
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

}
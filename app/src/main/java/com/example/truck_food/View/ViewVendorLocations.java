package com.example.truck_food.View;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.truck_food.Database.Database;
import com.example.truck_food.Database.DatabaseCompleteListener;
import com.example.truck_food.R;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.truck_food.User.Vendor;
import com.example.truck_food.databinding.ActivityUpdateLocationBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.truck_food.databinding.ActivityViewVendorLocationsBinding;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.HashMap;

public class ViewVendorLocations extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    HashMap<String, Vendor> vendors;

    private ActivityViewVendorLocationsBinding binding;

    // Used to ask user for their location
    private FusedLocationProviderClient fusedLocationClient;

    private LatLng currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewVendorLocationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
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
            String truckName = vendor.getTruckName();
            LatLng downtown = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(downtown).title(truckName));
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
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15.0f));
                        }
                    }
                }
            });
        }
    }


}
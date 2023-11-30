package com.example.truck_food.Location;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.truck_food.R;
import com.example.truck_food.databinding.ActivityTestLocationBinding;
import com.example.truck_food.databinding.ActivityUpdateLocationBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class UpdateLocation extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityUpdateLocationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


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
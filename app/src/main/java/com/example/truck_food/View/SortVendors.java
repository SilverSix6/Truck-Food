package com.example.truck_food.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.truck_food.Login.LoginScreen;
import com.example.truck_food.R;
import com.example.truck_food.User.Customer;
import com.example.truck_food.User.Vendor;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SortVendors extends AppCompatActivity {

    TextView sortSearch;
    TextView sortMaxDistance;
    Spinner options;
    CheckBox favorites;

    static double currentLat;
    static double currentLon;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_vendors);

        sortSearch = (TextView) findViewById(R.id.sortSeach);
        sortMaxDistance = (TextView) findViewById(R.id.sortDistance);
        options = (Spinner) findViewById(R.id.sortSpinner);
        favorites = (CheckBox) findViewById(R.id.sortFavorites);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        currentLocation();
    }

    public void search(View view) {
        Intent intent = new Intent();

        intent.putExtra("Search", String.valueOf(sortSearch.getText()));
        intent.putExtra("Max Distance", String.valueOf(sortMaxDistance.getText()));
        intent.putExtra("Options", options.getSelectedItem().toString());
        intent.putExtra("Favorites", sortSearch.isSelected());

        setResult(RESULT_OK, intent);
        finish();
    }

    public void back(View view) {
        finish();
    }

    public static LinkedList<Map.Entry<String, Vendor>> sortVendors(Set<Map.Entry<String, Vendor>> vendors, String search, String orderType, String distance, boolean favorites) {

        Stream<Map.Entry<String, Vendor>> vendorStream = vendors.stream();

        // Filter vendor name
        if (!Objects.equals(search, "")) {
            vendorStream = vendorStream.filter(e -> e.getValue().getTruckName().contains(search));
        }

        // Filter favorites
        if (favorites) {
            ArrayList<String> favList = ((Customer) LoginScreen.account).getFavorites();
            if (favList != null)
                vendorStream = vendorStream.filter(e -> favList.contains(e.getValue().getTruckName()));
            return vendorStream.collect(Collectors.toCollection(LinkedList::new));
        }

        // Sort by type

        switch (orderType) {
            case "Distance Ascending":
                vendorStream = vendorStream.sorted((a, b) -> (int) (Maps.distanceLatLong(a.getValue().getLatitude(), a.getValue().getLongitude(), currentLat, currentLon) - Maps.distanceLatLong(b.getValue().getLatitude(), b.getValue().getLongitude(), 0, 0)));
                break;
            case "Distance Descending":
                vendorStream = vendorStream.sorted((a, b) -> (int) (Maps.distanceLatLong(b.getValue().getLatitude(), b.getValue().getLongitude(), currentLat, currentLon) - Maps.distanceLatLong(a.getValue().getLatitude(), a.getValue().getLongitude(), 0, 0)));
                break;
            case "New to the App":
                vendorStream = vendorStream.sorted((a, b) -> Math.toIntExact(b.getValue().getDate() - a.getValue().getDate()));
                break;
            case "Top Reviews":
                vendorStream = vendorStream.sorted((a, b) -> (int) (b.getValue().getAverageReview() - a.getValue().getAverageReview()));
                break;
            default:

        }

        // Filter Distance
        if (!Objects.equals(distance, "")) {
            vendorStream = vendorStream.filter(e -> Maps.distanceLatLong(e.getValue().getLatitude(), e.getValue().getLongitude(), currentLat, currentLon) < Double.parseDouble(distance));
        }

        return vendorStream.collect(Collectors.toCollection(LinkedList::new));
    }

    public void currentLocation() {
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

                        currentLon = currentLocation.longitude;
                        currentLat = currentLocation.latitude;
                    }
                }
            });
        }
    }

}
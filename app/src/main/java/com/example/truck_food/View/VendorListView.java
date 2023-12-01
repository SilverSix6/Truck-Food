package com.example.truck_food.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.truck_food.Database.Database;
import com.example.truck_food.Database.DatabaseCompleteListener;
import com.example.truck_food.Login.LoginScreen;
import com.example.truck_food.Login.MainLoginScreen;
import com.example.truck_food.R;
import com.example.truck_food.Review.Menu;
import com.example.truck_food.User.Customer;
import com.example.truck_food.User.Vendor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class VendorListView extends AppCompatActivity {

    HashMap<String,Vendor> vendors;
    LinearLayout scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_list_view);

        scrollView = (LinearLayout) findViewById(R.id.scrollLayout);

        vendors = Database.getVendors(new DatabaseCompleteListener() {
            @Override
            public void databaseComplete() {
                // Update screen
                updateList(new ArrayList<>(vendors.values()));
            }
        });
    }

    public void updateList(ArrayList<Vendor> vendorList){
        for (Vendor vendor: vendorList){
            RelativeLayout layout = new RelativeLayout(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT));
            layout.setLayoutParams(params);


            Button button = new Button(this);
            button.setText(vendor.getTruckName());
            params = new RelativeLayout.LayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT));
            button.setLayoutParams(params);
            layout.addView(button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openVendorProfile(vendor);
                }
            });

            scrollView.addView(layout);
        }
    }

    public void openVendorProfile(Vendor vendor){
        Intent intent = new Intent(this, Menu.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("Vendor", vendor);
        intent.putExtra("Bundle", bundle);

        this.startActivity(intent);
        finish();
    }

    public void sort(View view) {
        Intent intent = new Intent(this, SortVendors.class);

        sortResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> sortResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle bundle = result.getData().getExtras();
                    assert bundle != null;
                    // Perform sort
                    sortVendors(bundle.getString("Search", ""), bundle.getString("Options", ""), bundle.getString("Max Distance", String.valueOf(-1)), bundle.getBoolean("Favorites", false));
                }
            });

    public void sortVendors(String search, String orderType, String distance, boolean favorites) {
        /*
        Stream<Vendor> vendorStream = vendors.values().stream();

        // Filter vendor name
        if (!Objects.equals(search, "")) {
            vendorStream = vendorStream.filter(item -> item.getTruckName().contains(search));
        }

        // Filter favorites


        // Sort by type
        switch (orderType) {
            case "Distance Ascending":
                vendorStream = vendorStream.sorted(Comparator.comparing(Ven))
                break;
            case "Distance Descending":
                break;
            case "New to the App":
                vendorStream = vendorStream.sorted(Comparator.comparing())
                break;
            default:

        }


        // Filter Distance
        if (!Objects.equals(distance, "")) {
            vendorStream = vendorStream.filter(item -> Maps.distanceLatLong(item.getLatitude(), item.getLongitude(), 0, 0) < Double.parseDouble(distance));
        }

        updateList(new ArrayList<>(vendorStream.collect(Collectors.toList())));

         */
    }

    public void logout(View view) {
        Intent intent = new Intent(this, MainLoginScreen.class);

        // todo: Add authentication stuff
        LoginScreen.account = null;

        this.startActivity(intent);
        finish();
    }

    public void mapView(View view) {
        Intent intent = new Intent(this, ViewVendorLocations.class);
        this.startActivity(intent);
        finish();
    }

    public void favorites(View view) {
        sortVendors("","","",true);
    }
}
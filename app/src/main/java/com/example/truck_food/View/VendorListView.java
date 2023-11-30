package com.example.truck_food.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.truck_food.Database.Database;
import com.example.truck_food.Database.DatabaseCompleteListener;
import com.example.truck_food.Login.MainLoginScreen;
import com.example.truck_food.R;
import com.example.truck_food.User.Vendor;

import java.util.ArrayList;
import java.util.HashMap;

public class VendorListView extends AppCompatActivity {

    HashMap<String, Vendor> vendors;
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
                updateList();
            }
        });
    }

    public void updateList(){
        for (Vendor vendor: vendors.values()){
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
        Intent intent = new Intent(this, VendorProfileView.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("Vendor", vendor);
        intent.putExtra("Bundle", bundle);

        this.startActivity(intent);
    }

    public void sort(View view) {
        Intent intent = new Intent(this, SortVendors.class);

        this.startActivity(intent);
    }

    public void logout(View view) {
        Intent intent = new Intent(this, MainLoginScreen.class);

        // todo: Add authentication stuff

        this.startActivity(intent);
    }

    public void mapView(View view) {
        Intent intent = new Intent(this, ViewVendorLocations.class);

        this.startActivity(intent);
    }

    public void favorites(View view) {

    }
}
package com.example.truck_food.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.truck_food.R;
import com.example.truck_food.User.Vendor;

public class VendorProfileView extends AppCompatActivity {
    Vendor vendor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_profile_view);

        Bundle bundle = getIntent().getBundleExtra("Bundle");
        assert bundle != null;
        vendor = bundle.getSerializable("Vendor", Vendor.class);
    }
}
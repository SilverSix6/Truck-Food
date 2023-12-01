package com.example.truck_food.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.truck_food.Location.UpdateLocation;
import com.example.truck_food.Login.MainLoginScreen;
import com.example.truck_food.R;


public class VendorAdminPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_admin_page);
    }

    public void logout(View view) {
        Intent intent = new Intent(this, MainLoginScreen.class);
        startActivity(intent);
    }

    public void updateLocation(View view) {
        Intent intent = new Intent(this, UpdateLocation.class);
        startActivity(intent);
    }

    public void editProfile(View view) {
        Intent intent = new Intent(this, EditVendorInformation.class);
        startActivity(intent);
    }

}
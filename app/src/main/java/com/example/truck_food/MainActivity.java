package com.example.truck_food;

import static com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.truck_food.Database.Database;
import com.example.truck_food.Database.DatabaseCompleteListener;
import com.example.truck_food.Location.TestLocation;
import com.example.truck_food.Login.MainLoginScreen;
import com.example.truck_food.User.Vendor;
import com.example.truck_food.View.ViewVendorLocations;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    HashMap<String, Vendor> vendors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void testMaps(View v){
        Intent intent = new Intent(this, TestLocation.class);
        startActivity(intent);
    }

    public void mainLogin(View view) {
        Intent intent = new Intent(this, MainLoginScreen.class);
        startActivity(intent);
    }

    public void testTask1(View v){
        Intent intent = new Intent(this, ViewVendorLocations.class);
        startActivity(intent);
    }
}
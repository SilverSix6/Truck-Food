package com.example.truck_food;

import static com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.truck_food.Location.TestLocation;
import com.example.truck_food.Location.UpdateLocation;
import com.example.truck_food.Login.MainLoginScreen;
import com.example.truck_food.Review.Menu;
import com.example.truck_food.User.Vendor;
import com.example.truck_food.View.ViewAllActiveVendors;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    HashMap<String, Vendor> vendors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, MainLoginScreen.class);
        startActivity(intent);
        finish();
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
        Intent intent = new Intent(this, ViewAllActiveVendors.class);
        startActivity(intent);
    }

    public void testTask5(View v){
        Intent intent = new Intent(this, UpdateLocation.class);
        startActivity(intent);
    }

    public void testMenu(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }



}
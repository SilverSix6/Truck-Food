package com.example.truck_food;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.truck_food.Database.Database;
import com.example.truck_food.User.MenuItem;
import com.example.truck_food.User.Vendor;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Vendor> vendors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(new MenuItem("MenuItem","Description",10.0));
        Vendor vendor = new Vendor("Username","Email","Password","TruckName","BannerImage","Description",menuItems);
        Database.addVendor(vendor);

        vendors = Database.getVendors();
    }

    public void refresh(View view){
        ((TextView) findViewById(R.id.textView)).setText(vendors.toString());
    }
}
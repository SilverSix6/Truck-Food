package com.example.truck_food.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.truck_food.Database.Database;
import com.example.truck_food.Location.UpdateLocation;
import com.example.truck_food.Login.LoginScreen;
import com.example.truck_food.Login.MainLoginScreen;
import com.example.truck_food.R;
import com.example.truck_food.User.Vendor;



public class VendorAdminPage extends AppCompatActivity {

    private Vendor loggedInVendor;

    private String vendorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_admin_page);

        loggedInVendor = (Vendor) LoginScreen.account;
        vendorId = LoginScreen.accountId;
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

    public void turnOffLocation(View v){
        double currentLat = loggedInVendor.getLatitude();
        double currentLong = loggedInVendor.getLongitude();
        int duration = Toast.LENGTH_SHORT;

        if(currentLat == 0 & currentLong == 0){
            //Toast that location is already off
            CharSequence text = "Your location is already turned off!";
            Toast toast = Toast.makeText(this /* MyActivity */, text, duration);
            toast.show();
        }else{
            loggedInVendor.setLatitude(0);
            loggedInVendor.setLongitude(0);
            Database.updateVendor(vendorId, loggedInVendor);
            //Toast that location is now turned off
            CharSequence text = "Location successfully turned off!";
            Toast toast = Toast.makeText(this /* MyActivity */, text, duration);
            toast.show();
        }
    }

}
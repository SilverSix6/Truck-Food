package com.example.truck_food.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.truck_food.Database.Database;
import com.example.truck_food.Database.DatabaseCompleteListener;
import com.example.truck_food.R;
import com.example.truck_food.User.Customer;
import com.example.truck_food.User.User;
import com.example.truck_food.User.Vendor;
import com.example.truck_food.View.VendorAdminPage;
import com.example.truck_food.View.VendorListView;
import com.example.truck_food.View.ViewVendorLocations;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class LoginScreen extends AppCompatActivity {

    public static User account;
    HashMap<String, Vendor> vendors;
    HashMap<String, Customer> customers;

    String EnteredUsername;
    String EnteredPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }

    public void signIn(View view) {
        //Get entered user/pass
        TextView usernametv = findViewById(R.id.usernametv);
        EnteredUsername = usernametv.getText().toString();
        TextView passwordtv = findViewById(R.id.passwordtv);
        EnteredPassword = passwordtv.getText().toString();

        validateLogin();
    }

    public void validateLogin(){

        customers = Database.getCustomers(new DatabaseCompleteListener() {
            @Override
            public void databaseComplete() {
                boolean found = false;

                for(Map.Entry<String, Customer> entry : customers.entrySet()) {
                    String key = entry.getKey();
                    Customer customer = entry.getValue();

                    String username = customer.getUsername();
                    String password = customer.getPassword();
                    if(username.equals(EnteredUsername)  && password.equals(EnteredPassword)){
                        found = true;
                        account = customer;
                        break;
                    }
                }

                if (found) {
                    // Is a Customer
                    loginCustomer();
                } else {
                    Log.d("Customer", "Not a customer");
                    // Check if they are a vendor
                    validateLoginVendor();
                }
            }
        });

    }

    public void validateLoginVendor() {
        vendors = Database.getVendors(new DatabaseCompleteListener() {
            @Override
            public void databaseComplete() {
                boolean found = false;
                //For each vendor, check if matches user/pass
                //If yes, save that key for later
                for(Map.Entry<String, Vendor> entry : vendors.entrySet()) {
                    String key = entry.getKey();
                    Vendor vendor = entry.getValue();

                    String username = vendor.getUsername();
                    String password = vendor.getPassword();
                    if(username.equals(EnteredUsername)  && password.equals(EnteredPassword)){
                        found = true;
                        account = vendor;
                        break;
                    }
                }

                if(found) {
                    // Is a vendor
                    loginVendor();
                } else {
                    // Not a vendor
                    Log.d("Vendor", "Not a vendor");
                    invalidLogin();
                }
            }
        });
    }

    public void invalidLogin(){
        Toast.makeText( this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
    }

    public void loginCustomer(){
        Intent intent = new Intent(this, VendorListView.class);
        this.startActivity(intent);
        finish();
    }

    public void loginVendor(){
        Intent intent = new Intent(this, VendorAdminPage.class);
        this.startActivity(intent);
        finish();
    }


    public void back(View view) {
        finish();
    }

}
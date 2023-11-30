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
import com.example.truck_food.User.Vendor;
import com.example.truck_food.View.VendorListView;
import com.example.truck_food.View.ViewVendorLocations;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class LoginScreen extends AppCompatActivity {

    HashMap<String, Vendor> vendors;
    HashMap<String, Customer> customers;
    boolean isVendor;
    String loginKey = "";
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

        boolean validLogin = validateLogin();

        if(validLogin){

            Intent intent;
            if(isVendor){//Open Main Vendor Screen
                intent = new Intent(this, VendorListView.class);
            }
            else{//Open Main Customer Screen, change to correct class at some point
                intent = new Intent(this, VendorListView.class);
            }
            intent.putExtra("loggedInUserKey", loginKey);
            this.startActivity(intent);

        }else{ //User/pass is invalid
            Log.d("Username", EnteredUsername);
            Log.d("Password", EnteredPassword);
            Log.d("Key", loginKey);
            Toast toast = Toast.makeText(this, "Please enter valid login", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    public boolean validateLogin(){

        vendors = Database.getVendors(new DatabaseCompleteListener() {
            @Override
            public void databaseComplete() {

                //For each vendor, check if matches user/pass
                //If yes, save that key for later
                for(Map.Entry<String, Vendor> entry : vendors.entrySet()) {
                    String key = entry.getKey();
                    Vendor vendor = entry.getValue();

                    String username = vendor.getUsername();
                    String password = vendor.getPassword();
                    if(username.equals(EnteredUsername)  && password.equals(EnteredPassword)){
                        loginKey = key;
                        isVendor = true;
                        return;
                    }
                }

            }
        });




        //Checking if the login is a customer, is crashing the rn tho so leave for now
        if(loginKey.length() > 0){
            return true;
        }
        customers = Database.getCustomers(new DatabaseCompleteListener() {
            @Override
            public void databaseComplete() {
                for(Map.Entry<String, Customer> entry : customers.entrySet()) {
                    String key = entry.getKey();
                    Customer customer = entry.getValue();

                    String username = customer.getUsername();
                    String password = customer.getPassword();
                    if(username.equals(EnteredUsername)  && password.equals(EnteredPassword)){
                        loginKey = key;
                        isVendor = false;
                        return;
                    }
                }
            }
        });

        //If a login match was found
        return(loginKey.length() > 0);
    }



    public void back(View view) {
        finish();
    }

}
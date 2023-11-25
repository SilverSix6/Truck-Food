package com.example.truck_food.Database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.truck_food.User.Customer;
import com.example.truck_food.User.Vendor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Database {
    static FirebaseDatabase database = FirebaseDatabase.getInstance();

    final static String customerLocation = "Firebase/Data/Customer";
    final static String vendorLocation = "Firebase/Data/Vendor";

    // Add Vendor
    public static void addVendor(Vendor vendor) {
        // Get uuid for new vendor and update value in vendor
        DatabaseReference newVendorDR = database.getReference(vendorLocation).push();
        vendor.setUuid(newVendorDR.getKey());
        newVendorDR.setValue(vendor);
    }

    // Add Customer
    public static void addCustomer(Customer customer) {
        // Get uuid for new customer and update value in vendor
        DatabaseReference newCustomerDR = database.getReference(customerLocation).push();
        customer.setUuid(newCustomerDR.getKey());
        newCustomerDR.setValue(customer);
    }

    //
    // Get Vendors information from the database
    //
    // Once database is much larger we will need to query only a portion of the database (Based off of city?)
    //
    public static ArrayList<Vendor> getVendors() {
        ArrayList<Vendor> vendors = new ArrayList<>();
        database.getReference(vendorLocation).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    Log.d("Firebase", "Error getting data from server");
                    return;
                }
                for(DataSnapshot vendor: task.getResult().getChildren()){
                    vendors.add((Vendor) vendor.getValue());
                }

            }
        });

        return vendors;
    }

    //
    // Get Customers From Firebase
    //
    // Not super useful for out tasks but added anyways if we want to add more features
    //
    public ArrayList<Customer> getCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        database.getReference(customerLocation).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    Log.d("Firebase", "Error getting data from server");
                    return;
                }
                for(DataSnapshot customer: task.getResult().getChildren()){
                    customers.add((Customer) customer.getValue());
                }
            }
        });

        return customers;
    }
}

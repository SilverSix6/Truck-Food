package com.example.truck_food.Database;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.truck_food.Review.Review;
import com.example.truck_food.User.Customer;
import com.example.truck_food.User.User;
import com.example.truck_food.User.Vendor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.HashMap;

public class Database {
    static FirebaseDatabase database = FirebaseDatabase.getInstance();

    final static String CUSTOMER_LOCATION = "Firebase/Data/Customer";
    final static String VENDOR_LOCATION = "Firebase/Data/Vendor";


    // Add Vendor
    public static void addVendor(Vendor vendor) {
        // Get uuid for new vendor and update value in vendor
        database.getReference(VENDOR_LOCATION).push().setValue(vendor);
    }

    // Add Customer
    public static void addCustomer(Customer customer) {
        // Get uuid for new customer and update value in vendor
        database.getReference(CUSTOMER_LOCATION).push().setValue(customer);
    }

    //
    // Get Vendors information from the database
    //
    // Once database is much larger we will need to query only a portion of the database (Based off of city?)
    //
    public static HashMap<String, Vendor> getVendors(DatabaseCompleteListener listener) {
        HashMap<String, Vendor> vendors = new HashMap<>();
        database.getReference(VENDOR_LOCATION).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    Log.d("Firebase", "Error getting data from server");
                    return;
                }
                for(DataSnapshot vendorSnapshot: task.getResult().getChildren()){
                    vendors.put(vendorSnapshot.getKey(),vendorSnapshot.getValue(Vendor.class));

                }
                listener.databaseComplete();
            }
        });

        return vendors;
    }

    //
    // Get Customers From Firebase
    //
    // Not super useful for out tasks but added anyways if we want to add more features
    //
    public static HashMap<String, Customer> getCustomers(DatabaseCompleteListener listener) {
        HashMap<String, Customer> customers = new HashMap<>();
        database.getReference(CUSTOMER_LOCATION).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    Log.d("Firebase", "Error getting data from server");
                    return;
                }
                for(DataSnapshot customerSnapshot: task.getResult().getChildren()){
                    customers.put(customerSnapshot.getKey(), customerSnapshot.getValue(Customer.class));
                }
                listener.databaseComplete();
            }
        });

        return customers;
    }

    public static Customer getCustomer(String uid, DatabaseCompleteListener listener){
        final Customer[] customer = {null};
        database.getReference(CUSTOMER_LOCATION).child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    Log.d("Firebase", "Error getting data from server");
                    return;
                }
                customer[0] = (Customer) task.getResult().getValue();
                listener.databaseComplete();
            }
        });
        return customer[0];
    }

    public static Vendor getVendor(String uid, DatabaseCompleteListener listener){
        final Vendor[] vendor = {null};
        database.getReference(VENDOR_LOCATION).child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(!task.isSuccessful()){
                    Log.d("Firebase", "Error getting data from server");
                    return;
                }
                vendor[0] = (Vendor) task.getResult().getValue();
                listener.databaseComplete();
            }
        });
        return vendor[0];
    }

    public static void updateCustomer(String uid, Customer customer){
        database.getReference(CUSTOMER_LOCATION).child(uid).setValue(customer);
    }

    public static void updateVendor(String uid, Vendor vendor){
        database.getReference(VENDOR_LOCATION).child(uid).setValue(vendor);
    }

}

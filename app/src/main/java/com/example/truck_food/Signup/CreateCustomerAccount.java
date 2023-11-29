package com.example.truck_food.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.truck_food.R;

public class CreateCustomerAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_create_customer_account);

        // Place holder activity if we want to add customer specific account creation
        // For now just go to the verification screen
        Intent intent = new Intent(this, CreateAccountVerification.class);
        intent.putExtra("Bundle",getIntent().getBundleExtra("Bundle"));
        this.startActivity(intent);
    }
}
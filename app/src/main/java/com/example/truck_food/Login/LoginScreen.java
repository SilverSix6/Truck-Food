package com.example.truck_food.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.truck_food.R;
import com.example.truck_food.View.VendorListView;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }

    public void signIn(View view) {
        // Do checks on the login

        Intent intent = new Intent(this, VendorListView.class);
        this.startActivity(intent);
    }
    public void back(View view) {
        finish();
    }

}
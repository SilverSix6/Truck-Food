package com.example.truck_food.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.truck_food.R;
import com.example.truck_food.Signup.CreateAccount;

public class MainLoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login_screen);
    }

    public void login(View view) {
        Intent intent = new Intent(this, LoginScreen.class);
        this.startActivity(intent);
    }

    public void signup(View view) {
        Intent intent = new Intent(this, CreateAccount.class);
        this.startActivity(intent);
    }
}
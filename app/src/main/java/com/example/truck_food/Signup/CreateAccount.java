package com.example.truck_food.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.truck_food.R;

import java.util.Scanner;

public class CreateAccount extends AppCompatActivity {
    EditText email;
    EditText username;
    EditText password;
    CheckBox isVendor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        email = (EditText) findViewById(R.id.editTextEmail);
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);

        isVendor = (CheckBox) findViewById(R.id.checkboxIsVendor);
    }

    public void signup(View view) {
        // Check email format
        Scanner scanner = new Scanner((Readable) email.getText());
        if (scanner.hasNext("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            Toast.makeText(this, "Please enter a valid email address.", Toast.LENGTH_LONG).show();
            return;
        }

        // Check if creating vendor account or consumer account
        Intent intent;
        if (isVendor.isChecked()) {
            intent = new Intent(this, CreateVendorAccount.class);
        } else {
            intent = new Intent(this, CreateCustomerAccount.class);
        }

        intent.putExtra("Email", email.getText());
        intent.putExtra("Username", username.getText());
        intent.putExtra("Password", password.getText());
        this.startActivity(intent);
    }

    public void back(View view) {
        finish();
    }
}
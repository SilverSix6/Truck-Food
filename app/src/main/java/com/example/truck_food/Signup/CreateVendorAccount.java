package com.example.truck_food.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.truck_food.R;

public class CreateVendorAccount extends AppCompatActivity {
    Bundle accountData;
    TextView truckName;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_vendor_account);

        accountData = getIntent().getBundleExtra("Bundle");

        truckName = (TextView) findViewById(R.id.editTextTruckName);
        description = (TextView) findViewById(R.id.editTextDescription);
    }

    public void next(View view) {
        Intent intent = new Intent(this, ImageUpload.class);

        accountData.putString("Truck Name", String.valueOf(truckName.getText()));
        accountData.putString("Description", String.valueOf(description.getText()));

        intent.putExtra("Bundle", accountData);

        this.startActivity(intent);
    }

    public void back(View view) {
        finish();
    }
}
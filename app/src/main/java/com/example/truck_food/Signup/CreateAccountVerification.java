package com.example.truck_food.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.truck_food.R;
import com.example.truck_food.View.VendorListView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CreateAccountVerification extends AppCompatActivity {
    Bundle accountInformation;
    ArrayList<MenuItem> menu;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_verification);

        accountInformation = getIntent().getBundleExtra("Bundle");
        //menu = getIntent().getParcelableArrayListExtra("Menu");

        textView = (TextView) findViewById(R.id.accountInformation);
    }

    public void next(View view) {
        Intent intent = new Intent(this, VendorListView.class);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Username: ");
        stringBuilder.append(accountInformation.getString(""));
        stringBuilder.append('\n');
        stringBuilder.append("Password: ");
        stringBuilder.append(accountInformation.getString(""));
        stringBuilder.append('\n');
        stringBuilder.append("Truckname: ");
        stringBuilder.append(accountInformation.getString(""));
        stringBuilder.append('\n');
        stringBuilder.append("Banner Image: ");
        stringBuilder.append(accountInformation.getString(""));
        stringBuilder.append('\n');
        stringBuilder.append("Description: ");
        stringBuilder.append(accountInformation.getString(""));
        stringBuilder.append('\n');
        stringBuilder.append("Menu: ");

        this.startActivity(intent);
    }
}
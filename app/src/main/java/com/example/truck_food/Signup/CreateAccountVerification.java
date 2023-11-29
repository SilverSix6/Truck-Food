package com.example.truck_food.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.truck_food.Database.Database;
import com.example.truck_food.R;
import com.example.truck_food.User.MenuItem;
import com.example.truck_food.User.Vendor;
import com.example.truck_food.View.VendorListView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class CreateAccountVerification extends AppCompatActivity {
    Bundle accountInformation;
    ArrayList<MenuItem> menu;
    TextView textView;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_verification);

        accountInformation = getIntent().getBundleExtra("Bundle");

        textView = (TextView) findViewById(R.id.accountInformation);

        //noinspection deprecation
        menu = (ArrayList<MenuItem>) accountInformation.getSerializable("menu");

        if(menu != null) {
            Log.d("SignUp", menu.toString());
        }
        else
            Log.d("Signup", "Menu null");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Username: ");
        stringBuilder.append(accountInformation.getString("Username"));
        stringBuilder.append('\n');
        stringBuilder.append("Email: ");
        stringBuilder.append(accountInformation.getString("Email"));
        stringBuilder.append('\n');
        stringBuilder.append("Password: ");
        stringBuilder.append(accountInformation.getString("Password"));
        stringBuilder.append('\n');
        stringBuilder.append("Truck Name: ");
        stringBuilder.append(accountInformation.getString("Truck Name"));
        stringBuilder.append('\n');
        stringBuilder.append("Banner Image: ");
        stringBuilder.append(accountInformation.getString("ImageURL"));
        stringBuilder.append('\n');
        stringBuilder.append("Description: ");
        stringBuilder.append(accountInformation.getString("Description"));
        stringBuilder.append('\n');
        stringBuilder.append("Menu: ");
        if(menu != null){
            for (MenuItem item: menu) {
                stringBuilder.append("\nItem Name: " + item.getName() + " Item Description: " + item.getDescription() + " Item Price: " + item.getPrice());
            }

        }

        textView.setText(stringBuilder.toString());
    }

    @SuppressWarnings("unchecked")
    public void next(View view) {

        if (menu != null){
            Database.addVendor(new Vendor(accountInformation.getString("Username"),
                    accountInformation.getString("Email"),
                    accountInformation.getString("Password"),
                    accountInformation.getString("Truck Name"),
                    accountInformation.getString("ImageURL"),
                    accountInformation.getString("Description"),
                    menu));
            //noinspection deprecation
        } else {
            Database.addVendor(new Vendor(accountInformation.getString("Username"),
                    accountInformation.getString("Email"),
                    accountInformation.getString("Password"),
                    accountInformation.getString("Truck Name"),
                    accountInformation.getString("Email"),
                    accountInformation.getString("Email"),
                    null));
        }

        Intent intent = new Intent(this, VendorListView.class);
        finishAffinity();
        this.startActivity(intent);
    }

    public void back(View view) {
        finish();
    }
}
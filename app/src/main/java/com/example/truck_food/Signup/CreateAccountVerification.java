package com.example.truck_food.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.truck_food.Database.Database;
import com.example.truck_food.Image.Image;
import com.example.truck_food.Login.MainLoginScreen;
import com.example.truck_food.R;
import com.example.truck_food.User.Customer;
import com.example.truck_food.User.MenuItem;
import com.example.truck_food.User.Vendor;
import com.example.truck_food.View.VendorListView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Objects;

public class CreateAccountVerification extends AppCompatActivity {
    Bundle accountInformation;
    ArrayList<MenuItem> menu;
    Image image;
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
        image = accountInformation.getSerializable("Image", Image.class);


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
        for (int i = 0; i < Objects.requireNonNull(accountInformation.getString("Email")).length(); i++){
            stringBuilder.append('*');
        }
        stringBuilder.append('\n');

        // Vendor specific stuff
        if (accountInformation.getString("Truck Name") != null) {
            stringBuilder.append("Truck Name: ");
            stringBuilder.append(accountInformation.getString("Truck Name"));
            stringBuilder.append('\n');
            stringBuilder.append("Banner Image: ");
            stringBuilder.append(accountInformation.getSerializable("Image", Image.class) != null);
            stringBuilder.append('\n');
            stringBuilder.append("Description: ");
            stringBuilder.append(accountInformation.getString("Description"));
            stringBuilder.append('\n');
            stringBuilder.append("Menu: ");
            if(menu != null){
                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
                for (MenuItem item: menu) {
                    stringBuilder.append("\n").append(item.getName()).append(": ").append(item.getDescription()).append("\n\tPrice: ").append(currencyFormat.format(item.getPrice()));
                }
            }
        }


        textView.setText(stringBuilder.toString());
    }

    @SuppressWarnings("unchecked")
    public void next(View view) {
        // Determine if creating a vendor or a customer
        // Truck name is always non null if creating a vendor
        if (accountInformation.getString("Truck Name") != null) {
            Database.addVendor(new Vendor(accountInformation.getString("Username"),
                    accountInformation.getString("Email"),
                    accountInformation.getString("Password"),
                    accountInformation.getString("Truck Name"),
                    image,
                    accountInformation.getString("Description"),
                    menu, null));
        } else {
            Database.addCustomer(new Customer(accountInformation.getString("Username"),
                    accountInformation.getString("Email"),
                    accountInformation.getString("Password"), null));
        }

        Intent intent = new Intent(this, MainLoginScreen.class);
        finishAffinity();
        this.startActivity(intent);
    }

    public void back(View view) {
        finish();
    }
}
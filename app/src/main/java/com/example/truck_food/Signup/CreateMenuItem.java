package com.example.truck_food.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.truck_food.R;

public class CreateMenuItem extends AppCompatActivity {

    TextView itemName;
    TextView itemDescription;
    TextView itemPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_menu_item);

        itemName = (TextView) findViewById(R.id.editTextMenuItemName);
        itemDescription = (TextView) findViewById(R.id.editTextMenuItemDescription);
        itemPrice = (TextView) findViewById(R.id.editTextMenuItemPrice);
    }

    public void add(View view) {
        
    }
    public void back(View view) {
        finish();
    }
}
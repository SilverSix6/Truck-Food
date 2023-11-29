package com.example.truck_food.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.truck_food.R;
import com.example.truck_food.User.MenuItem;

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
        // Make sure all fields are non empty
        if(itemName.getText() == null || itemDescription.getText() == null || itemPrice.getText() == null) {
            Toast.makeText(this,"All fields must be filled!", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent();
        intent.putExtra("data", new MenuItem(String.valueOf(itemName.getText()), String.valueOf(itemDescription.getText()), Double.valueOf(String.valueOf(itemPrice.getText()))));
        setResult(RESULT_OK, intent);
        finish();
    }
    public void back(View view) {
        finish();
    }
}
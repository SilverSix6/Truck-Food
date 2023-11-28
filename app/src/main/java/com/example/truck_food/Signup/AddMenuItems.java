package com.example.truck_food.Signup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.truck_food.R;
import com.example.truck_food.User.MenuItem;

import java.util.ArrayList;
import java.util.Objects;

public class AddMenuItems extends AppCompatActivity {
    Bundle accountInfo;
    ArrayList<MenuItem> menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu_items);

        accountInfo = getIntent().getBundleExtra("Bundle");

        menu = new ArrayList<>();
    }

    public void addMenuItem(View view) {
        ActivityResultLauncher<Intent> menuItemResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            assert result.getData() != null;
                            MenuItem data = (MenuItem) Objects.requireNonNull(result.getData().getExtras()).get("Menu Item");
                            menu.add(data);
                        }
                    }
                });

        Intent intent = new Intent(this, CreateMenuItem.class);

        menuItemResultLauncher.launch(intent);
    }

    public void next(View view) {
        Intent intent = new Intent(this, CreateAccountVerification.class);

        intent.putExtra("Bundle", accountInfo);
        intent.putExtra("Menu", menu);

        this.startActivity(intent);
    }
}
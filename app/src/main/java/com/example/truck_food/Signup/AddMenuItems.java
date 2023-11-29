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
import android.widget.TextView;

import com.example.truck_food.R;
import com.example.truck_food.User.MenuItem;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class AddMenuItems extends AppCompatActivity {
    Bundle accountInfo;
    ArrayList<MenuItem> menu;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu_items);

        accountInfo = getIntent().getBundleExtra("Bundle");

        menu = new ArrayList<>();

        textView = (TextView) findViewById(R.id.menuItemList);
    }

    public void addMenuItem(View view) {
        Intent intent = new Intent(this, CreateMenuItem.class);

        menuItemResultLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> menuItemResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK && result.getData() != null){
                    Bundle bundle = result.getData().getExtras();
                    assert bundle != null;
                    // Add item to menu
                    //noinspection deprecation
                    menu.add((MenuItem) bundle.getSerializable("data"));

                    // Update screen
                    textView.setText(createMenuString());
                }
            }
    );

    public void next(View view) {
        Intent intent = new Intent(this, CreateAccountVerification.class);

        accountInfo.putSerializable("menu", menu);

        intent.putExtra("Bundle", accountInfo);

        this.startActivity(intent);
    }

    public void back(View view) {
        finish();
    }

    public String createMenuString(){
        StringBuilder builder = new StringBuilder();

        for(MenuItem item: menu){
            builder.append("Name: ");
            builder.append(item.getName());
            builder.append("  Description: ");
            builder.append(item.getDescription());
            builder.append("  Price: ");
            builder.append(item.getPrice());
            builder.append("\n");
        }

        return builder.toString();
    }
}
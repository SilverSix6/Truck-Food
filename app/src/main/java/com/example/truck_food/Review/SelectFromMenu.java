package com.example.truck_food.Review;

import static com.example.truck_food.Review.Menu.addMenuItems;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.LinearLayout;

import com.example.truck_food.R;
import com.example.truck_food.User.MenuItem;
import com.example.truck_food.User.Vendor;

import java.util.ArrayList;
import java.util.HashMap;

public class SelectFromMenu extends AppCompatActivity {

    LinearLayout menu;
    Vendor vendor;
    HashMap<LinearLayout, MenuItem> rows;
    MenuItem selectedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_from_menu);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        vendor = bundle.getSerializable("vendor", Vendor.class);
        menu = findViewById(R.id.menu);
        int height = (int) getResources().getDisplayMetrics().density * 2;


        rows = addMenuItems(vendor, SelectFromMenu.this, height, menu);
        LinearLayout[] l = rows.keySet().toArray(new LinearLayout[0]);

        for (int i = 0; i < rows.size(); i++) {

            l[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MenuItem m = rows.get(v);
                    itemSelected(m);
                }
            });
        }

    }

    public void itemSelected(MenuItem m) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("menuitem", m);
        intent.putExtras(bundle);

        setResult(RESULT_OK, intent);
        finish();

    }
}
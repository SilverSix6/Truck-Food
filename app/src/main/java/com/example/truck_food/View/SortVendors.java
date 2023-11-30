package com.example.truck_food.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.truck_food.R;

public class SortVendors extends AppCompatActivity {

    TextView sortSearch;
    TextView sortMaxDistance;
    Spinner options;
    CheckBox favorites;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_vendors);

        sortSearch = (TextView) findViewById(R.id.sortSeach);
        sortMaxDistance = (TextView) findViewById(R.id.sortDistance);
        options = (Spinner) findViewById(R.id.sortSpinner);
        favorites = (CheckBox) findViewById(R.id.sortFavorites);
    }

    public void search(View view) {
        Intent intent = new Intent();

        intent.putExtra("Search", String.valueOf(sortSearch.getText()));
        intent.putExtra("Max Distance", String.valueOf(sortMaxDistance.getText()));
        intent.putExtra("Options", options.getSelectedItem().toString());
        intent.putExtra("Favorites", sortSearch.isSelected());

        setResult(RESULT_OK, intent);
        finish();
    }

    public void back(View view) {

    }
}
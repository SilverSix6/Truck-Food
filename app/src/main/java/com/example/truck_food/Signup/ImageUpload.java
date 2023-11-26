package com.example.truck_food.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.truck_food.R;

public class ImageUpload extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);
    }

    public void next(View view) {
        Intent intent = new Intent(this, AddMenuItems.class);
        this.startActivity(intent);
    }

    public void back(View view) {
        finish();
    }
}
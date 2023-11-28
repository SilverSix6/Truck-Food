package com.example.truck_food.Signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.truck_food.R;

public class ImageUpload extends AppCompatActivity {
    Bundle accountInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        accountInfo = getIntent().getBundleExtra("Bundle");
    }

    public void next(View view) {
        Intent intent = new Intent(this, AddMenuItems.class);

        accountInfo.putString("ImageURL", "Temp");

        intent.putExtra("Bundle", accountInfo);

        this.startActivity(intent);
    }

    public void back(View view) {
        finish();
    }
}
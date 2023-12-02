package com.example.truck_food.Review;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.truck_food.Image.Image;
import com.example.truck_food.R;
import com.example.truck_food.User.Vendor;

public class WriteReview extends AppCompatActivity {

    Vendor vendor;
    TextView title;
    ImageView bannerimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(new Slide());
        getWindow().setSharedElementEnterTransition(null);
        setContentView(R.layout.activity_write_review);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        vendor = bundle.getSerializable("vendor", Vendor.class);
        title = findViewById(R.id.textView2);
        bannerimg = findViewById(R.id.imageView);
        Image banner = vendor.getBannerImage();
        if (banner != null) {
            Bitmap b = banner.getBitmap();
            bannerimg.setImageBitmap(b);
        }
        title.setText(vendor.getTruckName());
    }
}
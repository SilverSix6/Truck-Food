package com.example.truck_food.Signup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import com.example.truck_food.Image.Image;
import com.example.truck_food.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;

public class ImageUpload extends AppCompatActivity {
    Bundle accountInfo;
    Image image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);

        accountInfo = getIntent().getBundleExtra("Bundle");

        image = new Image();
    }

    public void uploadImage(View view) {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK);
        pickPhoto.setType("image/*");
        getImageResultLauncher.launch(pickPhoto);
    }

    ActivityResultLauncher<Intent> getImageResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null){
                try {
                    Uri imageUri = result.getData().getData();
                    InputStream imageStream = null;
                    imageStream = getContentResolver().openInputStream(imageUri);
                    image.setBitmap(BitmapFactory.decodeStream(imageStream));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

    });



    public void next(View view) {
        Intent intent = new Intent(this, AddMenuItems.class);

        accountInfo.putSerializable("Image", image);

        intent.putExtra("Bundle", accountInfo);

        this.startActivity(intent);
    }

    public void back(View view) {
        finish();
    }
}
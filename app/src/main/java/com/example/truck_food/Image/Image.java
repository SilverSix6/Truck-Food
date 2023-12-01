package com.example.truck_food.Image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class Image implements Serializable {
    String imageData;

    public Image(){
    }

    public  Image(String imageData) {
        this.imageData = imageData;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public void setBitmap(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, outputStream);
        byte [] b = outputStream.toByteArray();
        imageData = Base64.encodeToString(b, Base64.DEFAULT);
    }

    public Bitmap getBitmap() {
        try {
            byte [] encodeByte=Base64.decode(imageData,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}

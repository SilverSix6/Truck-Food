package com.example.truck_food.Review;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.truck_food.Database.Database;
import com.example.truck_food.Image.Image;
import com.example.truck_food.Login.LoginScreen;
import com.example.truck_food.R;
import com.example.truck_food.User.MenuItem;
import com.example.truck_food.User.Vendor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class WriteReview extends AppCompatActivity {

    Vendor vendor;
    TextView title;
    ImageView bannerimg;
    LinearLayout items;
    LinearLayout stars;
    int score;
    EditText review;
    private DatabaseReference root;
    ArrayList<String> itemsOrdered;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setEnterTransition(new Slide());

        setContentView(R.layout.activity_write_review);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        vendor = bundle.getSerializable("vendor", Vendor.class);
        title = findViewById(R.id.textView2);
        bannerimg = findViewById(R.id.imageView);
        uid = "fsdfs";
        items = findViewById(R.id.items);
        root = FirebaseDatabase.getInstance().getReference();
        stars = findViewById(R.id.stars);
        review = findViewById(R.id.editTextText);
        itemsOrdered = new ArrayList<>();
        score = 0;
        Image banner = vendor.getBannerImage();
        if (banner != null) {
            Bitmap b = banner.getBitmap();
            bannerimg.setImageBitmap(b);
        }
        title.setText(vendor.getTruckName());
        readValue();
    }

    public void starClick(View view) {
        ImageView img;
        int index = 0;
        for (int i = 0; i < stars.getChildCount(); i++)
            if (stars.getChildAt(i).equals(view))
                index = i;
        for (int i = index; i >= 0; i--) {
            img = (ImageView) stars.getChildAt(i);
            if (String.valueOf(img.getTag()).equals("left"))
                img.setImageResource(R.drawable.gold_star1);
            else
                img.setImageResource(R.drawable.gold_star2);
        }
        for (int i = index + 1; i < stars.getChildCount(); i++) {
            img = (ImageView) stars.getChildAt(i);
            if (String.valueOf(img.getTag()).equals("left"))
                img.setImageResource(R.drawable.gold_starempty1);
            else
                img.setImageResource(R.drawable.gold_starempty2);
        }
        score = index + 1;


    }
    public void selectFromMenu(View view) {
        Intent intent = new Intent(this, SelectFromMenu.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("vendor", vendor);
        intent.putExtras(bundle);
        startActivityForResult(intent, 1);
    }
    protected void readValue() {
        root.get().addOnCompleteListener(onValuesFetched);
    }
    public void postReview(View view) {

        String reviewString = review.getText().toString();
        boolean valid = true;
        String fail = "";

        if (reviewString.isEmpty()) {
            valid = false;
            fail = "Please write a review.";

        } else if (score == 0) {
            valid = false;
            fail = "Please give a rating.";
        } else if (itemsOrdered.size() ==  0) {
            valid = false;
            fail = "Please select item(s) from the menu.";
        }
        if (valid == false) {
            Toast toast = Toast.makeText(this, fail, Toast.LENGTH_SHORT);
            toast.show();
        } else {

            Review r = new Review(reviewString, score, "tomer", vendor.getTruckName(), itemsOrdered);
            ArrayList<Review> reviews = vendor.getReviews();
            if (reviews == null) {
                reviews = new ArrayList<>();
            }
            reviews.add(r);
            vendor.setReviews(reviews);
            Database.updateVendor(uid, vendor);
            finish();
        }
    }
    private OnCompleteListener<DataSnapshot> onValuesFetched = new OnCompleteListener<DataSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DataSnapshot> task) {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
                String path = "Firebase/Data/Vendor";
                DataSnapshot d = task.getResult().child(path);

                d.getChildren().forEach((element) -> {
                    if (String.valueOf(element.child("truckName").getValue()).equals(vendor.getTruckName())) {
                        uid = element.getKey().toString();
                    }

                });
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MenuItem m = data.getSerializableExtra("menuitem", MenuItem.class);
        boolean valid = true;
        for (int i = 0; i < items.getChildCount(); i++) {
            TextView text = (TextView)items.getChildAt(i);
            if (m.getName().equals(text.getText().toString())) {
                Toast toast = Toast.makeText(this, "Please choose a different item.", Toast.LENGTH_SHORT);
                toast.show();
                valid = false;
                break;
            }
        }
        if (valid) {
            TextView t = new TextView(this);
            t.setText(m.getName());
            t.setTextSize(20);
            items.addView(t);
            itemsOrdered.add(m.getName());
        }
    }
}
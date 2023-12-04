package com.example.truck_food.Review;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.*;

import com.example.truck_food.Database.DatabaseCompleteListener;
import com.example.truck_food.Image.Image;
import com.example.truck_food.Login.LoginScreen;
import com.example.truck_food.User.User;
import com.example.truck_food.R;
import com.example.truck_food.User.Customer;
import com.example.truck_food.User.MenuItem;
import com.example.truck_food.User.Vendor;
import com.example.truck_food.Database.Database;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Menu extends AppCompatActivity {

    private DatabaseReference root;
    int menuIndex;
    TextView title;
    TextView about;
    String titleString;
    String vendorId;
    ConstraintLayout every;
    LinearLayout menu;
    
    Vendor vendor;
    HashMap<String, Vendor> vendors;
    ImageView bannerimg;
    Customer customer;
    HashMap<String, Customer> customers;
    String uid;
    LinearLayout stars2;

    int count;
    String customerId;
    LinearLayout stars1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().setExitTransition(new Slide());

        setContentView(R.layout.activity_menu);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        root = FirebaseDatabase.getInstance().getReference();
        menuIndex = 0;
        title = findViewById(R.id.textView2);
        every = findViewById(R.id.everything);
        about = findViewById(R.id.textView5);
        bannerimg = findViewById(R.id.imageView7);
        stars1 = findViewById(R.id.stars1);
        stars2 = findViewById(R.id.linearLayout);
        menu = findViewById(R.id.menu);
        uid = "";
        count = 0;
        if (bundle != null) {
            customerId = LoginScreen.accountId;
            Bundle b = bundle.getBundle("Bundle");
            vendor = b.getSerializable("Vendor", Vendor.class);
            updateMenu();
        }
        else {
            vendorId = "-NkiiwRA60hVkcwMRC8l";
            customerId = "-NkiUctovti9__4V-Dqt";
            vendors = Database.getVendors(new DatabaseCompleteListener() {
                @Override
                public void databaseComplete() {
                    vendor = vendors.get(vendorId);
                    updateMenu();
                }
            });

        }
    customers = Database.getCustomers(new DatabaseCompleteListener() {
        @Override
        public void databaseComplete() {
            customer = customers.get(customerId);
            readValue();
        }
    });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        vendor = data.getSerializableExtra("vendor", Vendor.class);
        updateStars();

    }

    public void addFavorite(View view) {
        ArrayList<String> favs = customer.getFavorites();
        if (favs == null) {
            favs = new ArrayList<>();
        }
        if(!favs.contains(vendor.getTruckName())){
            favs.add(vendor.getTruckName());
            customer.setFavorites(favs);
            Database.updateCustomer(customerId, customer);
            Toast toast = Toast.makeText(this, "Favorited!", Toast.LENGTH_SHORT);
            toast.show();
        }

        Toast toast = Toast.makeText(this, "This truck is already favorited!", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void createReview(View view) {
       Intent intent = new Intent(this, WriteReview.class);
       Bundle bundle = new Bundle();
       bundle.putSerializable("vendor", vendor);
       bundle.putSerializable("customer", customer);
       bundle.putString("uid", uid);
       intent.putExtras(bundle);
       startActivityForResult(intent, 1, ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(bannerimg, "banner"), Pair.create(title, "title")).toBundle());
    }

    public void updateMenu() {
        title.setText(vendor.getTruckName());
        about.setText(vendor.getDescription());
        int height = (int) getResources().getDisplayMetrics().density * 2;
        addMenuItems(vendor, Menu.this, height, menu);
        Image banner = vendor.getBannerImage();
        if (banner != null) {
            Bitmap b = banner.getBitmap();
            bannerimg.setImageBitmap(b);
        }
        updateStars();
    }


    protected void readValue() {
        root.get().addOnCompleteListener(onValuesFetched);
    }

    protected void updateStars() {
        int sum = 0;
        int score = 0;

        ArrayList<Review> reviews = vendor.getReviews();
        if (reviews != null) {
            for (int i = 0; i < reviews.size(); i++) {
                sum += reviews.get(i).getStars();
            }
            double avg = sum / reviews.size();
            score = (int) Math.round(avg);
            setStars(score, stars1);
            Review recent = reviews.get(reviews.size() - 1);
            score = recent.getStars();
            setStars(score, stars2);
            TextView reviewSample = findViewById(R.id.textView9);
            reviewSample.setText(recent.getReview());
        }
    }
public void setStars(int score, LinearLayout stars1) {
        ImageView img;
    for (int i = score - 1; i >= 0; i--) {
        img = (ImageView) stars1.getChildAt(i);
        if (String.valueOf(img.getTag()).equals("left"))
            img.setImageResource(R.drawable.gold_star1);
        else
            img.setImageResource(R.drawable.gold_star2);
    }
    for (int i = score; i < stars1.getChildCount(); i++) {
        img = (ImageView) stars1.getChildAt(i);
        if (String.valueOf(img.getTag()).equals("left"))
            img.setImageResource(R.drawable.gold_starempty1);
        else
            img.setImageResource(R.drawable.gold_starempty2);
    }
}

    public static HashMap<LinearLayout, MenuItem> addMenuItems(Vendor v, Context c, int height, LinearLayout menu) {

        HashMap<LinearLayout, MenuItem> rows = new HashMap<>();
        ArrayList<MenuItem> items = v.getMenu();

        for (int i = 0; i < items.size(); i++) {
            MenuItem item = items.get(i);
            LinearLayout menuItem = new LinearLayout(c);
            menuItem.setOrientation(LinearLayout.HORIZONTAL);
            TextView itemName = new TextView(c);
            itemName.setText(item.getName());
            TextView itemDesc = new TextView(c);
            TextView itemPrice = new TextView(c);
            itemPrice.setText(String.valueOf(item.getPrice()));
            itemDesc.setText(item.getDescription());
            LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
            LinearLayout.LayoutParams par2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.8f);
            LinearLayout.LayoutParams par3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.2f);

            itemName.setLayoutParams(par);
            itemDesc.setLayoutParams(par2);
            itemPrice.setLayoutParams(par3);

            itemName.setTextSize(24);
            itemDesc.setTextSize(18);
            itemPrice.setTextSize(18);

            menuItem.setMinimumHeight(100);
            menuItem.setPadding(0, 20, 0, 20);

            menuItem.addView(itemName);
            menuItem.addView(itemDesc);
            menuItem.addView(itemPrice);
            View div = new View(c);

            div.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
            div.setBackgroundColor(Color.parseColor("#000000"));
            div.setAlpha(0.25f);

            menu.addView(menuItem);
            menu.addView(div);
            rows.put(menuItem, item);

        }
        return rows;
    }




/*
    private OnCompleteListener<DataSnapshot> onValuesFetched = new OnCompleteListener<DataSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DataSnapshot> task) {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
                String path = "Firebase/Data/Customer";
                DataSnapshot d = task.getResult().child(path);



                d.getChildren().forEach((element) -> {

                });

            }
        }
    };
*/
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
                    vendor.setUsername(String.valueOf(element.child("username").getValue()));
                    vendor.setPassword(String.valueOf(element.child("password").getValue()));
                    vendor.setDate(Long.parseLong(String.valueOf(element.child("date").getValue())));
                    vendor.setEmail(String.valueOf(element.child("email").getValue()));
                    vendor.setLongitude(Double.parseDouble(String.valueOf(element.child("longitude").getValue())));
                    vendor.setLatitude(Double.parseDouble(String.valueOf(element.child("latitude").getValue())));
                }

            });
            d = task.getResult().child("Firebase/Data/Customer");
            d.getChildren().forEach((element) -> {
                if (String.valueOf(element.child("username").getValue()).equals(customer.getUsername())) {
                    Object o = element.child("date").getValue();
                    String temp = "";
                    Long l = 0l;
                    if (o == null)
                        customer.setDate(l);
                    else {
                        temp = String.valueOf(o);
                        customer.setDate(Long.parseLong(temp));
                    }
                    customer.setUsername(String.valueOf(element.child("username").getValue()));
                    customer.setPassword(String.valueOf(element.child("password").getValue()));
                    customer.setEmail(String.valueOf(element.child("email").getValue()));
                }

            });

        }
    }
};

}
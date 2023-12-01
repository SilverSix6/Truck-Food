package com.example.truck_food.Review;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.truck_food.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Menu extends AppCompatActivity {

    private DatabaseReference root;
    int menuIndex;
    TextView title;
    TextView about;
    String titleString;
    String vendorId;
    ConstraintLayout every;
    LinearLayout menu;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        root = FirebaseDatabase.getInstance().getReference();
        menuIndex = 0;
        title = findViewById(R.id.textView2);
        every = findViewById(R.id.everything);
        about = findViewById(R.id.textView5);
        menu = findViewById(R.id.menu);
        every.setVisibility(View.GONE);
        vendorId = "-NkS23qjMcP2ENhWiL9c";


        readValue();
    }
    private OnCompleteListener<DataSnapshot> onValuesFetched = new OnCompleteListener<DataSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DataSnapshot> task) {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                Log.d("firebase", String.valueOf(task.getResult().getValue()));
                String path = "Firebase/Data/Vendor/" + vendorId;
                DataSnapshot d = task.getResult().child(path);
                titleString = String.valueOf(d.child("truckName").getValue());
                String aboutString = String.valueOf(d.child("description").getValue());

                title.setText(titleString);
                about.setText(aboutString);


                d.child("menu").getChildren().forEach((element) -> {
                    LinearLayout menuItem = new LinearLayout(Menu.this);
                    menuItem.setOrientation(LinearLayout.HORIZONTAL);
                    TextView itemName = new TextView(Menu.this);
                    itemName.setText(String.valueOf(element.child("name").getValue()));
                    TextView itemDesc = new TextView(Menu.this);
                    TextView itemPrice = new TextView(Menu.this);
                    itemPrice.setText(String.valueOf(element.child("price").getValue()));
                    itemDesc.setText(String.valueOf(element.child("description").getValue()));
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
                    menuItem.setPadding(0,20,0,20);

                    menuItem.addView(itemName);
                    menuItem.addView(itemDesc);
                    menuItem.addView(itemPrice);
                    View div = new View(Menu.this);
                    int height =  (int)getResources().getDisplayMetrics().density * 2;
                    div.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
                    div.setBackgroundColor(Color.parseColor("#000000"));
                    div.setAlpha(0.25f);



                    menu.addView(menuItem);

                    menu.addView(div);


                });
                every.setVisibility(View.VISIBLE);
            }
        }
    };
    protected void readValue() {
        root.get().addOnCompleteListener(onValuesFetched);
    }
}
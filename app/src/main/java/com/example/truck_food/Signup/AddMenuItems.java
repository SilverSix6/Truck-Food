package com.example.truck_food.Signup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.truck_food.R;
import com.example.truck_food.User.MenuItem;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Formatter;
import java.util.Objects;

public class AddMenuItems extends AppCompatActivity {
    Bundle accountInfo;
    ArrayList<MenuItem> menu;
    TextView textView;
    LinearLayout linearLayout;
    Button skipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu_items);

        accountInfo = getIntent().getBundleExtra("Bundle");

        menu = new ArrayList<>();

        textView = (TextView) findViewById(R.id.menuItemList);
        linearLayout = (LinearLayout) findViewById(R.id.AddItemsLinearLayout);
        skipButton = (Button) findViewById(R.id.skipButton);
    }

    public void addMenuItem(View view) {
        Intent intent = new Intent(this, CreateMenuItem.class);

        menuItemResultLauncher.launch(intent);


    }

    ActivityResultLauncher<Intent> menuItemResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK && result.getData() != null){
                    Bundle bundle = result.getData().getExtras();
                    assert bundle != null;
                    // Add item to menu
                    //noinspection deprecation
                    menu.add((MenuItem) bundle.getSerializable("data"));

                    // Update screen
                    //noinspection deprecation
                    addToLinearLayout((MenuItem) Objects.requireNonNull(bundle.getSerializable("data")));
                    skipButton.setText("Next");
                }
            }
    );

    @SuppressLint("SetTextI18n")
    public void addToLinearLayout(MenuItem item) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

        TextView text = new TextView(this);
        text.setText(item.getName() + ": " + item.getDescription() + "\n" + currencyFormat.format(item.getPrice()));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT));
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        text.setLayoutParams(params);

        Button remove = new Button(new ContextThemeWrapper(this, R.style.Base_Theme_TruckFood));
        RelativeLayout.LayoutParams paramsButton = new RelativeLayout.LayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT));
        paramsButton.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        remove.setLayoutParams(paramsButton);

        remove.setText("Remove");
        remove.setMinWidth(300);
        remove.setMaxWidth(600);


        RelativeLayout itemGroup = new RelativeLayout(this);
        itemGroup.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        itemGroup.addView(text);
        itemGroup.addView(remove);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.removeView(itemGroup);
                menu.remove(item);
                if (menu.size() == 0) {
                    skipButton.setText("Skip");
                }
            }
        });

        textView.setText("");
        linearLayout.addView(itemGroup);
    }

    public void next(View view) {
        Intent intent = new Intent(this, CreateAccountVerification.class);

        accountInfo.putSerializable("menu", menu);

        intent.putExtra("Bundle", accountInfo);

        this.startActivity(intent);
    }

    public void back(View view) {
        finish();
    }

}
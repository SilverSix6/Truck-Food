package com.example.truck_food.Location;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.truck_food.R;

public class UpdateLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_location);
    }



    public void mainMenu(View v){
        // Create an AlertDialog.Builder instance
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Do you want to go back to the main menu?")
                // If the user confirms, finish the activity
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                // If the user cancels, just close the dialog
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
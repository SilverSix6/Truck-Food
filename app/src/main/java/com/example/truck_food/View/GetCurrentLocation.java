package com.example.truck_food.View;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

public class GetCurrentLocation extends Service {
    public GetCurrentLocation() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
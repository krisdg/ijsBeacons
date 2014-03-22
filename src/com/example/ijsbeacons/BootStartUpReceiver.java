package com.example.ijsbeacons;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootStartUpReceiver extends BroadcastReceiver {

       @Override
       public void onReceive(Context context, Intent intent) {
              // TODO: This method is called when the BroadcastReceiver is receiving

              // Start Service On Boot Start Up
              Intent service = new Intent(context, BackgroundService.class);
              context.startService(service);


       }
}
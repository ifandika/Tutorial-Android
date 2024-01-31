package com.cutomintentbr.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBR extends BroadcastReceiver {
    
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Ini MyBR", Toast.LENGTH_SHORT).show();
    }
    
}

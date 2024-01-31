package com.appbr.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.provider.Settings;
import android.bluetooth.BluetoothAdapter;

public class MyBroadcastReceiver extends BroadcastReceiver {
    
    @Override
    public void onReceive(Context context, Intent intent) {
        
        // Dapatkan aksi
        String action = intent.getAction();
        
        // Cek jika aksi sama dengan yg kita inginkan
        if(action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            // Dapatkan kode dari setiap tindakan dari Bluetooth
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            switch(state) {
                // Jika Bluetooth dihidupkan
                case BluetoothAdapter.STATE_ON: 
                    Toast.makeText(context, "Bluetooth on", Toast.LENGTH_LONG).show();
                    break;
                // Jika Bluetooth dimatikan
                case BluetoothAdapter.STATE_OFF:
                    Toast.makeText(context, "Bluetooth off", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}

package com.appbr.main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.widget.Toast;
import android.content.Intent;
import android.bluetooth.BluetoothAdapter;

public class MainActivity extends AppCompatActivity {
    // Objek BR saya
    private BroadcastReceiver myBR;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
        
        // Tetapkan nilai
        myBR = new MyBroadcastReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        
        // Objek IntentFilter dengan tipe aksi tindakan dari Bluetooth
        IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        // Register objek BR dan IntentFilter
        getApplicationContext().registerReceiver(myBR, intentFilter);
        Toast.makeText(getApplicationContext(), "Sukses register BR", Toast.LENGTH_LONG).show();
        
    }

    @Override
    protected void onStop() {
        super.onStop();
        
        // Hapus register objek BR
        getApplicationContext().unregisterReceiver(myBR);
        Toast.makeText(getApplicationContext(), "Sukses unregister BR", Toast.LENGTH_LONG).show();
        
    }
    
}

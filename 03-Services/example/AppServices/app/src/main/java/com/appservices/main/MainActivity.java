package com.appservices.main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.app.ActivityManager;
import android.content.Context;
import androidx.core.app.ActivityManagerCompat;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.loader.R;
import android.widget.EditText;
import android.util.Log;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.content.ComponentName;

import com.appservices.main.IsBinderService.LocalBinder;

public class MainActivity extends AppCompatActivity {
    EditText editTextInputNumber;
    Button buttonStartBackgroundservice, buttonStartForegroundservice, buttonStopBackgroundservice, 
           buttonStopForegroundservice, buttonGetNumber;
    
    IsBinderService isBinderService;
    // Sebagai variabel pananda konektivitas
    boolean bound;
           
    public void initComponents() {
        Log.d("MainActivity", "calling initComponents()");
        buttonStartBackgroundservice = findViewById(R.id.button_startBackgroundservice);
        buttonStartForegroundservice = findViewById(R.id.button_startForegroundservice);
        buttonStopBackgroundservice = findViewById(R.id.button_stopBackgroundservice);
        buttonStopForegroundservice = findViewById(R.id.button_stopForegroundservice);
        buttonGetNumber = findViewById(R.id.button_getNumber);
        editTextInputNumber = findViewById(R.id.editText_inputNumber);
    }
    
    public void initOnClick() {
        Log.d("MainActivity", "calling initOnClick()");
        buttonStartBackgroundservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startService(new Intent(getBaseContext(), IsBackgroundservice.class));
                startService(new Intent(MainActivity.this, IsServiceOwnThread.class));
            }
        });

        buttonStartForegroundservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IsForegroundservice.class);
                if(editTextInputNumber.getText().toString() != null) {
                    intent.putExtra("data_number", editTextInputNumber.getText().toString());
                }
                ContextCompat.startForegroundService(getBaseContext(), intent);
            }
        });

        buttonStopBackgroundservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(MainActivity.this, IsServiceOwnThread.class));
                //stopService(new Intent(getBaseContext(), IsBackgroundservice.class));        
            }
        });

        buttonStopForegroundservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(getBaseContext(), IsForegroundservice.class));
            }
        });
        
        // Memanggil fungsi getRandomNumber pada class IsBinderService
        buttonGetNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bound) {
                    int number = isBinderService.getRandomNumber();
                    Toast.makeText(MainActivity.this, String.valueOf(number), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    
    private ServiceConnection connection = new ServiceConnection() {
        
        // Terhubung
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("ServiceConnection", "callback onServiceConnrcted()");
            LocalBinder binder = ((LocalBinder)service);
            isBinderService = binder.getService();
            bound = true;
        }
        
        // Koneksi sudah tidak terhubung
        @Override
        public void onServiceDisconnected(ComponentName p1) {
            bound = false;
        }
        
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "callback onCreate()");
        Log.d("MainActivity", String.valueOf(Thread.currentThread().getName()));
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
		    Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		    setSupportActionBar(toolbar);
            
            initComponents();
            initOnClick();
            
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity", "callback onStart()");
        Intent intentBinder = new Intent(MainActivity.this, IsBinderService.class);
        bindService(intentBinder, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity", "callback onStop()");
        // Hapus/lepas koneksi
        unbindService(connection);
        bound = false;
    }
    
}

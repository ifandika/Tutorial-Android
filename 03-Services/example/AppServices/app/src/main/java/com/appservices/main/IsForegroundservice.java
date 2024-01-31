package com.appservices.main;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.app.PendingIntent;
import android.app.Notification;
import android.app.NotificationChannel;
import android.content.Context;
import androidx.core.content.ContextCompat;
import android.widget.Button;
import androidx.core.text.util.FindAddress;
import androidx.core.app.NotificationCompat;
import android.widget.Toast;
import android.util.Log;

public class IsForegroundservice extends Service {
    IBinder ibinder;
    
    public void exampleDownloadFile() {
        Toast.makeText(this, "Downloading file...", Toast.LENGTH_SHORT).show();
        try {
            Thread.sleep(10000);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        Toast.makeText(this, "Download success...", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onCreate() {
        Log.d("IsForegroundservice", String.valueOf(Thread.currentThread().getName()));
        super.onCreate();
        Log.d("IsForegroundservice", "callback onCreate()");
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("IsForegroundservice", "callback onStartCommand()");
        Toast.makeText(IsForegroundservice.this, "Service started...", Toast.LENGTH_SHORT).show();
        String data = intent.getStringExtra("data_number");
        Intent notifIntent = new Intent(IsForegroundservice.this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
            IsForegroundservice.this, 0, notifIntent, 0
        );

        Notification notif = new NotificationCompat.Builder(IsForegroundservice.this, Applications.CHANNEL_ID)
            .setContentTitle("Example Service")
            .setContentText(data)
            .setContentIntent(pendingIntent)
            .build();
        startForeground(1, notif);
        exampleDownloadFile();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent p1) {
        Log.d("IsForegroundservice", "callback onBind()");
        return ibinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d("IsForegroundservice", "callback onRebind()");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("IsForegroundservice", "callback onUnbind()");
        return false;
    }

    @Override
    public void onDestroy() {
        Log.d("IsForegroundservice", "callback onDestroy()");
        Toast.makeText(IsForegroundservice.this, "Service stoped...", Toast.LENGTH_SHORT).show();
    }
    
   
}

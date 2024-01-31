package com.appservices.main;

import android.app.Activity;
import android.os.Bundle;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
import android.util.Log;

/**
 * Tambahkan atribut <service> dan nama class nya di manifest
 */
public class IsBackgroundservice extends Service {
    IBinder ibinder;
    
    @Override
    public void onCreate() {
        Log.d("IsBackgroundservice", String.valueOf(Thread.currentThread().getName()));
        super.onCreate();
        Log.d("IsBackgroundservice", "callback onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("IsBackgroundservice", "callback onStartCommand()");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent p1) {
        Log.d("IsBackgroundservice", "callback onBind()");
        return ibinder;
    }
    
    @Override
    public void onRebind(Intent intent) {
        Log.d("IsBackgroundservice", "callback onRebind()");
    }
    
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("IsBackgroundservice", "callback onUnbind()");
        return false;
    }

    @Override
    public void onDestroy() {
        Log.d("IsBackgroundservice", "callback onDestroy()");
        //stopSelf();
        Toast.makeText(this, "Service stoped...", Toast.LENGTH_SHORT).show();
    }
    
}

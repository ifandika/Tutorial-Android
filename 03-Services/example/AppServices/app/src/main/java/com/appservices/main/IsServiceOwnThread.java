package com.appservices.main;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import android.os.HandlerThread;
import android.os.Process;
import android.util.Log;

public class IsServiceOwnThread extends Service {
    Looper looper;
    ServiceHandler serviceHandler;
    
    private class ServiceHandler extends Handler {
        
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d("ServiceHandler", String.valueOf(Thread.currentThread().getName()));
            try {
                Thread.sleep(5000);
            } catch(Exception | InterruptedException e) {
                System.out.println(e.getMessage());
            }
            stopSelf(msg.arg1);
        }
        
    }

    @Override
    public void onCreate() {
        Log.d("IsServiceOwnThread", String.valueOf(Thread.currentThread().getName()));
        HandlerThread handlerThread = 
            new HandlerThread("ServiceStartArgument", Process.THREAD_PRIORITY_BACKGROUND);
        handlerThread.start();
        looper = handlerThread.getLooper();
        serviceHandler = new ServiceHandler(looper);
    }
    
    @Override
    public IBinder onBind(Intent p1) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Starting service...", Toast.LENGTH_SHORT).show();
        
        Message message = serviceHandler.obtainMessage();
        message.arg1 = startId;
        serviceHandler.sendMessage(message);
        
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Stoping service...", Toast.LENGTH_SHORT).show();
    }
    
}

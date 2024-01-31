package com.appservices.main;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Binder;
import java.util.Random;

public class IsBinderService extends Service {
    // Akan menjadi objek LocalBinder
    private final IBinder ibinder = new LocalBinder();
    private final Random random = new Random();
    
    // Class untuk klien
    public class LocalBinder extends Binder {
        
        IsBinderService getService() {
            return IsBinderService.this;
        }
    }
    
    // Fungsi yg disediakan untuk klien
    public int getRandomNumber() {
        return random.nextInt(100);
    }
    
    @Override
    public IBinder onBind(Intent p1) {
        return ibinder;
    }
    
}

package com.appintent.main;
import android.content.Intent;
import android.provider.AlarmClock;
import androidx.appcompat.app.AppCompatActivity;
import android.provider.MediaStore;
import android.net.Uri;

public class IsIntent {
    private AppCompatActivity activity;
    
    public IsIntent(AppCompatActivity activity) {
        this.activity = activity;
    }
    
    // Untuk cek apakah ada yg bisa menangani intent
    public boolean resolveMyIntent(Intent intent) {
        if(intent == null) throw new RuntimeException("Var intent is null");
        return (intent.resolveActivity(activity.getPackageManager()) != null);
    }
    
    /**
     * Membuat alarm dengan objek aksi AlarmClock
     *
     * @param hour = Nilai waktu jam
     * @param minutes = Nilai waktu menit
     * @param message = Pesan
     */
    public void isAlarm(int hour, int minutes, String message) {
        Intent intent = new Intent();
        intent.setAction(AlarmClock.ACTION_SET_ALARM)
            .putExtra(AlarmClock.EXTRA_HOUR, hour)
            .putExtra(AlarmClock.EXTRA_MINUTES, minutes)
            .putExtra(AlarmClock.EXTRA_MESSAGE, message)
            .putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        if(resolveMyIntent(intent)) {
            activity.startActivity(intent);
        }
    }
    
    public void isViewGallery() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("image/***");
        if(resolveMyIntent(intent)) {
            activity.startActivity(intent);
        }
    }
    
}

package com.exam.multithreads;
import android.os.SystemClock;
import android.widget.Toast;
import android.content.Context;
import android.util.Log;
import android.os.Handler;
import android.os.Looper;

public class ExamLooperThread extends Thread {
    Handler handler;
    Looper looper;
    
    @Override
    public void run() {
        if(Thread.interrupted()) {
            return;
        }
        // handler = new Handler(); Error: can't create handler inside thread with not called Looper.prepare()
        
        // Menyiapkan looper
        Looper.prepare();
        
        // Objek looper untuk hentikan proses thread.
        looper = Looper.myLooper();
        
        // Buat handler untuk mengirim perkerjaan ke MessageQueue.
        handler = new ExamHandler();
        
        // Perkerjaan yg sudah dikirim akan dijalankan oleh looper.
        Looper.loop();
        
        Log.d("ExamLooperThread", "End of thread");
    }
}

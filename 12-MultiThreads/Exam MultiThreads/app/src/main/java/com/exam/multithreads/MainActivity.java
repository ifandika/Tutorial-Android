package com.exam.multithreads;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.util.Log;
import android.os.SystemClock;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button buttonStartTask, buttonStopTask, buttonTaskA, buttonTaskB;
    ExamLooperThread examThread;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
        
        buttonStartTask = findViewById(R.id.button_startTask);
        buttonStopTask = findViewById(R.id.button_stopTask);
        buttonTaskA = findViewById(R.id.button_taskA);
        buttonTaskB = findViewById(R.id.button_taskB);
        
        examThread = new ExamLooperThread();
    }

    @Override
    protected void onStart() {
        super.onStart();
        int activeThread = Thread.currentThread().activeCount();
        Toast.makeText(
            getApplicationContext(),
            String.valueOf(activeThread),
            Toast.LENGTH_SHORT).show();
        buttonStartTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //new Thread(examThread).start();
                    examThread.start();
                    int activeThread = Thread.currentThread().activeCount();
                    Toast.makeText(
                        getApplicationContext(),
                        String.valueOf(activeThread),
                        Toast.LENGTH_SHORT).show();
                }
        });
        
        buttonStopTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Hentikan thread dengan panggil looper.
                    //examThread.looper.quit();
                    examThread.interrupt();
                    int activeThread = Thread.currentThread().activeCount();
                    Toast.makeText(
                        getApplicationContext(),
                        String.valueOf(activeThread),
                        Toast.LENGTH_SHORT).show();
                }
            });
            
        buttonTaskA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Message message = Message.obtain();
                    message.what = 1;
                    examThread.handler.sendMessage(message);
                    
                    /**Handler threadHandler = new Handler(examThread.looper);
                    //examThread.handler.post(new Runnable() {
                    threadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            for(int i = 0; i < 10; i++) {
                                Log.d("MainActivity", String.valueOf(i));
                                SystemClock.sleep(1000);
                            }
                        }
                    });*/
                    
                }
            });
            
        buttonTaskB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Message message = Message.obtain();
                    // Nilai berbeda dengan task A
                    message.what = 2;
                    examThread.handler.sendMessage(message);
                    
                }
            });
    }
    
}

package com.exam.multithreads;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ExamHandler extends Handler {
    private static final int TASK_A = 1;
    private static final int TASK_B = 2;
    private final String TAG = "ExamHandler";
    
    @Override
    public void handleMessage(Message msg) {
        switch(msg.what) {
            case TASK_A:
                Log.d(TAG, "Task A Executed");
                break;
            case TASK_B:
                Log.d(TAG, "Task B Executed");
                break;
            default:
                super.handleMessage(msg);
        }
    }
    
}

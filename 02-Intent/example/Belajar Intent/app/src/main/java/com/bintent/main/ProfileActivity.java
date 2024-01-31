package com.bintent.main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;
import android.view.View;

public class ProfileActivity extends AppCompatActivity {
    TextView textViewResult;
    Button buttonBackActivity;
    
    /**
     * Kirim respon menggunakan Intent dengan data "Ini data dari ProfileActivity" dan kunci/key
     * "result", response code "RESULT_OK" = 1.
     */
    private void returnSendingIntentWithResult() {
        Intent returnIntent = getIntent();
        returnIntent.putExtra("result", "Ini data dari ProfileActivity");
        setResult(RESULT_OK, returnIntent);
    }
    
    /**
     * Menangakap jika ada intent yg din kirim.
     */
    private void getSendingIntent() {
        if(getIntent() != null) {
            Intent intent = getIntent();
            String data = intent.getBundleExtra("data").getString("data");
            textViewResult.setText(data);
        }
    }
    
    private void getSendingIntentWithBundle() {
        if(getIntent() != null) {
            Intent intent = getIntent();
            String data = intent.getStringExtra("data");
            textViewResult.setText(data);
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        
        textViewResult = findViewById(R.id.textView_result);
        buttonBackActivity = findViewById(R.id.button_backToActivity);
        
        // getSendingIntent();
        getSendingIntentWithBundle();
    }

    @Override
    protected void onStart() {
        super.onStart();
        buttonBackActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnSendingIntentWithResult();
                finish();
            }
        });
    }
    
    
}

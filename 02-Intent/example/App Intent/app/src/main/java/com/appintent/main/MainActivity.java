package com.appintent.main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.provider.MediaStore;

public class MainActivity extends AppCompatActivity {
    private Button buttonClick;
    private ImageView imageViewMain;
    private IsIntent isIntent;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    
    public void isCapturePhoto() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        if(isIntent.resolveMyIntent(intent)) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = data.getParcelableExtra("data");
            imageViewMain.setImageBitmap(bitmap);
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
        
        isIntent = new IsIntent(MainActivity.this);
        
        imageViewMain = findViewById(R.id.imageView_main);
        buttonClick = findViewById(R.id.button_click);
    }

    @Override
    protected void onStart() {
        super.onStart();
        buttonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Button Clicked", Toast.LENGTH_SHORT).show();
                //isIntent.isAlarm(1, 5, "Ini  Alarm");
                //isIntent.isViewGallery();
                isCapturePhoto();
            }
        });
    }
    
    
}

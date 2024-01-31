package com.takephoto.main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.content.Intent;
import android.os.Build;
import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;
import android.content.ContentValues;
import android.provider.MediaStore;
import android.net.Uri;

public class MainActivity extends AppCompatActivity {
    private Button buttonTakePhoto;
    private ImageView imageViewResultPhoto;
    private final int MY_PERMISSION_CODE = 123;
    private final int MY_CAPTURE_IMAGE_CODE = 111;
    private Uri uri;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
        
        buttonTakePhoto = findViewById(R.id.button_takePhoto);
        imageViewResultPhoto = findViewById(R.id.imageView_resultPhoto);
    }

    @Override
    protected void onStart() {
        super.onStart();
        buttonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                       checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] myPermission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(myPermission, MY_PERMISSION_CODE);
                    }
                } else {
                    takePhoto();
                }
                takePhoto();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case MY_PERMISSION_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } 
                else {
                    Toast.makeText(getApplicationContext(), "Izin akses camera dan tulis penyimpanan ekternal dilarang", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            imageViewResultPhoto.setImageURI(uri);
        }
    }
    
    
    private void takePhoto() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "New Image");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "From camera");
        uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, MY_CAPTURE_IMAGE_CODE);
    }
    
}

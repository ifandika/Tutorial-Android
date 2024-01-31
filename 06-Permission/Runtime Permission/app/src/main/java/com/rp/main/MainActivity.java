package com.rp.main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final int MY_PERMISSION_REQUEST_READ_CONTACT = 1;
    
    private void requestReadContact() {
        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Request Runtime Permission")
                    .setMessage("Ijinkan untuk izin READ_CONTACTS")
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface di, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_CONTACTS}, MY_PERMISSION_REQUEST_READ_CONTACT);
                        }
                    });
                    alertDialog.setPositiveButton("Grant Permission", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface di, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_CONTACTS}, MY_PERMISSION_REQUEST_READ_CONTACT);
                        }
                    });
                    alertDialog.show();
            }
            else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_CONTACTS}, MY_PERMISSION_REQUEST_READ_CONTACT);
                Toast.makeText(getApplicationContext(), "Req", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "ksmsmsms", Toast.LENGTH_SHORT).show();
        }
    }
    
    // Fungsi menerima hasil dari meminta izin
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode) {
            // Jika hasil kode sama dengan kode kita
            case MY_PERMISSION_REQUEST_READ_CONTACT:
                // Jika panjang data > 0(Tidak kosong) dan nilai ke 0 sama dengan nilai/kode izin PERMISSION_GRANTED(Diizinkan)
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Yeah, permission is granted !!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Permission is not granted !!?", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
        
        requestReadContact();
    }
    
}

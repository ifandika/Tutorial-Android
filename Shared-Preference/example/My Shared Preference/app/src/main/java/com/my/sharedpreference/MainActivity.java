package com.my.sharedpreference;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;
import android.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {
    Button buttonSave, buttonLoadData;
    EditText editTextInputName, editTextInputPhoneNumber;
    
    private void setPreference(String key, String valString, int valInt) {
        // Objek untuk menyimpan data
        SharedPreferences sp = getApplicationContext().getSharedPreferences("MySP", MODE_PRIVATE);
        // Objek untuk editor
        SharedPreferences.Editor spEditor = sp.edit();
        spEditor.putString(key, valString);
        if(valInt != 0) {
            spEditor.putInt(key, valInt);
        }
        spEditor.apply();
    }
    
    private void loadPreference() {
        SharedPreferences sharePref = getApplicationContext().getSharedPreferences("MySP", MODE_APPEND);
        String name = sharePref.getString("name", "defaultValue");
        int phoneNumber = sharePref.getInt("phone_number", 0);
        // sharePref.getAll(); Ambil semua data.
        Toast.makeText(    
            getApplicationContext(),
                (" Data name: "+name+", phone number: "+String.valueOf(phoneNumber)),
                Toast.LENGTH_SHORT).show();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
        
        buttonSave = findViewById(R.id.button_save);
        buttonLoadData = findViewById(R.id.buttonLoadData);
        editTextInputName = findViewById(R.id.editText_inputName);
        editTextInputPhoneNumber = findViewById(R.id.editText_inputPhoneNumber);
        
    }

    @Override
    protected void onStart() {
        super.onStart();
        
        buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        setPreference("name", editTextInputName.getText().toString(), 0);
                        setPreference("phone_number", null, Integer.parseInt(editTextInputPhoneNumber.getText().toString()));
                        Toast.makeText(getApplicationContext(), "Success save data", Toast.LENGTH_SHORT).show();
                    }
                    catch(Exception e) {
                        e.getMessage();
                    }
                }
            });
            
        buttonLoadData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadPreference();
                }
            });
    }
    
}

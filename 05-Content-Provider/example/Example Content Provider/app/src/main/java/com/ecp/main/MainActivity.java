package com.ecp.main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.content.ContentValues;
import android.database.Cursor;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button buttonSaveData, buttonLoadData, buttonUpdateData, buttonDeleteData;
    EditText editTextInputData;
    TextView textViewResultData;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
        
        buttonSaveData = findViewById(R.id.button_saveData);
        buttonLoadData = findViewById(R.id.button_loadData);
        buttonUpdateData = findViewById(R.id.button_updateData);
        buttonDeleteData = findViewById(R.id.button_deleteData);
        
        editTextInputData = findViewById(R.id.editText_inputData);
        
        textViewResultData = findViewById(R.id.textView_viewResultData);
        
    }

    @Override
    protected void onStart() {
        super.onStart();
        
        buttonSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put(MyContentProvider.NAME, editTextInputData.getText().toString());
                getContentResolver().insert(MyContentProvider.CONTENT_URI, cv);
            }
        });
        buttonUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put(MyContentProvider.NAME, "New Name");
                String selection = MyContentProvider.COLUMN_NAME+"=?";
                String[] selectionArgs = {editTextInputData.getText().toString()};
                int result = getContentResolver().update(MyContentProvider.CONTENT_URI, cv, selection, selectionArgs);
                Toast.makeText(getApplicationContext(), String.valueOf(result), Toast.LENGTH_SHORT).show();
            }
        });
        buttonDeleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = getContentResolver().delete(
                    MyContentProvider.CONTENT_URI,
                    MyContentProvider.COLUMN_NAME+"=?",
                    new String[]{editTextInputData.getText().toString()}
                );
                Toast.makeText(getApplicationContext(), String.valueOf(result), Toast.LENGTH_SHORT).show();
            }
        });
        buttonLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = getContentResolver().query(MyContentProvider.CONTENT_URI, null, null, null, null);
                if(cursor.moveToFirst()) {
                    StringBuilder sb = new StringBuilder();
                    while(!cursor.isAfterLast()) {
                        sb.append(
                            "\n"+cursor.getString(cursor.getColumnIndex(MyContentProvider.COLUMN_ID))
                            +" - "+cursor.getString(cursor.getColumnIndex(MyContentProvider.COLUMN_NAME)));
                        cursor.moveToNext();
                    }
                    textViewResultData.setText(sb);
                }
            }
        });
    }
    
}

package com.b.contentprovider.main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.content.ContentValues;
import android.widget.Toast;
import android.database.Cursor;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;

public class MainActivity extends AppCompatActivity {
    private Button buttonSaveData, buttonLoadData;
    private EditText editTextInputData;
    private TextView textViewResultData;
    private ContentValues cv = new ContentValues();
    
    
    private void initOnClick() {
        buttonSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cv.put(MyContentProvider.name, editTextInputData.getText().toString());
                
                // inserting into database through content URI
                getContentResolver().insert(MyContentProvider.CONTENT_URI, cv);
                
                // displaying a toast message
                Toast.makeText(getBaseContext(), "New Record Inserted", Toast.LENGTH_LONG).show();
                
            }
        });
        buttonLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // creating a cursor object of the
                // content URI
                Cursor cursor = getContentResolver().query(Uri.parse("content://com.demo.mycontentprovider/users"), null, null, null, null);

                // iteration of the cursor
                // to print whole table
                if(cursor.moveToFirst()) {
                    StringBuilder strBuild=new StringBuilder();
                    while (!cursor.isAfterLast()) {
                        strBuild.append("\n"+cursor.getString(cursor.getColumnIndex("id"))+ "-"+ cursor.getString(cursor.getColumnIndex("name")));
                        cursor.moveToNext();
                    }
                    textViewResultData.setText(strBuild);
                }
                else {
                    textViewResultData.setText("No Records Found");
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
        
        buttonSaveData = findViewById(R.id.button_saveData);
        buttonLoadData = findViewById(R.id.button_loadData);

        editTextInputData = findViewById(R.id.editText_inputData);

        textViewResultData = findViewById(R.id.textView_viewResultData);   
    }

    @Override
    protected void onStart() {
        super.onStart();
        initOnClick();
    }
    
}

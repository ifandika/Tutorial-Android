package com.crud.sqlite.main;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View;
import android.content.ContentValues;
import com.crud.sqlite.main.dbase.MyCRUD;
import android.database.Cursor;

public class MainActivity extends AppCompatActivity {
    private Button buttonAddData, buttonUpdateData, buttonDeleteData;
    private TextView textViewContainerData;
    private EditText editTextInputId, editTextInputName;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
        textViewContainerData = findViewById(R.id.textView_containerData);
        
        editTextInputId = findViewById(R.id.editText_inputId);
        editTextInputName = findViewById(R.id.editText_inputName);
        
        buttonAddData = findViewById(R.id.button_addData);
        buttonUpdateData = findViewById(R.id.button_updateData);
        buttonDeleteData = findViewById(R.id.button_deleteData);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshData();
        buttonAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();
                cv.put(MyCRUD.COLUMN_NAME, editTextInputName.getText().toString());
                getContentResolver().insert(MyCRUD.CONTENT_URI, cv);
                refreshData();
            }
        });
        
        buttonUpdateData.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    ContentValues cv = new ContentValues();
                    cv.put(MyCRUD.COLUMN_NAME, editTextInputName.getText().toString());
                    getContentResolver().update(
                        MyCRUD.CONTENT_URI,
                        cv,
                        (MyCRUD.COLUMN_ID+" = ?"),
                        new String[]{editTextInputId.getText().toString()}
                    );
                }
            });
            
        buttonDeleteData.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    getContentResolver().delete(
                        MyCRUD.CONTENT_URI,
                        (MyCRUD.COLUMN_ID+" = ?;"),
                        new String[]{editTextInputId.getText().toString()}
                    );
                }
            });
    }
    
    // Cek pembaruan di database.
    private void refreshData() {
        try {
            Cursor cursorTemp = getContentResolver().query(MyCRUD.CONTENT_URI, null, null, null, null);
            if(cursorTemp.moveToFirst() && cursorTemp != null) {
                StringBuilder sb = new StringBuilder();
                while(!cursorTemp.isAfterLast()) {
                    sb.append(
                        "\n"+
                        cursorTemp.getInt(cursorTemp.getColumnIndex(MyCRUD.COLUMN_ID))
                        +" - "+
                        cursorTemp.getString(cursorTemp.getColumnIndex(MyCRUD.COLUMN_NAME))
                    );
                    cursorTemp.moveToNext();
                }
                textViewContainerData.setText(sb);
            }
        }
        catch(Exception e) {
            e.getMessage();
        }
    }
}

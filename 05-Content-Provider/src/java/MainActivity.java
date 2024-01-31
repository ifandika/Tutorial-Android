public class MainActivity extends AppCompatActivity {
	
	private Button buttonSaveData, buttonLoadData;
    private EditText editTextInputData;
    private TextView textViewResultData;
    private ContentValues cv = new ContentValues();
    
    private void initOnClick() {
        buttonSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	// Ambil data yg dimasukan
                cv.put(MyContentProvider.name, editTextInputData.getText().toString());
                
                // Memasukan data ke database
                getContentResolver().insert(MyContentProvider.CONTENT_URI, cv);
                
                // Tampilkan pesan
                Toast.makeText(getBaseContext(), "New Record Inserted", Toast.LENGTH_LONG).show();
            }
        });
        buttonLoadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	// Objek Cursor untuk mengambil data sekaligus menyimpan hasil kueri.
                Cursor cursor = getContentResolver().query(Uri.parse("content://com.demo.mycontentprovider/users"), null, null, null, null);
                
                // Arahkan cursor ke paling pertama
                if(cursor.moveToFirst()) {
                    StringBuilder strBuild=new StringBuilder();
                    // Cek jika selanjutnya tidak kosong
                    while (!cursor.isAfterLast()) {
                        strBuild.append("\n"+cursor.getString(cursor.getColumnIndex("id"))+ "-"+ cursor.getString(cursor.getColumnIndex("name")));
                        cursor.moveToNext();
                    }
                    textViewResultData.setText(strBuild);
                }
                else {
                    textViewResultData.setText("Tidak ada data");
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
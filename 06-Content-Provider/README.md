# Content Provider
```java
public class MyContentProvider extends {
	
	@Override
	public void () {
		
	}
	...
}
```
- Digunakan untuk __mengakses data, menyimpan data apk, pusat untuk data apk. Berkerja di data akses layer.__
## Alur
![Alur Content Provider]("https://github.com/ifandika/Tutorial-Android/blob/master/06-Content-Provider/ic_flow_contentprovider.jpg")
### Content URI(Uniform Resource Identifier)
- Konsep utama pada content provider.
```
content://pemilik/path(opsional)/path_id(opsional)
```
- __content__ = Jenis jalur yg digunakan, semisal http/ftp/dll.
- __pemilik__ = Nama pemilik data yg akan dituju.
- __path__ = Untuk membedakan jenis data yg di tuju, semisal data gambar pd folder gambar, video pd folder video.
- __path_id__ = Jika ingin mengakses data tertentu, semisal akses data gambar ke ..., video ke ... .
## Operasi dasar
- [x] __CREATE__
- [x] __READ__ 
- [x] __UPDATE__
- [x] __DELETE__
## Alur akses data.
![Alur Content Provider]("https://github.com/ifandika/Tutorial-Android/blob/master/06-Content-Provider/ic_flow_access_data.jpg")
## Demo
- ### Kode MainActivity
```java
	...
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
            	// Objek Cursor untuk mengambil data
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
```
- ### Kode MyContentProvider
```java
public class MyContentProvider extends ContentProvider {
    public MyContentProvider() {}
    
    static final String PROVIDER_NAME = "com.demo.mycontentprovider";
    static final String URL = "content://" + PROVIDER_NAME + "/users";
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final String id = "id";
    static final String name = "name";
    static final int uriCode = 1;
    static final UriMatcher uriMatcher;
    private static HashMap<String, String> values;
    
    // Untuk operasi ke database.
    private SQLiteDatabase db;
    
    // Nama database.
    static final String DATABASE_NAME = "UserDB";
    
    // Nama tabel.
    static final String TABLE_NAME = "Users";

    // (Opsional)Versi database.
    static final int DATABASE_VERSION = 1;

    // SQL statement untuk membuat tabel.
    static final String CREATE_DB_TABLE = "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + " name TEXT NOT NULL);";
    
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // Untuk akses data.
        uriMatcher.addURI(PROVIDER_NAME, "users", uriCode);
        // Untuk akses data tertentu
        uriMatcher.addURI(PROVIDER_NAME, "users/*", uriCode);
    }
    
    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        if (db != null) {
            return true;
        }
        return false;
    }
    
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case uriCode:
                return "vnd.android.cursor.dir/users";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
    
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_NAME);
        switch (uriMatcher.match(uri)) {
            case uriCode:
                qb.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (sortOrder == null || sortOrder == "") {
            sortOrder = id;
        }
        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }
    
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = db.insert(TABLE_NAME, "", values);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLiteException("Gagal tambah data" + uri);
    }
    
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case uriCode:
                count = db.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
    
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case uriCode:
                count = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
    
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        
        // Buat tabel.
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }
        
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Hapus/drop tabel.
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
    ...
}
```
- ### Kode activity_main
```xml
	...
	<EditText
		android:layout_height="wrap_content"
		android:layout_width="match_parent"
		android:ems="10"
		android:id="@+id/editText_inputData"
		android:layout_gravity="center"
		android:layout_marginTop="100dp"
		android:hint="Masukan data"/>
		
	<Button
		android:layout_height="wrap_content"
		android:layout_width="wrap_content"
		android:text="Simpan"
		android:id="@+id/button_saveData"
		android:layout_gravity="center"
		android:layout_marginTop="100dp"/>
		
	<Button
		android:layout_height="wrap_content"
		android:layout_width="wrap_content"
		android:text="Muat data"
		android:layout_gravity="center"
		android:id="@+id/button_loadData"/>
		
	<TextView
		android:layout_height="match_parent"
		android:layout_width="match_parent"
		android:hint="Hasil"
		android:gravity="center"
		android:layout_gravity="center"
		android:id="@+id/textView_viewResultData"/>
	...
```
- ### Kode Manifest
```xml
	...
	<activity
       android:name=".MainActivity"
       android:label="@string/app_name">
       <intent-filter>
       		<action android:name="android.intent.action.MAIN"/>
            <category android:name="android.intent.category.LAUNCHER"/>
        </intent-filter>
    </activity>
    
	<provider
		android:name=".MyContentProvider"
		android:authorities="com.demo.mycontentprovider"
		android:enabled="true"
		android:exported="true">
	</provider>
	...
```
## Catatan

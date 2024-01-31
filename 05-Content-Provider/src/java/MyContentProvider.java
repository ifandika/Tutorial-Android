import android.content.ContentProvider;
import android.content.ContentValues;
import android.os.Bundle;
import android.net.Uri;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;
import android.content.ContentUris;
import android.database.sqlite.SQLiteException;

public class MyContentProvider extends ContentProvider {
	
	// URI provider(Alamat database).
    static final String PROVIDER_NAME = "com.demo.mycontentprovider";
    
    // URI final(Alamat lengkap).
    static final String URL = "content://" + PROVIDER_NAME + "/users";
    
    // Masukan ke objek Uri.
    static final Uri CONTENT_URI = Uri.parse(URL);
    
    // Kolom id.
    static final String COLUMN_ID = "id";
    
    // Kolom name.
    static final String COLUMN_NAME = "name";
    
    // Kode untuk path tertentu, misalnya untuk path /users kode = 1.
    static final int URI_CODE_USERS = 1;
    
    // Objek untuk membandingkan alamat yg akan dituju.
    static final UriMatcher uriMatcher;
    
    // Untuk utama operasi ke database.
    private SQLiteDatabase db;
    
    // Nama database.
    static final String DATABASE_NAME = "UserDB";
    
    // Nama tabel.
    static final String TABLE_NAME = "Users";

    // (Opsional)Versi database.
    static final int DATABASE_VERSION = 1;

    // SQL statement untuk membuat tabel.
    static final String CREATE_DB_TABLE = "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + " name TEXT NOT NULL);";
    
    private static HashMap<String, String> values;
    
    public MyContentProvider() {}
    
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // Tetapkan URI path /users dengan kode @uriCode
        uriMatcher.addURI(PROVIDER_NAME, "users", URI_CODE_USERS);
        // Tetapkan URI path /users/* dengan kode @uriCode
        uriMatcher.addURI(PROVIDER_NAME, "users/*", URI_CODE_USERS);
    }
    
    /**
     * Fungsi pertama yg dipanggil ketika objek ContentProvider dibuat.
     */
    @Override
    public boolean onCreate() {
        DatabaseHelper dbHelper = new DatabaseHelper(getContext());
        // Buat database
        db = dbHelper.getWritableDatabase();
        // Cek database telah dibuat ?
        return db != null ? true:false;
    }
    
    /**
     * Fungsi untuk MIME tipe.
     * @uri		Alamat sumber data.
     */
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case uriCode:
                return ContentResolver.CURSOR_DIR_BASE_TYPE+"/users";
                // return ContentResolver.ContentResolver.CURSOR_ITEM_BASE_TYPE = Untuk satu saja data yg akan di akses
                // return "vnd.android.cursor.dir/users";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
    
    /**
     * Fungsi untuk query(Ambil data).
     * @uri				Alamat data/sumber.
     * @projection		Kolom yg dipilih.
     * @selection		Untuk kondisi(WHERE).
     * @selectionArgs	...
     * @sortOrder		Untuk sorting ASC/DESC
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		// Objek khusus untuk kueri.
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		// Tetapkan nama tabel yg akan di kueri
        qb.setTables(TABLE_NAME);
		// Cek uri dari klien, jika sama maka sukses.
		switch (uriMatcher.match(uri)) {
			case URI_CODE_USERS:
				// Data akan di sorting by id
				if (sortOrder == null || sortOrder == "") {
					sortOrder = COLUMN_ID;
				}
				Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
				cursor.setNotificationUri(getContext().getContentResolver(), uri);
				return cursor;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }
    
    /**
     * Fumgsi untuk menambahkan data.
     * @uri		Alamat tempat penyimpanan data.
     * @values	Data yg dimasukan.
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
		// Hasil berupa nilai data ke berapa.
        long rowID = db.insert(TABLE_NAME, "", values);
        if (rowID > 0) {
            Uri uriResult = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(uriResult, null);
            // Hasil berupa spesifik uri dari data yg dimasukan.
            return uriResult;
        }
        throw new SQLiteException("Gagal tambah data" + uri);
    }
    
    /**
     * Fungsi memodif data.
     * @uri				Alamat data/sumber.
     * @values			Data yg akan diperbarui.
     * @selection		Kondisi untuk cek data mana yg akan diperbarui( MY_COLUMN_NAME = ? ), tanda ? adalah nilai dari input, memasukan nya lewat 
     *					selectionArgs.
     * @selectionArgs	Untuk memasukan nilai ke kondisi.
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case URI_CODE_USERS:
                count = db.update(TABLE_NAME, values, whereClause, whereArgs);
                break;
            default: throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
    
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)) {
            case URI_CODE_USERS:
                count = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            default: throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
    
    // Class untuk membantu membuat/menghapus database.
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        
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
}
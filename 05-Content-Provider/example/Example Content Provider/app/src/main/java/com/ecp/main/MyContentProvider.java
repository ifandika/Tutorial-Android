package com.ecp.main;

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
import android.database.sqlite.SQLiteQueryBuilder;
import android.content.ContentResolver;

public class MyContentProvider extends ContentProvider {
    
    static final String DB_NAME = "MyDB";
    static final String TABLE_NAME = "exampleDB";
    static final String PROVIDER_NAME = "com.example.ecp.provider";
    static final Uri CONTENT_URI = Uri.parse("content://"+PROVIDER_NAME+"/users");
    static final String COLUMN_ID = "id";
    static final String COLUMN_NAME = "name";
    static final int USERS_URI_CODE = 123;
    static final String NAME = "name";
    
    private final String TAG = "MyContentProvider";
    
    private static UriMatcher uriMatcher;
    
    private SQLiteDatabase dbase;
    
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "users", USERS_URI_CODE);
        uriMatcher.addURI(PROVIDER_NAME, "users/*", USERS_URI_CODE);
    }
    
    public MyContentProvider() {}
    
    @Override
    public boolean onCreate() {
        Log.d(TAG, "Callback onCreate()");
        DBaseHelper helper = new DBaseHelper(getContext());
        dbase = helper.getWritableDatabase();
        return dbase != null ? true:false;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "Callback insert()");
        long rowId = dbase.insert(TABLE_NAME, "", values);
        if(rowId > 0) {
            Log.d(TAG+"-> insert()", "Value rowId = "+rowId);
            Uri uriResult = ContentUris.withAppendedId(CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(uriResult, null);
            Log.d(TAG+"-> insert()", "Value uriResult = "+uriResult.toString());
            return uriResult;
        }
        throw new  SQLiteException("Failed to add a record into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] p2, String p3, String[] p4, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);
        
        switch(uriMatcher.match(uri)) {
            case USERS_URI_CODE:
                if(sortOrder == null || sortOrder == "") {
                    sortOrder = COLUMN_ID;
                }
                Cursor cursor = queryBuilder.query(dbase, p2, p3, p4, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(), uri);
                return cursor;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String whereClause, String[] whereArgs) {
        int count = 0;
        switch(uriMatcher.match(uri)) {
            case USERS_URI_CODE:
                count = dbase.delete(TABLE_NAME, whereClause, whereArgs);
                break;
            default: throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues cv, String whereClause, String[] whereArgs) {
        int count = 0;
        switch(uriMatcher.match(uri)) {
            case USERS_URI_CODE:
                count = dbase.update(TABLE_NAME, cv, whereClause, whereArgs);
                break;
            default: throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return count;
    }

    @Override
    public String getType(Uri uri) {
        return "shappl";
        /*
        switch(uriMatcher.match(uri)) {
            case USERS_URI_CODE: 
                Log.d(TAG, "Method getType() -> case USERS_URI_CODE");
                return ContentResolver.CURSOR_DIR_BASE_TYPE+"/users";
                //!return "vnd.android.cursor.dir/users";
            default: 
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }*/
    }
    
    private class DBaseHelper extends SQLiteOpenHelper {
        
        public DBaseHelper(Context context) {
            super(context, DB_NAME, null, 1);
        }
        
        @Override
        public void onCreate(SQLiteDatabase database) {
            String query = String.format(
                "CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL);",
                TABLE_NAME, COLUMN_ID, COLUMN_NAME);
            database.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int p2, int p3) {
            database.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(database);
        }
        
    }
}

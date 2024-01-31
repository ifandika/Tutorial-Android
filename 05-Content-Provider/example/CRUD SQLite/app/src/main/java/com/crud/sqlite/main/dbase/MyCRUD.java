package com.crud.sqlite.main.dbase;

import android.content.ContentProvider;
import android.os.CancellationSignal;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.UriMatcher;
import android.content.ContentUris;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;
import android.database.sqlite.SQLiteQueryBuilder;

public class MyCRUD extends ContentProvider {
    public static final String DBASE_NAME = "dbase";
    public static final String TABLE_NAME = "table_crud";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String PROVIDER_NAME = "com.crud.provider";
    public static String URL = "content://"+PROVIDER_NAME+"/users";
    public static Uri CONTENT_URI = Uri.parse(URL);
    public static final int USERS_URI_CODE = 123;
    
    private SQLiteDatabase dbase;
    private static UriMatcher uriMatcher;
    private final String TAG = "MyCRUD";
    
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "users", USERS_URI_CODE);
    }
    
    private class CRUDHelper extends SQLiteOpenHelper {
        public CRUDHelper(Context context) {
            super(context, DBASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase valDbase) {
            String create = String.format(
                "CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL);",
                TABLE_NAME, COLUMN_ID, COLUMN_NAME);
            valDbase.execSQL(create);
        }

        @Override
        public void onUpgrade(SQLiteDatabase valDbase, int p2, int p3) {
            valDbase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(valDbase);
        }
    }
    
    @Override
    public boolean onCreate() {
        dbase = new CRUDHelper(getContext()).getWritableDatabase();
        generateToast("Create Content Provider");
        if(dbase != null) {
            return true;
        }
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if(uri == null) throw new SQLiteException("Parameter value uri is null");
        switch(uriMatcher.match(uri)) {
            case USERS_URI_CODE:
                if(sortOrder == null || sortOrder == "") {
                    sortOrder = COLUMN_ID;
                }
                SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
                qb.setTables(TABLE_NAME);
                Cursor cursorRes = qb.query(dbase, projection, selection, selectionArgs, null, null, sortOrder);
                cursorRes.setNotificationUri(getContext().getContentResolver(), uri);
                generateToast("Success query data");
                return cursorRes;
            default:
                throw new SQLiteException("Error query unknown uri");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if(uri == null || values == null) throw new SQLiteException("Value parameter is nulll");
        long row = dbase.insert(TABLE_NAME, "", values);
        if(row > 0) {
            Uri uriResult = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(uri, null);
            Log.d(TAG, "Success insert data");
            generateToast("Success insert data");
            return uriResult;
        }
        throw new SQLiteException("Failed to add a record into " + uri);
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String whereClause, String[] whereArgs) {
        if(uri == null) throw new SQLiteException("Parameter value uri is null");
        if(contentValues == null) throw new SQLiteException("Parameter value contentValues is null");
        int res = 0;
        switch(uriMatcher.match(uri)) {
            case USERS_URI_CODE:
                res = dbase.update(TABLE_NAME, contentValues, whereClause, whereArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                generateToast("Success update data");
                return res;
            default:
                throw new SQLiteException("Error query unknown uri");
        }
    }

    @Override
    public int delete(Uri uri, String whereClause, String[] whereArgs) {
        if(uri == null || whereClause == null || whereArgs == null) throw new SQLiteException("Parameter value is null");
        int res = 0;
        switch(uriMatcher.match(uri)) {
            case USERS_URI_CODE:
                res = dbase.delete(TABLE_NAME, whereClause, whereArgs);
                getContext().getContentResolver().notifyChange(uri, null);
                generateToast("Success delete data");
                return res;
            default:
                throw new SQLiteException("Error query unknown uri");
        }
    }

    @Override
    public String getType(Uri uri) {
    	switch (uriMatcher.match(uri)) {
            case USERS_URI_CODE:
                return "vnd.android.cursor.dir/users";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
    
    private void generateToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
    
}

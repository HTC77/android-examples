package com.example.androidexamples.database;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "store.db";
    private static String DB_PATH;
    private final Context mContext;
    private SQLiteDatabase mDatabase;
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        if(Build.VERSION.SDK_INT > 15)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = Environment.getDataDirectory() + "/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void checkAndCopyDatabase(){
        boolean dbExist = checkDatabase();
        if(dbExist){
            Log.d(TAG, "checkAndCopyDatabase: database already exist");
            return;
        }
        getReadableDatabase();
        try {
            copyDatabase();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "checkAndCopyDatabase: ERRor copy database",e);
        }
    }

    private boolean checkDatabase(){
        String myPath = DB_PATH + DB_NAME;
        SQLiteDatabase myDB=null;
        try {
            myDB = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
        }catch (SQLiteException e){
            e.printStackTrace();
            Log.e(TAG, "checkDatabase: ERRor Open Database",e);
        }
        if(myDB!=null)
            myDB.close();
        return myDB!=null ? true : false;
    }

    private void copyDatabase() throws IOException {
        InputStream inputStream = mContext.getAssets().open(DB_NAME);
        String outputPath = DB_PATH + DB_NAME;
        OutputStream outputStream = new FileOutputStream(outputPath);
        int length = 0;
        byte[] buffer = new byte[1024];
        while ((length = inputStream.read(buffer))>0)
            outputStream.write(buffer,0,length);
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    public void openDatabase(){
        String dbPath = DB_PATH + DB_NAME;
        mDatabase = SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close(){
        if(mDatabase!=null)
            mDatabase.close();
        super.close();
    }
    public Cursor execQuery(String query){
        return mDatabase.rawQuery(query,null);
    }
}
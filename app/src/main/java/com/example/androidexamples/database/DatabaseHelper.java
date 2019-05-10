package com.example.androidexamples.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHelper {
    public static final String DBNAME = "sample.db";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context mContext) {
        this.mContext = mContext;
    }

    private void openDatabase(){
        if(mDatabase!= null && mDatabase.isOpen())
            return;
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        mDatabase = SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READWRITE);
    }
    private void closeDatabase(){
        if (mDatabase!=null)
            mDatabase.close();
    }
}

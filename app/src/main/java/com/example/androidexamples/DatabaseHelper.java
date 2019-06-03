package com.example.androidexamples;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DatabaseName = "DatabaseStudent";
    public static final String keyTableName = "TableStudent";
    public static final String keyId = "ID";
    public static final String keyName = "NAME";
    public static final String keyPass = "PASSWORD";
    public static final String CREATE_TABLE = "CREATE TABLE "+ keyTableName +
            " ("+ keyId +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ keyName +" VARCHAR(255) ,"+ keyPass +" VARCHAR(225));";
    public DatabaseHelper(Context context){
        super(context, DatabaseName, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + keyTableName);
        onCreate(sqLiteDatabase);
    }
}

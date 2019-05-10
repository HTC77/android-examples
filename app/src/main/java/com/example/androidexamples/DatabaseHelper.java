package com.example.androidexamples;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public final static String DB_NAME="dbPhoneBook.db";
    public final static String mTableName="mTable1";
    public final static String mId = "id";
    public final static String mName = "name";
    public final static String mNumber = "number";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+mTableName +"("+mId+" text,"+mName+" text,"+mNumber+" text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insert(String id, String name, String number){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(mId,id);
        cv.put(mName,name);
        cv.put(mNumber,number);
        return db.insert(mTableName,null,cv);
    }

    public long update(String id, String name, String number){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(mName,name);
        cv.put(mNumber,number);
        return db.update(mTableName,cv,mId+" = '"+id+"'",null);
    }
    public long delete(String id){
        return getWritableDatabase().delete(mTableName,mId+ " = '"+id+"'",null);
    }

    public Cursor showAll(){
        return getReadableDatabase().rawQuery("SELECT * FROM "+mTableName,null);
    }
    public Cursor search(String id){
        return getReadableDatabase().rawQuery("SELECT * FROM "+mTableName+" WHERE "+mId+" = '"+id+"'" ,null);
    }
}

package com.example.androidexamples;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

class DatabaseAdapter {
    private DatabaseHelper databaseHelper;
    private Context context;
    private String nameExist;
    private long id;

    public DatabaseAdapter(Context context) {
        databaseHelper = new DatabaseHelper(context);
        this.context = context;
    }

    public long insert(String name, String pass){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String[] columns = {databaseHelper.keyName};
        Cursor cursor = database.query(databaseHelper.keyTableName, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            nameExist = cursor.getString(cursor.getColumnIndex(databaseHelper.keyName));
        }
        if(nameExist == null || !nameExist.equals(name)) {
            values.put(databaseHelper.keyName, name);
            values.put(databaseHelper.keyPass, pass);
            id = database.insert(databaseHelper.keyTableName, null, values);
        }else{
            Toast.makeText(context, "اطلاعات  موجود می باشد.", Toast.LENGTH_SHORT).show();
        }

        return id ;
    }

    public String getDate(){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        String[] columns = {databaseHelper.keyId, databaseHelper.keyName, databaseHelper.keyPass};
        Cursor cursor = database.query(databaseHelper.keyTableName, columns, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()){
            int id =cursor.getInt(cursor.getColumnIndex(databaseHelper.keyId));
            String name =cursor.getString(cursor.getColumnIndex(databaseHelper.keyName));
            String  password =cursor.getString(cursor.getColumnIndex(databaseHelper.keyPass));
            buffer.append(id+ "   " + name + "   " + password +" \n");
        }
        return buffer.toString();
    }

    public int updateName(String oldName, String newName){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(databaseHelper.keyName, newName);
        String[] whereArgs = {oldName};
        int count = database.update(databaseHelper.keyTableName, values, databaseHelper.keyName + " = ?", whereArgs);
        return count;
    }

    public  int delete(String name){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] whereArgs ={name};

        int count =db.delete(databaseHelper.keyTableName, databaseHelper.keyName +" = ?",whereArgs);
        return  count;
    }
}

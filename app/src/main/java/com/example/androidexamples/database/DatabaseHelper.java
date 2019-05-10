package com.example.androidexamples.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.androidexamples.model.Product;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {
    public static final String DBNAME = "sample2.db";
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
    public List<Product> getProductList(){
        List<Product> productList = new ArrayList<>();
        Product product = null;
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM product",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            product = new Product(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3));
            productList.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return productList;
    }
}

package com.example.androidexamples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidexamples.adapter.ProductListAdapter;
import com.example.androidexamples.database.DatabaseHelper;
import com.example.androidexamples.model.Product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvProduct;
    private File database;
    private List<Product> productList;
    private ProductListAdapter adapter;
    private DatabaseHelper mDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDBHelper = new DatabaseHelper(this);
        lvProduct = findViewById(R.id.lvProduct);

        //check if database exists
        database = getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        if(database.exists() == false){
            if(copyDatabase())
                Toast.makeText(this, "Succesfully copied", Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(this, "Error in copy database", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        productList = mDBHelper.getProductList();
        adapter = new ProductListAdapter(this,productList);
        lvProduct.setAdapter(adapter);
    }

    boolean copyDatabase(){
        try {
            InputStream inputStream = this.getAssets().open(DatabaseHelper.DBNAME);
            OutputStream outputStream = new FileOutputStream(database.getPath());
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buffer))>0){
                outputStream.write(buffer);
            }
            outputStream.flush();
            outputStream.close();
            Log.v("MainActivity","db copied");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

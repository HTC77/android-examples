package com.example.androidexamples;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.androidexamples.adapter.OnTabListener;
import com.example.androidexamples.adapter.ProductAdapter;
import com.example.androidexamples.database.DatabaseHelper;
import com.example.androidexamples.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseHelper mDBhelper;
    private List<Product> productList;
    private ProductAdapter adapter;
    private Cursor cursor;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        loadDatabase();
    }

    private void loadDatabase() {
        mDBhelper = new DatabaseHelper(this);
        productList = new ArrayList<>();
        try {
            mDBhelper.checkAndCopyDatabase();
            mDBhelper.openDatabase();
        }catch (SQLiteException e){
            Log.e("Main_Activity", "loadDatabase: Error check or copy database", e);
        }

        try {
            cursor = mDBhelper.execQuery("SELECT * FROM product");
            if(cursor!= null && cursor.moveToFirst()){
                do {
                    Product product = new Product(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3));
                    productList.add(product);
                }while (cursor.moveToNext());
            }
        }catch (SQLiteException e){
            e.printStackTrace();
            Log.e("Main_Activity", "loadDatabase: Error in qery data", e);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adapter = new ProductAdapter(this,productList);
        adapter.notifyDataSetChanged();
        adapter.setOnTabListener(new OnTabListener() {
            @Override
            public void onTapView(int position) {
                Toast.makeText(MainActivity.this, "selected: "+productList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}

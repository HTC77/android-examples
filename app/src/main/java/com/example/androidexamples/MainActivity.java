package com.example.androidexamples;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnSave, btnEdit,btnDelete, btnSearch, btnShowAll;
    private EditText etId, etName, etNumber;
    private DatabaseHelper mDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnEdit = findViewById(R.id.btnEdit);
        btnSearch = findViewById(R.id.btnSearch);
        btnShowAll = findViewById(R.id.btnShowAll);

        etId = findViewById(R.id.etID);
        etName = findViewById(R.id.etName);
        etNumber = findViewById(R.id.etNumber);

        mDBHelper = new DatabaseHelper(this);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDBHelper.insert(etId.getText().toString(),etName.getText().toString(),etNumber.getText().toString())!= -1)
                    Toast.makeText(MainActivity.this, "با موفقیت ذخیره شد", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "خطا در ذخیره اطلاعات", Toast.LENGTH_SHORT).show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDBHelper.update(etId.getText().toString(),etName.getText().toString(),etNumber.getText().toString()) != -1)
                    Toast.makeText(MainActivity.this, "با موفقیت بروز رسانی شد", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "خطا در بروز رسانی اطلاعات", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDBHelper.delete(etId.getText().toString()) != -1)
                    Toast.makeText(MainActivity.this, "با موفقیت حذف شد", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "خطا در حذف", Toast.LENGTH_SHORT).show();
            }
        });

        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer message = new StringBuffer();
                Cursor cursor =  mDBHelper.showAll();
                if(cursor!=null && cursor.moveToFirst()){
                    do{
                        message.append(cursor.getString(0) + '\n' + cursor.getString(1) +'\n'+ cursor.getString(2));
                        message.append('\n');
                        message.append('\n');
                    }while(cursor.moveToNext());
                }
                showData(message.toString(),"دفترچه تلفن");
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer message = new StringBuffer();
                Cursor cursor = mDBHelper.search(etId.getText().toString());
                if(cursor != null && cursor.moveToFirst()){
                    do{
                        message.append(cursor.getString(0)+'\n' + cursor.getString(1) + '\n'+cursor.getString(2)+'\n');
                        message.append('\n');
                    }while (cursor.moveToNext());
                }
                showData(message.toString(),"نتیجه جستجو");
            }
        });
    }

    void showData(String message, String title){
        AlertDialog.Builder alert= new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setCancelable(true);
        alert.show();
    }
}

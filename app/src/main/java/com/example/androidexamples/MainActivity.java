package com.example.androidexamples;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etPass;
    private Button btnAdd, btnUpdate, btnDelete;
    private ListView list;
    private ArrayList<StudentModel> studentList = new ArrayList<>();
    private StudentListAdapter studentListAdapter;
    private DatabaseHelper databaseHelper;
    private long id;
    private StudentModel studentModel;
    private String nameList, passList, nameExist;
    private int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        databaseHelper = new DatabaseHelper(this);
        getData();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = etName.getText().toString();
                String pass = etPass.getText().toString();
                if (name.isEmpty() && pass.isEmpty()) {
                    studentModel = studentList.get(i);
                    nameList = studentModel.getName();
                    passList = studentModel.getPass();
                    etName.setText(nameList);
                    etPass.setText(passList);
                }else {
                    etName.setText("");
                    etPass.setText("");
                    studentModel = studentList.get(i);
                    nameList = studentModel.getName();
                    passList = studentModel.getPass();
                    etName.setText(nameList);
                    etPass.setText(passList);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etName.getText().toString();
                if(name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "اطلاعات را وارد کنید.", Toast.LENGTH_SHORT).show();
                }
                else{
                    int a = delete(name);
                    if(a<=0) {
                        Toast.makeText(getApplicationContext(), "خطا در حذف اطلاعات", Toast.LENGTH_SHORT).show();
                        etName.setText("");
                        etPass.setText("");
                    }
                    else {
                        studentList.clear();
                        getData();
                        studentListAdapter.notifyDataSetChanged();

                        Toast.makeText(getApplicationContext(), "اطلاعات با موفقیت حذف شد.", Toast.LENGTH_SHORT).show();
                        etName.setText("");
                        etPass.setText("");
                    }
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = etName.getText().toString();
                String pass = etPass.getText().toString();
                if (name.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "اطلاعات را وارد کنید.", Toast.LENGTH_SHORT).show();
                } else {
                    long id = insert(name, pass);
                    if (id <= 0) {
                        Toast.makeText(MainActivity.this, "خطا در ثبت اطلاعات", Toast.LENGTH_SHORT).show();
                        etName.setText("");
                        etPass.setText("");
                    } else {
                        studentList.clear();
                        getData();
                        studentListAdapter.notifyDataSetChanged();

                        Toast.makeText(MainActivity.this, "اطلاعات با موفقیت ثبت شد. ", Toast.LENGTH_SHORT).show();
                        etName.setText("");
                        etPass.setText("");
                    }
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = etName.getText().toString();
                String newPass = etPass.getText().toString();
                if(newName.isEmpty() || newPass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "اطلاعات را وارد کنید.", Toast.LENGTH_SHORT).show();
                }
                else{
                    int a = updateName(nameList, newName, newPass);
                    if(a<=0 ) {
                        Toast.makeText(getApplicationContext(), "خطا در ویرایش اطلاعات", Toast.LENGTH_SHORT).show();
                        etName.setText("");
                        etPass.setText("");
                    }
                    else {
                        studentList.clear();
                        getData();
                        studentListAdapter.notifyDataSetChanged();

                        Toast.makeText(getApplicationContext(), "اطلاعات با موفقیت ویرایش شد.", Toast.LENGTH_SHORT).show();
                        etName.setText("");
                        etPass.setText("");
                    }
                }
            }
        });

        studentListAdapter = new StudentListAdapter(this, studentList);
        list.setAdapter(studentListAdapter);
    }

    public long insert(String name, String pass){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        String[] columns = {databaseHelper.keyName};
        Cursor cursor = database.query(databaseHelper.keyTableName, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            nameExist = cursor.getString(cursor.getColumnIndex(databaseHelper.keyName));
            if(nameExist.equals(name)) {
                a++;
            }
        }
        if (nameExist == null || a <= 0){
            values.put(databaseHelper.keyName, name);
            values.put(databaseHelper.keyPass, pass);
            id = database.insert(databaseHelper.keyTableName, null, values);
            a = 0;
        }else{
            Toast.makeText(getApplicationContext(), "اطلاعات  موجود می باشد.", Toast.LENGTH_SHORT).show();
            id = 0;
            a = 0;
        }



        return id ;
    }

    public  int delete(String name){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] whereArgs ={name};

        int count =db.delete(databaseHelper.keyTableName, databaseHelper.keyName +" = ?",whereArgs);
        return  count;
    }

    public int updateName(String oldName, String newName, String newPass){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(databaseHelper.keyName, newName);
        values.put(databaseHelper.keyPass, newPass);
        String[] whereArgs = {oldName};
        int count = database.update(databaseHelper.keyTableName, values, databaseHelper.keyName + " = ? " , whereArgs);
        return count;
    }

    public void getData(){
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        String[] columns = {databaseHelper.keyId, databaseHelper.keyName, databaseHelper.keyPass};
        Cursor cursor = database.query(databaseHelper.keyTableName, columns, null,null,null,null,null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(databaseHelper.keyId));
            String name = cursor.getString(cursor.getColumnIndex(databaseHelper.keyName));
            String  password = cursor.getString(cursor.getColumnIndex(databaseHelper.keyPass));
            studentList.add(new StudentModel(name,password, String.valueOf(id)));
        }
    }

    void findView() {
        etName = findViewById(R.id.etName);
        etPass = findViewById(R.id.etPass);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        list =  findViewById(R.id.listStudent);
    }
}

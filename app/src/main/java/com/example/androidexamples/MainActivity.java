package com.example.androidexamples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etPass, etOldNameUpdate, etNewNameUpdate, etDelete;
    private Button btnAdd, btnUpdate, btnDelete, btnView;
    private DatabaseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();

        adapter = new DatabaseAdapter(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String pass = etPass.getText().toString();
                if (name.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "اطلاعات را وراد کنید.", Toast.LENGTH_SHORT).show();
                } else {
                    long id = adapter.insert(name, pass);
                    if (id <= 0) {
                        Toast.makeText(MainActivity.this, "خطا در ثبت اطلاعات.", Toast.LENGTH_SHORT).show();
                        etName.setText("");
                        etPass.setText("");
                    } else {
                        Toast.makeText(MainActivity.this, "اطلاعات با موفقیت ثبت شد. ", Toast.LENGTH_SHORT).show();
                        etName.setText("");
                        etPass.setText("");
                    }
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = adapter.getDate();
                Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldName = etOldNameUpdate.getText().toString();
                String newName = etNewNameUpdate.getText().toString();
                if(oldName.isEmpty() || newName.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "اطلاعات را وراد کنید.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int a= adapter.updateName( oldName, newName);
                    if(a<=0)
                    {
                        Toast.makeText(MainActivity.this, "اطلاعات ویرایش نشد.", Toast.LENGTH_SHORT).show();
                        etOldNameUpdate.setText("");
                        etNewNameUpdate.setText("");
                    } else {
                        Toast.makeText(MainActivity.this, "اطلاعات با موفقیت ویرایش شد.", Toast.LENGTH_SHORT).show();
                        etOldNameUpdate.setText("");
                        etNewNameUpdate.setText("");
                    }
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etDelete.getText().toString();
                if(name.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "اطلاعات را وراد کنید.", Toast.LENGTH_SHORT).show();
                }
                else{
                    int a= adapter.delete(name);
                    if(a<=0)
                    {
                        Toast.makeText(MainActivity.this, "خطا در حذف اطلاعات.", Toast.LENGTH_SHORT).show();
                        etDelete.setText("");
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "اطلاعات با موفقیت حذف شد.", Toast.LENGTH_SHORT).show();
                        etDelete.setText("");
                    }
                }
            }
        });

    }
    void findView() {
        etName = findViewById(R.id.etName);
        etPass = findViewById(R.id.etPass);
        etOldNameUpdate = findViewById(R.id.etOldNameUpdate);
        etNewNameUpdate = findViewById(R.id.etNewNameUpdate);
        etDelete = findViewById(R.id.edtNameDelete);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnView = findViewById(R.id.btnView);
        btnDelete = findViewById(R.id.btnDelete);
    }
}

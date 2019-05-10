package com.example.androidexamples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button btnSave, btnRetrieve;
    private EditText etText;
    private TextView tvText;
    private CheckBox chkAppend;
    private int writeMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeMode = chkAppend.isChecked() ? MODE_APPEND : MODE_PRIVATE;
                writeFile("a.txt");
            }
        });
        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFile("a.txt");
            }
        });
        readFile("a.txt");
    }

    private void findViews() {
        btnSave = findViewById(R.id.btnSave);
        btnRetrieve = findViewById(R.id.btnRetreive);
        etText = findViewById(R.id.etText);
        tvText = findViewById(R.id.tvFileContents);
        chkAppend = findViewById(R.id.chbAppend);
    }

    private void writeFile(String name){
        try {
            FileOutputStream outputStream = openFileOutput(name,writeMode);
            outputStream.write((etText.getText().toString()+'\n').getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "خطا در نوشتن در فایل", Toast.LENGTH_SHORT).show();
        }
    }
    private void readFile(String name){
        try {
            FileInputStream inputStream = openFileInput(name);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            tvText.setText(new String(buffer));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "خطا در خواندن فایل", Toast.LENGTH_SHORT).show();
        }
    }
}

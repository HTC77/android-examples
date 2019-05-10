package com.example.androidexamples;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private Button btnSave, btnLoad;
    private EditText etText;
    private static final int READ_BLOCK_SIZE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=etText.getText().toString();
                try{
                    //--SD Card Storage--
                    File sdCard= Environment.getExternalStorageDirectory();
                    File directory = new File(sdCard.getAbsolutePath()+"/MyFiles");
                    directory.mkdirs();
                    File file =new File(directory,"textFile.txt");
                    FileOutputStream fOut=new FileOutputStream(file);
                    //--SD Card

                    //     FileOutputStream fOut=openFileOutput("textFile.txt",MODE_WORLD_READABLE); //Internal
                    OutputStreamWriter osw=new OutputStreamWriter(fOut);
                    //--Write the String to the file--
                    osw.write(str);osw.flush();osw.close();
                    //--display file saved message--
                    Toast.makeText(getBaseContext(),"file saved successfully",Toast.LENGTH_SHORT).show();
                    //--Clears the EditText--
                    etText.setText("");
                }catch (IOException ioe){
                    Toast.makeText(MainActivity.this, "ERR: "+ ioe.getMessage(), Toast.LENGTH_LONG).show();
                    ioe.printStackTrace();
                }
            }
        });
        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //--SD Card Storage--
                    File sdCard= Environment.getExternalStorageDirectory();
                    File directory = new File(sdCard.getAbsolutePath()+"/MyFiles");
                    directory.mkdirs();
                    File file =new File(directory,"textFile.txt");
                    FileInputStream fln=new FileInputStream(file);
                    InputStreamReader isr=new InputStreamReader(fln);
                    char[] inputBuffer=new char[READ_BLOCK_SIZE];
                    String s="";
                    int charRead;
                    while ((charRead=isr.read(inputBuffer))>0){
                        //--Convert the chars to String--
                        String readString = String.copyValueOf(inputBuffer,0,charRead);
                        s+=readString;
                        inputBuffer=new char[READ_BLOCK_SIZE];
                    }
                    //--set The EditText to the text has been
                    //read--
                    etText.setText(s);
                    Toast.makeText(getBaseContext(),"File loded successfully",Toast.LENGTH_SHORT).show();
                } catch (IOException ioe) {
                    Toast.makeText(MainActivity.this, "ERR: "+ ioe.getMessage(), Toast.LENGTH_LONG).show();
                    ioe.printStackTrace();
                }
            }
        });
    }

    private void findViews() {
        btnSave = findViewById(R.id.btnSave);
        btnLoad = findViewById(R.id.btnLoad);
        etText = findViewById(R.id.et1);
    }
}

package com.example.androidexamples;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Button btn = findViewById(R.id.btn_MainActivity);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("age3",45);
                i.setData(Uri.parse("in matn ra az activity dovom daryaft kardim"));
                setResult(RESULT_OK,i);
                finish();
            }
        });
    }

    public void onClick(View v){
        final EditText etNumber1 = findViewById(R.id.editText1);
        final EditText etNumber2 = findViewById(R.id.editText2);
        if (etNumber1.getText().toString().trim().isEmpty()
                || etNumber2.getText().toString().trim().isEmpty())
        {
            Toast.makeText(this, "Enter a number", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent= new Intent(getBaseContext(),MainActivity.class);
        intent.putExtra("Number1",etNumber1.getText().toString());
        intent.putExtra("Number2",etNumber2.getText().toString());
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == 1){
            if(data.hasExtra("Result")){
                EditText  etNumber3 = findViewById(R.id.editText3);
                etNumber3.setText(Integer.toString(data.getExtras().getInt("Result")));
                etNumber3.setKeyListener(null);
            }
        }
    }
}

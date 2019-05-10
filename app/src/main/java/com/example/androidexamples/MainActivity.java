package com.example.androidexamples;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button btnSend;
    private EditText etMessage;
    private String contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        contact = "+989034962777";
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms: "+ contact));
                Bundle data = new Bundle();
                data.putString("sms_body",etMessage.getText().toString());
                intent.putExtras(data);
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        btnSend = findViewById(R.id.btnSend);
        etMessage = findViewById(R.id.etMessage);
    }
}

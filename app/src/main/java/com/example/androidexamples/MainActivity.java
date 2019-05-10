package com.example.androidexamples;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView img1,img2;
    private Button btnImg1,btnImg2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        btnImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);
            }
        });
        btnImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                img1.setImageBitmap(bitmap);
                break;
            case  2:
                img2.setImageBitmap((Bitmap)data.getExtras().get("data"));
                break;
        }
    }

    private void findViews() {
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        btnImg1 = findViewById(R.id.btnImg1);
        btnImg2 = findViewById(R.id.btnImg2);
    }
}

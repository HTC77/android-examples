package com.example.androidexamples;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imageView;
    private EditText etTitle;
    private Button btnChoose, btnUpload;
    private final int IMG_REQUSET = 7;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        btnChoose.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
    }

    private void findViews() {
        imageView = findViewById(R.id.imageView);
        etTitle = findViewById(R.id.etImageTitle);
        btnChoose = findViewById(R.id.btnChoose);
        btnUpload = findViewById(R.id.btnUpload);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnChoose:
                selectImage();
                break;

            case R.id.btnUpload:
                uploadImage();
                break;
        }
    }

    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUSET);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUSET && resultCode == RESULT_OK && data != null){
            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(),data.getData());
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
                etTitle.setVisibility(View.VISIBLE);
                btnUpload.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }

    private void uploadImage(){
        String image = imageToString();
        String title = etTitle.getText().toString();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ImageModel> call = apiInterface.uploadImage(title,image);
        call.enqueue(new Callback<ImageModel>() {
            @Override
            public void onResponse(Call<ImageModel> call, Response<ImageModel> response) {
                ImageModel imageModel = response.body();
                Toast.makeText(MainActivity.this,
                        "Server response: "+imageModel.getResponse(),
                        Toast.LENGTH_LONG).show();
                imageView.setVisibility(View.GONE);
                btnUpload.setEnabled(false);
                etTitle.setText("");
                etTitle.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ImageModel> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "ERROR IN SERVER ",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
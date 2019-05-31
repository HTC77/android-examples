package com.example.androidexamples;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private CharSequence items[] = {"Google", "Apple", "Microsoft"};
    private   boolean[] itemChecked = new boolean[items.length];
    private ProgressDialog progressDialog;
    private boolean isServiceStarted;
    private Button btnService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemChecked[1] = true;
        isServiceStarted = false;
        btnService = findViewById(R.id.btnService);
    }

    public void onBtnDialogClick(View v) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("In ghesmat title ra vared mikonad")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "Ok ra click kardid", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "cancel ra click kardid", Toast.LENGTH_SHORT).show();
            }
        }).setMultiChoiceItems(items, itemChecked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                Toast.makeText(getBaseContext(), items[which] + " ra Entekhab kardid", Toast.LENGTH_SHORT).show();
            }
        }).show();
    }

    public void onBtnProgressClick(View v) {
        progressDialog = ProgressDialog.show(
                this, "Title ra inja vared mikonid ",
                " peygham ra inja vared mikonid", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    progressDialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void onBtnDetailedProgressClick(View v) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(android.R.drawable.ic_dialog_info);
        progressDialog.setTitle("Downloading files...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "Ok clicked!", Toast.LENGTH_SHORT).show();
                    }
                });
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
                "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "Cancel clicked!", Toast.LENGTH_SHORT).show();
                    }
                });
        progressDialog.show();
        progressDialog.setProgress(0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <=15; i++) {
                    try {
                        Thread.sleep(1000);
                        progressDialog.incrementProgressBy((int)100/15);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                progressDialog.dismiss();
            }
        }).start();
    }

    public void onBtnTimeViewClick(View v){
        startActivity(new Intent(this,CustomComponentActivity.class));
    }
    public void onBtnServiceClick(View v){
        Intent intent = new Intent(this,MyService.class);
        if(!isServiceStarted){
          startService(intent);
          btnService.setText("Stop Service");
        }
        else{
          stopService(intent);
          btnService.setText("Start Service");
        }
        isServiceStarted = !isServiceStarted;
    }
    public void onBtnBroadcastClick(View v){
        Intent intent = new Intent(this,MyReceiver.class);
        sendBroadcast(intent);
    }
    public void onBtnDragDropClick(View v){
        startActivity(new Intent(this,DragDropActivity.class));
    }
}
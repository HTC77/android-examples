package com.example.androidexamples;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String EXTRA_NOTIFICATION_ID = "NotifId";
    private static final String ACTION_SNOOZE = "mAndroidExamples.intent.action.mReceiver";
    private CharSequence items[] = {"Google", "Apple", "Microsoft"};
    private   boolean[] itemChecked = new boolean[items.length];
    private ProgressDialog progressDialog;
    private boolean isServiceStarted;
    private Button btnService;
    private int notificationID=1;
    private final static String CHANNEL_ID="1";
    private int numMessages=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemChecked[1] = true;
        isServiceStarted = false;
        btnService = findViewById(R.id.btnService);
        createNotificationChannel(); // for API >= 26
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
    public void onBtnNotificationClick(View v){
        displayNotification();
    }public void onBtnBigNotificationClick(View v){
        displayNotification2();
    }
    public void onBtnActionNotificationClick(View v){
        displayNotification3();
    }

    private void displayNotification() {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, NotificationViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(this,CHANNEL_ID);
        mBuilder.setSmallIcon(R.drawable.ic_launcher1);
        mBuilder.setContentTitle("System Alarm");
        mBuilder.setContentText("Hi, This is Android Notification Detail!");
        mBuilder.setPriority(NotificationManager.IMPORTANCE_DEFAULT);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setVibrate(new long[]{100,250,100,500});

        NotificationManagerCompat nm = NotificationManagerCompat.from(this);
        nm.notify(notificationID,mBuilder.build());
    }

    private void displayNotification2() {
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, NotificationViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationViewActivity.class);
        // Adds the intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent( 0,  PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(this,CHANNEL_ID);
        mBuilder.setSmallIcon(R.drawable.ic_launcher1);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.vmn);
        mBuilder.setLargeIcon(largeIcon);
        mBuilder.setContentTitle("Notification Alert, Click me");
        mBuilder.setContentText("Hi, This is Android Notification Detail!");
        mBuilder.setTicker("New Message Alert!");
        mBuilder.setPriority(NotificationManager.IMPORTANCE_DEFAULT);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setVibrate(new long[]{100,250,100,500});
        // Increase notification number every time a new notification arrives
        mBuilder.setNumber(++numMessages);

        // Add Big View Specific Configuration
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        String[] events = new String[6];
        events[0] = "This is first line...";
        events[1] = "This is second line...";
        events[2] =  "This is third line...";
        events[3] = "This is 4th line...";
        events[4] = "This is 5th line...";
        events[5] = "This is 6th line...";
        // Sets a title for the Inbox style big view
        inboxStyle.setBigContentTitle("Big title details: ");
        // Moves events into the big view
        for (int i = 0; i < events.length; i++)
            inboxStyle.addLine(events[i]);
        mBuilder.setStyle(inboxStyle);

        NotificationManagerCompat nm = NotificationManagerCompat.from(this);
        nm.notify(notificationID,mBuilder.build());
    }

    private void displayNotification3() {
        Intent snoozeIntent = new Intent(this, MyReceiver.class);
        snoozeIntent.setAction(ACTION_SNOOZE);
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(this, NotificationViewActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(this,CHANNEL_ID);
        mBuilder.setSmallIcon(R.drawable.ic_launcher1);
        mBuilder.setContentTitle("System Alarm");
        mBuilder.setContentText("Hi, This is Android Notification Detail!");
        mBuilder.setPriority(NotificationManager.IMPORTANCE_DEFAULT);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setVibrate(new long[]{100,250,100,500});
        mBuilder.addAction(R.drawable.ic_snooze, getString(R.string.snooze),
                snoozePendingIntent);

        NotificationManagerCompat nm = NotificationManagerCompat.from(this);
        nm.notify(notificationID,mBuilder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class  MainActivity2 extends AppCompatActivity {
    Button btn;
    final static String CHANNEL_ID="channel id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btn=findViewById(R.id.test);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushNotification();

            }
        });



    }

    private void pushNotification() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,"notif", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("This is the discription");
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Intent intent=new Intent(this,MainActivity2.class);
        PendingIntent p=PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_bell)
                .setContentTitle("Profitez")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(p)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("This is a test "));

        NotificationManagerCompat notif=NotificationManagerCompat.from(this);
        notif.notify(10,builder.build());




    }
}
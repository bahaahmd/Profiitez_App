package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import Adapter.NotificationAdapter;

public class notification_setting_client extends AppCompatActivity {
    String  nom[]={"My Account","Pramotional Notifications"};
    String prenom[]={"You will receive daily updates","You will receive daily updates"};
    int image[]= {R.drawable.notification_client, R.drawable.notification_client};
    ListView listView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_setting_client);
        listView= (ListView) findViewById(R.id.listview4);
        NotificationAdapter customBaseAdapter=new NotificationAdapter(getApplicationContext(),nom,prenom,image);
        listView.setAdapter(customBaseAdapter);


        imageView=(ImageView) findViewById(R.id.imageView3);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity();
            }

            public void openActivity() {
                Intent intent=new Intent(notification_setting_client.this,settings_client.class);
                startActivity(intent);
                finish();
            }
        });
    }

}

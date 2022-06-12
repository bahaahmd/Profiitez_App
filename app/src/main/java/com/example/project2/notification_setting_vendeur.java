package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import Adapter.NotificationAdapter;

public class notification_setting_vendeur extends AppCompatActivity {
    String  nom[]={"Mon compte"," Notifications promotionnels"};
    String prenom[]={"You will receive daily updates","You will receive daily updates"};
    int image[]= {R.drawable.ic_notification__1_, R.drawable.ic_notification__1_};
    ListView listView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_setting_vendeur);
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
                Intent intent=new Intent(notification_setting_vendeur.this,setting_vendeur.class);
                startActivity(intent);
                finish();
            }
        });
    }

}

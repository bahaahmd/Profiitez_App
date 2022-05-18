package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import Adapter.ProfileAdapter;

public class settings_client extends AppCompatActivity {
    String  nom[]={"Account","Notification","Change Address",};
    int image[]= {R.drawable.profil_user_client, R.drawable.notification_client, R.drawable.home_client};
    ListView listView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_client);
        listView= (ListView) findViewById(R.id.list_view);
        ProfileAdapter profileAdapter =new ProfileAdapter(getApplicationContext(),nom,image);
        listView.setAdapter(profileAdapter);



    }
}


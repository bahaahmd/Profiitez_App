package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import Adapter.ProfileAdapter;

public class setting_client extends AppCompatActivity {
    String  nom[]={"Account","Notification",};
    int image[]= {R.drawable.profil_user_client,R.drawable.notification_client};
    ListView listView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_client);
        listView= (ListView) findViewById(R.id.list_view);
        ProfileAdapter customBaseAdapter=new ProfileAdapter(getApplicationContext(),nom,image);
        listView.setAdapter(customBaseAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("ic_setting_", "item is clicked @ Position::" + i);
                if (i == 0) {
                    startActivity(new Intent(setting_client.this, account_setting_client.class));

                }

                    else {if (i == 1) {
                        startActivity(new Intent(setting_client.this, notification_setting_client.class));

                    }}

                };
        });
        imageView=(ImageView) findViewById(R.id.imageView3);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity();
            }

            public void openActivity() {
                Intent intent=new Intent(setting_client.this,profil_client.class);
                startActivity(intent);
                finish();
            }
        });
    }
}




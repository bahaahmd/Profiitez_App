
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

public class setting_vendeur extends AppCompatActivity {
        String  nom[]={"Mon compte","Notification","Change l'adresse",};
        int image[]= {R.drawable.ic_profile_user, R.drawable.ic_notification__1_, R.drawable.ic_home__4_};
        ListView listView;
        ImageView imageView;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.settingsvendeur);
            listView= (ListView) findViewById(R.id.list_view);
            ProfileAdapter customBaseAdapter=new ProfileAdapter(getApplicationContext(),nom,image);
            listView.setAdapter(customBaseAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.i("ic_setting_", "item is clicked @ Position::" + i);
                    if (i == 0) {
                        startActivity(new Intent(setting_vendeur.this, account_setting_vendeur.class));
                    } else {
                        if (i == 2) {
                            startActivity(new Intent(setting_vendeur.this, change_adress.class));
                            finish();
                        } else {if (i == 1) {
                            startActivity(new Intent(setting_vendeur.this, notification_setting_vendeur.class));
                            finish();
                        }}

                    };}
            });
            imageView=(ImageView) findViewById(R.id.imageView3);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openActivity();
                }

                public void openActivity() {
                    Intent intent=new Intent(setting_vendeur.this,ActivityVendeur.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }


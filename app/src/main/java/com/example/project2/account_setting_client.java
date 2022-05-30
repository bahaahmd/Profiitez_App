package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import Adapter.ProfileAdapter;

public class account_setting_client extends AppCompatActivity {
    String  nom[]={"Sécurité","Désactiver le compte"};
    int image[]= {R.drawable.secure_client,R.drawable.delete_client};
    ListView listView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_setting_client);
        listView= (ListView) findViewById(R.id.list_view);
        ProfileAdapter profileAdapter =new ProfileAdapter(getApplicationContext(),nom,image);
        listView.setAdapter(profileAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("ic_setting_","item is clicked @ Position::"+i);
                if (i==0){
                    startActivity(new Intent(account_setting_client.this,security_setting_client.class));
                    finish();
                }else
                if(i==1) {


                    showalert();


                }


            }

            private void showalert() {
                AlertDialog.Builder builder= new AlertDialog.Builder(account_setting_client.this,R.style.AlertDialogTheme);
                View view= LayoutInflater.from(account_setting_client.this).inflate(R.layout.dialog_layout_client,(ConstraintLayout)findViewById(R.id.layoutdialogcontainer));
                builder.setView(view);
                ((TextView)view.findViewById(R.id.texttitle)).setText("WARNING");
                ((TextView)view.findViewById(R.id.textmessage)).setText("Are u sure to delete your account?");
                ((Button)view.findViewById(R.id.buttonactionyes)).setText("yes");
                ((Button)view.findViewById(R.id.buttonactionno)).setText("No");
                final AlertDialog alertDialog=builder.create();
                view.findViewById(R.id.buttonactionyes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        Toast.makeText(account_setting_client.this,"deleted",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(account_setting_client.this,essai.class);
                        startActivity(intent);
                    }

                });
                view.findViewById(R.id.buttonactionno).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        Toast.makeText(account_setting_client.this,"not deleted",Toast.LENGTH_SHORT).show();

                    }

                });

                alertDialog.show();

            }
        });
        imageView=(ImageView) findViewById(R.id.arriere);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity();
            }

            public void openActivity() {
                Intent intent=new Intent(account_setting_client.this,setting_client.class);
                startActivity(intent);
                finish();
            }
        });


    }}
package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class security_setting_vendeur extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView;
    EditText edt;
    EditText edt2;
    EditText edt3;
    TextView txt;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_setting_vendeur);

        edt=findViewById(R.id.editTextTextPassword);
        edt2=findViewById(R.id.editTextTextPassword2);
        edt3=findViewById(R.id.editTextTextPassword3);
        txt=findViewById(R.id.textView6);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.getText().clear();
                edt3.getText().clear();
                edt2.getText().clear();
            }
        });




        b=findViewById(R.id.buttonupdate);
        b.setOnClickListener(this);

    }

    public void onClick(View view) {
        Toast.makeText(security_setting_vendeur.this, "mot pass changé", Toast.LENGTH_SHORT).show();
        edt=findViewById(R.id.editTextTextPassword);
        edt2=findViewById(R.id.editTextTextPassword2);
        edt3=findViewById(R.id.editTextTextPassword3);
        Toast.makeText(security_setting_vendeur.this, "en maintenance", Toast.LENGTH_LONG).show();


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.getText().clear();
                edt3.getText().clear();
                edt2.getText().clear();
            }
        });
        imageView=(ImageView) findViewById(R.id.arriere);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity();
            }

            public void openActivity() {
                Intent intent=new Intent(security_setting_vendeur.this,account_setting_vendeur.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

}


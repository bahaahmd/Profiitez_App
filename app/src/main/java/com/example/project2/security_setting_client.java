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

public class security_setting_client extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView;
    EditText edt;
    EditText edt2;
    EditText edt3;
    TextView txt;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secuity_setting_client);

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
        Toast.makeText(security_setting_client.this, "password updated", Toast.LENGTH_SHORT).show();
        edt=findViewById(R.id.editTextTextPassword);
        edt2=findViewById(R.id.editTextTextPassword2);
        edt3=findViewById(R.id.editTextTextPassword3);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.getText().clear();
                edt3.getText().clear();
                edt2.getText().clear();
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}


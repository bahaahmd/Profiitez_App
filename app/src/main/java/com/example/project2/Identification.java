package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Identification extends AppCompatActivity {
    Button log, cnx;
    EditText email, pass;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identification);
        mAuth = FirebaseAuth.getInstance();
        log = findViewById(R.id.SinscrireButton_identification);
        cnx = findViewById(R.id.ConnexionButton_identificatiion);
        email = findViewById(R.id.Email_identification);
        pass = findViewById(R.id.Password_identification);
        cnx = findViewById(R.id.ConnexionButton_identificatiion);

        log.setOnClickListener(view ->
        {
            startActivity(new Intent(Identification.this, Inscription.class));
        });




        cnx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
                //startActivity(new Intent(Identification.this, HomePage.class));

            }
        });



    }
    private void loginUser() {
        String nEmail = email.getText().toString();
        String nPass = pass.getText().toString();

        if (TextUtils.isEmpty(nEmail)) {
            email.setError("champ vide");
            email.requestFocus();
        } else if (TextUtils.isEmpty(nPass)) {
            pass.setError("champ vide");
            pass.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(nEmail, nPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(Identification.this, HomePage.class));
                        finish();
                        Toast.makeText(Identification.this, "User logged in successfully", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Identification.this, "Log in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }

}
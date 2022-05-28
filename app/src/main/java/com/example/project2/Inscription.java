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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Inscription extends AppCompatActivity {
    EditText UserName, email, PhoneNumber, password, ConfPass;
    Button inscrire;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inscription);
        Button connecter = (Button) findViewById(R.id.SeConnecter_inscription);
        UserName = findViewById(R.id.Nom_inscription);
        email = findViewById(R.id.Email_inscription);
        PhoneNumber = findViewById(R.id.Numero_inscription);
        password = findViewById(R.id.Password_inscription);
        ConfPass = findViewById(R.id.ConfirmPassword_inscription);
        inscrire = findViewById(R.id.Inscrire_inscription);

        mAuth = FirebaseAuth.getInstance();

        inscrire.setOnClickListener(view -> {
            createUser();
        });
        connecter.setOnClickListener(view -> {
            startActivity(new Intent(Inscription.this, Identification.class));
        });

    }

    private void createUser() {
        String userName=UserName.getText().toString();
        String nEmail = email.getText().toString();
        String nPassword = password.getText().toString();
        String nom = UserName.getText().toString();
        String numero = PhoneNumber.getText().toString();
        String confirmer = ConfPass.getText().toString();
        if (TextUtils.isEmpty(nom)) {
            UserName.setError("champ vide");
            UserName.requestFocus();
        } else if (numero.isEmpty()) {
            PhoneNumber.setError("champ vide");
            PhoneNumber.requestFocus();
        } else if (!Patterns.PHONE.matcher(numero).matches()) {
            PhoneNumber.setError("numéro de téléphone doit contenir que des chiffres");
            PhoneNumber.requestFocus();
        } else if (numero.length() < 10) {
            PhoneNumber.setError("numéro de téléphone doit contenir 10 chiffres");
        }

        else if (TextUtils.isEmpty(nEmail)) {
            email.setError("champ vide");
            email.requestFocus();
        } else if (TextUtils.isEmpty(nPassword)) {
            password.setError("champ vide");
            password.requestFocus();
        } else if (TextUtils.isEmpty(confirmer)) {
            password.setError("champ vide");
            password.requestFocus();
        } else if (!nPassword.equals(confirmer)) {
            ConfPass.setError("réconfirmer votre mot de passe correctement");
            ConfPass.setText("");
        } else {
            mAuth.createUserWithEmailAndPassword(nEmail, nPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = user1.getUid();
                        DatabaseReference users = FirebaseDatabase.getInstance().getReference().child("Users");
                        Date date=new Date();
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("'Le ' dd/MM 'à' hh:mma");

                        user user2=new user(uid,userName,simpleDateFormat.format(date),null,"https://previews.123rf.com/images/microphoto1981/microphoto19811710/microphoto1981171000024/88705547-ic%C3%B4ne-de-profil-avatar-par-d%C3%A9faut-marqueur-de-photo-gris-profil-par-d%C3%A9faut-masculin-image-de-personn.jpg");
                        users.child(uid).setValue(user2);



                        Toast.makeText(Inscription.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Inscription.this, Identification.class));
                    } else {
                        Toast.makeText(Inscription.this, "Inscription Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}
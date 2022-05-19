package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class NouvauProduit extends AppCompatActivity {
EditText nom,ancienprix,nouveauprix,description;
Button valide;
ImageView bck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouvau_produit);
        nom=findViewById(R.id.nom_produit);
        ancienprix=findViewById(R.id.ancien_prix);
        nouveauprix=findViewById(R.id.Nouveau_prix);
        description=findViewById(R.id.description);
        valide=findViewById(R.id.button_valide);
        bck=findViewById(R.id.arriere);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NouvauProduit.this,ActivityVendeur.class));
            }
        });
    }
    private void cratePost(){
        String Nomp =nom.getText().toString();
        String ancien =ancienprix.getText().toString();
        String nouveau =nouveauprix.getText().toString();
        String desc =description.getText().toString();
    }

}
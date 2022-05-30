package com.example.project2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class NouvauProduit extends AppCompatActivity {
    EditText nom,ancienprix,nouveauprix,description;
    Button valide;
    ImageView bck;



    ImageSlider imageSlider;
    List<SlideModel> slide;
    TextView compteur,compteur2,compteur3;
    LottieAnimationView lotie;


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
        lotie=findViewById(R.id.plus);
        imageSlider=findViewById(R.id.image_slider);
        compteur=findViewById(R.id.cpt);
        compteur2=findViewById(R.id.cpt_nom);
        compteur3=findViewById(R.id.cpt_descrp);
        slide=new ArrayList<>();
        lotie.playAnimation();
        slide.clear();
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NouvauProduit.this,ActivityVendeur.class));
            }
        });

        lotie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent,100);

            }


        });


        nom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                compteur2.setText(String.valueOf(s.length())+"/20");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                compteur3.setText(String.valueOf(s.length())+"/40");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        valide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cratePost();
            }
        });

    }
    private void cratePost(){
        String Nomp =nom.getText().toString();
        String ancien =ancienprix.getText().toString();
        String nouveau =nouveauprix.getText().toString();
        String desc =description.getText().toString();
        int anc=Integer.parseInt(ancien);
        int neuv=Integer.parseInt(nouveau);
        if(slide.size()==0){
            Toast.makeText(this, "veuillez remplir le champ de photo avec au moins une photo", Toast.LENGTH_LONG).show();
        }
        else if (Nomp.isEmpty()){
            nom.setError("champ vide, veuillez le remplir");
            nom.requestFocus();
        }
        else if(ancien.isEmpty()){
            ancienprix.setError("champ vide, veuillez le remplir");
            ancienprix.requestFocus();
        }
        else if(nouveau.isEmpty()){
            nouveauprix.setError("champ vide, veuillez le remplir");
            nouveauprix.requestFocus();
        }
        if(anc<neuv){
            nouveauprix.setError("le nooueau doit etre inferieure a l'ancien");
            nouveauprix.requestFocus();
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==RESULT_OK){
            Uri uri=data.getData();
            if(slide.size()<5){
                slide.add(new SlideModel(String.valueOf(uri), ScaleTypes.CENTER_CROP));
                imageSlider.setImageList(slide);
                compteur.setText(slide.size()+"/5");
                if (slide.size()==5){
                    lotie.setVisibility(View.GONE);
                }

            }


        }
    }
}

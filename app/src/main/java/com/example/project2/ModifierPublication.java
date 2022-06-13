package com.example.project2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.Calendar;
import java.util.List;

public class ModifierPublication extends AppCompatActivity {
    ImageSlider imageSlider;
    EditText nomProduit,descProduit,prixNouveau,prixAncien,categorie;
    TextView dateFin,cptPhoto,cptNom,cptDsc;
    LottieAnimationView lottieAnimationView;
    List<SlideModel> models;
    ImageView back;
    Button valider;
    Uri image;
    DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_publication);
        imageSlider=findViewById(R.id.image_slider1);
        nomProduit=findViewById(R.id.nomProduit);
        descProduit=findViewById(R.id.description1);
        prixAncien=findViewById(R.id.ancienPrix);
        prixNouveau=findViewById(R.id.nouveauPrix);
        dateFin=findViewById(R.id.dateFin);
        categorie=findViewById(R.id.categorie);
        cptPhoto=findViewById(R.id.cptPhoto);
        cptNom=findViewById(R.id.cptNom);
        cptDsc=findViewById(R.id.cptDescrp);
        lottieAnimationView=findViewById(R.id.plus1);
        back=findViewById(R.id.back);
        valider=findViewById(R.id.buttonValide);




        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        dateFin=findViewById(R.id.Date_fin);
        dateFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ModifierPublication.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date1 = day + "/" + month + "/" + year;
                dateFin.setText(date1);

            }
        };





        back.setOnClickListener(new View.OnClickListener() {  //button pour revenir a l'accueil
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModifierPublication.this,ActivityVendeur.class));
            }
        });





        lottieAnimationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent,100);
            }
        });




        nomProduit.addTextChangedListener(new TextWatcher() {  //compteur de caracteres de nom produit
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cptNom.setText(String.valueOf(s.length())+"/20");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





        descProduit.addTextChangedListener(new TextWatcher() {    //compteur de caracteres de description produit
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cptDsc.setText(String.valueOf(s.length())+"/40");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifierPub();
            }
        });



    }

    private void modifierPub() {
        String Nomp = nomProduit.getText().toString();
        String ancien = prixAncien.getText().toString();
        String nouveau = prixNouveau.getText().toString();
        String desc = descProduit.getText().toString();

        //5admatek

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            image = data.getData();

            if (models.size() < 5) {
                models.add(new SlideModel(String.valueOf(image), ScaleTypes.CENTER_CROP));
                imageSlider.setImageList(models);
                cptPhoto.setText(models.size() + "/5");
                if (models.size() == 5) {
                    lottieAnimationView.setVisibility(View.GONE);
                }

            }


        }

    }

}


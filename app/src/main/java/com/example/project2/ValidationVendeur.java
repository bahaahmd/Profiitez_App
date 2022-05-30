package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Patterns;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.cert.PolicyNode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.DuplicateFormatFlagsException;

public class ValidationVendeur extends AppCompatActivity {
TextView open,close;
int toHour,toMinute,tcHour,tcMinute;
Button valider;
EditText nom,loc,num;
DatabaseReference vender;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation_vendeur);
        vender= FirebaseDatabase.getInstance().getReference("Users").child("Venders");
        nom=findViewById(R.id.Nom_market);
        loc=findViewById(R.id.Localisation);
        num=findViewById(R.id.Numero_market);



        open=findViewById(R.id.HO);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(ValidationVendeur.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hour, int minute) {
                                toHour=hour;
                                toMinute=minute;
                                String time=toHour+ ":"+toMinute;
                                SimpleDateFormat f24Hours =new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date =f24Hours.parse(time);
                                    SimpleDateFormat f12Hours =new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    open.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        },12,0,false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(toHour,toMinute);
                timePickerDialog.show();
            }
        });


        close=findViewById(R.id.HC);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(ValidationVendeur.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hour, int minute) {
                                tcHour=hour;
                                tcMinute=minute;
                                String time=tcHour+ ":"+tcMinute;
                                SimpleDateFormat f24Hours =new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date =f24Hours.parse(time);
                                    SimpleDateFormat f12Hours =new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    close.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        },12,0,false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(tcHour,tcMinute);
                timePickerDialog.show();
            }
        });

        //Button valider
        valider=findViewById(R.id.Valider_vendeur);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createMarket();

            }
        });

    }


    public void createMarket(){
        String nomMarket = nom.getText().toString();
        String localisation = loc.getText().toString();
        String numero = num.getText().toString();
        String heureOuverture = open.getText().toString();
        String heureFermeture = close.getText().toString();
        FirebaseUser id = FirebaseAuth.getInstance().getCurrentUser();
        String vid = id.getUid();

        if(!TextUtils.isEmpty(nomMarket) && !TextUtils.isEmpty(localisation) && !TextUtils.isEmpty(numero) && !TextUtils.isEmpty(heureOuverture) && !TextUtils.isEmpty(heureFermeture) ){
        Market m=new Market(nomMarket,localisation,numero,heureFermeture,heureOuverture,vid);
        vender.child(vid).setValue(m);
            startActivity(new Intent (ValidationVendeur.this,ActivityVendeur.class));
            finish();
        }else {

        if(nomMarket.isEmpty()){
           nom.setError("champ vide");
           nom.requestFocus();
        }else if (localisation.isEmpty()){
            loc.setError("champ vide");
            loc.requestFocus();
        }else if (!URLUtil.isValidUrl(localisation)){
            loc.setError("Entrer un lien");
            loc.requestFocus();
        }else if (numero.isEmpty()) {
            num.setError("champ vide");
            num.requestFocus();
        } else if (!Patterns.PHONE.matcher(numero).matches()) {
            num.setError("numéro de téléphone doit contenir que des chiffres");
            num.requestFocus();
        } else if (numero.length() < 10) {
            num.setError("numéro de téléphone doit contenir 10 chiffres");
        }


    }}
}
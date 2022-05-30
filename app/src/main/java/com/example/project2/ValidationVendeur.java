package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Patterns;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.security.cert.PolicyNode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.DuplicateFormatFlagsException;
import java.util.List;
import java.util.Locale;

public class ValidationVendeur extends AppCompatActivity implements LocationListener {
    TextView open, close;
    int toHour, toMinute, tcHour, tcMinute;
    Button valider;
    EditText nom, loc, num;

    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation_vendeur);
        nom = findViewById(R.id.Nom_market);
        loc = findViewById(R.id.Localisation);
        num = findViewById(R.id.Numero_market);
        open = findViewById(R.id.HO);

        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(ValidationVendeur.this);
                builder.setTitle("Localisation");
                builder.setMessage("Vous pouvez remplier ce formulaire si seulement si vous etes dans votre magasin");
                builder.setPositiveButton("Ok !", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        if (ActivityCompat.checkSelfPermission(ValidationVendeur.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ValidationVendeur.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        onLocationChanged(location);
                    }
                });

                builder.create().show();


            }
        });
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
        else
            startActivity(new Intent (ValidationVendeur.this,ActivityVendeur.class));



    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        try {
            Geocoder geocoder=new Geocoder(this,Locale.getDefault());
            double longitude=location.getLongitude();
            double latitude=location.getLatitude();
            List<Address> addresses=geocoder.getFromLocation(latitude,longitude,1);
            loc.setText(addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea()  );
            loc.setEnabled(false);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}
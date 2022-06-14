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
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.cert.PolicyNode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.DuplicateFormatFlagsException;
import java.util.List;
import java.util.Locale;



public class ValidationVendeur extends AppCompatActivity implements LocationListener{
    TextView open, close;
    int toHour, toMinute, tcHour, tcMinute;
    Button valider;
    EditText nom, loc, num;
    DatabaseReference vender,user;
    LocationManager locationManager;
    double latitude,longitude;
    FirebaseUser id = FirebaseAuth.getInstance().getCurrentUser();
    String vid = id.getUid();
    String s;
    Market m=new Market();


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
                AlertDialog.Builder builder = new AlertDialog.Builder(ValidationVendeur.this);
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

        vender = FirebaseDatabase.getInstance().getReference("Users").child("Venders");
        user=FirebaseDatabase.getInstance().getReference("Users");
        nom = findViewById(R.id.Nom_market);
        loc = findViewById(R.id.Localisation);
        num = findViewById(R.id.Numero_market);


        open = findViewById(R.id.HO);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(ValidationVendeur.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hour, int minute) {
                                toHour = hour;
                                toMinute = minute;
                                String time = toHour + ":" + toMinute;
                                SimpleDateFormat f24Hours = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    open.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, 12, 0, false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(toHour, toMinute);
                timePickerDialog.show();
            }
        });


        close = findViewById(R.id.HC);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(ValidationVendeur.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hour, int minute) {
                                tcHour = hour;
                                tcMinute = minute;
                                String time = tcHour + ":" + tcMinute;
                                SimpleDateFormat f24Hours = new SimpleDateFormat(
                                        "HH:mm"
                                );
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat(
                                            "hh:mm aa"
                                    );
                                    close.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, 12, 0, false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(tcHour, tcMinute);
                timePickerDialog.show();
            }
        });

        //Button valider
        valider = findViewById(R.id.Valider_vendeur);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMarket();
finish();

            }
        });

    }


    public void createMarket() {



        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                String nomMarket = nom.getText().toString();
                String localisation = loc.getText().toString();
                String numero = num.getText().toString();
                String heureOuverture = open.getText().toString();
                String heureFermeture = close.getText().toString();






                if (!TextUtils.isEmpty(nomMarket) && !TextUtils.isEmpty(numero) && !TextUtils.isEmpty(heureOuverture) && !TextUtils.isEmpty(heureFermeture)) {

                    m.setFerme(heureFermeture);
                    m.setOuvert(heureOuverture);
                    m.setId(vid);
                    m.setLocal(localisation);
                    m.setNom(nomMarket);
                    m.setNumero(numero);
                    m.setImage(s);
                    vender.child(vid).setValue(m);

                    startActivity(new Intent(ValidationVendeur.this, ActivityVendeur.class));
                    finish();
                    for(DataSnapshot d:dataSnapshot.getChildren()) {
                        com.example.project2.user us = d.getValue(user.class);





                        if (vid.equals(us.getId())) {

                            s = us.getImage();

                            break;


                        }




                    }


                }


                else {

                    if (nomMarket.isEmpty()) {
                        nom.setError("champ vide");
                        nom.requestFocus();
//                    } else if (localisation.isEmpty()) {
//                        loc.setError("champ vide");
//                        loc.requestFocus();
//                    } else if (!URLUtil.isValidUrl(localisation)) {
//                        loc.setError("Entrer un lien");
//                        loc.requestFocus();
                 } else if (numero.isEmpty()) {
                        num.setError("champ vide");
                        num.requestFocus();
                    } else if (!Patterns.PHONE.matcher(numero).matches()) {
                        num.setError("numéro de téléphone doit contenir que des chiffres");
                        num.requestFocus();
                    } else if (numero.length() < 10) {
                        num.setError("numéro de téléphone doit contenir 10 chiffres");
                    } else
                        startActivity(new Intent(ValidationVendeur.this, ActivityVendeur.class));


                }
                m.setImage(s);
                vender.child(vid).setValue(m);












            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("jhj", "loadPost:onCancelled", databaseError.toException());
            }
        });





    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        try{
            Geocoder geocoder=new Geocoder(ValidationVendeur.this,Locale.getDefault());
            latitude=location.getLatitude();
            longitude=location.getLongitude();

            List<Address> addresses=geocoder.getFromLocation(latitude,longitude,1);
            loc.setText(addresses.get(0).getLocality()+", " + addresses.get(0).getAdminArea());
        } catch (Exception e) {
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
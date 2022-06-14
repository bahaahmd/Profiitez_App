package com.example.project2;

import android.Manifest;
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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class change_adress extends AppCompatActivity implements LocationListener {
    ImageView imageView;
   EditText nomMarket,loc,numeroMarket;
   TextView Ho,Hc;
   int  toHour , tcHour ,tcMinute, toMinute ;
    Vendeur v=new Vendeur();
    String img;



   DatabaseReference vender,produit;
    LocationManager locationManager;
    double  latitude,longitude;
    FirebaseUser id = FirebaseAuth.getInstance().getCurrentUser();



    String vid = id.getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_adress);
        vender= FirebaseDatabase.getInstance().getReference("Users").child("Venders").child(vid);
        produit= FirebaseDatabase.getInstance().getReference("ProductsHome");
        imageView=(ImageView) findViewById(R.id.imageView5);
        nomMarket=findViewById(R.id.Nom_markett);
        loc=findViewById(R.id.Localisation);
        numeroMarket=findViewById(R.id.Numero_market);
        Ho=findViewById(R.id.HO);
        Hc=findViewById(R.id.HC);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity();

                String nom=nomMarket.getText().toString();
                String num=numeroMarket.getText().toString();
                String lo=loc.getText().toString();
                String o=Ho.getText().toString();
                String c=Hc.getText().toString();
                set(nom,num,lo,o,c);




            }

        });


        Hc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(change_adress.this,
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
                                    Hc.setText(f12Hours.format(date));
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





        Ho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(change_adress.this,
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
                                    Ho.setText(f12Hours.format(date));
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









        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(change_adress.this);
                builder.setTitle("Localisation");
                builder.setMessage("Vous pouvez remplier ce formulaire si seulement si vous etes dans votre magasin");
                builder.setPositiveButton("Ok !", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        if (ActivityCompat.checkSelfPermission(change_adress.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(change_adress.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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


    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            Geocoder geocoder = new Geocoder(change_adress.this, Locale.getDefault());
            latitude = location.getLatitude();
            longitude = location.getLongitude();

            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            loc.setText(addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }
    public void set(String nom,String num,String lo,String o,String c){
        produit.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Get Post object and use the values to update the UI
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    ProductHome m=d.getValue(ProductHome.class);

                    if(vid.equals(m.getIdv())){


                        produit.child(m.getId()).child("v").child("fermer").setValue(c).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    System.out.println("finished");
                                }else{
                                    System.out.println("erreur");
                                }
                            }
                        });

                        produit.child(m.getId()).child("v").child("nom").setValue(nom);
                        produit.child(m.getId()).child("v").child("ouvert").setValue(o);






                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("jhj", "loadPost:onCancelled", databaseError.toException());
            }


        });

        vender.child("nom").setValue(nom);
        vender.child("numero").setValue(num);
        vender.child("local").setValue(lo);
        vender.child("ouvert").setValue(o);
        vender.child("ferme").setValue(c);
    }
    public void openActivity() {
        Intent intent=new Intent(change_adress.this, setting_vendeur.class);
        startActivity(intent);
    }
}

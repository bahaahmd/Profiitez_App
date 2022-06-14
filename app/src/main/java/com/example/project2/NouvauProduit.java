package com.example.project2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;


import java.util.HashMap;
import java.util.List;

public class NouvauProduit extends AppCompatActivity {
    EditText nom,nomm,ancienprix,nouveauprix,description;
    Button valide;
    final static String CHANNEL_ID="channel id";
    ImageView bck;
    Vendeur v=new Vendeur();

    TextView date,close,open;
    DatePickerDialog.OnDateSetListener setListener;
    int i = 0;
    Product p = new Product();
    ProductHome pp = new ProductHome();




Spinner spinner;
    ImageSlider imageSlider;
    List<SlideModel> slide;
    TextView compteur,compteur2,compteur3;
    LottieAnimationView lotie;
    String urlP;
    DatabaseReference databaseReference, databaseReferencee,archive,user;
    StorageReference storageReference, storageReferencee,archives;
    Uri mImageUri;
    HashMap<String, String> H = new HashMap<>();
    StorageTask tt;

    FirebaseUser id = FirebaseAuth.getInstance().getCurrentUser();
    String vid = id.getUid();

     String[] categories;
    private Object adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseReferencee = FirebaseDatabase.getInstance().getReference("Products");
        databaseReference = FirebaseDatabase.getInstance().getReference("ProductsHome");
        archive = FirebaseDatabase.getInstance().getReference("Archive");
        user = FirebaseDatabase.getInstance().getReference("Users").child("Venders");
        archives = FirebaseStorage.getInstance().getReference("Archive");

        storageReference = FirebaseStorage.getInstance().getReference("Products");
        storageReferencee = FirebaseStorage.getInstance().getReference("ProductsHome");

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
        nomm = findViewById(R.id.Nom_market);
        open = findViewById(R.id.HO);
        close = findViewById(R.id.HC);





        date=findViewById(R.id.Date_fin);
spinner=findViewById(R.id.categorie);


  String[] categories={"Alimentation","Sport","Electronique","Beauté","Article Bébe","Santé","Autres"};

adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,categories);
spinner.setAdapter((SpinnerAdapter) adapter);


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        date=findViewById(R.id.Date_fin);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        NouvauProduit.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListener, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date1 = day + "/" + month + "/" + year;
                date.setText(date1);

            }
        };

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
                isNotif();
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            mImageUri = data.getData();

            if (slide.size() < 5) {
                slide.add(new SlideModel(String.valueOf(mImageUri), ScaleTypes.CENTER_CROP));
                imageSlider.setImageList(slide);
                compteur.setText(slide.size() + "/5");
                if (slide.size() == 5) {
                    lotie.setVisibility(View.GONE);
                }

            }


        }

    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void cratePost() {


        //  String datee = date.getText().toString();
        String Nomp = nom.getText().toString();
        String ancien = ancienprix.getText().toString();
        String nouveau = nouveauprix.getText().toString();
        String desc = description.getText().toString();
        String datee = date.getText().toString();
        String cat = spinner.getSelectedItem().toString();



        if(ancien.isEmpty() || nouveau.isEmpty()){
            if(nouveau.isEmpty()){
                nouveauprix.setError("donnez une valeur svp");
                nouveauprix.requestFocus();}else{
                ancienprix.setError("donnez une valeur svp");
                ancienprix.requestFocus();

            }


        }else {
            int anc = Integer.parseInt(ancien);
            int neuv = Integer.parseInt(nouveau);
            String idProduct = databaseReferencee.push().getKey();

            if (!TextUtils.isEmpty(Nomp) && !TextUtils.isEmpty(ancien) && !TextUtils.isEmpty(nouveau) && !TextUtils.isEmpty(desc)) {
                Intent intent=new Intent(NouvauProduit.this,ActivityVendeur.class);
                startActivity(intent);

                if (mImageUri != null) {
                    StorageReference fileRefrence = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
                    tt = fileRefrence.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileRefrence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(NouvauProduit.this, "UloadSucces", Toast.LENGTH_LONG).show();
                                    for (SlideModel s : slide) {
                                        urlP = uri.toString();

                                        H.put(databaseReferencee.push().getKey(), urlP);
                                    }
                                    user.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            // Get Post object and use the values to update the UI
                                            for(DataSnapshot d:dataSnapshot.getChildren()){
                                                Market u=d.getValue(Market.class);
                                                System.out.println(u.getId());

                                                if(vid.equals(u.getId())){

                                                    v.setOuvert(u.getOuvert());
                                                    v.setFermer(u.getFerme());
                                                    v.setNom(u.getNom());
                                                    v.setImage(u.getImage());
                                                    pp.setV(v);


                                                    pp.setId(idProduct);
                                                    pp.setImageUrl(uri.toString());
                                                    //p.setDate(datee);
                                                    pp.setName(Nomp);
                                                    pp.setPrice_ancien(ancien);
                                                    pp.setPrice_nouveau(nouveau);
                                                    pp.setDescription(desc);
                                                    pp.setIdv(vid);
                                                    pp.setDate(datee);
                                                    pp.setCategorie(cat);





                                                    databaseReference.child(idProduct).setValue(pp);
                                                    archive.child(idProduct).setValue(pp);

                                                    p.setImageUrl(H);
                                                    //p.setDate(datee);
                                                    p.setName(Nomp);
                                                    p.setPrice_ancien(ancien);
                                                    p.setPrice_nouveau(nouveau);
                                                    p.setDescription(desc);
                                                    p.setIdv(vid);
                                                    p.setV(v);
                                                    p.setDate(datee);
                                                    p.setCategorie(cat);
                                                    p.setId(idProduct);

                                                    databaseReferencee.child(idProduct).setValue(p);



                                                }


                                            }






                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            // Getting Post failed, log a message
                                            Log.w("jhj", "loadPost:onCancelled", databaseError.toException());
                                        }
                                    });













                                }
                            });

                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NouvauProduit.this, e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });
                } else {
                    Toast.makeText(this, "erreor", Toast.LENGTH_LONG).show();
                }

                Toast.makeText(this, "save succesufuly", Toast.LENGTH_LONG).show();



            }else {

                if (slide.size() == 0) {
                    Toast.makeText(this, "veuillez remplir le champ de photo avec au moins une photo", Toast.LENGTH_LONG).show();
                } else if (Nomp.isEmpty()) {
                    nom.setError("champ vide, veuillez le remplir");
                    nom.requestFocus();
                } else if (ancien.isEmpty()) {
                    ancienprix.setError("champ vide, veuillez le remplir");
                    ancienprix.requestFocus();
                } else if (nouveau.isEmpty()) {
                    nouveauprix.setError("champ vide, veuillez le remplir");
                    nouveauprix.requestFocus();
                }
                if (anc < neuv) {
                    nouveauprix.setError("le nooueau doit etre inferieure a l'ancien");
                    nouveauprix.requestFocus();
                }


            }
        }


    }
    private void pushNotification() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,"notif", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("This is the discription");
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Intent intent=new Intent(this,MainActivity2.class);
        PendingIntent p=PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_bell)
                .setContentTitle("Profitez")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(p)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("you have a new product "));

        NotificationManagerCompat notif=NotificationManagerCompat.from(this);
        notif.notify(10,builder.build());




    }
    private void isNotif(){

            String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference alertV = FirebaseDatabase.getInstance().getReference().child("Alert").child(id);
            alertV.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                    {
                        pushNotification();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }



}

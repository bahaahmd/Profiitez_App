package com.example.project2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ModifierPublication extends AppCompatActivity {
    EditText nom,nomm,ancienprix,nouveauprix,description,categorie;
    Button valide;
    ImageView bck;
    Vendeur v=new Vendeur();

    TextView date,close,open;
    DatePickerDialog.OnDateSetListener setListener;
    int i = 0;
    Product p = new Product();
    ProductHome pp = new ProductHome();





    ImageSlider imageSlider;
    List<SlideModel> slide;
    TextView compteur,compteur2,compteur3;
    LottieAnimationView lotie;
    String urlP,s;
    DatabaseReference databaseReference, databaseReferencee,archive,user;
    StorageReference storageReference, storageReferencee,archives;
    Uri mImageUri;
    HashMap<String, String> H = new HashMap<>();
    StorageTask tt;


    FirebaseUser id = FirebaseAuth.getInstance().getCurrentUser();
    String vid = id.getUid();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        s=  getIntent().getStringExtra("id");
        System.out.println(s);
        databaseReferencee = FirebaseDatabase.getInstance().getReference("Products");
        databaseReference = FirebaseDatabase.getInstance().getReference("ProductsHome");

        archive = FirebaseDatabase.getInstance().getReference("Archive");
        user = FirebaseDatabase.getInstance().getReference("Users").child("Venders");
        archives = FirebaseStorage.getInstance().getReference("Archive");

        storageReference = FirebaseStorage.getInstance().getReference("Products");
        storageReferencee = FirebaseStorage.getInstance().getReference("ProductsHome");

        setContentView(R.layout.activity_modifier_publication);
        nom=findViewById(R.id.nomProduit);
        ancienprix=findViewById(R.id.ancienPrix);
        nouveauprix=findViewById(R.id.nouveauPrix);
        description=findViewById(R.id.description1);
        valide=findViewById(R.id.buttonValide);
        bck=findViewById(R.id.back);
        lotie=findViewById(R.id.plus1);
        imageSlider=findViewById(R.id.image_slider1);
        compteur=findViewById(R.id.cptPhoto);
        compteur2=findViewById(R.id.cptNom);
        compteur3=findViewById(R.id.cptDescrp);
        slide=new ArrayList<>();
        lotie.playAnimation();
        slide.clear();
        nomm = findViewById(R.id.Nom_market);
        open = findViewById(R.id.HO);
        close = findViewById(R.id.HC);
        date=findViewById(R.id.dateFin);
        categorie=findViewById(R.id.Categorie);








        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        date.setOnClickListener(new View.OnClickListener() {
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
                date.setText(date1);

            }
        };

        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ModifierPublication.this,ActivityVendeur.class));
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
        String cat = categorie.getText().toString();



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

            if (!TextUtils.isEmpty(Nomp) && !TextUtils.isEmpty(ancien) && !TextUtils.isEmpty(nouveau) && !TextUtils.isEmpty(desc)) {
                Intent intent=new Intent(ModifierPublication.this,ActivityVendeur.class);
                startActivity(intent);

                if (mImageUri != null) {
                    StorageReference fileRefrence = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
                    tt = fileRefrence.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileRefrence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Toast.makeText(ModifierPublication.this, "UloadSucces", Toast.LENGTH_LONG).show();
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

                                                if(vid.equals(u.getId())){

                                                    v.setOuvert(u.getOuvert());
                                                    v.setFermer(u.getFerme());
                                                    v.setNom(u.getNom());
                                                    v.setImage(u.getImage());
                                                    pp.setV(v);



                                                    pp.setImageUrl(uri.toString());
                                                    //p.setDate(datee);
                                                    pp.setName(Nomp);
                                                    pp.setPrice_ancien(ancien);
                                                    pp.setPrice_nouveau(nouveau);
                                                    pp.setDescription(desc);
                                                    pp.setIdv(vid);
                                                    pp.setDate(datee);
                                                    pp.setCategorie(cat);







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
                                                    pp.setId(s);
                                                    p.setId(s);
                                                    archive.child(s).setValue(pp);
                                                    databaseReference.child(s).setValue(pp);



                                                    databaseReferencee.child(s).setValue(p);











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
                            Toast.makeText(ModifierPublication.this, e.getMessage(), Toast.LENGTH_LONG).show();

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



}
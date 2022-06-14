package com.example.project2;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class edit_profil_vendeur extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    ImageView img;
    Uri mImageUri;
    EditText editText,nom,email;
    Button btn;
    String urlP,s,ss;
    Vendeur v=new Vendeur();
    StorageReference Sref= FirebaseStorage.getInstance().getReference("Profil");
    DatabaseReference database,ref,produit;
    String idV = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference dd;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            mImageUri=data.getData();
            img.setImageURI(mImageUri);

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profil_vendeurt);
        imageView= findViewById(R.id.imageView3);
        img=findViewById(R.id.imageView7);
        nom=findViewById(R.id.passwordancienn);
        dd=FirebaseDatabase.getInstance().getReference("url");

        btn=findViewById(R.id.buttonupdatee) ;
        email= findViewById(R.id.editTextTextPassword4);
        editText=findViewById(R.id.editTextTextPassword5);
        textView=findViewById((R.id.textView7));
      ref= FirebaseDatabase.getInstance().getReference("Users").child("Venders");
      produit=FirebaseDatabase.getInstance().getReference("ProductsHome");

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openActivity();
            }

            public void openActivity() {
                Intent intent=new Intent(edit_profil_vendeur.this, ActivityVendeur.class);
                startActivity(intent);

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setdata(ref);

            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, 100);

            }

        });
        getdata();



    }
    void getdata() {
        String idV = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance().getReference("Users").child("Venders").child(idV);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    System.out.println("here client ");
                    Market vender = snapshot.getValue(Market.class);
                    nom.setText(vender.getNom());
                    editText.setText(vender.getNumero());
                    Picasso.get().load(vender.getImage()).into(img);
                    email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());


                } catch (Exception e) {
                    System.out.println("err " + e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    void setdata(DatabaseReference ref) {








        ref.child(idV).child("nom").setValue(nom.getText().toString());
        ref.child(idV).child("numero").setValue(editText.getText().toString());
        if (mImageUri != null) {
            StorageReference fileRefrence = Sref.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            fileRefrence.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRefrence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Toast.makeText(edit_profil_vendeur.this, "UloadSucces", Toast.LENGTH_LONG).show();
                            urlP = uri.toString();
                            ref.child(idV).child("image").setValue(urlP);
                            Producti i=new Producti(urlP);
                            dd.setValue(i);










                        }
                    });



                }





            });

        }
        produit.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    ProductHome p=d.getValue(ProductHome.class);
                    System.out.println(d.getValue());
                    System.out.println(p.getIdv());
                    if(idV.equals(p.getIdv())){






                        produit.child(p.getId()).child("v").child("nom").setValue(nom.getText().toString());
                        dd.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Producti i=snapshot.getValue(Producti.class);
                                System.out.println(i.getIdc());
                                produit.child(p.getId()).child("v").child("image").setValue(i.getIdc());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        break;

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
}
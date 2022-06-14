package com.example.project2;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
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

public class edit_profil_client extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    ImageView img;
    Uri mImageUri;
    EditText editText,nom,email;
    Button btn;
    String urlP;
    StorageReference Sref= FirebaseStorage.getInstance().getReference("Profil");
    DatabaseReference database;
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
        setContentView(R.layout.edit_profil_client);
        imageView= findViewById(R.id.imageView3);
        img=findViewById(R.id.imageView7);
        nom=findViewById(R.id.passwordancien);
        btn=findViewById(R.id.buttonupdate) ;
        email= findViewById(R.id.editTextTextPassword4);
        editText=findViewById(R.id.editTextTextPassword5);
        textView=findViewById((R.id.textView7));
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users");

        getdata();
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openActivity();
            }

            public void openActivity() {
                Intent intent=new Intent(edit_profil_client.this, HomePage.class);
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



    }
    void getdata() {
        String idV = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance().getReference().child("Users").child(idV);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    System.out.println("here client ");
                    user client = snapshot.getValue(user.class);
                    nom.setText(client.getUserName());
                    Picasso.get().load(client.getImage()).into(img);
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
        String idV = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (mImageUri != null) {
            ref.child(idV).child("userName").setValue(nom.getText().toString());
            StorageReference fileRefrence = Sref.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            fileRefrence.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileRefrence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Toast.makeText(edit_profil_client.this, "UloadSucces", Toast.LENGTH_LONG).show();
                            urlP = uri.toString();
                            System.out.println(urlP);
                            ref.child(idV).child("image").setValue(urlP);

                        }
                    });
                    ref.child(idV).child("userName").setValue(nom.getText().toString());


                }});
        }
    }
}
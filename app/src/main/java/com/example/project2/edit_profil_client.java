package com.example.project2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class edit_profil_client extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    ImageView img;
    EditText editText,nom,email;
    Button btn;
    DatabaseReference database;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri=data.getData();
            img.setImageURI(uri);

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
        DatabaseReference ref=database = FirebaseDatabase.getInstance().getReference().child("Users");
        getdata();
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openActivity();
            }

            public void openActivity() {
                Intent intent=new Intent(edit_profil_client.this, Profile.class);
                startActivity(intent);

            }
        });

        Button btn= findViewById(R.id.buttonupdate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setdata(ref);
                Intent intent=new Intent(edit_profil_client.this,Profile.class);

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
    void setdata(DatabaseReference ref) {
        String idV = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ref.child(idV).child("userName").setValue(nom.getText().toString());


    }
}


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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

public class edit_profil_client extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    ImageView img;
EditText editText;
Button btn;
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
        imageView=(ImageView) findViewById(R.id.imageView3);
        img=(ImageView)findViewById(R.id.imageView7);
        editText=(EditText) findViewById(R.id.passwordancien);
        btn=(Button)findViewById(R.id.buttonupdate) ;
        editText=(EditText) findViewById(R.id.editTextTextPassword4);
        editText=(EditText) findViewById(R.id.editTextTextPassword5);
        textView=(TextView) findViewById((R.id.textView7));
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
        TextView mail =  findViewById(R.id.textView);
        TextView prenom =  findViewById(R.id.textView2);
        EditText prenom_= findViewById(R.id.editTextTextPassword3);
        EditText mail_= findViewById(R.id.editTextTextPassword4);
        Button btn= findViewById(R.id.buttonupdate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=prenom_.getText().toString();
                System.out.println(name);

                prenom.setText(name);
finish();
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
}


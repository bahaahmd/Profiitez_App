package com.example.project2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.disklrucache.DiskLruCache;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class publication_produit extends AppCompatActivity {

    RecyclerView recView;
    LinearLayoutManager linearLayoutManager;
    RatingBar ratingBar;
    FirebaseAuth mAuth;
    Button button,local;
    DatabaseReference database,database1;
    userAdapter Uadapter;
    ImageSlider imageSlider;
    LottieAnimationView lottieAnimationView;
    ArrayList<user> userComment=new ArrayList<>();
    TextView price_old,price_new,name,fin_date;
    ImageView favorite;
    ProductHome product;
    boolean clicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        setContentView(R.layout.activity_publication_produit);
        price_old=findViewById(R.id.AncienPrix);
        price_new=findViewById(R.id.NouveauPrix);
        name=findViewById(R.id.article);
        fin_date=findViewById(R.id.jusqua);
        mAuth = FirebaseAuth.getInstance();
        recView=findViewById(R.id.lst);
        ratingBar=findViewById(R.id.rating_bar);
        lottieAnimationView=findViewById(R.id.loti);
        favorite=findViewById(R.id.favorite_image);
        String id=getIntent().getStringExtra("id");


        //System.out.println("id is "+id);

        getData(id);
        SetImages(id);
        getComment();
        isExist(id);

        button=findViewById(R.id.btn_appeler);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!clicked){
                    clicked=true;
                    favorite.setImageResource(R.drawable.ic__full_heart);
                    getFov(product,id);
                }
                else{
                    clicked=false;
                    favorite.setImageResource(R.drawable.ic__empty_heart);
                    revFov(id);

                }

            }

        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call=new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:0558593389"));
                if (call.resolveActivity(getPackageManager())!=null)
                    startActivity(call);
            }

        });



        //pour les commentaires
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Dialog dialog=new Dialog(publication_produit.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.onrate);
                dialog.show();
                EditText editText=dialog.findViewById(R.id.edt);
                Button button=dialog.findViewById(R.id.btn);
                TextView textView=dialog.findViewById(R.id.text_annuler);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                TextView textView1=dialog.findViewById(R.id.text_limit);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        textView1.setText(String.valueOf(s.length())+"/30");
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {




                        String s=editText.getText().toString();
                        if (s.isEmpty()){
                            editText.setError("champ vide");

                            editText.requestFocus();
                        }
                        else {
                            Date date=new Date();
                            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("'Le ' dd/MM 'à' hh:mma");

                            //String id=usersRef.push().getKey();
                            String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            System.out.println("user "+user);
                            //"-N2Gbkc9C5c-_Z8VcLLv"
                            getUser(s,usersRef,user,simpleDateFormat.format(date));
                            dialog.dismiss();
                            getComment();






                        }
                    }
                });
            }
        });



    }
    void SetImages(String id){
        List<SlideModel> slideModels=new ArrayList<>();
        ImageSlider imageSlider=findViewById(R.id.image);
        database = FirebaseDatabase.getInstance().getReference("Products").child(id).child("imageUrl");
        //pour image slider

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                slideModels.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    try {
                        keys.add(dataSnapshot.getKey());


                        slideModels.add(new SlideModel(dataSnapshot.getValue().toString(),ScaleTypes.CENTER_CROP));

                    }catch (Exception e){
                        System.out.println("err:"+e.getMessage());
                    }
                }

                imageSlider.setImageList(slideModels);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    void getComment(){
        database1 = FirebaseDatabase.getInstance().getReference().child("Users");
        database1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userComment.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {


                    try {
                        keys.add(dataSnapshot.getKey());
                        user userr = dataSnapshot.getValue(user.class);
                        if(userr.getCommentaire()!=null){
                            userComment.add(new user(userr.getId(),userr.getUserName(), userr.getDate(), userr.getCommentaire(),userr.getImage()));

                        }
                    }catch (Exception e){
                        System.out.println("err:"+e.getMessage());
                    }
                }
                userAdapter Uadapter = new userAdapter(userComment,publication_produit.this);
                recView.setAdapter(Uadapter);
                linearLayoutManager = new LinearLayoutManager(publication_produit.this);
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recView.setLayoutManager(linearLayoutManager);
                lottieAnimationView.playAnimation();
                lottieAnimationView.setTranslationY(0);
                lottieAnimationView.animate().translationY(1000).setDuration(1000).setStartDelay(1000);
                Uadapter.notifyDataSetChanged();
            }
            @Override

            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void getData(String id){
        database=FirebaseDatabase.getInstance().getReference("ProductsHome").child(id);;
        database.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



              //  Map<String, Object> map = (Map<String, Object>) snapshot.getValue();

                try {


                    product = snapshot.getValue(ProductHome.class);
                    price_old.setText(product.getPrice_ancien());
                    price_new.setText(product.getPrice_nouveau());
                    fin_date.setText(product.getDate());
                    name.setText(product.getName());
                   // product= new ProductHome(map.get("id").toString(),map.get("name").toString(),map.get("imageUrl").toString(),map.get("price_ancien").toString(),map.get("price_nouveau").toString(),map.get("date").toString(),map.get("rating").toString());

                }catch (Exception e){
                    System.out.println("err "+e.getMessage());
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());


            }
        });
    }
    void getUser(String s,DatabaseReference usersRef,String id,String date){
        usersRef.child(id).child("date").setValue(date);
        usersRef.child(id).child("commentaire").setValue(s);


    }

    void getFov(ProductHome product,String id){
        String idV = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference favProduct = FirebaseDatabase.getInstance().getReference().child("Favorite").child(idV);
        favProduct.child(id).setValue(product);


    }
    void revFov(String id){
        String idV = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference favProduct = FirebaseDatabase.getInstance().getReference().child("Favorite").child(idV);
        favProduct.child(id).removeValue();

    }
    void isExist(String id){
        String idV = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference product = FirebaseDatabase.getInstance().getReference().child("Favorite").child(idV).child(id);
        product.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //System.out.println("cliked1"+snapshot.exists());
                if (snapshot.exists())
                {
                    favorite.setImageResource(R.drawable.ic__full_heart);
                }

                else {
                    favorite.setImageResource(R.drawable.ic__empty_heart);

                }
                //System.out.println("cliked2"+snapshot.exists());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}
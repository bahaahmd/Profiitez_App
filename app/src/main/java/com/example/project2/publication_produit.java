package com.example.project2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.StatementEvent;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class publication_produit extends AppCompatActivity {
Button arrow;
    RecyclerView recView;
    LinearLayoutManager linearLayoutManager;
    final static String CHANNEL_ID="channel id";
    RatingBar ratingBar;
    FirebaseAuth mAuth;
    Button button,local;
    DatabaseReference database,database1;
    userAdapter Uadapter;
    ImageSlider imageSlider;
    double latitude ,longitude;
    ImageView imageVendeur;
    LottieAnimationView lottieAnimationView;
    ArrayList<user> userComment=new ArrayList<>();
    TextView price_old,price_new,name,fin_date,nom_vendeur,dateOF,descr;
    ImageView favorite,alert;
    ProductHome product;
    user userClient;
    boolean clicked,clickedAlert;
    DatabaseReference vender;
    String idVendeur;
    static String tel;
    TextView categorie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        setContentView(R.layout.activity_publication_produit);
        nom_vendeur= findViewById(R.id.nom_vendeur);
        imageVendeur = findViewById(R.id.image_vendeur);
        price_old=findViewById(R.id.AncienPrix);
        price_old.setPaintFlags(price_old.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        price_new=findViewById(R.id.NouveauPrix);
        name=findViewById(R.id.article);
        dateOF=findViewById(R.id.dateOF);
        descr=findViewById(R.id.descr);
        fin_date=findViewById(R.id.jusqua);
        mAuth = FirebaseAuth.getInstance();
        recView=findViewById(R.id.lst);
        ratingBar=findViewById(R.id.rating_bar);
        lottieAnimationView=findViewById(R.id.loti);
        favorite=findViewById(R.id.favorite_image);
        alert=findViewById(R.id.alert_image);
        categorie=findViewById(R.id.categorie);
        local=findViewById(R.id.local_btn);


        String id=getIntent().getStringExtra("id");
        DatabaseReference usersRefProduct = FirebaseDatabase.getInstance().getReference().child("Products").child(id);
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();



        getData(id);
        getComment(id);
        isExist(id);


        getuserFromProduct(user);

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
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!clickedAlert){
                    clickedAlert=true;
                    alert.setImageResource(R.drawable.ic_baseline_turned_in_24);
                    getAlert(product);
                    isGetNotif(id) ;



                }
                else {
                    clickedAlert=false;
                    alert.setImageResource(R.drawable.ic_baseline_turned_in_not_24);
                    revAlert();
                    isRevNotif(id);
                }






            }
        });



        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent call=new Intent(Intent.ACTION_DIAL);
                call.setData(Uri.parse("tel:"+tel+""));
                if (call.resolveActivity(getPackageManager())!=null)
                    startActivity(call);

            }

        });

        local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:"+latitude +","+longitude+""));
                System.out.println("latitude"+latitude+"lomg"+longitude);
                if (intent.resolveActivity(getPackageManager())!=null)
                    startActivity(intent);


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


                            System.out.println("user "+user);
                            //"-N2Gbkc9C5c-_Z8VcLLv"
                            getUser(s,usersRef,usersRefProduct,user,simpleDateFormat.format(date));
                            dialog.dismiss();
                            getComment(id);






                        }
                    }
                });
            }
        });



    }
    void getComment(String id){
        database1 = FirebaseDatabase.getInstance().getReference().child("Products").child(id).child("users");
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

    ///////

    ////////////

    void getData(String id){
        List<SlideModel> slideModels=new ArrayList<>();
        ImageSlider imageSlider=findViewById(R.id.image);
        database=FirebaseDatabase.getInstance().getReference("ProductsHome").child(id);
        database.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                slideModels.clear();
                try {
                    product = snapshot.getValue(ProductHome.class);
                    idVendeur=product.getIdv();
                    isAlert(idVendeur);
                    getTelephone(idVendeur);
                    getLocalisation(idVendeur);

                    slideModels.add(new SlideModel(product.getImageUrl().toString(),ScaleTypes.CENTER_CROP));
                    price_old.setText(product.getPrice_ancien());
                    price_new.setText(product.getPrice_nouveau());
                    fin_date.setText(product.getDate());
                    name.setText(product.getName());
                    Picasso.get().load(product.getV().getImage()).into(imageVendeur);
                    nom_vendeur.setText(product.getV().nom);
                    dateOF.setText(product.getV().getOuvert()+"-"+product.getV().getFermer());
                    descr.setText(product.getDescription());
                    categorie.setText(product.getCategorie());
                }catch (Exception e){
                    System.out.println("err "+e.getMessage());
                }
                imageSlider.setImageList(slideModels);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());


            }
        });
    }
    void getUser(String s,DatabaseReference usersRef,DatabaseReference usersRefProduct,String id,String date ){
        usersRef.child(id).child("date").setValue(date);
        usersRef.child(id).child("commentaire").setValue(s);
        usersRefProduct.child("users").child(id).child("commentaire").setValue(s);
        usersRefProduct.child("users").child(id).child("date").setValue(date);
        usersRefProduct.child("users").child(id).child("image").setValue(userClient.getImage());
        usersRefProduct.child("users").child(id).child("userName").setValue(userClient.getUserName());



    }

//favorite
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
                if (snapshot.exists())
                {
                    favorite.setImageResource(R.drawable.ic__full_heart);
                }

                else {
                    favorite.setImageResource(R.drawable.ic__empty_heart);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
//alert

    void getAlert(ProductHome product){
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference alertProduct = FirebaseDatabase.getInstance().getReference().child("Alert").child(id);
        alertProduct.child(product.getIdv()).child("nom").setValue(product.getV().getNom());
        alertProduct.child(product.getIdv()).child("image").setValue(product.getV().getImage());
        alertProduct.child(product.getIdv()).child("fermer").setValue(product.getV().getFermer());
        alertProduct.child(product.getIdv()).child("ouvert").setValue(product.getV().getOuvert());


    }
    void revAlert(){

        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference alertProduct = FirebaseDatabase.getInstance().getReference().child("Alert").child(id);
        alertProduct.child(id).removeValue();

    }
    void isAlert(String idVendeur){
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference alertV = FirebaseDatabase.getInstance().getReference().child("Alert").child(id).child(idVendeur);
        alertV.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    alert.setImageResource(R.drawable.ic_baseline_turned_in_24);
                }

                else {

                    alert.setImageResource(R.drawable.ic_baseline_turned_in_not_24);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

//image

    void getuserFromProduct(String id){
        database=FirebaseDatabase.getInstance().getReference("Users").child(id);;
        database.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                //  Map<String, Object> map = (Map<String, Object>) snapshot.getValue();

                try {
                    userClient = snapshot.getValue(user.class);

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
        arrow=findViewById(R.id.arrow);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(publication_produit.this,HomePage.class));
            }
        });
    }
    void getTelephone(String idVendeur){
        DatabaseReference telephone = FirebaseDatabase.getInstance().getReference().child("Users").child("Venders").child(idVendeur);
        telephone.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                   Vendeur vndr = snapshot.getValue(Vendeur.class);
                    tel=vndr.getNumero();






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

    private void getNotification() {
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
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Someone alert you"));

        NotificationManagerCompat notif=NotificationManagerCompat.from(this);
        notif.notify(10,builder.build());




    }

    private void revNotification() {
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
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Someone remove his alert "));

        NotificationManagerCompat notif=NotificationManagerCompat.from(this);
        notif.notify(10,builder.build());




    }
    private void isGetNotif(String id){

        String idCurrent = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference alertV = FirebaseDatabase.getInstance().getReference().child("ProductsHome").child(id);
        alertV.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProductHome productHome=snapshot.getValue(ProductHome.class);
              if(idCurrent.equals(productHome.getIdv())){
                  getNotification();
              }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void isRevNotif(String id){

        String idCurrent = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference alertV = FirebaseDatabase.getInstance().getReference().child("ProductsHome").child(id);
        alertV.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ProductHome productHome=snapshot.getValue(ProductHome.class);
                if(idCurrent.equals(productHome.getIdv())){
                    revNotification();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void getLocalisation(String idVendeur){
        DatabaseReference local = FirebaseDatabase.getInstance().getReference().child("localisation").child(idVendeur);
        local.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    localisation local = snapshot.getValue(localisation.class);
                    latitude=local.getLatitude();
                    longitude=local.getLongitude();

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






}

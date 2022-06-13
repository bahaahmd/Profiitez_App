package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import Adapter.ProfileAdapter;


public class Profile_vendeur extends Fragment {
    ImageView img;
    ListView listView;
    String nom[] = { "Paramètres", "évaluez nous", "Référer un ami", "Aide", "Se déconnecter"};
    int image[] = { R.drawable.ic_settings__6_, R.drawable.ic_rate_us, R.drawable.ic_share__1_, R.drawable.ic_question, R.drawable.ic_logout__1_};

    View view;
    ImageView imageView;
    ConstraintLayout parent;

    TextView nomClient,emailClient;
    DatabaseReference database;
    FirebaseAuth     mAuth;
    FirebaseUser    user;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getdata();

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.vendeur_profil, container, false);
        listView = (ListView) view.findViewById(R.id.list_view);
        img=(ImageView) view.findViewById(R.id.editproffil);
        imageView = view.findViewById(R.id.imageView);
        nomClient=view.findViewById(R.id.textView2);
        emailClient=view.findViewById(R.id.textView);
        parent=(ConstraintLayout) view.findViewById(R.id.layoutdialogcontainer);
        ProfileAdapter profileAdapter = new ProfileAdapter(getActivity(), nom, image);
        listView.setAdapter(profileAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("ic_ssetting", "item is clicked @ Position::" + i);
                if (i == 0) {
                    startActivity(new Intent(getActivity(), setting_vendeur.class));

                } else if(i==4){

                    show();



                }
                else if(i==1){
                    Toast.makeText(getActivity(), "En maintenance ", Toast.LENGTH_LONG).show();




                }
                else if(i==2){
                    Toast.makeText(getActivity(), "En maintenance ", Toast.LENGTH_LONG).show();




                }
                else if(i==3){
                    Toast.makeText(getActivity(), "En maintenance ", Toast.LENGTH_LONG).show();



                }



            }

            private void show() {  AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_layout_client ,parent);
                builder.setView(view);
                ((TextView) view.findViewById(R.id.texttitle)).setText("ATTENTION");
                ((TextView) view.findViewById(R.id.textmessage)).setText("étes-vous sur de désactiver votre compte?");
                ((Button) view.findViewById(R.id.buttonactionyes)).setText("OUI");
                ((Button) view.findViewById(R.id.buttonactionno)).setText("NON");
                final AlertDialog alertDialog = builder.create();
                view.findViewById(R.id.buttonactionyes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        logout();
                        checkUser();
                        alertDialog.dismiss();


                    }

                });
                view.findViewById(R.id.buttonactionno).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();


                    }

                });

                alertDialog.show();


            }


        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), edit_profil_vendeur.class));
            }
        });


        return view;

    }    void getdata() {
        String idV = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance().getReference().child("Users").child(idV);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    System.out.println("here client ");
                    user client = snapshot.getValue(user.class);
                    nomClient.setText(client.getUserName());
                    Picasso.get().load(client.getImage()).into(imageView);
                    emailClient.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());


                } catch (Exception e) {
                    System.out.println("err " + e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    void logout(){
        mAuth=FirebaseAuth.getInstance();

        mAuth.signOut();
    }
    public void checkUser() {

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(getActivity(), Identification.class));
        }
    }


}
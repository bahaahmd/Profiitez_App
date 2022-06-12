 package com.example.project2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.project2.Product;
import com.example.project2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Formattable;
import java.util.HashMap;
import java.util.List;

import Adapter.NewsAdapter;
import Adapter.PopularAdapter;
import Adapter.RecyclerViewInterface;
import Adapter.VendeurAdapter;


 public class HomeVendeurFragment extends Fragment implements RecyclerViewInterface {
     RecyclerView recyclerView;
     VendeurAdapter adapter;



     ArrayList<ProductHome> list=new ArrayList();
     CardView nouvau;
     ImageView mrkt;
     FirebaseUser id = FirebaseAuth.getInstance().getCurrentUser();
     DatabaseReference databaseReference,p;
     String vid = id.getUid();
     public class ViewHolder extends RecyclerView.ViewHolder{


         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             ImageView  trash,update;
             update=itemView.findViewById(R.id.update);
             trash=itemView.findViewById(R.id.trash);
             trash.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     int isClicked=getAbsoluteAdapterPosition();
                     System.out.println(isClicked);

                 }
             }

             );


         }

     }



     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
         View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home_vendeur, container, false);
         recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView_vendeur);
         setVendeurrRecycler(list);














mrkt=view.findViewById(R.id.ic_home);
         mrkt.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(getActivity(),HomePage.class));
             }
         });


         nouvau=view.findViewById(R.id.nouveau);

         nouvau.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(getActivity(),NouvauProduit.class));
             }
         });

         return view;


     }

     private void setVendeurrRecycler(ArrayList<ProductHome> list)
     {
         RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
         recyclerView.setLayoutManager(layoutManager);
         adapter=new VendeurAdapter(getContext(),list,this);
         recyclerView.setAdapter(adapter);


     }





     @Override
     public void onCreate(@Nullable Bundle savedInstanceState) {



         p=FirebaseDatabase.getInstance().getReference("Products");
         databaseReference= FirebaseDatabase.getInstance().getReference("ProductsHome");
         databaseReference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 // Get Post object and use the values to update the UI
                 for(DataSnapshot d:dataSnapshot.getChildren()){
                     ProductHome p=d.getValue(ProductHome.class);

                     if(vid.equals(p.getIdv())){
                         list.add(p);
                     }

                 }





             }

             @Override
             public void onCancelled(DatabaseError databaseError) {
                 // Getting Post failed, log a message
                 Log.w("jhj", "loadPost:onCancelled", databaseError.toException());
             }
         });
         super.onCreate(savedInstanceState);









}
public void removeItem(int pos) {
   // list.remove(pos);
    //adapter.notifyItemRemoved(pos);

    databaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {



         int i=0;

            for (DataSnapshot dataSnapshot : snapshot.getChildren())

            { if(i==pos){
                databaseReference.child(dataSnapshot.getKey()).removeValue();
                break;
            }else{i++;}


            };

        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });


}
     @Override
     public void onItemClickP(int position) {

     }

     @Override
     public void onItemClickN(int position) {
         removeItem(position);


     }

 }
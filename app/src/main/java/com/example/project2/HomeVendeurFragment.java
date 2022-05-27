 package com.example.project2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.project2.Product;
import com.example.project2.R;

import java.util.ArrayList;

import Adapter.NewsAdapter;
import Adapter.PopularAdapter;
import Adapter.VendeurAdapter;


 public class HomeVendeurFragment extends Fragment {
     RecyclerView recyclerView;
     VendeurAdapter adapter;
     ArrayList<Product> list;
     CardView nouvau;
     ImageView mrkt;


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
     private void setVendeurrRecycler(ArrayList<Product> list)
     {
         RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
         recyclerView.setLayoutManager(layoutManager);
         adapter=new VendeurAdapter(getContext(),list);
         recyclerView.setAdapter(adapter);

     }

     @Override
     public void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         list = new ArrayList<>();
//         list.add(new Product("Sneakers", R.drawable.sneakers, "7000 DA", "4000 DA", "Jusqu'au 25 Mai", "Good"));
//         list.add(new Product("Super-Star", R.drawable.star, "7000 DA", "4000 DA", "Jusqu'au 25 Mai", "Good"));
//         list.add(new Product("Sneakers", R.drawable.sneakers, "7000 DA", "4000 DA", "Jusqu'au 25 Mai", "Good"));
//         list.add(new Product("Super-Star", R.drawable.star, "7000 DA", "4000 DA", "Jusqu'au 25 Mai", "Good"));
//         list.add(new Product("Sneakers", R.drawable.sneakers, "7000 DA", "4000 DA", "Jusqu'au 25 Mai", "Good"));
     }

     }
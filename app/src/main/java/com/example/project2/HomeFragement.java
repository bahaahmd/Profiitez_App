package com.example.project2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project2.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Adapter.NewsAdapter;
import Adapter.PopularAdapter;
import Adapter.ProvinceAdapter;
import Adapter.RecyclerViewInterface;


public class HomeFragement extends Fragment implements RecyclerViewInterface {
    RecyclerView recyclerView,recyclerView2;
    StorageReference storageReference;
    PopularAdapter adapter;
    NewsAdapter adapter_new;
    ArrayList<ProductHome>list;
    ArrayList<ProductHome>news_list;
    ArrayList<Province>provinces_list;
    Spinner spinner_provinces;
    DatabaseReference databaseReference;
    DatabaseReference vender;
    ImageView btnHeart;
    ImageView btnMarket;
    FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home_fragement, container, false);
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        setPopularRecycler();
        recyclerView2=(RecyclerView) view.findViewById(R.id.recyclerView_2);
        setNewsRecycler();
        mAuth = FirebaseAuth.getInstance();
        spinner_provinces=(Spinner) view.findViewById(R.id.spinner);
        setProvinceSpinner(provinces_list);
        vender=FirebaseDatabase.getInstance().getReference("Users").child("Venders");
        btnMarket=view.findViewById(R.id.market);
        btnMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get Post object and use the values to update the UI
                        if(!dataSnapshot.exists()){
                            startActivity(new Intent(getActivity(), ValidationVendeur.class));

                        }else{
                        for(DataSnapshot d:dataSnapshot.getChildren()){
                            Market m=d.getValue(Market.class);

                            FirebaseUser id = FirebaseAuth.getInstance().getCurrentUser();
                            String vid = id.getUid();

                            if(!m.getId().equals(vid)  ) {
                                startActivity(new Intent(getActivity(), ValidationVendeur.class));

                            }else{
                                startActivity(new Intent(getActivity(), ActivityVendeur.class));
break;

                            }
                        }
                        }
}
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        Log.w("jhj", "loadPost:onCancelled", databaseError.toException());
                    }
                };
                vender.addValueEventListener(postListener);
            }
        });

        btnHeart=view.findViewById(R.id.btnHeart);
        btnHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),FavoriteActivity.class));
            }
        });
        return view;


    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list=new ArrayList<>();
        news_list=new ArrayList<>();



        provinces_list=new ArrayList<>();
        provinces_list.add(new Province("Oran"));
        provinces_list.add(new Province("Alger"));
        provinces_list.add(new Province("Tiaret"));
        provinces_list.add(new Province("Setif"));
        provinces_list.add(new Province("Bayed"));
    }
    public void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(getActivity(), Identification.class));
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private void setPopularRecycler()
    {

        databaseReference = FirebaseDatabase.getInstance().getReference("ProductsHome");
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new PopularAdapter(getContext(),list,this);
        recyclerView.setAdapter(adapter);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {

                    try {
                        keys.add(dataSnapshot.getKey());
                        ProductHome product = dataSnapshot.getValue(ProductHome.class);



                        list.add(product);
                    }catch (Exception e){
                        System.out.println("err:"+e.getMessage());
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void setNewsRecycler()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference("ProductsHome");

        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),2);
        recyclerView2.setLayoutManager(layoutManager);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter_new=new NewsAdapter(getActivity(),news_list,this);
        recyclerView2.setAdapter(adapter_new);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                news_list.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
//                    Map<String,String> product = (Map<String, String>) dataSnapshot.getValue();
//                    news_list.add(new Product(product.get("id"),product.get("name"), product.get("ImageUrl"),product.get("price_ancien"),product.get("price_nouveau"),product.get("date"),product.get("rating")));

                    try {
                        keys.add(dataSnapshot.getKey());
                        ProductHome product = dataSnapshot.getValue(ProductHome.class);
                        news_list.add(product);
                    }catch (Exception e){
                        System.out.println("err:"+e.getMessage());
                    }
                };
                adapter_new.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }





    private void setProvinceSpinner(ArrayList<Province> provinces_list)
    {
        ProvinceAdapter provinces=new ProvinceAdapter(getContext(),R.layout.province_item,provinces_list);
        spinner_provinces.setAdapter(provinces);
    }


    @Override
    public void onItemClickP(int position) {

        Intent intentPopular = new Intent(HomeFragement.this.getActivity(),publication_produit.class);
        intentPopular.putExtra("id",list.get(position).getId());
        startActivity(intentPopular);


    }
    @Override
    public void onItemClickN(int position) {

        Intent intentNews = new Intent(HomeFragement.this.getActivity(),publication_produit.class);
        intentNews.putExtra("id",news_list.get(position).getId());
        startActivity(intentNews);


    }

}


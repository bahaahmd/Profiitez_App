package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Adapter.FavoriteAdapter;
import Adapter.RecyclerViewInterface;

public class FavoriteActivity extends AppCompatActivity implements RecyclerViewInterface {
    RecyclerView recyclerView3;
    FavoriteAdapter adapter_fav;
    Button arrow;
    ArrayList<ProductHome>fav_list;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        recyclerView3=(RecyclerView) findViewById(R.id.recview);
        fav_list=new ArrayList<>();

        setFavouriteRecycler(fav_list);
        arrow=findViewById(R.id.arrow);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FavoriteActivity.this,HomePage.class));
            }
        });
    }

    private void setFavouriteRecycler(ArrayList<ProductHome> fav_list)
    {
        String idV = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Favorite").child(idV);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView3.setLayoutManager(layoutManager);
        adapter_fav=new FavoriteAdapter(this,fav_list,this);
        recyclerView3.setAdapter(adapter_fav);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fav_list.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    try {
                        keys.add(dataSnapshot.getKey());
                        ProductHome product = dataSnapshot.getValue(ProductHome.class);
                        fav_list.add(product);
                    }catch (Exception e){
                        System.out.println("err:"+e.getMessage());
                    }
                };
                adapter_fav.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onItemClickP(int position) {
        Intent intentFavorite = new Intent(FavoriteActivity.this,publication_produit.class);
        intentFavorite.putExtra("id",fav_list.get(position).getId());
        startActivity(intentFavorite);

    }

    @Override
    public void onItemClickN(int position) {

    }


}
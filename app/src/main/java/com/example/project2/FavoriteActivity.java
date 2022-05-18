package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

import Adapter.FavoriteAdapter;

public class FavoriteActivity extends AppCompatActivity {
    RecyclerView recyclerView3;
    FavoriteAdapter adapter_fav;
    Button arrow;
    ArrayList<Product>fav_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        recyclerView3=(RecyclerView) findViewById(R.id.recview);
        fav_list=new ArrayList<>();
        fav_list.add(new Product("Sneakers", R.drawable.sneakers,"7000 DA","4000 DA","Jusqu'au 25 Mai","Good"));
        fav_list.add(new Product("Super-Star", R.drawable.star,"7000 DA","4000 DA","Jusqu'au 25 Mai","Good"));
        fav_list.add(new Product("Super-Star", R.drawable.star,"7000 DA","4000 DA","Jusqu'au 25 Mai","Good"));
        fav_list.add(new Product("Sneakers", R.drawable.sneakers,"7000 DA","4000 DA","Jusqu'au 25 Mai","Good"));
        fav_list.add(new Product("Sneakers", R.drawable.sneakers,"7000 DA","4000 DA","Jusqu'au 25 Mai","Good"));
        fav_list.add(new Product("Sneakers", R.drawable.sneakers,"7000 DA","4000 DA","Jusqu'au 25 Mai","Good"));

        setFavouriteRecycler(fav_list);
        arrow=findViewById(R.id.arrow);
       arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FavoriteActivity.this,HomePage.class));
            }
        });
    }

    private void setFavouriteRecycler(ArrayList<Product> fav_list)
    {

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView3.setLayoutManager(layoutManager);
        adapter_fav=new FavoriteAdapter(this,fav_list);
        recyclerView3.setAdapter(adapter_fav);
    }

}
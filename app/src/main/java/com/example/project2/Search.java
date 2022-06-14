package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Adapter.NewsAdapter;
import Adapter.PopularAdapter;
import Adapter.RecyclerViewInterface;
import Adapter.SearchAdapter;

public class Search extends Fragment implements RecyclerViewInterface {
    RecyclerView recyclerView;
    SearchAdapter adapter;
    SearchView searchView;
    ArrayList<ProductHome> list;
    NewsAdapter adapter_new;
    RelativeLayout parent;
    DatabaseReference databaseReference;
    LottieAnimationView lotie;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_search, container, false);
        recyclerView=(RecyclerView) view.findViewById(R.id.search_recView);
        setNewsRecycler(list);
        recyclerView.setVisibility(View.INVISIBLE);
        lotie=view.findViewById(R.id.anima);
        lotie.playAnimation();
        parent=(RelativeLayout) view.findViewById(R.id.parent);
        searchView= view.findViewById(R.id.search_bar);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.isEmpty()){
                    lotie.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }else{
                    lotie.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                }

                filter(newText);
                return false;
            }
        });

        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        getSearch();


    }
    private void setNewsRecycler(ArrayList<ProductHome> news_list)
    {
        GridLayoutManager layoutManager_2=new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager_2);
        layoutManager_2.setOrientation(LinearLayoutManager.VERTICAL);
        adapter=new SearchAdapter(getContext(),news_list,this);
        recyclerView.setAdapter(adapter);

    }
    private  void getSearch(){
        list=new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("ProductsHome");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    try {
                        keys.add(dataSnapshot.getKey());
                        ProductHome productHome = dataSnapshot.getValue(ProductHome.class);
                        list.add(productHome);
                    }catch (Exception e){
                        System.out.println("err:"+e.getMessage());
                    }
                };
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void filter(String text) {
        ArrayList<ProductHome> filteredList = new ArrayList<>();

        for (ProductHome item : list) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
    }

    @Override
    public void onItemClickP(int position) {

        Intent intentSearch = new Intent(Search.this.getActivity(),publication_produit.class);
        intentSearch.putExtra("id",list.get(position).getId());
        startActivity(intentSearch);

    }

    @Override
    public void onItemClickN(int position) {

    }
}
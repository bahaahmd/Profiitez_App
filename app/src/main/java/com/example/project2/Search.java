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

import Adapter.PopularAdapter;
import Adapter.RecyclerViewInterface;
import Adapter.SearchAdapter;

public class Search extends Fragment implements RecyclerViewInterface {
    RecyclerView recyclerView;
    SearchAdapter adapter;
    SearchView searchView;
    ArrayList<Search_item> list;
    RelativeLayout parent;
    DatabaseReference databaseReference;
    LottieAnimationView lotie;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_search, container, false);
        recyclerView=(RecyclerView) view.findViewById(R.id.search_recView);
        setSearchRecycler(list);
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
                lotie.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
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
//        list.add(new Search_item("Nike",""));
//        list.add(new Search_item("Adidas",""));









    }



    private void setSearchRecycler(ArrayList<Search_item> list)
    {
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new SearchAdapter(getContext(),list,this);
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
//                    try {
//                    Map<String,String> search_item = (Map<String, String>) dataSnapshot.getValue();
//
//                    list.add(new Search_item(search_item.get("name"), search_item.get("ImageUrl")));
//                    }catch (Exception e){
//                        System.out.println("err:"+e.getMessage());
//                    }

                    try {
                        keys.add(dataSnapshot.getKey());
                        Search_item search_item = dataSnapshot.getValue(Search_item.class);
                        list.add(search_item);
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
        ArrayList<Search_item> filteredList = new ArrayList<>();

        for (Search_item item : list) {
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
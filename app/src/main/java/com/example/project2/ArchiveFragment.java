package com.example.project2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import Adapter.FavoriteAdapter;
import Adapter.RecyclerViewInterface;
import Adapter.VendeurAdapter;


public class ArchiveFragment extends Fragment implements RecyclerViewInterface {
RecyclerView recyclerView1;
ArrayList<ProductHome> list1;
FavoriteAdapter adapter1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_archive, container, false);
       recyclerView1= view.findViewById(R.id.recview_archive);
        setArchiveRecycler(list1);

        return view;
    }



    private void setArchiveRecycler(ArrayList<ProductHome> list)
    {

        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter1 =new FavoriteAdapter(getContext(),list,this);
        recyclerView1.setAdapter(adapter1);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list1 = new ArrayList<>();
list1.add(new ProductHome("eeee","ddd","ee","eeee","ddd","ee","rr"));
        list1.add(new ProductHome("eeee","ddd","ee","eeee","ddd","ee","rr"));

    }

    @Override
    public void onItemClickP(int position) {

    }

    @Override
    public void onItemClickN(int position) {

    }







}
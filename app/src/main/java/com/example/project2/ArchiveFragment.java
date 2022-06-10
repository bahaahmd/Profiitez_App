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

import Adapter.ArchiveAdapter;
import Adapter.RecyclerViewInterface;
import Adapter.VendeurAdapter;


public class ArchiveFragment extends Fragment implements RecyclerViewInterface {
RecyclerView recyclerView;
        ArrayList<ProductHome> list;
        ArchiveAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_archive, container, false);
                recyclerView=(RecyclerView) view.findViewById(R.id.recview_archive);
        list=new ArrayList<>();
        setArchiveRecycler(list);
        return view;
    }
    private void setArchiveRecycler(ArrayList<ProductHome> list)
    {
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new ArchiveAdapter(getContext(),list,this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
    }






    @Override
    public void onItemClickP(int position) {

    }

    @Override
    public void onItemClickN(int position) {

    }
}
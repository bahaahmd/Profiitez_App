package com.example.project2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

import Adapter.NewsAdapter;
import Adapter.PopularAdapter;
import Adapter.ProvinceAdapter;


public class HomeFragement extends Fragment {
    RecyclerView recyclerView,recyclerView2,recyclerView3;
    PopularAdapter adapter;
    NewsAdapter adapter_new;
    ArrayList<Product> list;
    ArrayList<Product>news_list;
    ArrayList<Province>provinces_list;
    Spinner spinner_provinces;
    ImageView btnHeart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home_fragement, container, false);
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        setPopularRecycler(list);
        recyclerView2=(RecyclerView) view.findViewById(R.id.recyclerView_2);
        setNewsRecycler(news_list);
        spinner_provinces=(Spinner) view.findViewById(R.id.spinner);
        setProvinceSpinner(provinces_list);
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
        list.add(new Product("Sneakers", R.drawable.sneakers,"7000 DA","4000 DA","Jusqu'au 25 Mai","Good"));
        list.add(new Product("Super-Star", R.drawable.star,"7000 DA","4000 DA","Jusqu'au 25 Mai","Good"));
        list.add(new Product("Sneakers", R.drawable.sneakers,"7000 DA","4000 DA","Jusqu'au 25 Mai","Good"));
        list.add(new Product("Super-Star", R.drawable.star,"7000 DA","4000 DA","Jusqu'au 25 Mai","Good"));
        list.add(new Product("Sneakers", R.drawable.sneakers,"7000 DA","4000 DA","Jusqu'au 25 Mai","Good"));

        news_list=new ArrayList<>();
        news_list.add(new Product("Sneakers", R.drawable.sneakers,"7000 DA","4000 DA","Jusqu'au 25 Mai","Good"));
        news_list.add(new Product("Super-Star", R.drawable.star,"7000 DA","4000 DA","Jusqu'au 25 Mai","Good"));
        news_list.add(new Product("Super-Star", R.drawable.star,"7000 DA","4000 DA","Jusqu'au 25 Mai","Good"));
        news_list.add(new Product("Sneakers", R.drawable.sneakers,"7000 DA","4000 DA","Jusqu'au 25 Mai","Good"));
        news_list.add(new Product("Sneakers", R.drawable.sneakers,"7000 DA","4000 DA","Jusqu'au 25 Mai","Good"));
        news_list.add(new Product("Sneakers", R.drawable.sneakers,"7000 DA","4000 DA","Jusqu'au 25 Mai","Good"));



        provinces_list=new ArrayList<>();
        provinces_list.add(new Province("Oran"));
        provinces_list.add(new Province("Alger"));
        provinces_list.add(new Province("Tiaret"));
        provinces_list.add(new Province("Setif"));
        provinces_list.add(new Province("Bayed"));
    }
    private void setPopularRecycler(ArrayList<Product> list)
    {
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new PopularAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);

    }
    private void setNewsRecycler(ArrayList<Product> news_list)
    {
        GridLayoutManager layoutManager_2=new GridLayoutManager(getActivity(),2);
        recyclerView2.setLayoutManager(layoutManager_2);
        layoutManager_2.setOrientation(LinearLayoutManager.VERTICAL);
        adapter_new=new NewsAdapter(getContext(),news_list);
        recyclerView2.setAdapter(adapter_new);
    }



    private void setProvinceSpinner(ArrayList<Province> provinces_list)
    {
        ProvinceAdapter provinces=new ProvinceAdapter(getContext(),R.layout.province_item,provinces_list);
        spinner_provinces.setAdapter(provinces);
    }





}


package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import Adapter.NewsAdapter;
import Adapter.PopularAdapter;

public class HomePage extends AppCompatActivity {
    RecyclerView recyclerView, recyclerView2;
    PopularAdapter adapter;
    NewsAdapter adapter_new;
    BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setItemActiveIndicatorColor(ColorStateList.valueOf(getResources().getColor(R.color.gradient)));

        //To set The default cases
        HomeFragement fragment = new HomeFragement();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, "");
        fragmentTransaction.commit();


        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        fragment = new HomeFragement();
                        break;
                    case R.id.nav_profile:
                        fragment = new Profile();
                        break;
                    case R.id.nav_notification:
                        fragment = new notification();
                        break;
                    case R.id.nav_search:
                        fragment = new Search();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();

                return true;
            }
        });


    }


}
package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ActivityVendeur extends AppCompatActivity {

    BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_vendeur);
nav=findViewById(R.id.bottomNavigationView_vendeur);

        HomeVendeurFragment fragment = new HomeVendeurFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_vendeur, fragment, "");
        fragmentTransaction.commit();

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.Home_vendeur:
                        fragment = new HomeVendeurFragment();

                        break;
                    case R.id.Profile_vendeur:
                        fragment = new Profile();
                        break;
                    case R.id.notification_vendeur:
                        fragment = new notification();
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_vendeur, fragment)
                        .commit();

                return true;
            }
        });

    }

}








package com.example.project2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.NewsAdapter;
import Adapter.NotifAdapter;
import Adapter.SearchAdapter;


public class notification extends Fragment {
    RecyclerView recyclerView;
    NotifAdapter adapter;
    ArrayList<Notif> list;
    ImageView arrow;
    Button button;
    Notif notif;
    final static String CHANNEL_ID="channel id";
    DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_notification, container, false);
        recyclerView=(RecyclerView) view.findViewById(R.id.notifRec);
        View view1=LayoutInflater.from(getContext()).inflate(R.layout.notification_item,container,false);
        button=(Button) view1.findViewById(R.id.btn_menu);
        arrow=view.findViewById(R.id.arrow);
        setNotifRecycler();
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),HomePage.class));
            }
        });

        return view;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list=new ArrayList<>();

    }
    private void setNotifRecycler()
    {
        String idV = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("ProductsHome");

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);

        recyclerView.setLayoutManager(layoutManager);
        adapter=new NotifAdapter(getContext(),list);
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
                         notif = new Notif(product.getImageUrl(),product.getName(),product.getDescription(),product.getDate());

                    }catch (Exception e){
                        System.out.println("err:"+e.getMessage());
                    }
                }
                list.add(notif);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
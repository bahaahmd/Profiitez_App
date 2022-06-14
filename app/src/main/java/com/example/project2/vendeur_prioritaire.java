//package com.example.project2;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//public class vendeur_prioritaire extends AppCompatActivity {
//Button
//    arrow;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.vendeur_prioritaire);
//        arrow=findViewById(R.id.arrow);
//        arrow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(vendeur_prioritaire.this,HomePage.class));
//            }
//        });
//    }
//
//}
package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
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

import Adapter.AlertAdapter;
import Adapter.FavoriteAdapter;
import Adapter.RecyclerViewInterface;
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class vendeur_prioritaire extends AppCompatActivity {
    RecyclerView recyclerView3;
    AlertAdapter alert_adapter;
    Button arrow;
    ArrayList<Vendeur> alert_list;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vendeur_prioritaire);
        recyclerView3=(RecyclerView) findViewById(R.id.recview);
        alert_list=new ArrayList<>();
        setAlertRecycler(alert_list);
        arrow=findViewById(R.id.arrow);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(vendeur_prioritaire.this,HomePage.class));
            }
        });
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView3);
        recyclerView3.setAdapter(alert_adapter);
    }

    ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int pos=viewHolder.getAdapterPosition();
            alert_list.remove(pos);
            alert_adapter.notifyItemRemoved(pos);
            revAlert();

        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(vendeur_prioritaire.this,c,recyclerView,viewHolder,dX,dY,
                    actionState,isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(vendeur_prioritaire.this,R.color.gradient))
                    .addActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


    private void setAlertRecycler(ArrayList<Vendeur> alert_list)
    {
        String idV = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Alert").child(idV);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView3.setLayoutManager(layoutManager);
        alert_adapter=new AlertAdapter(alert_list,this);
        recyclerView3.setAdapter(alert_adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                alert_list.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    try {
                        keys.add(dataSnapshot.getKey());
                        Vendeur vndr = dataSnapshot.getValue(Vendeur.class);
                        alert_list.add(vndr);
                    }catch (Exception e){
                        System.out.println("err:"+e.getMessage());
                    }
                };
                alert_adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    void revAlert(){

        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference alertProduct = FirebaseDatabase.getInstance().getReference().child("Alert").child(id);
        alertProduct.child(id).removeValue();

    }


}

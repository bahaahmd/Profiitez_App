package com.example.project2;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapter.FavoriteAdapter;
import Adapter.RecyclerViewInterface;


public class ArchiveFragment extends Fragment implements RecyclerViewInterface {


        
     
    FirebaseUser id = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseReference;

    String vid = id.getUid();

RecyclerView recyclerView1;
ArrayList<ProductHome> list=new ArrayList();

FavoriteAdapter adapter1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_archive, container, false);
       recyclerView1= view.findViewById(R.id.recview_archive);
        setArchiveRecycler(list);

        return view;
    }



    private void setArchiveRecycler(ArrayList<ProductHome> list) {
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter1 =new FavoriteAdapter(getContext(),list,this);
        recyclerView1.setAdapter(adapter1);
      

    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference= FirebaseDatabase.getInstance().getReference("Archive");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                // Get Post object and use the values to update the UI
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    ProductHome p=d.getValue(ProductHome.class);

                    if(vid.equals(p.getIdv())){
                        list.add(p);
                    }

                }
                System.out.println(list.size());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("jhj", "loadPost:onCancelled", databaseError.toException());
            }
        });


    }

    @Override
    public void onItemClickP(int position) {

    }

    @Override
    public void onItemClickN(int position) {

    }







}
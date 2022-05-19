package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import Adapter.ProfileAdapter;


public class Profile extends Fragment {

    ListView listView;
    String nom[] = {"Favourites", "Settings", "Rate us", "Refer a Friend", "Help", "Log Out"};
    int image[] = {R.drawable.heart_client, R.drawable.setting_client, R.drawable.rate_us_client, R.drawable.share_client, R.drawable.question_client, R.drawable.log_out_client};

    View view;
    ImageView imageView;
    ConstraintLayout parent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.client_profil, container, false);
        listView = (ListView) view.findViewById(R.id.list_view);
        imageView=(ImageView) view.findViewById(R.id.editproffil);
        parent=(ConstraintLayout) view.findViewById(R.id.layoutdialogcontainer);
        ProfileAdapter profileAdapter = new ProfileAdapter(getActivity(), nom, image);
        listView.setAdapter(profileAdapter);
       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("ic_ssetting", "item is clicked @ Position::" + i);
                if (i == 1) {
                    startActivity(new Intent(getActivity(), settings_client.class));

                } else {
                    if (i == 5) {
                        show();
                    }

                }
            }

            private void show() {  AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogTheme);
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_layout_client ,parent);
                builder.setView(view);
                ((TextView) view.findViewById(R.id.texttitle)).setText("WARNING");
                ((TextView) view.findViewById(R.id.textmessage)).setText("Are u sure to Log out from your account?");
                ((Button) view.findViewById(R.id.buttonactionyes)).setText("yes");
                ((Button) view.findViewById(R.id.buttonactionno)).setText("No");
                final AlertDialog alertDialog = builder.create();
                view.findViewById(R.id.buttonactionyes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();

                        Intent intent = new Intent(getActivity(), essai.class);
                        startActivity(intent);
                    }

                });
                view.findViewById(R.id.buttonactionno).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        Toast.makeText(getActivity(), " ", Toast.LENGTH_SHORT).show();

                    }

                });

                alertDialog.show();


            }


        });*/


        return view;

    }
}
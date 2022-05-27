
package com.example.project2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Map;

public class userAdapter extends RecyclerView.Adapter<userAdapter.ViewHolder>  {
    private ArrayList<user> users;
    private Context context;

    public userAdapter(ArrayList<user> users,Context context) {
        this.users = users;
        this.context=context;    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem,parent,false);
        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).asBitmap().load(users.get(position).getImage()).into(holder.imageView);
        holder.name1.setText(users.get(position).getUserName());
        holder.date1.setText(users.get(position).getDate());
        holder.cmnt1.setText(users.get(position).getCommentaire());



    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView name1,date1,cmnt1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
            name1=itemView.findViewById(R.id.user_name);
            date1=itemView.findViewById(R.id.date);
            cmnt1=itemView.findViewById(R.id.cmntr);

        }




    }
}
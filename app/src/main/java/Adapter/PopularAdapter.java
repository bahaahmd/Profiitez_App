package Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project2.Product;
import com.example.project2.ProductHome;
import com.example.project2.R;
import com.example.project2.publication_produit;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ProduitViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<ProductHome> ProductPopulaireList=new ArrayList();


    public PopularAdapter(Context context, ArrayList<ProductHome> list ,RecyclerViewInterface recyclerViewInterface)
    {
        this.context=context;
        this.ProductPopulaireList=list;
        this.recyclerViewInterface=recyclerViewInterface;

    }

    @NonNull
    @Override
    public ProduitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.popular_card_view,parent,false);
        return new ProduitViewHolder(view,recyclerViewInterface);
    }


    @Override
    public void onBindViewHolder(@NonNull ProduitViewHolder holder, final int position) {
        holder.name.setText(ProductPopulaireList.get(position).getName());
        //holder.ProductImage.setImageResource(ProductPopulaireList.get(position).getImageUrl());
        Glide.with(context).asBitmap().load(ProductPopulaireList.get(position).getImageUrl()).into(holder.ProductImage);
        holder.price_old.setText(ProductPopulaireList.get(position).getPrice_ancien());
        holder.price_old.setPaintFlags(holder.price_old.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        holder.price_new.setText(ProductPopulaireList.get(position).getPrice_nouveau());
        holder.date.setText(ProductPopulaireList.get(position).getDate());
//        holder.parent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.startActivity(
//                        new Intent(context, publication_produit.class)
//
//                );
//
//
//            }
//        });



    }



    @Override
    public int getItemCount() {
        return ProductPopulaireList.size();
    }

    public static final class ProduitViewHolder extends RecyclerView.ViewHolder {
        ImageView ProductImage;
        TextView price_old,price_new,name,date;
        CardView parent;

        public ProduitViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            ProductImage=itemView.findViewById(R.id.image2);
            price_old=itemView.findViewById(R.id.AncienPrix);
            price_new=itemView.findViewById(R.id.NouveauPrix);
            name=itemView.findViewById(R.id.Name);
            parent=itemView.findViewById(R.id.parent);
            date=itemView.findViewById(R.id.durée);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface!=null){
                        int pos=getAdapterPosition();
                        if (pos!=RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClickP(pos);
                        }
                    }
                }
            });


        }
    }




}

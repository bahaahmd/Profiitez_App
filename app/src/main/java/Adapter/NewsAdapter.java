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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ProduitViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<ProductHome> ProductNewList =new ArrayList();

    public NewsAdapter(Context context,ArrayList<ProductHome> list ,RecyclerViewInterface recyclerViewInterface)
    {
        this.context=context;
        this.ProductNewList =list;
        this.recyclerViewInterface= recyclerViewInterface;
    }

    @NonNull
    @Override
    public ProduitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.news_card_view,parent,false);
        return new ProduitViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ProduitViewHolder holder,final  int position) {
        holder.name.setText(ProductNewList.get(position).getName());
        holder.date.setText(ProductNewList.get(position).getDate());
        //holder.ProductImage.setImageResource(ProductNewList.get(position).getImageUrl());
        Glide.with(context).asBitmap().load(ProductNewList.get(position).getImageUrl()).into(holder.ProductImage);
        holder.price_old.setText(ProductNewList.get(position).getPrice_ancien());
        holder.price_old.setPaintFlags(holder.price_old.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        holder.price_new.setText(ProductNewList.get(position).getPrice_nouveau());

//        holder.parent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.startActivity(
//                        new Intent(context, publication_produit.class)
//                );
//            }
//        });




    }

    @Override
    public int getItemCount() {
        return ProductNewList.size();
    }
    public static final class ProduitViewHolder extends RecyclerView.ViewHolder {
        ImageView ProductImage;
        TextView price_old,price_new,name,date;
        CardView parent;

        public ProduitViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            ProductImage=itemView.findViewById(R.id.image3);
            price_old=itemView.findViewById(R.id.AncienPrix);
            price_new=itemView.findViewById(R.id.NouveauPrix);
            name=itemView.findViewById(R.id.Name);
            parent=itemView.findViewById(R.id.new_parent);
            date=itemView.findViewById(R.id.durée);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface!=null){
                        int pos=getAdapterPosition();
                        if (pos!=RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClickN(pos);
                        }
                    }
                }
            });



        }
    }




}

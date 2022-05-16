package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.Product;
import com.example.project2.R;

import java.util.ArrayList;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ProduitViewHolder> {

    Context context;
    ArrayList<Product> ProductPopulaireList=new ArrayList();


    public PopularAdapter(Context context,ArrayList<Product> list )
    {
        this.context=context;
        this.ProductPopulaireList=list;
    }

    @NonNull
    @Override
    public PopularAdapter.ProduitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.popular_card_view,parent,false);
        return new ProduitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.ProduitViewHolder holder, final int position) {
        holder.name.setText(ProductPopulaireList.get(position).getName());
        holder.ProductImage.setImageResource(ProductPopulaireList.get(position).getImageUrl());
        holder.price_old.setText(ProductPopulaireList.get(position).getPrice_ancien());
        holder.price_new.setText(ProductPopulaireList.get(position).getPrice_nouveau());
        holder.date.setText(ProductPopulaireList.get(position).getDate());



    }

    @Override
    public int getItemCount() {
        return ProductPopulaireList.size();
    }
    public static final class ProduitViewHolder extends RecyclerView.ViewHolder {
        ImageView ProductImage;
        TextView price_old,price_new,name,date;
        CardView parent;

        public ProduitViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductImage=itemView.findViewById(R.id.image2);
            price_old=itemView.findViewById(R.id.AncienPrix);
            price_new=itemView.findViewById(R.id.NouveauPrix);
            name=itemView.findViewById(R.id.Name);
            parent=itemView.findViewById(R.id.parent);
            date=itemView.findViewById(R.id.durée);



        }
    }




}

package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.Product;
import com.example.project2.R;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ProduitViewHolder> {

    Context context;
    ArrayList<Product> ProductFavoriteListe=new ArrayList();


    public FavoriteAdapter(Context context, ArrayList<Product> list )
    {
        this.context=context;
        this.ProductFavoriteListe=list;
    }

    @NonNull
    @Override
    public ProduitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.favorite_card_view,parent,false);
        return new ProduitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProduitViewHolder holder, int position) {
        holder.name.setText(ProductFavoriteListe.get(position).getName());
        holder.date.setText(ProductFavoriteListe.get(position).getDate());
        holder.ProductImage.setImageResource(ProductFavoriteListe.get(position).getImageUrl());
        holder.price_old.setText(ProductFavoriteListe.get(position).getPrice_ancien());
        holder.price_new.setText(ProductFavoriteListe.get(position).getPrice_nouveau());
    }


    @Override
    public int getItemCount() {
        return ProductFavoriteListe.size();
    }
    public static final class ProduitViewHolder extends RecyclerView.ViewHolder {
        ImageView ProductImage;
        TextView price_old,price_new,name,date;

        public ProduitViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductImage=itemView.findViewById(R.id.image3);
            price_old=itemView.findViewById(R.id.AncienPrix);
            price_new=itemView.findViewById(R.id.NouveauPrix);
            name=itemView.findViewById(R.id.Name);
            date=itemView.findViewById(R.id.durée);


        }
    }




}

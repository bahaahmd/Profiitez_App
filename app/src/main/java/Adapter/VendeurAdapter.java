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

import com.bumptech.glide.Glide;
import com.example.project2.Product;
import com.example.project2.R;

import java.util.ArrayList;

public class VendeurAdapter extends RecyclerView.Adapter<VendeurAdapter.ProduitVendeurViewHolder> {

    Context context;
    ArrayList<Product> ProductVendeur =new ArrayList();

    public VendeurAdapter(Context context, ArrayList<Product> list )
    {
        this.context=context;
        this.ProductVendeur =list;
    }

    @NonNull
    @Override
    public VendeurAdapter.ProduitVendeurViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.vendeur_post,parent,false);
        return new VendeurAdapter.ProduitVendeurViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProduitVendeurViewHolder holder, int position) {
        holder.name.setText(ProductVendeur.get(position).getName());
        holder.date.setText(ProductVendeur.get(position).getDate());
        //holder.ProductImage.setImageResource(ProductVendeur.get(position).getImageUrl());
        Glide.with(context).asBitmap().load(ProductVendeur.get(position).getImageUrl()).into(holder.ProductImage);

        holder.price_old.setText(ProductVendeur.get(position).getPrice_ancien());
        holder.price_new.setText(ProductVendeur.get(position).getPrice_nouveau());
    }

    @Override
    public int getItemCount() {
        return ProductVendeur.size();
    }
    public static final class ProduitVendeurViewHolder extends RecyclerView.ViewHolder {
        ImageView ProductImage;
        TextView price_old,price_new,name,date;


        public ProduitVendeurViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductImage=itemView.findViewById(R.id.image_vendeur);
            price_old=itemView.findViewById(R.id.AncienPrix_vendeur);
            price_new=itemView.findViewById(R.id.NouveauPrix_vendeur);
            name=itemView.findViewById(R.id.cat_vendeur);

            date=itemView.findViewById(R.id.date_vendeur);



        }
    }
}



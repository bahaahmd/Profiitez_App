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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ProduitViewHolder> {

    Context context;
    ArrayList<Product> ProductNewList =new ArrayList();

    public NewsAdapter(Context context,ArrayList<Product> list )
    {
        this.context=context;
        this.ProductNewList =list;
    }

    @NonNull
    @Override
    public ProduitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.news_card_view,parent,false);
        return new ProduitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProduitViewHolder holder,final  int position) {
        holder.name.setText(ProductNewList.get(position).getName());
        holder.date.setText(ProductNewList.get(position).getDate());
        holder.ProductImage.setImageResource(ProductNewList.get(position).getImageUrl());
        holder.price_old.setText(ProductNewList.get(position).getPrice_ancien());
        holder.price_new.setText(ProductNewList.get(position).getPrice_nouveau());




    }

    @Override
    public int getItemCount() {
        return ProductNewList.size();
    }
    public static final class ProduitViewHolder extends RecyclerView.ViewHolder {
        ImageView ProductImage;
        TextView price_old,price_new,name,date;
        CardView parent;

        public ProduitViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductImage=itemView.findViewById(R.id.image3);
            price_old=itemView.findViewById(R.id.AncienPrix);
            price_new=itemView.findViewById(R.id.NouveauPrix);
            name=itemView.findViewById(R.id.Name);
            parent=itemView.findViewById(R.id.new_parent);
            date=itemView.findViewById(R.id.durée);



        }
    }




}

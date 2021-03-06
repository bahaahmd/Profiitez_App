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
import com.squareup.picasso.Picasso;

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
        ProductHome productHome =ProductNewList.get(position);
        holder.name.setText(ProductNewList.get(position).getV().getNom());
        holder.date.setText(ProductNewList.get(position).getDate());
        Picasso.get().load(productHome.getImageUrl()).into(holder.ProductImage);
        holder.price_old.setText(ProductNewList.get(position).getPrice_ancien());

        holder.nameItem.setText(ProductNewList.get(position).getDescription());

        holder.nameItem.setText(ProductNewList.get(position).getName());

        holder.price_old.setPaintFlags(holder.price_old.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        holder.price_new.setText(ProductNewList.get(position).getPrice_nouveau());
        Picasso.get().load(productHome.getV().getImage()).into(holder.profile);
        holder.categorie.setText(ProductNewList.get(position).getCategorie());




    }

    @Override
    public int getItemCount() {
        return ProductNewList.size();
    }
    public static final class ProduitViewHolder extends RecyclerView.ViewHolder {
        ImageView ProductImage,profile;
        TextView price_old,price_new,name,date,nameItem,categorie;
        CardView parent;

        public ProduitViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            ProductImage=itemView.findViewById(R.id.image3);
            price_old=itemView.findViewById(R.id.AncienPrix);
            price_new=itemView.findViewById(R.id.NouveauPrix);
            name=itemView.findViewById(R.id.Name);
            parent=itemView.findViewById(R.id.new_parent);
            date=itemView.findViewById(R.id.dur??e);
            nameItem=itemView.findViewById(R.id.type);
            profile=itemView.findViewById(R.id.PhotoProfile);
            categorie=itemView.findViewById(R.id.categories);


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

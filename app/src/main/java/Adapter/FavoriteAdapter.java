package Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project2.Product;
import com.example.project2.ProductHome;
import com.example.project2.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ProduitViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<ProductHome> ProductFavoriteListe;


    public FavoriteAdapter(Context context, ArrayList<ProductHome> list,RecyclerViewInterface recyclerViewInterface )
    {
        this.context=context;
        this.ProductFavoriteListe=list;
        this.recyclerViewInterface=recyclerViewInterface;
    }

    @NonNull
    @Override
    public ProduitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.favorite_card_view,parent,false);
        return new ProduitViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ProduitViewHolder holder, int position) {
        holder.name.setText(ProductFavoriteListe.get(position).getV().getNom());
        holder.date.setText(ProductFavoriteListe.get(position).getDate());
        Glide.with(context).asBitmap().load(ProductFavoriteListe.get(position).getImageUrl()).into(holder.ProductImage);
        holder.price_old.setText(ProductFavoriteListe.get(position).getPrice_ancien());
        holder.price_new.setText(ProductFavoriteListe.get(position).getPrice_nouveau());
        holder.nameItem.setText(ProductFavoriteListe.get(position).getName());
        holder.date.setText(ProductFavoriteListe.get(position).getDate());
        Glide.with(context).asBitmap().load(ProductFavoriteListe.get(position).getV().getImage()).into(holder.profile);


    }


    @Override
    public int getItemCount() {
        return ProductFavoriteListe.size();
    }
    public static final class ProduitViewHolder extends RecyclerView.ViewHolder {
        ImageView ProductImage,profile;
        TextView price_old,price_new,name,date,nameItem;

        public ProduitViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            ProductImage=itemView.findViewById(R.id.image4);
            price_old=itemView.findViewById(R.id.AncienPrix);
            price_new=itemView.findViewById(R.id.NouveauPrix);
            name=itemView.findViewById(R.id.Name);
            date=itemView.findViewById(R.id.durée);
            nameItem=itemView.findViewById(R.id.type);
            profile=itemView.findViewById(R.id.PhotoProfile);


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

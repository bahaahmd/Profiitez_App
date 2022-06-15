package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project2.ModifierPublication;
import com.example.project2.Product;
import com.example.project2.ProductHome;
import com.example.project2.R;

import java.util.ArrayList;

public class VendeurAdapter extends RecyclerView.Adapter<VendeurAdapter.ProduitVendeurViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<ProductHome> ProductVendeur ;


    public VendeurAdapter(Context context, ArrayList<ProductHome> list,RecyclerViewInterface recyclerViewInterface )
    {
        this.context=context;
        this.ProductVendeur =list;
        this.recyclerViewInterface=recyclerViewInterface;
    }


    @NonNull
    @Override
    public VendeurAdapter.ProduitVendeurViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.vendeur_post,parent,false);
        return new VendeurAdapter.ProduitVendeurViewHolder(view,recyclerViewInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull ProduitVendeurViewHolder holder,  int position) {
        holder.name.setText(ProductVendeur.get(position).getName());
        holder.date.setText(ProductVendeur.get(position).getDate());
        //holder.ProductImage.setImageResource(ProductVendeur.get(position).getImageUrl());
        Glide.with(context).asBitmap().load(ProductVendeur.get(position).getImageUrl()).into(holder.ProductImage);

        holder.price_old.setText(ProductVendeur.get(position).getPrice_ancien());
        holder.price_new.setText(ProductVendeur.get(position).getPrice_nouveau());
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ModifierPublication.class);
                intent.putExtra("id",ProductVendeur.get(position).getId());
                System.out.println(ProductVendeur.get(position).getId()+"+++++++++++++++++");
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return ProductVendeur.size();
    }

    public static final class ProduitVendeurViewHolder extends RecyclerView.ViewHolder {
        ImageView ProductImage,delete,update;
        TextView price_old,price_new,name,date;


        public ProduitVendeurViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            delete=itemView.findViewById(R.id.trash);

            ProductImage=itemView.findViewById(R.id.image_vendeur);
            price_old=itemView.findViewById(R.id.AncienPrix_vendeur);
            price_new=itemView.findViewById(R.id.NouveauPrix_vendeur);
            name=itemView.findViewById(R.id.cat_vendeur);

            date=itemView.findViewById(R.id.date_vendeur);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface!=null){
                        int position=getAdapterPosition();
                        if (position!=RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClickN(position);     }
                    }
                }
            });
            update=itemView.findViewById(R.id.update);







        }
    }
}



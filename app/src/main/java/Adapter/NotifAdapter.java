package Adapter;

import android.content.Context;
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
import com.example.project2.Notif;
import com.example.project2.Product;
import com.example.project2.R;

import java.util.ArrayList;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.ProduitViewHolder> {

    Context context;
    ArrayList<Notif> ProductNotification;


    public NotifAdapter(Context context,ArrayList<Notif> list )
    {
        this.context=context;
        this.ProductNotification=list;
    }

    @NonNull
    @Override
    public NotifAdapter.ProduitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.notification_item,parent,false);
        return new ProduitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProduitViewHolder holder, int position) {
        holder.title.setText(ProductNotification.get(position).getTitle());
        Glide.with(context).asBitmap().load(ProductNotification.get(position).getImageUrl()).into(holder.ProductImage);
        holder.timer.setText(ProductNotification.get(position).getTimer());
        holder.description.setText(ProductNotification.get(position).getDescription());
    }



    @Override
    public int getItemCount() {
        return ProductNotification.size();
    }
    public static final class ProduitViewHolder extends RecyclerView.ViewHolder {
        ImageView ProductImage;
        TextView description,title,timer;


        public ProduitViewHolder(@NonNull View itemView) {
            super(itemView);
            ProductImage=itemView.findViewById(R.id.imgNotification);
            title=itemView.findViewById(R.id.title);
            timer=itemView.findViewById(R.id.timer);
            description=itemView.findViewById(R.id.description);



        }
    }




}

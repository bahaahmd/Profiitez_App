package Adapter;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project2.ProductHome;
import com.example.project2.R;
import com.example.project2.Search_item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private ArrayList<ProductHome> mExampleList;
    private Context context;

    public static final class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView ProductImage,profile;
        TextView price_old,price_new,name,date,nameItem;
        CardView parent;

        public SearchViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            ProductImage=itemView.findViewById(R.id.image3);
            price_old=itemView.findViewById(R.id.AncienPrix);
            price_new=itemView.findViewById(R.id.NouveauPrix);
            name=itemView.findViewById(R.id.Name);
            parent=itemView.findViewById(R.id.new_parent);
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

    public SearchAdapter(Context context,ArrayList<ProductHome> exampleList,RecyclerViewInterface recyclerViewInterface) {
        this.context=context;
        mExampleList = exampleList;
        this.recyclerViewInterface=recyclerViewInterface;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.news_card_view,parent,false);
        return new SearchViewHolder(view,recyclerViewInterface);
    }


    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        ProductHome productHome =mExampleList.get(position);
        Glide.with(context).asBitmap().load(mExampleList.get(position).getImageUrl()).into(holder.ProductImage);
        holder.name.setText(mExampleList.get(position).getV().getNom());
        holder.date.setText(mExampleList.get(position).getDate());
        Picasso.get().load(productHome.getImageUrl()).into(holder.ProductImage);
        holder.price_old.setText(mExampleList.get(position).getPrice_ancien());
        holder.nameItem.setText(mExampleList.get(position).getDescription());
        holder.price_old.setPaintFlags(holder.price_old.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        holder.price_new.setText(mExampleList.get(position).getPrice_nouveau());
        Picasso.get().load(productHome.getV().getImage()).into(holder.profile);

    }


    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public void filterList(ArrayList<ProductHome> filteredList) {
        mExampleList = filteredList;
        notifyDataSetChanged();
    }
}
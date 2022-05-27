package Adapter;
import android.content.Context;
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
import com.example.project2.R;
import com.example.project2.Search_item;

import java.util.ArrayList;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private ArrayList<Search_item> mExampleList;
    private Context context;

    public static final class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView item_Image;
        TextView item_name;


        public SearchViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            item_Image=itemView.findViewById(R.id.Image_item);
            item_name=itemView.findViewById(R.id.item_name);

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

    public SearchAdapter(Context context,ArrayList<Search_item> exampleList,RecyclerViewInterface recyclerViewInterface) {
        this.context=context;
        mExampleList = exampleList;
        this.recyclerViewInterface=recyclerViewInterface;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.search_item,parent,false);
        return new SearchViewHolder(view,recyclerViewInterface);
    }


    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        //holder.item_Image.setImageResource(mExampleList.get(position).getImageUrl());
        Glide.with(context).asBitmap().load(mExampleList.get(position).getImageUrl()).into(holder.item_Image);
        holder.item_name.setText(mExampleList.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public void filterList(ArrayList<Search_item> filteredList) {
        mExampleList = filteredList;
        notifyDataSetChanged();
    }
}
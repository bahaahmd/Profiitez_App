package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project2.R;
import com.example.project2.Vendeur;

import java.util.ArrayList;

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.ViewHolder> {

    private ArrayList<Vendeur> listAlert;
    private Context context;

    public AlertAdapter(ArrayList<Vendeur> listAlert, Context context) {
        this.listAlert = listAlert;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).asBitmap().load(listAlert.get(position).getImage()).into(holder.imageView);
        holder.name.setText(listAlert.get(position).getNom());
        holder.date.setText(listAlert.get(position).getOuvert()+"-"+listAlert.get(position).getFermer());

    }

    @Override
    public int getItemCount() {
        return listAlert.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView name,date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageUser);
            name=itemView.findViewById(R.id.userName);
            date=itemView.findViewById(R.id.heureFermEtOuv);


        }
    }
}
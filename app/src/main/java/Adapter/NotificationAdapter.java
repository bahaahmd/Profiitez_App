package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project2.R;

public class NotificationAdapter extends BaseAdapter {
    Context context;
    String nom[];
    String prenom[];
    int image[];
    LayoutInflater inflater;
    public NotificationAdapter(Context ctx, String[] nomlist, String [] prenomlist, int[]images){
        this.context=ctx;
        this.nom=nomlist;
        this.prenom=prenomlist;
        this.image=images;
        inflater=LayoutInflater.from(ctx);
    }
    @Override
    public int getCount() {
        return nom.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.list_notification_setting,null);
        TextView textView=(TextView) view.findViewById(R.id.nom);
        TextView textView2=(TextView) view.findViewById(R.id.prenom);
        ImageView imageView=(ImageView) view.findViewById(R.id.imageView2);
        textView.setText(nom[i]);
        textView2.setText(prenom[i]);
        imageView.setImageResource(image[i]);
        return view;
    }}
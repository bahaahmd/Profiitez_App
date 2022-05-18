package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project2.R;

public class ProfileAdapter extends BaseAdapter {
    Context context;
    String nom[];
    int image[];
    LayoutInflater inflater;
    public ProfileAdapter(Context ctx, String[] nomlist, int[]images){
        this.context=ctx;
        this.nom=nomlist;
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
        view=inflater.inflate(R.layout.list_setting,null);
        TextView textView=(TextView) view.findViewById(R.id.nom);
        ImageView imageView=(ImageView) view.findViewById(R.id.imageView2);
        textView.setText(nom[i]);
        imageView.setImageResource(image[i]);
        return view;
    }
}

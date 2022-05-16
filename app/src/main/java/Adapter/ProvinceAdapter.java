package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project2.Province;
import com.example.project2.R;

import java.util.List;

public class ProvinceAdapter extends ArrayAdapter<Province> {
    LayoutInflater layoutInflater;
    public ProvinceAdapter(@NonNull Context context, int resource, @NonNull List<Province> provinces) {
        super(context, resource, provinces);
        layoutInflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view =layoutInflater.inflate(R.layout.province_item,null,true);
        Province province=getItem(position);
        TextView province_name=(TextView) view.findViewById(R.id.province_name);
        province_name.setText(province.getProvince_name());
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
            convertView =layoutInflater.inflate(R.layout.province_item,parent,false);
        Province province=getItem(position);
        TextView province_name=(TextView) convertView.findViewById(R.id.province_name);
        province_name.setText(province.getProvince_name());
        return convertView;
    }
}

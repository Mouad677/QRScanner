package com.example.scanqr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    ArrayList<Wifi> Wifi_list;
    Context context;
    LayoutInflater inflater;

    public ListViewAdapter(Context context,ArrayList<Wifi> Wifi_list) {
        this.context = context;
        this.Wifi_list = Wifi_list;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return Wifi_list.size();
    }

    @Override
    public Object getItem(int position) {
        return Wifi_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.wifi_custom, parent, false);
        Wifi wifi = (Wifi) this.Wifi_list.get(position);
        TextView txt_Wname = (TextView) convertView.findViewById(R.id.txt_Wname);
        TextView txt_WPassword = (TextView) convertView.findViewById(R.id.txt_Wpassword);
        txt_Wname.setText(wifi.nom);
        txt_WPassword.setText(wifi.getPassword());
        return convertView;
    }
}

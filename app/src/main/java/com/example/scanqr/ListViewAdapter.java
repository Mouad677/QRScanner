package com.example.scanqr;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    ArrayList<Wifi> Wifi_list;
    Context context;
    LayoutInflater inflater;

    public ListViewAdapter(Context context,ArrayList<Wifi> Wifi_list) {
        this.context = context;
        this.Wifi_list = Wifi.listWifi;
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
        ImageView img_delete = (ImageView) convertView.findViewById(R.id.img_delete);
        txt_Wname.setText(wifi.nom);
        txt_WPassword.setText(wifi.getPassword());
        img_delete.setOnClickListener((View view) ->{
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("Message");
            alert.setMessage("Delete this Wifi ?");
            alert.setPositiveButton("Yes", (DialogInterface d, int which) ->{
                Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right);
                animation.setDuration(300);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        WifiHistoric.deleteWifi(wifi.nom);
                        Wifi_list.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Wifi deleted!", Toast.LENGTH_SHORT).show();
                        d.dismiss();
                    }
                    
                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                view.startAnimation(animation);

            });
            alert.setNegativeButton("No", (DialogInterface d, int which) ->{
                d.cancel();
            });
            alert.show();

        });
        return convertView;
    }
}

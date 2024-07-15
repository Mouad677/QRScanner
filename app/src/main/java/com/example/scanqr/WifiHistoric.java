package com.example.scanqr;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class WifiHistoric extends AppCompatActivity {
    static SQLiteDatabase Db;
    ListView lv;
    ArrayList<Wifi> list;
    Wifi wifi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_historic);
        lv = findViewById(R.id.lv);

        Db = WifiActivity.db;
        list = Wifi.listWifi;
        Show();
        ListViewAdapter adapter = new ListViewAdapter(this,list);
        lv.setAdapter(adapter);

    }


    public void Show(){
        try {
            String req = "SELECT * FROM WifiTable";
            Cursor c = Db.rawQuery("SELECT * FROM WifiTable", null);
            c.moveToFirst();
            String s = "";
            while (!c.isAfterLast()){
                String name = c.getString(0);
                String password = c.getString(1);
                wifi = new Wifi(name, password);
                if(!list.contains(wifi)){
                    list.add(wifi);
                }else {
                    Toast.makeText(this, "Ce wifi existe deja!", Toast.LENGTH_SHORT).show();
                }

                c.moveToNext();
            }
        }catch (Exception e){
            System.out.println("Error : " + e.getMessage().toString());
        }

    }

    public static void deleteWifi(String ssid){
        String req = "DELETE FROM WifiTable WHERE Name = ?";
        String[] idW = {String.valueOf(ssid)};
        try {
            Db.execSQL(req, idW);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
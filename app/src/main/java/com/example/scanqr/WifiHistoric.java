package com.example.scanqr;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class WifiHistoric extends AppCompatActivity {
    SQLiteDatabase Db;
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
        Cursor c = Db.rawQuery("SELECT * FROM WifiTable", null);
        c.moveToFirst();
        String s = "";
        while (!c.isAfterLast()){
            String name = c.getString(0);
            String password = c.getString(1);
            wifi = new Wifi(name, password);
            list.add(wifi);
            c.moveToNext();
        }
    }
}
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
    DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_historic);
        lv = findViewById(R.id.lv);

        Db = WifiActivity.db;
        dbHelper = new DbHelper(this);
        Db = dbHelper.getReadableDatabase();
        list = Wifi.listWifi;
        loadWifi();
        ListViewAdapter adapter = new ListViewAdapter(this,list);
        lv.setAdapter(adapter);

    }

    private void loadWifi(){
        list.clear();
        Cursor cursor = Db.rawQuery("SELECT * FROM WifiTable", null);
        if(cursor.moveToFirst()){
            do {
                int nameIndex = cursor.getColumnIndex("Name");
                int passwordIndex = cursor.getColumnIndex("Password");
                String namew  = cursor.getString(nameIndex);
                String passwordw = cursor.getString(passwordIndex);
                Wifi wf = new Wifi(namew, passwordw);
                list.add(wf);
            }while (cursor.moveToNext());
        }else {
            Toast.makeText(this, "No wifi found !", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
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
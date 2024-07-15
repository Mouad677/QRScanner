package com.example.scanqr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WifiActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txt_WifiName, txt_WifiPassword;
    Button bt_copy;
    static SQLiteDatabase db;
    String wifiName = "";
    String wifiPassword = "";
    ImageView img_historic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        img_historic = findViewById(R.id.img_hist);

        img_historic.setOnClickListener((View view) -> {
            startActivity(new Intent(WifiActivity.this, WifiHistoric.class));
        });

        initViews();
        CreerTable();

    }

    public void CreerTable(){
        String req = "CREATE TABLE IF NOT EXISTS WifiTable (Name TEXT, Password TEXT)";
        db.execSQL(req);
    }

    public void initViews() {
        txt_WifiName = findViewById(R.id.txt_wifiName);
        txt_WifiPassword = findViewById(R.id.txt_wifiPassword);
        bt_copy = findViewById(R.id.btCopy);
        db = openOrCreateDatabase("WifiDB.db", Context.MODE_PRIVATE, null);

        ArrayList<String> WifiList = new ArrayList<>();
        WifiList = getIntent().getStringArrayListExtra("Wifi");
        wifiName = WifiList.get(0).toString();
        wifiPassword = WifiList.get(1).toString();
        txt_WifiName.setText(wifiName);
        txt_WifiPassword.setText(wifiPassword);

        ContentValues values = new ContentValues();
        values.put("Name", wifiName);
        values.put("Password", wifiPassword);

        db.insert("WifiTable",null, values);


        bt_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                String password = txt_WifiPassword.getText().toString();
                ClipData clip = ClipData.newPlainText("label", password);
                if (clipboard == null || clip == null) return;
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(),"Password Copied", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnScanBarcode:
                startActivity(new Intent(WifiActivity.this, ScannedBarcodeActivity.class));
                break;
        }
    }
}
package com.example.scanqr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnScanBarcode;

    ImageView img_menu;
    TextView txt_github;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScanBarcode = findViewById(R.id.btnScanBarcode);
        img_menu = findViewById(R.id.img_menu);
        txt_github = findViewById(R.id.txt_github);

        txt_github.setOnClickListener((View view) ->{
            Uri uri = Uri.parse("https://github.com/Mouad677");
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        });

        btnScanBarcode.setOnClickListener((View view) ->{
            startActivity(new Intent(MainActivity.this, ScannedBarcodeActivity.class));
        });

        img_menu.setOnClickListener((View view) ->{
            PopupMenu popupMenu = new PopupMenu(this, img_menu);
            popupMenu.inflate(R.menu.menu);
            popupMenu.show();

            popupMenu.setOnMenuItemClickListener((MenuItem item) ->{
                switch (item.getItemId()){
                    case R.id.historicW:{
                        startActivity(new Intent(MainActivity.this, WifiHistoric.class));
                        break;
                    }
                    case R.id.generateW:{
                        startActivity(new Intent(MainActivity.this, WifiGenerator.class));
                        break;
                    }
                }
                return false;
            });
        });
    }
}
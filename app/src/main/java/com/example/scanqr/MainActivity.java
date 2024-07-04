package com.example.scanqr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnScanBarcode;

    ImageView img_Historic;
    TextView txt_github;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScanBarcode = findViewById(R.id.btnScanBarcode);
        img_Historic = findViewById(R.id.img_historic);
        txt_github = findViewById(R.id.txt_github);

        txt_github.setOnClickListener((View view) ->{
            Uri uri = Uri.parse("https://github.com/Mouad677");
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        });

        btnScanBarcode.setOnClickListener((View view) ->{
            startActivity(new Intent(MainActivity.this, ScannedBarcodeActivity.class));
        });

        img_Historic.setOnClickListener((View view) ->{
            startActivity(new Intent(MainActivity.this, WifiHistoric.class));
        });
    }
}
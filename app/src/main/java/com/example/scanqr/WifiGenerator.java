package com.example.scanqr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.OutputStream;


public class WifiGenerator extends AppCompatActivity {
    EditText ed_ssid, ed_password;
    ImageView img_Qr;
    Button bt_generate, bt_save;
    private Bitmap bitmap;
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_generator);
        ed_ssid = findViewById(R.id.ed_ssid);
        ed_password = findViewById(R.id.ed_password);
        img_Qr = findViewById(R.id.img_Qr);
        bt_generate = findViewById(R.id.bt_generate);
        bt_save = findViewById(R.id.bt_save);

        bt_generate.setOnClickListener(this::generateQr);
        bt_save.setOnClickListener(this::SaveQr);

    }

    private void generateQr(View view){
        String nameW = ed_ssid.getText().toString().trim();
        String passwordW = ed_password.getText().toString().trim();
        if(nameW.isEmpty() || passwordW.isEmpty()){
            Toast.makeText(this, "Enter the wifi name and the wifi password", Toast.LENGTH_LONG).show();
        }
        String QrContent = "WIFI:T:WPA;S:" + nameW + ";P:" + passwordW + ";;";
        try {
            BarcodeEncoder encoder = new BarcodeEncoder();
            BitMatrix bitMatrix = encoder.encode(QrContent, BarcodeFormat.QR_CODE, 500, 500);
            bitmap = encoder.createBitmap(bitMatrix);
            img_Qr.setImageBitmap(bitmap);
        }catch (WriterException e){
            e.printStackTrace();
            Toast.makeText(this, "Error generating Qr code", Toast.LENGTH_SHORT).show();
        }
    }

    private void SaveQr(View view){
        if (bitmap == null){
            Toast.makeText(this, "Generate the Qr code", Toast.LENGTH_LONG).show();
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }else {
        saveImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if(requestCode == PERMISSION_REQUEST_CODE){
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                saveImage();
                }else{
                    Toast.makeText(this, "Permission denied to write to external storage", Toast.LENGTH_SHORT).show();
                }
            }
    }

    private void saveImage(){
        OutputStream outputStream;
        try {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "QRCode_" + System.currentTimeMillis() +".png");
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/QRCode");

            }
            Uri imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if(imageUri != null){
                outputStream = getContentResolver().openOutputStream(imageUri);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                if(imageUri != null){
                    outputStream.close();
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle("Message");
                    alert.setMessage("Qr Code Saved to gallery");
                    alert.setPositiveButton("Ok", (DialogInterface d, int which) ->{
                       d.cancel();
                    });
                    alert.show();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error saving Qr Code!", Toast.LENGTH_LONG).show();
        }
    }

}
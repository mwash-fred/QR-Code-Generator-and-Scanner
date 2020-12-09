package com.example.quickpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
//QRGenerator imports
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QrGenerator extends AppCompatActivity {

    private String TAG = "GenerateQRCode";
    EditText msgEncode;
    ImageView qrcode;
    Button btnGenerate;
    String inputTxt;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    ImageButton qr_scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_generator);
        btnGenerate = findViewById(R.id.btnGenerate);
        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgEncode = QrGenerator.this.findViewById(R.id.enterText);
                String text = msgEncode.getText().toString().trim();
                QrGenerator.this.generate(text);
            }
        });
        qr_scanner = findViewById(R.id.qr_sanner);
        qr_scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QrGenerator.this, ScannerActivity.class));
            }
        });
    }

    public void generate(String text){
        inputTxt = text.trim();
        if(inputTxt.length()>0){
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = Math.min(width, height);
            smallerDimension = smallerDimension * 3/4;
            qrgEncoder = new QRGEncoder(inputTxt, null, QRGContents.Type.TEXT,smallerDimension);
            try{
                bitmap = qrgEncoder.encodeAsBitmap();
                qrcode = findViewById(R.id.qrcode);
                qrcode.setImageBitmap(bitmap);
            }
            catch (WriterException e) {
                Log.v(TAG, e.toString());
            }
        }else {
            msgEncode.setError("Required");
        }
    }
}

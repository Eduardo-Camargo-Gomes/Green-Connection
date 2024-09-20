package com.example.pdm_qrcode01;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class EmailActivity extends AppCompatActivity {

    private ImageView qrCodeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        qrCodeImageView = findViewById(R.id.qrCodeImageView);

        String qrCodeText = getIntent().getStringExtra("qrCodeText");
        if (qrCodeText != null) {
            Bitmap qrCodeBitmap = QRCodeGenerator.generateQRCode(qrCodeText, 500, 500);
            if (qrCodeBitmap != null) {
                qrCodeImageView.setImageBitmap(qrCodeBitmap);
            }
        }
    }
}

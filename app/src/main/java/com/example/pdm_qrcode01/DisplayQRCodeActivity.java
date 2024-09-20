package com.example.pdm_qrcode01;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayQRCodeActivity extends AppCompatActivity {

    private ImageView qrCodeImageView;
    private TextView qrCodeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_qrcode);

        qrCodeImageView = findViewById(R.id.qrCodeImageView);
        qrCodeTextView = findViewById(R.id.qrCodeTextView);

        // Receba os dados da Intent
        Intent intent = getIntent();
        String text = intent.getStringExtra("TEXT");
        Bitmap qrCodeBitmap = intent.getParcelableExtra("QR_CODE_BITMAP");

        // Defina o texto e a imagem no layout
        qrCodeTextView.setText(text);
        if (qrCodeBitmap != null) {
            qrCodeImageView.setImageBitmap(qrCodeBitmap);
        }
    }
}

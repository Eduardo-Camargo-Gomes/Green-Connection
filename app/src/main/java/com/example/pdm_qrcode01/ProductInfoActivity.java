package com.example.pdm_qrcode01;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProductInfoActivity extends AppCompatActivity {

    private TextView tvBarcode;
    private TextView tvDate;
    private Button btnSearchWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);

        tvBarcode = findViewById(R.id.tvBarcode);
        tvDate = findViewById(R.id.tvDate);
        btnSearchWeb = findViewById(R.id.btnSearchWeb);

        Intent intent = getIntent();
        String barcode = intent.getStringExtra("barcode");
        String date = intent.getStringExtra("date");

        tvBarcode.setText("Código de Barras: " + barcode);
        tvDate.setText("Data da Leitura: " + date);

        btnSearchWeb.setOnClickListener(view -> {
            String searchQuery = "https://www.google.com/search?q=" + Uri.encode(barcode);
            Intent searchIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(searchQuery));
            startActivity(searchIntent);
        });
    }
}

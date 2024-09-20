package com.example.pdm_qrcode01;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int NAV_LEER = R.id.navigation_leer;
    private static final int NAV_CRIAR = R.id.navigation_criar;
    private static final int NAV_HISTORICO = R.id.navigation_historico;
    private static final int NAV_CONFIGURACOES = R.id.navigation_configuracoes;

    private IntentIntegrator integrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_leer) {

                return true;
            } else if (itemId == R.id.navigation_criar) {
                startActivity(new Intent(MainActivity.this, CriarActivity.class));
                return true;
            } else if (itemId == R.id.navigation_historico) {

                return true;
            } else if (itemId == R.id.navigation_configuracoes) {

                return true;
            }
            return false;
        });


        View btnScanQRCode = findViewById(R.id.btnScanQRCode);
        View btnScanBarcode = findViewById(R.id.btnScanBarcode);

        integrator = new IntentIntegrator(this);
        integrator.setPrompt("Aponte a câmera para o código");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);

        btnScanQRCode.setOnClickListener(view -> {
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            integrator.initiateScan();
        });

        btnScanBarcode.setOnClickListener(view -> {
            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.initiateScan();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Leitura cancelada", Toast.LENGTH_SHORT).show();
            } else {
                String scanResult = result.getContents();
                String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

                if (scanResult.startsWith("http")) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(scanResult));
                    startActivity(browserIntent);
                } else {
                    Intent productInfoIntent = new Intent(MainActivity.this, ProductInfoActivity.class);
                    productInfoIntent.putExtra("barcode", scanResult);
                    productInfoIntent.putExtra("date", currentDate);
                    startActivity(productInfoIntent);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

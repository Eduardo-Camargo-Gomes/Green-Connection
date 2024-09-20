package com.example.pdm_qrcode01;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CriarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar);

        View primeiraView = findViewById(R.id.primeiraView);
        primeiraView.setOnClickListener(v -> {
            startActivity(new Intent(CriarActivity.this, EscolhaActivity.class));
        });

        View segundaView = findViewById(R.id.segundaView);
        segundaView.setOnClickListener(v -> {
            // Implementar navegação para outra tela
        });
    }
}

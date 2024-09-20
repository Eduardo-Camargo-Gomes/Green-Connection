package com.example.pdm_qrcode01;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EscolhaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha);

        Button btnTexto = findViewById(R.id.btnTexto);
        btnTexto.setOnClickListener(v -> {
            startActivity(new Intent(EscolhaActivity.this, TextoActivity.class));
        });

        Button btnEmail = findViewById(R.id.btnEmail);
        btnEmail.setOnClickListener(v -> {
            startActivity(new Intent(EscolhaActivity.this, EmailActivity.class));
        });

        Button btnAplicativo = findViewById(R.id.btnAplicativo);
        btnAplicativo.setOnClickListener(v -> {
            startActivity(new Intent(EscolhaActivity.this, AplicativoActivity.class));
        });
    }
}

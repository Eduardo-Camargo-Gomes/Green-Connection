package com.example.pdm_qrcode01;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TextoActivity extends AppCompatActivity {

    private EditText editText;
    private Button btnPronto;
    private ImageView qrCodeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texto);

        // Inicialize os componentes da interface
        editText = findViewById(R.id.editText);
        btnPronto = findViewById(R.id.btnPronto);
        qrCodeImageView = findViewById(R.id.qrCodeImageView);

        // Defina o comportamento do botão quando clicado
        btnPronto.setOnClickListener(v -> {
            // Obtenha o texto do EditText
            String text = editText.getText().toString().trim();
            // Verifique se o texto não está vazio
            if (text.isEmpty()) {
                Toast.makeText(TextoActivity.this, "Digite um texto", Toast.LENGTH_SHORT).show();
                return;
            }

            // Gere o QR Code
            Bitmap qrCodeBitmap = QRCodeGenerator.generateQRCode(text, 512, 512);
            // Verifique se o QR Code foi gerado corretamente
            if (qrCodeBitmap != null) {
                // Exiba o QR Code no ImageView
                qrCodeImageView.setImageBitmap(qrCodeBitmap);
            } else {
                Toast.makeText(TextoActivity.this, "Erro ao gerar QR Code", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

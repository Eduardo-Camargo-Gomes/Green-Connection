package com.example.green_connect

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.green_connect.databinding.ActivityGerarQrcodeBinding

class GerarQRCodeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGerarQrcodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGerarQrcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Defina o ID da planta como 1360058
        val plantId = "1360058" // ID da primeira planta
        val url = "https://green-connect-ecg-default-rtdb.firebaseio.com/plantas/planta_$plantId.json"

        // Gerar o QR Code vinculado ao URL da planta
        val bitmapQRCode: Bitmap? = QRCodeUtil.gerarQRCode(url)

        if (bitmapQRCode != null) {
            binding.imageViewQRCode.setImageBitmap(bitmapQRCode)
        } else {
            Toast.makeText(this, "Erro ao gerar QR Code", Toast.LENGTH_SHORT).show()
        }
    }
}

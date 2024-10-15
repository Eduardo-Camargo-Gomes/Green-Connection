package com.example.green_connect

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.green_connect.databinding.ActivityQrcodeDisplayBinding

class QRCodeDisplayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQrcodeDisplayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrcodeDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bitmapQRCode = intent.getParcelableExtra<Bitmap>("QR_CODE_BITMAP")
        if (bitmapQRCode != null) {
            binding.qrCodeImageView.setImageBitmap(bitmapQRCode)
        }
    }
}

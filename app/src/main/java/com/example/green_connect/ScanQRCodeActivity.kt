package com.example.green_connect

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator

class ScanQRCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iniciarEscaneamento()
    }

    private fun iniciarEscaneamento() {
        val integrator = IntentIntegrator(this)
        integrator.setPrompt("Escaneie o QR code da planta")
        integrator.setBeepEnabled(true)
        integrator.setOrientationLocked(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                val plantId = result.contents // ID da planta escaneado
                buscarDadosDaPlanta(plantId)
            } else {
                Toast.makeText(this, "Escaneamento cancelado", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun buscarDadosDaPlanta(plantId: String) {
        val intent = Intent(this, PlantDetailsActivity::class.java)
        intent.putExtra("PLANT_ID", plantId)
        startActivity(intent)
    }
}

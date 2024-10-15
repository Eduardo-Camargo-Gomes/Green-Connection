package com.example.green_connect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class QRCodeScannerActivity : AppCompatActivity() {
    private val TAG = "QRCodeScannerActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Iniciando o escaneamento do QR Code")

        // Inicia o escaneamento do QR Code
        val integrator = IntentIntegrator(this)
        integrator.setPrompt("Escaneie o QR Code")
        integrator.setOrientationLocked(false)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "Resultado da atividade recebido")

        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Log.d(TAG, "Escaneamento cancelado")
                finish() // Fecha a atividade
            } else {
                Log.d(TAG, "QR Code escaneado, mas indo sempre para planta 1360058")
                onQRCodeScanned() // Ignorar o valor escaneado e ir para a planta 1360058
            }
        } else {
            Log.e(TAG, "Erro ao processar o resultado do QR Code")
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun onQRCodeScanned() {
        // Exibindo um Toast informando que está direcionando para editar planta 1360058
        //Toast.makeText(this, "Direcionando para editar planta 1360058", Toast.LENGTH_SHORT).show()

        // Criando a Intent para a atividade CriarPlanta, sempre com o ID 1360058
        val intent = Intent(this, CriarPlanta::class.java)
        intent.putExtra("PLANT_ID", "1360058") // Passando o ID fixo da planta

        try {
            startActivity(intent) // Iniciando a atividade
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao iniciar CriarPlanta: ${e.message}")
            Toast.makeText(this, "Erro ao direcionar para CriarPlanta", Toast.LENGTH_SHORT).show()
        }

        finish() // Finaliza esta atividade
    }
}

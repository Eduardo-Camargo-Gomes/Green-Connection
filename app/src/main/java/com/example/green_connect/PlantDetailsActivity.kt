package com.example.green_connect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.green_connect.databinding.ActivityPlantDetailsBinding
import com.google.firebase.database.*
import com.example.green_connect.QRCodeUtil.gerarQRCode


class PlantDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlantDetailsBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Carregar diretamente os dados da planta com ID planta_1360058
        carregarDadosDaPlanta()
    }

    private fun carregarDadosDaPlanta() {
        database = FirebaseDatabase.getInstance("https://green-connect-ecg-default-rtdb.firebaseio.com/")
            .getReference("plantas/planta_1360058")

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val nomePlanta = snapshot.child("nome_planta").getValue(String::class.java) ?: "Desconhecido"
                    val umidadeAr = snapshot.child("Umidade_do_ar").getValue(Double::class.java) ?: 0.0
                    val umidadeSolo = snapshot.child("Umidade_do_solo").getValue(Int::class.java) ?: 0
                    val temperatura = snapshot.child("temperatura").getValue(Double::class.java) ?: 0.0
                    val maxTemp = snapshot.child("max_temperatura").getValue(Double::class.java) ?: 0.0
                    val minTemp = snapshot.child("min_temperatura").getValue(Double::class.java) ?: 0.0
                    val maxUmid = snapshot.child("max_umidade").getValue(Int::class.java) ?: 0
                    val minUmid = snapshot.child("min_umidade").getValue(Int::class.java) ?: 0

                    // Exibir os dados na interface
                    binding.textViewNomePlanta.text = nomePlanta
                    binding.textViewUmidadeAr.text = "Umidade do Ar: $umidadeAr"
                    binding.textViewUmidadeSolo.text = "Umidade do Solo: $umidadeSolo"
                    binding.textViewTemperatura.text = "Temperatura: $temperatura"
                    binding.textViewMaxTemperatura.text = "Temperatura Máx: $maxTemp"
                    binding.textViewMinTemperatura.text = "Temperatura Mín: $minTemp"
                    binding.textViewMaxUmidade.text = "Umidade Máx: $maxUmid"
                    binding.textViewMinUmidade.text = "Umidade Mín: $minUmid"

                    // Gerar o QR Code com a URL da planta
                    val url = "https://green-connect-ecg-default-rtdb.firebaseio.com/plantas/planta_1360058.json"
                    gerarQRCode(url)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Tratar erro de leitura do Firebase
            }
        })
    }

}

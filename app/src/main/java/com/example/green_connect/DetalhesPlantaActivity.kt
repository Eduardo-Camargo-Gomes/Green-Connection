package com.example.green_connect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.green_connect.databinding.ActivityDetalhesPlantaBinding

class DetalhesPlantaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalhesPlantaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesPlantaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val planta: Planta? = intent.getParcelableExtra("planta")

        planta?.let {
            binding.textViewNomePlanta.text = it.nome_planta
            binding.textViewUmidadeAr.text = "Umidade do Ar: ${it.Umidade_do_ar}%"
            binding.textViewUmidadeSolo.text = "Umidade do Solo: ${it.Umidade_do_solo}%"
            binding.textViewTemperatura.text = "Temperatura: ${it.temperatura}°C"
        } ?: run {
            binding.textViewNomePlanta.text = "Planta não encontrada"
        }
    }
}

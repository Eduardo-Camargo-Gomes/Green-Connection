package com.example.green_connect

import com.example.green_connect.Planta
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CardViewPlantActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var plantAdapter: PlantAdapter

    // Lista mutável de plantas
    private val plantas = mutableListOf<Planta>(
        Planta(Id_da_planta = 1L, Umidade_do_ar = 45.0f, Umidade_do_solo = 30.0f, max_temperatura = 35f, max_umidade = 60f, min_temperatura = 10f, min_umidade = 20f, nome_planta = "Planta 1", temperatura = 25.0f),
        Planta(Id_da_planta = 2L, Umidade_do_ar = 50.0f, Umidade_do_solo = 40.0f, max_temperatura = 38f, max_umidade = 65f, min_temperatura = 15f, min_umidade = 25f, nome_planta = "Planta 2", temperatura = 27.0f),
        Planta(Id_da_planta = 3L, Umidade_do_ar = 55.0f, Umidade_do_solo = 50.0f, max_temperatura = 40f, max_umidade = 70f, min_temperatura = 20f, min_umidade = 30f, nome_planta = "Planta 3", temperatura = 30.0f)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.card_view_plant) // Verifique se este layout está correto

        recyclerView = findViewById(R.id.recyclerViewPlants) // Certifique-se de que o RecyclerView está definido no seu layout
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Definindo o que acontece quando uma planta é clicada
        plantAdapter = PlantAdapter(plantas) { planta ->
            // Aqui você pode implementar o que acontece ao clicar em um CardView
            Toast.makeText(this, "Clique em: ${planta.nome_planta}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = plantAdapter
    }
}

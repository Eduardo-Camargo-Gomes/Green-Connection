package com.example.green_connect

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Tela_Pesquisar : AppCompatActivity() {

    private lateinit var adapter: PlantAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var pesquisaEntradaEditText: TextInputEditText
    private lateinit var buttonFazerBusca: Button
    private val todasPlantas = mutableListOf<Planta>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_pesquisar)

        recyclerView = findViewById(R.id.recyclerView)
        pesquisaEntradaEditText = findViewById(R.id.pesquisaEntradaEditText)
        buttonFazerBusca = findViewById(R.id.buttonFazerBusca)

        adapter = PlantAdapter(mutableListOf()) { planta ->
            // Ação ao clicar na planta, exibir detalhes
            val intent = Intent(this, PlantDetailsActivity::class.java).apply {
                putExtra("plantaId", planta.Id_da_planta) // Supondo que 'id_da_planta' seja o ID da planta
            }
            startActivity(intent)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        configurarBotoes()
        carregarPlantas()

        pesquisaEntradaEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                filtrarPlantas(query)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        buttonFazerBusca.setOnClickListener {
            val query = pesquisaEntradaEditText.text.toString()
            filtrarPlantas(query)
        }
    }

    private fun carregarPlantas() {
        val database = FirebaseDatabase.getInstance().getReference("plantas")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                todasPlantas.clear() // Clear previous data
                for (snapshot in dataSnapshot.children) {
                    val planta = snapshot.getValue(Planta::class.java)
                    planta?.let { todasPlantas.add(it) }
                }
                adapter.atualizarLista(todasPlantas)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Tela_Pesquisar", "Erro ao carregar plantas: ${error.message}")
            }
        })
    }

    private fun filtrarPlantas(query: String) {
        val plantasFiltradas = todasPlantas.filter { planta ->
            planta.nome_planta.contains(query, ignoreCase = true)
        }
        adapter.atualizarLista(plantasFiltradas)
    }

    private fun configurarBotoes() {
        val buttonMenu = findViewById<ImageButton>(R.id.imageButton)
        val buttonListar = findViewById<ImageButton>(R.id.buttonListar)

        buttonMenu.setOnClickListener {
            val intent = Intent(this, Tela_Menu::class.java)
            startActivity(intent)
        }

        buttonListar.setOnClickListener {
            val intent = Intent(this, Tela_configuracao::class.java)
            startActivity(intent)
        }
    }
}

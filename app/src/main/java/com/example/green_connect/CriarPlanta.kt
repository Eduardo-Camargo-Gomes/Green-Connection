package com.example.green_connect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.green_connect.databinding.ActivityCriarPlantaBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CriarPlanta : AppCompatActivity() {

    private lateinit var binding: ActivityCriarPlantaBinding
    private lateinit var database: DatabaseReference
    private val plantId = "1360058" // ID fixo da planta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCriarPlantaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("CriarPlanta", "Sempre editando a planta com ID: $plantId")

        // Inicializa a referência do banco de dados para a planta 1360058
        database = FirebaseDatabase.getInstance("https://green-connect-ecg-default-rtdb.firebaseio.com/")
            .getReference("plantas/planta_$plantId")

        carregarDadosDaPlanta()

        // Configura o botão de salvar
        binding.butoonCriarNoMenu.setOnClickListener {
            salvarDadosDaPlanta()
        }
    }

    private fun carregarDadosDaPlanta() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val idDaPlanta = snapshot.child("Id_da_planta").getValue(Long::class.java) ?: 0L
                    val umidadeDoAr = snapshot.child("Umidade_do_ar").getValue(Float::class.java) ?: 0.0F
                    val umidadeDoSolo = snapshot.child("Umidade_do_solo").getValue(Int::class.java) ?: 0
                    val maxTemperatura = snapshot.child("max_temperatura").getValue(Int::class.java) ?: 30
                    val maxUmidade = snapshot.child("max_umidade").getValue(Int::class.java) ?: 50
                    val minTemperatura = snapshot.child("min_temperatura").getValue(Int::class.java) ?: 20
                    val minUmidade = snapshot.child("min_umidade").getValue(Int::class.java) ?: 0
                    val nomePlanta = snapshot.child("nome_planta").getValue(String::class.java) ?: ""
                    val temperatura = snapshot.child("temperatura").getValue(Float::class.java) ?: 0.0F

                    binding.nomePlanta.setText(nomePlanta)
                    binding.spinnerTempMin.setSelection(getIndex(binding.spinnerTempMin, minTemperatura))
                    binding.spinnerTempMax.setSelection(getIndex(binding.spinnerTempMax, maxTemperatura))
                    binding.spinnerUmidMin.setSelection(getIndex(binding.spinnerUmidMin, minUmidade))
                    binding.spinnerUmidMax.setSelection(getIndex(binding.spinnerUmidMax, maxUmidade))
                } else {
                    Toast.makeText(this@CriarPlanta, "Dados da planta não encontrados.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("CriarPlanta", "Erro ao carregar dados: ${error.message}")
            }
        })
    }

    private fun salvarDadosDaPlanta() {
        val nomePlanta = binding.nomePlanta.text.toString()

        // Verifica se o nome da planta não está vazio
        if (nomePlanta.isEmpty()) {
            Toast.makeText(this, "O nome da planta não pode estar vazio.", Toast.LENGTH_SHORT).show()
            return
        }

        val minTemp = binding.spinnerTempMin.selectedItem?.toString()?.replace("C°", "")?.toIntOrNull() ?: 10
        val maxTemp = binding.spinnerTempMax.selectedItem?.toString()?.replace("C°", "")?.toIntOrNull() ?: 35
        val minUmid = binding.spinnerUmidMin.selectedItem?.toString()?.replace("%", "")?.toIntOrNull() ?: 750
        val maxUmid = binding.spinnerUmidMax.selectedItem?.toString()?.replace("%", "")?.toIntOrNull() ?: 900

        // Atualiza os dados no Firebase
        val plantaAtualizada = mapOf(
            "nome_planta" to nomePlanta,
            "min_temperatura" to minTemp,
            "max_temperatura" to maxTemp,
            "min_umidade" to minUmid,
            "max_umidade" to maxUmid
        )

        database.updateChildren(plantaAtualizada).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show()
                Log.d("CriarPlanta", "Dados atualizados com sucesso: $plantaAtualizada")

                // Atualiza a lista de CardViews
                atualizarListaCardViews()

                // Retorna para a Tela_Menu após a atualização
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(this, "Falha ao atualizar os dados: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun atualizarListaCardViews() {
        // Aqui você deve implementar a lógica para atualizar a lista de CardViews na tela principal.
        // Pode ser através de uma Intent ou uma chamada de função se você estiver usando um padrão de arquitetura como MVVM ou MVP.
        // Exemplo:
        val intent = Intent(this, Tela_Menu::class.java)
        intent.putExtra("atualizar_lista", true) // Passa um sinalizador para atualizar a lista
        startActivity(intent)
    }

    private fun getIndex(spinner: Spinner, value: Int): Int {
        for (i in 0 until spinner.adapter.count) {
            val item = spinner.getItemAtPosition(i).toString().replace("C°", "").trim()
            val itemValue = item.toIntOrNull()
            if (itemValue != null && itemValue == value) {
                return i
            }
        }
        return 0
    }
}

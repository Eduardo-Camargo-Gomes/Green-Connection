package com.example.green_connect

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.green_connect.databinding.ActivityTelaPesquisarBinding
import com.google.firebase.database.*

class Tela_Pesquisar : AppCompatActivity() {

    private lateinit var binding: ActivityTelaPesquisarBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaPesquisarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa o Firebase Database
        database = FirebaseDatabase.getInstance().reference.child("plantas")

        // Configura o clique do botão de busca
        binding.buttonFazerBusca.setOnClickListener {
            val procurarText = binding.pesquisaEntradaEditText.text.toString().trim()

            if (procurarText.isNotEmpty()) {
                try {
                    val searchId = procurarText.toInt()
                    pesquisarplantasporUserId(searchId)
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "ID de usuário inválido", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Digite um ID de usuário válido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun pesquisarplantasporUserId(userId: Int) {
        val Consulta = database.orderByChild("id_user").equalTo(userId.toDouble())

        Consulta.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                binding.pesquisaEntradaEditText.text?.clear()
                // Exibe as plantas encontradas
                for (snapshot in dataSnapshot.children) {
                    val planta = snapshot.getValue(Planta::class.java)
                    if (planta != null) {
                        // Aqui você pode manipular a planta encontrada
                        // Exemplo de exibição:
                        // exibirPlanta(planta)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@Tela_Pesquisar, "Erro ao buscar plantas: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

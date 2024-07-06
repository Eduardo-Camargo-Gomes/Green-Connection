package com.example.green_connect

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.green_connect.databinding.ActivityTelaCriarPlantaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Tela_Criar_Planta : AppCompatActivity() {

    // Variável para vinculação de layout
    private lateinit var binding: ActivityTelaCriarPlantaBinding

    // Autenticação do Firebase
    private lateinit var auth: FirebaseAuth

    // Database do Firebase
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaCriarPlantaBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Inicializa Firebase Auth e Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Configura o clique do botão salvar
        binding.buttonSalvar.setOnClickListener {
            salvarPlanta()
        }

        // Ajusta o padding da view principal para levar em conta as inserções do sistema
        // (como barras de status e navegação)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun salvarPlanta() {
        val nome = binding.NomePlanta.text.toString()
        val umidadeMaxima = binding.editTextUmidadeMaxima.text.toString().toIntOrNull()
        val umidadeMinima = binding.editTextUmidadeMinima.text.toString().toIntOrNull()
        val temperaturaMaxima = binding.editTextTemperaturaMaxima.text.toString().toIntOrNull()
        val temperaturaMinima = binding.editTextTemperaturaMinima.text.toString().toIntOrNull()
        val quantidadeAgua = binding.editTextQuantidadeAgua.text.toString().toIntOrNull()

        if (nome.isNotBlank() && umidadeMaxima != null && umidadeMinima != null &&
            temperaturaMaxima != null && temperaturaMinima != null &&
            quantidadeAgua != null) {
            val planta = Planta(
                nome,
                umidadeMaxima,
                umidadeMinima,
                temperaturaMaxima,
                temperaturaMinima,
                quantidadeAgua
            )

            // Salva a planta no Realtime Database
            val userId = auth.currentUser?.uid
            if (userId != null) {
                database.reference.child("users").child(userId).child("plantas").push().setValue(planta)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Planta salva com sucesso!", Toast.LENGTH_SHORT).show()
                            // Redireciona para o menu após o sucesso
                            val intent = Intent(this, Tela_Menu::class.java)
                            startActivity(intent)
                            finish() // Opcional: Fecha a tela atual para que o usuário não possa voltar
                        } else {
                            Toast.makeText(this, "Erro ao salvar planta.", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Por favor, insira valores válidos.", Toast.LENGTH_SHORT).show()
        }
    }
}

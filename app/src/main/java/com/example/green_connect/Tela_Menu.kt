package com.example.green_connect

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.green_connect.databinding.ActivityTelaMenuBinding
import com.google.firebase.auth.FirebaseAuth

class Tela_Menu : AppCompatActivity() {

    // Variável para vinculação de layout
    private lateinit var binding: ActivityTelaMenuBinding

    // Autenticação do Firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Infla o layout usando View Binding
        binding = ActivityTelaMenuBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Inicializa a autenticação do Firebase
        auth = FirebaseAuth.getInstance()

        // Configura o clique do botão "Sair"
        binding.buttonSair.setOnClickListener {
            // Realiza o logout do usuário
            auth.signOut()
            // Redireciona para a tela inicial
            val intent = Intent(this, Tela_inicial::class.java)
            startActivity(intent)
            finish()
        }

        // Ajusta o padding da view principal para levar em conta as inserções do sistema (como barras de status e navegação)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

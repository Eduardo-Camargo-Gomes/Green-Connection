package com.example.green_connect

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.green_connect.databinding.ActivityTelaConfiguracaoBinding
import com.google.firebase.auth.FirebaseAuth

class Tela_configuracao : AppCompatActivity() {

    // Variável para vinculação de layout
    private lateinit var binding: ActivityTelaConfiguracaoBinding

    // Autenticação do Firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTelaConfiguracaoBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)

        // Inicializa a autenticação do Firebase
        auth = FirebaseAuth.getInstance()

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configura o clique do botão "Sair"
        binding.buttonSair.setOnClickListener{
            // Realiza o logout do usuário
            auth.signOut()
            val intent = Intent(this, Tela_inicial::class.java)
            startActivity(intent)
            finish()
        }  
    }
}

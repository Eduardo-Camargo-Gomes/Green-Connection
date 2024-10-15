package com.example.green_connect

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import com.example.green_connect.databinding.ActivityTelaInicialBinding // Importa a classe de binding

class Tela_inicial : AppCompatActivity() {

    private lateinit var binding: ActivityTelaInicialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ativa o modo de tela cheia
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        // Desativar o modo escuro
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityTelaInicialBinding.inflate(layoutInflater) // Inflar o layout
        setContentView(binding.root)

        binding.ButtonCadastar.setOnClickListener {
            val intent = Intent(this, Tela_Cadastro::class.java)
            startActivity(intent)
        }

        binding.buttonLogar.setOnClickListener {
            val intent = Intent(this, TelaLogar::class.java)
            startActivity(intent)
        }
    }
}

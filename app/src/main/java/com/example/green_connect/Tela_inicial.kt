package com.example.green_connect

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Tela_inicial : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_tela_inicial)

        val buttonCadastrar = findViewById<Button>(R.id.ButtonCadastar)
        buttonCadastrar.setOnClickListener {
            val intent = Intent(this, Tela_Cadastro::class.java)
            startActivity(intent)
        }

        val buttonLogar = findViewById<Button>(R.id.buttonLogar)
        buttonLogar.setOnClickListener {
            val intent = Intent(this, Tela_logar::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

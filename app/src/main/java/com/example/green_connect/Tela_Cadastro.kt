package com.example.green_connect

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.get
import com.example.green_connect.databinding.ActivityTelaCadastroBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient


class Tela_Cadastro : AppCompatActivity() {

    private lateinit var binding: ActivityTelaCadastroBinding
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaCadastroBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)

        //editar o valor de texto do botão do google
        val text_botao_cadastrar_google = binding.buttonGOOGLE.getChildAt(0)as TextView
        text_botao_cadastrar_google.text = "Google"

        binding.ButtonCadastar.setOnClickListener{

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            fun onStart() {
                super.onStart()
                // Check if user is signed in (non-null) and update UI accordingly.
            }
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
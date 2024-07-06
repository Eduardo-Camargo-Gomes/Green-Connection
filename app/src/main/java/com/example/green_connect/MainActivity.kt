package com.example.green_connect

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    // Variável para autenticação do Firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        // Configura a tela para exibição em tela cheia
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Define o layout da atividade
        setContentView(R.layout.activity_main)

        // Inicializa o FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Adiciona um atraso de 3 segundos antes de verificar o usuário atual
        Handler(Looper.getMainLooper()).postDelayed({
            checkCurrentUser()  // Verificar o usuário após o delay
        }, 3000)

        // Ajusta o padding da view principal para levar em conta as inserções do sistema
        // (como barras de status e navegação)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
    }

    /**
     * Verifica se há um usuário autenticado no Firebase.
     * Se houver, abre a tela principal (menu).
     * Caso contrário, redireciona para a tela introdutória.
     */
    private fun checkCurrentUser() {
        val currentUser = auth.currentUser
        if (currentUser != null && !TextUtils.isEmpty(currentUser.email)) {
            // Usuário está logado, mostra uma mensagem de boas-vindas e abre o menu
            Toast.makeText(baseContext, getString(R.string.user_logged_in, currentUser.email), Toast.LENGTH_SHORT).show()
            abreMenu()
        } else {
            // Usuário não está logado, redireciona para a tela introdutória
            val intent = Intent(this, Tela_Introdutoria1::class.java)
            startActivity(intent)
            finish()
        }
    }

    /**
     * Método para abrir a tela principal após login bem-sucedido
     */
    private fun abreMenu() {
        val intent = Intent(this, Tela_Menu::class.java)
        startActivity(intent)
        finish()
    }
}

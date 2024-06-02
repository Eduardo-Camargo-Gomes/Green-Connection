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
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()  // Inicializar o FirebaseAuth

        Handler(Looper.getMainLooper()).postDelayed({
            checkCurrentUser()  // Verificar o usuário após o delay
        }, 3000)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()

    }

    private fun checkCurrentUser() {
        val currentUser = auth.currentUser
        if (currentUser != null && !TextUtils.isEmpty(currentUser.email)) {
            Toast.makeText(baseContext, getString(R.string.user_logged_in, currentUser.email), Toast.LENGTH_SHORT).show()
            abreMenu()
        } else {
            val intent = Intent(this, Tela_Introdutoria1::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Método para abrir a tela principal após login bem-sucedido
    private fun abreMenu() {
        val intent = Intent(this, Tela_Menu::class.java)
        startActivity(intent)
        finish()
    }
}

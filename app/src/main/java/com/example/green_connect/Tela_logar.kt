package com.example.green_connect

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.green_connect.databinding.ActivityTelaLogarBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth

class Tela_logar : AppCompatActivity() {

    private lateinit var binding: ActivityTelaLogarBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaLogarBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)

        auth = Firebase.auth

        // Editar o valor de texto do botão do Google
        val textBotaoCadastrarGoogle = binding.buttonGOOGLE.getChildAt(0) as TextView
        textBotaoCadastrarGoogle.text = "Google"

        if(binding.emialUsuario.text.isNullOrEmpty()){
            Toast.makeText(baseContext, "Digite um email cadastrado", Toast.LENGTH_SHORT).show()
        }else if(binding.editTextTextPassword.text.isNullOrEmpty()){
            Toast.makeText(baseContext, "Digite uma senha", Toast.LENGTH_SHORT).show()
        }else{
            loginUsuarioSenha(binding.emialUsuario.text.toString(), binding.editTextTextPassword.text.toString())
        }

        loginUsuarioSenha(binding.emialUsuario.text.toString(), binding.editTextTextPassword.text.toString())

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun loginUsuarioSenha(usuario: String, senha: String) {
        binding.ButtonLogar.setOnClickListener {
            auth.signInWithEmailAndPassword(
                usuario, senha
            ).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    abreMenu()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Erro de autenticação!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun abreMenu() {
        Toast.makeText(baseContext, "Autenticação Efetuada", Toast.LENGTH_SHORT).show()
        binding.emialUsuario.text.clear()
        binding.editTextTextPassword.text.clear()
        val intent = Intent(this, Tela_Menu::class.java)
        startActivity(intent)
        finish()
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        //val currentUser = auth.currentUser
        //updateUI(currentUser)
    }
}

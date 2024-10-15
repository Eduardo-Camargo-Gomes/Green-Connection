package com.example.green_connect

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.green_connect.databinding.ActivityTelaConfiguracaoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Tela_configuracao : AppCompatActivity() {

    // Variável para vinculação de layout
    private lateinit var binding: ActivityTelaConfiguracaoBinding

    // Autenticação do Firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Infla o layout usando View Binding
        binding = ActivityTelaConfiguracaoBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)

        // Inicializa a autenticação do Firebase
        auth = FirebaseAuth.getInstance()

        // Obter o usuário atual
        val user: FirebaseUser? = auth.currentUser

        // Exibir o nome e o e-mail do usuário no layout
        if (user != null) {
            // Exibe o email do usuário
            binding.textEmail.text = user.email

            // Exibe o nome do usuário (se estiver disponível, pode ser nulo)
            binding.textUsername.text = user.displayName ?: "Usuário"
        }

        // Ajuste para incluir o preenchimento com as barras de sistema (como a barra de status)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configura o clique do botão "Desconectar" com confirmação
        binding.buttonLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    // Função para exibir a caixa de diálogo de confirmação
    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desconectar")
        builder.setMessage("Você tem certeza de que deseja sair da conta?")
        builder.setPositiveButton("Sim") { dialog, which ->
            // Realiza o logout do usuário
            auth.signOut()
            val intent = Intent(this, Tela_inicial::class.java)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton("Não") { dialog, which ->
            // Apenas fecha o diálogo
            dialog.dismiss()
        }

        // Exibe o diálogo
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}

package com.example.green_connect

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.example.green_connect.databinding.ActivityTelaCadastroBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth

class Tela_Cadastro : AppCompatActivity() {

    // Variável para vinculação de layout
    private lateinit var binding: ActivityTelaCadastroBinding
    // Cliente de login do Google
    private lateinit var googleSignInClient: GoogleSignInClient
    // Autenticação do Firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Infla o layout usando View Binding
        binding = ActivityTelaCadastroBinding.inflate(layoutInflater)
        val view = binding.root
        enableEdgeToEdge()
        setContentView(view)

        // Inicializa a autenticação do Firebase
        auth = Firebase.auth

        // Configura opções de login do Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("96728314722-2hktnd64dc6afd0djugoba4aicmcsqv3.apps.googleusercontent.com")
            .requestEmail()
            .build()

        // Cria cliente de login do Google
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Edita o texto do botão de cadastro com o Google
        val textBotaoCadastrarGoogle = binding.buttonGOOGLE.getChildAt(0) as TextView
        textBotaoCadastrarGoogle.text = "Google"

        // Configura o clique do botão de cadastro com Google
        binding.buttonGOOGLE.setOnClickListener {
            cliqueBotaoGoogle()
        }

        // Configura o clique do botão de cadastro
        binding.ButtonCadastar.setOnClickListener {
            if (TextUtils.isEmpty(binding.emailUsuario.text)) {
                binding.emailUsuario.error = "Informe o usuário para cadastro"
            } else if (TextUtils.isEmpty(binding.editTextTextPassword.text)) {
                binding.editTextTextPassword.error = "Informe a senha para cadastro"
            } else {
                criarUsuarioSenha(binding.emailUsuario.text.toString(),
                    binding.editTextTextPassword.text.toString())
            }
        }

        // Ajusta o padding da view principal para levar em conta as inserções do sistema (como barras de status e navegação)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            v.updatePadding(
                top = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                bottom = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            )
            insets
        }
    }

    // Método para iniciar o fluxo de login com Google
    private fun cliqueBotaoGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        abreActivity.launch(signInIntent)
    }

    // Callback para receber o resultado da atividade de login do Google
    private val abreActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            try {
                val conta = task.getResult(ApiException::class.java)
                loginComGoogle(conta.idToken)
            } catch (exception: ApiException) {
                Toast.makeText(baseContext, getString(R.string.google_auth_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Método para criar usuário
    private fun criarUsuarioSenha( email:String, senha:String){
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this){
            taks ->
            if (taks.isSuccessful){
                Toast.makeText(baseContext, "Usuário criado com sucesso", Toast.LENGTH_SHORT).show()
                limpaCampos()
                abreMenu()
            }else{
                Toast.makeText(baseContext, "Erro na criação do usuário", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Método para autenticar com Firebase usando o token do Google
    private fun loginComGoogle(token: String?) {
        if (token != null) {
            val credential = GoogleAuthProvider.getCredential(token, null)
            auth.signInWithCredential(credential).addOnCompleteListener(this) { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Autenticação efetuada com o Google", Toast.LENGTH_SHORT).show()
                    abreMenu()
                } else {
                    Toast.makeText(baseContext, "Erro de autenticação com o Google", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Método para abrir a tela principal após login bem-sucedido
    private fun abreMenu() {
        val intent = Intent(this, Tela_Menu::class.java)
        startActivity(intent)
        finish()
    }

    private fun limpaCampos() {
        binding.emailUsuario.text.clear()
        binding.editTextTextPassword.text.clear()
    }

    // Método para verificar se já há um usuário logado ao iniciar a atividade
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null && !TextUtils.isEmpty(currentUser.email)) {
            Toast.makeText(baseContext, getString(R.string.user_logged_in, currentUser.email), Toast.LENGTH_SHORT).show()
            abreMenu()
        }

        // Dentro da classe Tela_Cadastro
        binding.button.setOnClickListener {
            val intent = Intent(this, TelaLogar::class.java)
            startActivity(intent)
        }
    }
}

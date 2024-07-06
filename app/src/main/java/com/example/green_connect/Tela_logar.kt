package com.example.green_connect

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.example.green_connect.databinding.ActivityTelaLogarBinding
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

class TelaLogar : AppCompatActivity() {
    // Variáveis para binding, cliente de login do Google e autenticação do Firebase
    private lateinit var binding: ActivityTelaLogarBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaLogarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()  // Habilitar borda a borda para UI

        auth = Firebase.auth  // Inicializar autenticação do Firebase

        // Configurar opções de login do Google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("96728314722-2hktnd64dc6afd0djugoba4aicmcsqv3.apps.googleusercontent.com")
            .requestEmail()
            .build()

        // Criar cliente de login do Google
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Modificar o texto do botão do Google
        val textBotaoCadastrarGoogle = binding.buttonGOOGLE.getChildAt(0) as TextView
        textBotaoCadastrarGoogle.text = getString(R.string.google_sign_in_button)

        // Configurar ação do botão de login com email e senha
        binding.ButtonLogar.setOnClickListener {
            val email = binding.emailUsuario.text.toString()
            val senha = binding.editTextTextPassword.text.toString()

            if (TextUtils.isEmpty(email)) {
                binding.emailUsuario.error = getString(R.string.email_empty_error)
            } else if (TextUtils.isEmpty(senha)) {
                binding.editTextTextPassword.error = getString(R.string.password_empty_error)
            } else {
                loginUsuarioSenha(email, senha)
            }
        }

        // Configurar ação do botão de login com Google
        binding.buttonGOOGLE.setOnClickListener {
            cliqueBotaoGoogle()
        }

        // Ajustar padding da view principal para lidar com as barras do sistema
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

    // Método para autenticar com Firebase usando o token do Google
    private fun loginComGoogle(token: String?) {
        if (token != null) {
            val credential = GoogleAuthProvider.getCredential(token, null)
            auth.signInWithCredential(credential).addOnCompleteListener(this)
            { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Autenticação efetuada com o Google", Toast.LENGTH_SHORT).show()
                    abreMenu()
                } else {
                    Toast.makeText(baseContext, "Erro de autenticação com o Google", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Método para autenticar com Firebase usando email e senha
    private fun loginUsuarioSenha(email: String, senha: String) {
        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(baseContext, getString(R.string.auth_success), Toast.LENGTH_SHORT).show()
                abreMenu()
            } else {
                Toast.makeText(baseContext, getString(R.string.auth_error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Método para abrir a tela principal após login bem-sucedido
    private fun abreMenu() {
        limpaCampos()
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
    }
}

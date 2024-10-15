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

    private lateinit var binding: ActivityTelaLogarBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaLogarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        auth = Firebase.auth
        configurarGoogleSignIn()
        configurarBotoes()
        aplicarInsetsDeTela()
    }

    // Configuração do Google Sign-In
    private fun configurarGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("96728314722-2hktnd64dc6afd0djugoba4aicmcsqv3.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Modificar o texto do botão de login com Google
        val textBotaoCadastrarGoogle = binding.buttonGOOGLE.getChildAt(0) as TextView
        textBotaoCadastrarGoogle.text = getString(R.string.google_sign_in_button)
    }

    // Configuração dos botões de login
    private fun configurarBotoes() {
        binding.ButtonLogar.setOnClickListener {
            val email = binding.emailUsuario.text.toString()
            val senha = binding.editTextTextPassword.text.toString()

            if (camposValidos(email, senha)) {
                loginUsuarioSenha(email, senha)
            }
        }

        binding.buttonGOOGLE.setOnClickListener {
            cliqueBotaoGoogle()
        }
    }

    // Verificação de campos de email e senha
    private fun camposValidos(email: String, senha: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            binding.emailUsuario.error = getString(R.string.email_empty_error)
            return false
        }
        if (TextUtils.isEmpty(senha)) {
            binding.editTextTextPassword.error = getString(R.string.password_empty_error)
            return false
        }
        return true
    }

    // Aplicar padding para bordas do sistema
    private fun aplicarInsetsDeTela() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            v.updatePadding(
                top = insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                bottom = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            )
            insets
        }
    }

    // Iniciar o fluxo de login com Google
    private fun cliqueBotaoGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        abreActivity.launch(signInIntent)
    }

    // Callback para resultado do login com Google
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

    // Login com Google
    private fun loginComGoogle(token: String?) {
        token?.let {
            val credential = GoogleAuthProvider.getCredential(it, null)
            auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, getString(R.string.google_auth_success), Toast.LENGTH_SHORT).show()
                    abreMenu()
                } else {
                    exibirErro(task)
                }
            }
        }
    }

    // Login com Email e Senha
    private fun loginUsuarioSenha(email: String, senha: String) {
        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(baseContext, getString(R.string.auth_success), Toast.LENGTH_SHORT).show()
                abreMenu()
            } else {
                exibirErro(task)
            }
        }
    }

    // Método para abrir o menu principal após login
    private fun abreMenu() {
        limpaCampos()
        startActivity(Intent(this, Tela_Menu::class.java))
        finish()
    }

    // Limpar campos após login
    private fun limpaCampos() {
        binding.emailUsuario.text.clear()
        binding.editTextTextPassword.text.clear()
    }

    // Exibir mensagem de erro
    private fun exibirErro(task: Task<AuthResult>) {
        task.exception?.message?.let {
            Toast.makeText(baseContext, "Erro: $it", Toast.LENGTH_LONG).show()
        }
    }

    // Verificar se há usuário logado ao iniciar
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null && !TextUtils.isEmpty(currentUser.email)) {
            Toast.makeText(baseContext, getString(R.string.user_logged_in, currentUser.email), Toast.LENGTH_SHORT).show()
            abreMenu()
        }
    }
}
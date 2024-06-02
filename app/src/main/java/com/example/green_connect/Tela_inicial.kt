package com.example.green_connect

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

class Tela_inicial : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Habilita a exibição de borda a borda
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContentView(R.layout.activity_tela_inicial)

        // Lida com o clique do botão de cadastro
        val buttonCadastrar = findViewById<Button>(R.id.ButtonCadastar)
        buttonCadastrar.setOnClickListener {
            val intent = Intent(this, Tela_Cadastro::class.java)
            startActivity(intent)
        }

        // Lida com o clique do botão de login
        val buttonLogar = findViewById<Button>(R.id.buttonLogar)
        buttonLogar.setOnClickListener {
            val intent = Intent(this, TelaLogar::class.java)
            startActivity(intent)
        }

        // Drawable para os indicadores de página
        val VerdeEscuroDrawable = ContextCompat.getDrawable(baseContext, R.drawable.backgrund_button)
        val BrancoDrawable = ContextCompat.getDrawable(baseContext, R.drawable.drawable_bottonsemfundo)

        // Ajusta o preenchimento da visualização principal para levar em conta as inserções do sistema
        val mainView = findViewById<View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainView) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                left = systemBarsInsets.left,
                top = systemBarsInsets.top,
                right = systemBarsInsets.right,
                bottom = systemBarsInsets.bottom
            )
            insets
        }
    }
}

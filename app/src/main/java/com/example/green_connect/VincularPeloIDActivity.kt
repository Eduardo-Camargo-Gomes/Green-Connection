package com.example.green_connect


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class VincularPeloIDActivity : AppCompatActivity() {
    private lateinit var editTextId: EditText
    private lateinit var buttonConfirmar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vincular_pelo_idactivity)

        editTextId = findViewById(R.id.editTextId)
        buttonConfirmar = findViewById(R.id.buttonConfirmar)

        buttonConfirmar.setOnClickListener {
            val plantId = editTextId.text.toString().trim()
            if (plantId.isNotEmpty()) {
                // Redireciona para a tela de detalhes da planta
                val intent = Intent(this, PlantDetailsActivity::class.java)
                intent.putExtra("PLANT_ID", plantId)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Por favor, insira um ID válido", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

package com.example.green_connect

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.green_connect.databinding.ActivityTelaMenuBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class Tela_Menu : AppCompatActivity() {

    // Variável para vinculação de layout
    private lateinit var binding: ActivityTelaMenuBinding

    // Autenticação do Firebase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaMenuBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Configura o TabLayout e ViewPager
        configTabLayout()

        // Inicializa a autenticação do Firebase
        auth = FirebaseAuth.getInstance()

        // Lida com o clique do buttonBuscar
        binding.buttonListar.setOnClickListener {
            // Inicia a tela de cadastro
            val intent = Intent(this, Tela_configuracao::class.java)
            startActivity(intent)
        }

        //lida com o clique do button
        binding.buttonBuscar.setOnClickListener {
            val intent = Intent(this, Tela_Pesquisar::class.java)
            startActivity(intent)
        }

        //lida com o clique do button
        binding.buttonCreatePlants.setOnClickListener{
            //inicai a tela de criar planta
            val intent = Intent(this, Tela_Criar_Planta::class.java)
            startActivity(intent)
        }


        // Ajusta o padding da view principal para levar em conta as inserções do sistema
        // (como barras de status e navegação)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     * Configura o TabLayout e ViewPager com os fragmentos correspondentes
     */
    private fun configTabLayout() {
        val adapter = ViewPagerAdapter(this)
        adapter.addFragment(PlantFragment(), "Plantas")
        //adapter.addFragment(PlatesFragment(), "Placas")

        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = adapter.itemCount

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = adapter.getTitle(position)
        }.attach()
    }
}

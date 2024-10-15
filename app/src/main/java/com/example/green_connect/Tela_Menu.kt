package com.example.green_connect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.green_connect.databinding.ActivityTelaMenuBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Tela_Menu : AppCompatActivity() {
    private lateinit var binding: ActivityTelaMenuBinding
    private lateinit var plantAdapter: PlantAdapter
    private lateinit var plantas: MutableList<Planta> // Inicializa a lista de plantas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializando a lista de plantas
        plantas = mutableListOf()

        configurarRecyclerView()
        configurarBotoes()
    }

    private fun configurarBotoes() {
        // Botão de Criar Planta
        binding.buttonCreatePlants.setOnClickListener {
            mostrarDialogOpcoes()
        }

        // Botão de Buscar
        binding.buttonBuscar.setOnClickListener {
            val intent = Intent(this, Tela_Pesquisar::class.java)
            startActivity(intent)
        }

        // Botão de Listar
        binding.buttonListar.setOnClickListener {
            val intent = Intent(this, Tela_configuracao::class.java)
            startActivity(intent)
        }

        // Botão de Tempo
        binding.imageButton.setOnClickListener {
            atualizarListaCardViews()
        }
    }

    private fun configurarRecyclerView() {
        val recyclerView = binding.recyclerViewPlantas
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Passando a função de clique para o adaptador
        plantAdapter = PlantAdapter(plantas) { planta ->
            // Ação ao clicar em uma planta
            // Aqui você pode fazer o que quiser ao clicar em um item da planta
            Toast.makeText(this, "Clique em: ${planta.nome_planta}", Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = plantAdapter
        recyclerView.visibility = View.GONE // Inicia invisível
    }


    private fun mostrarDialogOpcoes() {
        val options = arrayOf("Escanear QR Code")// "Vincular pelo ID", "Gerar QR Code", "Criar Planta")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Escolha uma opção")
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> {
                    val intent = Intent(this, QRCodeScannerActivity::class.java)
                    startActivity(intent)
                }
                /*
                1 -> {
                    val intent = Intent(this, VincularPeloIDActivity::class.java)
                    startActivity(intent)
                }
                2 -> {// Inicia a activity de CriarPlanta para edição
                    val intent = Intent(this, CriarPlanta::class.java)
                    startActivityForResult(intent, REQUEST_CODE_EDIT_PLANTA)

                    //gerarQRCode()
                }
                3 -> {
                    // Inicia a activity de CriarPlanta para edição
                    val intent = Intent(this, CriarPlanta::class.java)
                    startActivityForResult(intent, REQUEST_CODE_EDIT_PLANTA)
               }
                 */
            }
        }
        builder.show()
    }

    // Função para lidar com o resultado da atividade CriarPlanta
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_EDIT_PLANTA && resultCode == RESULT_OK) {
            data?.let {
                val nomePlanta = it.getStringExtra("nome_planta") ?: return
                val minTemp = it.getIntExtra("min_temperatura", 0)
                val maxTemp = it.getIntExtra("max_temperatura", 0)
                val minUmid = it.getIntExtra("min_umidade", 0)
                val maxUmid = it.getIntExtra("max_umidade", 0)

                // Cria uma nova planta com os dados recebidos
                val plantaAtualizada = Planta(
                    Id_da_planta = System.currentTimeMillis(),
                    Umidade_do_ar = minUmid.toFloat(),
                    Umidade_do_solo = maxUmid.toFloat(),
                    max_temperatura = maxTemp.toFloat(),
                    min_temperatura = minTemp.toFloat(),
                    max_umidade = 90f,
                    min_umidade = 30f,
                    nome_planta = nomePlanta,
                    temperatura = 28f
                )


                // Adiciona a nova planta à lista e atualiza o RecyclerView
                adicionarNovaPlanta(plantaAtualizada)
                atualizarListaCardViews()
            }
        }
    }

    private fun adicionarNovaPlanta(planta: Planta) {
        // Adiciona a nova planta à lista
        plantas.add(planta)

        // Notifica o adaptador sobre a inserção do novo item
        plantAdapter.notifyItemInserted(plantas.size - 1)

        // Opcionalmente, rolar para o final da lista para mostrar a nova planta
        binding.recyclerViewPlantas.scrollToPosition(plantas.size - 1)
    }

    private fun gerarQRCode() {
        // Aqui você deve ter o ID da planta disponível
        val plantId = "1360058"
        val url = "https://green-connect-ecg-default-rtdb.firebaseio.com/plantas/planta_$plantId.json"

        if (url.isNotEmpty()) {
            val bitmapQRCode = QRCodeUtil.gerarQRCode(url)
            if (bitmapQRCode != null) {
                val intent = Intent(this, QRCodeDisplayActivity::class.java)
                intent.putExtra("QR_CODE_BITMAP", bitmapQRCode)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Erro ao gerar QR Code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun atualizarListaCardViews() {
        // Limpa a lista existente
        plantas.clear()

        val database = FirebaseDatabase.getInstance()
        val plantaRef = database.getReference("plantas")

        plantaRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val planta = snapshot.getValue(Planta::class.java)
                    planta?.let {
                        plantas.add(it)
                        Log.d("Tela_Menu", "Adicionando planta: ${it.nome_planta}")
                    }
                }

                Log.d("Tela_Menu", "Total de plantas: ${plantas.size}")
                plantAdapter.notifyDataSetChanged()

                // Verifica se há plantas para mostrar
                if (plantas.isNotEmpty()) {
                    binding.recyclerViewPlantas.visibility = View.VISIBLE // Torna visível se houver plantas
                } else {
                    binding.recyclerViewPlantas.visibility = View.GONE // Mantém invisível se não houver plantas
                }

                Toast.makeText(this@Tela_Menu, "Lista de plantas atualizada!", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@Tela_Menu, "Erro ao atualizar a lista: ${databaseError.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        const val REQUEST_CODE_EDIT_PLANTA = 1001
        const val REQUEST_CODE_SCAN_QR = 1002 // Código para escanear QR
    }
}
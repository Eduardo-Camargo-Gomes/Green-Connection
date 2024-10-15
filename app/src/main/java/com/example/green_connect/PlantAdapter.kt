package com.example.green_connect

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class PlantAdapter(
    val plantas: MutableList<Planta>,
    private val onPlantClicked: (Planta) -> Unit
) : RecyclerView.Adapter<PlantAdapter.PlantaViewHolder>() {

    inner class PlantaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNomePlanta: TextView = itemView.findViewById(R.id.textViewNomePlanta)
        val textViewUmidadeAr: TextView = itemView.findViewById(R.id.textViewUmidadeAr)
        val textViewUmidadeSolo: TextView = itemView.findViewById(R.id.textViewUmidadeSolo)
        val textViewTemperatura: TextView = itemView.findViewById(R.id.textViewTemperatura)

        fun bind(planta: Planta) {
            textViewNomePlanta.text = planta.nome_planta
            textViewUmidadeAr.text = "Umidade do Ar: ${planta.Umidade_do_ar}%"
            textViewUmidadeSolo.text = "Umidade do Solo: ${planta.Umidade_do_solo}%"
            textViewTemperatura.text = "Temperatura: ${planta.temperatura}°C"

            itemView.setOnClickListener { onPlantClicked(planta) }
            itemView.setOnLongClickListener {
                showOptionsDialog(itemView.context, planta)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_layout, parent, false)
        return PlantaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantaViewHolder, position: Int) {
        holder.bind(plantas[position])
    }

    override fun getItemCount(): Int = plantas.size

    private fun showOptionsDialog(context: Context, planta: Planta) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Escolha uma opção")
        builder.setItems(arrayOf("Editar", "Excluir")) { _, which ->
            when (which) {
                0 -> {
                    editPlanta(planta, context)
                }
                1 -> {
                    deletePlanta(planta, context)
                }
            }
        }
        builder.setNegativeButton("Cancelar", null)
        builder.show()
    }

    private fun editPlanta(planta: Planta, context: Context) {
        val intent = Intent(context, CriarPlanta::class.java)
        intent.putExtra("planta", planta)
        context.startActivity(intent)
    }

    private fun deletePlanta(planta: Planta, context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirmação")
            .setMessage("Tem certeza que deseja excluir ${planta.nome_planta}?")
            .setPositiveButton("Sim") { _, _ ->
                val position = plantas.indexOf(planta)
                if (position != -1) {
                    plantas.removeAt(position)
                    notifyItemRemoved(position)
                }
            }
            .setNegativeButton("Não", null)
            .show()
    }



    fun atualizarLista(novaLista: List<Planta>) {
        plantas.clear()
        plantas.addAll(novaLista)
        notifyDataSetChanged()
    }
}

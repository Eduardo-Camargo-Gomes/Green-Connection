package com.example.greenconnect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class Adapter(

) : RecyclerView.Adapter<Adapter.MyViewHolder>() {

    //lista de string, porem precisamos criar obejtos (nome e data da planta)
    private val myList: List<String>

    //METODO DE CRIAR LINHAS DAS PLANTAS
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_adapter_planta, parent, false)
        return MyViewHolder(itemView)
    }

    //RETORNA A QUANTIDADE DE LINAHS QUE DEVEM SER CRIADAS
    override fun getItemCount() = myList.size

    //QUEM PASSA AS INFORMAÇÕES DE IMAGEM E TEXTO
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val name = myList(position)
        holder.TextName.text = name

    }

    class MyViewHolder(item:View):RecyclerView.ViewHolder(itemView){
        val TextName: TextView = itemView.findViewById(R.id.TextName)
    }

}
package com.example.green_connect


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PlantListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var plantAdapter: PlantAdapter
    private lateinit var plantList: MutableList<Planta>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_list)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        plantList = mutableListOf()
        plantAdapter = PlantAdapter(plantList) { planta ->
            // Ação ao clicar em uma planta (abre detalhes, por exemplo)
        }
        recyclerView.adapter = plantAdapter

        loadPlants()
    }

    private fun loadPlants() {
        val database = FirebaseDatabase.getInstance().getReference("plantas")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val loadedPlants = mutableListOf<Planta>()
                for (snapshot in dataSnapshot.children) {
                    val planta = snapshot.getValue(Planta::class.java)
                    planta?.let { loadedPlants.add(it) }
                }
                plantList.clear()
                plantList.addAll(loadedPlants)
                plantAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Tratar erros, talvez mostrar um Toast ou Log
            }
        })
    }
}

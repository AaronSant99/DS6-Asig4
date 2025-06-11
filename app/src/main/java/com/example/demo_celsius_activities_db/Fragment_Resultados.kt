package com.example.demo_celsius_activities_db

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Fragment_Resultados : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnEliminarTodos: View
    private val adapter = ResultadosAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Habilita el menú del fragmento
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment__resultados, container, false)?.apply {
            recyclerView = findViewById(R.id.recyclerView)
            btnEliminarTodos = findViewById(R.id.btnEliminarTodos)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter

            val dbHelper = DatabaseHelper(requireContext())
            val results = dbHelper.getAllResults()
            adapter.setData(results)
            btnEliminarTodos.visibility = if (results.isNotEmpty()) View.VISIBLE else View.GONE

            btnEliminarTodos.setOnClickListener {
                dbHelper.deleteAllResults()
                adapter.setData(emptyList())
                btnEliminarTodos.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_export, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_export_csv -> {
                exportToCSV()
                true
            }
            R.id.action_export_excel -> {
                exportToExcel()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun exportToCSV() {
        // Aquí va la lógica de exportación CSV
        Toast.makeText(requireContext(), "Exportando a CSV...", Toast.LENGTH_SHORT).show()
    }

    private fun exportToExcel() {
        // Aquí va la lógica de exportación Excel
        Toast.makeText(requireContext(), "Exportando a Excel...", Toast.LENGTH_SHORT).show()
    }
}

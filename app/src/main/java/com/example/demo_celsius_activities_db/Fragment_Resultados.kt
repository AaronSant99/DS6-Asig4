package com.example.demo_celsius_activities_db

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class Fragment_Resultados : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var btnEliminarTodos: View
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val adapter = ResultadosAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment__resultados, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        btnEliminarTodos = view.findViewById(R.id.btnEliminarTodos)
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener {
            cargarDatos()
            swipeRefreshLayout.isRefreshing = false
        }

        btnEliminarTodos.setOnClickListener {
            DatabaseHelper(requireContext()).deleteAllResults()
            cargarDatos()
        }

        cargarDatos()
        return view
    }

    private fun cargarDatos() {
        val results = DatabaseHelper(requireContext()).getAllResults()
        adapter.setData(results)
        btnEliminarTodos.visibility = if (results.isNotEmpty()) View.VISIBLE else View.GONE
    }
}

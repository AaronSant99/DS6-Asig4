package com.example.demo_celsius_activities_db

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Fragment_Resultados : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val adapter = ResultadosAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment__resultados, container, false)?.apply {
            recyclerView = findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter

            val dbHelper = DatabaseHelper(requireContext())
            val results = dbHelper.getAllResults()
            adapter.setData(results)
        }
    }
}

class ResultadosAdapter(private val resultList: MutableList<Pair<Int, Double>> = mutableListOf()) : RecyclerView.Adapter<ResultadosAdapter.ResultadoViewHolder>() {

    inner class ResultadoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val resultadoTextView: TextView = itemView.findViewById(R.id.resultadoTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultadoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_resultado, parent, false)
        return ResultadoViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ResultadoViewHolder, position: Int) {
        val (id, result) = resultList[position]
        holder.resultadoTextView.text = "Registro: $id, °F: $result"
    }

    override fun getItemCount(): Int {
        return resultList.size
    }

    fun setData(data: List<Pair<Int, Double>>) {
        resultList.clear()
        resultList.addAll(data)
        notifyDataSetChanged()
    }
}

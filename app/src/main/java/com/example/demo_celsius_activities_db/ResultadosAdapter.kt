package com.example.demo_celsius_activities_db

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ResultadosAdapter(
    private val resultList: MutableList<Resultado> = mutableListOf()
) : RecyclerView.Adapter<ResultadosAdapter.ResultadoViewHolder>() {

    inner class ResultadoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val resultadoTextView: TextView = itemView.findViewById(R.id.resultadoTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultadoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_resultado, parent, false)
        return ResultadoViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ResultadoViewHolder, position: Int) {
        val resultado = resultList[position]
        holder.resultadoTextView.text =
            "Registro: ${resultado.id}, ${resultado.tipo}: ${resultado.valor}"
    }

    override fun getItemCount(): Int = resultList.size

    fun setData(data: List<Resultado>) {
        resultList.clear()
        resultList.addAll(data)
        notifyDataSetChanged()
    }
}

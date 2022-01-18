package com.example.pamstonks.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.pamstonks.R
import android.widget.Toast
import com.example.pamstonks.dataclasses.Stock

interface OnItemClickListener {
    fun onItemClick(view: View, stock: Stock)
}

class SearchRecyclerViewAdapter(private val dataSet: List<Stock>) : RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val codeView: TextView = view.findViewById(R.id.codeTextView)
        val nameView: TextView = view.findViewById(R.id.nameTextView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.company_search_view_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.codeView.text = dataSet[position].ticker
        viewHolder.nameView.text = if (dataSet[position].name.length > 35) "${dataSet[position].name.subSequence(0, 35)}..." else dataSet[position].name

        viewHolder.itemView.setOnClickListener(View.OnClickListener { v ->
            Toast.makeText(v.context, dataSet[position].name, Toast.LENGTH_SHORT).show()
        })
    }

    override fun getItemCount() = dataSet.size
}
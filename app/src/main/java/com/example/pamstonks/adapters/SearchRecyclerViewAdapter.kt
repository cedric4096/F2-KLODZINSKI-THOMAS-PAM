package com.example.pamstonks.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.pamstonks.R
import com.example.pamstonks.dataclasses.Stock

class SearchRecyclerViewAdapter(private val dataSet: List<Stock>, private val onItemClicked: (Stock) -> Unit) : RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(view: View, onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        val codeView: TextView = view.findViewById(R.id.codeTextView)
        val nameView: TextView = view.findViewById(R.id.nameTextView)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.company_search_view_item, viewGroup, false)

        return ViewHolder(view) {
            onItemClicked(dataSet[it])
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.codeView.text = dataSet[position].ticker
        viewHolder.nameView.text = if (dataSet[position].name.length > 35) "${dataSet[position].name.subSequence(0, 35)}..." else dataSet[position].name
    }

    override fun getItemCount() = dataSet.size
}
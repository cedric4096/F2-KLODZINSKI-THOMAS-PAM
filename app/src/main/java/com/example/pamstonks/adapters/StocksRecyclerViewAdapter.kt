package com.example.pamstonks.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pamstonks.R
import com.example.pamstonks.dataclasses.Stock
import com.example.pamstonks.viewmodels.StockViewModel

class StocksRecyclerViewAdapter(private val dataSet: List<Stock>, private val viewModel: StockViewModel, private val onItemClicked: (Stock) -> Unit) : RecyclerView.Adapter<StocksRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(view: View, onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        val codeView: TextView = view.findViewById(R.id.codeTextView)
        val nameView: TextView = view.findViewById(R.id.nameTextView)
        val deleteButton: ImageButton = view.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.company_home_list_view_item, viewGroup, false)

        return ViewHolder(view) {
            onItemClicked(dataSet[it])
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.codeView.text = dataSet[position].ticker
        viewHolder.nameView.text = if (dataSet[position].name.length > 35) "${dataSet[position].name.subSequence(0, 35)}..." else dataSet[position].name
        viewHolder.deleteButton.setOnClickListener {
            viewModel.delete(dataSet[position])
        }
    }

    override fun getItemCount() = dataSet.size
}
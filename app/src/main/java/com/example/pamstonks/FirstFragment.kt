package com.example.pamstonks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pamstonks.adapters.StocksRecyclerViewAdapter
import com.example.pamstonks.databinding.FragmentFirstBinding
import com.example.pamstonks.dataclasses.Stock
import com.example.pamstonks.viewmodels.StockViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    private val stocks: StockViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerview = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerview.layoutManager = LinearLayoutManager(view.context)

        val listObserver = Observer<MutableList<Stock>> { newList ->
            val adapter = StocksRecyclerViewAdapter(newList) {
                val bundle = bundleOf("stockName" to it.name, "stockTicker" to it.ticker)
                findNavController().navigate(R.id.action_FirstFragment_to_stockFragment, bundle)
            }
            recyclerview.adapter = adapter
        }

        stocks.currentStocks.observe(viewLifecycleOwner, listObserver)

        binding.addStockButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
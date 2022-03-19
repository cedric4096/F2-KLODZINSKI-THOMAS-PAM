package com.example.pamstonks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pamstonks.PamstonksApplication
import com.example.pamstonks.R
import com.example.pamstonks.adapters.StocksRecyclerViewAdapter
import com.example.pamstonks.databinding.FragmentHomeBinding
import com.example.pamstonks.dataclasses.Stock
import com.example.pamstonks.viewmodels.StockViewModel
import com.example.pamstonks.viewmodels.StockViewModelFactory

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val stocks: StockViewModel by activityViewModels {
        StockViewModelFactory((activity?.application as PamstonksApplication).repository)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerview = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerview.layoutManager = LinearLayoutManager(view.context)

        val listObserver = Observer<List<Stock>> { newList ->
            val adapter = StocksRecyclerViewAdapter(newList, stocks) {
                val bundle = bundleOf("stockName" to it.name, "stockTicker" to it.ticker)
                findNavController().navigate(R.id.action_HomeFragment_to_StockFragment, bundle)
            }
            recyclerview.adapter = adapter
        }

        stocks.allStocks.observe(viewLifecycleOwner, listObserver)

        binding.addStockButton.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_SearchFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
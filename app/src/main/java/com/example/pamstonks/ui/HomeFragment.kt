package com.example.pamstonks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    /**
     * Gets the StockViewModel from activity view models. Uses StockViewModelFactory to build it with the StockRepository.
     */
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

        // Gets the RecyclerView and declares the layout
        val recyclerview = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerview.layoutManager = LinearLayoutManager(view.context)

        // Declares an Observer over a list of Stock items
        val listObserver = Observer<List<Stock>> { newList ->
            // On each change in the list, creates a new Adapter with the list, the StockViewModel and the items onClick listener
            val adapter = StocksRecyclerViewAdapter(newList, stocks) {
                // On click on item, navigates to the details page with the bundle containing stock information
                val bundle = bundleOf("stockName" to it.name, "stockTicker" to it.ticker)
                findNavController().navigate(R.id.action_HomeFragment_to_StockFragment, bundle)
            }
            recyclerview.adapter = adapter
        }

        // Observes the stock view model
        stocks.allStocks.observe(viewLifecycleOwner, listObserver)

        // Sets the onClick listener for the addStock button
        binding.addStockButton.setOnClickListener {
            // Navigates to the search page
            findNavController().navigate(R.id.action_HomeFragment_to_SearchFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
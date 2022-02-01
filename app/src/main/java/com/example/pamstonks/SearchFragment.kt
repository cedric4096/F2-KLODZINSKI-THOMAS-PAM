package com.example.pamstonks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pamstonks.adapters.SearchRecyclerViewAdapter
import com.example.pamstonks.databinding.FragmentSearchBinding
import com.example.pamstonks.dataclasses.SearchResult
import com.example.pamstonks.dataclasses.Stock
import com.example.pamstonks.viewmodels.SearchResultViewModel
import com.example.pamstonks.viewmodels.StockViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    private val model: SearchResultViewModel by activityViewModels()
    private val stocks: StockViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerview = view.findViewById<RecyclerView>(R.id.resultsView)
        recyclerview.layoutManager = LinearLayoutManager(view.context)

        val resultsObserver = Observer<SearchResult> { newResults ->
            val adapter = SearchRecyclerViewAdapter(newResults.results) {
                val nList: MutableList<Stock> = stocks.currentStocks.value!!
                nList.add(it)
                stocks.currentStocks.postValue(nList)

                findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
            recyclerview.adapter = adapter
        }

        model.currentResults.observe(viewLifecycleOwner, resultsObserver)

        binding.buttonSecond.setOnClickListener {
            lifecycleScope.launch {
                val str = StockAPI.searchForStocks(binding.searchCompanyTextBox.text.toString())
                val data = Json{ignoreUnknownKeys = true}.decodeFromString<SearchResult>(str)

                model.currentResults.postValue(data)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.pamstonks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pamstonks.PamstonksApplication
import com.example.pamstonks.R
import com.example.pamstonks.StockAPI
import com.example.pamstonks.adapters.SearchRecyclerViewAdapter
import com.example.pamstonks.databinding.FragmentSearchBinding
import com.example.pamstonks.dataclasses.SearchResult
import com.example.pamstonks.viewmodels.SearchResultViewModel
import com.example.pamstonks.viewmodels.StockViewModel
import com.example.pamstonks.viewmodels.StockViewModelFactory
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    private val model: SearchResultViewModel by activityViewModels()
    private val stocks: StockViewModel by activityViewModels {
        StockViewModelFactory((activity?.application as PamstonksApplication).repository)
    }

    private val json: Json = Json { ignoreUnknownKeys = true }

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
                stocks.insert(it)
                findNavController().navigate(R.id.action_SearchFragment_to_HomeFragment)
            }
            recyclerview.adapter = adapter
            binding.resultCountTextView.text = String.format(resources.getString(R.string.results_count), newResults.results.count())
        }

        model.currentResults.observe(viewLifecycleOwner, resultsObserver)

        binding.searchButton.setOnClickListener {
            lifecycleScope.launch {
                val str = StockAPI.searchForStocks(binding.searchCompanyTextBox.text.toString())
                val data = json.decodeFromString<SearchResult>(str)

                model.currentResults.postValue(data)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
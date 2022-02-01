package com.example.pamstonks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.pamstonks.databinding.FragmentStockdetailsBinding
import com.example.pamstonks.dataclasses.PreviousCloseResult
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class StockFragment : Fragment() {
    private var _binding: FragmentStockdetailsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStockdetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tickerTextView.text = arguments?.getString("stockTicker")

        lifecycleScope.launch {
            val str = StockAPI.getStockPreviousCloseByTicker(arguments?.getString("stockTicker")!!)
            val data = Json{ignoreUnknownKeys = true}.decodeFromString<PreviousCloseResult>(str)

            binding.closeValueTextView.text = "$${data.results[0].closing}"
            binding.highestValueTextView.text = data.results[0].highest.toString()
            binding.lowestValueTextView.text = data.results[0].lowest.toString()
            binding.openingValueTextView.text = data.results[0].opening.toString()

            val percentage = if (data.results[0].closing > data.results[0].opening) {
                (data.results[0].closing / data.results[0].opening - 1) * 100
                binding.percentageTextView.setTextColor()
            } else {
                (- data.results[0].opening / data.results[0].closing - 1) * 100
            }

            binding.percentageTextView.text = "${if (percentage > 0) "+" else ""}${
                String.format(
                    "%.2f",
                    percentage
                )
            }%"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.pamstonks.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.pamstonks.R
import com.example.pamstonks.StockAPI
import com.example.pamstonks.databinding.FragmentStockdetailsBinding
import com.example.pamstonks.dataclasses.AggregateResult
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.*

class StockFragment : Fragment() {
    private var _binding: FragmentStockdetailsBinding? = null

    private val binding get() = _binding!!

    /**
     * JSON deserializer, which ignores unknown keys to avoid errors.
     */
    private val json: Json = Json { ignoreUnknownKeys = true }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStockdetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieves the passed bundle data
        binding.tickerTextView.text = arguments?.getString("stockTicker")

        // Async block for network requests
        lifecycleScope.launch {
            // Retrieves stock sessions data
            val str = StockAPI.getStockAggregateBarsByTicker(arguments?.getString("stockTicker")!!)
            val data = json.decodeFromString<AggregateResult>(str)

            // If no data (no network or more than 5 calls per minute), displays an error toast
            if (data.count == 0L) {
                Toast.makeText(context, R.string.loading_error, Toast.LENGTH_SHORT).show()
            } else {
                // Sets text views data from the last session
                binding.closeValueTextView.text = "$${data.results.last().closing}"
                binding.highestValueTextView.text = data.results.last().highest.toString()
                binding.lowestValueTextView.text = data.results.last().lowest.toString()
                binding.openingValueTextView.text = data.results.last().opening.toString()

                // Calculates the variation percentage value and color
                val percentage = if (data.results.last().closing > data.results.last().opening) {
                    binding.percentageTextView.setTextColor(Color.parseColor("#00ff40"))
                    (data.results.last().closing - data.results.last().opening) / data.results.last().closing * 100
                } else {
                    binding.percentageTextView.setTextColor(Color.parseColor("#ff0000"))
                    (data.results.last().closing - data.results.last().opening) / data.results.last().opening * 100
                }

                // Displays the percentage with only 2 digits after the dot
                binding.percentageTextView.text = "${if (percentage > 0) "+" else ""}${
                    String.format(
                        "%.2f",
                        percentage
                    )
                }%"

                // Chart setup
                binding.chart.axisLeft.setDrawGridLines(true)
                val xAxis: XAxis = binding.chart.xAxis
                xAxis.setDrawGridLines(false)
                xAxis.setDrawAxisLine(false)

                binding.chart.axisRight.isEnabled = false
                binding.chart.legend.isEnabled = false
                binding.chart.description.isEnabled = false
                binding.chart.setBackgroundColor(Color.WHITE)

                binding.chart.animateX(500, Easing.EaseInSine)

                xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
                xAxis.valueFormatter = MyAxisFormatter()
                xAxis.setDrawLabels(true)
                xAxis.granularity = 1f

                val entries: ArrayList<CandleEntry> = ArrayList()

                // Creating entries for each session
                for (item in data.results) {
                    entries.add(CandleEntry(item.date.toFloat(), item.highest.toFloat(), item.lowest.toFloat(), item.opening.toFloat(), item.closing.toFloat()))
                }

                val dataSet = CandleDataSet(entries, "Entries")
                dataSet.axisDependency = YAxis.AxisDependency.LEFT
                dataSet.shadowColor = Color.DKGRAY
                dataSet.shadowWidth = 3f
                dataSet.formLineWidth = 5f
                dataSet.showCandleBar = true
                dataSet.decreasingColor = Color.RED
                dataSet.decreasingPaintStyle = Paint.Style.FILL_AND_STROKE
                dataSet.increasingColor = Color.GREEN
                dataSet.increasingPaintStyle = Paint.Style.FILL_AND_STROKE
                dataSet.neutralColor = Color.BLUE

                // Sets the chart entries and redraws it
                val chartData = CandleData(dataSet)
                binding.chart.data = chartData
                binding.chart.invalidate()
            }
        }
    }

    /**
     * Formatter class for the graph, formats the displayed dates.
     */
    inner class MyAxisFormatter : IndexAxisValueFormatter() {
        override fun getFormattedValue(value: Float, axis: AxisBase?): String {
            val calendar = GregorianCalendar()
            val format = SimpleDateFormat("dd/MM", Locale.US)
            calendar.timeInMillis = value.toLong()

            return format.format(calendar.time)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
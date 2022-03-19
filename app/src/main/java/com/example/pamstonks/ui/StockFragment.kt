package com.example.pamstonks.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
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

        binding.tickerTextView.text = arguments?.getString("stockTicker")

        lifecycleScope.launch {
            var str = StockAPI.getStockPreviousCloseByTicker(arguments?.getString("stockTicker")!!)
            var data = json.decodeFromString<AggregateResult>(str)

            binding.closeValueTextView.text = "$${data.results[0].closing}"
            binding.highestValueTextView.text = data.results[0].highest.toString()
            binding.lowestValueTextView.text = data.results[0].lowest.toString()
            binding.openingValueTextView.text = data.results[0].opening.toString()

            val percentage = if (data.results[0].closing > data.results[0].opening) {
                binding.percentageTextView.setTextColor(Color.parseColor("#00ff40"))
                (data.results[0].closing / data.results[0].opening - 1) * 100
            } else {
                binding.percentageTextView.setTextColor(Color.parseColor("#ff0000"))
                (- data.results[0].opening / data.results[0].closing - 1) * 100
            }

            binding.percentageTextView.text = "${if (percentage > 0) "+" else ""}${
                String.format(
                    "%.2f",
                    percentage
                )
            }%"

            str = StockAPI.getStockAggregateBarsByTicker(arguments?.getString("stockTicker")!!)
            data = json.decodeFromString(str)

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

            val chartData = CandleData(dataSet)
            binding.chart.data = chartData
            binding.chart.invalidate()
        }
    }

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
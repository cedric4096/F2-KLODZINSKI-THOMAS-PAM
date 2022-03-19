package com.example.pamstonks

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class StockAPI {
    companion object {
        private const val apiRoot = "https://api.polygon.io/"

        private suspend fun getStockData(apiToUse: String): String {
            return withContext(Dispatchers.IO) {
                val url = URL(apiRoot + apiToUse)

                with (url.openConnection() as HttpURLConnection) {
                    requestMethod = "GET"
                    setRequestProperty("Accept", "application/json")
                    setRequestProperty(
                        "Authorization",
                        "Bearer " + "CNZHn38lFjpd2lvG8A3DazTe1xheWQTa"
                    )

                    BufferedReader(InputStreamReader(inputStream)).readText()
                }
            }
        }

        suspend fun searchForStocks(name: String): String {
            return try {
                getStockData("v3/reference/tickers?search=$name&active=true&sort=ticker&order=asc&limit=10")
            } catch (e: Exception) {
                "{\"status\": \"0\", \"results\": []}"
            }
        }

        suspend fun getStockPreviousCloseByTicker(ticker: String): String {
            return try {
                getStockData("v2/aggs/ticker/$ticker/prev")
            } catch (e: Exception) {
                "{\"status\": \"0\", \"results\": [], \"count\": 0}"
            }
        }

        suspend fun getStockAggregateBarsByTicker(ticker: String): String {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val calendar = GregorianCalendar()
            val now = format.format(calendar.time)
            calendar.add(Calendar.MONTH, -1)
            val monthAgo = format.format(calendar.time)

            return try {
                getStockData("v2/aggs/ticker/$ticker/range/1/day/$monthAgo/$now")
            } catch (e: Exception) {
                "{\"status\": \"0\", \"results\": [], \"count\": 0}"
            }
        }
    }
}
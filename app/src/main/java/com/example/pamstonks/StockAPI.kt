package com.example.pamstonks

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

/**
 * Stock API class. Contains the static methods for calling the Polygon.io API.
 */
class StockAPI {
    companion object {
        private const val apiRoot = "https://api.polygon.io/"

        /**
         * Gets data from the API.
         */
        private suspend fun getStockData(apiToUse: String): String {
            // Returns the response data of the following HTTP request
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

        /**
         * Sends a search request with the provided name or ticker, and returns the results.
         */
        suspend fun searchForStocks(name: String): String {
            // If Exception (i.e. no network or no more calls available), returns a valid empty item
            return try {
                getStockData("v3/reference/tickers?search=$name&active=true&sort=ticker&order=asc&limit=10")
            } catch (e: Exception) {
                "{\"status\": \"0\", \"results\": []}"
            }
        }

        /**
         * Sends a request with the provided ticker and returns the aggregates from the API.
         */
        suspend fun getStockAggregateBarsByTicker(ticker: String): String {
            // Calculates the date a month ago
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val calendar = GregorianCalendar()
            val now = format.format(calendar.time)
            calendar.add(Calendar.MONTH, -1)
            val monthAgo = format.format(calendar.time)

            // If Exception (i.e. no network or no more calls available), returns a valid empty item
            return try {
                getStockData("v2/aggs/ticker/$ticker/range/1/day/$monthAgo/$now")
            } catch (e: Exception) {
                "{\"status\": \"0\", \"results\": [], \"count\": 0}"
            }
        }
    }
}
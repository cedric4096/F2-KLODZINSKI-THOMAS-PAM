package com.example.pamstonks

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

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
            return getStockData("v3/reference/tickers?search=$name&limit=20")
        }

        suspend fun getStockPreviousCloseByTicker(ticker: String): String {
            return getStockData("v2/aggs/ticker/$ticker/prev")
        }

        suspend fun getStockAggregateBarsByTicker(ticker: String, date: String): String {
            return getStockData("v2/aggs/ticker/$ticker/range/1/hour/$date/$date")
        }
    }
}
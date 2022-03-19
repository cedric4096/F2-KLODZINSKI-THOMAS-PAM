package com.example.pamstonks.repositories

import androidx.annotation.WorkerThread
import com.example.pamstonks.daos.StockDAO
import com.example.pamstonks.dataclasses.Stock
import kotlinx.coroutines.flow.Flow

class StockRepository (private val stockDAO: StockDAO) {
    val allStocks: Flow<List<Stock>> = stockDAO.getAll()

    @WorkerThread
    suspend fun insertAll(vararg stock: Stock) {
        stockDAO.insertAll(*stock)
    }

    @WorkerThread
    suspend fun delete(stock: Stock) {
        stockDAO.delete(stock)
    }
}
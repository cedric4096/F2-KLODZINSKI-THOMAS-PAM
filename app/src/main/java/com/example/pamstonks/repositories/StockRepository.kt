package com.example.pamstonks.repositories

import androidx.annotation.WorkerThread
import com.example.pamstonks.daos.StockDAO
import com.example.pamstonks.dataclasses.Stock
import kotlinx.coroutines.flow.Flow

/**
 * Stock repository. Allows fetching and persisting in database.
 */
class StockRepository (private val stockDAO: StockDAO) {
    /**
     * Gets all items from database as a Flow, which allows async operations.
     */
    val allStocks: Flow<List<Stock>> = stockDAO.getAll()

    /**
     * Inserts items in database, asynchronously.
     */
    @WorkerThread
    suspend fun insertAll(vararg stock: Stock) {
        stockDAO.insertAll(*stock)
    }

    /**
     * Deletes an item from database, asynchronously.
     */
    @WorkerThread
    suspend fun delete(stock: Stock) {
        stockDAO.delete(stock)
    }
}
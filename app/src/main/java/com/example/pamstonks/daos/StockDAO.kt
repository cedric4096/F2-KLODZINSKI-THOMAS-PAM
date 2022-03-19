package com.example.pamstonks.daos

import androidx.room.*
import com.example.pamstonks.dataclasses.Stock
import kotlinx.coroutines.flow.Flow

/**
 * DAO for Stock entity. Used to retrieve items from database.
 */
@Dao
interface StockDAO {
    /**
     * Gets all Stock items from database.
     */
    @Query("SELECT * FROM stock")
    fun getAll(): Flow<List<Stock>>

    /**
     * Inserts provided Stock items in database.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg stocks: Stock)

    /**
     * Deletes provided Stock item from database.
     */
    @Delete
    suspend fun delete(stock: Stock)
}
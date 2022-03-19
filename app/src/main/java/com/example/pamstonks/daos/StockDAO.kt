package com.example.pamstonks.daos

import androidx.room.*
import com.example.pamstonks.dataclasses.Stock
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDAO {
    @Query("SELECT * FROM stock")
    fun getAll(): Flow<List<Stock>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg stocks: Stock)

    @Delete
    suspend fun delete(stock: Stock)
}
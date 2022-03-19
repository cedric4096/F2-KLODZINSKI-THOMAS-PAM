package com.example.pamstonks

import android.app.Application
import com.example.pamstonks.repositories.StockRepository

/**
 * Pamstonks application main class
 */
class PamstonksApplication : Application() {
    /**
     * Gets the database.
     */
    val database by lazy { AppDatabase.getDatabase(this) }

    /**
     * Gets the Stock repository.
     */
    val repository by lazy { StockRepository(database.stockDao()) } // Builds the repository with the StockDAO object from the database
}
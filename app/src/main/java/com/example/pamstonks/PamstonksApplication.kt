package com.example.pamstonks

import android.app.Application
import com.example.pamstonks.repositories.StockRepository

class PamstonksApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { StockRepository(database.stockDao()) }
}
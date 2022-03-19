package com.example.pamstonks

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pamstonks.daos.StockDAO
import com.example.pamstonks.dataclasses.Stock

/**
 * Application database. Persists the Stock items between app instances.
 */
@Database(entities = [Stock::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Gets the StockDAO object
     */
    abstract fun stockDao(): StockDAO

    companion object {
        /**
         * Static database reference.
         */
        @Volatile
        private var db: AppDatabase? = null

        /**
         * Gets the database instance or builds it if it does not exists already.
         */
        fun getDatabase(context: Context): AppDatabase {
            return db ?: synchronized(this) {
                val inst = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "db"
                ).build()
                db = inst

                inst
            }
        }
    }
}
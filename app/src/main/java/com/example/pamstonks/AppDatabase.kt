package com.example.pamstonks

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pamstonks.daos.StockDAO
import com.example.pamstonks.dataclasses.Stock

@Database(entities = [Stock::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stockDao(): StockDAO

    companion object {
        @Volatile
        private var db: AppDatabase? = null

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
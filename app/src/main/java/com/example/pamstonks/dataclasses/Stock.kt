package com.example.pamstonks.dataclasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * Stock entity. Stores basic stock information.
 */
@Entity
@Serializable
data class Stock (
    /**
     * Ticker of the stock, primary key for the database.
     */
    @PrimaryKey
    val ticker: String,
    /**
     * Name of the associated company
     */
    val name: String
)
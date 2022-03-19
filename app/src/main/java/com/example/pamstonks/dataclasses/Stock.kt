package com.example.pamstonks.dataclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Stock (
    @PrimaryKey
    val ticker: String,
    val name: String,
    val market: String,
    val type: String? = null,
    val active: Boolean,
    @ColumnInfo(name = "currency_name")
    @SerialName("currency_name")
    val currencyName: String,
    @ColumnInfo(name = "last_updated_utc")
    @SerialName("last_updated_utc")
    val lastUpdatedUTC: String
)
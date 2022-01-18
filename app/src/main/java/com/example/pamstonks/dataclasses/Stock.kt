package com.example.pamstonks.dataclasses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Stock (
    val ticker: String,
    val name: String,
    val market: String,
    val locale: String,

    @SerialName("primary_exchange")
    val primaryExchange: String,

    val type: String? = null,
    val active: Boolean,

    @SerialName("currency_name")
    val currencyName: String,

    val cik: String? = null,

    @SerialName("composite_figi")
    val compositeFigi: String? = null,

    @SerialName("share_class_figi")
    val shareClassFigi: String? = null,

    @SerialName("last_updated_utc")
    val lastUpdatedUTC: String
)
package com.example.pamstonks.dataclasses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResult (
    val results: List<Stock>,
    val status: String,

    @SerialName("request_id")
    val requestID: String
)
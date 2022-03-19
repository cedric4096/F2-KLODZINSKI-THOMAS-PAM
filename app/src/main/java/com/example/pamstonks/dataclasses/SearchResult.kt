package com.example.pamstonks.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult (
    val results: List<Stock>,
    val status: String
)
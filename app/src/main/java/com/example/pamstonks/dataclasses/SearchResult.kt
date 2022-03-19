package com.example.pamstonks.dataclasses

import kotlinx.serialization.Serializable

/**
 * Search result class. Stores stock search results.
 */
@Serializable
data class SearchResult (
    val results: List<Stock>,
    val status: String
)
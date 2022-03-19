package com.example.pamstonks.dataclasses

import kotlinx.serialization.Serializable

/**
 * Aggregate result class. Stores data from Aggregation API, contains stock sessions data.
 */
@Serializable
data class AggregateResult (
    val results: List<SessionResult>,
    val status: String,
    val count: Long
)
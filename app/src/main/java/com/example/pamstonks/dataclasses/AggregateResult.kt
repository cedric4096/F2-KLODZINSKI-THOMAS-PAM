package com.example.pamstonks.dataclasses

import kotlinx.serialization.Serializable

@Serializable
data class AggregateResult (
    val results: List<SessionResult>,
    val status: String,
    val count: Long
)
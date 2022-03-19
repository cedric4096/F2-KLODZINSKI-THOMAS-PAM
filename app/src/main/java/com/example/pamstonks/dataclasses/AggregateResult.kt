package com.example.pamstonks.dataclasses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AggregateResult (
    val results: List<SessionResult>,
    val status: String,

    @SerialName("request_id")
    val requestID: String,
    val count: Long
)
package com.example.pamstonks.dataclasses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreviousCloseResult (
    val results: List<SessionResult>,
    val status: String,

    @SerialName("request_id")
    val requestID: String,
    val count: Long
)

@Serializable
data class SessionResult (
    @SerialName("h")
    val highest: Double,
    @SerialName("l")
    val lowest: Double,
    @SerialName("o")
    val opening: Double,
    @SerialName("c")
    val closing: Double
)
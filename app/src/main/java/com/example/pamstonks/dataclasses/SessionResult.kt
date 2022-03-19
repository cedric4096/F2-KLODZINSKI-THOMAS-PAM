package com.example.pamstonks.dataclasses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionResult (
    @SerialName("h")
    val highest: Double,
    @SerialName("l")
    val lowest: Double,
    @SerialName("o")
    val opening: Double,
    @SerialName("c")
    val closing: Double,
    @SerialName("t")
    val date: Double
)
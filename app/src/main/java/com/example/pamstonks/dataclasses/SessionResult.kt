package com.example.pamstonks.dataclasses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Session result class. Stores session values data.
 */
@Serializable
data class SessionResult (
    /**
     * Highest value of the session.
     */
    @SerialName("h")
    val highest: Double,
    /**
     * Lowest value of the session.
     */
    @SerialName("l")
    val lowest: Double,
    /**
     * Opening value of the session.
     */
    @SerialName("o")
    val opening: Double,
    /**
     * Closing value of the session.
     */
    @SerialName("c")
    val closing: Double,
    /**
     * Date of the session.
     */
    @SerialName("t")
    val date: Double
)
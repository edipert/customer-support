package com.ediperturk.customer.api.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Geo(
    @SerialName("lat") val lat: Double,
    @SerialName("lng") val lng: Double,
)
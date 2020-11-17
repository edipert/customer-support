package com.ediperturk.customer.api.data.entity

import androidx.room.Embedded
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Address(
    @SerialName("street") val street: String,
    @SerialName("suite") val suite: String,
    @SerialName("city") val city: String,
    @SerialName("zipcode") val zipcode: String,
    @SerialName("geo") @Embedded val geo: Geo,
) {

    val fullAddress: String
        get() = "$street, $suite - $city / $zipcode"
}
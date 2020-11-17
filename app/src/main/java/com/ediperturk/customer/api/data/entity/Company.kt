package com.ediperturk.customer.api.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Company(
    @SerialName("name") val companyName: String,
    @SerialName("catchPhrase") val catchPhrase: String,
    @SerialName("bs") val bs: String
)
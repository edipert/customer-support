package com.ediperturk.customer.api.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "user")
data class User(
    @SerialName("id") @PrimaryKey val id: Int,
    @SerialName("name") val name: String,
    @SerialName("username") val username: String,
    @SerialName("email") val email: String,
    @SerialName("phone") val phone: String,
    @SerialName("website") val website: String,
    @SerialName("address") @Embedded val address: Address,
    @SerialName("company") @Embedded val company: Company
) {

    val initials: String
        get() {
            var value = ""
            name.split(" ").forEach { value += it.first() }
            return value.toUpperCase()
        }
}
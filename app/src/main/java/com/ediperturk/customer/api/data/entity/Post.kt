package com.ediperturk.customer.api.data.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "post")
data class Post(
    @SerialName("id") @PrimaryKey val id: Int,
    @SerialName("userId") val userId: Int,
    @SerialName("title") val title: String,
    @SerialName("body") val body: String
)
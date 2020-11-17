package com.ediperturk.customer.api.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "comment")
data class Comment(
    @SerialName("id") @PrimaryKey val id: Int,
    @SerialName("postId") val postId: Int,
    @SerialName("name") val name: String,
    @SerialName("email") val email: String,
    @SerialName("body") val body: String
)
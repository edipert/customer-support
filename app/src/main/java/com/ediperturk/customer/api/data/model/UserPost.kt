package com.ediperturk.customer.api.data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.ediperturk.customer.api.data.entity.Post
import com.ediperturk.customer.api.data.entity.User

data class UserPost(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val posts: List<Post>
)
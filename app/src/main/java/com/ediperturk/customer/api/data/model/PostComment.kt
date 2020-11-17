package com.ediperturk.customer.api.data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.ediperturk.customer.api.data.entity.Comment
import com.ediperturk.customer.api.data.entity.Post
import com.ediperturk.customer.api.data.entity.User

class PostComment(
    @Embedded val post: Post,
    @Relation(
        parentColumn = "userId",
        entityColumn = "id"
    )
    val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "postId"
    )
    val comments: List<Comment>
)
package com.ediperturk.customer.api.repository.user

import com.ediperturk.customer.api.base.Result
import com.ediperturk.customer.api.data.entity.Comment
import com.ediperturk.customer.api.data.entity.Post
import com.ediperturk.customer.api.data.entity.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUser(userId: Int): Flow<Result<User>>

    suspend fun getUsers(): Flow<Result<List<User>>>

    suspend fun getPosts(userId: Int): Flow<Result<List<Post>>>

    suspend fun getComments(postId: Int): Flow<Result<List<Comment>>>
}
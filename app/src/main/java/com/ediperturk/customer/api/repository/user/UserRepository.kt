package com.ediperturk.customer.api.repository.user

import com.ediperturk.customer.api.base.Result
import com.ediperturk.customer.api.data.entity.User
import com.ediperturk.customer.api.data.model.PostComment
import com.ediperturk.customer.api.data.model.UserPost
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUser(userId: Int): Flow<Result<User>>

    suspend fun getUsers(): Flow<Result<List<User>>>

    suspend fun getPosts(userId: Int): Flow<Result<UserPost>>

    suspend fun getComments(postId: Int): Flow<Result<PostComment>>
}
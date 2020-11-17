package com.ediperturk.customer.api.service

import com.ediperturk.customer.api.data.entity.Comment
import com.ediperturk.customer.api.data.entity.Post
import com.ediperturk.customer.api.data.entity.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("users")
    suspend fun getUser(@Query("id") user: Int): Response<List<User>>

    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @GET("posts")
    suspend fun getUserPosts(@Query("userId") userID: Int): Response<List<Post>>

    @GET("comments")
    suspend fun getPostComments(@Query("postId") postId: Int): Response<List<Comment>>
}
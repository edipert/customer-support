package com.ediperturk.customer.api.repository.user

import com.ediperturk.customer.api.local.UserDao
import com.ediperturk.customer.api.repository.BaseRepository
import com.ediperturk.customer.api.service.UserService
import com.ediperturk.customer.common.DispatcherProvider
import com.ediperturk.customer.manager.resource.Resources
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    override val resources: Resources,
    private val userDao: UserDao,
    private val userService: UserService,
    override var dispatcher: DispatcherProvider
) : BaseRepository(), UserRepository {


    override suspend fun getUser(userId: Int) = performGetOperation(
        // Get user from database by user id
        { userDao.getUser(userId) },
        // Fetch user from remote data source by user id
        { userService.getUser(userId) },
        // Save fetched user data to database
        { userDao.insertUsers(it) }
    )

    override suspend fun getUsers() = performGetOperation(
        // Get user list from database
        { userDao.getUsers() },
        // Fetch user list from remote data source
        { userService.getUsers() },
        // Save fetched user list data to database
        { userDao.insertUsers(it) }
    )

    override suspend fun getPosts(userId: Int) = performGetOperation(
        // Get user and user's post list from database by user id
        { userDao.getUserPosts(userId) },
        // Fetch user's post list from remote data source by user id
        { userService.getUserPosts(userId) },
        // Save fetched user's post list fto database
        { userDao.insertPosts(it) }
    )

    override suspend fun getComments(postId: Int) = performGetOperation(
        // Get user, user's post and post's comment list from database by post id
        { userDao.getPostComments(postId) },
        // Fetch post's comment list from remote data source by post id
        { userService.getPostComments(postId) },
        // Save fetched post's comment list to database
        { userDao.insertComments(it) }
    )
}
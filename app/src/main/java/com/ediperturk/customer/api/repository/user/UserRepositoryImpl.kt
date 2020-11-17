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
        { userDao.getUser(userId) },
        { userService.getUser(userId) },
        { userDao.insertUsers(it) }
    )

    override suspend fun getUsers() = performGetOperation(
        { userDao.getUsers() },
        { userService.getUsers() },
        { userDao.insertUsers(it) }
    )

    override suspend fun getPosts(userId: Int) = performGetOperation(
        { userDao.getUserPosts(userId) },
        { userService.getUserPosts(userId) },
        { userDao.insertPosts(it) }
    )

    override suspend fun getComments(postId: Int) = performGetOperation(
        { userDao.getPostComments(postId) },
        { userService.getPostComments(postId) },
        { userDao.insertComments(it) }
    )
}
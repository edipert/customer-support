package com.ediperturk.customer.api.repository.user

import com.ediperturk.customer.api.base.Result
import com.ediperturk.customer.api.data.entity.User
import com.ediperturk.customer.api.local.UserDao
import com.ediperturk.customer.api.repository.BaseRepository
import com.ediperturk.customer.api.service.UserService
import com.ediperturk.customer.common.DispatcherProvider
import com.ediperturk.customer.manager.resource.Resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/* Since we do not use The Room Database in unit test, we created a repository class where we can only mock http requests. */
class UserRepositoryImpl @Inject constructor(
    override val resources: Resources,
    private val userDao: UserDao,
    private val userService: UserService,
    override var dispatcher: DispatcherProvider
) : BaseRepository(), UserRepository {

    override suspend fun getUser(userId: Int): Flow<Result<User>> {
        return performGetOperation {
            userService.getUser(userId)
        }.map {
            when (it) {
                is Result.Success -> Result.Success(it.data[0])
                is Result.Error -> Result.Error(it.exception)
            }
        }
    }

    override suspend fun getUsers() = performGetOperation {
        userService.getUsers()
    }

    override suspend fun getPosts(userId: Int) = performGetOperation {
        userService.getUserPosts(
            userId
        )
    }

    override suspend fun getComments(postId: Int) = performGetOperation {
        userService.getPostComments(postId)
    }
}
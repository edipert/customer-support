package com.ediperturk.customer.api.local

import androidx.annotation.VisibleForTesting
import androidx.room.*
import com.ediperturk.customer.api.data.entity.Comment
import com.ediperturk.customer.api.data.entity.Post
import com.ediperturk.customer.api.data.entity.User
import com.ediperturk.customer.api.data.model.PostComment
import com.ediperturk.customer.api.data.model.UserPost

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUser(userId: Int): User

    @Query("SELECT * FROM user")
    fun getUsers(): List<User>

    @Transaction
    @Query("SELECT * FROM user WHERE id = :userId")
    fun getUserPosts(userId: Int): UserPost

    @Transaction
    @Query("SELECT * FROM post WHERE id = :postId")
    fun getPostComments(postId: Int): PostComment

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<Post>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comments: List<Comment>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @VisibleForTesting
    fun insertUsersForTest(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @VisibleForTesting
    fun insertPostsForTest(posts: List<Post>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @VisibleForTesting
    fun insertCommentsForTest(comments: List<Comment>)
}
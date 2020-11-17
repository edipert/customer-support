package com.ediperturk.customer.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ediperturk.customer.api.base.Result
import com.ediperturk.customer.api.data.entity.Post
import com.ediperturk.customer.api.exception.ApiException
import com.ediperturk.customer.api.local.UserDao
import com.ediperturk.customer.api.repository.user.UserRepositoryImpl
import com.ediperturk.customer.api.service.UserService
import com.ediperturk.customer.manager.resource.Resources
import com.ediperturk.customer.util.CoroutineTestRule
import com.ediperturk.customer.util.TestUtils
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.stub
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import retrofit2.Response
import javax.inject.Inject
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [28], application = HiltTestApplication::class)
class PostListViewModelUniTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Inject
    lateinit var resources: Resources

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val userDao = mock<UserDao>()
    private val userService = mock<UserService>()

    private lateinit var repository: UserRepositoryImpl

    @Before
    fun setUp() {
        hiltRule.inject()

        repository = UserRepositoryImpl(
            resources,
            userDao,
            userService,
            coroutinesTestRule.testDispatcherProvider
        )
    }

    @Test
    fun `flow emits posts successfully`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val postList = mutableListOf<Post>()
        var exception: Exception? = null

        assertEquals(
            true,
            postList.isEmpty(),
            "Post list should be empty!"
        )

        assertNull(
            exception,
            "Exception should be null!"
        )

        userService.stub {
            onBlocking { getUserPosts(1) } doReturn Response.success(
                Json.decodeFromString<List<Post>>(
                    TestUtils.getJson("posts.json")
                ).filter { it.userId == 1 }
            )
        }

        val flow = repository.getPosts(1)

        flow.collect { result ->
            when (result) {
                is Result.Success -> postList.addAll(result.data)
                is Result.Error -> exception = result.exception
            }
        }

        assertEquals(
            true,
            postList.isNotEmpty(),
            "Post list should not be empty!"
        )

        assertEquals(
            10,
            postList.size,
            "Post list size is not correct!"
        )

        assertNull(
            exception,
            "Exception should be null!"
        )
    }

    @Test
    fun `should retry with error`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val postList = mutableListOf<Post>()
        var exception: Exception? = null

        assertEquals(
            true,
            postList.isEmpty(),
            "Post list should be empty!"
        )

        assertNull(
            exception,
            "Exception should be null!"
        )

        userService.stub {
            onBlocking { getUserPosts(1) } doAnswer {
                throw ApiException(
                    resources.getDefaultNetworkExceptionTitle(),
                    resources.getDefaultNetworkExceptionMessage()
                )
            }
        }

        val flow = repository.getPosts(1)

        flow.collect { result ->
            when (result) {
                is Result.Success -> postList.addAll(result.data)
                is Result.Error -> exception = result.exception
            }
        }

        assertEquals(
            true,
            postList.isEmpty(),
            "Post list should be empty!"
        )

        assertNotNull(
            exception,
            "Exception should not be null!"
        )

        assertTrue(
            exception is ApiException,
            "Exception should be instance of ApiException!"
        )

    }

    @After
    fun tearDown() {

    }
}
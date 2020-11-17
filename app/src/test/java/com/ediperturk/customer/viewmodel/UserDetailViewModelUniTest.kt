package com.ediperturk.customer.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ediperturk.customer.util.CoroutineTestRule
import com.ediperturk.customer.api.base.Result
import com.ediperturk.customer.api.data.entity.User
import com.ediperturk.customer.api.exception.ApiException
import com.ediperturk.customer.api.local.UserDao
import com.ediperturk.customer.api.repository.user.UserRepositoryImpl
import com.ediperturk.customer.api.service.UserService
import com.ediperturk.customer.manager.resource.Resources
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
import java.io.File
import java.io.FileNotFoundException
import javax.inject.Inject
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@Config(sdk = [28], application = HiltTestApplication::class)
class UserDetailViewModelUniTest {

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
    fun `flow emits user successfully`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        var user: User? = null
        var exception: Exception? = null

        assertNull(
            user,
            "User should be null!"
        )

        assertNull(
            exception,
            "Exception should be null!"
        )

        userService.stub {
            onBlocking { getUser(1) } doReturn Response.success(
                Json.decodeFromString<List<User>>(
                    TestUtils.getJson("users.json")
                ).filter { it.id == 1 }
            )
        }

        val flow = repository.getUser(1)

        flow.collect { result ->
            when (result) {
                is Result.Success -> user = result.data
                is Result.Error -> exception = result.exception
            }
        }

        assertNotNull(
            user,
            "User should not be null!"
        )

        assertNull(
            exception,
            "Exception should be null!"
        )
    }

    @Test
    fun `should retry with error`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        var user: User? = null
        var exception: Exception? = null

        assertNull(
            user,
            "User should be null!"
        )

        assertNull(
            exception,
            "Exception should be null!"
        )

        userService.stub {
            onBlocking { getUser(1) } doAnswer {
                throw ApiException(
                    resources.getDefaultNetworkExceptionTitle(),
                    resources.getDefaultNetworkExceptionMessage()
                )
            }
        }

        val flow = repository.getUser(1)

        flow.collect { result ->
            when (result) {
                is Result.Success -> user = result.data
                is Result.Error -> exception = result.exception
            }
        }

        assertNull(
            user,
            "User should be null!"
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
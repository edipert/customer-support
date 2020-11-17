package com.ediperturk.customer

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.ediperturk.customer.api.local.UserDao
import com.ediperturk.customer.ui.postdetail.PostDetailFragment
import com.ediperturk.customer.ui.postdetail.PostDetailFragmentArgs
import com.ediperturk.customer.util.RecyclerViewItemCountAssertion
import com.ediperturk.customer.util.Utils
import com.ediperturk.customer.util.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@MediumTest
class PostDetailFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var userDao: UserDao

    @Before
    fun init() {
        hiltRule.inject()

        userDao.insertUsersForTest(
            Json.decodeFromString(
                Utils.getJsonFromAssets("users.json")
            )
        )

        userDao.insertPostsForTest(
            Json.decodeFromString(
                Utils.getJsonFromAssets("posts.json")
            )
        )
    }

    @Test
    fun show_comments_on_screen() {
        val bundle = PostDetailFragmentArgs.Builder(1).build().toBundle()
        launchFragmentInHiltContainer<PostDetailFragment>(bundle)

        Thread.sleep(2000)

        onView(withId(R.id.fullNameTextView)).check(matches(withText("Leanne Graham")))

        onView(withId(R.id.commentsRecyclerView)).check(matches(isDisplayed()))

        onView(withId(R.id.commentsRecyclerView)).check(RecyclerViewItemCountAssertion(5))

        Thread.sleep(2000)
    }
}
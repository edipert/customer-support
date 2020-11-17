package com.ediperturk.customer

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.ediperturk.customer.api.local.UserDao
import com.ediperturk.customer.ui.postlist.PostListAdapter
import com.ediperturk.customer.ui.postlist.PostListFragment
import com.ediperturk.customer.ui.postlist.PostListFragmentArgs
import com.ediperturk.customer.util.*
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
class PostListFragmentTest {

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
    }

    @Test
    fun show_post_on_screen() {
        val bundle = PostListFragmentArgs.Builder(1).build().toBundle()
        launchFragmentInHiltContainer<PostListFragment>(bundle)

        Thread.sleep(2000)

        onView(withId(R.id.fullNameTextView)).check(matches(withText("Leanne Graham")))

        onView(withId(R.id.postsRecyclerView)).check(matches(isDisplayed()))

        onView(withId(R.id.postsRecyclerView)).check(RecyclerViewItemCountAssertion(10))

        Thread.sleep(2000)

        onView(withId(R.id.postsRecyclerView))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<PostListAdapter.PostViewHolder>(
                        0,
                        clickItemWithId(R.id.seeCommentsTextView)
                    )
            )

        Thread.sleep(2000)

        onView(
            withRecyclerView(R.id.postsRecyclerView)
            .atPositionOnView(0, R.id.seeCommentsTextView))
            .check(matches(withText(R.string.text_hide_comments)))

        Thread.sleep(1000)

        onView(withId(R.id.postsRecyclerView))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<PostListAdapter.PostViewHolder>(
                        0,
                        clickItemWithId(R.id.seeCommentsTextView)
                    )
            )

        Thread.sleep(1000)

        onView(
            withRecyclerView(R.id.postsRecyclerView)
                .atPositionOnView(0, R.id.seeCommentsTextView))
            .check(matches(withText(R.string.text_see_comments)))

        Thread.sleep(2000)
    }
}
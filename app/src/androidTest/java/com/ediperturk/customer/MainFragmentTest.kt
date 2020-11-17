package com.ediperturk.customer

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.ediperturk.customer.ui.main.MainFragment
import com.ediperturk.customer.ui.main.UserListAdapter
import com.ediperturk.customer.util.RecyclerViewItemCountAssertion
import com.ediperturk.customer.util.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@MediumTest
class MainFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun list_users_on_home() {
        launchFragmentInHiltContainer<MainFragment>()

        Thread.sleep(2000)

        onView(withId(R.id.usersRecyclerView)).check(matches(isDisplayed()))

        onView(withId(R.id.usersRecyclerView)).check(RecyclerViewItemCountAssertion(10))

        /*onView(withId(R.id.usersRecyclerView)).perform(
            RecyclerViewActions.scrollToPosition<UserListAdapter.UserViewHolder>(
                10
            )
        )*/


        Thread.sleep(2000)

        onView(withId(R.id.usersRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<UserListAdapter.UserViewHolder>(
                2,
                click()
            )
        )

        Thread.sleep(2000)
    }
}
package com.ediperturk.customer

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.ediperturk.customer.api.local.UserDao
import com.ediperturk.customer.ui.userdetail.UserDetailFragment
import com.ediperturk.customer.ui.userdetail.UserDetailFragmentArgs
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
class UserDetailFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var userDao: UserDao

    @Before
    fun init() {
        hiltRule.inject()

        // Insert user list from json
        userDao.insertUsersForTest(
            Json.decodeFromString(
                Utils.getJsonFromAssets("users.json")
            )
        )
    }

    @Test
    fun show_user_detail_on_screen() {
        val user = userDao.getUser(1)
        val bundle = UserDetailFragmentArgs.Builder(user.name, 1).build().toBundle()

        launchFragmentInHiltContainer<UserDetailFragment>(bundle)

        Thread.sleep(2000)

        onView(withId(R.id.fullNameTextView)).check(matches(withText("Leanne Graham")))

        Thread.sleep(2000)
    }
}
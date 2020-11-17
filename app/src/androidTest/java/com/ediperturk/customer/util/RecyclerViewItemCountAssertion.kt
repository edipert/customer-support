package com.ediperturk.customer.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import org.hamcrest.Matchers

class RecyclerViewItemCountAssertion(
    private val count: Int
) : ViewAssertion {

    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        if (view is RecyclerView) {
            assertThat(view.adapter?.itemCount, Matchers.`is`(count))
        }
    }
}
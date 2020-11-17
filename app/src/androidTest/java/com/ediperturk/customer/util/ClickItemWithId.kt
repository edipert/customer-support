package com.ediperturk.customer.util

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import org.hamcrest.Matcher

class ClickItemWithId(
    private val id: Int
) : ViewAction {
    override fun getConstraints(): Matcher<View>? {
        return null
    }

    override fun getDescription(): String {
        return "Click on a child view with specified id."
    }

    override fun perform(uiController: UiController, view: View) {
        val v = view.findViewById(id) as View
        v.performClick()
    }
}
package com.ediperturk.customer.util

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class RecyclerViewMatcher(
    private val recyclerViewId: Int
) {
    fun atPosition(position: Int): Matcher<View?>? {
        return atPositionOnView(position, -1)
    }

    fun atPositionOnView(position: Int, targetViewId: Int): Matcher<View?>? {
        return object : TypeSafeMatcher<View?>() {
            lateinit var resources: Resources
            var childView: View? = null

            override fun describeTo(description: Description) {
                var idDescription = recyclerViewId.toString()
                if (::resources.isInitialized) {
                    idDescription = try {
                        resources.getResourceName(recyclerViewId)
                    } catch (_: Resources.NotFoundException) {
                        String.format(
                            "%s (resource name not found)",
                            Integer.valueOf(recyclerViewId)
                        )
                    }
                }
                description.appendText("with id: $idDescription")
            }

            override fun matchesSafely(view: View?): Boolean {
                if (view == null) return false

                resources = view.resources
                if (childView == null) {
                    val recyclerView =
                        view.rootView?.findViewById<View>(recyclerViewId) as RecyclerView
                    childView = if (recyclerView.id == recyclerViewId) {
                        recyclerView.findViewHolderForAdapterPosition(position)?.itemView
                    } else {
                        return false
                    }
                }
                return if (targetViewId == -1) {
                    view === childView
                } else {
                    val targetView = childView?.findViewById<View>(targetViewId)
                    view === targetView
                }
            }
        }
    }
}
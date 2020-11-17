package com.ediperturk.customer.navigation

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import androidx.annotation.CallSuper
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.ediperturk.customer.R

@Navigator.Name(PopupNavigator.NAVIGATOR_NAME)
class PopupNavigator(
    private val context: Context,
    private val manager: FragmentManager
) : Navigator<PopupNavigator.Destination>() {

    companion object {
        const val DIALOG_TAG = "androidx-nav-fragment:navigator:alert:"
        const val NAVIGATOR_NAME = "popup"
    }

    private var mDialogCount = 0

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Extras?
    ): NavDestination {
        val className = destination.getClassName()

        val fragment = manager.fragmentFactory.instantiate(
            context.classLoader,
            className
        )

        val tag = DIALOG_TAG + mDialogCount++

        if (fragment is DialogFragment) {
            fragment.arguments = args
            fragment.show(manager, tag)
        } else {
            throw IllegalStateException("Not instance of DialogFragment")
        }

        return destination
    }

    override fun createDestination() = Destination(this)

    override fun popBackStack(): Boolean {
        val fragment = manager.findFragmentByTag(DIALOG_TAG + --mDialogCount)
        return if (fragment != null && fragment is DialogFragment) {
            fragment.dismiss()
            true
        } else {
            false
        }
    }

    @NavDestination.ClassType(DialogFragment::class)
    inner class Destination(navigator: Navigator<out NavDestination>) : NavDestination(navigator) {

        private lateinit var mClassName: String

        @CallSuper
        override fun onInflate(
            context: Context,
            attrs: AttributeSet
        ) {
            super.onInflate(context, attrs)
            val a = context.resources.obtainAttributes(attrs, R.styleable.PopupNavigator)
            val className = a.getString(R.styleable.PopupNavigator_android_name)
            className?.let { setClassName(it) }
            a.recycle()
        }

        private fun setClassName(name: String) {
            this.mClassName = name
        }

        fun getClassName(): String {
            return mClassName
        }
    }
}
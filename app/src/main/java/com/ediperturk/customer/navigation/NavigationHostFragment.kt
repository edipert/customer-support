package com.ediperturk.customer.navigation

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.Navigation
import com.ediperturk.customer.R

class NavigationHostFragment : Fragment(), NavHost {

    private var mGraphId: Int = 0
    private var mDefaultNavHost: Boolean = true

    private lateinit var navigationController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigationController = NavController(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val containerView = FragmentContainerView(inflater.context)
        containerView.id = id
        return containerView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Navigation.setViewNavController(view, navigationController)

        navigationController.navigatorProvider.addNavigator(
            ScreenNavigator(
                requireContext(),
                childFragmentManager,
                id
            )
        )

        navigationController.navigatorProvider.addNavigator(
            PopupNavigator(
                requireContext(),
                childFragmentManager
            )
        )

        if (mGraphId != 0)
            navigationController.setGraph(mGraphId)
    }

    @CallSuper
    override fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)

        val navHost = context.obtainStyledAttributes(attrs, R.styleable.NavHost)
        val graphId = navHost.getResourceId(R.styleable.NavHost_navGraph, 0)
        if (graphId != 0) {
            mGraphId = graphId
        }

        navHost.recycle()
        val a = context.obtainStyledAttributes(attrs, R.styleable.NavHostFragment)
        val defaultHost = a.getBoolean(R.styleable.NavHostFragment_defaultNavHost, false)
        if (defaultHost) {
            mDefaultNavHost = true
        }

        a.recycle()
    }

    override fun getNavController() = navigationController
}
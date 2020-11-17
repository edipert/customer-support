package com.ediperturk.customer.di

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.ediperturk.customer.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object NavigationModule {

    @Provides
    @ActivityScoped
    fun provideNavController(activity: Activity): NavController {
        val navController =
            TestNavHostController(ApplicationProvider.getApplicationContext<Activity>())
        navController.setGraph(R.navigation.navigation)
        return navController
    }
}
package com.ediperturk.customer.di

import android.app.Activity
import androidx.navigation.NavController
import com.ediperturk.customer.common.findNavController
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
        return activity.findNavController()
    }
}
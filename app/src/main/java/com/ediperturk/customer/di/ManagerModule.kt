package com.ediperturk.customer.di

import com.ediperturk.customer.manager.connection.Connectivity
import com.ediperturk.customer.manager.connection.ConnectivityManager
import com.ediperturk.customer.manager.resource.ResourceManager
import com.ediperturk.customer.manager.resource.Resources
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class ManagerModule {

    @Binds
    @Singleton
    abstract fun bindConnectivityManager(connectivityManager: ConnectivityManager): Connectivity

    @Binds
    @Singleton
    abstract fun bindResourceManager(resourceManager: ResourceManager): Resources
}
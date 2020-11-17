package com.ediperturk.customer.di

import com.ediperturk.customer.common.DefaultDispatcherProvider
import com.ediperturk.customer.common.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DispatcherModule {

    @Provides
    @Singleton
    fun provideDispatcher(): DispatcherProvider {
        return DefaultDispatcherProvider()
    }
}
package com.ediperturk.customer.di

import com.ediperturk.customer.api.repository.user.UserRepository
import com.ediperturk.customer.api.repository.user.UserRepositoryImpl
import com.ediperturk.customer.api.service.UserService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class DataModule {

    companion object {

        @Provides
        @Singleton
        fun provideUserService(retrofit: Retrofit): UserService {
            return retrofit.create(UserService::class.java)
        }
    }

    @Binds
    @Singleton
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}
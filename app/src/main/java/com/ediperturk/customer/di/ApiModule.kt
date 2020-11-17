package com.ediperturk.customer.di

import com.ediperturk.customer.BuildConfig
import com.ediperturk.customer.api.interceptor.ConnectionInterceptor
import com.ediperturk.customer.manager.connection.Connectivity
import com.ediperturk.customer.manager.resource.Resources
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideConnectionInterceptor(
        connectivity: Connectivity,
        resources: Resources
    ): ConnectionInterceptor {
        return ConnectionInterceptor(connectivity, resources)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(connectionInterceptor: ConnectionInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(connectionInterceptor)
            .build()
    }

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(Json {
                ignoreUnknownKeys = true
                encodeDefaults = false
                coerceInputValues = true
                classDiscriminator = "media_type"
            }.asConverterFactory(contentType))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }
}
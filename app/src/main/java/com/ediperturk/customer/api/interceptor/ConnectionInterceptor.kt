package com.ediperturk.customer.api.interceptor

import com.ediperturk.customer.api.exception.NoConnectivityException
import com.ediperturk.customer.manager.connection.Connectivity
import com.ediperturk.customer.manager.resource.Resources
import okhttp3.Interceptor
import okhttp3.Response

class ConnectionInterceptor(
    private val connectivity: Connectivity,
    private val resources: Resources
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!connectivity.isConnected()) {
            throw NoConnectivityException(
                resources.getDefaultNetworkExceptionTitle(),
                resources.getDefaultNetworkExceptionMessage()
            )
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}
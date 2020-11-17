package com.ediperturk.customer.api.exception

import java.io.IOException

class NoConnectivityException(
    private val title: String? = null,
    private val errorMessage: String
) : IOException(errorMessage) {
    fun title() = title

    fun message() = message ?: errorMessage
}
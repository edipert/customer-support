package com.ediperturk.customer.api.base

import com.ediperturk.customer.api.exception.ApiException

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: ApiException) : Result<Nothing>()
}
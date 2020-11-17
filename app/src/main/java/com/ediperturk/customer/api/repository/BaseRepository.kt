package com.ediperturk.customer.api.repository

import androidx.annotation.VisibleForTesting
import com.ediperturk.customer.api.base.Result
import com.ediperturk.customer.api.exception.ApiException
import com.ediperturk.customer.common.DispatcherProvider
import com.ediperturk.customer.manager.resource.Resources
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

abstract class BaseRepository {

    abstract val resources: Resources

    abstract var dispatcher: DispatcherProvider

    private suspend fun <T : Any> makeRequest(call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return Result.Success(body)
                }
            }

            return Result.Error(
                ApiException(
                    resources.getDefaultErrorTitle(),
                    "${response.code()} ${response.message()}"
                )
            )
        } catch (exception: Exception) {
            Result.Error(
                ApiException(
                    resources.getDefaultErrorTitle(),
                    exception.message ?: resources.getDefaultErrorMessage()
                )
            )
        }
    }

    fun <T : Any, A : Any> performGetOperation(
        databaseQuery: () -> T,
        networkCall: suspend () -> Response<A>,
        saveCallResult: suspend (A) -> Unit
    ) = flow {
        when (val result = makeRequest { networkCall.invoke() }) {
            is Result.Success -> {
                saveCallResult(result.data)
                emit(Result.Success(databaseQuery.invoke()))
            }
            is Result.Error -> {
                emit(Result.Success(databaseQuery.invoke()))
                emit(Result.Error(result.exception))
            }
        }
    }.flowOn(dispatcher.io())

    @VisibleForTesting
    fun <T : Any> performGetOperation(
        networkCall: suspend () -> Response<T>,
    ) = flow {
        when (val result = makeRequest { networkCall.invoke() }) {
            is Result.Success -> {
                emit(Result.Success(result.data))
            }
            is Result.Error -> {
                emit(Result.Error(result.exception))
            }
        }
    }.flowOn(dispatcher.io())
}
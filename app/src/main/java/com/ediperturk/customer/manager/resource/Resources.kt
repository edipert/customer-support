package com.ediperturk.customer.manager.resource

import androidx.annotation.StringRes

interface Resources {

    fun getString(@StringRes id: Int): String

    fun getString(@StringRes resId: Int, vararg args: Any): String

    fun getDefaultErrorTitle(): String

    fun getDefaultErrorMessage(): String

    fun getDefaultNetworkExceptionTitle(): String

    fun getDefaultNetworkExceptionMessage(): String
}

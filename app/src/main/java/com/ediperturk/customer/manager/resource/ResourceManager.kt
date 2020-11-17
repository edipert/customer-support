package com.ediperturk.customer.manager.resource

import android.content.Context
import androidx.annotation.StringRes
import com.ediperturk.customer.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceManager @Inject constructor(
    @ApplicationContext private val context: Context
) : Resources {

    override fun getString(@StringRes id: Int): String {
        return context.resources.getString(id)
    }

    override fun getString(@StringRes resId: Int, vararg args: Any): String {
        return context.getString(resId, *args)
    }

    override fun getDefaultErrorTitle(): String {
        return context.getString(R.string.error_api_default_exception_title)
    }

    override fun getDefaultErrorMessage(): String {
        return context.getString(R.string.error_api_default_exception_text)
    }

    override fun getDefaultNetworkExceptionTitle(): String {
        return context.getString(R.string.error_no_connection)
    }

    override fun getDefaultNetworkExceptionMessage(): String {
        return context.getString(R.string.error_suggest_for_network_access)
    }
}
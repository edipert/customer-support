package com.ediperturk.customer.manager.connection

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityManager @Inject constructor(
    @ApplicationContext context: Context
) : Connectivity {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    override fun isConnected(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkActiveNetwork()
        } else {
            checkActiveNetworkObsolete()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkActiveNetwork(): Boolean {
        val network = connectivityManager.activeNetwork
        network?.let {
            connectivityManager.getNetworkCapabilities(it)?.let { nc ->
                return nc.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            }
        }

        return false
    }

    @Deprecated("For devices older than Marshmallow")
    @Suppress("DEPRECATION")
    private fun checkActiveNetworkObsolete(): Boolean {
        connectivityManager.activeNetworkInfo?.let {
            return it.isConnectedOrConnecting
        }
        return false
    }
}
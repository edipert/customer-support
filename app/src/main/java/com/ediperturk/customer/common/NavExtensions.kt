package com.ediperturk.customer.common

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.ediperturk.customer.R
import com.ediperturk.customer.navigation.NavigationHostFragment
import com.ediperturk.customer.navigation.annotation.NavigationHost
import com.ediperturk.customer.ui.dialog.alert.AlertDialogArgs
import com.ediperturk.customer.ui.dialog.progress.ProgressDialogArgs

fun Activity.findNavController(): NavController {
    val navigationHost = this.javaClass.getAnnotation(NavigationHost::class.java)
        ?: throw Exception("Activity must have navigation host!")

    if (this is AppCompatActivity) {
        val navHostFragment = supportFragmentManager.findFragmentById(navigationHost.hostViewId) as NavigationHostFragment
        return navHostFragment.navController
    }

    throw Exception("Activity must extended from AppCompatActivity!")
}

fun NavController.alert(
    title: String?,
    message: String,
    cancellable: Boolean = false,
    outsideTouch: Boolean = false,
) {
    graph.getAction(R.id.alert)?.let {
        navigate(
            it.destinationId,
            AlertDialogArgs.Builder(title, message)
                .setCancellable(cancellable)
                .setOutsideTouch(outsideTouch)
                .build().toBundle()
        )
    }
}

fun NavController.progress(
    arguments: ProgressDialogArgs? = null
) {
    if (currentDestination?.id != R.id.progressDialog) {
        graph.getAction(R.id.progress)?.let {
            arguments?.let { args ->
                navigate(it.destinationId, args.toBundle())
            } ?: run {
                navigate(it.destinationId)
            }
        }
    }
}

fun NavController.dismiss() {
    if (currentDestination?.id == R.id.progressDialog || currentDestination?.id == R.id.alertDialog) {
        popBackStack()
    }
}
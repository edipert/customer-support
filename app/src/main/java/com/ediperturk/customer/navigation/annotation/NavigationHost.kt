package com.ediperturk.customer.navigation.annotation

import androidx.annotation.IdRes

@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class NavigationHost(@IdRes val hostViewId: Int)
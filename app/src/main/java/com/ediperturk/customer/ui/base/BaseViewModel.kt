package com.ediperturk.customer.ui.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.ediperturk.customer.api.exception.ApiException
import com.ediperturk.customer.manager.resource.Resources
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {

    }

    override fun onCleared() {
        super.onCleared()
    }
}
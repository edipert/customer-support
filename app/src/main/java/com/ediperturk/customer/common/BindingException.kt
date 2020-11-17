package com.ediperturk.customer.common

import android.view.View
import androidx.databinding.BindingAdapter

object BindingExtensions

@set:BindingAdapter("isVisible")
inline var View.visible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }
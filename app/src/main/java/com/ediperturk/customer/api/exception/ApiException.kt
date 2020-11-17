package com.ediperturk.customer.api.exception

class ApiException(
    val title: String?,
    override val message: String
) : Exception()
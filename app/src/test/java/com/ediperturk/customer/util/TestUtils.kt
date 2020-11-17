package com.ediperturk.customer.util

import java.io.File
import java.io.FileNotFoundException

object TestUtils {

    fun getJson(path: String): String {
        val uri = this.javaClass.classLoader?.getResource(path) ?: throw FileNotFoundException()
        val file = File(uri.path)
        return String(file.readBytes())
    }
}
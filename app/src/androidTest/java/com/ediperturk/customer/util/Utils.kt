package com.ediperturk.customer.util

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.charset.Charset

object Utils {
    fun getJsonFromAssets(fileName: String): String {
        return try {
            val inputStream = ApplicationProvider.getApplicationContext<Context>().assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charset.forName("UTF-8"))
        } catch (e: IOException) {
            e.printStackTrace()
            throw e
        }
    }

    private fun getJson(path: String): String {
        val uri = this.javaClass.classLoader?.getResource(path) ?: throw FileNotFoundException()
        val file = File(uri.path)
        return String(file.readBytes())
    }
}
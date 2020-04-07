package com.speech.service

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.speech.BuildConfig
import java.lang.Exception

object Logger {

    private val crashlytics = FirebaseCrashlytics.getInstance()

    fun log(exception: Exception) {
        if (!BuildConfig.DEBUG){
            exception.message?.let { message -> crashlytics.log(message) }
            crashlytics.recordException(exception)
        } else {
            exception.printStackTrace()
        }
    }
}
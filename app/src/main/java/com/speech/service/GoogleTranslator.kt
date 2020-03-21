package com.speech.service

import android.content.Context
import android.os.StrictMode
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import com.speech.R
import javax.inject.Inject

class GoogleTranslator @Inject constructor(private val context: Context) {

    private var translate: Translate?
    private var translateServiceError: String = "translate service error"

    init {
        translate = getTranslateService()
    }

    fun getTranslatedText(origText: String, toLanguage: String): String {
        translate ?: return translateServiceError

        val translation = translate!!.translate(
            origText,
            Translate.TranslateOption.targetLanguage(toLanguage),
            Translate.TranslateOption.model("base")
        )
        return translation.translatedText
    }

    private fun getTranslateService(): Translate? {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            context.resources.openRawResource(R.raw.credentials).use { credentialsFile ->
                val myCredentials = GoogleCredentials.fromStream(credentialsFile)
                val translateOptions = TranslateOptions.newBuilder().setCredentials(myCredentials).build()
                return translateOptions.service
            }
        } catch (e: Exception){
            e.stackTrace
            translateServiceError = e.message.toString()
        }
        return null
    }
}
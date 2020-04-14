package com.speech.service.translation

import android.content.Context
import android.os.StrictMode
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import com.speech.R
import com.speech.service.Logger
import javax.inject.Inject

class GoogleTranslator @Inject constructor(private val context: Context) : ITranslator {

    private var translate: Translate?
    private var translateServiceError: String = "translate service error"

    init {
        translate = getTranslateService()
    }

    override fun getTranslatedText(text: String, targetLanguage: String): String {
        translate ?: return translateServiceError

        val translation = translate!!.translate(
            text,
            Translate.TranslateOption.targetLanguage(targetLanguage),
            Translate.TranslateOption.model("base")
        )
        return translation.translatedText
    }

    private fun getTranslateService(): Translate? {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            context.resources.openRawResource(R.raw.credentials_2).use { credentialsFile ->
                val myCredentials = GoogleCredentials.fromStream(credentialsFile)
                val translateOptions = TranslateOptions.newBuilder().setCredentials(myCredentials).build()
                return translateOptions.service
            }
        } catch (e: Exception) {
            Logger.log(e)
            translateServiceError = e.message.toString()
        }
        return null
    }
}
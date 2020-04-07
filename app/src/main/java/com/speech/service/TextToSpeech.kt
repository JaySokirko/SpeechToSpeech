package com.speech.service

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.*
import javax.inject.Inject

class TextToSpeech @Inject constructor(context: Context) {

    private lateinit var androidTextToSpeech: TextToSpeech

    init {
        try {
            androidTextToSpeech = TextToSpeech(context, TextToSpeech.OnInitListener { status ->
                if (status != TextToSpeech.ERROR) {
                    androidTextToSpeech.language = Locale.UK;
                }
            })
        } catch (exception: Exception) {
            Logger.log(exception)
        }
    }

    fun speak(language: String, text: String) {
        androidTextToSpeech.language = Locale(language)
        try {
            androidTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        } catch (exception: java.lang.Exception) {
            Logger.log(exception)
        }
    }

    fun shutdown() {
        androidTextToSpeech.apply { stop(); shutdown() }
    }
}
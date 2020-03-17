package com.speech.util

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.*

class TextToSpeech(context: Context) {

    private lateinit var androidTextToSpeech: TextToSpeech

    init {
        androidTextToSpeech = TextToSpeech(context,
            TextToSpeech.OnInitListener { status ->
                if (status != TextToSpeech.ERROR) {
                    androidTextToSpeech.language = Locale.UK;
                }
            }
        )
    }

    fun setLocale(locale: Locale){
        androidTextToSpeech.language = locale
    }

    fun speak(text: String){
        androidTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

    fun shutdown(){
        androidTextToSpeech.stop()
        androidTextToSpeech.shutdown()
    }
}
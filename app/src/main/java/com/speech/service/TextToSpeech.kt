package com.speech.service

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.*
import javax.inject.Inject

class TextToSpeech @Inject constructor(context: Context) {

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

    fun speak(language: String, text: String){
        androidTextToSpeech.language = Locale(language)
        androidTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

    fun shutdown(){
        androidTextToSpeech.apply { stop(); shutdown() }
    }
}
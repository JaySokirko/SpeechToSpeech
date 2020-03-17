package com.speech.viewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.databinding.BaseObservable
import com.speech.network.GoogleTranslator
import com.speech.util.SPEECH_REQUEST
import com.speech.util.TextToSpeech
import java.util.*


class Translation(context: Context, private val activity: Activity) : BaseObservable() {

    private var googleTranslator: GoogleTranslator = GoogleTranslator(context)
    private var textToSpeech: TextToSpeech = TextToSpeech(context)

    var translatedText: String? = null
        set(value) {
            field = value
            notifyChange()
        }

    fun onSpeechButtonClick() {
        startSpeechIntent()
    }

    fun onActivityResult(requestCode: Int, data: Intent) {
        if (requestCode == SPEECH_REQUEST) {
            val result: ArrayList<String>? = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            result ?: return
            translatedText = googleTranslator.getTranslatedText(result[0])
            textToSpeech.apply {
                setLocale(Locale("ru", "RU"))
                speak(translatedText.toString())
            }
        }
    }

    private fun startSpeechIntent() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en")
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "say something, bro")
        activity.startActivityForResult(intent, SPEECH_REQUEST)
    }
}
package com.speech.viewModel

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.speech.network.GoogleTranslator
import com.speech.util.REQUEST_SPEECH_NATIVE
import com.speech.util.REQUEST_SPEECH_RESPONDENT
import com.speech.util.TextToSpeech
import java.util.*


class Translation(context: Context) : BaseObservable() {

    private var textToSpeech: TextToSpeech = TextToSpeech(context)
    private val googleTranslator: GoogleTranslator = GoogleTranslator(context)
    val eventObserver: MutableLiveData<Event> = MutableLiveData()

    var translatedText: String? = null
        set(value) {
            field = value
            notifyChange()
        }

    fun onSpeechButtonClick() {
        eventObserver.postValue(Event.START_SPEECH_INTENT)
    }

    fun onActivityResult(requestCode: Int, data: Intent) {
        if (requestCode == REQUEST_SPEECH_NATIVE) {
            val result: ArrayList<String>? = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            result ?: return
            eventObserver.postValue(Event.SPEECH_NATIVE_FINISH)
//            translatedText = googleTranslator.getTranslatedText(result[0])
//            textToSpeech.apply {
//                setLocale(Locale("ru", "RU"))
//                speak(translatedText.toString())
//            }
        }
        if (requestCode == REQUEST_SPEECH_RESPONDENT){
            val result: ArrayList<String>? = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            result ?: return
            eventObserver.postValue(Event.SPEECH_RESPONDENT_FINISH)
        }
    }

    fun onDestroy(){
        textToSpeech.shutdown()
    }
}
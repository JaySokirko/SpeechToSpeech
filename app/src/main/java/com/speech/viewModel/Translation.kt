package com.speech.viewModel

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.databinding.ObservableField
import com.speech.di.DaggerTranslationComponent
import com.speech.di.TranslationComponent
import com.speech.di.TranslationModule
import com.speech.service.GoogleTranslator
import com.speech.util.EventObserver
import com.speech.util.REQUEST_SPEECH_FOREIGN
import com.speech.util.REQUEST_SPEECH_NATIVE
import com.speech.util.TextToSpeech
import java.util.*

class Translation (context: Context) {

    var nativeTranslate: ObservableField<String> = ObservableField()
    var foreignTranslate: ObservableField<String> = ObservableField()

    private val dagger: TranslationComponent = DaggerTranslationComponent
        .builder()
        .translationModule(TranslationModule(context))
        .build()

    private val textToSpeech: TextToSpeech = dagger.getTextToSpeech()
    private val googleTranslator: GoogleTranslator = dagger.getGoogleTranslator()

    fun onActivityResult(requestCode: Int, data: Intent) {
        if (requestCode == REQUEST_SPEECH_NATIVE || requestCode == REQUEST_SPEECH_FOREIGN) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            result ?: return

            if (requestCode == REQUEST_SPEECH_NATIVE) {
                EventObserver.commonObserver.postValue(EventObserver.Event.SPEECH_NATIVE_INTENT_FINISH)
                foreignTranslate.set(googleTranslator.getTranslatedText(result[0], "en"))
                textToSpeech.speak(Locale.UK, foreignTranslate.get().toString())
            }
            if (requestCode == REQUEST_SPEECH_FOREIGN) {
                EventObserver.commonObserver.postValue(EventObserver.Event.SPEECH_FOREIGN_INTENT_FINISH)
                nativeTranslate.set(googleTranslator.getTranslatedText(result[0], "ru"))
                textToSpeech.speak(Locale("ru"), nativeTranslate.get().toString())
            }
        }
    }

    fun onDestroy() {
        textToSpeech.shutdown()
    }

    companion object {
        lateinit var translation: Translation
        fun getInstance(context: Context): Translation {
            if (translation == null) {
                translation = Translation(context)
            }
            return translation
        }
    }
}

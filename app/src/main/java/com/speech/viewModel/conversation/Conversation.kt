package com.speech.viewModel.conversation

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.databinding.ObservableField
import com.speech.R
import com.speech.di.DaggerTranslationComponent
import com.speech.di.TranslationComponent
import com.speech.di.TranslationModule
import com.speech.service.GoogleTranslator
import com.speech.service.TextToSpeech
import com.speech.util.EventObserver
import com.speech.util.REQUEST_SPEAK_FOREIGN
import com.speech.util.REQUEST_SPEAK_NATIVE

open class Conversation (private val context: Context) {

    var nativeTranslate: ObservableField<String> = ObservableField()
    var foreignTranslate: ObservableField<String> = ObservableField()

    private val dagger: TranslationComponent = DaggerTranslationComponent
        .builder()
        .translationModule(TranslationModule(context))
        .build()

    val textToSpeechService: TextToSpeech = dagger.getTextToSpeech()
    private val googleTranslator: GoogleTranslator = dagger.getGoogleTranslator()

    fun onActivityResult(requestCode: Int, data: Intent) {
        if (requestCode == REQUEST_SPEAK_NATIVE || requestCode == REQUEST_SPEAK_FOREIGN) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            result ?: return

            if (requestCode == REQUEST_SPEAK_NATIVE) {
                EventObserver.commonObserver.postValue(EventObserver.Event.SPEAK_NATIVE_INTENT_FINISH)
                foreignTranslate.set(googleTranslator.getTranslatedText(result[0], "en"))
                textToSpeechService.speak(context.getString(R.string.en), foreignTranslate.get().toString())
            }
            if (requestCode == REQUEST_SPEAK_FOREIGN) {
                EventObserver.commonObserver.postValue(EventObserver.Event.SPEAK_FOREIGN_INTENT_FINISH)
                nativeTranslate.set(googleTranslator.getTranslatedText(result[0], "ru"))
                textToSpeechService.speak(context.getString(R.string.ru), nativeTranslate.get().toString())
            }
        }
    }

    fun onStop() {
        textToSpeechService.shutdown()
    }

    companion object {
        var conversation: Conversation? = null
        fun getInstance(context: Context): Conversation? {
            if (conversation == null) {
                conversation = Conversation(context)
            }
            return conversation
        }
    }
}

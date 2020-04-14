package com.speech.viewModel.conversation

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.speech.R
import com.speech.util.EventObserver
import com.speech.util.REQUEST_SPEAK_FOREIGN
import com.speech.util.REQUEST_SPEAK_NATIVE

open class Conversation(context: Context): ConversationBase(context) {

    var nativeTranslate: ObservableField<String> = ObservableField()
    var foreignTranslate: ObservableField<String> = ObservableField()
    var translatedTextNullOrEmpty: ObservableBoolean = ObservableBoolean()

    init {
        translatedTextNullOrEmpty.set(true)
        nativeLanguage.set(context.getString(R.string.ru))
        foreignLanguage.set(context.getString(R.string.en))
        nativeLanguageHint = context.getString(R.string.speech_intent_hint_ru)
        foreignLanguageHint = context.getString(R.string.speech_intent_hint_en)
    }

    fun onActivityResult(requestCode: Int, data: Intent) {
        if (requestCode == REQUEST_SPEAK_NATIVE || requestCode == REQUEST_SPEAK_FOREIGN) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            result ?: return

            if (result[0].isNotBlank()) {
                translatedTextNullOrEmpty.set(false)
            }

            if (requestCode == REQUEST_SPEAK_NATIVE) {
                EventObserver.commonObserver.postValue(EventObserver.Event.SPEAK_NATIVE_INTENT_FINISH)

                foreignTranslate.set(translator.getTranslatedText(result[0],
                    foreignLanguage.get().toString()))

                textToSpeechService.speak(foreignLanguage.get().toString(),
                    foreignTranslate.get().toString())
            }

            if (requestCode == REQUEST_SPEAK_FOREIGN) {
                EventObserver.commonObserver.postValue(EventObserver.Event.SPEAK_FOREIGN_INTENT_FINISH)

                nativeTranslate.set(translator.getTranslatedText(result[0],
                    nativeLanguage.get().toString()))

                textToSpeechService.speak(nativeLanguage.get().toString(),
                    nativeTranslate.get().toString())
            }
        }
    }

    companion object {
        var conversation: Conversation? = null
        val nativeLanguage: ObservableField<String> = ObservableField()
        val foreignLanguage: ObservableField<String> = ObservableField()
        lateinit var nativeLanguageHint: String
        lateinit var foreignLanguageHint: String

        fun getInstance(context: Context): Conversation? {
            if (conversation == null) {
                conversation = Conversation(context)
            }
            return conversation
        }
    }
}


package com.speech.viewModel.conversation

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import com.speech.util.EventObserver
import com.speech.util.REQUEST_SPEAK_FOREIGN
import com.speech.util.REQUEST_SPEAK_NATIVE

open class Conversation(context: Context): ConversationBase(context) {

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


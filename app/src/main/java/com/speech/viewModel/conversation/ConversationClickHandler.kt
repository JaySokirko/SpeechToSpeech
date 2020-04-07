package com.speech.viewModel.conversation

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.speech.R
import com.speech.util.EventObserver

class ConversationClickHandler(private val context: Context): Conversation(context) {

    val clicksObserver: MutableLiveData<EventObserver.Event> = MutableLiveData()

    fun startSpeak() {
        clicksObserver.postValue(EventObserver.Event.START_SPEAK_INTENT)
    }

    fun textToSpeech(text: ObservableField<String>, language: ObservableField<String>){
        textToSpeechService.speak(language.get().toString(), text.get().toString())
    }

    fun setRussianLanguage(){
        clicksObserver.postValue(EventObserver.Event.RUSSIAN_LANGUAGE_SELECTED)
        nativeLanguage.set(context.resources.getString(R.string.ru))
        foreignLanguage.set(context.resources.getString(R.string.en))
        nativeLanguageHint = context.getString(R.string.speech_intent_hint_ru)
        foreignLanguageHint = context.getString(R.string.speech_intent_hint_en)
    }

    fun setEnglishLanguage(){
        clicksObserver.postValue(EventObserver.Event.ENGLISH_LANGUAGE_SELECTED)
        nativeLanguage.set(context.getString(R.string.en))
        foreignLanguage.set(context.getString(R.string.ru))
        nativeLanguageHint = context.getString(R.string.speech_intent_hint_en)
        foreignLanguageHint = context.getString(R.string.speech_intent_hint_ru)
    }
}
package com.speech.viewModel.conversation

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.speech.util.EventObserver

class ClickHandler(context: Context): Conversation(context) {

    val clicksObserver: MutableLiveData<EventObserver.Event> = MutableLiveData()

    fun startSpeak() {
        clicksObserver.postValue(EventObserver.Event.START_SPEAK_INTENT)
    }

    fun textToSpeech(observableField: ObservableField<String>, speechLanguage: String){
        val text: String = observableField.get().toString()
        textToSpeechService.speak(speechLanguage, text)
    }
}
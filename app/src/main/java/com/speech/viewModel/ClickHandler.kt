package com.speech.viewModel

import androidx.lifecycle.MutableLiveData
import com.speech.util.EventObserver

class ClickHandler{

    val clicksObserver: MutableLiveData<EventObserver.Event> = MutableLiveData()

    fun onSpeechButtonClick() {
        clicksObserver.postValue(EventObserver.Event.START_SPEECH_INTENT)
    }
}
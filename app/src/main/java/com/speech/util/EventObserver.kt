package com.speech.util

import androidx.lifecycle.MutableLiveData

class EventObserver {

    companion object{
        val commonObserver: MutableLiveData<Event> = MutableLiveData()
    }

    enum class Event {
        START_SPEECH_INTENT,
        SPEECH_NATIVE_INTENT_FINISH,
        SPEECH_FOREIGN_INTENT_FINISH
    }
}
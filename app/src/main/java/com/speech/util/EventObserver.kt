package com.speech.util

import androidx.lifecycle.MutableLiveData

class EventObserver {

    companion object{
        val commonObserver: MutableLiveData<Event> = MutableLiveData()
    }

    enum class Event {
        START_SPEAK_INTENT,
        SPEAK_NATIVE_INTENT_FINISH,
        SPEAK_FOREIGN_INTENT_FINISH
    }
}
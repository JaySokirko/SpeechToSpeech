package com.speech.di

import com.speech.service.TextToSpeech
import com.speech.service.translation.GoogleTranslator
import dagger.Component

@Component(modules = [TranslationModule::class])
interface TranslationComponent {
    fun getTextToSpeech(): TextToSpeech
    fun getGoogleTranslator(): GoogleTranslator
}
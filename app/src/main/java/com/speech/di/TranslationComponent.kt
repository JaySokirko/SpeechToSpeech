package com.speech.di

import com.speech.service.GoogleTranslator
import com.speech.util.TextToSpeech
import dagger.Component

@Component(modules = [TranslationModule::class])
interface TranslationComponent {
    fun getTextToSpeech(): TextToSpeech
    fun getGoogleTranslator(): GoogleTranslator
}
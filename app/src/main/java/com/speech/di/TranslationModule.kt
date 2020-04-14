package com.speech.di

import android.content.Context
import com.speech.service.translation.GoogleTranslator
import com.speech.service.TextToSpeech
import dagger.Module
import dagger.Provides

@Module
class TranslationModule(val context: Context) {

    @Provides
    fun provideTextToSpeech(): TextToSpeech {
        return TextToSpeech(context)
    }

    @Provides
    fun provideGoogleTranslator(): GoogleTranslator {
        return GoogleTranslator(context)
    }
}
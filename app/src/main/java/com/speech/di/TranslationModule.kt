package com.speech.di

import android.content.Context
import com.speech.service.GoogleTranslator
import com.speech.util.TextToSpeech
import com.speech.viewModel.Translation
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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
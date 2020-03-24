package com.speech.viewModel.conversation

import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.speech.R
import com.speech.di.DaggerTranslationComponent
import com.speech.di.TranslationComponent
import com.speech.di.TranslationModule
import com.speech.service.GoogleTranslator
import com.speech.service.TextToSpeech

open class ConversationParent(context: Context) {

    var nativeTranslate: ObservableField<String> = ObservableField()
    var foreignTranslate: ObservableField<String> = ObservableField()
    var translatedTextNullOrEmpty: ObservableBoolean = ObservableBoolean()
    val nativeLanguage: ObservableField<String> = ObservableField()
    val foreignLanguage: ObservableField<String> = ObservableField()

    init {
        translatedTextNullOrEmpty.set(true)
        nativeLanguage.set(context.getString(R.string.ru))
        foreignLanguage.set(context.getString(R.string.en))
    }

    protected val dagger: TranslationComponent = DaggerTranslationComponent
        .builder()
        .translationModule(TranslationModule(context))
        .build()

    protected val textToSpeechService: TextToSpeech = dagger.getTextToSpeech()
    protected val googleTranslator: GoogleTranslator = dagger.getGoogleTranslator()

}
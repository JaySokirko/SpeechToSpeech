package com.speech.viewModel.conversation

import android.content.Context
import com.speech.di.DaggerTranslationComponent
import com.speech.di.TranslationComponent
import com.speech.di.TranslationModule
import com.speech.service.TextToSpeech
import com.speech.service.translation.ITranslator
import com.speech.viewModel.IBindable

open class ConversationBase(context: Context): IBindable {

    protected val dagger: TranslationComponent = DaggerTranslationComponent
        .builder()
        .translationModule(TranslationModule(context))
        .build()

    protected val textToSpeechService: TextToSpeech = dagger.getTextToSpeech()
    protected val translator: ITranslator = dagger.getGoogleTranslator()

    override fun onDestroy() {
        textToSpeechService.shutdown()
    }
}
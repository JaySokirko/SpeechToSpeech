package com.speech.ui

import android.content.Intent
import android.speech.RecognizerIntent
import androidx.fragment.app.Fragment
import com.speech.viewModel.Translation

abstract class SpeakerParentFragment : Fragment() {

    abstract var translation: Translation

    protected fun getSpeechIntent(language: String, hint: String): Intent {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, hint)
        return intent
    }

    override fun onDestroy() {
        super.onDestroy()
        translation.onDestroy()
    }
}
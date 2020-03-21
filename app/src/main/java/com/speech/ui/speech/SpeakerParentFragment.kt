package com.speech.ui.speech

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.fragment.app.Fragment
import com.speech.util.REQUEST_SPEECH_NATIVE
import com.speech.util.REQUEST_SPEECH_FOREIGN
import com.speech.viewModel.ClickHandler
import com.speech.viewModel.Translation

abstract class SpeakerParentFragment : Fragment() {

    protected lateinit var translation: Translation
    protected lateinit var clickHandler: ClickHandler

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        translation = Translation.getInstance(context!!)
        clickHandler = ClickHandler()
    }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data ?: return
        if (requestCode == REQUEST_SPEECH_NATIVE || requestCode == REQUEST_SPEECH_FOREIGN) {
            translation.onActivityResult(requestCode, data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        translation.onDestroy()
    }
}
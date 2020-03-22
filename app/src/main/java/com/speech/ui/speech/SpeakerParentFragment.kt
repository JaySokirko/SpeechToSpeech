package com.speech.ui.speech

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.fragment.app.Fragment
import com.speech.util.REQUEST_SPEAK_NATIVE
import com.speech.util.REQUEST_SPEAK_FOREIGN
import com.speech.viewModel.conversation.ClickHandler
import com.speech.viewModel.conversation.Conversation

abstract class SpeakerParentFragment : Fragment() {

    protected lateinit var conversation: Conversation
    protected lateinit var clickHandler: ClickHandler

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        conversation = Conversation.getInstance(context!!)!!
        clickHandler = ClickHandler(context!!)
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
        if (requestCode == REQUEST_SPEAK_NATIVE || requestCode == REQUEST_SPEAK_FOREIGN) {
            conversation.onActivityResult(requestCode, data)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        conversation.onDestroy()
    }
}
package com.speech.ui.conversation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.RecognizerIntent
import androidx.fragment.app.Fragment
import com.speech.ui.dialog.NoInternetConnectionDialog
import com.speech.util.REQUEST_SPEAK_NATIVE
import com.speech.util.REQUEST_SPEAK_FOREIGN
import com.speech.viewModel.conversation.ConversationClickHandler
import com.speech.viewModel.conversation.Conversation

abstract class SpeakerBaseFragment : Fragment() {

    protected val handler = Handler()
    protected lateinit var conversation: Conversation
    protected lateinit var conversationClickHandler: ConversationClickHandler

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        conversation = Conversation.getInstance(context!!)!!
        conversationClickHandler = ConversationClickHandler(context!!)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data ?: return
        if (requestCode == REQUEST_SPEAK_NATIVE || requestCode == REQUEST_SPEAK_FOREIGN) {
            conversation.onActivityResult(requestCode, data)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        conversation.onDestroy()
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

    protected fun showNoInternetConnectionDialog() {
        NoInternetConnectionDialog().show(
            fragmentManager!!,
            NoInternetConnectionDialog::class.java.canonicalName
        )
    }
}
package com.speech.ui.conversation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.speech.R
import com.speech.databinding.FragmentForeignSpeakerBinding
import com.speech.util.EventObserver
import com.speech.util.InternetConnection
import com.speech.util.REQUEST_SPEAK_FOREIGN
import com.speech.viewModel.conversation.Conversation

class ForeignSpeakerFragment : SpeakerBaseFragment() {

    private lateinit var binding: FragmentForeignSpeakerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_foreign_speaker,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.conversation = conversation
        binding.clickHandler = conversationClickHandler

        conversationClickHandler.clicksObserver.observe(this, Observer { event ->
            if (event == EventObserver.Event.START_SPEAK_INTENT) {
                if (!InternetConnection.isInternetConnectionEnabled(context!!)){
                    showNoInternetConnectionDialog()
                } else {
                    startActivityForResult(getSpeechIntent(Conversation.foreignLanguage.get().toString(),
                        Conversation.foreignLanguageHint), REQUEST_SPEAK_FOREIGN)
                }
            }
        })
    }
}
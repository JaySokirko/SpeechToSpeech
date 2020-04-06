package com.speech.ui.conversation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.speech.R
import com.speech.databinding.FragmentNativeSpeakerBinding
import com.speech.util.EventObserver
import com.speech.util.InternetConnection
import com.speech.util.REQUEST_SPEAK_NATIVE
import kotlinx.android.synthetic.main.fragment_native_speaker.*

class NativeSpeakerFragment : SpeakerBaseFragment() {

    private lateinit var binding: FragmentNativeSpeakerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_native_speaker,
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
                if (!InternetConnection.isInternetConnectionEnabled(context!!)) {
                    showInternetConnectionDialog()
                } else {
                    startActivityForResult(
                        getSpeechIntent("ru", getString(R.string.speech_intent_hint_ru)),
                        REQUEST_SPEAK_NATIVE
                    )
                }
            }
        })

        EventObserver.commonObserver.observe(this, Observer { event ->
            if (event == EventObserver.Event.ENGLISH_LANGUAGE_SET) {
                motion_layout.transitionToEnd()
            }
            else if (event == EventObserver.Event.RUSSIAN_LANGUAGE_SET) {
//                motion_layout.transitionToStart()
            }
        })
    }
}
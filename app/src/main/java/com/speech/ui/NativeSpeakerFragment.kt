package com.speech.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.speech.R
import com.speech.databinding.FragmentNativeSpeakerBinding
import com.speech.util.REQUEST_SPEECH_NATIVE
import com.speech.viewModel.Event
import com.speech.viewModel.Translation

class NativeSpeakerFragment : SpeakerParentFragment() {

    override lateinit var translation: Translation
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
            false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        translation = Translation(context = context!!)
        binding.translation = translation

        translation.eventObserver.observe(this, Observer { event ->
            if (event == Event.START_SPEECH_INTENT) {
                startActivityForResult(getSpeechIntent("ru",
                        resources.getString(R.string.speech_intent_hint_ru)), REQUEST_SPEECH_NATIVE)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data ?: return
        if (requestCode == REQUEST_SPEECH_NATIVE) {
            translation.onActivityResult(requestCode, data)
        }
    }
}
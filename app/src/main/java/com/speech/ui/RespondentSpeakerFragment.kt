package com.speech.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.speech.R
import com.speech.databinding.FragmentRespondentSpeakerBinding
import com.speech.util.REQUEST_SPEECH_RESPONDENT
import com.speech.viewModel.Event
import com.speech.viewModel.Translation

class RespondentSpeakerFragment : SpeakerParentFragment() {

    override lateinit var translation: Translation
    private lateinit var binding: FragmentRespondentSpeakerBinding
    private val TAG = "TAG"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_respondent_speaker,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        translation = Translation(context = context!!)
        binding.translation = translation

        translation.eventObserver.observe(this, Observer { event ->
            if (event == Event.START_SPEECH_INTENT) {
                startActivityForResult(getSpeechIntent("en",
                resources.getString(R.string.speech_intent_hint_en)), REQUEST_SPEECH_RESPONDENT)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data ?: return
        if (requestCode == REQUEST_SPEECH_RESPONDENT) {
            translation.onActivityResult(requestCode, data)
        }
    }
}
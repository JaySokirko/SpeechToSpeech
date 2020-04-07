package com.speech.ui.conversation

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.speech.R
import com.speech.databinding.FragmentNativeSpeakerBinding
import com.speech.service.Logger
import com.speech.util.EventObserver
import com.speech.util.InternetConnection
import com.speech.util.REQUEST_SPEAK_NATIVE
import com.speech.viewModel.conversation.Conversation
import kotlinx.android.synthetic.main.fragment_native_speaker.*


class NativeSpeakerFragment : SpeakerBaseFragment() {

    private lateinit var binding: FragmentNativeSpeakerBinding
    private var speechBtnAnimDuration: Long = 0

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
        speechBtnAnimDuration = resources.getInteger(R.integer.speech_btn_anim_duration).toLong()

        conversationClickHandler.clicksObserver.observe(this, Observer { event ->
            if (event == EventObserver.Event.START_SPEAK_INTENT) {
                if (!InternetConnection.isInternetConnectionEnabled(context!!)) {
                    showNoInternetConnectionDialog()
                } else {
                    try {
                        startActivityForResult(getSpeechIntent(Conversation.nativeLanguage.get().toString(),
                            Conversation.nativeLanguageHint), REQUEST_SPEAK_NATIVE)
                    } catch (exception: ActivityNotFoundException){
                        Logger.log(exception)
                    }
                }
            }
            else if (event == EventObserver.Event.ENGLISH_LANGUAGE_SELECTED) {
                language_buttons_motion_layout.transitionToEnd()
                translate_motion_layout.transitionToEnd()
                disableSpeechBtn()
            }
            else if (event == EventObserver.Event.RUSSIAN_LANGUAGE_SELECTED) {
                language_buttons_motion_layout.transitionToStart()
                translate_motion_layout.transitionToEnd()
                disableSpeechBtn()
            }
        })

        EventObserver.commonObserver.observe(this, Observer { event ->
            if (event == EventObserver.Event.SPEAK_NATIVE_INTENT_FINISH ||
                event == EventObserver.Event.SPEAK_FOREIGN_INTENT_FINISH) {
                translate_motion_layout.transitionToStart()
                enableSpeechBtn()
            }
        })
    }

    private fun enableSpeechBtn(){
        speech_btn.animate().setDuration(speechBtnAnimDuration).alpha(1f).start()
        handler.postDelayed({ speech_btn.isEnabled = true }, speechBtnAnimDuration)
    }

    private fun disableSpeechBtn(){
        speech_btn.animate().setDuration(speechBtnAnimDuration).alpha(0f).start()
        handler.postDelayed({ speech_btn.isEnabled = false }, speechBtnAnimDuration)
    }
}
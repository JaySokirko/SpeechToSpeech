package com.speech.ui

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.speech.R
import com.speech.viewModel.Event
import com.speech.viewModel.Translation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var backgroundAnimation: AnimationDrawable
    private val TAG = "TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupAnimatedBackground()
        setupFragmentPagerAdapter()

//        val translation = Translation(applicationContext)
//        translation.eventObserver.observe(this, Observer { event ->
//            if (event == Event.SPEECH_RESPONDENT_FINISH){
//                view_pager.setCurrentItem(0, true)
//            }
//            if (event == Event.SPEECH_NATIVE_FINISH){
//                view_pager.setCurrentItem(1, true)
//            }
//        })
    }

    override fun onResume() {
        super.onResume()
        backgroundAnimation.start()
    }

    override fun onPause() {
        super.onPause()
        backgroundAnimation.stop()
    }

    private fun setupFragmentPagerAdapter() {
        val fragmentList = listOf(NativeSpeakerFragment(), RespondentSpeakerFragment())
        val adapter = FragmentPagerAdapter(supportFragmentManager, fragmentList)
        view_pager.adapter = adapter
    }

    private fun setupAnimatedBackground() {
        backgroundAnimation = parent_layout.background as AnimationDrawable
        backgroundAnimation.setEnterFadeDuration(5000);
        backgroundAnimation.setExitFadeDuration(2000);
    }
}

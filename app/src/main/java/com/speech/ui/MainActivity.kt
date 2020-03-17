package com.speech.ui

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.speech.R
import com.speech.databinding.ActivityMainBinding
import com.speech.util.SPEECH_REQUEST
import com.speech.viewModel.Translation
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var backgroundAnimation: AnimationDrawable
    private lateinit var translation: Translation

    private val TAG = "TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupAnimatedBackground()

        translation = Translation(context = this, activity = this)
        binding.translation = translation
    }

    override fun onResume() {
        super.onResume()
        backgroundAnimation.start()
    }

    override fun onPause() {
        super.onPause()
        backgroundAnimation.stop()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data ?: return
        if(requestCode == SPEECH_REQUEST) {
            translation.onActivityResult(requestCode, data)
        }
    }

    private fun setupAnimatedBackground() {
        backgroundAnimation = parent_layout.background as AnimationDrawable
        backgroundAnimation.setEnterFadeDuration(5000);
        backgroundAnimation.setExitFadeDuration(2000);
    }
}
